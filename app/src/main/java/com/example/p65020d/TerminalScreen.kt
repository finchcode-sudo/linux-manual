package com.example.p65020d

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Typeface
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.termux.terminal.TerminalSession
import com.termux.terminal.TerminalSessionClient
import com.termux.view.TerminalView
import com.termux.view.TerminalViewClient

/** 全局持有终端会话：切换底部 tab 时会话不会丢失。 */
object TerminalHost : TerminalViewClient, TerminalSessionClient {
    var session: TerminalSession? = null
    var view: TerminalView? = null
    var ctrl by mutableStateOf(false)
    var alt by mutableStateOf(false)
    var finished by mutableStateOf(false)
    var textSizePx = 0
    private var appContext: Context? = null

    fun ensureSession(context: Context): TerminalSession {
        appContext = context.applicationContext
        session?.let { if (it.isRunning) return it }
        val home = context.filesDir.absolutePath
        val env = arrayOf(
            "TERM=xterm-256color", "COLORTERM=truecolor", "HOME=$home", "TMPDIR=${context.cacheDir.absolutePath}",
            "SHELL=/system/bin/sh", "LANG=en_US.UTF-8", "PREFIX=/system",
            "PATH=/system/bin:/system/xbin:/vendor/bin", "ANDROID_ROOT=/system", "ANDROID_DATA=/data",
            "EXTERNAL_STORAGE=/sdcard"
        )
        val s = TerminalSession("/system/bin/sh", home, arrayOf("sh"), env, 2000, this)
        session = s
        finished = false
        return s
    }

    fun send(text: String) {
        val b = text.toByteArray(Charsets.UTF_8)
        session?.write(b, 0, b.size)
    }

    // ---- TerminalSessionClient ----
    override fun onTextChanged(changedSession: TerminalSession) { view?.onScreenUpdated() }
    override fun onTitleChanged(changedSession: TerminalSession) {}
    override fun onSessionFinished(finishedSession: TerminalSession) { finished = true }
    override fun onCopyTextToClipboard(session: TerminalSession, text: String?) {
        val cm = appContext?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        cm?.setPrimaryClip(ClipData.newPlainText("terminal", text ?: ""))
    }
    override fun onPasteTextFromClipboard(session: TerminalSession?) {
        val cm = appContext?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val t = cm?.primaryClip?.takeIf { it.itemCount > 0 }?.getItemAt(0)?.coerceToText(appContext)
        if (!t.isNullOrEmpty()) session?.emulator?.paste(t.toString())
    }
    override fun onBell(session: TerminalSession) {}
    override fun onColorsChanged(session: TerminalSession) { view?.onScreenUpdated() }
    override fun onTerminalCursorStateChange(state: Boolean) {}
    override fun getTerminalCursorStyle(): Int? = null

    // ---- TerminalViewClient ----
    override fun onScale(scale: Float): Float {
        if (scale < 0.9f || scale > 1.1f) {
            val delta = if (scale > 1f) 2 else -2
            textSizePx = (textSizePx + delta).coerceIn(16, 120)
            view?.setTextSize(textSizePx)
            return 1.0f
        }
        return scale
    }
    override fun onSingleTapUp(e: MotionEvent?) {
        view?.let {
            it.requestFocus()
            (it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(it, 0)
        }
    }
    override fun shouldBackButtonBeMappedToEscape() = false
    override fun shouldEnforceCharBasedInput() = true
    override fun shouldUseCtrlSpaceWorkaround() = false
    override fun isTerminalViewSelected() = true
    override fun copyModeChanged(copyMode: Boolean) {}
    override fun onKeyDown(keyCode: Int, e: KeyEvent?, session: TerminalSession?) = false
    override fun onKeyUp(keyCode: Int, e: KeyEvent?) = false
    override fun onLongPress(event: MotionEvent?) = false
    override fun readControlKey(): Boolean { val v = ctrl; if (v) ctrl = false; return v }
    override fun readAltKey(): Boolean { val v = alt; if (v) alt = false; return v }
    override fun readShiftKey() = false
    override fun readFnKey() = false
    override fun onCodePoint(codePoint: Int, ctrlDown: Boolean, session: TerminalSession?) = false
    override fun onEmulatorSet() {}
    override fun logError(tag: String?, message: String?) {}
    override fun logWarn(tag: String?, message: String?) {}
    override fun logInfo(tag: String?, message: String?) {}
    override fun logDebug(tag: String?, message: String?) {}
    override fun logVerbose(tag: String?, message: String?) {}
    override fun logStackTraceWithMessage(tag: String?, message: String?, e: Exception?) {}
    override fun logStackTrace(tag: String?, e: Exception?) {}
}

@Composable
fun TerminalScreen(contentPadding: PaddingValues) {
    val context = LocalContext.current
    var restartKey by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .consumeWindowInsets(contentPadding)
            .imePadding()
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            key(restartKey) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        val s = TerminalHost.ensureSession(ctx)
                        TerminalView(ctx, null).apply {
                            if (TerminalHost.textSizePx == 0) {
                                TerminalHost.textSizePx = (14 * ctx.resources.displayMetrics.scaledDensity).toInt()
                            }
                            setTextSize(TerminalHost.textSizePx)
                            setTypeface(Typeface.MONOSPACE)
                            setTerminalViewClient(TerminalHost)
                            attachSession(s)
                            isFocusable = true
                            isFocusableInTouchMode = true
                            TerminalHost.view = this
                            requestFocus()
                        }
                    },
                    onRelease = { if (TerminalHost.view === it) TerminalHost.view = null }
                )
            }
            if (TerminalHost.finished) {
                Button(
                    onClick = { TerminalHost.session = null; restartKey++ },
                    modifier = Modifier.align(Alignment.Center)
                ) { Text("会话已结束，点击重新启动") }
            }
        }
        ExtraKeysRow()
    }
}

@Composable
private fun ExtraKeysRow() {
    val v = TerminalHost.view
    fun key(code: Int) { v?.handleKeyCode(code, 0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        @Composable fun K(label: String, active: Boolean = false, onClick: () -> Unit) {
            TextButton(
                onClick = onClick,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (active) Color(0xFF8AB4F8) else Color.White
                )
            ) { Text(label, fontSize = 14.sp) }
        }
        K("ESC") { key(KeyEvent.KEYCODE_ESCAPE) }
        K("TAB") { key(KeyEvent.KEYCODE_TAB) }
        K("CTRL", TerminalHost.ctrl) { TerminalHost.ctrl = !TerminalHost.ctrl }
        K("ALT", TerminalHost.alt) { TerminalHost.alt = !TerminalHost.alt }
        K("^C") { TerminalHost.send("\u0003") }
        K("^D") { TerminalHost.send("\u0004") }
        K("↑") { key(KeyEvent.KEYCODE_DPAD_UP) }
        K("↓") { key(KeyEvent.KEYCODE_DPAD_DOWN) }
        K("←") { key(KeyEvent.KEYCODE_DPAD_LEFT) }
        K("→") { key(KeyEvent.KEYCODE_DPAD_RIGHT) }
        K("/") { TerminalHost.send("/") }
        K("-") { TerminalHost.send("-") }
        K("|") { TerminalHost.send("|") }
        K("~") { TerminalHost.send("~") }
    }
}
