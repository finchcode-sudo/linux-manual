package com.termux

import android.content.Context
import android.system.Os
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File
import java.util.zip.ZipInputStream

/** 首次使用时，把打包在 APK 里的 Termux bootstrap 解压到 files/usr。 */
object Bootstrap {
    enum class State { IDLE, INSTALLING, DONE, ERROR }

    var state by mutableStateOf(State.IDLE)
    var fileCount by mutableIntStateOf(0)
    var error by mutableStateOf("")

    fun prefix(ctx: Context) = File(ctx.filesDir, "usr")
    fun home(ctx: Context) = File(ctx.filesDir, "home")
    fun isInstalled(ctx: Context) = File(prefix(ctx), "bin/bash").exists()

    @Synchronized
    fun startInstall(ctx: Context) {
        if (state == State.INSTALLING) return
        if (isInstalled(ctx)) { state = State.DONE; return }
        state = State.INSTALLING
        fileCount = 0
        error = ""
        val app = ctx.applicationContext
        Thread {
            try {
                doInstall(app)
                state = State.DONE
            } catch (t: Throwable) {
                error = t.stackTraceToString().take(900)
                state = State.ERROR
            }
        }.start()
    }

    private fun doInstall(ctx: Context) {
        val staging = File(ctx.filesDir, "usr-staging")
        staging.deleteRecursively()
        staging.mkdirs()
        val symlinks = ArrayList<Pair<String, String>>()

        ctx.assets.open("bootstrap-aarch64.zip").use { raw ->
            ZipInputStream(raw.buffered()).use { zip ->
                val buf = ByteArray(64 * 1024)
                while (true) {
                    val e = zip.nextEntry ?: break
                    val name = e.name
                    if (name == "SYMLINKS.txt") {
                        val text = String(zip.readBytes(), Charsets.UTF_8)
                        for (line in text.lines()) {
                            if (line.isBlank()) continue
                            val parts = line.split("←")
                            if (parts.size != 2) throw RuntimeException("bad SYMLINKS line: $line")
                            symlinks.add(parts[0] to parts[1])
                        }
                    } else if (e.isDirectory) {
                        File(staging, name).mkdirs()
                    } else {
                        val f = File(staging, name)
                        f.parentFile?.mkdirs()
                        f.outputStream().use { out ->
                            while (true) {
                                val n = zip.read(buf)
                                if (n < 0) break
                                out.write(buf, 0, n)
                            }
                        }
                        if (name.startsWith("bin/") || name.startsWith("libexec") ||
                            name.startsWith("lib/apt/apt-helper") || name.startsWith("lib/apt/methods")
                        ) {
                            Os.chmod(f.absolutePath, 448) // 0700
                        }
                        fileCount++
                    }
                }
            }
        }
        if (symlinks.isEmpty()) throw RuntimeException("No SYMLINKS.txt in bootstrap")
        for ((target, path) in symlinks) {
            val link = File(staging, path)
            link.parentFile?.mkdirs()
            Os.symlink(target, link.absolutePath)
        }
        val prefix = prefix(ctx)
        prefix.deleteRecursively()
        if (!staging.renameTo(prefix)) throw RuntimeException("Cannot move staging to ${prefix.absolutePath}")
        home(ctx).mkdirs()
        File(prefix, "tmp").mkdirs()
    }
}
