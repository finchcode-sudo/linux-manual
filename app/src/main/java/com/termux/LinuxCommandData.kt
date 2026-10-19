package com.termux

import androidx.compose.runtime.Immutable

/**
 * Linux 指令分类
 */
enum class CmdCategory(val displayName: String, val icon: String) {
    FILE_MGMT("文件管理", "📁"),
    FILE_VIEW("文件查看", "📄"),
    TEXT_PROC("文本处理", "✏️"),
    SYS_INFO("系统信息", "🔧"),
    PROC_MGMT("进程管理", "⚙️"),
    NETWORK("网络相关", "🌐"),
    PERM_USER("权限用户", "🔐"),
    COMPRESS("压缩归档", "🗜️"),
    PACKAGE("包管理", "📦"),
    DEV_TOOLS("开发工具", "🔨"),
    SCHEDULE("任务调度", "⏰"),
    DEBUG_TOOLS("调试分析", "🐛")
}

/**
 * 指令示例
 */
@Immutable
data class CmdExample(
    val description: String,
    val code: String
)

/**
 * 一条 Linux 指令
 */
@Immutable
data class LinuxCommand(
    val name: String,
    val category: CmdCategory,
    val description: String,
    val syntax: String,
    val examples: List<CmdExample>,
    val icon: String,
    val tips: String? = null
)

/**
 * 完整 Linux 指令数据库
 */
val allLinuxCommands = listOf(
    // ==================== 📁 文件管理 ====================
    LinuxCommand(
        "ls", CmdCategory.FILE_MGMT, "列出目录内容",
        "ls [选项] [路径]",
        listOf(
            CmdExample("列出当前目录所有文件", "ls"),
            CmdExample("显示详细信息（权限、大小、修改时间）", "ls -l"),
            CmdExample("显示隐藏文件（以 . 开头的文件）", "ls -a"),
            CmdExample("以人类可读格式显示大小", "ls -lh"),
            CmdExample("按修改时间排序（最新的在前）", "ls -lt"),
            CmdExample("递归列出子目录", "ls -R"),
            CmdExample("按文件大小排序（最大的在前）", "ls -lhS"),
            CmdExample("按时间排序（最旧的在前）", "ls -ltr"),
            CmdExample("只列出当前目录下的子目录", "ls -d */"),
            CmdExample("统计当前目录文件数量", "ls -1 | wc -l"),
            CmdExample("查看最近修改的 10 个文件", "ls -lt | head -n 10"),
            CmdExample("显示隐藏文件但不含 . 和 ..", "ls -A")
        ), "📁", "常用组合: ls -lah 显示所有文件及详细信息"
    ),
    LinuxCommand(
        "cd", CmdCategory.FILE_MGMT, "切换当前工作目录",
        "cd [目录路径]",
        listOf(
            CmdExample("进入指定目录", "cd /home/user/Documents"),
            CmdExample("返回上一级目录", "cd .."),
            CmdExample("返回上两级目录", "cd ../.."),
            CmdExample("返回用户主目录", "cd ~"),
            CmdExample("返回上一个工作目录", "cd -"),
            CmdExample("进入根目录", "cd /"),
            CmdExample("在脚本中切换到脚本自身所在目录", "cd \"$(dirname \"$0\")\""),
            CmdExample("创建并进入新目录", "mkdir -p demo && cd demo"),
            CmdExample("进入刚下载的最新目录", "cd \"\$(ls -dt */ | head -1)\""),
            CmdExample("进入符号链接指向的真实目录", "cd -P /path/to/link")
        ), "📂"
    ),
    LinuxCommand(
        "pwd", CmdCategory.FILE_MGMT, "显示当前工作目录的绝对路径",
        "pwd",
        listOf(
            CmdExample("显示当前路径", "pwd"),
            CmdExample("显示物理路径（忽略符号链接）", "pwd -P"),
            CmdExample("将当前路径保存到变量", "dir=$(pwd)")
        ), "📍"
    ),
    LinuxCommand(
        "cp", CmdCategory.FILE_MGMT, "复制文件或目录",
        "cp [选项] 源 目标",
        listOf(
            CmdExample("复制文件", "cp file1.txt file2.txt"),
            CmdExample("递归复制目录", "cp -r dir1 dir2"),
            CmdExample("保留文件属性复制", "cp -p file1 file2"),
            CmdExample("交互式复制（覆盖前确认）", "cp -i source dest"),
            CmdExample("显示复制进度", "cp -v file1 file2"),
            CmdExample("只在源文件更新时才覆盖复制", "cp -u file1.txt file2.txt"),
            CmdExample("归档模式：保留权限/时间戳并递归复制", "cp -a dir1 dir2"),
            CmdExample("保留权限和时间戳完整复制目录", "cp -a src/ backup/"),
            CmdExample("复制时显示过程", "cp -rv src/ dst/"),
            CmdExample("不覆盖已存在的文件", "cp -n a.txt /tmp/"),
            CmdExample("覆盖前自动备份旧文件", "cp --backup=numbered a.txt /tmp/"),
            CmdExample("仅复制较新的文件", "cp -u *.txt /backup/"),
            CmdExample("保留权限和时间戳复制目录", "cp -a src/ dst/"),
            CmdExample("只复制较新的文件", "cp -u *.txt backup/"),
            CmdExample("覆盖前自动备份", "cp --backup=numbered a.txt b.txt")
        ), "📋", "复制目录时必须加 -r 选项"
    ),
    LinuxCommand(
        "mv", CmdCategory.FILE_MGMT, "移动或重命名文件/目录",
        "mv [选项] 源 目标",
        listOf(
            CmdExample("重命名文件", "mv old.txt new.txt"),
            CmdExample("移动文件到另一目录", "mv file.txt /path/to/dir/"),
            CmdExample("移动目录", "mv dir1 /path/to/dir2/"),
            CmdExample("交互式移动（覆盖前确认）", "mv -i source dest"),
            CmdExample("不覆盖已有文件", "mv -n source dest"),
            CmdExample("覆盖前确认", "mv -i a.txt /tmp/"),
            CmdExample("批量修改扩展名", "for f in *.txt; do mv \"\$f\" \"\${f%.txt}.md\"; done"),
            CmdExample("快速备份文件（加 .bak 后缀）", "mv file{,.bak}"),
            CmdExample("不覆盖已存在文件", "mv -n *.jpg photos/")
        ), "✂️"
    ),
    LinuxCommand(
        "rm", CmdCategory.FILE_MGMT, "删除文件或目录",
        "rm [选项] 路径",
        listOf(
            CmdExample("删除文件", "rm file.txt"),
            CmdExample("递归删除目录及其内容", "rm -r dir/"),
            CmdExample("强制删除（不提示）", "rm -f file.txt"),
            CmdExample("递归强制删除目录", "rm -rf dir/"),
            CmdExample("交互式删除（逐个确认）", "rm -i file.txt"),
            CmdExample("删除前逐个确认", "rm -i *.log"),
            CmdExample("删除 7 天前的 .tmp 文件", "find . -name \"*.tmp\" -mtime +7 -delete"),
            CmdExample("删除以 - 开头的文件", "rm -- -weird-file"),
            CmdExample("删除 7 天前的日志", "find /var/log/app -name \"*.log\" -mtime +7 -delete")
        ), "🗑️", "⚠️ rm -rf / 会删除整个系统，慎用！建议先 cd 确认位置"
    ),
    LinuxCommand(
        "mkdir", CmdCategory.FILE_MGMT, "创建新目录",
        "mkdir [选项] 目录名",
        listOf(
            CmdExample("创建单个目录", "mkdir myfolder"),
            CmdExample("创建多级目录（自动创建父目录）", "mkdir -p a/b/c/d"),
            CmdExample("设置目录权限", "mkdir -m 755 myfolder"),
            CmdExample("创建多个目录", "mkdir dir1 dir2 dir3"),
            CmdExample("一次创建项目目录结构", "mkdir -p proj/{src,docs,tests}"),
            CmdExample("创建目录并指定权限", "mkdir -m 755 public"),
            CmdExample("创建多级目录", "mkdir -p a/b/c"),
            CmdExample("创建并进入目录", "mkdir -p demo && cd demo")
        ), "📁"
    ),
    LinuxCommand(
        "rmdir", CmdCategory.FILE_MGMT, "删除空目录",
        "rmdir [选项] 目录名",
        listOf(
            CmdExample("删除空目录", "rmdir emptydir"),
            CmdExample("删除空目录层级", "rmdir -p a/b/c"),
            CmdExample("配合find批量删除所有空目录", "find . -type d -empty -exec rmdir {} \\;")
        ), "📁", "目录不为空时 rmdir 会失败，此时用 rm -r"
    ),
    LinuxCommand(
        "touch", CmdCategory.FILE_MGMT, "创建空文件或更新文件时间戳",
        "touch [选项] 文件",
        listOf(
            CmdExample("创建空文件", "touch newfile.txt"),
            CmdExample("创建多个文件", "touch file1.txt file2.txt file3.txt"),
            CmdExample("将文件时间更新为当前时间", "touch existing.txt"),
            CmdExample("设置特定时间戳", "touch -t 202501011200 file.txt"),
            CmdExample("批量创建空文件", "touch {1..5}.txt"),
            CmdExample("指定文件修改时间", "touch -d \"2024-01-01 12:00\" file.txt"),
            CmdExample("让文件时间与另一个文件一致", "touch -r ref.txt file.txt")
        ), "📄"
    ),
    LinuxCommand(
        "find", CmdCategory.FILE_MGMT, "在目录树中搜索文件",
        "find [路径] [条件] [动作]",
        listOf(
            CmdExample("按文件名搜索", "find /home -name \"*.txt\""),
            CmdExample("按大小搜索（大于 100MB）", "find / -size +100M"),
            CmdExample("按修改时间搜索（7天内修改）", "find . -mtime -7"),
            CmdExample("按权限搜索", "find . -perm 755"),
            CmdExample("找到并执行命令", "find . -name \"*.log\" -exec rm {} \\;"),
            CmdExample("搜索空文件或目录", "find . -empty"),
            CmdExample("删除 7 天前的 .log 文件", "find . -name \"*.log\" -mtime +7 -delete"),
            CmdExample("查找大于 100MB 的文件", "find . -type f -size +100M"),
            CmdExample("在 .py 文件中查找含 TODO 的文件", "find . -name \"*.py\" -exec grep -l \"TODO\" {} +"),
            CmdExample("查找最近 24 小时修改的文件", "find . -type f -mtime -1"),
            CmdExample("统计所有 txt 文件总行数", "find . -name \"*.txt\" -print0 | xargs -0 wc -l"),
            CmdExample("查找空目录", "find . -type d -empty"),
            CmdExample("查找带 SUID 权限的文件", "find / -perm -4000 -type f 2>/dev/null"),
            CmdExample("在 py 文件里搜索 TODO", "find . -name \"*.py\" -exec grep -ln \"TODO\" {} +")
        ), "🔍", "-name 区分大小写，-iname 忽略大小写"
    ),
    LinuxCommand(
        "locate", CmdCategory.FILE_MGMT, "通过数据库快速定位文件",
        "locate [选项] 关键词",
        listOf(
            CmdExample("快速搜索文件", "locate myfile.txt"),
            CmdExample("忽略大小写搜索", "locate -i README"),
            CmdExample("统计匹配数量", "locate -c \"*.xml\"")
        ), "🔍", "locate 依赖 updatedb 数据库，新文件可能查不到"
    ),

    // ==================== 📄 文件查看 ====================
    LinuxCommand(
        "cat", CmdCategory.FILE_VIEW, "连接并显示文件内容",
        "cat [选项] 文件",
        listOf(
            CmdExample("显示文件内容", "cat file.txt"),
            CmdExample("显示行号", "cat -n file.txt"),
            CmdExample("合并多个文件", "cat file1.txt file2.txt > merged.txt"),
            CmdExample("显示非打印字符", "cat -A file.txt"),
            CmdExample("一次性写入多行内容到文件", "cat > hello.txt << 'EOF'"),
            CmdExample("清空文件内容", "cat /dev/null > file.txt"),
            CmdExample("用 heredoc 写入多行内容", "cat > file.txt << 'EOF'")
        ), "📄"
    ),
    LinuxCommand(
        "less", CmdCategory.FILE_VIEW, "分页查看文件内容（支持上下翻页）",
        "less [选项] 文件",
        listOf(
            CmdExample("分页查看", "less longfile.txt"),
            CmdExample("打开并定位到行号", "less +100 file.txt"),
            CmdExample("在 less 中搜索（输入 /关键词 回车）", "less file.txt  # 然后输入 /searchterm"),
            CmdExample("显示行号", "less -N file.txt"),
            CmdExample("实时跟踪文件末尾（类似 tail -f）", "less +F app.log"),
            CmdExample("查看时不换行（宽表格）", "less -S data.csv")
        ), "📖", "在 less 中: q 退出, / 搜索, n 下一个匹配, N 上一个匹配"
    ),
    LinuxCommand(
        "more", CmdCategory.FILE_VIEW, "分页查看文件（仅向下翻页）",
        "more [选项] 文件",
        listOf(
            CmdExample("分页查看文件", "more file.txt"),
            CmdExample("从指定行开始", "more +50 file.txt"),
            CmdExample("设置每页行数", "more -20 file.txt")
        ), "📖", "more 功能较少，推荐用 less"
    ),
    LinuxCommand(
        "head", CmdCategory.FILE_VIEW, "显示文件开头部分（默认 10 行）",
        "head [选项] 文件",
        listOf(
            CmdExample("显示前 10 行", "head file.txt"),
            CmdExample("显示前 20 行", "head -n 20 file.txt"),
            CmdExample("显示前 100 字节", "head -c 100 file.txt"),
            CmdExample("显示多个文件", "head -n 5 file1.txt file2.txt"),
            CmdExample("查看文件前 5 行", "head -n 5 file.txt"),
            CmdExample("显示除最后一行外的所有内容", "head -n -1 file.txt"),
            CmdExample("取前 100 字节", "head -c 100 file.bin"),
            CmdExample("跳过表头显示 CSV", "tail -n +2 data.csv | head")
        ), "⬆️"
    ),
    LinuxCommand(
        "tail", CmdCategory.FILE_VIEW, "显示文件末尾部分（默认 10 行）",
        "tail [选项] 文件",
        listOf(
            CmdExample("显示末尾 10 行", "tail file.txt"),
            CmdExample("显示末尾 50 行", "tail -n 50 file.txt"),
            CmdExample("实时跟踪文件新增内容", "tail -f /var/log/syslog"),
            CmdExample("跟踪并显示行号", "tail -n 20 -f log.txt"),
            CmdExample("从指定偏移开始显示", "tail -c +100 file.txt"),
            CmdExample("实时跟踪日志并只看错误", "tail -f app.log | grep --line-buffered ERROR"),
            CmdExample("跳过首行（去掉表头）", "tail -n +2 data.csv"),
            CmdExample("日志轮转后继续跟踪", "tail -F app.log"),
            CmdExample("同时跟踪多个日志", "tail -f a.log b.log"),
            CmdExample("从第 2 行开始输出", "tail -n +2 file.txt")
        ), "⬇️", "tail -f 常用来实时查看日志，按 Ctrl+C 退出"
    ),
    LinuxCommand(
        "nl", CmdCategory.FILE_VIEW, "为文件内容添加行号显示",
        "nl [选项] 文件",
        listOf(
            CmdExample("显示带行号的文件", "nl file.txt"),
            CmdExample("所有行都编号（含空行）", "nl -ba file.txt"),
            CmdExample("自定义行号格式", "nl -n rz file.txt  # 右对齐补零")
        ), "🔢"
    ),

    // ==================== ✏️ 文本处理 ====================
    LinuxCommand(
        "grep", CmdCategory.TEXT_PROC, "在文件中搜索匹配模式的行",
        "grep [选项] 模式 [文件]",
        listOf(
            CmdExample("在文件中搜索关键词", "grep \"error\" log.txt"),
            CmdExample("忽略大小写搜索", "grep -i \"warning\" log.txt"),
            CmdExample("递归搜索目录", "grep -r \"TODO\" ./src/"),
            CmdExample("显示匹配行及行号", "grep -n \"function\" main.kt"),
            CmdExample("显示不匹配的行", "grep -v \"debug\" log.txt"),
            CmdExample("统计匹配行数", "grep -c \"pattern\" file.txt"),
            CmdExample("使用正则表达式", "grep -E \"^[0-9]+\" file.txt"),
            CmdExample("显示匹配行及前后各 3 行", "grep -C 3 \"error\" log.txt"),
            CmdExample("递归搜索指定类型文件并显示行号", "grep -rn \"TODO\" --include=\"*.py\" ."),
            CmdExample("排除注释行和空行", "grep -Ev '^\\s*(#|\$)' config.conf"),
            CmdExample("只列出匹配的文件名", "grep -rl \"password\" /etc 2>/dev/null"),
            CmdExample("显示匹配行及上下 2 行", "grep -C 2 \"Exception\" app.log"),
            CmdExample("匹配多个关键词（忽略大小写）", "grep -iE \"error|warn|fatal\" app.log"),
            CmdExample("提取文件中的 IP 地址", "grep -oE '([0-9]{1,3}\\.){3}[0-9]{1,3}' access.log"),
            CmdExample("查进程时排除 grep 自身", "ps aux | grep [n]ginx"),
            CmdExample("匹配多个关键字（忽略大小写）", "grep -Ei \"error|warn|fatal\" app.log"),
            CmdExample("提取日志中的 IP 地址", "grep -oE '[0-9]{1,3}(\\.[0-9]{1,3}){3}' access.log")
        ), "🔎", "grep -E 支持扩展正则; 注意：grep 按行匹配"
    ),
    LinuxCommand(
        "sed", CmdCategory.TEXT_PROC, "流编辑器，对文本进行替换、删除、插入",
        "sed [选项] '命令' 文件",
        listOf(
            CmdExample("替换第一个匹配的字符串", "sed 's/old/new/' file.txt"),
            CmdExample("替换所有匹配的字符串", "sed 's/old/new/g' file.txt"),
            CmdExample("直接修改文件", "sed -i 's/foo/bar/g' file.txt"),
            CmdExample("删除匹配行", "sed '/pattern/d' file.txt"),
            CmdExample("只打印第 5-10 行", "sed -n '5,10p' file.txt"),
            CmdExample("在行前插入文本", "sed '3i\\\\插入的内容' file.txt"),
            CmdExample("全文替换并直接修改文件", "sed -i 's/old/new/g' file.txt"),
            CmdExample("替换前自动备份原文件", "sed -i.bak 's/old/new/g' file.txt"),
            CmdExample("打印第 10 到 20 行", "sed -n '10,20p' file.txt"),
            CmdExample("删除所有空行", "sed '/^\$/d' file.txt"),
            CmdExample("删除注释行", "sed '/^#/d' config.conf"),
            CmdExample("在文件第一行插入内容", "sed -i '1i # header' file.txt"),
            CmdExample("去掉每行开头的空白", "sed 's/^[ \\t]*//' file.txt")
        ), "📝", "sed -i 直接改文件，建议先不加 -i 测试效果"
    ),
    LinuxCommand(
        "awk", CmdCategory.TEXT_PROC, "强大的文本分析工具，按列处理",
        "awk [选项] '模式{动作}' 文件",
        listOf(
            CmdExample("打印第一列", "awk '{print \\$1}' file.txt"),
            CmdExample("打印特定列（以 : 分隔）", "awk -F':' '{print \\$1, \\$3}' /etc/passwd"),
            CmdExample("条件过滤", "awk '\\\\$3 > 100 {print \\$1}' file.txt"),
            CmdExample("行号与内容一起打印", "awk '{print NR, \\$0}' file.txt"),
            CmdExample("求和某一列", "awk '{sum += \\$1} END {print sum}' numbers.txt"),
            CmdExample("打印第 1 列", "awk '{print \$1}' file.txt"),
            CmdExample("指定分隔符，提取用户名", "awk -F: '{print \$1}' /etc/passwd"),
            CmdExample("对某一列求和", "awk '{sum+=\$1} END {print sum}' nums.txt"),
            CmdExample("筛选第 3 列大于 100 的行", "awk '\$3 > 100' data.txt"),
            CmdExample("按整行去重并保持顺序", "awk '!seen[\$0]++' file.txt"),
            CmdExample("跳过首行并打印第 2 列", "awk 'NR>1 {print \$2}' data.csv"),
            CmdExample("查看进程 PID 和命令", "ps aux | awk '{print \$2, \$11}'"),
            CmdExample("统计各 IP 访问次数", "awk '{c[\$1]++} END {for (i in c) print i, c[i]}' access.log"),
            CmdExample("统计每个 IP 的访问次数", "awk '{c[\$1]++} END {for (i in c) print c[i], i}' access.log | sort -nr")
        ), "📊", "awk 非常强大，可写复杂脚本；\\$1 表示第一个字段"
    ),
    LinuxCommand(
        "sort", CmdCategory.TEXT_PROC, "对文件行进行排序",
        "sort [选项] 文件",
        listOf(
            CmdExample("按字母序排序", "sort file.txt"),
            CmdExample("按数字大小排序", "sort -n numbers.txt"),
            CmdExample("逆序排序", "sort -r file.txt"),
            CmdExample("去重并排序", "sort -u file.txt"),
            CmdExample("按第二列排序", "sort -k2 file.txt"),
            CmdExample("按人类可读大小排序", "sort -h sizes.txt"),
            CmdExample("统计出现次数并按频率排序（Top 10）", "sort file.txt | uniq -c | sort -nr | head -10"),
            CmdExample("按第 2 列数值排序（逗号分隔）", "sort -t, -k2 -n data.csv"),
            CmdExample("统计出现次数并倒序（Top 10）", "sort file.txt | uniq -c | sort -nr | head -n 10")
        ), "🔤"
    ),
    LinuxCommand(
        "uniq", CmdCategory.TEXT_PROC, "去除或统计重复行（需先排序）",
        "uniq [选项] 文件",
        listOf(
            CmdExample("去除连续重复行", "uniq file.txt"),
            CmdExample("统计重复次数", "uniq -c file.txt"),
            CmdExample("只显示重复行", "uniq -d file.txt"),
            CmdExample("只显示不重复的行", "uniq -u file.txt"),
            CmdExample("忽略前 N 个字段比较", "uniq -f 1 file.txt"),
            CmdExample("找出重复的行", "sort file.txt | uniq -d"),
            CmdExample("找出只出现一次的行", "sort file.txt | uniq -u"),
            CmdExample("统计每行出现次数", "sort file.txt | uniq -c")
        ), "🔁", "uniq 只去除相邻重复行，常和 sort 配合: sort file | uniq"
    ),
    LinuxCommand(
        "cut", CmdCategory.TEXT_PROC, "从每行中截取指定字段",
        "cut [选项] 文件",
        listOf(
            CmdExample("截取第 1-5 个字符", "cut -c1-5 file.txt"),
            CmdExample("按分隔符取字段", "cut -d':' -f1 /etc/passwd"),
            CmdExample("取多个字段", "cut -d',' -f1,3 data.csv"),
            CmdExample("取第 3 个字段及之后所有", "cut -d',' -f3- data.csv"),
            CmdExample("提取用户名列表", "cut -d: -f1 /etc/passwd"),
            CmdExample("提取 CSV 的第 1、3 列", "cut -d, -f1,3 data.csv"),
            CmdExample("截取每行前 10 个字符", "cut -c1-10 file.txt")
        ), "✂️"
    ),
    LinuxCommand(
        "tr", CmdCategory.TEXT_PROC, "字符替换或删除",
        "tr [选项] 字符集1 [字符集2]",
        listOf(
            CmdExample("小写转大写", "tr 'a-z' 'A-Z' < file.txt"),
            CmdExample("删除指定字符", "tr -d 'aeiou' < file.txt"),
            CmdExample("压缩连续重复字符", "tr -s ' ' < file.txt"),
            CmdExample("补集替换（除指定字符外）", "tr -c 'a-zA-Z' ' ' < file.txt"),
            CmdExample("转为大写", "echo \"hello\" | tr 'a-z' 'A-Z'"),
            CmdExample("去掉 Windows 的回车符", "tr -d '\\r' < win.txt > unix.txt")
        ), "🔤"
    ),
    LinuxCommand(
        "wc", CmdCategory.TEXT_PROC, "统计文件行数、单词数、字节数",
        "wc [选项] 文件",
        listOf(
            CmdExample("统计行数", "wc -l file.txt"),
            CmdExample("统计单词数", "wc -w file.txt"),
            CmdExample("统计字符数", "wc -m file.txt"),
            CmdExample("显示所有统计", "wc file.txt"),
            CmdExample("统计多个文件", "wc -l *.txt"),
            CmdExample("统计目录下所有 py 文件总行数", "find . -name \"*.py\" | xargs wc -l | tail -1"),
            CmdExample("统计文件个数", "find . -type f | wc -l")
        ), "📊"
    ),
    LinuxCommand(
        "diff", CmdCategory.TEXT_PROC, "逐行比较两个文件的差异",
        "diff [选项] 文件1 文件2",
        listOf(
            CmdExample("比较两个文件", "diff file1.txt file2.txt"),
            CmdExample("并排显示差异", "diff -y file1.txt file2.txt"),
            CmdExample("忽略大小写差异", "diff -i file1.txt file2.txt"),
            CmdExample("使用统一格式显示", "diff -u file1.txt file2.txt"),
            CmdExample("递归比较目录", "diff -r dir1 dir2"),
            CmdExample("生成统一格式差异（可用于打补丁）", "diff -u old.txt new.txt"),
            CmdExample("只列出两个目录中不同的文件", "diff -rq dir1 dir2"),
            CmdExample("比较排序后的两个文件", "diff <(sort a.txt) <(sort b.txt)"),
            CmdExample("生成统一格式差异（可用于 patch）", "diff -u old.txt new.txt > fix.patch")
        ), "🔁", "统一格式 diff -u 常用于生成补丁文件"
    ),

    // ==================== 🔧 系统信息 ====================
    LinuxCommand(
        "uname", CmdCategory.SYS_INFO, "显示系统信息",
        "uname [选项]",
        listOf(
            CmdExample("显示内核名称", "uname -s"),
            CmdExample("显示主机名", "uname -n"),
            CmdExample("显示内核版本", "uname -r"),
            CmdExample("显示所有信息", "uname -a"),
            CmdExample("显示系统架构", "uname -m")
        ), "💻", "uname -a 一条命令查看完整系统信息"
    ),
    LinuxCommand(
        "df", CmdCategory.SYS_INFO, "显示磁盘分区使用情况",
        "df [选项] [挂载点]",
        listOf(
            CmdExample("显示所有文件系统", "df"),
            CmdExample("以人类可读格式显示", "df -h"),
            CmdExample("显示文件系统类型", "df -T"),
            CmdExample("显示 inode 使用量", "df -i"),
            CmdExample("显示特定目录所在分区", "df -h /home")
        ), "💾"
    ),
    LinuxCommand(
        "du", CmdCategory.SYS_INFO, "估算文件和目录的磁盘使用量",
        "du [选项] [路径]",
        listOf(
            CmdExample("显示目录大小", "du -sh /home/user"),
            CmdExample("显示子目录大小", "du -h /var/log"),
            CmdExample("按大小排序", "du -sh * | sort -h"),
            CmdExample("显示总大小", "du -sh ."),
            CmdExample("显示深度为 1 的目录", "du -h --max-depth=1"),
            CmdExample("查看当前目录下各项大小并排序", "du -sh * | sort -rh | head -10"),
            CmdExample("找出最大的 20 个文件/目录", "du -ah . | sort -rh | head -20"),
            CmdExample("当前目录下最占空间的 10 项", "du -sh * | sort -rh | head -n 10"),
            CmdExample("找出最大的 20 个文件和目录", "du -ah . | sort -rh | head -n 20")
        ), "📀"
    ),
    LinuxCommand(
        "free", CmdCategory.SYS_INFO, "显示内存使用情况",
        "free [选项]",
        listOf(
            CmdExample("显示内存使用", "free"),
            CmdExample("以人类可读格式显示", "free -h"),
            CmdExample("持续监控内存（每 2 秒）", "free -s 2"),
            CmdExample("以 MB 为单位", "free -m"),
            CmdExample("显示高低内存统计", "free -l"),
            CmdExample("每 2 秒刷新一次", "free -h -s 2"),
            CmdExample("只显示已用/总内存", "free -h | awk '/Mem/{print \$3 \"/\" \$2}'")
        ), "🧠"
    ),
    LinuxCommand(
        "uptime", CmdCategory.SYS_INFO, "显示系统运行时间与负载",
        "uptime",
        listOf(
            CmdExample("查看系统运行时间", "uptime"),
            CmdExample("查看更友好的格式", "uptime -p  # 某些版本支持"),
            CmdExample("显示开机时间", "uptime -s"),
            CmdExample("只看运行时长", "uptime -p")
        ), "⏰"
    ),
    LinuxCommand(
        "dmesg", CmdCategory.SYS_INFO, "显示内核环缓冲区消息（硬件/驱动日志）",
        "dmesg [选项]",
        listOf(
            CmdExample("查看内核日志", "dmesg"),
            CmdExample("查看最后 10 条消息", "dmesg | tail -10"),
            CmdExample("实时查看", "dmesg -w"),
            CmdExample("以人类可读时间戳显示", "dmesg -T"),
            CmdExample("清除内核日志", "dmesg -c"),
            CmdExample("带可读时间戳", "dmesg -T | tail"),
            CmdExample("只看错误和警告", "dmesg -T -l err,warn"),
            CmdExample("带可读时间戳看最近日志", "dmesg -T | tail -n 30")
        ), "📟", "dmesg -T 可读性最好，显示标准时间"
    ),
    LinuxCommand(
        "lscpu", CmdCategory.SYS_INFO, "显示 CPU 架构信息",
        "lscpu",
        listOf(
            CmdExample("查看所有 CPU 信息", "lscpu"),
            CmdExample("以 JSON 格式输出", "lscpu -J"),
            CmdExample("显示可解析格式", "lscpu -p"),
            CmdExample("只看型号和核数", "lscpu | grep -E \"Model name|^CPU\\(s\\)\"")
        ), "🔲"
    ),
    LinuxCommand(
        "lsblk", CmdCategory.SYS_INFO, "列出所有块设备信息",
        "lsblk [选项]",
        listOf(
            CmdExample("列出所有块设备", "lsblk"),
            CmdExample("显示完整设备路径", "lsblk -o NAME,FSTYPE,SIZE,MOUNTPOINT"),
            CmdExample("以树形显示", "lsblk -t"),
            CmdExample("显示文件系统信息", "lsblk -f"),
            CmdExample("自定义显示列", "lsblk -o NAME,SIZE,TYPE,MOUNTPOINT")
        ), "💽"
    ),
    LinuxCommand(
        "date", CmdCategory.SYS_INFO, "显示或设置系统日期时间",
        "date [选项] [+格式]",
        listOf(
            CmdExample("显示当前时间", "date"),
            CmdExample("自定义格式显示", "date '+%Y-%m-%d %H:%M:%S'"),
            CmdExample("显示 UTC 时间", "date -u"),
            CmdExample("显示时间戳", "date +%s"),
            CmdExample("设置系统时间（需 root）", "date -s '2025-01-01 12:00:00'"),
            CmdExample("格式化输出时间", "date +\"%Y-%m-%d %H:%M:%S\""),
            CmdExample("时间戳转日期", "date -d @1700000000"),
            CmdExample("计算 7 天后的日期", "date -d \"+7 days\" +%F"),
            CmdExample("昨天的日期", "date -d \"yesterday\" +%F")
        ), "📅"
    ),

    // ==================== ⚙️ 进程管理 ====================
    LinuxCommand(
        "ps", CmdCategory.PROC_MGMT, "列出当前运行的进程",
        "ps [选项]",
        listOf(
            CmdExample("查看当前用户进程", "ps"),
            CmdExample("查看所有进程", "ps -aux"),
            CmdExample("查看进程树", "ps -ejH"),
            CmdExample("自定义显示字段", "ps -eo pid,ppid,cmd,%mem,%cpu"),
            CmdExample("按 CPU 使用率排序", "ps -aux --sort=-%cpu"),
            CmdExample("查看特定进程", "ps -aux | grep nginx"),
            CmdExample("按内存占用排序 Top 10", "ps aux --sort=-%mem | head -n 11"),
            CmdExample("按 CPU 占用排序 Top 5", "ps aux --sort=-%cpu | head -n 6"),
            CmdExample("查找某个进程", "ps -ef | grep nginx"),
            CmdExample("自定义显示列", "ps -eo pid,ppid,%cpu,%mem,cmd --sort=-%mem | head"),
            CmdExample("查看进程运行时长", "ps -p 1234 -o etime"),
            CmdExample("按内存占用排序（前 10）", "ps aux --sort=-%mem | head -n 10"),
            CmdExample("查看进程已运行时间", "ps -p 1234 -o pid,etime,cmd")
        ), "🔍"
    ),
    LinuxCommand(
        "top", CmdCategory.PROC_MGMT, "实时显示进程活动（类似任务管理器）",
        "top [选项]",
        listOf(
            CmdExample("启动 top", "top"),
            CmdExample("只显示特定用户的进程", "top -u username"),
            CmdExample("设置刷新间隔（秒）", "top -d 5"),
            CmdExample("以批处理模式运行（输出一次）", "top -b -n 1"),
            CmdExample("按内存排序", "top -o %MEM"),
            CmdExample("批处理模式输出一次（可用于脚本）", "top -b -n 1 | head -20"),
            CmdExample("监控指定进程", "top -p 1234"),
            CmdExample("批处理模式输出一次", "top -b -n 1 | head -n 20")
        ), "📊", "在 top 中: q 退出, k 杀死进程, r 调整优先级, M 按内存排序, P 按 CPU 排序"
    ),
    LinuxCommand(
        "kill", CmdCategory.PROC_MGMT, "向进程发送信号（默认终止进程）",
        "kill [选项] PID",
        listOf(
            CmdExample("终止进程", "kill 1234"),
            CmdExample("强制杀死进程", "kill -9 1234"),
            CmdExample("优雅停止进程", "kill -15 1234"),
            CmdExample("重新加载配置", "kill -HUP 1234"),
            CmdExample("暂停进程", "kill -STOP 1234"),
            CmdExample("继续暂停的进程", "kill -CONT 1234"),
            CmdExample("强制结束某名称的所有进程", "kill -9 \$(pgrep -f myapp)"),
            CmdExample("结束占用 8080 端口的进程", "kill \$(lsof -t -i:8080)"),
            CmdExample("检测进程是否存活", "kill -0 1234 && echo alive")
        ), "💀", "kill -9 (SIGKILL) 不可被捕获，进程立即结束但可能丢失数据"
    ),
    LinuxCommand(
        "pkill", CmdCategory.PROC_MGMT, "按名称终止进程",
        "pkill [选项] 进程名",
        listOf(
            CmdExample("终止所有同名进程", "pkill firefox"),
            CmdExample("精确匹配进程名", "pkill -x nginx"),
            CmdExample("按用户名终止进程", "pkill -u username"),
            CmdExample("发送特定信号", "pkill -9 -f \"python script\""),
            CmdExample("按完整命令行匹配结束", "pkill -f \"python app.py\"")
        ), "💀", "pkill 按进程名匹配，注意不要误杀"
    ),
    LinuxCommand(
        "nohup", CmdCategory.PROC_MGMT, "让进程在退出终端后继续运行",
        "nohup 命令 [参数] &",
        listOf(
            CmdExample("后台运行且忽略挂断", "nohup python server.py &"),
            CmdExample("重定向输出到指定文件", "nohup ./longtask > output.log 2>&1 &"),
            CmdExample("后台运行脚本", "nohup ./start.sh &"),
            CmdExample("后台运行并记录日志", "nohup python app.py > out.log 2>&1 &"),
            CmdExample("后台运行并显示 PID", "nohup ./run.sh > /dev/null 2>&1 & echo \$!"),
            CmdExample("后台运行并打印 PID", "nohup ./run.sh >/dev/null 2>&1 & echo \$!")
        ), "🔄", "nohup 标准输出默认重定向到 nohup.out"
    ),
    LinuxCommand(
        "nice", CmdCategory.PROC_MGMT, "以指定优先级运行程序",
        "nice -n 调整值 命令",
        listOf(
            CmdExample("以较低优先级运行", "nice -n 19 ./bigtask"),
            CmdExample("以较高优先级运行", "nice -n -10 ./urgent"),
            CmdExample("查看进程优先级", "ps -eo pid,ni,cmd"),
            CmdExample("以低优先级运行", "nice -n 10 ./heavy_job.sh")
        ), "⚖️", "nice 范围 -20（最高）到 19（最低），普通用户只能调低"
    ),

    // ==================== 🌐 网络相关 ====================
    LinuxCommand(
        "ping", CmdCategory.NETWORK, "测试网络连通性和延迟",
        "ping [选项] 目标",
        listOf(
            CmdExample("持续 ping（Ctrl+C 停止）", "ping google.com"),
            CmdExample("发送 4 个包后停止", "ping -c 4 google.com"),
            CmdExample("指定间隔时间", "ping -i 2 google.com"),
            CmdExample("洪水 ping（需 root）", "ping -f localhost"),
            CmdExample("只发 4 个包", "ping -c 4 baidu.com"),
            CmdExample("快速连续发包测试稳定性", "ping -i 0.2 -c 20 192.168.1.1"),
            CmdExample("判断主机是否在线", "ping -c 1 -W 2 host && echo up || echo down"),
            CmdExample("快速间隔 0.2 秒探测", "ping -i 0.2 -c 10 baidu.com")
        ), "📡", "按 Ctrl+C 停止 ping"
    ),
    LinuxCommand(
        "curl", CmdCategory.NETWORK, "数据传输工具，支持 HTTP/FTP 等协议",
        "curl [选项] URL",
        listOf(
            CmdExample("获取网页内容", "curl https://api.example.com"),
            CmdExample("下载文件", "curl -O https://example.com/file.zip"),
            CmdExample("发送 POST 请求", "curl -X POST -d 'key=value' https://api.example.com"),
            CmdExample("设置请求头", "curl -H 'Authorization: Bearer token' https://api.example.com"),
            CmdExample("跟随重定向", "curl -L https://bit.ly/short"),
            CmdExample("显示响应头", "curl -I https://example.com"),
            CmdExample("只获取 HTTP 状态码", "curl -s -o /dev/null -w \"%{http_code}\\n\" https://example.com"),
            CmdExample("发送 JSON 的 POST 请求", "curl -X POST -H \"Content-Type: application/json\" -d '{\"name\":\"test\"}' https://api.example.com"),
            CmdExample("跟随重定向并保存文件", "curl -L -o file.zip https://example.com/file.zip"),
            CmdExample("断点续传下载", "curl -C - -O https://example.com/big.iso"),
            CmdExample("带账号密码请求", "curl -u user:pass https://example.com/api"),
            CmdExample("请求并用 jq 格式化 JSON", "curl -s https://api.example.com/data | jq ."),
            CmdExample("查看本机公网 IP", "curl ifconfig.me")
        ), "🌐", "curl 功能极强，几乎可发任意 HTTP 请求"
    ),
    LinuxCommand(
        "wget", CmdCategory.NETWORK, "非交互式网络下载器",
        "wget [选项] URL",
        listOf(
            CmdExample("下载文件", "wget https://example.com/file.zip"),
            CmdExample("断点续传", "wget -c https://example.com/bigfile.iso"),
            CmdExample("下载到指定目录", "wget -P /downloads https://example.com/file.zip"),
            CmdExample("递归下载网站", "wget -r -l 1 https://example.com"),
            CmdExample("限制下载速度", "wget --limit-rate=200k https://example.com/file.zip"),
            CmdExample("指定保存文件名", "wget -O name.zip https://example.com/file.zip"),
            CmdExample("从列表文件批量下载", "wget -i urls.txt"),
            CmdExample("镜像整站（不上溯父目录）", "wget -r -np -k https://example.com/docs/"),
            CmdExample("限速下载", "wget --limit-rate=1m https://example.com/file.zip"),
            CmdExample("下载并直接解压", "wget -qO- https://example.com/a.tar.gz | tar xz")
        ), "⬇️", "wget 支持断点续传（-c），下载大文件很有用"
    ),
    LinuxCommand(
        "ip", CmdCategory.NETWORK, "显示/操作网络设备、路由、隧道（替代 ifconfig）",
        "ip [子命令] [选项]",
        listOf(
            CmdExample("显示网络接口信息", "ip addr"),
            CmdExample("显示路由表", "ip route"),
            CmdExample("启用/禁用网络接口", "ip link set eth0 up"),
            CmdExample("显示邻居表（ARP）", "ip neigh"),
            CmdExample("添加 IP 地址", "ip addr add 192.168.1.100/24 dev eth0"),
            CmdExample("简洁显示所有网卡 IP", "ip -br a"),
            CmdExample("查看访问某地址走哪条路由", "ip route get 8.8.8.8"),
            CmdExample("启用/关闭网卡", "sudo ip link set eth0 up")
        ), "🌐", "ip 命令是现代 Linux 网络管理工具，推荐替代 ifconfig"
    ),
    LinuxCommand(
        "ss", CmdCategory.NETWORK, "查看 socket 统计信息（替代 netstat）",
        "ss [选项]",
        listOf(
            CmdExample("列出所有连接", "ss -tuln"),
            CmdExample("显示所有 TCP 连接", "ss -t"),
            CmdExample("显示所有 UDP 连接", "ss -u"),
            CmdExample("显示进程信息", "ss -tulnp"),
            CmdExample("按状态过滤", "ss state listening"),
            CmdExample("查看监听端口及对应进程", "sudo ss -tlnp"),
            CmdExample("统计各连接状态数量", "ss -ant | awk 'NR>1{print \$1}' | sort | uniq -c"),
            CmdExample("查看连接汇总", "ss -s"),
            CmdExample("查看 80 端口的连接", "ss -tn sport = :80"),
            CmdExample("查看监听端口及进程", "ss -tlnp"),
            CmdExample("查看已建立的连接", "ss -tn state established")
        ), "🔌", "ss 比 netstat 更快更现代"
    ),
    LinuxCommand(
        "ssh", CmdCategory.NETWORK, "远程连接服务器（Secure Shell）",
        "ssh [选项] user@host",
        listOf(
            CmdExample("连接到远程服务器", "ssh user@192.168.1.100"),
            CmdExample("指定端口连接", "ssh -p 2222 user@host"),
            CmdExample("密钥认证", "ssh -i ~/.ssh/id_rsa user@host"),
            CmdExample("执行远程命令", "ssh user@host 'ls -la'"),
            CmdExample("端口转发（本地->远程）", "ssh -L 8080:localhost:80 user@host"),
            CmdExample("指定端口和密钥登录", "ssh -p 2222 -i ~/.ssh/id_rsa user@host"),
            CmdExample("远程执行命令后退出", "ssh user@host \"df -h\""),
            CmdExample("动态 SOCKS 代理", "ssh -N -D 1080 user@host"),
            CmdExample("通过跳板机连接", "ssh -J jump@jumphost user@target")
        ), "🔐"
    ),
    LinuxCommand(
        "scp", CmdCategory.NETWORK, "通过 SSH 安全复制文件",
        "scp [选项] 源 目标",
        listOf(
            CmdExample("上传文件到服务器", "scp file.txt user@host:/remote/path/"),
            CmdExample("从服务器下载文件", "scp user@host:/remote/file.txt ./"),
            CmdExample("递归复制目录", "scp -r dir/ user@host:/remote/"),
            CmdExample("指定端口", "scp -P 2222 file.txt user@host:/path/"),
            CmdExample("上传整个目录", "scp -r ./dir user@host:/remote/path/"),
            CmdExample("指定端口上传", "scp -P 2222 file.txt user@host:/tmp/"),
            CmdExample("从远程下载到当前目录", "scp user@host:/path/file.txt ."),
            CmdExample("压缩传输", "scp -C big.log user@host:/tmp/"),
            CmdExample("复制整个目录到远程", "scp -r dir user@host:/path/")
        ), "🔐"
    ),

    // ==================== 🔐 权限用户 ====================
    LinuxCommand(
        "chmod", CmdCategory.PERM_USER, "修改文件或目录的权限",
        "chmod [选项] 权限 文件",
        listOf(
            CmdExample("数字法：755（所有者全部，其他读+执行）", "chmod 755 script.sh"),
            CmdExample("符号法：添加执行权限", "chmod +x script.sh"),
            CmdExample("为所有者添加写权限", "chmod u+w file.txt"),
            CmdExample("递归修改目录权限", "chmod -R 755 dir/"),
            CmdExample("移除其他人的读权限", "chmod o-r file.txt"),
            CmdExample("设置所有权限相同", "chmod =rw file.txt"),
            CmdExample("设置私钥权限（SSH 要求）", "chmod 600 ~/.ssh/id_rsa"),
            CmdExample("文件 644、目录 755 批量修正", "find . -type f -exec chmod 644 {} + && find . -type d -exec chmod 755 {} +"),
            CmdExample("所有者加执行、组去掉写", "chmod u+x,g-w file.sh"),
            CmdExample("目录 755、文件 644 批量设置", "find . -type d -exec chmod 755 {} + && find . -type f -exec chmod 644 {} +"),
            CmdExample("给所有者加执行、去掉组写权限", "chmod u+x,g-w file")
        ), "🔐", "权限位: r=4, w=2, x=1; chmod 755 = rwxr-xr-x"
    ),
    LinuxCommand(
        "chown", CmdCategory.PERM_USER, "修改文件或目录的所有者",
        "chown [选项] 所有者[:组] 文件",
        listOf(
            CmdExample("更改文件所有者", "chown alice file.txt"),
            CmdExample("同时更改所有者和组", "chown alice:staff file.txt"),
            CmdExample("仅更改组", "chown :staff file.txt"),
            CmdExample("递归更改目录所有权", "chown -R alice:staff dir/"),
            CmdExample("参考另一文件的所有权", "chown --reference=ref.txt target.txt"),
            CmdExample("递归修改所有者和组", "chown -R user:group /data"),
            CmdExample("把文件交给当前用户", "sudo chown \$USER: file.txt"),
            CmdExample("只改组", "chown :www-data /var/www/html")
        ), "👤", "chown 需要 root 权限"
    ),
    LinuxCommand(
        "chgrp", CmdCategory.PERM_USER, "修改文件或目录的所属组",
        "chgrp [选项] 组名 文件",
        listOf(
            CmdExample("更改文件所属组", "chgrp staff file.txt"),
            CmdExample("递归更改", "chgrp -R staff dir/"),
            CmdExample("参考另一文件的组", "chgrp --reference=ref.txt target.txt")
        ), "👥"
    ),
    LinuxCommand(
        "sudo", CmdCategory.PERM_USER, "以超级用户或其他用户身份执行命令",
        "sudo [选项] 命令",
        listOf(
            CmdExample("以 root 执行命令", "sudo apt update"),
            CmdExample("以其他用户执行", "sudo -u username command"),
            CmdExample("切换到 root shell", "sudo -i"),
            CmdExample("以 root 执行上一条命令", "sudo !!"),
            CmdExample("以其他用户执行命令", "sudo -u www-data whoami"),
            CmdExample("查看自己有哪些 sudo 权限", "sudo -l"),
            CmdExample("用 root 权限写入文件", "echo \"text\" | sudo tee -a /etc/hosts")
        ), "🛡️", "sudo !! 在忘记加 sudo 时很好用"
    ),
    LinuxCommand(
        "su", CmdCategory.PERM_USER, "切换用户身份",
        "su [选项] [用户名]",
        listOf(
            CmdExample("切换到 root（需要密码）", "su"),
            CmdExample("切换并加载目标用户环境", "su - username"),
            CmdExample("以其他用户执行命令", "su -c 'command' username"),
            CmdExample("以其他用户执行单条命令", "su -c \"whoami\" username")
        ), "👤", "su - 会加载目标用户的环境变量"
    ),
    LinuxCommand(
        "passwd", CmdCategory.PERM_USER, "修改用户密码",
        "passwd [选项] [用户名]",
        listOf(
            CmdExample("修改当前用户密码", "passwd"),
            CmdExample("修改其他用户密码（root）", "passwd username"),
            CmdExample("锁定用户账号", "passwd -l username"),
            CmdExample("解锁用户账号", "passwd -u username"),
            CmdExample("查看密码状态", "passwd -S username"),
            CmdExample("查看用户密码状态", "sudo passwd -S username"),
            CmdExample("锁定用户账户", "sudo passwd -l username"),
            CmdExample("强制用户下次登录改密码", "sudo passwd -e username")
        ), "🔑"
    ),

    // ==================== 🗜️ 压缩归档 ====================
    LinuxCommand(
        "tar", CmdCategory.COMPRESS, "创建/解压 tar 归档文件（常与 gzip/bzip2 配合）",
        "tar [选项] [归档文件] [源文件]",
        listOf(
            CmdExample("创建 tar 归档", "tar -cvf archive.tar dir/"),
            CmdExample("创建并 gzip 压缩", "tar -czvf archive.tar.gz dir/"),
            CmdExample("解压 tar.gz", "tar -xzvf archive.tar.gz"),
            CmdExample("解压 tar.bz2", "tar -xjvf archive.tar.bz2"),
            CmdExample("列出归档内容", "tar -tvf archive.tar"),
            CmdExample("解压到指定目录", "tar -xzvf archive.tar.gz -C /target/dir"),
            CmdExample("打包并 gzip 压缩", "tar -czvf backup.tar.gz dir/"),
            CmdExample("查看压缩包内容（不解压）", "tar -tzvf backup.tar.gz"),
            CmdExample("打包时排除某些文件", "tar -czf backup.tar.gz --exclude='*.log' --exclude='node_modules' dir/"),
            CmdExample("只解压其中一个文件", "tar -xzf backup.tar.gz path/to/file"),
            CmdExample("打包并通过 SSH 传输到远程", "tar czf - dir/ | ssh user@host \"tar xzf - -C /data\""),
            CmdExample("只解压包内某个文件", "tar -xzf backup.tar.gz path/in/archive.txt"),
            CmdExample("边打包边传到远程", "tar czf - dir/ | ssh user@host \"tar xzf - -C /path\"")
        ), "🗜️", "常用记忆: c=创建, x=解压, z=gzip, j=bzip2, v=显示, f=文件名"
    ),
    LinuxCommand(
        "gzip", CmdCategory.COMPRESS, "压缩/解压文件（.gz 格式）",
        "gzip [选项] 文件",
        listOf(
            CmdExample("压缩文件（原文件被替换）", "gzip file.txt"),
            CmdExample("解压文件", "gunzip file.txt.gz"),
            CmdExample("保留原文件压缩", "gzip -c file.txt > file.txt.gz"),
            CmdExample("指定压缩级别（1-9，9 最高）", "gzip -9 file.txt"),
            CmdExample("递归压缩目录", "gzip -r dir/"),
            CmdExample("压缩但保留原文件", "gzip -k file.txt"),
            CmdExample("解压", "gzip -d file.txt.gz"),
            CmdExample("最大压缩比", "gzip -9 file.log"),
            CmdExample("不解压直接查看", "zcat file.log.gz | head")
        ), "🗜️", "gzip 只压缩单个文件，压缩目录用 tar"
    ),
    LinuxCommand(
        "zip", CmdCategory.COMPRESS, "创建/更新 zip 压缩文件",
        "zip [选项] zip文件 [源文件]",
        listOf(
            CmdExample("创建 zip 归档", "zip archive.zip file1.txt file2.txt"),
            CmdExample("递归压缩目录", "zip -r archive.zip dir/"),
            CmdExample("加密压缩", "zip -e secure.zip secret.txt"),
            CmdExample("排除指定文件", "zip -r archive.zip dir/ -x \"*.log\""),
            CmdExample("更新已有 zip", "zip -u archive.zip newfile.txt"),
            CmdExample("带密码压缩", "zip -er secret.zip dir/"),
            CmdExample("压缩时排除 .git", "zip -r a.zip . -x \"*.git*\""),
            CmdExample("递归压缩并排除 .git", "zip -r project.zip . -x \"*.git*\"")
        ), "🗜️", "zip 是跨平台最通用的压缩格式"
    ),
    LinuxCommand(
        "unzip", CmdCategory.COMPRESS, "解压 zip 格式文件",
        "unzip [选项] zip文件",
        listOf(
            CmdExample("解压到当前目录", "unzip archive.zip"),
            CmdExample("解压到指定目录", "unzip archive.zip -d /target/dir"),
            CmdExample("列出 zip 内容", "unzip -l archive.zip"),
            CmdExample("不解压测试完整性", "unzip -t archive.zip"),
            CmdExample("覆盖前不提示", "unzip -o archive.zip"),
            CmdExample("只查看内容不解压", "unzip -l a.zip"),
            CmdExample("解压时覆盖不提示", "unzip -o a.zip")
        ), "🗜️"
    ),

    // ==================== 📦 包管理 ====================
    LinuxCommand(
        "apt", CmdCategory.PACKAGE, "Debian/Ubuntu 的包管理工具（新版）",
        "apt [子命令] [包名]",
        listOf(
            CmdExample("更新包索引", "sudo apt update"),
            CmdExample("升级所有可升级的包", "sudo apt upgrade"),
            CmdExample("安装软件包", "sudo apt install nginx"),
            CmdExample("移除软件包", "sudo apt remove nginx"),
            CmdExample("彻底移除（含配置文件）", "sudo apt purge nginx"),
            CmdExample("搜索包", "apt search keywork"),
            CmdExample("显示包信息", "apt show nginx"),
            CmdExample("清理缓存", "sudo apt autoremove && sudo apt autoclean"),
            CmdExample("更新并升级所有软件", "sudo apt update && sudo apt upgrade -y"),
            CmdExample("安装软件（自动确认）", "sudo apt install -y git curl"),
            CmdExample("搜索软件包", "apt search nginx"),
            CmdExample("查看已安装的某个软件", "apt list --installed | grep nginx"),
            CmdExample("彻底卸载并清理配置", "sudo apt remove --purge nginx && sudo apt autoremove -y"),
            CmdExample("免确认安装", "sudo apt install -y curl git"),
            CmdExample("彻底卸载并清理", "sudo apt remove --purge -y nginx && sudo apt autoremove -y")
        ), "📦", "apt 比 apt-get 更友好，推荐日常使用"
    ),
    LinuxCommand(
        "dpkg", CmdCategory.PACKAGE, "Debian 包管理器（底层操作 .deb 包）",
        "dpkg [选项] [包名.deb]",
        listOf(
            CmdExample("安装 .deb 包", "sudo dpkg -i package.deb"),
            CmdExample("删除包", "sudo dpkg -r package"),
            CmdExample("列出已安装的包", "dpkg -l"),
            CmdExample("查看包安装的文件", "dpkg -L package"),
            CmdExample("查找文件所属包", "dpkg -S /bin/ls"),
            CmdExample("重新配置包", "sudo dpkg-reconfigure package")
        ), "📦"
    ),
    LinuxCommand(
        "snap", CmdCategory.PACKAGE, "Ubuntu Snap 包管理",
        "snap [子命令] [包名]",
        listOf(
            CmdExample("安装 snap 包", "sudo snap install vlc"),
            CmdExample("列出已安装的 snap", "snap list"),
            CmdExample("更新 snap", "sudo snap refresh"),
            CmdExample("移除 snap", "sudo snap remove vlc"),
            CmdExample("搜索 snap 包", "snap find keywork")
        ), "📦"
    ),

    // ==================== 🔨 开发工具 ====================
    LinuxCommand(
        "git", CmdCategory.DEV_TOOLS, "分布式版本控制系统",
        "git [子命令] [选项]",
        listOf(
            CmdExample("克隆仓库", "git clone https://github.com/user/repo.git"),
            CmdExample("查看状态", "git status"),
            CmdExample("添加文件到暂存区", "git add file.txt"),
            CmdExample("提交更改", "git commit -m \"提交信息\""),
            CmdExample("推送到远程", "git push origin main"),
            CmdExample("拉取最新代码", "git pull"),
            CmdExample("创建新分支", "git branch new-feature"),
            CmdExample("切换分支", "git checkout new-feature"),
            CmdExample("创建并直接切换到新分支", "git checkout -b new-feature"),
            CmdExample("查看提交历史", "git log --oneline --graph"),
            CmdExample("查看某文件的改动详情", "git diff file.txt"),
            CmdExample("查看已暂存但未提交的改动", "git diff --staged"),
            CmdExample("合并指定分支到当前分支", "git merge new-feature"),
            CmdExample("变基到目标分支（整理提交历史）", "git rebase main"),
            CmdExample("临时保存未提交的改动", "git stash"),
            CmdExample("恢复最近一次暂存的改动", "git stash pop"),
            CmdExample("撤销暂存区的改动（保留工作区）", "git reset HEAD file.txt"),
            CmdExample("彻底丢弃工作区改动，回到最近提交", "git reset --hard HEAD"),
            CmdExample("查看远程仓库地址", "git remote -v"),
            CmdExample("拉取远程更新但不合并", "git fetch origin"),
            CmdExample("打一个版本标签", "git tag v1.0.0"),
            CmdExample("推送标签到远程", "git push origin v1.0.0"),
            CmdExample("查看某一行代码是谁改的", "git blame file.txt"),
            CmdExample("撤销最后一次提交但保留改动", "git reset --soft HEAD~1"),
            CmdExample("简洁图形化查看提交历史", "git log --oneline --graph --all"),
            CmdExample("修改上一次提交信息", "git commit --amend"),
            CmdExample("暂存当前修改后再恢复", "git stash && git stash pop"),
            CmdExample("创建并切换到新分支", "git checkout -b feature/x"),
            CmdExample("查看所有分支及跟踪关系", "git branch -vv"),
            CmdExample("搜索哪次提交改动了某段文字", "git log -p -S \"keyword\""),
            CmdExample("交互式整理最近 3 次提交", "git rebase -i HEAD~3"),
            CmdExample("全局设置用户名", "git config --global user.name \"Your Name\""),
            CmdExample("创建并切换分支", "git checkout -b feature/new"),
            CmdExample("搜索哪次提交改过某段代码", "git log -p -S \"functionName\"")
        ), "🔨", "git add . 添加所有变更，git commit -a -m \"msg\" 可跳过暂存；" +
            "rebase 会改写提交历史，已推送到远程的分支慎用"
    ),
    LinuxCommand(
        "gcc", CmdCategory.DEV_TOOLS, "GNU C 语言编译器",
        "gcc [选项] 源文件 [-o 输出文件]",
        listOf(
            CmdExample("编译 C 程序", "gcc main.c -o main"),
            CmdExample("编译带调试信息", "gcc -g main.c -o main"),
            CmdExample("开启所有警告", "gcc -Wall main.c -o main"),
            CmdExample("链接数学库", "gcc main.c -lm -o main"),
            CmdExample("优化编译（O2 级别）", "gcc -O2 main.c -o main"),
            CmdExample("编译为动态库", "gcc -shared -fPIC lib.c -o lib.so"),
            CmdExample("开启警告并优化编译", "gcc -Wall -O2 -o app main.c"),
            CmdExample("带调试信息编译", "gcc -g -o app main.c"),
            CmdExample("只预处理", "gcc -E main.c")
        ), "🔨"
    ),
    LinuxCommand(
        "make", CmdCategory.DEV_TOOLS, "自动化构建工具（读取 Makefile）",
        "make [目标]",
        listOf(
            CmdExample("执行默认目标", "make"),
            CmdExample("执行指定目标", "make install"),
            CmdExample("清理构建产物", "make clean"),
            CmdExample("显示但不执行命令", "make -n"),
            CmdExample("并行编译（4 线程）", "make -j4"),
            CmdExample("按 CPU 核数并行编译", "make -j\$(nproc)"),
            CmdExample("指定目录执行", "make -C src/")
        ), "🔨", "make 根据 Makefile 文件判断哪些文件需要重新编译"
    ),
    LinuxCommand(
        "gdb", CmdCategory.DEV_TOOLS, "GNU 调试器",
        "gdb [选项] [程序] [core文件]",
        listOf(
            CmdExample("启动调试器", "gdb ./program"),
            CmdExample("带 core dump 调试", "gdb ./program core.dump"),
            CmdExample("设置断点", "gdb> break main"),
            CmdExample("运行程序", "gdb> run"),
            CmdExample("单步执行", "gdb> step"),
            CmdExample("查看变量", "gdb> print variable"),
            CmdExample("查看堆栈", "gdb> backtrace"),
            CmdExample("加载 core 文件分析崩溃", "gdb ./app core"),
            CmdExample("附加到运行中的进程", "sudo gdb -p 1234"),
            CmdExample("带参数调试", "gdb --args ./app arg1 arg2"),
            CmdExample("非交互输出调用栈", "gdb -batch -ex bt ./app core")
        ), "🐛", "在 gdb 中可用 bt 查看调用栈，q 退出"
    ),
    LinuxCommand(
        "strace", CmdCategory.DEV_TOOLS, "跟踪进程的系统调用和信号",
        "strace [选项] 命令",
        listOf(
            CmdExample("跟踪程序的系统调用", "strace ls"),
            CmdExample("跟踪特定系统调用", "strace -e open,read ls"),
            CmdExample("保存跟踪结果到文件", "strace -o trace.log ./program"),
            CmdExample("统计系统调用时间", "strace -c ./program"),
            CmdExample("跟踪子进程", "strace -f ./program"),
            CmdExample("跟踪运行中的进程", "sudo strace -p 1234"),
            CmdExample("跟踪子进程并输出到文件", "strace -f -o trace.txt ./app"),
            CmdExample("统计系统调用次数", "strace -c ./app"),
            CmdExample("只看文件打开操作", "strace -e trace=open,openat ./app"),
            CmdExample("统计系统调用耗时", "strace -c ls"),
            CmdExample("只跟踪文件打开", "strace -e trace=open,openat cat file.txt")
        ), "🔍", "strace 是排查程序问题的利器"
    ),
    LinuxCommand(
        "ldd", CmdCategory.DEV_TOOLS, "显示程序或共享库的依赖关系",
        "ldd [选项] 文件",
        listOf(
            CmdExample("查看程序依赖的库", "ldd /bin/ls"),
            CmdExample("显示未使用的直接依赖", "ldd -u /bin/ls"),
            CmdExample("查看版本信息", "ldd --version")
        ), "🔗", "如果输出中出现 'not found'，说明缺少依赖库"
    ),

    // ==================== 额外常用 ====================
    LinuxCommand(
        "echo", CmdCategory.FILE_MGMT, "输出文本或变量值到标准输出",
        "echo [选项] [字符串]",
        listOf(
            CmdExample("输出文本", "echo \"Hello World\""),
            CmdExample("输出变量值", "echo \${'$'}HOME"),
            CmdExample("输出到文件", "echo \"内容\" > file.txt"),
            CmdExample("追加到文件", "echo \"新行\" >> file.txt"),
            CmdExample("启用转义字符", "echo -e \"第一行\\n第二行\""),
            CmdExample("追加内容到文件", "echo \"new line\" >> file.txt"),
            CmdExample("输出带换行的文本", "echo -e \"line1\\nline2\""),
            CmdExample("查看上一条命令的退出码", "echo \$?"),
            CmdExample("查看 PATH 中的各目录", "echo \$PATH | tr ':' '\\n'")
        ), "💬"
    ),
    LinuxCommand(
        "alias", CmdCategory.FILE_MGMT, "创建命令别名",
        "alias [别名='命令']",
        listOf(
            CmdExample("创建别名", "alias ll='ls -lah'"),
            CmdExample("查看所有别名", "alias"),
            CmdExample("临时取消别名", "unalias ll"),
            CmdExample("常用别名设置", "alias gs='git status' && alias gp='git pull'"),
            CmdExample("别名 git status", "alias gs='git status'"),
            CmdExample("查看已有 git 别名", "alias | grep git")
        ), "🔗", "别名只对当前会话有效，永久生效需写入 ~/.bashrc"
    ),
    LinuxCommand(
        "history", CmdCategory.FILE_MGMT, "显示历史命令",
        "history [选项]",
        listOf(
            CmdExample("显示命令历史", "history"),
            CmdExample("显示最近 N 条", "history 20"),
            CmdExample("清空历史", "history -c"),
            CmdExample("重复执行第 N 条", "!100"),
            CmdExample("重复上一条命令", "!!"),
            CmdExample("搜索历史中的 ssh 命令", "history | grep ssh"),
            CmdExample("查看最近 20 条", "history | tail -20"),
            CmdExample("用 sudo 重新执行上一条命令", "sudo !!")
        ), "📜", "!! 重复上一条，!$ 重复上一条命令的最后一个参数"
    ),
    LinuxCommand(
        "man", CmdCategory.SYS_INFO, "查看命令的在线手册",
        "man [章节号] 命令",
        listOf(
            CmdExample("查看命令手册", "man ls"),
            CmdExample("查看指定章节", "man 5 crontab"),
            CmdExample("搜索手册页", "man -k keywork"),
            CmdExample("显示手册路径", "man -w ls"),
            CmdExample("按关键词搜索手册", "man -k keyword"),
            CmdExample("导出手册为纯文本", "man ls | col -b > ls.txt"),
            CmdExample("按关键字搜索手册", "man -k \"copy files\"")
        ), "📖", "man 手册章节: 1=命令, 5=配置文件, 2=系统调用, 3=C 库函数"
    ),
    LinuxCommand(
        "which", CmdCategory.SYS_INFO, "显示命令的绝对路径",
        "which [选项] 命令",
        listOf(
            CmdExample("查找命令路径", "which python"),
            CmdExample("显示所有匹配路径", "which -a python"),
            CmdExample("检查命令是否存在", "which docker || echo \"not installed\"")
        ), "🔍"
    ),
    LinuxCommand(
        "crontab", CmdCategory.SYS_INFO, "配置定时任务（cron）",
        "crontab [选项]",
        listOf(
            CmdExample("编辑定时任务", "crontab -e"),
            CmdExample("查看当前定时任务", "crontab -l"),
            CmdExample("清空定时任务", "crontab -r"),
            CmdExample("每分钟执行一次", "* * * * * /script.sh"),
            CmdExample("每天凌晨 3 点执行", "0 3 * * * /backup.sh"),
            CmdExample("每周一 9 点执行", "0 9 * * 1 /weekly.sh"),
            CmdExample("查看当前任务（忽略注释）", "crontab -l | grep -v '^#'"),
            CmdExample("用命令追加一条每 5 分钟的任务", "(crontab -l; echo \"*/5 * * * * /path/job.sh\") | crontab -")
        ), "⏰", "cron 格式: 分 时 日 月 周 命令"
    ),
    LinuxCommand(
        "systemctl", CmdCategory.PROC_MGMT, "管理系统服务（systemd）",
        "systemctl [子命令] [服务名]",
        listOf(
            CmdExample("启动服务", "sudo systemctl start nginx"),
            CmdExample("停止服务", "sudo systemctl stop nginx"),
            CmdExample("重启服务", "sudo systemctl restart nginx"),
            CmdExample("查看服务状态", "systemctl status nginx"),
            CmdExample("设置开机自启", "sudo systemctl enable nginx"),
            CmdExample("禁用开机自启", "sudo systemctl disable nginx"),
            CmdExample("列出运行中的服务", "systemctl list-units --type=service --state=running"),
            CmdExample("启用并立即启动服务", "sudo systemctl enable --now nginx"),
            CmdExample("列出启动失败的服务", "systemctl list-units --failed"),
            CmdExample("修改 unit 文件后重新加载", "sudo systemctl daemon-reload"),
            CmdExample("列出开机自启的服务", "systemctl list-unit-files --state=enabled")
        ), "⚙️"
    ),
    LinuxCommand(
        "journalctl", CmdCategory.SYS_INFO, "查看 systemd 日志",
        "journalctl [选项]",
        listOf(
            CmdExample("查看所有日志", "journalctl"),
            CmdExample("查看本次启动的日志", "journalctl -b"),
            CmdExample("查看特定服务的日志", "journalctl -u nginx"),
            CmdExample("实时跟踪日志", "journalctl -f"),
            CmdExample("查看最后 20 行", "journalctl -n 20"),
            CmdExample("按时间过滤", "journalctl --since \"1 hour ago\""),
            CmdExample("实时查看某服务日志", "journalctl -u nginx -f"),
            CmdExample("查看最近一小时的日志", "journalctl -u nginx --since \"1 hour ago\""),
            CmdExample("只看本次启动的错误日志", "journalctl -p err -b"),
            CmdExample("清理 7 天前的日志", "sudo journalctl --vacuum-time=7d"),
            CmdExample("查看日志占用空间", "journalctl --disk-usage")
        ), "📜"
    ),

    // ==================== 📁 文件管理（新增）====================
    LinuxCommand(
        "ln", CmdCategory.FILE_MGMT, "创建硬链接或软链接",
        "ln [选项] 源 链接名",
        listOf(
            CmdExample("创建硬链接", "ln file.txt link.txt"),
            CmdExample("创建软链接（符号链接）", "ln -s /path/to/file.txt link.txt"),
            CmdExample("强制覆盖已有链接", "ln -sf target.txt link.txt"),
            CmdExample("创建软链接", "ln -s /path/to/target linkname"),
            CmdExample("强制更新已存在的软链接", "ln -sfn /new/target linkname"),
            CmdExample("查看链接指向的真实路径", "readlink -f linkname")
        ), "🔗", "软链接可以跨文件系统、指向目录；硬链接不可以"
    ),
    LinuxCommand(
        "tree", CmdCategory.FILE_MGMT, "以树状结构显示目录内容",
        "tree [选项] [路径]",
        listOf(
            CmdExample("显示当前目录树", "tree"),
            CmdExample("限制显示深度", "tree -L 2"),
            CmdExample("只显示目录", "tree -d"),
            CmdExample("显示隐藏文件", "tree -a"),
            CmdExample("排除 .git 和 node_modules", "tree -a -I '.git|node_modules'"),
            CmdExample("显示文件大小", "tree -h")
        ), "🌳", "部分发行版需要单独安装 tree 包"
    ),
    LinuxCommand(
        "stat", CmdCategory.FILE_MGMT, "显示文件或文件系统的详细状态",
        "stat [选项] 文件",
        listOf(
            CmdExample("查看文件详细信息", "stat file.txt"),
            CmdExample("只显示文件类型", "stat -c '%F' file.txt"),
            CmdExample("查看文件系统信息", "stat -f file.txt"),
            CmdExample("显示权限、所有者和文件名", "stat -c '%a %U %n' file.txt"),
            CmdExample("只显示文件大小（字节）", "stat -c %s file.txt")
        ), "📊", "包含大小、权限、inode、修改/访问/变更时间等"
    ),
    LinuxCommand(
        "file", CmdCategory.FILE_MGMT, "判断文件类型",
        "file [选项] 文件",
        listOf(
            CmdExample("查看文件类型", "file document.pdf"),
            CmdExample("查看多个文件", "file *.jpg"),
            CmdExample("查看符号链接指向的真实类型", "file -L link.txt"),
            CmdExample("显示 MIME 类型", "file -i file.txt"),
            CmdExample("批量识别文件类型", "file *")
        ), "🔎", "根据文件内容（魔数）判断类型，不依赖扩展名"
    ),
    LinuxCommand(
        "xargs", CmdCategory.FILE_MGMT, "将标准输入转换为命令参数",
        "命令 | xargs [选项] 目标命令",
        listOf(
            CmdExample("删除 find 找到的所有文件", "find . -name \"*.tmp\" | xargs rm"),
            CmdExample("每次只传一个参数", "find . -name \"*.log\" | xargs -n 1 echo"),
            CmdExample("并行执行（4 个进程）", "cat urls.txt | xargs -P 4 -I {} curl -O {}"),
            CmdExample("处理带空格的文件名", "find . -name \"*.txt\" -print0 | xargs -0 ls -l"),
            CmdExample("并发 4 个进程执行", "ls *.jpg | xargs -P 4 -I{} convert {} {}.png"),
            CmdExample("每次传一个参数", "cat list.txt | xargs -n 1 echo"),
            CmdExample("对每个结果执行命令", "ls *.txt | xargs -I{} cp {} /backup/"),
            CmdExample("4 个进程并行处理", "find . -name \"*.png\" | xargs -P 4 -n 1 optipng")
        ), "🧩", "常和 find、grep 配合，把查找结果批量传给下一个命令"
    ),
    LinuxCommand(
        "rsync", CmdCategory.FILE_MGMT, "高效的文件同步/远程复制工具",
        "rsync [选项] 源 目标",
        listOf(
            CmdExample("本地同步目录", "rsync -av dir1/ dir2/"),
            CmdExample("同步到远程服务器", "rsync -avz dir/ user@host:/remote/path/"),
            CmdExample("同步时删除目标多余文件", "rsync -av --delete dir1/ dir2/"),
            CmdExample("显示传输进度", "rsync -avz --progress dir/ user@host:/path/"),
            CmdExample("压缩同步到远程", "rsync -avz src/ user@host:/dst/"),
            CmdExample("先试运行（不实际执行）", "rsync -avn src/ dst/"),
            CmdExample("镜像同步并删除多余文件", "rsync -av --delete src/ dst/"),
            CmdExample("显示进度并支持断点续传", "rsync -avzP big.iso user@host:/data/"),
            CmdExample("排除某些目录", "rsync -av --exclude='node_modules' --exclude='.git' src/ dst/"),
            CmdExample("指定 SSH 端口", "rsync -avz -e \"ssh -p 2222\" src/ user@host:/dst/"),
            CmdExample("增量同步到远程", "rsync -avzP src/ user@host:/dst/")
        ), "🔄", "只传输差异部分，比 scp 更适合大文件/多次同步"
    ),

    // ==================== 📄 文件查看（新增）====================
    LinuxCommand(
        "tac", CmdCategory.FILE_VIEW, "反向显示文件内容（cat 的反写）",
        "tac 文件",
        listOf(
            CmdExample("倒序显示文件行", "tac file.txt"),
            CmdExample("倒序显示日志（看最新的在前）", "tac /var/log/syslog"),
            CmdExample("倒序查看文件（最新日志在前）", "tac app.log | head"),
            CmdExample("倒序查看日志最后几行", "tac app.log | head -n 20")
        ), "🔃", "名字就是 cat 反过来写"
    ),
    LinuxCommand(
        "xxd", CmdCategory.FILE_VIEW, "以十六进制格式显示文件内容",
        "xxd [选项] 文件",
        listOf(
            CmdExample("查看文件的十六进制内容", "xxd file.bin"),
            CmdExample("限制显示字节数", "xxd -l 64 file.bin"),
            CmdExample("将十六进制还原成二进制", "xxd -r hex.txt file.bin")
        ), "🔢", "常用于调试二进制文件、协议抓包分析"
    ),

    // ==================== ✏️ 文本处理（新增）====================
    LinuxCommand(
        "join", CmdCategory.TEXT_PROC, "按公共字段连接两个文件的行",
        "join [选项] 文件1 文件2",
        listOf(
            CmdExample("按第一列连接两个文件", "join file1.txt file2.txt"),
            CmdExample("指定连接的列", "join -1 2 -2 1 file1.txt file2.txt"),
            CmdExample("指定分隔符", "join -t',' file1.csv file2.csv")
        ), "🔗", "类似 SQL 的 JOIN，两个文件需要先按连接字段排序"
    ),
    LinuxCommand(
        "paste", CmdCategory.TEXT_PROC, "按列合并多个文件",
        "paste [选项] 文件1 文件2 ...",
        listOf(
            CmdExample("并排合并两个文件", "paste file1.txt file2.txt"),
            CmdExample("指定分隔符", "paste -d',' file1.txt file2.txt"),
            CmdExample("合并成一行", "paste -s file.txt"),
            CmdExample("把多行合并成一行（逗号分隔）", "paste -sd, file.txt")
        ), "📎"
    ),
    LinuxCommand(
        "comm", CmdCategory.TEXT_PROC, "逐行比较两个已排序文件的异同",
        "comm [选项] 文件1 文件2",
        listOf(
            CmdExample("显示三栏：仅1、仅2、共有", "comm file1.txt file2.txt"),
            CmdExample("只显示两文件共有的行", "comm -12 file1.txt file2.txt"),
            CmdExample("只显示 file1 独有的行", "comm -23 file1.txt file2.txt")
        ), "🔀", "使用前两个文件都要先 sort 过"
    ),
    LinuxCommand(
        "expand", CmdCategory.TEXT_PROC, "将 Tab 转换为空格",
        "expand [选项] 文件",
        listOf(
            CmdExample("把 Tab 转成空格", "expand file.txt"),
            CmdExample("指定 Tab 宽度", "expand -t 4 file.txt")
        ), "➡️", "对应的反操作是 unexpand（空格转 Tab）"
    ),
    LinuxCommand(
        "tee", CmdCategory.TEXT_PROC, "将标准输入同时输出到屏幕和文件",
        "命令 | tee [选项] 文件",
        listOf(
            CmdExample("边看输出边保存到文件", "ls -l | tee out.txt"),
            CmdExample("追加而不覆盖", "ls -l | tee -a out.txt"),
            CmdExample("同时写多个文件", "echo hi | tee file1.txt file2.txt"),
            CmdExample("同时输出到屏幕和文件", "ls -l | tee out.log"),
            CmdExample("追加到文件", "echo \"line\" | tee -a out.log")
        ), "🍵", "常用于需要 sudo 写文件又想看到输出的场景：echo x | sudo tee file"
    ),
    LinuxCommand(
        "rev", CmdCategory.TEXT_PROC, "反转每一行字符的顺序",
        "rev 文件",
        listOf(
            CmdExample("反转一行文字", "echo \"hello\" | rev"),
            CmdExample("反转文件每一行", "rev file.txt")
        ), "🔁"
    ),

    // ==================== 🔧 系统信息（新增）====================
    LinuxCommand(
        "hostname", CmdCategory.SYS_INFO, "显示或设置主机名",
        "hostname [选项] [新主机名]",
        listOf(
            CmdExample("查看主机名", "hostname"),
            CmdExample("查看 IP 地址", "hostname -I"),
            CmdExample("临时设置主机名（需 root）", "sudo hostname newname"),
            CmdExample("查看完整域名（FQDN）", "hostname -f")
        ), "🖥️", "永久修改需编辑 /etc/hostname 并重启，或用 hostnamectl"
    ),
    LinuxCommand(
        "id", CmdCategory.SYS_INFO, "显示用户和组的 ID 信息",
        "id [用户名]",
        listOf(
            CmdExample("查看当前用户 ID", "id"),
            CmdExample("查看指定用户 ID", "id alice"),
            CmdExample("只显示用户名", "id -un"),
            CmdExample("只查看用户组ID", "id -g"),
            CmdExample("显示所属组名", "id -nG")
        ), "🪪"
    ),
    LinuxCommand(
        "who", CmdCategory.SYS_INFO, "显示当前登录系统的用户",
        "who [选项]",
        listOf(
            CmdExample("查看已登录用户", "who"),
            CmdExample("显示更详细信息", "who -a"),
            CmdExample("只查看当前用户", "who am i")
        ), "👥"
    ),
    LinuxCommand(
        "lsof", CmdCategory.SYS_INFO, "列出当前系统打开的文件（含网络连接）",
        "lsof [选项]",
        listOf(
            CmdExample("查看谁在占用某个端口", "lsof -i :8080"),
            CmdExample("查看某进程打开的文件", "lsof -p 1234"),
            CmdExample("查看某文件被哪个进程占用", "lsof file.txt"),
            CmdExample("查看所有网络连接", "lsof -i"),
            CmdExample("列出所有监听中的 TCP 端口", "lsof -i tcp -sTCP:LISTEN"),
            CmdExample("查看某用户打开的文件", "lsof -u username"),
            CmdExample("查看目录下被占用的文件（排查无法卸载）", "lsof +D /mnt/data")
        ), "📂", "在 Linux 里“一切皆文件”，socket、设备都算，排查端口占用很好用"
    ),
    LinuxCommand(
        "arch", CmdCategory.SYS_INFO, "显示系统的硬件架构",
        "arch",
        listOf(
            CmdExample("查看 CPU 架构", "arch")
        ), "🏗️", "等价于 uname -m"
    ),
    LinuxCommand(
        "env", CmdCategory.SYS_INFO, "显示或修改环境变量并运行命令",
        "env [选项] [变量=值] [命令]",
        listOf(
            CmdExample("查看所有环境变量", "env"),
            CmdExample("临时设置变量运行命令", "env FOO=bar ./script.sh"),
            CmdExample("清空环境后运行命令", "env -i bash"),
            CmdExample("排序显示所有环境变量", "env | sort"),
            CmdExample("查看代理设置", "env | grep -i proxy")
        ), "🌱", "脚本第一行常见的 #!/usr/bin/env python3 就是用它来找解释器"
    ),

    // ==================== ⚙️ 进程管理（新增）====================
    LinuxCommand(
        "htop", CmdCategory.PROC_MGMT, "交互式进程监控工具（top 的增强版）",
        "htop",
        listOf(
            CmdExample("启动 htop", "htop"),
            CmdExample("只看某用户的进程", "htop -u username"),
            CmdExample("设置刷新间隔为10秒", "htop -d 10")
        ), "📈", "支持鼠标操作、彩色显示、按进程树查看，需要单独安装"
    ),
    LinuxCommand(
        "jobs", CmdCategory.PROC_MGMT, "列出当前 shell 的后台任务",
        "jobs [选项]",
        listOf(
            CmdExample("查看后台任务列表", "jobs"),
            CmdExample("显示任务的 PID", "jobs -l"),
            CmdExample("只列出正在运行的任务", "jobs -r")
        ), "📋", "配合 bg/fg 使用；Ctrl+Z 可以把前台任务挂起放入后台"
    ),
    LinuxCommand(
        "bg", CmdCategory.PROC_MGMT, "将暂停的任务放到后台继续运行",
        "bg [任务号]",
        listOf(
            CmdExample("恢复最近暂停的任务到后台", "bg"),
            CmdExample("恢复指定编号的任务", "bg %2"),
            CmdExample("把暂停的任务放到后台继续", "bg %1")
        ), "⏯️"
    ),
    LinuxCommand(
        "fg", CmdCategory.PROC_MGMT, "将后台任务切回前台",
        "fg [任务号]",
        listOf(
            CmdExample("把最近的后台任务切回前台", "fg"),
            CmdExample("切回指定编号的任务", "fg %2"),
            CmdExample("把后台任务调回前台", "fg %1")
        ), "⏯️"
    ),
    LinuxCommand(
        "pgrep", CmdCategory.PROC_MGMT, "按名称或条件查找进程 PID",
        "pgrep [选项] 模式",
        listOf(
            CmdExample("查找进程 PID", "pgrep nginx"),
            CmdExample("显示进程名和 PID", "pgrep -l nginx"),
            CmdExample("按用户过滤", "pgrep -u root"),
            CmdExample("只匹配完全一致的进程名", "pgrep -x nginx")
        ), "🔍", "配合 kill 使用：kill $(pgrep nginx)"
    ),
    LinuxCommand(
        "killall", CmdCategory.PROC_MGMT, "按进程名终止所有同名进程",
        "killall [选项] 进程名",
        listOf(
            CmdExample("终止所有同名进程", "killall firefox"),
            CmdExample("发送指定信号", "killall -9 nginx"),
            CmdExample("终止前先确认", "killall -i chrome")
        ), "💀", "和 pkill 类似，但默认要求精确匹配进程名"
    ),
    LinuxCommand(
        "timeout", CmdCategory.PROC_MGMT, "限时运行一个命令，超时自动终止",
        "timeout [选项] 时长 命令",
        listOf(
            CmdExample("限制命令最多运行 10 秒", "timeout 10 ping google.com"),
            CmdExample("超时后发送指定信号", "timeout -s SIGKILL 5 ./script.sh"),
            CmdExample("超时先发TERM信号，宽限5秒后再强制杀死", "timeout -k 5 10 ./script.sh"),
            CmdExample("限制命令最多运行 5 秒", "timeout 5 ping example.com"),
            CmdExample("超时后强制杀死", "timeout -s KILL 10 ./job.sh")
        ), "⏱️", "防止某个命令卡住不退出，常用于脚本和自动化测试"
    ),

    // ==================== 🌐 网络相关（新增）====================
    LinuxCommand(
        "netstat", CmdCategory.NETWORK, "显示网络连接、路由表、端口占用（传统工具，已被 ss 取代）",
        "netstat [选项]",
        listOf(
            CmdExample("列出所有监听端口", "netstat -tuln"),
            CmdExample("查看端口对应的进程", "netstat -tulnp"),
            CmdExample("查看路由表", "netstat -r"),
            CmdExample("查看监听端口", "netstat -tlnp"),
            CmdExample("统计已建立的连接数", "netstat -an | grep ESTABLISHED | wc -l"),
            CmdExample("查看路由表", "netstat -rn")
        ), "🔌", "较新系统建议用 ss 代替，netstat 可能需要单独安装"
    ),
    LinuxCommand(
        "traceroute", CmdCategory.NETWORK, "追踪数据包到目标主机经过的路由",
        "traceroute 目标",
        listOf(
            CmdExample("追踪到目标的路径", "traceroute google.com"),
            CmdExample("使用 ICMP 协议", "traceroute -I google.com"),
            CmdExample("限制最大跳数", "traceroute -m 15 google.com"),
            CmdExample("不做 DNS 解析（更快）", "traceroute -n example.com")
        ), "🛰️", "排查网络延迟、丢包发生在哪一跳很有用"
    ),
    LinuxCommand(
        "dig", CmdCategory.NETWORK, "查询 DNS 记录信息",
        "dig [选项] 域名 [记录类型]",
        listOf(
            CmdExample("查询域名 A 记录", "dig example.com"),
            CmdExample("查询指定类型记录", "dig example.com MX"),
            CmdExample("只显示简洁结果", "dig +short example.com"),
            CmdExample("指定 DNS 服务器查询", "dig @8.8.8.8 example.com"),
            CmdExample("查询 MX 记录", "dig MX example.com"),
            CmdExample("反向解析", "dig -x 8.8.8.8")
        ), "🔎", "比 nslookup 信息更详细，是排查 DNS 问题的常用工具"
    ),
    LinuxCommand(
        "nc", CmdCategory.NETWORK, "网络瑞士军刀，用于端口测试、简单数据传输",
        "nc [选项] 主机 端口",
        listOf(
            CmdExample("测试端口是否开放", "nc -zv example.com 80"),
            CmdExample("监听端口等待连接", "nc -l 8080"),
            CmdExample("简单发送文件", "nc -l 8080 > received.txt"),
            CmdExample("扫描端口范围", "nc -zv example.com 20-100"),
            CmdExample("监听端口接收数据", "nc -l 9000")
        ), "🔪", "全称 netcat，功能强大，也常被用来做端口扫描或简易聊天"
    ),
    LinuxCommand(
        "iptables", CmdCategory.NETWORK, "配置 Linux 内核防火墙规则",
        "iptables [选项]",
        listOf(
            CmdExample("查看当前规则", "sudo iptables -L -n"),
            CmdExample("允许某端口的入站流量", "sudo iptables -A INPUT -p tcp --dport 80 -j ACCEPT"),
            CmdExample("拒绝某 IP 的所有流量", "sudo iptables -A INPUT -s 1.2.3.4 -j DROP"),
            CmdExample("保存规则（Debian系）", "sudo iptables-save > /etc/iptables/rules.v4"),
            CmdExample("查看规则（带计数、不解析域名）", "sudo iptables -L -n -v"),
            CmdExample("查看 NAT 规则", "sudo iptables -t nat -L -n"),
            CmdExample("备份规则", "sudo iptables-save > rules.v4"),
            CmdExample("查看规则（带行号）", "sudo iptables -L -n -v --line-numbers")
        ), "🧱", "较新发行版常用 nftables 或 firewalld 做上层封装"
    ),
    LinuxCommand(
        "ifconfig", CmdCategory.NETWORK, "显示/配置网络接口（传统工具，已被 ip 取代）",
        "ifconfig [接口] [选项]",
        listOf(
            CmdExample("查看所有网络接口", "ifconfig"),
            CmdExample("查看指定接口", "ifconfig eth0"),
            CmdExample("启用网卡", "sudo ifconfig eth0 up"),
            CmdExample("禁用网卡", "sudo ifconfig eth0 down")
        ), "🌐", "新系统建议用 ip addr / ip link 代替"
    ),

    // ==================== 🔐 权限用户（新增）====================
    LinuxCommand(
        "useradd", CmdCategory.PERM_USER, "创建新用户",
        "useradd [选项] 用户名",
        listOf(
            CmdExample("创建新用户", "sudo useradd alice"),
            CmdExample("创建用户并建立家目录", "sudo useradd -m alice"),
            CmdExample("指定 shell", "sudo useradd -m -s /bin/bash alice"),
            CmdExample("创建用户并指定家目录和 shell", "sudo useradd -m -s /bin/bash username"),
            CmdExample("创建用户并加入 sudo 组", "sudo useradd -m -G sudo username")
        ), "➕", "创建后通常还要用 passwd 设置密码"
    ),
    LinuxCommand(
        "usermod", CmdCategory.PERM_USER, "修改已有用户的属性",
        "usermod [选项] 用户名",
        listOf(
            CmdExample("把用户加入某个组", "sudo usermod -aG sudo alice"),
            CmdExample("修改用户主目录", "sudo usermod -d /home/newdir alice"),
            CmdExample("修改用户 shell", "sudo usermod -s /bin/zsh alice"),
            CmdExample("把当前用户加入 docker 组", "sudo usermod -aG docker \$USER"),
            CmdExample("修改用户登录 shell", "sudo usermod -s /bin/zsh username")
        ), "✏️", "-aG 一定要带 -a（append），不然会把用户原来的附加组都清掉"
    ),
    LinuxCommand(
        "groups", CmdCategory.PERM_USER, "显示用户所属的组",
        "groups [用户名]",
        listOf(
            CmdExample("查看当前用户所在的组", "groups"),
            CmdExample("查看指定用户所在的组", "groups alice")
        ), "👥"
    ),
    LinuxCommand(
        "umask", CmdCategory.PERM_USER, "查看或设置新建文件/目录的默认权限掩码",
        "umask [掩码]",
        listOf(
            CmdExample("查看当前 umask", "umask"),
            CmdExample("设置新的 umask", "umask 022"),
            CmdExample("以符号形式显示", "umask -S")
        ), "🎭", "文件默认权限 = 666 - umask，目录默认权限 = 777 - umask"
    ),

    // ==================== 🗜️ 压缩归档（新增）====================
    LinuxCommand(
        "bzip2", CmdCategory.COMPRESS, "压缩/解压 .bz2 格式文件",
        "bzip2 [选项] 文件",
        listOf(
            CmdExample("压缩文件", "bzip2 file.txt"),
            CmdExample("解压文件", "bunzip2 file.txt.bz2"),
            CmdExample("保留原文件压缩", "bzip2 -k file.txt")
        ), "🗜️", "压缩率通常比 gzip 高，但速度更慢；常配合 tar -j 使用"
    ),
    LinuxCommand(
        "xz", CmdCategory.COMPRESS, "压缩/解压 .xz 格式文件（压缩率很高）",
        "xz [选项] 文件",
        listOf(
            CmdExample("压缩文件", "xz file.txt"),
            CmdExample("解压文件", "unxz file.txt.xz"),
            CmdExample("指定压缩级别（0-9）", "xz -9 file.txt")
        ), "🗜️", "压缩率通常优于 gzip/bzip2，但压缩耗时更长；常配合 tar -J 使用"
    ),

    // ==================== 📦 包管理（新增）====================
    LinuxCommand(
        "yum", CmdCategory.PACKAGE, "RedHat/CentOS 系的包管理工具（旧版）",
        "yum [子命令] [包名]",
        listOf(
            CmdExample("安装软件包", "sudo yum install nginx"),
            CmdExample("更新所有包", "sudo yum update"),
            CmdExample("卸载软件包", "sudo yum remove nginx"),
            CmdExample("搜索包", "yum search keyword")
        ), "📦", "较新的 CentOS/RHEL/Fedora 已改用 dnf 作为继任者"
    ),
    LinuxCommand(
        "rpm", CmdCategory.PACKAGE, "RedHat 系底层包管理器（操作 .rpm 包）",
        "rpm [选项] 包名.rpm",
        listOf(
            CmdExample("安装 rpm 包", "sudo rpm -ivh package.rpm"),
            CmdExample("卸载软件包", "sudo rpm -e package"),
            CmdExample("查询已安装的包", "rpm -qa | grep nginx"),
            CmdExample("查看包信息", "rpm -qi package")
        ), "📦"
    ),
    LinuxCommand(
        "pacman", CmdCategory.PACKAGE, "Arch Linux 的包管理工具",
        "pacman [选项] [包名]",
        listOf(
            CmdExample("更新系统所有包", "sudo pacman -Syu"),
            CmdExample("安装软件包", "sudo pacman -S nginx"),
            CmdExample("卸载软件包", "sudo pacman -R nginx"),
            CmdExample("搜索包", "pacman -Ss keyword")
        ), "📦"
    ),

    // ==================== 🔨 开发工具（新增）====================
    LinuxCommand(
        "python3", CmdCategory.DEV_TOOLS, "运行 Python 3 脚本或交互式解释器",
        "python3 [选项] [脚本.py]",
        listOf(
            CmdExample("进入交互式解释器", "python3"),
            CmdExample("运行脚本", "python3 script.py"),
            CmdExample("查看版本", "python3 --version"),
            CmdExample("启动一个简易 HTTP 服务器", "python3 -m http.server 8000"),
            CmdExample("创建虚拟环境", "python3 -m venv venv"),
            CmdExample("格式化查看 JSON", "python3 -m json.tool data.json"),
            CmdExample("执行一行代码", "python3 -c \"print(1 + 1)\""),
            CmdExample("一行代码计算", "python3 -c \"print(2**10)\"")
        ), "🐍"
    ),
    LinuxCommand(
        "pip", CmdCategory.DEV_TOOLS, "Python 包管理工具",
        "pip [子命令] [包名]",
        listOf(
            CmdExample("安装包", "pip install requests"),
            CmdExample("卸载包", "pip uninstall requests"),
            CmdExample("列出已安装的包", "pip list"),
            CmdExample("导出依赖清单", "pip freeze > requirements.txt"),
            CmdExample("按依赖文件批量安装", "pip install -r requirements.txt"),
            CmdExample("查看可升级的包", "pip list --outdated"),
            CmdExample("安装指定版本", "pip install requests==2.31.0"),
            CmdExample("升级某个包", "pip install -U requests")
        ), "📦", "较新系统里可能需要用 pip3 或加 --break-system-packages"
    ),
    LinuxCommand(
        "docker", CmdCategory.DEV_TOOLS, "容器化平台，构建/运行/管理容器",
        "docker [子命令] [选项]",
        listOf(
            CmdExample("查看运行中的容器", "docker ps"),
            CmdExample("拉取镜像", "docker pull nginx"),
            CmdExample("运行容器", "docker run -d -p 8080:80 nginx"),
            CmdExample("查看容器日志", "docker logs -f 容器ID"),
            CmdExample("进入容器内部", "docker exec -it 容器ID bash"),
            CmdExample("停止容器", "docker stop 容器ID"),
            CmdExample("启动容器并映射端口", "docker run -d --name web -p 8080:80 nginx"),
            CmdExample("进入运行中的容器", "docker exec -it web bash"),
            CmdExample("查看最近 100 行日志并跟踪", "docker logs -f --tail 100 web"),
            CmdExample("挂载当前目录临时运行", "docker run --rm -v \"\$(pwd)\":/app -w /app node:20 npm install"),
            CmdExample("停止所有运行中的容器", "docker stop \$(docker ps -q)"),
            CmdExample("构建镜像", "docker build -t app:1.0 ."),
            CmdExample("清理所有未使用的资源", "docker system prune -af"),
            CmdExample("查看容器资源占用", "docker stats --no-stream")
        ), "🐳"
    ),

    // ==================== ⏰ 任务调度 / 🐛 调试分析 / 其他新增指令 ====================
    LinuxCommand(
        "7z", CmdCategory.COMPRESS, "7-Zip压缩工具，支持多种格式",
        "7z [命令] [选项] 压缩包 [文件]",
        listOf(
            CmdExample("压缩文件", "7z a archive.7z file1 file2"),
            CmdExample("解压文件", "7z x archive.7z"),
            CmdExample("列出内容", "7z l archive.7z")
        ), "🗜️"
    ),
    LinuxCommand(
        "addr2line", CmdCategory.DEBUG_TOOLS, "将程序地址转换为文件名和行号",
        "addr2line [选项] 地址",
        listOf(
            CmdExample("转换地址为源码行", "addr2line -e a.out 0x4005b6"),
            CmdExample("显示函数名", "addr2line -f -e a.out 0x4005b6")
        ), "🐛"
    ),
    LinuxCommand(
        "apropos", CmdCategory.SYS_INFO, "按关键词搜索man手册页",
        "apropos 关键词",
        listOf(
            CmdExample("搜索相关命令", "apropos copy"),
            CmdExample("等价于man -k", "man -k partition")
        ), "🔧"
    ),
    LinuxCommand(
        "ar", CmdCategory.DEV_TOOLS, "创建和管理静态库归档文件",
        "ar [选项] 归档文件 [成员文件]",
        listOf(
            CmdExample("创建静态库", "ar rcs libfoo.a foo.o bar.o"),
            CmdExample("列出归档内容", "ar t libfoo.a"),
            CmdExample("提取成员", "ar x libfoo.a")
        ), "🔨"
    ),
    LinuxCommand(
        "at", CmdCategory.SCHEDULE, "在指定时间执行一次性任务",
        "at 时间",
        listOf(
            CmdExample("10分钟后执行", "at now + 10 minutes"),
            CmdExample("指定时间执行", "at 22:00"),
            CmdExample("从标准输入读取要执行的命令", "echo \"reboot\" | at 03:00")
        ), "⏰", "输入命令后按 Ctrl+D 结束"
    ),
    LinuxCommand(
        "atq", CmdCategory.SCHEDULE, "查看当前用户待执行的at任务队列",
        "atq",
        listOf(
            CmdExample("列出待执行任务", "atq")
        ), "⏰"
    ),
    LinuxCommand(
        "atrm", CmdCategory.SCHEDULE, "删除已排队的at任务",
        "atrm 任务号",
        listOf(
            CmdExample("删除指定任务", "atrm 3")
        ), "⏰"
    ),
    LinuxCommand(
        "autoconf", CmdCategory.DEV_TOOLS, "生成configure脚本的工具",
        "autoconf",
        listOf(
            CmdExample("生成configure脚本", "autoconf"),
            CmdExample("指定配置文件", "autoconf -o configure configure.ac")
        ), "🔨"
    ),
    LinuxCommand(
        "automake", CmdCategory.DEV_TOOLS, "自动生成Makefile.in",
        "automake [选项]",
        listOf(
            CmdExample("生成Makefile.in", "automake --add-missing"),
            CmdExample("初始化项目", "automake -a -c")
        ), "🔨"
    ),
    LinuxCommand(
        "basename", CmdCategory.TEXT_PROC, "提取路径中的文件名部分",
        "basename 路径 [后缀]",
        listOf(
            CmdExample("获取文件名", "basename /usr/bin/sort"),
            CmdExample("去除后缀", "basename file.tar.gz .gz"),
            CmdExample("去掉路径和扩展名", "basename /a/b/c.txt .txt")
        ), "✏️"
    ),
    LinuxCommand(
        "bash", CmdCategory.DEV_TOOLS, "Bourne Again Shell，Linux默认shell",
        "bash [选项] [脚本]",
        listOf(
            CmdExample("执行脚本", "bash script.sh"),
            CmdExample("进入交互式shell", "bash"),
            CmdExample("查看版本", "bash --version"),
            CmdExample("执行命令字符串", "bash -c \"echo hello\""),
            CmdExample("调试脚本（显示每条命令）", "bash -x script.sh"),
            CmdExample("只检查语法不执行", "bash -n script.sh"),
            CmdExample("执行一条命令字符串", "bash -c \"cd /tmp && ls\"")
        ), "🔨"
    ),
    LinuxCommand(
        "batch", CmdCategory.SCHEDULE, "在系统负载较低时执行任务",
        "batch",
        listOf(
            CmdExample("提交批处理任务", "batch")
        ), "⏰", "输入命令后按 Ctrl+D 结束"
    ),
    LinuxCommand(
        "bc", CmdCategory.DEV_TOOLS, "命令行任意精度计算器",
        "bc [选项]",
        listOf(
            CmdExample("交互式计算", "bc"),
            CmdExample("直接计算表达式", "echo '2+3*4' | bc"),
            CmdExample("浮点精度计算", "echo 'scale=2; 10/3' | bc"),
            CmdExample("保留两位小数的除法", "echo \"scale=2; 10/3\" | bc"),
            CmdExample("计算幂", "echo \"2^10\" | bc")
        ), "🔨"
    ),
    LinuxCommand(
        "bind", CmdCategory.DEV_TOOLS, "显示或设置bash按键绑定",
        "bind [选项] [按键序列:命令]",
        listOf(
            CmdExample("列出所有绑定", "bind -p"),
            CmdExample("查看某个绑定", "bind -q complete")
        ), "🔨"
    ),
    LinuxCommand(
        "blkid", CmdCategory.SYS_INFO, "显示块设备属性（UUID、文件系统类型）",
        "blkid [设备]",
        listOf(
            CmdExample("列出所有设备信息", "blkid"),
            CmdExample("查看指定设备", "blkid /dev/sda1")
        ), "🔧"
    ),
    LinuxCommand(
        "bpftrace", CmdCategory.DEBUG_TOOLS, "基于eBPF的动态追踪工具",
        "bpftrace [选项] 脚本",
        listOf(
            CmdExample("追踪系统调用", "bpftrace -e 'tracepoint:syscalls:sys_enter_open { printf(\"%s\\n\", comm); }'")
        ), "🐛"
    ),
    LinuxCommand(
        "cal", CmdCategory.SYS_INFO, "显示日历",
        "cal [月] [年]",
        listOf(
            CmdExample("显示本月日历", "cal"),
            CmdExample("显示指定年份日历", "cal 2026"),
            CmdExample("显示指定月份", "cal 8 2026"),
            CmdExample("显示今年全年日历", "cal -y")
        ), "🔧"
    ),
    LinuxCommand(
        "chattr", CmdCategory.PERM_USER, "修改文件系统级扩展属性",
        "chattr [选项] 文件",
        listOf(
            CmdExample("设置不可修改属性", "chattr +i file.txt"),
            CmdExample("取消不可修改", "chattr -i file.txt")
        ), "🔐", "常用于保护重要系统文件"
    ),
    LinuxCommand(
        "chcon", CmdCategory.PERM_USER, "修改文件SELinux安全上下文",
        "chcon [选项] 上下文 文件",
        listOf(
            CmdExample("设置安全上下文", "chcon -t httpd_sys_content_t file.html"),
            CmdExample("递归修改", "chcon -R -t httpd_sys_content_t /var/www")
        ), "🔐"
    ),
    LinuxCommand(
        "chroot", CmdCategory.PERM_USER, "切换根目录运行命令",
        "chroot 新根目录 [命令]",
        listOf(
            CmdExample("切换根目录并运行shell", "chroot /mnt/newroot /bin/bash")
        ), "🔐", "常用于系统修复或容器构建"
    ),
    LinuxCommand(
        "cksum", CmdCategory.FILE_MGMT, "计算文件CRC校验和及字节数",
        "cksum 文件",
        listOf(
            CmdExample("计算校验和", "cksum file.txt")
        ), "📁"
    ),
    LinuxCommand(
        "clear", CmdCategory.SYS_INFO, "清空终端屏幕",
        "clear",
        listOf(
            CmdExample("清屏", "clear")
        ), "🔧"
    ),
    LinuxCommand(
        "cmake", CmdCategory.DEV_TOOLS, "跨平台自动化构建系统",
        "cmake [选项] 源码目录",
        listOf(
            CmdExample("生成构建文件", "cmake .."),
            CmdExample("指定生成器编译", "cmake --build ."),
            CmdExample("配置Release模式", "cmake -DCMAKE_BUILD_TYPE=Release ..")
        ), "🔨"
    ),
    LinuxCommand(
        "cmp", CmdCategory.FILE_VIEW, "逐字节比较两个文件",
        "cmp [选项] 文件1 文件2",
        listOf(
            CmdExample("比较两个文件", "cmp file1 file2"),
            CmdExample("显示所有差异字节", "cmp -l file1 file2"),
            CmdExample("静默比较，只用退出码判断是否相同", "cmp -s file1 file2 && echo 相同")
        ), "📄"
    ),
    LinuxCommand(
        "col", CmdCategory.TEXT_PROC, "过滤控制字符（常用于man输出）",
        "col [选项]",
        listOf(
            CmdExample("过滤退格字符", "cat file | col -b")
        ), "✏️"
    ),
    LinuxCommand(
        "column", CmdCategory.TEXT_PROC, "将输入格式化为整齐的多列表格",
        "column [选项] 文件",
        listOf(
            CmdExample("表格化显示", "column -t file.csv"),
            CmdExample("指定分隔符", "column -t -s, file.csv"),
            CmdExample("对齐显示 CSV", "column -s, -t data.csv"),
            CmdExample("对齐命令输出", "mount | column -t")
        ), "✏️"
    ),
    LinuxCommand(
        "cpio", CmdCategory.COMPRESS, "归档和恢复文件的工具",
        "cpio [选项]",
        listOf(
            CmdExample("打包文件", "find . | cpio -ov > archive.cpio"),
            CmdExample("解包", "cpio -idv < archive.cpio")
        ), "🗜️"
    ),
    LinuxCommand(
        "csplit", CmdCategory.TEXT_PROC, "按模式将文件分割成多个片段",
        "csplit 文件 模式",
        listOf(
            CmdExample("按行号分割", "csplit file.txt 10 20"),
            CmdExample("按正则分割", "csplit file.txt /PATTERN/")
        ), "✏️"
    ),
    LinuxCommand(
        "ctags", CmdCategory.DEV_TOOLS, "为源码生成索引标签，方便编辑器跳转",
        "ctags [选项] 文件",
        listOf(
            CmdExample("生成标签文件", "ctags -R ."),
            CmdExample("生成指定语言标签", "ctags -R --languages=Python .")
        ), "🔨"
    ),
    LinuxCommand(
        "dc", CmdCategory.TEXT_PROC, "逆波兰表示法(RPN)计算器",
        "dc",
        listOf(
            CmdExample("计算表达式", "echo '3 4 + p' | dc"),
            CmdExample("计算平方根", "echo '2 v p' | dc")
        ), "✏️"
    ),
    LinuxCommand(
        "dd", CmdCategory.FILE_MGMT, "按块复制转换文件，常用于制作启动盘",
        "dd [选项]",
        listOf(
            CmdExample("制作启动U盘", "dd if=image.iso of=/dev/sdb bs=4M status=progress"),
            CmdExample("备份分区", "dd if=/dev/sda of=backup.img bs=1M"),
            CmdExample("测试磁盘顺序写入速度", "dd if=/dev/zero of=testfile bs=1M count=1024 oflag=direct"),
            CmdExample("创建 100MB 测试文件", "dd if=/dev/zero of=test.bin bs=1M count=100 status=progress"),
            CmdExample("生成随机数据文件", "dd if=/dev/urandom of=rand.bin bs=1M count=10"),
            CmdExample("测试磁盘写入速度", "dd if=/dev/zero of=test.bin bs=1M count=1024 status=progress")
        ), "📁", "⚠️ 操作设备前务必确认of=目标，写错会导致数据丢失"
    ),
    LinuxCommand(
        "diff3", CmdCategory.TEXT_PROC, "三方文件差异比较与合并",
        "diff3 文件1 文件2 文件3",
        listOf(
            CmdExample("三方比较合并", "diff3 mine.txt older.txt yours.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "dir", CmdCategory.FILE_MGMT, "列出目录内容（类似ls）",
        "dir [选项] [路径]",
        listOf(
            CmdExample("列出目录", "dir"),
            CmdExample("详细列表", "dir -l")
        ), "📁"
    ),
    LinuxCommand(
        "dirname", CmdCategory.TEXT_PROC, "提取路径中的目录部分",
        "dirname 路径",
        listOf(
            CmdExample("获取目录名", "dirname /usr/bin/sort"),
            CmdExample("获取所在目录", "dirname /a/b/c.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "dmidecode", CmdCategory.SYS_INFO, "读取硬件DMI/SMBIOS信息",
        "dmidecode [选项]",
        listOf(
            CmdExample("显示所有硬件信息", "sudo dmidecode"),
            CmdExample("查看内存信息", "sudo dmidecode -t memory"),
            CmdExample("查看BIOS信息", "sudo dmidecode -t bios")
        ), "🔧"
    ),
    LinuxCommand(
        "dnf", CmdCategory.PACKAGE, "Fedora/RHEL系新一代包管理器",
        "dnf [子命令] [包名]",
        listOf(
            CmdExample("安装软件", "sudo dnf install nginx"),
            CmdExample("更新系统", "sudo dnf update"),
            CmdExample("搜索软件包", "dnf search nginx"),
            CmdExample("卸载软件", "sudo dnf remove nginx")
        ), "📦"
    ),
    LinuxCommand(
        "ed", CmdCategory.TEXT_PROC, "经典行编辑器",
        "ed 文件",
        listOf(
            CmdExample("编辑文件", "ed file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "exec", CmdCategory.SYS_INFO, "用新进程替换当前shell进程",
        "exec 命令",
        listOf(
            CmdExample("替换当前进程", "exec bash"),
            CmdExample("重定向文件描述符", "exec 3< file.txt")
        ), "🔧"
    ),
    LinuxCommand(
        "export", CmdCategory.SYS_INFO, "设置环境变量并导出给子进程",
        "export 变量=值",
        listOf(
            CmdExample("设置环境变量", "export PATH=\\\$PATH:/usr/local/bin"),
            CmdExample("查看已导出变量", "export -p"),
            CmdExample("取消导出某个变量", "export -n VAR"),
            CmdExample("追加 PATH", "export PATH=\"\$PATH:/opt/bin\""),
            CmdExample("加载 .env 中的变量", "export \$(grep -v '^#' .env | xargs)")
        ), "🔧"
    ),
    LinuxCommand(
        "expr", CmdCategory.TEXT_PROC, "计算表达式（数值/字符串）",
        "expr 表达式",
        listOf(
            CmdExample("数值计算", "expr 3 + 5"),
            CmdExample("字符串长度", "expr length \"hello\"")
        ), "✏️"
    ),
    LinuxCommand(
        "factor", CmdCategory.TEXT_PROC, "对数字进行质因数分解",
        "factor 数字",
        listOf(
            CmdExample("质因数分解", "factor 60")
        ), "✏️"
    ),
    LinuxCommand(
        "fallocate", CmdCategory.FILE_MGMT, "预分配文件磁盘空间",
        "fallocate [选项] 文件",
        listOf(
            CmdExample("预分配1GB空间", "fallocate -l 1G bigfile.img"),
            CmdExample("释放（打洞）之前预分配的空间", "fallocate -d bigfile.img")
        ), "📁"
    ),
    LinuxCommand(
        "false", CmdCategory.SYS_INFO, "始终返回失败退出状态(1)",
        "false",
        listOf(
            CmdExample("测试失败分支", "false || echo 失败")
        ), "🔧"
    ),
    LinuxCommand(
        "fc", CmdCategory.SYS_INFO, "编辑并重新执行历史命令",
        "fc [选项]",
        listOf(
            CmdExample("编辑上一条命令", "fc"),
            CmdExample("重新执行历史第10条", "fc -s 10")
        ), "🔧"
    ),
    LinuxCommand(
        "fdisk", CmdCategory.SYS_INFO, "磁盘分区管理工具",
        "fdisk [选项] 设备",
        listOf(
            CmdExample("查看分区表", "sudo fdisk -l"),
            CmdExample("交互式分区", "sudo fdisk /dev/sda"),
            CmdExample("查看指定磁盘的分区表", "sudo fdisk -l /dev/sda")
        ), "🔧", "⚠️ 分区操作有风险，操作前请备份数据"
    ),
    LinuxCommand(
        "figlet", CmdCategory.SYS_INFO, "生成ASCII艺术字",
        "figlet 文本",
        listOf(
            CmdExample("生成艺术字", "figlet Hello"),
            CmdExample("指定字体样式", "figlet -f slant Hello")
        ), "🔧"
    ),
    LinuxCommand(
        "flatpak", CmdCategory.PACKAGE, "跨发行版应用打包与分发系统",
        "flatpak [子命令] 应用",
        listOf(
            CmdExample("安装应用", "flatpak install flathub org.gimp.GIMP"),
            CmdExample("运行应用", "flatpak run org.gimp.GIMP"),
            CmdExample("列出已安装", "flatpak list")
        ), "📦"
    ),
    LinuxCommand(
        "flock", CmdCategory.PROC_MGMT, "通过文件锁管理脚本并发执行",
        "flock [选项] 锁文件 命令",
        listOf(
            CmdExample("加锁执行脚本", "flock /tmp/lock.file ./script.sh"),
            CmdExample("给脚本加文件锁，防止被重复并发执行", "flock -n /tmp/myjob.lock -c './myjob.sh'")
        ), "⚙️"
    ),
    LinuxCommand(
        "fmt", CmdCategory.TEXT_PROC, "简单的文本格式化排版工具",
        "fmt [选项] 文件",
        listOf(
            CmdExample("格式化文本", "fmt file.txt"),
            CmdExample("指定宽度", "fmt -w 60 file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "fold", CmdCategory.TEXT_PROC, "按指定宽度折行显示文本",
        "fold [选项] 文件",
        listOf(
            CmdExample("按80列折行", "fold -w 80 file.txt"),
            CmdExample("按单词边界折行，不截断单词", "fold -s -w 40 file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "fortune", CmdCategory.SYS_INFO, "随机显示一条名言或趣味语句",
        "fortune",
        listOf(
            CmdExample("显示随机名言", "fortune")
        ), "🔧"
    ),
    LinuxCommand(
        "fsck", CmdCategory.SYS_INFO, "检查并修复文件系统错误",
        "fsck [选项] 设备",
        listOf(
            CmdExample("检查文件系统", "sudo fsck /dev/sda1"),
            CmdExample("自动修复", "sudo fsck -y /dev/sda1"),
            CmdExample("即使标记为clean也强制检查", "sudo fsck -f /dev/sda1")
        ), "🔧", "建议在文件系统未挂载状态下运行"
    ),
    LinuxCommand(
        "fuser", CmdCategory.PROC_MGMT, "显示正在使用文件或端口的进程",
        "fuser [选项] 文件/端口",
        listOf(
            CmdExample("查看占用文件的进程", "fuser file.txt"),
            CmdExample("查看占用端口的进程", "fuser 8080/tcp"),
            CmdExample("强制终止占用进程", "fuser -k 8080/tcp"),
            CmdExample("查看谁占用了 8080 端口", "fuser -v 8080/tcp")
        ), "⚙️"
    ),
    LinuxCommand(
        "gawk", CmdCategory.TEXT_PROC, "GNU版awk，功能更强的文本处理工具",
        "gawk '模式{动作}' 文件",
        listOf(
            CmdExample("打印第一列", "gawk '{print $1}' file.txt"),
            CmdExample("按条件过滤", "gawk '$3 > 100' file.txt"),
            CmdExample("统计文件行数", "gawk 'END{print NR}' file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "getconf", CmdCategory.SYS_INFO, "查询系统配置变量",
        "getconf 变量名",
        listOf(
            CmdExample("查看页大小", "getconf PAGE_SIZE"),
            CmdExample("查看最大文件名长度", "getconf NAME_MAX /")
        ), "🔧"
    ),
    LinuxCommand(
        "getent", CmdCategory.SYS_INFO, "查询系统数据库(passwd/group/hosts等)条目",
        "getent 数据库 [键]",
        listOf(
            CmdExample("查询用户信息", "getent passwd root"),
            CmdExample("查询主机解析", "getent hosts example.com")
        ), "🔧"
    ),
    LinuxCommand(
        "getfacl", CmdCategory.PERM_USER, "查看文件的访问控制列表(ACL)",
        "getfacl 文件",
        listOf(
            CmdExample("查看ACL权限", "getfacl file.txt")
        ), "🔐"
    ),
    LinuxCommand(
        "getopts", CmdCategory.DEV_TOOLS, "shell脚本中解析命令行选项",
        "getopts 选项字符串 变量",
        listOf(
            CmdExample("脚本内解析参数", "while getopts \"a:b\" opt; do echo \\\$opt; done")
        ), "🔨"
    ),
    LinuxCommand(
        "groupadd", CmdCategory.PERM_USER, "创建新用户组",
        "groupadd [选项] 组名",
        listOf(
            CmdExample("创建用户组", "sudo groupadd developers"),
            CmdExample("指定GID创建", "sudo groupadd -g 1005 developers"),
            CmdExample("创建系统组（用于服务账户）", "sudo groupadd -r sysgroup")
        ), "🔐"
    ),
    LinuxCommand(
        "groupdel", CmdCategory.PERM_USER, "删除用户组",
        "groupdel 组名",
        listOf(
            CmdExample("删除用户组", "sudo groupdel developers")
        ), "🔐"
    ),
    LinuxCommand(
        "groupmod", CmdCategory.PERM_USER, "修改用户组属性",
        "groupmod [选项] 组名",
        listOf(
            CmdExample("重命名组", "sudo groupmod -n newname oldname"),
            CmdExample("修改GID", "sudo groupmod -g 1010 developers")
        ), "🔐"
    ),
    LinuxCommand(
        "gunzip", CmdCategory.COMPRESS, "解压gzip压缩文件",
        "gunzip 文件.gz",
        listOf(
            CmdExample("解压文件", "gunzip file.gz"),
            CmdExample("解压到标准输出", "gunzip -c file.gz > out.txt")
        ), "🗜️"
    ),
    LinuxCommand(
        "hexdump", CmdCategory.FILE_VIEW, "以十六进制格式显示文件内容",
        "hexdump [选项] 文件",
        listOf(
            CmdExample("十六进制查看", "hexdump -C file.bin"),
            CmdExample("只看开头32字节，C风格显示", "hexdump -C -n 32 file.bin")
        ), "📄"
    ),
    LinuxCommand(
        "host", CmdCategory.NETWORK, "DNS查询工具",
        "host 域名",
        listOf(
            CmdExample("查询域名IP", "host example.com"),
            CmdExample("反向查询", "host 93.184.216.34"),
            CmdExample("反向查询IP对应的域名", "host 8.8.8.8")
        ), "🌐"
    ),
    LinuxCommand(
        "hostid", CmdCategory.SYS_INFO, "显示当前主机的十六进制标识符",
        "hostid",
        listOf(
            CmdExample("显示主机ID", "hostid")
        ), "🔧"
    ),
    LinuxCommand(
        "install", CmdCategory.FILE_MGMT, "复制文件并设置权限属性（常用于安装脚本）",
        "install [选项] 源 目标",
        listOf(
            CmdExample("安装可执行文件", "install -m 755 script.sh /usr/local/bin/"),
            CmdExample("创建目录并安装", "install -d /usr/local/myapp")
        ), "📁"
    ),
    LinuxCommand(
        "inxi", CmdCategory.SYS_INFO, "显示详细系统硬件与软件信息",
        "inxi [选项]",
        listOf(
            CmdExample("查看完整系统信息", "inxi -Fxz"),
            CmdExample("查看CPU信息", "inxi -C")
        ), "🔧"
    ),
    LinuxCommand(
        "iostat", CmdCategory.PROC_MGMT, "报告CPU和磁盘I/O统计信息",
        "iostat [选项] [间隔]",
        listOf(
            CmdExample("查看IO统计", "iostat"),
            CmdExample("每2秒刷新一次", "iostat 2"),
            CmdExample("只看某个磁盘设备，每2秒刷新", "iostat -d sda 2"),
            CmdExample("每秒刷新磁盘 IO（5 次）", "iostat -x 1 5")
        ), "⚙️"
    ),
    LinuxCommand(
        "last", CmdCategory.SYS_INFO, "显示最近登录用户的历史记录",
        "last [选项]",
        listOf(
            CmdExample("查看登录历史", "last"),
            CmdExample("查看指定用户", "last root"),
            CmdExample("只看最近5条登录记录", "last -n 5"),
            CmdExample("最近 10 次登录", "last -n 10"),
            CmdExample("查看重启记录", "last reboot")
        ), "🔧"
    ),
    LinuxCommand(
        "lastb", CmdCategory.SYS_INFO, "显示登录失败的历史记录",
        "lastb",
        listOf(
            CmdExample("查看登录失败记录", "sudo lastb")
        ), "🔧"
    ),
    LinuxCommand(
        "ldconfig", CmdCategory.DEV_TOOLS, "更新动态链接库缓存",
        "ldconfig [选项]",
        listOf(
            CmdExample("更新库缓存", "sudo ldconfig"),
            CmdExample("查看当前缓存库", "ldconfig -p")
        ), "🔨"
    ),
    LinuxCommand(
        "locale", CmdCategory.SYS_INFO, "显示或设置系统语言环境",
        "locale [选项]",
        listOf(
            CmdExample("查看当前语言环境", "locale"),
            CmdExample("列出所有可用locale", "locale -a"),
            CmdExample("临时切换语言环境运行某条命令", "LANG=en_US.UTF-8 date")
        ), "🔧"
    ),
    LinuxCommand(
        "logger", CmdCategory.SYS_INFO, "向系统日志写入消息",
        "logger [选项] 消息",
        listOf(
            CmdExample("写入系统日志", "logger \"备份任务完成\""),
            CmdExample("指定优先级", "logger -p user.warn \"磁盘空间不足\"")
        ), "🔧"
    ),
    LinuxCommand(
        "login", CmdCategory.PERM_USER, "登录系统",
        "login [用户名]",
        listOf(
            CmdExample("以指定用户登录", "login user1")
        ), "🔐"
    ),
    LinuxCommand(
        "logname", CmdCategory.SYS_INFO, "显示当前登录用户名",
        "logname",
        listOf(
            CmdExample("查看登录用户名", "logname")
        ), "🔧"
    ),
    LinuxCommand(
        "logout", CmdCategory.PERM_USER, "退出当前登录shell",
        "logout",
        listOf(
            CmdExample("退出登录会话", "logout")
        ), "🔐"
    ),
    LinuxCommand(
        "look", CmdCategory.TEXT_PROC, "在已排序文件中查找以指定字符串开头的行",
        "look 字符串 [文件]",
        listOf(
            CmdExample("查找单词", "look apple /usr/share/dict/words")
        ), "✏️"
    ),
    LinuxCommand(
        "lshw", CmdCategory.SYS_INFO, "列出详细硬件配置信息",
        "lshw [选项]",
        listOf(
            CmdExample("查看所有硬件", "sudo lshw"),
            CmdExample("以简短格式显示", "sudo lshw -short")
        ), "🔧"
    ),
    LinuxCommand(
        "lsipc", CmdCategory.SYS_INFO, "显示System V进程间通信资源信息",
        "lsipc",
        listOf(
            CmdExample("查看IPC资源", "lsipc")
        ), "🔧"
    ),
    LinuxCommand(
        "lslocks", CmdCategory.SYS_INFO, "列出系统当前文件锁",
        "lslocks",
        listOf(
            CmdExample("查看文件锁", "lslocks")
        ), "🔧"
    ),
    LinuxCommand(
        "lslogins", CmdCategory.SYS_INFO, "显示系统所有用户的登录信息",
        "lslogins [选项]",
        listOf(
            CmdExample("列出所有用户信息", "lslogins")
        ), "🔧"
    ),
    LinuxCommand(
        "lsmem", CmdCategory.SYS_INFO, "显示内存块的分布范围与状态",
        "lsmem",
        listOf(
            CmdExample("查看内存块信息", "lsmem")
        ), "🔧"
    ),
    LinuxCommand(
        "lsns", CmdCategory.SYS_INFO, "列出系统命名空间",
        "lsns [选项]",
        listOf(
            CmdExample("查看所有命名空间", "lsns"),
            CmdExample("按类型过滤", "lsns -t net")
        ), "🔧"
    ),
    LinuxCommand(
        "lspci", CmdCategory.SYS_INFO, "列出所有PCI设备",
        "lspci [选项]",
        listOf(
            CmdExample("查看PCI设备列表", "lspci"),
            CmdExample("显示详细信息", "lspci -v"),
            CmdExample("只看显卡信息", "lspci | grep -i vga")
        ), "🔧"
    ),
    LinuxCommand(
        "lsusb", CmdCategory.SYS_INFO, "列出所有USB设备",
        "lsusb [选项]",
        listOf(
            CmdExample("查看USB设备列表", "lsusb"),
            CmdExample("显示详细信息", "lsusb -v"),
            CmdExample("查看USB设备树结构", "lsusb -t")
        ), "🔧"
    ),
    LinuxCommand(
        "lz4", CmdCategory.COMPRESS, "高速压缩算法工具",
        "lz4 [选项] 文件",
        listOf(
            CmdExample("压缩文件", "lz4 file.txt file.lz4"),
            CmdExample("解压文件", "lz4 -d file.lz4 file.txt")
        ), "🗜️"
    ),
    LinuxCommand(
        "lzma", CmdCategory.COMPRESS, "LZMA压缩工具",
        "lzma [选项] 文件",
        listOf(
            CmdExample("压缩文件", "lzma file.txt"),
            CmdExample("解压文件", "lzma -d file.txt.lzma")
        ), "🗜️"
    ),
    LinuxCommand(
        "lzop", CmdCategory.COMPRESS, "基于LZO算法的快速压缩工具",
        "lzop [选项] 文件",
        listOf(
            CmdExample("压缩文件", "lzop file.txt"),
            CmdExample("解压文件", "lzop -d file.txt.lzo")
        ), "🗜️"
    ),
    LinuxCommand(
        "md5sum", CmdCategory.FILE_MGMT, "计算文件MD5校验和",
        "md5sum 文件",
        listOf(
            CmdExample("计算MD5值", "md5sum file.iso"),
            CmdExample("校验完整性", "md5sum -c checksums.md5"),
            CmdExample("生成校验文件并验证", "md5sum file.iso > sums.md5 && md5sum -c sums.md5")
        ), "📁"
    ),
    LinuxCommand(
        "mesg", CmdCategory.SYS_INFO, "控制是否允许其他用户向终端写消息",
        "mesg [y|n]",
        listOf(
            CmdExample("允许写消息", "mesg y"),
            CmdExample("禁止写消息", "mesg n")
        ), "🔧"
    ),
    LinuxCommand(
        "mkfifo", CmdCategory.FILE_MGMT, "创建命名管道(FIFO)",
        "mkfifo 名称",
        listOf(
            CmdExample("创建命名管道", "mkfifo mypipe")
        ), "📁"
    ),
    LinuxCommand(
        "mkfs", CmdCategory.SYS_INFO, "在设备上创建文件系统（格式化）",
        "mkfs [选项] 设备",
        listOf(
            CmdExample("格式化为ext4", "sudo mkfs.ext4 /dev/sdb1"),
            CmdExample("格式化为FAT32", "sudo mkfs.vfat /dev/sdb1")
        ), "🔧", "⚠️ 会清空目标设备所有数据"
    ),
    LinuxCommand(
        "mknod", CmdCategory.FILE_MGMT, "创建特殊文件（设备文件）",
        "mknod 名称 类型 主设备号 次设备号",
        listOf(
            CmdExample("创建字符设备文件", "sudo mknod /dev/mydev c 100 0")
        ), "📁"
    ),
    LinuxCommand(
        "mkswap", CmdCategory.SYS_INFO, "创建交换分区/文件",
        "mkswap 设备/文件",
        listOf(
            CmdExample("创建交换空间", "sudo mkswap /swapfile"),
            CmdExample("启用交换空间", "sudo swapon /swapfile")
        ), "🔧"
    ),
    LinuxCommand(
        "mktemp", CmdCategory.FILE_MGMT, "安全创建临时文件或目录",
        "mktemp [选项]",
        listOf(
            CmdExample("创建临时文件", "mktemp"),
            CmdExample("创建临时目录", "mktemp -d"),
            CmdExample("指定文件名模板创建", "mktemp /tmp/myapp.XXXXXX"),
            CmdExample("创建临时文件并使用", "tmp=\$(mktemp) && echo \$tmp")
        ), "📁"
    ),
    LinuxCommand(
        "mount", CmdCategory.SYS_INFO, "挂载文件系统",
        "mount [选项] 设备 挂载点",
        listOf(
            CmdExample("挂载设备", "sudo mount /dev/sdb1 /mnt"),
            CmdExample("查看已挂载文件系统", "mount"),
            CmdExample("挂载ISO镜像", "sudo mount -o loop file.iso /mnt"),
            CmdExample("以只读方式挂载", "sudo mount -o ro /dev/sdb1 /mnt"),
            CmdExample("整齐显示挂载信息", "mount | column -t"),
            CmdExample("挂载 NFS", "sudo mount -t nfs host:/share /mnt"),
            CmdExample("以读写方式重新挂载", "sudo mount -o remount,rw /")
        ), "🔧"
    ),
    LinuxCommand(
        "mt", CmdCategory.SYS_INFO, "控制磁带驱动器操作",
        "mt [选项] 设备 命令",
        listOf(
            CmdExample("倒带磁带", "mt -f /dev/st0 rewind")
        ), "🔧"
    ),
    LinuxCommand(
        "mtr", CmdCategory.NETWORK, "结合ping和traceroute的实时网络诊断工具",
        "mtr 主机",
        listOf(
            CmdExample("追踪并显示实时统计", "mtr example.com"),
            CmdExample("生成报告模式", "mtr -r example.com"),
            CmdExample("指定探测次数后自动退出", "mtr -c 10 example.com")
        ), "🌐"
    ),
    LinuxCommand(
        "ncal", CmdCategory.SYS_INFO, "以另一种布局显示日历",
        "ncal [月] [年]",
        listOf(
            CmdExample("显示日历", "ncal"),
            CmdExample("显示某年日历", "ncal -y 2026")
        ), "🔧"
    ),
    LinuxCommand(
        "neofetch", CmdCategory.SYS_INFO, "以ASCII图案风格显示系统信息摘要",
        "neofetch",
        listOf(
            CmdExample("显示系统信息", "neofetch")
        ), "🔧"
    ),
    LinuxCommand(
        "newgrp", CmdCategory.PERM_USER, "切换当前用户的有效用户组",
        "newgrp 组名",
        listOf(
            CmdExample("切换有效组", "newgrp developers")
        ), "🔐"
    ),
    LinuxCommand(
        "nftables", CmdCategory.NETWORK, "新一代Linux防火墙框架（取代iptables）",
        "nft [命令]",
        listOf(
            CmdExample("列出规则", "sudo nft list ruleset"),
            CmdExample("添加规则表", "sudo nft add table inet filter")
        ), "🌐"
    ),
    LinuxCommand(
        "ninja", CmdCategory.DEV_TOOLS, "小型高速构建系统",
        "ninja [目标]",
        listOf(
            CmdExample("执行构建", "ninja"),
            CmdExample("指定构建目录", "ninja -C build")
        ), "🔨"
    ),
    LinuxCommand(
        "nm", CmdCategory.DEBUG_TOOLS, "列出目标文件的符号表",
        "nm [选项] 文件",
        listOf(
            CmdExample("查看符号表", "nm a.out"),
            CmdExample("只看动态符号", "nm -D lib.so")
        ), "🐛"
    ),
    LinuxCommand(
        "nmcli", CmdCategory.NETWORK, "NetworkManager命令行管理工具",
        "nmcli [对象] [命令]",
        listOf(
            CmdExample("查看网络连接", "nmcli connection show"),
            CmdExample("连接WiFi", "nmcli device wifi connect SSID password 密码"),
            CmdExample("查看设备状态", "nmcli device status")
        ), "🌐"
    ),
    LinuxCommand(
        "nproc", CmdCategory.SYS_INFO, "显示可用处理器核心数量",
        "nproc [选项]",
        listOf(
            CmdExample("查看核心数", "nproc"),
            CmdExample("显示所有核心（含离线）", "nproc --all")
        ), "🔧"
    ),
    LinuxCommand(
        "nslookup", CmdCategory.NETWORK, "查询DNS域名解析信息",
        "nslookup 域名",
        listOf(
            CmdExample("查询域名IP", "nslookup example.com"),
            CmdExample("指定DNS服务器查询", "nslookup example.com 8.8.8.8"),
            CmdExample("查询MX邮件记录", "nslookup -type=mx example.com")
        ), "🌐"
    ),
    LinuxCommand(
        "numfmt", CmdCategory.TEXT_PROC, "转换数字为人类可读格式",
        "numfmt [选项]",
        listOf(
            CmdExample("转为可读格式", "echo 1000000 | numfmt --to=iec"),
            CmdExample("把可读格式转换回数字", "numfmt --from=iec 1M")
        ), "✏️"
    ),
    LinuxCommand(
        "objdump", CmdCategory.DEBUG_TOOLS, "显示目标文件详细信息（反汇编等）",
        "objdump [选项] 文件",
        listOf(
            CmdExample("反汇编代码", "objdump -d a.out"),
            CmdExample("查看文件头", "objdump -f a.out")
        ), "🐛"
    ),
    LinuxCommand(
        "od", CmdCategory.FILE_VIEW, "以八进制等格式转储文件内容",
        "od [选项] 文件",
        listOf(
            CmdExample("八进制查看", "od file.bin"),
            CmdExample("十六进制查看", "od -x file.bin")
        ), "📄"
    ),
    LinuxCommand(
        "parted", CmdCategory.SYS_INFO, "高级磁盘分区管理工具",
        "parted [设备]",
        listOf(
            CmdExample("交互式分区", "sudo parted /dev/sda"),
            CmdExample("查看分区表", "sudo parted -l"),
            CmdExample("非交互式创建分区", "sudo parted /dev/sda mkpart primary ext4 0% 100%")
        ), "🔧", "⚠️ 分区操作有风险，操作前请备份数据"
    ),
    LinuxCommand(
        "patch", CmdCategory.DEV_TOOLS, "将补丁文件应用到源码",
        "patch [选项] < 补丁文件",
        listOf(
            CmdExample("应用补丁", "patch < fix.patch"),
            CmdExample("指定目标文件应用", "patch file.c fix.patch"),
            CmdExample("生成补丁", "diff -u old.c new.c > fix.patch"),
            CmdExample("先试运行", "patch --dry-run -p1 < fix.patch"),
            CmdExample("撤销补丁", "patch -R -p1 < fix.patch")
        ), "🔨"
    ),
    LinuxCommand(
        "pathchk", CmdCategory.TEXT_PROC, "检查文件路径的有效性和可移植性",
        "pathchk 路径",
        listOf(
            CmdExample("检查路径合法性", "pathchk /usr/local/bin")
        ), "✏️"
    ),
    LinuxCommand(
        "perf", CmdCategory.DEBUG_TOOLS, "Linux性能分析工具",
        "perf [子命令]",
        listOf(
            CmdExample("统计程序性能", "perf stat ./myprogram"),
            CmdExample("采样分析热点", "perf record ./myprogram"),
            CmdExample("查看分析报告", "perf report")
        ), "🐛"
    ),
    LinuxCommand(
        "pkg-config", CmdCategory.DEV_TOOLS, "查询已安装库的编译链接参数",
        "pkg-config [选项] 库名",
        listOf(
            CmdExample("获取编译参数", "pkg-config --cflags gtk+-3.0"),
            CmdExample("获取链接参数", "pkg-config --libs gtk+-3.0")
        ), "🔨"
    ),
    LinuxCommand(
        "pmap", CmdCategory.PROC_MGMT, "显示进程的内存映射情况",
        "pmap [选项] PID",
        listOf(
            CmdExample("查看进程内存映射", "pmap 1234"),
            CmdExample("显示详细信息", "pmap -x 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "popd", CmdCategory.FILE_MGMT, "从目录栈弹出目录并切换",
        "popd",
        listOf(
            CmdExample("弹出并切换目录", "popd")
        ), "📁"
    ),
    LinuxCommand(
        "pr", CmdCategory.TEXT_PROC, "将文本文件分页格式化用于打印",
        "pr [选项] 文件",
        listOf(
            CmdExample("分页格式化", "pr file.txt"),
            CmdExample("分栏显示", "pr -2 file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "printenv", CmdCategory.SYS_INFO, "打印环境变量",
        "printenv [变量名]",
        listOf(
            CmdExample("查看所有环境变量", "printenv"),
            CmdExample("查看指定变量", "printenv PATH")
        ), "🔧"
    ),
    LinuxCommand(
        "printf", CmdCategory.TEXT_PROC, "按格式输出文本",
        "printf 格式 参数",
        listOf(
            CmdExample("格式化输出", "printf \"%s is %d\\n\" Age 25"),
            CmdExample("格式化数字并补零", "printf \"%03d\\n\" 5"),
            CmdExample("补零格式化数字", "printf \"%05d\\n\" 42"),
            CmdExample("保留两位小数", "printf \"%.2f\\n\" 3.14159"),
            CmdExample("逐行输出多个参数", "printf \"%s\\n\" a b c")
        ), "✏️"
    ),
    LinuxCommand(
        "prlimit", CmdCategory.PROC_MGMT, "查看或设置进程资源限制",
        "prlimit [选项] [-p PID]",
        listOf(
            CmdExample("查看进程限制", "prlimit -p 1234"),
            CmdExample("设置文件描述符上限", "prlimit --nofile=2048 -p 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "pstree", CmdCategory.PROC_MGMT, "以树状图显示进程关系",
        "pstree [选项] [PID]",
        listOf(
            CmdExample("显示进程树", "pstree"),
            CmdExample("显示PID", "pstree -p"),
            CmdExample("查看某个进程的子进程树", "pstree -p 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "pwdx", CmdCategory.PROC_MGMT, "显示进程的当前工作目录",
        "pwdx PID",
        listOf(
            CmdExample("查看进程工作目录", "pwdx 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "rar", CmdCategory.COMPRESS, "创建RAR格式压缩包",
        "rar a 压缩包 文件",
        listOf(
            CmdExample("创建压缩包", "rar a archive.rar file1 file2"),
            CmdExample("解压", "unrar x archive.rar")
        ), "🗜️"
    ),
    LinuxCommand(
        "rbash", CmdCategory.SYS_INFO, "受限shell，限制用户可执行的操作",
        "rbash",
        listOf(
            CmdExample("以受限模式启动", "rbash")
        ), "🔧"
    ),
    LinuxCommand(
        "read", CmdCategory.DEV_TOOLS, "从标准输入读取一行到变量（shell内建）",
        "read [选项] 变量",
        listOf(
            CmdExample("读取用户输入", "read name; echo \\\$name"),
            CmdExample("带提示读取", "read -p \"请输入: \" name"),
            CmdExample("静默读取密码（不回显）", "read -s -p \"密码: \" pass"),
            CmdExample("提示输入并显示", "read -p \"Name: \" name && echo \"Hi \$name\""),
            CmdExample("隐藏输入（密码）", "read -s -p \"Password: \" pw")
        ), "🔨"
    ),
    LinuxCommand(
        "readlink", CmdCategory.FILE_MGMT, "显示符号链接指向的真实路径",
        "readlink [选项] 链接",
        listOf(
            CmdExample("查看链接目标", "readlink /usr/bin/python"),
            CmdExample("显示绝对路径", "readlink -f link")
        ), "📁"
    ),
    LinuxCommand(
        "realpath", CmdCategory.FILE_MGMT, "解析并显示文件的绝对路径",
        "realpath 路径",
        listOf(
            CmdExample("获取绝对路径", "realpath ../file.txt"),
            CmdExample("配合脚本获取文件的绝对路径", "abspath=$(realpath file.txt)"),
            CmdExample("计算相对路径", "realpath --relative-to=/a /a/b/c")
        ), "📁"
    ),
    LinuxCommand(
        "reboot", CmdCategory.SYS_INFO, "重启系统",
        "reboot",
        listOf(
            CmdExample("重启系统", "sudo reboot")
        ), "🔧", "⚠️ 会中断所有正在运行的进程"
    ),
    LinuxCommand(
        "rename", CmdCategory.FILE_MGMT, "批量重命名文件",
        "rename 's/旧/新/' 文件",
        listOf(
            CmdExample("批量替换后缀", "rename 's/.txt/.md/' *.txt"),
            CmdExample("预览重命名效果，不实际执行", "rename -n 's/.txt/.md/' *.txt")
        ), "📁"
    ),
    LinuxCommand(
        "renice", CmdCategory.PROC_MGMT, "调整正在运行进程的优先级",
        "renice 优先级 -p PID",
        listOf(
            CmdExample("调整进程优先级", "renice -n 10 -p 1234"),
            CmdExample("提升优先级需root", "sudo renice -n -5 -p 1234"),
            CmdExample("调整运行中进程的优先级", "sudo renice -n 5 -p 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "reset", CmdCategory.SYS_INFO, "重置终端状态",
        "reset",
        listOf(
            CmdExample("重置终端", "reset")
        ), "🔧", "终端显示异常时可用来恢复"
    ),
    LinuxCommand(
        "resize", CmdCategory.SYS_INFO, "调整并显示终端窗口大小",
        "resize",
        listOf(
            CmdExample("调整终端大小设置", "resize")
        ), "🔧"
    ),
    LinuxCommand(
        "screen", CmdCategory.PROC_MGMT, "终端多路复用器，支持会话保持",
        "screen [选项]",
        listOf(
            CmdExample("创建新会话", "screen -S mysession"),
            CmdExample("分离会话", "Ctrl+A 然后 D"),
            CmdExample("恢复会话", "screen -r mysession"),
            CmdExample("查看所有会话", "screen -ls"),
            CmdExample("新建命名会话", "screen -S work"),
            CmdExample("后台直接运行命令", "screen -dmS job ./run.sh"),
            CmdExample("列出并恢复会话", "screen -ls && screen -r work")
        ), "⚙️"
    ),
    LinuxCommand(
        "screenfetch", CmdCategory.SYS_INFO, "以ASCII图案显示系统信息",
        "screenfetch",
        listOf(
            CmdExample("显示系统信息", "screenfetch")
        ), "🔧"
    ),
    LinuxCommand(
        "script", CmdCategory.SYS_INFO, "记录终端会话到文件",
        "script [文件]",
        listOf(
            CmdExample("开始记录会话", "script session.log"),
            CmdExample("结束记录", "exit")
        ), "🔧"
    ),
    LinuxCommand(
        "seq", CmdCategory.TEXT_PROC, "生成数字序列",
        "seq [选项] 起 [步长] 止",
        listOf(
            CmdExample("生成1到10", "seq 1 10"),
            CmdExample("指定步长", "seq 0 2 20"),
            CmdExample("自定义分隔符", "seq -s, 1 5"),
            CmdExample("补零编号", "seq -w 1 10"),
            CmdExample("步长为 2", "seq 1 2 10")
        ), "✏️"
    ),
    LinuxCommand(
        "set", CmdCategory.SYS_INFO, "设置shell选项或显示所有变量（内建命令）",
        "set [选项]",
        listOf(
            CmdExample("显示所有变量", "set"),
            CmdExample("开启调试模式", "set -x"),
            CmdExample("脚本中命令出错时立即退出", "set -e")
        ), "🔧"
    ),
    LinuxCommand(
        "setfacl", CmdCategory.PERM_USER, "设置文件的访问控制列表(ACL)",
        "setfacl [选项] 文件",
        listOf(
            CmdExample("授予用户读写权限", "setfacl -m u:alice:rw file.txt"),
            CmdExample("删除ACL条目", "setfacl -x u:alice file.txt")
        ), "🔐"
    ),
    LinuxCommand(
        "setsid", CmdCategory.PROC_MGMT, "在新会话中运行程序",
        "setsid 命令",
        listOf(
            CmdExample("脱离终端运行", "setsid ./myapp &")
        ), "⚙️"
    ),
    LinuxCommand(
        "sh", CmdCategory.DEV_TOOLS, "POSIX标准shell解释器",
        "sh [选项] [脚本]",
        listOf(
            CmdExample("执行脚本", "sh script.sh"),
            CmdExample("以POSIX兼容模式执行命令字符串", "sh -c \"echo hello\"")
        ), "🔨"
    ),
    LinuxCommand(
        "sha1sum", CmdCategory.FILE_MGMT, "计算文件SHA1校验和",
        "sha1sum 文件",
        listOf(
            CmdExample("计算SHA1值", "sha1sum file.iso"),
            CmdExample("校验文件完整性", "sha1sum -c checksums.sha1")
        ), "📁"
    ),
    LinuxCommand(
        "sha256sum", CmdCategory.FILE_MGMT, "计算文件SHA256校验和",
        "sha256sum 文件",
        listOf(
            CmdExample("计算SHA256值", "sha256sum file.iso"),
            CmdExample("校验完整性", "sha256sum -c checksums.sha256"),
            CmdExample("校验下载文件", "echo \"<hash>  file.iso\" | sha256sum -c"),
            CmdExample("校验文件完整性", "sha256sum -c SHA256SUMS"),
            CmdExample("生成目录下所有文件校验值", "find . -type f -exec sha256sum {} + > SHA256SUMS")
        ), "📁"
    ),
    LinuxCommand(
        "sha512sum", CmdCategory.FILE_MGMT, "计算文件SHA512校验和",
        "sha512sum 文件",
        listOf(
            CmdExample("计算SHA512值", "sha512sum file.iso"),
            CmdExample("校验文件完整性", "sha512sum -c checksums.sha512")
        ), "📁"
    ),
    LinuxCommand(
        "shred", CmdCategory.FILE_MGMT, "安全彻底删除文件（覆写数据）",
        "shred [选项] 文件",
        listOf(
            CmdExample("安全删除文件", "shred -u file.txt"),
            CmdExample("多次覆写", "shred -n 5 -u file.txt")
        ), "📁", "用于敏感数据的不可恢复删除"
    ),
    LinuxCommand(
        "shuf", CmdCategory.TEXT_PROC, "随机打乱文件行顺序或生成随机数",
        "shuf [选项] 文件",
        listOf(
            CmdExample("打乱行顺序", "shuf file.txt"),
            CmdExample("生成随机数", "shuf -i 1-100 -n 5"),
            CmdExample("从列表中随机抽取一行", "shuf -n 1 names.txt"),
            CmdExample("随机取一行", "shuf -n 1 file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "sl", CmdCategory.SYS_INFO, "恶搞命令，显示一列火车（打错ls时触发）",
        "sl",
        listOf(
            CmdExample("查看火车动画", "sl")
        ), "🔧"
    ),
    LinuxCommand(
        "sleep", CmdCategory.PROC_MGMT, "暂停执行指定时间",
        "sleep 时间",
        listOf(
            CmdExample("暂停5秒", "sleep 5"),
            CmdExample("暂停2分钟", "sleep 2m"),
            CmdExample("以小时为单位等待", "sleep 1h"),
            CmdExample("等待 5 秒再执行", "sleep 5 && echo done"),
            CmdExample("支持小数秒", "sleep 0.5")
        ), "⚙️"
    ),
    LinuxCommand(
        "socat", CmdCategory.NETWORK, "多用途网络数据中继工具",
        "socat 地址1 地址2",
        listOf(
            CmdExample("端口转发", "socat TCP-LISTEN:8080,fork TCP:localhost:80"),
            CmdExample("测试TCP连接", "socat - TCP:example.com:80")
        ), "🌐"
    ),
    LinuxCommand(
        "source", CmdCategory.DEV_TOOLS, "在当前shell中执行脚本（同.命令）",
        "source 文件",
        listOf(
            CmdExample("加载配置文件", "source ~/.bashrc"),
            CmdExample("等价简写（. 就是 source）", ". ~/.bashrc"),
            CmdExample("激活 Python 虚拟环境", "source venv/bin/activate")
        ), "🔨"
    ),
    LinuxCommand(
        "split", CmdCategory.FILE_MGMT, "将大文件分割成多个小文件",
        "split [选项] 文件 前缀",
        listOf(
            CmdExample("按大小分割", "split -b 100M bigfile.iso part_"),
            CmdExample("按行数分割", "split -l 1000 file.txt part_"),
            CmdExample("按行数切分大文件", "split -l 1000 big.txt part_"),
            CmdExample("按大小切分", "split -b 100M big.iso part_"),
            CmdExample("合并切分文件", "cat part_* > big.iso"),
            CmdExample("合并切分的文件", "cat part_* > big.zip")
        ), "📁"
    ),
    LinuxCommand(
        "strings", CmdCategory.FILE_VIEW, "提取二进制文件中的可打印字符串",
        "strings [选项] 文件",
        listOf(
            CmdExample("提取字符串", "strings /bin/ls"),
            CmdExample("显示偏移量", "strings -t x file.bin"),
            CmdExample("只显示长度超过10的字符串", "strings -n 10 file.bin")
        ), "📄"
    ),
    LinuxCommand(
        "strip", CmdCategory.DEV_TOOLS, "移除可执行文件中的调试符号信息",
        "strip [选项] 文件",
        listOf(
            CmdExample("移除符号信息", "strip myprogram"),
            CmdExample("生成新文件不修改原文件", "strip -o out myprogram")
        ), "🔨", "可显著减小可执行文件体积"
    ),
    LinuxCommand(
        "sum", CmdCategory.FILE_MGMT, "计算文件校验和（传统checksum）",
        "sum 文件",
        listOf(
            CmdExample("计算校验和", "sum file.txt")
        ), "📁"
    ),
    LinuxCommand(
        "sync", CmdCategory.SYS_INFO, "将缓存数据写入磁盘",
        "sync",
        listOf(
            CmdExample("同步磁盘缓存", "sync")
        ), "🔧", "拔U盘前建议先执行sync"
    ),
    LinuxCommand(
        "sysctl", CmdCategory.SYS_INFO, "运行时查看或修改内核参数",
        "sysctl [选项] 参数",
        listOf(
            CmdExample("查看所有内核参数", "sysctl -a"),
            CmdExample("修改参数", "sudo sysctl -w net.ipv4.ip_forward=1"),
            CmdExample("查看单个参数", "sysctl vm.swappiness"),
            CmdExample("查看是否开启 IP 转发", "sysctl net.ipv4.ip_forward"),
            CmdExample("重新加载配置", "sudo sysctl -p")
        ), "🔧"
    ),
    LinuxCommand(
        "taskset", CmdCategory.PROC_MGMT, "设置进程的CPU亲和性",
        "taskset [选项] 掩码 命令",
        listOf(
            CmdExample("绑定到指定CPU核心运行", "taskset -c 0,1 ./myprogram"),
            CmdExample("查看进程CPU绑定", "taskset -p 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "telnet", CmdCategory.NETWORK, "远程登录及端口测试工具",
        "telnet 主机 [端口]",
        listOf(
            CmdExample("连接远程主机", "telnet example.com"),
            CmdExample("测试端口连通性", "telnet example.com 80")
        ), "🌐"
    ),
    LinuxCommand(
        "test", CmdCategory.DEV_TOOLS, "检测表达式条件（shell内建，等价于[ ]）",
        "test 表达式",
        listOf(
            CmdExample("判断文件是否存在", "test -f file.txt && echo 存在"),
            CmdExample("等价写法", "&& echo 存在"),
            CmdExample("目录不存在则创建", "[ -d dir ] || mkdir dir")
        ), "🔨"
    ),
    LinuxCommand(
        "time", CmdCategory.PROC_MGMT, "统计命令执行耗时",
        "time 命令",
        listOf(
            CmdExample("测量命令执行时间", "time ls -la"),
            CmdExample("查看更详细的资源占用（含内存）", "/usr/bin/time -v ./script.sh")
        ), "⚙️"
    ),
    LinuxCommand(
        "tmux", CmdCategory.PROC_MGMT, "终端多路复用器，支持分屏和会话保持",
        "tmux [命令]",
        listOf(
            CmdExample("创建新会话", "tmux new -s mysession"),
            CmdExample("分离会话", "Ctrl+B 然后 D"),
            CmdExample("恢复会话", "tmux attach -t mysession"),
            CmdExample("查看所有会话", "tmux ls"),
            CmdExample("新建命名会话", "tmux new -s work"),
            CmdExample("关闭会话", "tmux kill-session -t work")
        ), "⚙️"
    ),
    LinuxCommand(
        "tput", CmdCategory.SYS_INFO, "控制终端输出（颜色、光标位置等）",
        "tput 参数",
        listOf(
            CmdExample("清屏", "tput clear"),
            CmdExample("设置文字颜色", "tput setaf 1"),
            CmdExample("获取终端列数", "tput cols")
        ), "🔧"
    ),
    LinuxCommand(
        "trap", CmdCategory.DEV_TOOLS, "捕获并处理shell信号",
        "trap 命令 信号",
        listOf(
            CmdExample("捕获Ctrl+C", "trap 'echo 已中断' INT"),
            CmdExample("脚本退出时清理", "trap 'rm -f tmp.txt' EXIT")
        ), "🔨"
    ),
    LinuxCommand(
        "true", CmdCategory.SYS_INFO, "始终返回成功退出状态(0)",
        "true",
        listOf(
            CmdExample("用于无限循环", "while true; do echo hi; sleep 1; done")
        ), "🔧"
    ),
    LinuxCommand(
        "truncate", CmdCategory.FILE_MGMT, "调整文件大小（截断或扩展）",
        "truncate [选项] 文件",
        listOf(
            CmdExample("截断为指定大小", "truncate -s 100M file.img"),
            CmdExample("清空文件内容", "truncate -s 0 file.txt"),
            CmdExample("清空文件但保留文件", "truncate -s 0 app.log"),
            CmdExample("创建 1GB 稀疏文件", "truncate -s 1G big.img")
        ), "📁"
    ),
    LinuxCommand(
        "tsort", CmdCategory.TEXT_PROC, "对有向图进行拓扑排序",
        "tsort 文件",
        listOf(
            CmdExample("拓扑排序", "tsort dependencies.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "tty", CmdCategory.SYS_INFO, "显示当前终端设备文件名",
        "tty",
        listOf(
            CmdExample("查看当前终端", "tty")
        ), "🔧"
    ),
    LinuxCommand(
        "umount", CmdCategory.SYS_INFO, "卸载已挂载的文件系统",
        "umount 挂载点/设备",
        listOf(
            CmdExample("卸载设备", "sudo umount /mnt"),
            CmdExample("强制卸载", "sudo umount -f /mnt"),
            CmdExample("延迟卸载（设备忙时也能成功）", "sudo umount -l /mnt/usb")
        ), "🔧"
    ),
    LinuxCommand(
        "unalias", CmdCategory.SYS_INFO, "删除已定义的别名",
        "unalias 别名",
        listOf(
            CmdExample("删除别名", "unalias ll"),
            CmdExample("删除所有别名", "unalias -a")
        ), "🔧"
    ),
    LinuxCommand(
        "uncompress", CmdCategory.COMPRESS, "解压.Z格式压缩文件",
        "uncompress 文件.Z",
        listOf(
            CmdExample("解压文件", "uncompress file.Z")
        ), "🗜️"
    ),
    LinuxCommand(
        "unexpand", CmdCategory.TEXT_PROC, "将空格转换为制表符",
        "unexpand [选项] 文件",
        listOf(
            CmdExample("空格转Tab", "unexpand file.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "unlink", CmdCategory.FILE_MGMT, "删除单个文件（比rm更底层）",
        "unlink 文件",
        listOf(
            CmdExample("删除文件", "unlink file.txt")
        ), "📁"
    ),
    LinuxCommand(
        "unshare", CmdCategory.PERM_USER, "在新命名空间中运行程序",
        "unshare [选项] 命令",
        listOf(
            CmdExample("创建新网络命名空间运行", "sudo unshare --net bash"),
            CmdExample("创建新PID命名空间", "sudo unshare --pid --fork bash")
        ), "🔐"
    ),
    LinuxCommand(
        "updatedb", CmdCategory.FILE_MGMT, "更新locate命令使用的文件数据库",
        "updatedb",
        listOf(
            CmdExample("更新文件索引数据库", "sudo updatedb")
        ), "📁", "locate命令依赖此数据库，需定期更新"
    ),
    LinuxCommand(
        "userdel", CmdCategory.PERM_USER, "删除用户账户",
        "userdel [选项] 用户名",
        listOf(
            CmdExample("删除用户", "sudo userdel username"),
            CmdExample("删除用户及主目录", "sudo userdel -r username")
        ), "🔐"
    ),
    LinuxCommand(
        "users", CmdCategory.SYS_INFO, "显示当前登录系统的用户列表",
        "users",
        listOf(
            CmdExample("查看在线用户", "users")
        ), "🔧"
    ),
    LinuxCommand(
        "uudecode", CmdCategory.TEXT_PROC, "解码uuencode编码的文件",
        "uudecode 文件",
        listOf(
            CmdExample("解码文件", "uudecode file.uu")
        ), "✏️"
    ),
    LinuxCommand(
        "uuencode", CmdCategory.TEXT_PROC, "将二进制文件编码为ASCII文本",
        "uuencode 源文件 输出名",
        listOf(
            CmdExample("编码文件", "uuencode file.bin file.bin > file.uu")
        ), "✏️"
    ),
    LinuxCommand(
        "valgrind", CmdCategory.DEBUG_TOOLS, "内存调试与性能分析工具",
        "valgrind [选项] 程序",
        listOf(
            CmdExample("检测内存泄漏", "valgrind --leak-check=full ./myprogram"),
            CmdExample("性能分析", "valgrind --tool=callgrind ./myprogram")
        ), "🐛"
    ),
    LinuxCommand(
        "vdf", CmdCategory.SYS_INFO, "显示磁盘空间使用情况（df的变体）",
        "vdf [选项]",
        listOf(
            CmdExample("查看磁盘使用", "vdf -h")
        ), "🔧"
    ),
    LinuxCommand(
        "vdir", CmdCategory.FILE_MGMT, "以长格式列出目录内容（等价于ls -l）",
        "vdir [选项] [路径]",
        listOf(
            CmdExample("详细列出目录", "vdir")
        ), "📁"
    ),
    LinuxCommand(
        "visudo", CmdCategory.PERM_USER, "安全编辑/etc/sudoers文件",
        "visudo",
        listOf(
            CmdExample("编辑sudo权限配置", "sudo visudo")
        ), "🔐", "会自动检查语法错误，比直接编辑更安全"
    ),
    LinuxCommand(
        "vmstat", CmdCategory.PROC_MGMT, "报告虚拟内存、进程、CPU统计信息",
        "vmstat [间隔] [次数]",
        listOf(
            CmdExample("查看系统状态", "vmstat"),
            CmdExample("每2秒刷新5次", "vmstat 2 5"),
            CmdExample("只看内存统计摘要", "vmstat -s"),
            CmdExample("每秒采样（5 次）", "vmstat 1 5")
        ), "⚙️"
    ),
    LinuxCommand(
        "w", CmdCategory.SYS_INFO, "显示当前登录用户及其正在做的事",
        "w",
        listOf(
            CmdExample("查看在线用户活动", "w")
        ), "🔧"
    ),
    LinuxCommand(
        "wait", CmdCategory.PROC_MGMT, "等待后台任务完成（shell内建）",
        "wait [PID]",
        listOf(
            CmdExample("等待所有后台任务", "wait"),
            CmdExample("等待指定PID", "wait 1234")
        ), "⚙️"
    ),
    LinuxCommand(
        "watch", CmdCategory.PROC_MGMT, "周期性执行命令并实时显示结果",
        "watch [选项] 命令",
        listOf(
            CmdExample("每2秒刷新一次", "watch -n 2 df -h"),
            CmdExample("高亮变化部分", "watch -d free -m"),
            CmdExample("每秒刷新磁盘使用", "watch -n 1 df -h"),
            CmdExample("高亮变化部分", "watch -d 'ls -l'"),
            CmdExample("每 5 秒查看连接统计", "watch -n 5 \"ss -s\"")
        ), "⚙️"
    ),
    LinuxCommand(
        "whereis", CmdCategory.FILE_MGMT, "查找命令的二进制、源码及手册位置",
        "whereis 命令",
        listOf(
            CmdExample("查找命令位置", "whereis python3"),
            CmdExample("只查找二进制文件位置", "whereis -b python3")
        ), "📁"
    ),
    LinuxCommand(
        "whoami", CmdCategory.PERM_USER, "显示当前有效用户名",
        "whoami",
        listOf(
            CmdExample("查看当前用户", "whoami")
        ), "🔐"
    ),
    LinuxCommand(
        "whois", CmdCategory.NETWORK, "查询域名或IP的注册信息",
        "whois 域名/IP",
        listOf(
            CmdExample("查询域名信息", "whois example.com"),
            CmdExample("查询IP地址的归属信息", "whois 8.8.8.8"),
            CmdExample("查看域名注册信息", "whois example.com | head -30")
        ), "🌐"
    ),
    LinuxCommand(
        "yes", CmdCategory.SYS_INFO, "无限重复输出字符串直至中断",
        "yes [字符串]",
        listOf(
            CmdExample("持续输出y", "yes | rm -i *"),
            CmdExample("持续输出指定字符串", "yes hello")
        ), "🔧"
    ),
    LinuxCommand(
        "zcat", CmdCategory.FILE_VIEW, "查看gzip压缩文件内容而无需解压",
        "zcat 文件.gz",
        listOf(
            CmdExample("查看压缩文件内容", "zcat file.txt.gz"),
            CmdExample("合并多个压缩日志后统计行数", "zcat *.gz | wc -l"),
            CmdExample("不解压直接查看", "zcat file.gz | head")
        ), "📄"
    ),
    LinuxCommand(
        "zcmp", CmdCategory.FILE_VIEW, "比较两个gzip压缩文件",
        "zcmp 文件1.gz 文件2.gz",
        listOf(
            CmdExample("比较压缩文件", "zcmp file1.gz file2.gz")
        ), "📄"
    ),
    LinuxCommand(
        "zdiff", CmdCategory.FILE_VIEW, "显示两个gzip压缩文件的差异",
        "zdiff 文件1.gz 文件2.gz",
        listOf(
            CmdExample("查看压缩文件差异", "zdiff file1.gz file2.gz")
        ), "📄"
    ),
    LinuxCommand(
        "zegrep", CmdCategory.TEXT_PROC, "在gzip压缩文件中用扩展正则搜索",
        "zegrep 模式 文件.gz",
        listOf(
            CmdExample("压缩文件中搜索", "zegrep 'error|warn' log.gz")
        ), "✏️"
    ),
    LinuxCommand(
        "zfgrep", CmdCategory.TEXT_PROC, "在gzip压缩文件中做固定字符串搜索",
        "zfgrep 字符串 文件.gz",
        listOf(
            CmdExample("压缩文件中搜索固定字符串", "zfgrep 'ERROR' log.gz")
        ), "✏️"
    ),
    LinuxCommand(
        "zforce", CmdCategory.COMPRESS, "强制为gzip文件添加.gz后缀",
        "zforce 文件",
        listOf(
            CmdExample("强制添加后缀", "zforce file")
        ), "🗜️"
    ),
    LinuxCommand(
        "zgrep", CmdCategory.TEXT_PROC, "在gzip压缩文件中搜索文本",
        "zgrep 模式 文件.gz",
        listOf(
            CmdExample("压缩文件中搜索", "zgrep 'error' log.gz"),
            CmdExample("忽略大小写搜索压缩文件", "zgrep -i 'error' log.gz")
        ), "✏️"
    ),
    LinuxCommand(
        "zmore", CmdCategory.FILE_VIEW, "分页查看gzip压缩文件内容",
        "zmore 文件.gz",
        listOf(
            CmdExample("分页查看压缩文件", "zmore file.txt.gz")
        ), "📄"
    ),
    LinuxCommand(
        "znew", CmdCategory.COMPRESS, "将.Z文件转换为.gz格式",
        "znew 文件.Z",
        listOf(
            CmdExample("转换压缩格式", "znew file.Z")
        ), "🗜️"
    ),
    LinuxCommand(
        "zstd", CmdCategory.COMPRESS, "Facebook推出的高速压缩算法工具",
        "zstd [选项] 文件",
        listOf(
            CmdExample("压缩文件", "zstd file.txt"),
            CmdExample("解压文件", "zstd -d file.txt.zst"),
            CmdExample("高压缩比模式", "zstd -19 file.txt")
        ), "🗜️"
    ),
    LinuxCommand(
        "stty", CmdCategory.SYS_INFO, "查看或设置终端行参数",
        "stty [选项]",
        listOf(
            CmdExample("查看终端设置", "stty -a"),
            CmdExample("恢复默认设置", "stty sane")
        ), "🔧"
    ),
    LinuxCommand(
        "fzf", CmdCategory.DEV_TOOLS, "命令行模糊搜索神器，可交互过滤任意列表",
        "fzf [选项]",
        listOf(
            CmdExample("交互式搜索文件", "find . -type f | fzf"),
            CmdExample("结合cd快速跳转目录", "cd \\$(find . -type d | fzf)"),
            CmdExample("搜索历史命令", "history | fzf")
        ), "🔨", "常与 Ctrl+R（搜历史）、Ctrl+T（搜文件）快捷键配合使用"
    ),
    LinuxCommand(
        "rg", CmdCategory.TEXT_PROC, "ripgrep，比grep快得多的递归文本搜索工具",
        "rg [选项] 模式 [路径]",
        listOf(
            CmdExample("递归搜索关键词", "rg \"TODO\""),
            CmdExample("忽略大小写搜索", "rg -i \"error\""),
            CmdExample("只搜索指定类型文件", "rg -t py \"def main\"")
        ), "✏️", "自动跳过.gitignore中的文件，速度远超传统grep"
    ),
    LinuxCommand(
        "fd", CmdCategory.FILE_MGMT, "find的现代替代品，语法更简单速度更快",
        "fd [模式] [路径]",
        listOf(
            CmdExample("按名称搜索文件", "fd config"),
            CmdExample("按扩展名搜索", "fd -e txt"),
            CmdExample("搜索并执行命令", "fd -e log -x rm")
        ), "📁"
    ),
    LinuxCommand(
        "bat", CmdCategory.FILE_VIEW, "带语法高亮和行号的cat替代品",
        "bat [选项] 文件",
        listOf(
            CmdExample("查看文件（带高亮）", "bat file.py"),
            CmdExample("显示行号", "bat -n file.txt"),
            CmdExample("直接当cat用", "bat --plain file.txt")
        ), "📄"
    ),
    LinuxCommand(
        "eza", CmdCategory.FILE_MGMT, "现代化的ls替代品，支持图标和git状态",
        "eza [选项] [路径]",
        listOf(
            CmdExample("列出文件（带图标）", "eza --icons"),
            CmdExample("树状显示", "eza -T"),
            CmdExample("详细列表带git状态", "eza -l --git")
        ), "📁"
    ),
    LinuxCommand(
        "exa", CmdCategory.FILE_MGMT, "现代化ls替代品（eza的前身，部分系统仍在用）",
        "exa [选项] [路径]",
        listOf(
            CmdExample("列出文件（带图标）", "exa --icons"),
            CmdExample("树状显示", "exa -T"),
            CmdExample("按git状态显示文件", "exa -l --git")
        ), "📁"
    ),
    LinuxCommand(
        "zoxide", CmdCategory.FILE_MGMT, "智能记忆常用路径的cd替代工具",
        "z 关键词",
        listOf(
            CmdExample("跳转到最常访问的匹配目录", "z proj"),
            CmdExample("查看访问频率排名", "zoxide query -l"),
            CmdExample("交互式选择匹配的目录", "zi")
        ), "📁", "需先执行 cd 若干次建立访问记录"
    ),
    LinuxCommand(
        "jq", CmdCategory.DEV_TOOLS, "命令行JSON处理器，过滤、格式化、提取字段",
        "jq [选项] 过滤器 [文件]",
        listOf(
            CmdExample("格式化JSON", "cat data.json | jq ."),
            CmdExample("提取字段", "jq '.name' data.json"),
            CmdExample("过滤数组元素", "jq '.[] | select(.age>18)' data.json"),
            CmdExample("格式化 JSON", "jq . data.json"),
            CmdExample("提取数组中每项的字段", "jq -r '.items[].id' data.json"),
            CmdExample("条件筛选", "jq '.[] | select(.age > 18)' data.json"),
            CmdExample("请求接口并取值", "curl -s https://api.example.com/data | jq '.data'")
        ), "🔨"
    ),
    LinuxCommand(
        "yq", CmdCategory.DEV_TOOLS, "命令行YAML处理器，用法类似jq",
        "yq [选项] 表达式 文件",
        listOf(
            CmdExample("查看YAML内容", "yq . config.yaml"),
            CmdExample("提取字段", "yq '.spec.replicas' deploy.yaml"),
            CmdExample("转换为JSON", "yq -o=json config.yaml")
        ), "🔨"
    ),
    LinuxCommand(
        "httpie", CmdCategory.NETWORK, "比curl更友好的命令行HTTP客户端",
        "http [选项] 方法 URL",
        listOf(
            CmdExample("发送GET请求", "http GET example.com/api"),
            CmdExample("发送JSON POST请求", "http POST example.com/api name=Tom"),
            CmdExample("查看响应头", "http --headers example.com")
        ), "🌐"
    ),
    LinuxCommand(
        "iftop", CmdCategory.NETWORK, "实时监控网络流量占用情况",
        "iftop [选项]",
        listOf(
            CmdExample("查看实时网络流量", "sudo iftop"),
            CmdExample("指定网卡监控", "sudo iftop -i eth0")
        ), "🌐"
    ),
    LinuxCommand(
        "speedtest-cli", CmdCategory.NETWORK, "命令行网速测试工具",
        "speedtest-cli [选项]",
        listOf(
            CmdExample("测试网速", "speedtest-cli"),
            CmdExample("简洁输出结果", "speedtest-cli --simple"),
            CmdExample("指定测速服务器ID", "speedtest-cli --server 1234")
        ), "🌐"
    ),
    LinuxCommand(
        "nvm", CmdCategory.DEV_TOOLS, "Node.js版本管理器",
        "nvm [子命令] [版本]",
        listOf(
            CmdExample("安装指定版本", "nvm install 20"),
            CmdExample("切换版本", "nvm use 20"),
            CmdExample("查看已安装版本", "nvm list")
        ), "🔨"
    ),
    LinuxCommand(
        "pyenv", CmdCategory.DEV_TOOLS, "Python版本管理器",
        "pyenv [子命令] [版本]",
        listOf(
            CmdExample("安装指定版本", "pyenv install 3.12.0"),
            CmdExample("设置全局版本", "pyenv global 3.12.0"),
            CmdExample("查看已安装版本", "pyenv versions")
        ), "🔨"
    ),
    LinuxCommand(
        "rbenv", CmdCategory.DEV_TOOLS, "Ruby版本管理器",
        "rbenv [子命令] [版本]",
        listOf(
            CmdExample("安装指定版本", "rbenv install 3.3.0"),
            CmdExample("设置全局版本", "rbenv global 3.3.0"),
            CmdExample("查看已安装版本", "rbenv versions")
        ), "🔨"
    ),
    LinuxCommand(
        "docker-compose", CmdCategory.DEV_TOOLS, "定义和管理多容器Docker应用",
        "docker-compose [子命令]",
        listOf(
            CmdExample("启动所有服务", "docker-compose up -d"),
            CmdExample("停止所有服务", "docker-compose down"),
            CmdExample("查看日志", "docker-compose logs -f")
        ), "🔨"
    ),
    LinuxCommand(
        "kubectl", CmdCategory.DEV_TOOLS, "Kubernetes集群命令行管理工具",
        "kubectl [命令] [资源]",
        listOf(
            CmdExample("查看所有Pod", "kubectl get pods"),
            CmdExample("查看Pod日志", "kubectl logs pod名"),
            CmdExample("进入容器内部", "kubectl exec -it pod名 -- bash"),
            CmdExample("应用配置文件", "kubectl apply -f deploy.yaml"),
            CmdExample("查看所有命名空间的 Pod", "kubectl get pods -A"),
            CmdExample("查看 Pod 详细信息", "kubectl get pods -o wide"),
            CmdExample("实时查看日志", "kubectl logs -f pod-name"),
            CmdExample("进入 Pod", "kubectl exec -it pod-name -- sh"),
            CmdExample("滚动重启 Deployment", "kubectl rollout restart deployment/app"),
            CmdExample("查看当前上下文", "kubectl config get-contexts"),
            CmdExample("滚动重启", "kubectl rollout restart deployment/myapp")
        ), "🔨"
    ),
    LinuxCommand(
        "gpg", CmdCategory.PERM_USER, "GNU隐私卫士，加密与数字签名工具",
        "gpg [选项]",
        listOf(
            CmdExample("生成密钥对", "gpg --gen-key"),
            CmdExample("加密文件", "gpg -e -r 邮箱 file.txt"),
            CmdExample("解密文件", "gpg -d file.txt.gpg"),
            CmdExample("导出公钥", "gpg --export -a 邮箱 > pub.key")
        ), "🔐"
    ),
    LinuxCommand(
        "openssl", CmdCategory.PERM_USER, "加密算法与SSL/TLS证书工具集",
        "openssl 子命令 [选项]",
        listOf(
            CmdExample("生成自签名证书", "openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem"),
            CmdExample("查看证书信息", "openssl x509 -in cert.pem -text -noout"),
            CmdExample("计算文件哈希", "openssl sha256 file.txt"),
            CmdExample("生成随机十六进制字符串", "openssl rand -hex 16"),
            CmdExample("测试 HTTPS 站点证书", "openssl s_client -connect example.com:443 -servername example.com"),
            CmdExample("测试 HTTPS 握手", "openssl s_client -connect example.com:443")
        ), "🔐"
    ),
    LinuxCommand(
        "fail2ban-client", CmdCategory.PERM_USER, "查看和控制fail2ban入侵防护服务",
        "fail2ban-client [命令]",
        listOf(
            CmdExample("查看运行状态", "sudo fail2ban-client status"),
            CmdExample("查看指定监狱状态", "sudo fail2ban-client status sshd"),
            CmdExample("解封IP", "sudo fail2ban-client set sshd unbanip 1.2.3.4")
        ), "🔐"
    ),
    LinuxCommand(
        "ncdu", CmdCategory.SYS_INFO, "交互式磁盘占用分析工具",
        "ncdu [路径]",
        listOf(
            CmdExample("分析当前目录占用", "ncdu"),
            CmdExample("分析指定目录", "ncdu /var/log")
        ), "🔧", "比 du -h 更直观，可交互式钻取查看大文件"
    ),
    LinuxCommand(
        "glances", CmdCategory.PROC_MGMT, "跨平台综合系统监控工具",
        "glances [选项]",
        listOf(
            CmdExample("启动监控面板", "glances"),
            CmdExample("以Web模式运行", "glances -w")
        ), "⚙️"
    ),
    LinuxCommand(
        "dstat", CmdCategory.PROC_MGMT, "实时系统资源统计（CPU/磁盘/网络综合版）",
        "dstat [选项]",
        listOf(
            CmdExample("查看综合资源统计", "dstat"),
            CmdExample("只看CPU和内存", "dstat -c -m")
        ), "⚙️"
    ),
    LinuxCommand(
        "tldr", CmdCategory.DEV_TOOLS, "精简版命令示例手册，比man更快上手",
        "tldr 命令名",
        listOf(
            CmdExample("查看命令速查示例", "tldr tar"),
            CmdExample("更新本地缓存", "tldr --update")
        ), "🔨"
    ),
    LinuxCommand(
        "asciinema", CmdCategory.DEV_TOOLS, "录制并分享终端会话操作过程",
        "asciinema [子命令]",
        listOf(
            CmdExample("开始录制", "asciinema rec demo.cast"),
            CmdExample("回放录制内容", "asciinema play demo.cast")
        ), "🔨"
    ),
    LinuxCommand(
        "shutdown", CmdCategory.SYS_INFO, "关闭或重启系统",
        "shutdown [选项] [时间]",
        listOf(
            CmdExample("立即关机", "sudo shutdown -h now"),
            CmdExample("立即重启", "sudo shutdown -r now"),
            CmdExample("10分钟后关机", "sudo shutdown -h +10"),
            CmdExample("取消已计划的关机", "sudo shutdown -c")
        ), "🔧", "⚠️ 会中断所有正在运行的进程，建议提前通知其他用户"
    ),
    LinuxCommand(
        "ufw", CmdCategory.NETWORK, "简化版防火墙管理工具（Uncomplicated Firewall）",
        "ufw [选项]",
        listOf(
            CmdExample("启用防火墙", "sudo ufw enable"),
            CmdExample("查看当前规则状态", "sudo ufw status"),
            CmdExample("允许某端口", "sudo ufw allow 22"),
            CmdExample("拒绝某端口", "sudo ufw deny 80"),
            CmdExample("删除一条规则", "sudo ufw delete allow 22"),
            CmdExample("查看详细状态", "sudo ufw status verbose"),
            CmdExample("放行端口", "sudo ufw allow 22/tcp"),
            CmdExample("只允许指定 IP 访问 SSH", "sudo ufw allow from 1.2.3.4 to any port 22"),
            CmdExample("删除规则", "sudo ufw delete allow 80"),
            CmdExample("只允许某 IP 访问 SSH", "sudo ufw allow from 192.168.1.10 to any port 22")
        ), "🌐", "Debian/Ubuntu 系发行版常用，比直接写 iptables 规则简单很多"
    ),
    LinuxCommand(
        "base64", CmdCategory.TEXT_PROC, "对数据进行 Base64 编码或解码",
        "base64 [选项] [文件]",
        listOf(
            CmdExample("编码文件内容", "base64 file.txt"),
            CmdExample("编码字符串", "echo -n 'hello' | base64"),
            CmdExample("解码", "echo 'aGVsbG8=' | base64 -d"),
            CmdExample("编码文件", "base64 file.bin > file.b64")
        ), "✏️"
    ),
    LinuxCommand(
        "type", CmdCategory.SYS_INFO, "显示命令的类型（内建命令/别名/可执行文件路径）",
        "type 命令名",
        listOf(
            CmdExample("查看命令类型", "type ls"),
            CmdExample("查看所有匹配（包括同名别名/函数）", "type -a python")
        ), "🔧", "Shell 内建命令，比 which 更全面，能识别别名和函数"
    ),
    LinuxCommand(
        "ssh-keygen", CmdCategory.NETWORK, "生成、管理和转换 SSH 认证密钥",
        "ssh-keygen [选项]",
        listOf(
            CmdExample("生成默认密钥对（RSA）", "ssh-keygen"),
            CmdExample("生成 Ed25519 密钥对（推荐）", "ssh-keygen -t ed25519 -C \"注释\""),
            CmdExample("指定文件名生成", "ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa_work"),
            CmdExample("查看公钥指纹", "ssh-keygen -lf ~/.ssh/id_rsa.pub"),
            CmdExample("生成 ed25519 密钥", "ssh-keygen -t ed25519 -C \"you@example.com\""),
            CmdExample("把公钥复制到服务器", "ssh-copy-id user@host")
        ), "🔐", "生成后用 ssh-copy-id 把公钥推送到服务器即可免密登录"
    ),
    LinuxCommand(
        "ulimit", CmdCategory.PROC_MGMT, "查看或设置当前 Shell 的资源使用限制",
        "ulimit [选项] [值]",
        listOf(
            CmdExample("查看所有限制", "ulimit -a"),
            CmdExample("查看最大文件描述符数", "ulimit -n"),
            CmdExample("临时调大文件描述符上限", "ulimit -n 65535"),
            CmdExample("查看最大进程数", "ulimit -u")
        ), "⚙️", "Shell 内建命令，只在当前会话生效；永久生效需改 /etc/security/limits.conf"
    ),
    LinuxCommand(
        "npm", CmdCategory.DEV_TOOLS, "Node.js 包管理工具",
        "npm [子命令]",
        listOf(
            CmdExample("初始化项目", "npm init -y"),
            CmdExample("安装依赖", "npm install"),
            CmdExample("安装并写入 package.json", "npm install express --save"),
            CmdExample("全局安装", "npm install -g typescript"),
            CmdExample("运行 package.json 中的脚本", "npm run build"),
            CmdExample("安装开发依赖", "npm install -D typescript"),
            CmdExample("查看全局已装包", "npm ls -g --depth=0"),
            CmdExample("查看可更新的包", "npm outdated")
        ), "🔨"
    ),
    LinuxCommand(
        "sar", CmdCategory.DEBUG_TOOLS, "收集、报告并保存系统活动信息（CPU/内存/IO等历史数据）",
        "sar [选项] [间隔] [次数]",
        listOf(
            CmdExample("查看CPU使用率，每2秒一次共3次", "sar -u 2 3"),
            CmdExample("查看内存使用情况", "sar -r"),
            CmdExample("查看磁盘IO", "sar -d"),
            CmdExample("CPU 使用率（每秒 5 次）", "sar -u 1 5"),
            CmdExample("内存使用率", "sar -r 1 5"),
            CmdExample("网络流量", "sar -n DEV 1 5")
        ), "🐛", "需先安装 sysstat 包；比 top 更适合看历史趋势"
    ),
    LinuxCommand(
        "disown", CmdCategory.PROC_MGMT, "将后台任务从当前 Shell 的任务列表中移除",
        "disown [任务号]",
        listOf(
            CmdExample("移除最近一个后台任务", "disown"),
            CmdExample("移除指定任务", "disown %1"),
            CmdExample("移除所有后台任务", "disown -a")
        ), "⚙️", "配合 nohup 使用可让进程在关闭终端后继续运行且不受 SIGHUP 影响"
    ),
    LinuxCommand(
        "adduser", CmdCategory.PERM_USER, "交互式创建新用户（比 useradd 更友好，Debian/Ubuntu 常用）",
        "adduser 用户名",
        listOf(
            CmdExample("交互式创建用户", "sudo adduser alice"),
            CmdExample("创建用户并加入指定组", "sudo adduser bob developers")
        ), "🔐", "会自动创建家目录、提示设置密码，比 useradd 少了很多参数要记"
    ),

    LinuxCommand(
        "vi", CmdCategory.TEXT_PROC, "经典全屏幕文本编辑器（Linux 上通常是 vim 的软链接）",
        "vi [文件名]",
        listOf(
            CmdExample("打开/新建文件", "vi file.txt"),
            CmdExample("进入编辑模式后输入内容，按Esc返回命令模式", "i"),
            CmdExample("命令模式下保存并退出", ":wq"),
            CmdExample("不保存直接退出", ":q!"),
            CmdExample("打开并跳到第 10 行", "vi +10 file.txt"),
            CmdExample("打开并跳到匹配处", "vi +/pattern file.txt"),
            CmdExample("只读方式打开", "vi -R file.txt"),
            CmdExample("对比两个文件", "vi -d a.txt b.txt")
        ), "✏️", "i 进入插入模式，Esc 返回命令模式，:wq 保存退出，:q! 强制不保存退出"
    ),
    LinuxCommand(
        "nano", CmdCategory.TEXT_PROC, "简单易用的终端文本编辑器，比 vi 更适合新手",
        "nano [选项] [文件名]",
        listOf(
            CmdExample("打开/新建文件", "nano file.txt"),
            CmdExample("跳转到指定行", "nano +50 file.txt"),
            CmdExample("显示行号", "nano -l file.txt")
        ), "✏️", "底部一直显示常用快捷键提示，新手友好"
    ),
    LinuxCommand(
        "ssh-agent", CmdCategory.NETWORK, "管理 SSH 私钥的后台程序，避免每次连接都要输入密钥密码",
        "ssh-agent [命令]",
        listOf(
            CmdExample("启动ssh-agent并让当前shell使用它", "eval $(ssh-agent)"),
            CmdExample("在子shell中运行ssh-agent并执行命令", "ssh-agent bash")
        ), "🔐", "配合 ssh-add 使用：先启动 agent，再把密钥加进去"
    ),
    LinuxCommand(
        "ssh-add", CmdCategory.NETWORK, "把私钥添加到 ssh-agent 的缓存中，之后连接不用重复输密码",
        "ssh-add [选项] [私钥文件]",
        listOf(
            CmdExample("把默认私钥添加到ssh-agent", "ssh-add ~/.ssh/id_rsa"),
            CmdExample("查看已添加的密钥列表", "ssh-add -l"),
            CmdExample("从ssh-agent中移除某个密钥", "ssh-add -d ~/.ssh/id_rsa"),
            CmdExample("移除所有已添加的密钥", "ssh-add -D")
        ), "🔐"
    ),
    LinuxCommand(
        "ssh-copy-id", CmdCategory.NETWORK, "把本机的 SSH 公钥拷贝到远程主机，实现免密登录",
        "ssh-copy-id [选项] [用户名@]主机",
        listOf(
            CmdExample("把公钥安装到远程主机", "ssh-copy-id user@server"),
            CmdExample("指定端口", "ssh-copy-id -p 2222 user@server"),
            CmdExample("指定要使用的公钥文件", "ssh-copy-id -i ~/.ssh/id_ed25519.pub user@server")
        ), "🔐"
    ),
    LinuxCommand(
        "ssh-keyscan", CmdCategory.NETWORK, "批量收集远程主机的 SSH 公钥指纹",
        "ssh-keyscan [选项] 主机",
        listOf(
            CmdExample("收集某主机的公钥", "ssh-keyscan example.com"),
            CmdExample("写入known_hosts文件", "ssh-keyscan example.com >> ~/.ssh/known_hosts")
        ), "🔐"
    ),
    LinuxCommand(
        "sshd", CmdCategory.NETWORK, "OpenSSH 的服务端守护进程，负责接受 SSH 连接请求",
        "sshd [选项]",
        listOf(
            CmdExample("检查配置文件语法是否正确", "sshd -t"),
            CmdExample("以调试模式在前台运行（不作为daemon）", "sshd -d")
        ), "🔐", "配置文件通常在 /etc/ssh/sshd_config，改完记得 sshd -t 校验语法"
    ),
    LinuxCommand(
        "sshpass", CmdCategory.NETWORK, "免交互式提供 SSH 密码，方便脚本自动化（生产环境不建议明文密码）",
        "sshpass [选项] 命令",
        listOf(
            CmdExample("明文传密码（仅测试环境用）", "sshpass -p 'mypass' ssh user@host"),
            CmdExample("从文件读取密码", "sshpass -f password.txt ssh user@host"),
            CmdExample("从环境变量SSHPASS读取密码", "sshpass -e ssh user@host")
        ), "🔐", "⚠️ 生产服务器不建议用明文密码方式，优先用密钥登录"
    ),
    LinuxCommand(
        "nmap", CmdCategory.NETWORK, "开源的网络探测和安全审核工具，用于扫描主机、端口和服务",
        "nmap [选项] [目标]",
        listOf(
            CmdExample("扫描目标主机的开放端口", "nmap scanme.nmap.org"),
            CmdExample("探测服务版本信息", "nmap -sV example.com"),
            CmdExample("扫描指定端口范围", "nmap -p 1-1000 example.com"),
            CmdExample("ping扫描，只探测主机是否存活", "nmap -sP 192.168.1.0/24"),
            CmdExample("扫描主机开放端口及服务版本", "nmap -sV 192.168.1.10"),
            CmdExample("发现局域网内存活主机", "nmap -sn 192.168.1.0/24")
        ), "🌐", "扫描非自己所有的网络前请确认已获得授权，未授权扫描可能违反当地法律"
    ),
    LinuxCommand(
        "iperf", CmdCategory.NETWORK, "网络性能测试工具，测量两台主机之间的最大带宽",
        "iperf [选项]",
        listOf(
            CmdExample("在服务器端监听", "iperf -s"),
            CmdExample("在客户端测试到服务器的带宽", "iperf -c 192.168.1.1"),
            CmdExample("使用UDP模式测试，指定带宽", "iperf -u -c 192.168.1.1 -b 100M -t 60")
        ), "🌐"
    ),
    LinuxCommand(
        "ipcalc", CmdCategory.NETWORK, "IP 地址计算器，快速算出子网掩码、广播地址、可用主机数等",
        "ipcalc [选项] IP地址[/掩码位数]",
        listOf(
            CmdExample("计算一个网段的详细信息", "ipcalc 192.168.1.0/24"),
            CmdExample("查看某IP在指定掩码下的信息", "ipcalc 10.0.0.5 255.255.255.0")
        ), "🌐"
    ),
    LinuxCommand(
        "fping", CmdCategory.NETWORK, "比 ping 更高效的批量主机存活探测工具，能并行 ping 多个目标",
        "fping [选项] [主机...]",
        listOf(
            CmdExample("同时探测多个IP", "fping 192.168.0.1 192.168.0.2 192.168.0.3"),
            CmdExample("扫描整个网段", "fping -g 192.168.0.0/24")
        ), "🌐"
    ),
    LinuxCommand(
        "ethtool", CmdCategory.NETWORK, "查看和修改以太网网卡的配置信息（速率、双工模式、驱动等）",
        "ethtool [选项] 网卡名",
        listOf(
            CmdExample("查看网卡速度和链路状态", "ethtool eth0"),
            CmdExample("查看网卡使用的驱动信息", "ethtool -i eth0"),
            CmdExample("查看网卡统计信息", "ethtool -S eth0")
        ), "🌐"
    ),
    LinuxCommand(
        "arping", CmdCategory.NETWORK, "用 ARP 协议探测局域网内某个 IP 是否被占用/存活",
        "arping [选项] IP地址",
        listOf(
            CmdExample("探测某IP是否在线", "arping 192.168.1.1"),
            CmdExample("指定网卡探测", "arping -I eth0 192.168.1.1")
        ), "🌐"
    ),
    LinuxCommand(
        "atop", CmdCategory.DEBUG_TOOLS, "系统资源监控工具，能记录CPU、内存、磁盘、网络的历史使用情况",
        "atop [选项] [间隔] [次数]",
        listOf(
            CmdExample("启动实时监控", "atop"),
            CmdExample("每5秒刷新一次", "atop 5"),
            CmdExample("从历史日志回放某天的记录", "atop -r /var/log/atop/atop_20260101")
        ), "🐛", "跟 top 的区别是 atop 默认会把数据记录到日志，方便事后排查问题"
    ),
    LinuxCommand(
        "mpstat", CmdCategory.DEBUG_TOOLS, "查看多核 CPU 环境下，每个 CPU 核心的使用率",
        "mpstat [选项] [间隔] [次数]",
        listOf(
            CmdExample("查看系统启动以来的平均CPU使用情况", "mpstat"),
            CmdExample("每2秒输出一次，共3次，查看所有CPU核心", "mpstat -P ALL 2 3")
        ), "🐛"
    ),
    LinuxCommand(
        "pidstat", CmdCategory.DEBUG_TOOLS, "按进程维度监控 CPU、内存、IO 等资源使用情况",
        "pidstat [选项] [间隔] [次数]",
        listOf(
            CmdExample("查看所有进程的CPU使用情况", "pidstat -u -p ALL"),
            CmdExample("查看某个进程的内存使用情况", "pidstat -r -p 1234")
        ), "🐛"
    ),
    LinuxCommand(
        "lsb_release", CmdCategory.SYS_INFO, "显示当前 Linux 发行版及版本相关信息",
        "lsb_release [选项]",
        listOf(
            CmdExample("显示完整的发行版信息", "lsb_release -a"),
            CmdExample("只显示发行版描述", "lsb_release -d")
        ), "🔧"
    ),
    LinuxCommand(
        "hostnamectl", CmdCategory.SYS_INFO, "查询和修改系统主机名（systemd 系统专用）",
        "hostnamectl [选项] [指令]",
        listOf(
            CmdExample("查看当前主机名及系统信息", "hostnamectl status"),
            CmdExample("永久修改主机名（不用重启）", "sudo hostnamectl set-hostname newname"),
            CmdExample("修改主机名", "sudo hostnamectl set-hostname myserver")
        ), "🔧"
    ),
    LinuxCommand(
        "timedatectl", CmdCategory.SYS_INFO, "查询和设置系统日期、时间、时区（systemd 系统专用）",
        "timedatectl [选项] [指令]",
        listOf(
            CmdExample("查看当前时间和时区设置", "timedatectl status"),
            CmdExample("设置系统时区", "sudo timedatectl set-timezone Asia/Shanghai"),
            CmdExample("开启NTP自动时间同步", "sudo timedatectl set-ntp true"),
            CmdExample("搜索可用时区", "timedatectl list-timezones | grep Asia")
        ), "🔧"
    ),
    LinuxCommand(
        "firewall-cmd", CmdCategory.NETWORK, "firewalld 防火墙的命令行管理工具（CentOS/RHEL 7+ 默认防火墙）",
        "firewall-cmd [选项]",
        listOf(
            CmdExample("查看firewalld版本", "firewall-cmd --version"),
            CmdExample("永久开放443端口", "sudo firewall-cmd --permanent --add-port=443/tcp"),
            CmdExample("让规则立即生效", "sudo firewall-cmd --reload"),
            CmdExample("查看已开放的端口和服务", "firewall-cmd --list-all")
        ), "🌐", "支持不重启服务动态更新规则；改动记得加 --permanent 并 --reload"
    ),
    LinuxCommand(
        "chage", CmdCategory.PERM_USER, "查看或修改用户密码的有效期限",
        "chage [选项] 用户名",
        listOf(
            CmdExample("查看某用户的密码策略信息", "chage -l root"),
            CmdExample("设置密码90天后过期", "sudo chage -M 90 username"),
            CmdExample("强制用户下次登录时必须改密码", "sudo chage -d 0 username")
        ), "🔐"
    ),
    LinuxCommand(
        "chpasswd", CmdCategory.PERM_USER, "批量修改用户密码，从标准输入读取 用户名:密码 格式",
        "chpasswd [选项]",
        listOf(
            CmdExample("从文件批量导入用户名密码修改密码", "chpasswd < user.txt"),
            CmdExample("配合echo快速改单个用户密码", "echo 'alice:newpass123' | chpasswd")
        ), "🔐", "输入文件格式是 用户名:密码，一行一个"
    ),
    LinuxCommand(
        "lastlog", CmdCategory.PERM_USER, "显示系统中所有用户最近一次登录的时间和来源",
        "lastlog [选项]",
        listOf(
            CmdExample("查看所有用户的最近登录记录", "lastlog"),
            CmdExample("只查看指定用户", "lastlog -u alice")
        ), "🔐"
    ),
    LinuxCommand(
        "gpasswd", CmdCategory.PERM_USER, "管理用户组的密码、管理员和成员关系",
        "gpasswd [选项] 组名",
        listOf(
            CmdExample("把用户添加到某个组", "sudo gpasswd -a alice developers"),
            CmdExample("把用户从某个组移除", "sudo gpasswd -d alice developers"),
            CmdExample("设置某用户为组管理员", "sudo gpasswd -A alice developers")
        ), "🔐"
    ),
    LinuxCommand(
        "dpkg-query", CmdCategory.PACKAGE, "查询 dpkg 软件包数据库，查找文件归属、已装软件列表等",
        "dpkg-query [选项] [软件包名]",
        listOf(
            CmdExample("查找某个文件是哪个包安装的", "dpkg-query -S /usr/bin/vim"),
            CmdExample("列出所有已安装的软件包", "dpkg-query -l"),
            CmdExample("查看某软件包的详细信息", "dpkg-query -s vim")
        ), "📦"
    ),
    LinuxCommand(
        "pigz", CmdCategory.COMPRESS, "多线程并行版的 gzip，压缩解压速度比 gzip 快很多",
        "pigz [选项] [文件...]",
        listOf(
            CmdExample("用8个线程压缩", "pigz -p 8 file.txt"),
            CmdExample("配合tar打包并并行压缩", "tar -cvf - dir/ | pigz -p 8 > output.tgz"),
            CmdExample("解压", "pigz -d output.gz")
        ), "🗜️", "生成的 .gz 文件跟普通 gzip 完全兼容，只是压缩/解压过程更快"
    ),
    LinuxCommand(
        "unrar", CmdCategory.COMPRESS, "解压 RAR 格式压缩包",
        "unrar [命令] [压缩包] [文件...]",
        listOf(
            CmdExample("解压到当前目录（保留完整路径）", "unrar x archive.rar"),
            CmdExample("查看压缩包内容而不解压", "unrar l archive.rar"),
            CmdExample("解压到指定目录，不保留路径结构", "unrar e archive.rar /path/to/dest/")
        ), "🗜️"
    ),
    LinuxCommand(
        "mkisofs", CmdCategory.FILE_MGMT, "把指定目录/文件制作成 ISO 9660 格式的光盘镜像文件",
        "mkisofs [选项] -o 输出文件.iso 源目录",
        listOf(
            CmdExample("把目录制作成ISO镜像", "mkisofs -o output.iso /path/to/dir"),
            CmdExample("制作可引导的ISO镜像", "mkisofs -b isolinux.bin -c boot.cat -o output.iso /path/to/dir")
        ), "📁"
    ),
    LinuxCommand(
        "iconv", CmdCategory.TEXT_PROC, "转换文件的字符编码，比如 GBK 转 UTF-8",
        "iconv -f 原编码 -t 目标编码 文件",
        listOf(
            CmdExample("查看支持的所有编码", "iconv -l"),
            CmdExample("GBK转UTF-8并输出到新文件", "iconv -f GBK -t UTF-8 file.txt -o file_utf8.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "dos2unix", CmdCategory.TEXT_PROC, "把 Windows/DOS 格式的文本文件（CRLF 换行）转成 Unix 格式（LF 换行）",
        "dos2unix [选项] 文件",
        listOf(
            CmdExample("原地转换一个文件", "dos2unix file.txt"),
            CmdExample("转换多个文件", "dos2unix file1.txt file2.txt"),
            CmdExample("转换后另存为新文件，不改动原文件", "dos2unix -n oldfile.txt newfile.txt")
        ), "✏️"
    ),
    LinuxCommand(
        "pidof", CmdCategory.PROC_MGMT, "根据进程名查找对应的进程 ID",
        "pidof [选项] 进程名",
        listOf(
            CmdExample("查找nginx的所有进程ID", "pidof nginx"),
            CmdExample("只返回第一个匹配的PID", "pidof -s nginx")
        ), "⚙️"
    ),
    LinuxCommand(
        "ltrace", CmdCategory.DEV_TOOLS, "跟踪进程调用的动态库函数，用于调试库函数级别的问题",
        "ltrace [选项] 命令",
        listOf(
            CmdExample("跟踪程序运行时调用的库函数", "ltrace ./a.out"),
            CmdExample("同时显示每次调用的耗时", "ltrace -T ./a.out"),
            CmdExample("同时跟踪系统调用", "ltrace -S ./a.out")
        ), "🔨", "跟 strace 是一对：strace 跟踪系统调用，ltrace 跟踪库函数调用"
    ),
    LinuxCommand(
        "readelf", CmdCategory.DEV_TOOLS, "显示 ELF 格式可执行文件/目标文件/共享库的详细信息",
        "readelf [选项] 文件",
        listOf(
            CmdExample("查看ELF文件头信息", "readelf -h a.out"),
            CmdExample("查看所有段（section）信息", "readelf -S a.out"),
            CmdExample("查看符号表", "readelf -s a.out")
        ), "🔨"
    ),
    LinuxCommand(
        "perl", CmdCategory.DEV_TOOLS, "Perl 脚本语言的解释器",
        "perl [选项] 脚本文件",
        listOf(
            CmdExample("执行perl脚本", "perl script.pl"),
            CmdExample("直接执行一行perl代码", "perl -e 'print \"hello\\n\"'"),
            CmdExample("查看perl版本", "perl -v")
        ), "🔨"
    ),
    LinuxCommand(
        "pv", CmdCategory.FILE_MGMT, "在管道中显示数据处理进度（速度、剩余时间、百分比）",
        "pv [选项] [文件]",
        listOf(
            CmdExample("复制文件时显示进度条", "pv source.iso > dest.iso"),
            CmdExample("配合zip压缩显示打包进度", "pv /var/log/syslog | gzip > syslog.gz"),
            CmdExample("配合dd拷贝磁盘并显示进度", "pv /dev/sda | dd of=/dev/sdb bs=4M")
        ), "📁", "很多命令本身不显示进度（比如 cp、dd），中间接一道 pv 就能看到进度"
    ),
    LinuxCommand(
        "htpasswd", CmdCategory.NETWORK, "创建和管理 Apache/Nginx Basic Auth 用的密码文件",
        "htpasswd [选项] 密码文件 用户名",
        listOf(
            CmdExample("新建密码文件并添加第一个用户", "htpasswd -c .htpasswd alice"),
            CmdExample("往已有密码文件里加用户", "htpasswd .htpasswd bob"),
            CmdExample("命令行直接指定密码（不交互输入）", "htpasswd -b .htpasswd alice mypassword")
        ), "🌐"
    ),
    LinuxCommand(
        "runlevel", CmdCategory.SYS_INFO, "查看当前系统的运行级别（传统 SysV init 概念，systemd系统已弱化）",
        "runlevel",
        listOf(
            CmdExample("查看当前及上一个运行级别", "runlevel")
        ), "🔧", "现代 systemd 发行版已经用 target 取代了传统运行级别概念"
    ),
    LinuxCommand(
        "ab", CmdCategory.SYS_INFO, "ab命令是一个测试你 Apache http 服务器的工具，你可以通过这个工具，指定一个单位时间内向 apache 发出的请求数量来看看你的 Apache 和机器配合的性能如何。",
        "ab[-Aauth-username:password][-cconcurrency][-Ccookie-name=value][-d][-ecsv-file][-ggnuplot-file][-h][-Hcustom-header][-i",
        listOf(
            CmdExample("查看帮助信息", "ab --help")
        ), "📌"
    ),
    LinuxCommand(
        "accept", CmdCategory.SYS_INFO, "accept命令属于CUPS套件，用于指示打印系统接受发往指定目标打印机的打印任务。",
        "accept(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "accept --help")
        ), "📌"
    ),
    LinuxCommand(
        "ack", CmdCategory.TEXT_PROC, "专为程序员设计的代码搜索工具，类似 grep 但默认跳过版本控制目录等噪音",
        "ack [选项] 模式 [文件/目录]",
        listOf(
            CmdExample("在当前目录代码中搜索", "ack 'function main'"),
            CmdExample("只搜索Python文件", "ack --python 'def main'")
        ), "📌"
    ),
    LinuxCommand(
        "ag", CmdCategory.SYS_INFO, "列出当前目录下包含npm的文件",
        "ag[options]pattern[path...]ag[可选项]匹配模式[路径...]",
        listOf(
            CmdExample("列出当前目录下包含npm的文件", "➜  vue-project ag npm ./")
        ), "📌"
    ),
    LinuxCommand(
        "ansible", CmdCategory.SYS_INFO, "ansible命令是一款强大的自动化运维工具，基于Python开发，采用SSH协议进行远程管理，无需在被管理节点安装代理。Ansible可以实现自动化部署、配置管理、应用部署、任务编排等多种IT自动化需求。",
        "ansible[options]<host-pattern>ansible-playbook[options]<playbook-file>",
        listOf(
            CmdExample("查看帮助信息", "ansible --help")
        ), "📌"
    ),
    LinuxCommand(
        "apachectl", CmdCategory.SYS_INFO, "apachectl命令是Apache的Web服务器前端控制工具，用以启动、关闭和重新启动Web服务器进程。",
        "apachectl(参数)",
        listOf(
            CmdExample("查看帮助信息", "apachectl --help")
        ), "📌"
    ),
    LinuxCommand(
        "apk", CmdCategory.PACKAGE, "Alpine Linux 的包管理工具",
        "apk [子命令]",
        listOf(
            CmdExample("更新软件源索引", "apk update"),
            CmdExample("安装软件包", "apk add curl"),
            CmdExample("卸载软件包", "apk del curl")
        ), "📌"
    ),
    LinuxCommand(
        "apt-get", CmdCategory.PACKAGE, "apt-get命令是Debian Linux发行版中的APT软件包管理工具。所有基于Debian的发行都使用这个包管理系统。deb包可以把一个应用的文件包在一起，大体就如同Windows上的安装文件。",
        "apt-get[OPTION]PACKAGE",
        listOf(
            CmdExample("在修改/etc/apt/sources.list或者/etc/apt/preferences之后运行该命令。此外您需要定", "apt-get update"),
            CmdExample("安装一个新软件包：", "apt-get install packagename"),
            CmdExample("卸载一个已安装的软件包（保留配置文件）：", "apt-get remove packagename"),
            CmdExample("卸载一个已安装的软件包（删除配置文件）：", "apt-get –purge remove packagename"),
            CmdExample("会把已装或已卸的软件都备份在硬盘上，所以如果需要空间的话，可以让这个命令来删除你已经删掉的软件：", "apt-get autoclean apt")
        ), "📌"
    ),
    LinuxCommand(
        "apt-key", CmdCategory.PACKAGE, "apt-key命令用于管理Debian Linux系统中的软件包密钥。每个发布的deb包，都是通过密钥认证的，apt-key用来管理密钥。",
        "apt-key(参数)",
        listOf(
            CmdExample("查看帮助信息", "apt-key --help")
        ), "📌"
    ),
    LinuxCommand(
        "apt-sortpkgs", CmdCategory.PACKAGE, "apt-sortpkgs命令是Debian Linux下对软件包索引文件进行排序的简单工具。",
        "apt-sortpkgs(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "apt-sortpkgs --help")
        ), "📌"
    ),
    LinuxCommand(
        "aptitude", CmdCategory.PACKAGE, "aptitude命令与apt-get命令一样，都是Debian Linux及其衍生系统中功能极其强大的包管理工具。与apt-get不同的是，aptitude在处理依赖问题上更佳一些。举例来说，aptitude在删除一个包时，会同时删除本身所",
        "aptitude(选项)(参数)",
        listOf(
            CmdExample("以下是我总结的一些常用aptitude命令，仅供参考：", "aptitude update            # 更新可用的包列表")
        ), "📌"
    ),
    LinuxCommand(
        "arj", CmdCategory.COMPRESS, "arj命令是.arj格式的压缩文件的管理器，用于创建和管理.arj压缩包。",
        "arj(参数)",
        listOf(
            CmdExample("查看帮助信息", "arj --help")
        ), "📌"
    ),
    LinuxCommand(
        "arp", CmdCategory.NETWORK, "arp 命令是 Address Resolution Protocol，地址解析协议，是通过解析网络层地址来找寻数据链路层地址的一个网络协议包中极其重要的网络传输协议。而该命令可以显示和修改 arp 协议解析表中的缓冲数据。",
        "arp（选项）（参数）",
        listOf(
            CmdExample("显示arp 缓冲区内容", "arp -v"),
            CmdExample("添加静态 arp 映射", "arp -s IP MAC-ADDRESS"),
            CmdExample("删除 arp 缓存条目", "arp -d 192.168.1.1")
        ), "📌"
    ),
    LinuxCommand(
        "arpd", CmdCategory.NETWORK, "arpd命令是用来收集免费arp信息的一个守护进程，它将收集到的信息保存在磁盘上或者在需要时，提供给内核用户用于避免多余广播。",
        "arpd(选项)(参数)",
        listOf(
            CmdExample("启动arpd进程：", "arpd -b /var/tmp/arpd.db"),
            CmdExample("运行一段时间后，查看结果：", "arpd -l -b /var/tmp/arpd.db")
        ), "📌"
    ),
    LinuxCommand(
        "arptables", CmdCategory.NETWORK, "arptables命令用来设置、维护和检查Linux内核中的arp包过滤规则表。",
        "arptables(选项)",
        listOf(
            CmdExample("查看帮助信息", "arptables --help")
        ), "📌"
    ),
    LinuxCommand(
        "arpwatch", CmdCategory.NETWORK, "arpwatch命令用来监听网络上arp的记录。",
        "arpwatch(选项)",
        listOf(
            CmdExample("查看帮助信息", "arpwatch --help")
        ), "📌"
    ),
    LinuxCommand(
        "as", CmdCategory.DEV_TOOLS, "as命令是GNU组织推出的一款汇编语言编译器，它支持多种不同类型的处理器。",
        "as[选项][参数]",
        listOf(
            CmdExample("查看帮助信息", "as --help")
        ), "📌"
    ),
    LinuxCommand(
        "axel", CmdCategory.SYS_INFO, "axel是Linux下一个不错的HTTP/ftp高速下载工具。支持多线程下载、断点续传，且可以从多个地址或者从一个地址的多个连接来下载同一个文件。适合网速不给力时多线程下载提高下载速度。比如在国内VPS或服务器上下载lnmp一键安装包用Ax",
        "axel[options]url1[url2][url...]",
        listOf(
            CmdExample("如下载lnmp安装包指定10个线程，存到/tmp/：", "axel -n 10 -o /tmp/ http://www.jsdig.com/lnmp.tar.gz")
        ), "📌"
    ),
    LinuxCommand(
        "badblocks", CmdCategory.FILE_MGMT, "badblock命令用于查找磁盘中损坏的区块。 硬盘是一个损耗设备，当使用一段时间后可能会出现坏道等物理故障。电脑硬盘出现坏道后，如果不及时更换或进行技术处理，坏道就会越来越多，并会造成频繁死机和数据丢失。最好的处理方式是更换磁盘，但在临时",
        "badblock(选项)(参数)",
        listOf(
            CmdExample("badblocks以 4096 的一个block，每一个block检查16次，将结果输出到“hda-badblocks-", "badblocks -b 4096 -c 16 /dev/hda1 -o hda-badblocks-list"),
            CmdExample("hda-badblocks-list是个文本文件，内容如下：", "cat hda-badblocks-list")
        ), "📌"
    ),
    LinuxCommand(
        "blockdev", CmdCategory.FILE_MGMT, "blockdev命令在命令调用“ioxtls”函数，以实现对设备的控制。",
        "blockdev(选项)(参数)",
        listOf(
            CmdExample("设置设备为只读：", "blockdev --setro /dev/hda4"),
            CmdExample("读取设备是否为只读：", "blockdev --getro /dev/hda4"),
            CmdExample("设置设别为可读写：", "blockdev --setrw /dev/hda4")
        ), "📌"
    ),
    LinuxCommand(
        "break", CmdCategory.PROC_MGMT, "跳出当前循环（for/while/until），Shell 内建命令",
        "break [n]",
        listOf(
            CmdExample("跳出循环", "for i in 1 2 3; do [ \$i = 2 ] && break; echo \$i; done")
        ), "📌"
    ),
    LinuxCommand(
        "builtin", CmdCategory.SYS_INFO, "强制执行 Shell 内建命令，忽略同名的外部命令/函数",
        "builtin 命令 [参数]",
        listOf(
            CmdExample("强制用内建的cd而不是同名函数", "builtin cd /tmp")
        ), "📌"
    ),
    LinuxCommand(
        "bunzip2", CmdCategory.SYS_INFO, "bzip2可以对文件进行压缩与解压缩。此命令类似于“gzip/gunzip”命令，只能对文件进行压缩。对于目录只能压缩目录下的所有文件，压缩完成后，在目录下生成以“.bz2”为后缀的压缩包。bunzip2其实是bzip2的符号链接，即软链接",
        "bunzip2(选项)(参数)",
        listOf(
            CmdExample("将/opt目录下的etc.zip、var.zip和backup.zip进行压缩，设置压缩率为最高，同时在压缩完毕后不删除", "bzip2 -9vk /opt/etc.zip /opt/var.zip /opt/backup.zip"),
            CmdExample("解压缩：", "bunzip2 -v /opt/etc.zip.bz2")
        ), "📌"
    ),
    LinuxCommand(
        "bye", CmdCategory.SYS_INFO, "bye命令在ftp模式下，输入bye即可中断目前的连线作业，并结束ftp的执行。",
        "bye",
        listOf(
            CmdExample("查看帮助信息", "bye --help")
        ), "📌"
    ),
    LinuxCommand(
        "bzcat", CmdCategory.COMPRESS, "bzcat命令无需解压缩指定的.bz2文件，即可显示解压缩后的文件内容。",
        "bzcat(参数)",
        listOf(
            CmdExample("将/tmp/man.config以bzip2格式压缩：", "bzip2 -z man.config"),
            CmdExample("将上面的压缩文件内容读出来：", "bzcat man.config.bz2")
        ), "📌"
    ),
    LinuxCommand(
        "bzcmp", CmdCategory.COMPRESS, "bzcmp命令主要功能是在不真正解压缩.bz2压缩包的情况下，比较两个压缩包中的文件，省去了解压缩后在调用cmp命令的过程。",
        "bzcmp(参数)",
        listOf(
            CmdExample("查看帮助信息", "bzcmp --help")
        ), "📌"
    ),
    LinuxCommand(
        "bzdiff", CmdCategory.COMPRESS, "bzdiff命令用于直接比较两个“.bz2”压缩包中文件的不同，省去了解压缩后再调用diff命令的过程。",
        "bzdiff(参数)",
        listOf(
            CmdExample("查看帮助信息", "bzdiff --help")
        ), "📌"
    ),
    LinuxCommand(
        "bzgrep", CmdCategory.COMPRESS, "bzgrep命令使用正则表达式搜索“.bz2”压缩包中文件，将匹配的行显示到标注输出。",
        "bzgrep(参数)",
        listOf(
            CmdExample("查看帮助信息", "bzgrep --help")
        ), "📌"
    ),
    LinuxCommand(
        "bzip2recover", CmdCategory.COMPRESS, "bzip2recover命令可用于恢复被破坏的“.bz2”压缩包中的文件。",
        "bzip2recover(参数)",
        listOf(
            CmdExample("查看帮助信息", "bzip2recover --help")
        ), "📌"
    ),
    LinuxCommand(
        "bzless", CmdCategory.COMPRESS, "bzless命令是增强“.bz2”压缩包查看器，bzless比bzmore命令功能更加强大。",
        "bzless(参数)",
        listOf(
            CmdExample("查看帮助信息", "bzless --help")
        ), "📌"
    ),
    LinuxCommand(
        "bzmore", CmdCategory.COMPRESS, "bzmore命令用于查看bzip2压缩过的文本文件的内容，当下一屏显示不下时可以实现分屏显示。",
        "bzmore(参数)",
        listOf(
            CmdExample("查看帮助信息", "bzmore --help")
        ), "📌"
    ),
    LinuxCommand(
        "cancel", CmdCategory.SYS_INFO, "cancel命令用于取消已存在的打印任务。",
        "cancel(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "cancel --help")
        ), "📌"
    ),
    LinuxCommand(
        "cdrecord", CmdCategory.SYS_INFO, "cdrecord命令用于Linux系统下光盘刻录，它支持cd和DVD格式。linux下一般都带有cdrecord软件。",
        "cdrecord(选项)(参数)",
        listOf(
            CmdExample("查看系统所有 CD-R(w) 设备：", "cdrecord -scanbus"),
            CmdExample("用iso文件刻录一张光盘：", "cdrecord -v -eject speed=4 dev=0,3,0 backup.iso")
        ), "📌"
    ),
    LinuxCommand(
        "chfn", CmdCategory.SYS_INFO, "chfn命令用来改变finger命令显示的信息。这些信息都存放在/etc目录里的passwd文件里。若不指定任何选项，则chfn命令会进入问答式界面。",
        "chfn(选项)(参数)",
        listOf(
            CmdExample("范例1，改变finger信息：", "chfn"),
            CmdExample("范例2，改变账号真实姓名：", "chfn -f jack"),
            CmdExample("范例3：", "shell>> chfn")
        ), "📌"
    ),
    LinuxCommand(
        "chkconfig", CmdCategory.SYS_INFO, "chkconfig命令检查、设置系统的各种服务。这是Red Hat公司遵循GPL规则所开发的程序，它可查询操作系统在每一个执行等级中会执行哪些系统服务，其中包括各类常驻服务。谨记chkconfig不是立即自动禁止或激活一个服务，它只是简单的",
        "chkconfig(选项)",
        listOf(
            CmdExample("查看帮助信息", "chkconfig --help")
        ), "📌"
    ),
    LinuxCommand(
        "chsh", CmdCategory.SYS_INFO, "chsh命令用来更换登录系统时使用的shell。若不指定任何参数与用户名称，则chsh会以应答的方式进行设置。",
        "chsh(选项)(参数)",
        listOf(
            CmdExample("第一种：", "chsh -l"),
            CmdExample("第二种：", "cat /etc/shells"),
            CmdExample("查看当前正在使用的shell：", "echo \$SHELL"),
            CmdExample("把我的shell改成zsh：", "chsh -s /bin/zsh"),
            CmdExample("把shell修改回/bin/bash：", "chsh -s /bin/bash")
        ), "📌"
    ),
    LinuxCommand(
        "clock", CmdCategory.SYS_INFO, "clock命令用于调整 RTC 时间。 RTC 是电脑内建的硬件时间，执行这项指令可以显示现在时刻，调整硬件时钟的时间，将系统时间设成与硬件时钟之时间一致，或是把系统时间回存到硬件时钟。",
        "clock[--adjust][--debug][--directisa][--getepoch][--hctosys][--set--date=\"<日期时间>\"][--setepoch--epoch=<>][--show][--sys",
        listOf(
            CmdExample("获取当前的时间", "获取当前的时间"),
            CmdExample("显示UTC时间", "clock -utc #显示UTC时间")
        ), "📌"
    ),
    LinuxCommand(
        "clockdiff", CmdCategory.SYS_INFO, "在ip报文的首部和ICMP报文的首部都可以放入时间戳数据。clockdiff程序正是使用时间戳来测算目的主机和本地主机的系统时间差。",
        "clockdiff [选项] [参数]",
        listOf(
            CmdExample("两种方法测试的都比较准确。", "lixi@lixi-desktop:~#./clockdiff gigagate1.Princeton.EDU")
        ), "📌"
    ),
    LinuxCommand(
        "colrm", CmdCategory.SYS_INFO, "colrm命令用于删除文件中的指定列。colrm命令从标准输入设备读取书记，转而输出到标准输出设备。如果不加任何参数，则colrm命令不会过滤任何一行。",
        "colrm(参数)",
        listOf(
            CmdExample("查看帮助信息", "colrm --help")
        ), "📌"
    ),
    LinuxCommand(
        "command", CmdCategory.SYS_INFO, "command命令调用指定的指令并执行，命令执行时不查询shell函数。command命令只能够执行shell内部的命令。",
        "command(参数)",
        listOf(
            CmdExample("使用command命令调用执行echo Linux，输入如下命令：", "command echo Linux            #调用执行shell内部指令"),
            CmdExample("上面的命令执行后，将调用执行命令echo Linux，其执行结果如下：", "Linux")
        ), "📌"
    ),
    LinuxCommand(
        "compress", CmdCategory.COMPRESS, "compress命令使用“Lempress-Ziv”编码压缩数据文件。compress是个历史悠久的压缩程序，文件经它压缩后，其名称后面会多出\".Z\"的扩展名。当要解压缩时，可执行uncompress指令。事实上uncompress是指",
        "compress(选项)(参数)",
        listOf(
            CmdExample("将/etc/man.config复到/tmp，并加以压缩", "cd /tmp"),
            CmdExample("将刚刚的压缩档解开", "compress -d man.config.Z"),
            CmdExample("将 man.config 压缩成另外一个文件来备份", "compress -c man.config > man.config.back.Z")
        ), "📌"
    ),
    LinuxCommand(
        "consoletype", CmdCategory.SYS_INFO, "consoletype命令用于打印已连接的终端类型到标准输出，并能够检查已连接的终端是当前终端还是虚拟终端。",
        "consoletype",
        listOf(
            CmdExample("查看帮助信息", "consoletype --help")
        ), "📌"
    ),
    LinuxCommand(
        "continue", CmdCategory.PROC_MGMT, "跳过本次循环剩余部分，直接进入下一次循环，Shell 内建命令",
        "continue [n]",
        listOf(
            CmdExample("跳过某次循环", "for i in 1 2 3; do [ \$i = 2 ] && continue; echo \$i; done")
        ), "📌"
    ),
    LinuxCommand(
        "convertquota", CmdCategory.SYS_INFO, "convertquota命令用于将老的磁盘额数据文件（“quota.user”和“quota.group”）转换为新格式的文件（“quota.user”和“quota.group”）。",
        "convertquota(选项)(参数)",
        listOf(
            CmdExample("使用convertquota指令转换指定文件系统/data的磁盘配额数据文件。在命令行中输入下面的命令：", "convertquota -u /data     //转换文件系统\"/data\"上的用户磁盘配额文件")
        ), "📌"
    ),
    LinuxCommand(
        "createrepo", CmdCategory.PACKAGE, "为一批 RPM 包生成 yum/dnf 仓库所需的元数据",
        "createrepo [选项] 目录",
        listOf(
            CmdExample("为目录生成仓库元数据", "createrepo /path/to/rpms")
        ), "📌"
    ),
    LinuxCommand(
        "cu", CmdCategory.SYS_INFO, "cu命令用于连接另一个系统主机。cu(call up)指令可连接另一台主机，并采用类似拨号终端机的接口工作，也可执行简易的文件传输作业。",
        "cu[dehnotv][-a<通信端口>][-c<电话号码>][-E<脱离字符>][-I<设置文件>][-l<外围设备代号>][-s<连线速率>][-x<排错模式>][-z<系统主机>][--help][-nostop][--parity=",
        listOf(
            CmdExample("与远程主机连接", "cu -c 0102377765")
        ), "📌"
    ),
    LinuxCommand(
        "cupsdisable", CmdCategory.SYS_INFO, "cupsdisable命令用于停止指定的打印机。",
        "cupsdisable(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "cupsdisable --help")
        ), "📌"
    ),
    LinuxCommand(
        "cupsenable", CmdCategory.SYS_INFO, "cupsenable命令用于启动指定的打印机。",
        "cupsenable(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "cupsenable --help")
        ), "📌"
    ),
    LinuxCommand(
        "declare", CmdCategory.SYS_INFO, "声明变量并设置其类型/属性，Shell 内建命令",
        "declare [选项] 变量名[=值]",
        listOf(
            CmdExample("声明一个整数变量", "declare -i num=5"),
            CmdExample("声明一个只读变量", "declare -r name=alice"),
            CmdExample("查看所有已声明的变量", "declare -p")
        ), "📌"
    ),
    LinuxCommand(
        "depmod", CmdCategory.SYS_INFO, "depmod命令可产生模块依赖的映射文件，在构建嵌入式系统时，需要由这个命令来生成相应的文件，由modprobe使用。",
        "depmod(选项)",
        listOf(
            CmdExample("查看帮助信息", "depmod --help")
        ), "📌"
    ),
    LinuxCommand(
        "dhclient", CmdCategory.SYS_INFO, "dhclient命令使用动态主机配置协议动态的配置网络接口的网络参数。",
        "dhclient(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "dhclient --help")
        ), "📌"
    ),
    LinuxCommand(
        "dhcpd", CmdCategory.NETWORK, "ISC DHCP 服务器守护进程，为局域网设备自动分配 IP 地址",
        "dhcpd[选项][网络接口]",
        listOf(
            CmdExample("以调试模式在前台启动", "dhcpd -d -f")
        ), "📌"
    ),
    LinuxCommand(
        "dhcrelay", CmdCategory.NETWORK, "dhcrelay命令使用dhcrelay命令可以提供中继DHCP和BOOTP请求，从一个没有DHCP服务器的子网直接连接到其它子网内的一个或多个DHCP服务器。该命令在DHCP中继服务器上使用，同时支持DHCPv4/BOOTP和DHCPv6",
        "dhcrelay[选项][DHCP服务器]",
        listOf(
            CmdExample("查看帮助信息", "dhcrelay --help")
        ), "📌"
    ),
    LinuxCommand(
        "diffstat", CmdCategory.DEBUG_TOOLS, "diffstat命令用来显示diff命令输出信息的柱状图，用以显示diff命令比较两个文件的不同统计信息。用户也可以直接使用|将diff命令所输出的结果直接送给diffstat命令进行统计结果的显示。使用该命令时，若所比较的文件或者子目录不",
        "diffstat(选项)(参数)",
        listOf(
            CmdExample("将目录\"test1\"和\"test2\"下的同名文件\"testf.txt\"使用diff命令进行比较。然后使用diffstat", "diff test1 test2 | diffstat    #进行比较结果的统计显示"),
            CmdExample("对于查看文件中的内容，用户可以通过cat命令进行查看即可，具体操作如下：", "cat test1/testf.txt           #查看test1/testf的内容"),
            CmdExample("从上面的文件内容显示，可以看到两个文件内容的差别。现在来运行刚才的命令，对文件比较的结果进行统计显示，结果如下：", "testfile | 2 +-             #统计信息输出显示")
        ), "📌"
    ),
    LinuxCommand(
        "dircolors", CmdCategory.SYS_INFO, "dircolors命令设置ls命令在显示目录或文件时所用的色彩。dircolors可根据[色彩配置文件]来设置LS_COLORS环境变量或是显示设置LS_COLORS环境变量的命令。",
        "dircolors(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "dircolors --help")
        ), "📌"
    ),
    LinuxCommand(
        "dirs", CmdCategory.FILE_MGMT, "显示目录栈内容，配合 pushd/popd 使用",
        "dirs [选项]",
        listOf(
            CmdExample("查看当前目录栈", "dirs"),
            CmdExample("带序号显示", "dirs -v")
        ), "📌"
    ),
    LinuxCommand(
        "dnsdomainname", CmdCategory.NETWORK, "dnsdomainname命令用于定义DNS系统中FQDN名称中的域名。",
        "dnsdomainname(选项)",
        listOf(
            CmdExample("查看帮助信息", "dnsdomainname --help")
        ), "📌"
    ),
    LinuxCommand(
        "domainname", CmdCategory.NETWORK, "domainname命令用于显示和设置系统的NIS域名。",
        "domainname(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "domainname --help")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-deb", CmdCategory.PACKAGE, "dpkg-deb命令是Debian Linux下的软件包管理工具，它可以对软件包执行打包和解包操作以及提供软件包信息。",
        "dpkg-deb(选项)(参数)",
        listOf(
            CmdExample("解压程序文件：", "dpkg-deb -x drcom-pum_1.0-0ubuntu1~ppa1~jaunty1_i386.deb drcom"),
            CmdExample("解压控制文件：", "dpkg-deb -e drcom-pum_1.0-0ubuntu1~ppa1~jaunty1_i386.deb drcom/DEBIAN"),
            CmdExample("打包生成deb文件：", "dpkg-deb -b drcom drcom_1.4.8.2_i386.deb"),
            CmdExample("查询deb包中的文件内容：", "dpkg-deb -c demo.deb")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-divert", CmdCategory.PACKAGE, "dpkg-divert命令是Debian Linux中创建并管理一个转向（diversion）列表，其使得安装文件的默认位置失效的工具。",
        "dpkg-divert(选项)(参数)",
        listOf(
            CmdExample("指定软件包wibble安装时，写入/usr/bin/example.foo，而不是/usr/bin/example：", "dpkg-divert --package wibble --divert /usr/bin/example.foo --rename /usr/bin/example"),
            CmdExample("指定软件包wibble安装时，删除对/usr/bin/example的转移修改：", "dpkg-divert --package wibble --rename --remove /usr/bin/example"),
            CmdExample("删除对/usr/bin/example的转移修改：", "dpkg-divert --rename --remove /usr/bin/example"),
            CmdExample("添加一个软件包安装时，写入/usr/bin/example.foo，而不是/usr/bin/example的修改：", "dpkg-divert --divert /usr/bin/example.foo --rename /usr/bin/example")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-preconfigure", CmdCategory.PACKAGE, "dpkg-preconfigure命令用于在Debian Linux中软件包安装之前询问问题。",
        "dpkg-preconfigure(选项)(参数)",
        listOf(
            CmdExample("导入debconf模板：", "dpkg-preconfigure /var/cache/apt/archives/mysql-server-5.5*.deb")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-reconfigure", CmdCategory.PACKAGE, "dpkg-reconfigure命令是Debian Linux中重新配置已经安装过的软件包，可以将一个或者多个已安装的软件包传递给此指令，它将询问软件初次安装后的配置问题。",
        "dpkg-reconfigure(选项)(参数)",
        listOf(
            CmdExample("用于配置语言：", "sudo dpkg-reconfigure locales")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-split", CmdCategory.PACKAGE, "dpkg-split命令用来将Debian Linux中的大软件包分割成小软件包，它还能够将已分割的文件进行合并。",
        "dpkg-split(选项)(参数)",
        listOf(
            CmdExample("把foo.deb分割出N个大小为460KB的文件：", "dpkg-split -s foo.deb"),
            CmdExample("合并分割文件：", "dpkg-split -j \"foo*\"")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-statoverride", CmdCategory.PACKAGE, "dpkg-statoverride命令用于Debian Linux中覆盖文件的所有权和模式，让dpkg于包安装时使得文件所有权与模式失效。",
        "dpkg-statoverride(选项)",
        listOf(
            CmdExample("修改文件夹的权限属性：", "sudo dpkg-statoverride --update --add nagios nagios 751 /var/lib/nagios3"),
            CmdExample("强制修改文件夹的权限属性：", "sudo dpkg-statoverride --force --update --add root sasl 755 /var/spool/postfix/var/run/saslauthd"),
            CmdExample("将文件从数据库中删除：", "sudo dpkg-statoverride --remove /usr/bin/wall")
        ), "📌"
    ),
    LinuxCommand(
        "dpkg-trigger", CmdCategory.PACKAGE, "dpkg-trigger命令是Debian Linux下的软件包触发器。",
        "dpkg-trigger(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "dpkg-trigger --help")
        ), "📌"
    ),
    LinuxCommand(
        "dris", CmdCategory.SYS_INFO, "dris命令用于显示和清空目录堆栈中的内容。",
        "dris(选项)",
        listOf(
            CmdExample("查看帮助信息", "dris --help")
        ), "📌"
    ),
    LinuxCommand(
        "dump", CmdCategory.COMPRESS, "dump命令用于备份ext2或者ext3文件系统。可将目录或整个文件系统备份至指定的设备，或备份成一个大文件。",
        "dump(选项)(参数)",
        listOf(
            CmdExample("将/home目录所有内容备份到/tmp/homeback.bak文件中，备份层级为0并在/etc/dumpdates中记", "‍dump -0u -f /tmp/homeback.bak /home"),
            CmdExample("将/home目录所有内容备份到/tmp/homeback.bak文件中，备份层级为1（只备份上次使用层次0备份后发生过改", "dump -1u -f /tmp/homeback.bak /home")
        ), "📌"
    ),
    LinuxCommand(
        "e2fsck", CmdCategory.FILE_MGMT, "e2fsck命令用于检查第二扩展文件系统的完整性，通过适当的选项可以尝试修复出现的错误。",
        "e2fsck(选项)(参数)",
        listOf(
            CmdExample("检查/dev/sda1是否有问题，如发现问题便自动修复：", "e2fsck -a -y /dev/sda1")
        ), "📌"
    ),
    LinuxCommand(
        "e2label", CmdCategory.FILE_MGMT, "e2label命令用来设置第二扩展文件系统的卷标。",
        "e2label(参数)",
        listOf(
            CmdExample("许多用了多年Linux的人可能也没有用过e2label命令。但是这个命令相当有效。在介绍它之前,我们先看看/etc/fs", "label=//ext3 defaults 1 1"),
            CmdExample("然后在/etc/fstab里加入：", "label=/new  /new  ext3  defaults  1 1")
        ), "📌"
    ),
    LinuxCommand(
        "edquota", CmdCategory.SYS_INFO, "edquota命令用于编辑指定用户或工作组磁盘配额。edquota预设会使用vi来编辑使用者或群组的quota设置。",
        "edquota(选项)(参数)",
        listOf(
            CmdExample("第一步：", "vi /etc/fstab"),
            CmdExample("找到对应于/home和/www的行，例如：", "/dev/sda5 /home ext2 defaults 1 2"),
            CmdExample("在/home里实现用户级的磁盘配额，所以对sda5行的挂装选项域做如下修改：", "/dev/sda5 /home ext2 defaults，usrquota 1 2"),
            CmdExample("注意，是usrquota哦。类似的，我们可以如下修改/www行：", "/dev/sda7 /www ext2 defaults，grpquota 1 2"),
            CmdExample("改/etc/fstab文件中", "LABEL=/ / ext2 defaults,usrquota,grpquota 1 1")
        ), "📌"
    ),
    LinuxCommand(
        "egrep", CmdCategory.SYS_INFO, "egrep命令用于在文件内查找指定的字符串。egrep执行效果与grep -E相似，使用的语法及参数可参照grep指令，与grep的不同点在于解读字符串的方法。egrep是用extended regular expression语法来解读的",
        "egrep(选项)(查找模式)(文件名1，文件名2，……)",
        listOf(
            CmdExample("显示文件中符合条件的字符。例如，查找当前目录下所有文件中包含字符串\"Linux\"的文件，可以使用如下命令：", "egrep Linux *"),
            CmdExample("结果如下所示：", "以下五行为 testfile 中包含Linux字符的行"),
            CmdExample("过滤注释行和空白行", "egrep -v '^\\s*(#|$)' filename")
        ), "📌"
    ),
    LinuxCommand(
        "eject", CmdCategory.SYS_INFO, "eject命令用来退出抽取式设备。若设备已挂入，则eject命令会先将该设备卸除再退出。",
        "eject(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "eject --help")
        ), "📌"
    ),
    LinuxCommand(
        "elinks", CmdCategory.SYS_INFO, "elinks命令能实现一个纯文本界面的WWW浏览器，操作方式与“lynx”类似。",
        "elinks(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "elinks --help")
        ), "📌"
    ),
    LinuxCommand(
        "elm", CmdCategory.SYS_INFO, "elm命令是一个E-mail客户端管理程序，它提供了纯文本交互式全屏幕界面。",
        "elm(选项)",
        listOf(
            CmdExample("查看帮助信息", "elm --help")
        ), "📌"
    ),
    LinuxCommand(
        "emacs", CmdCategory.SYS_INFO, "emacs命令是由GNU组织的创始人Richard Stallman开发的一个功能强大的全屏文本编辑器，它支持多种编程语言，具有很多优良的特性。有众多的系统管理员和软件开发者使用emacs。",
        "emacs(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "emacs --help")
        ), "📌"
    ),
    LinuxCommand(
        "enable", CmdCategory.SYS_INFO, "启用或禁用 Shell 内建命令",
        "enable [选项] [命令名]",
        listOf(
            CmdExample("禁用内建的echo，改用外部echo", "enable -n echo"),
            CmdExample("查看所有已启用的内建命令", "enable")
        ), "📌"
    ),
    LinuxCommand(
        "ex", CmdCategory.SYS_INFO, "在ex模式下启动vim文本编辑器。ex执行效果如同vi -E，适用于法及参数可参照vi指令，如要从Ex模式回到普通模式，则在vim中输入:vi或:visual即可。",
        "ex（参数）",
        listOf(
            CmdExample("查看帮助信息", "ex --help")
        ), "📌"
    ),
    LinuxCommand(
        "exit", CmdCategory.PROC_MGMT, "退出当前 Shell 或脚本，并可指定退出状态码",
        "exit [状态码]",
        listOf(
            CmdExample("正常退出", "exit 0"),
            CmdExample("以错误状态退出", "exit 1")
        ), "📌"
    ),
    LinuxCommand(
        "exportfs", CmdCategory.SYS_INFO, "exportfs 命令用来管理当前NFS共享的文件系统列表。",
        "exportfs [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "exportfs --help")
        ), "📌"
    ),
    LinuxCommand(
        "fgrep", CmdCategory.SYS_INFO, "fgrep命令是用来搜索 file 参数指定的输入文件（缺省为标准输入）中的匹配模式的行。fgrep 命令特别搜索 Pattern 参数，它们是固定的字符串。如果在 File 参数中指定一个以上的文件 fgrep 命令将显示包含匹配行的文件",
        "fgrep(选项)(参数)",
        listOf(
            CmdExample("搜索几个文件中的一个简单字符串：", "fgrep strcpy *.c"),
            CmdExample("计数匹配某模式的行数：", "fgrep -c 『{』pgm.cfgrep -c 『}』pgm.c"),
            CmdExample("显示包含某模式的文件名：", "fgrep -l strcpy *.c")
        ), "📌"
    ),
    LinuxCommand(
        "findfs", CmdCategory.FILE_MGMT, "findfs命令依据卷标（Label）和UUID查找文件系统所对应的设备文件。findfs命令会搜索整个磁盘，看是否有匹配的标签或者UUID没有，如果有则打印到标注输出上。findfs命令也是e2fsprogs项目的一部分。",
        "findfs(参数)",
        listOf(
            CmdExample("通过卷标名查找对应的文件系统：", "findfs LABEL=/boot")
        ), "📌"
    ),
    LinuxCommand(
        "finger", CmdCategory.SYS_INFO, "finger命令用于查找并显示用户信息。包括本地与远端主机的用户皆可，帐号名称没有大小写的差别。单独执行finger指令，它会显示本地主机现在所有的用户的登陆信息，包括帐号名称，真实姓名，登入终端机，闲置时间，登入时间以及地址和电话。",
        "finger(选项)(参数)",
        listOf(
            CmdExample("在计算机上使用finger：", "finger")
        ), "📌"
    ),
    LinuxCommand(
        "firewalld", CmdCategory.NETWORK, "firewalld命令是CentOS/RHEL 7+系统中的动态防火墙管理器，提供对iptables内核模块的封装，支持网络/防火墙区域(zone)的概念，支持IPv4防火墙设置和IPv6设置。firewalld使用daemon守护进程，支",
        "firewall-cmd[OPTIONS...]COMMAND",
        listOf(
            CmdExample("查看帮助信息", "firewalld --help")
        ), "📌"
    ),
    LinuxCommand(
        "fishshell", CmdCategory.SYS_INFO, "fish（Friendly Interactive Shell）交互式命令行 shell，语法友好、自动补全强",
        "fish",
        listOf(
            CmdExample("启动fish shell", "fish"),
            CmdExample("查看版本", "fish --version")
        ), "📌"
    ),
    LinuxCommand(
        "ftp", CmdCategory.NETWORK, "ftp命令用来设置文件系统相关功能。ftp服务器在网上较为常见，Linux ftp命令的功能是用命令的方式来控制在本地机和远程机之间传送文件，这里详细介绍Linux ftp命令的一些经常使用的命令，相信掌握了这些使用Linux进行ftp操作",
        "ftp(选项)(参数)",
        listOf(
            CmdExample("FTP 匿名登录账号密码", "账号：anonymous"),
            CmdExample("关闭FTP连接", "bye"),
            CmdExample("下载文件", "ftp> get readme.txt # 下载 readme.txt 文件"),
            CmdExample("上传文件", "ftp> put /path/readme.txt # 上传 readme.txt 文件")
        ), "📌"
    ),
    LinuxCommand(
        "ftpcount", CmdCategory.NETWORK, "ftpcount命令用于显示目前已FTP登入的用户人数。执行这项指令可得知目前用FTP登入系统的人数以及FTP登入人数的上限。",
        "ftpcount [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "ftpcount --help")
        ), "📌"
    ),
    LinuxCommand(
        "ftpshut", CmdCategory.NETWORK, "功能说明：在指定的时间关闭ftp服务器。本指令提供系统管理者在设置的时间关闭FTP服务器，且能在关闭之前发出警告信息通知用户。关闭时间若设置后为\"none\"，则会马上关闭服务器。如果采 用\"+30\"的方式来设置表示服务器在30分钟之",
        "ftpshut [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "ftpshut --help")
        ), "📌"
    ),
    LinuxCommand(
        "ftptop", CmdCategory.NETWORK, "ftptop命令类似于top命令的显示风格显示proftpd服务器的连接状态。",
        "ftptop(选项)",
        listOf(
            CmdExample("查看帮助信息", "ftptop --help")
        ), "📌"
    ),
    LinuxCommand(
        "ftpwho", CmdCategory.NETWORK, "ftpwho命令ftp服务器套件proftpd的工作指令，用于显示当前每个ftp会话信息。",
        "ftpwho(选项)",
        listOf(
            CmdExample("查看帮助信息", "ftpwho --help")
        ), "📌"
    ),
    LinuxCommand(
        "gcov", CmdCategory.DEV_TOOLS, "gcov命令是一款测试程序的代码覆盖率的工具。",
        "gcov(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "gcov --help")
        ), "📌"
    ),
    LinuxCommand(
        "get_module", CmdCategory.SYS_INFO, "get_module命令用于获取Linux内核模块的详细信息。",
        "get_module 模块名",
        listOf(
            CmdExample("使用lsmod命令查看内核模块：", "lsmod | head -5"),
            CmdExample("使用get_module命令查看模块详细信息：", "get_module ipv6")
        ), "📌"
    ),
    LinuxCommand(
        "getcap", CmdCategory.PERM_USER, "getcap命令用于查看文件的能力（capabilities）。在 Linux 中，文件的能力是一种权限控制机制，可以赋予普通文件特定的特权操作，而无需完全的 root 权限。",
        "getcap[选项][文件名...]",
        listOf(
            CmdExample("查看一个可执行文件的能力：", "getcap /usr/bin/ping"),
            CmdExample("查看当前目录下所有文件的能力：", "getcap *"),
            CmdExample("查看进程的能力（以 PID 为例）：", "getcap -p 1234")
        ), "📌"
    ),
    LinuxCommand(
        "getenforce", CmdCategory.PERM_USER, "查看 SELinux 当前的运行模式（enforcing 强制/permissive 宽容/disabled 禁用）。",
        "getenforce",
        listOf(
            CmdExample("查看帮助信息", "getenforce --help")
        ), "📌"
    ),
    LinuxCommand(
        "getsebool", CmdCategory.PERM_USER, "getsebool命令是用来查询SElinux策略内各项规则的布尔值。SELinux的策略与规则管理相关命令：seinfo命令、sesearch命令、getsebool命令、setsebool命令、semanage命令。",
        "getsebool[-a][布尔值条款]",
        listOf(
            CmdExample("查询本系统内所有的布尔值设置状况：", "getsebool -a"),
            CmdExample("查询httpd_enable_homedirs是否为关闭，若没关闭，请关闭它：", "getsebool httpd_enable_homedirs")
        ), "📌"
    ),
    LinuxCommand(
        "gpm", CmdCategory.SYS_INFO, "gpm命令是Linux的虚拟控制台下的鼠标服务器，用于在虚拟控制台下实现鼠标复制和粘贴文本的功能。",
        "gpm(选项)",
        listOf(
            CmdExample("查看帮助信息", "gpm --help")
        ), "📌"
    ),
    LinuxCommand(
        "groupmems", CmdCategory.PERM_USER, "groupmems命令允许用户管理他/她自己的组成员列表，而不需要超级用户权限。groupmems实用程序适用于将其用户配置为以他们自己的名义主组（即来宾/来宾）的系统。",
        "groupmems-auser_name|-d用户名|[-g 用户组名]|-l|-p",
        listOf(
            CmdExample("查看帮助信息", "groupmems --help")
        ), "📌"
    ),
    LinuxCommand(
        "grpck", CmdCategory.SYS_INFO, "grpck命令用于验证组文件的完整性，在验证之前，需要先锁定（lock）组文件/etc/group和/etc/shadow。",
        "grpck(选项)",
        listOf(
            CmdExample("对组账号和影子文件进行验证：", "必须以管理员身份运行"),
            CmdExample("测试错误的实例：", "**echo check_user:x: >> /etc/group    # 添加一行错误的格式数据")
        ), "📌"
    ),
    LinuxCommand(
        "grpconv", CmdCategory.SYS_INFO, "grpconv命令用来开启群组的投影密码。Linux系统里的用户和群组密码，分别存放在/etc目录下的passwd和group文件中。因系统运作所需，任何人都得以读取它们，造成安全上的破绽。投影密码将文件内的密码改存在/etc目录下的sha",
        "grpconv",
        listOf(
            CmdExample("设置cdy组密码", "groupmod --password 123456 cdy"),
            CmdExample("启动影子系统", "grpconv")
        ), "📌"
    ),
    LinuxCommand(
        "grpunconv", CmdCategory.SYS_INFO, "grpunconv命令用来关闭群组的投影密码。它会把密码从gshadow文件内，回存到group文件里。",
        "grpunconv",
        listOf(
            CmdExample("未关闭的情况", "cat /etc/gshadow | grep cdy"),
            CmdExample("关闭影子密码", "cat /etc/gshadow"),
            CmdExample("查看密码已经复制到/etc/group中了。", "cat /etc/group | grep cdy")
        ), "📌"
    ),
    LinuxCommand(
        "grub", CmdCategory.SYS_INFO, "grub命令是多重引导程序grub的命令行shell工具。",
        "grub(选项)",
        listOf(
            CmdExample("依次输入命令：", "root (hd0,1)   /* 假设 /dev/hda2 是你的 /boot 所在的分区 */")
        ), "📌"
    ),
    LinuxCommand(
        "grub2-mkconfig", CmdCategory.SYS_INFO, "根据当前系统内核和配置重新生成 GRUB2 启动菜单配置文件",
        "grub2-mkconfig -o 输出文件",
        listOf(
            CmdExample("重新生成GRUB配置", "sudo grub2-mkconfig -o /boot/grub2/grub.cfg")
        ), "📌"
    ),
    LinuxCommand(
        "grub2-set-default", CmdCategory.SYS_INFO, "设置 GRUB2 默认启动的系统/内核版本",
        "grub2-set-default 序号或名称",
        listOf(
            CmdExample("设置默认启动项为第一项", "sudo grub2-set-default 0")
        ), "📌"
    ),
    LinuxCommand(
        "gzexe", CmdCategory.COMPRESS, "gzexe命令用来压缩可执行文件，压缩后的文件仍然为可执行文件，在执行时进行自动解压缩。当您去执行被压缩过的执行文件时，该文件会自动解压然后继续执行，和使用一般的执行文件相同。这个命令也可以看成是gunzip命令的一个扩展。",
        "gzexe(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "gzexe --help")
        ), "📌"
    ),
    LinuxCommand(
        "halt", CmdCategory.SYS_INFO, "halt命令用来关闭正在运行的Linux操作系统。halt命令会先检测系统的runlevel，若runlevel为0或6，则关闭系统，否则即调用shutdown来关闭系统。",
        "halt(选项)",
        listOf(
            CmdExample("查看帮助信息", "halt --help")
        ), "📌"
    ),
    LinuxCommand(
        "hcitool", CmdCategory.NETWORK, "配置和管理蓝牙设备的命令行工具",
        "dev  显示本地设备inq  查询远程设备scan 扫描远程设备name 从远程设备获取名称info 从远程设备获取信息spinq   启动定期查询epinq   退出定期查询cmd 提交任意HCI命令con 显示活动连接cc 创建到远程",
        listOf(
            CmdExample("扫描附近的蓝牙设备", "hcitool scan"),
            CmdExample("查看本机蓝牙设备信息", "hcitool dev")
        ), "📌"
    ),
    LinuxCommand(
        "hdparm", CmdCategory.SYS_INFO, "hdparm命令提供了一个命令行的接口用于读取和设置IDE或SCSI硬盘参数。",
        "hdparm(选项)(参数)",
        listOf(
            CmdExample("显示硬盘的相关设置：", "hdparm /dev/sda"),
            CmdExample("测试硬盘的读取速度：", "hdparm -T /dev/sda"),
            CmdExample("测试硬盘缓存的读取速度：", "hdparm -T /dev/xvda"),
            CmdExample("检测硬盘的电源管理模式：", "hdparm -C /dev/sda"),
            CmdExample("查询并设置硬盘多重扇区存取的扇区数，以增进硬盘的存取效率：", "hdparm -m /dev/sda")
        ), "📌"
    ),
    LinuxCommand(
        "helm", CmdCategory.SYS_INFO, "helm命令是Kubernetes的包管理工具，类似于Ubuntu的apt、CentOS的yum或macOS的homebrew。Helm使用Charts来定义、安装和升级复杂的Kubernetes应用程序。",
        "helm[command]Available Commands:completion   为指定的shell生成自动补全脚本create       创建一个新的Chartdebug        调试Chartdeprecated   列",
        listOf(
            CmdExample("查看帮助信息", "helm --help")
        ), "📌"
    ),
    LinuxCommand(
        "help", CmdCategory.SYS_INFO, "help命令help命令只能显示bash内建命令的帮助信息，而对于外部命令的帮助信息只能使用man或者info命令查看。",
        "help(选项)(参数)",
        listOf(
            CmdExample("使用help命令显示shell内部shopt命令的帮助信息，输入如下命令：", "help shopt                #获取shopt命令的帮助信息")
        ), "📌"
    ),
    LinuxCommand(
        "hping3", CmdCategory.SYS_INFO, "hping是用于生成和解析TCPIP协议数据包的开源工具。创作者是Salvatore Sanfilippo。目前最新版是hping3，支持使用tcl脚本自动化地调用其API。hping是安全审计、防火墙测试等工作的标配工具。hping优势在",
        "hping3 [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "hping3 --help")
        ), "📌"
    ),
    LinuxCommand(
        "htdigest", CmdCategory.SYS_INFO, "htdigest命令是Apache的Web服务器内置工具，用于创建和更新储存用户名、域和用于摘要认证的密码文件。",
        "htdigest(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "htdigest --help")
        ), "📌"
    ),
    LinuxCommand(
        "hwclock", CmdCategory.SYS_INFO, "hwclock命令是一个硬件时钟访问工具，它可以显示当前时间、设置硬件时钟的时间和设置硬件时钟为系统时间，也可设置系统时间为硬件时钟的时间。",
        "hwclock(选项)",
        listOf(
            CmdExample("设置硬件时间要依赖于操作系统时间，具体方法如下：", "hwclock –systohc"),
            CmdExample("不加任何参数使用hwclock，可以查看当前的硬件日期和时间。", "hwclock"),
            CmdExample("查看clock文件，确认是否设置了UTC：", "cat /etc/default/rcS"),
            CmdExample("在其他一些版本的Linux（如RebHat）中可以这样查看：", "cat /etc/sysconfig/clock")
        ), "📌"
    ),
    LinuxCommand(
        "ifcfg", CmdCategory.SYS_INFO, "ifcfg命令是一个Bash脚本程序，用来设置Linux中的网络接口参数。",
        "ifcfg(参数)",
        listOf(
            CmdExample("查看帮助信息", "ifcfg --help")
        ), "📌"
    ),
    LinuxCommand(
        "ifdown", CmdCategory.SYS_INFO, "ifdown命令用于禁用指定的网络接口。",
        "ifdown(参数)",
        listOf(
            CmdExample("查看帮助信息", "ifdown --help")
        ), "📌"
    ),
    LinuxCommand(
        "ifstat", CmdCategory.DEBUG_TOOLS, "ifstat命令就像iostat/vmstat描述其它的系统状况一样，是一个统计网络接口活动状态的工具。ifstat工具系统中并不默认安装，需要自己下载源码包，重新编译安装，使用过程相对比较简单。",
        "ifstat [选项] [参数]",
        listOf(
            CmdExample("默认使用", "ifstat"),
            CmdExample("默认ifstat不监控回环接口，显示的流量单位是KB。", "ifstat -tT"),
            CmdExample("监控所有网络接口", "ifstat -a")
        ), "📌"
    ),
    LinuxCommand(
        "ifup", CmdCategory.SYS_INFO, "ifup命令用于激活指定的网络接口。",
        "ifup(参数)",
        listOf(
            CmdExample("查看帮助信息", "ifup --help")
        ), "📌"
    ),
    LinuxCommand(
        "indent", CmdCategory.SYS_INFO, "indent命令可辨识C的原始代码文件，并加以格式化，以方便程序员阅读、修改等操作。",
        "indent(选项)(源文件)或indent（选项)(源文件)(-o 目标文件)",
        listOf(
            CmdExample("使用indent命令将C语言源文件\"test.c\"中所有的sizeof后面添加一个空格，输入如下命令：", "indent -bs /home/rootlocal/桌面/test.c")
        ), "📌"
    ),
    LinuxCommand(
        "info", CmdCategory.SYS_INFO, "info命令是Linux下info格式的帮助指令。",
        "info(选项)(参数)",
        listOf(
            CmdExample("在info后面输入命令的名称就可以查看该命令的info帮助文档了：", "info info"),
            CmdExample("面介绍一下它的几个常用快捷键。", "**?键：** 它就会显示info的常用快捷键。"),
            CmdExample("命令", "**？**      显示帮助窗口")
        ), "📌"
    ),
    LinuxCommand(
        "init", CmdCategory.SYS_INFO, "init命令是Linux下的进程初始化工具，init进程是所有Linux进程的父进程，它的进程号为1。init命令是Linux操作系统中不可缺少的程序之一，init进程是Linux内核引导运行的，是系统中的第一个进程。",
        "init(选项)(参数)",
        listOf(
            CmdExample("到底什么是运行级呢？简单的说，运行级就是操作系统当前正在运行的功能级别。这个级别从0到6 ，具有不同的功能。你也可以在/", "#0  停机（千万不能把initdefault 设置为0）")
        ), "📌"
    ),
    LinuxCommand(
        "inotifywait", CmdCategory.SYS_INFO, "Inotify一种强大的、细粒度的、异步文件系统监控机制，它满足各种各样的文件监控需要，可以监控文件系统的访问属性、读写属性、权限属性、删除创建、移动等操作，也就是可以监控文件发生的一切变化。。",
        "inotifywait [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "inotifywait --help")
        ), "📌"
    ),
    LinuxCommand(
        "insmod", CmdCategory.SYS_INFO, "insmod命令用于将给定的模块加载到内核中。Linux有许多功能是通过模块的方式，在需要时才载入kernel。如此可使kernel较为精简，进而提高效率，以及保有较大的弹性。这类可载入的模块，通常是设备驱动程序。",
        "insmod(选项)(参数)",
        listOf(
            CmdExample("加载RAID1阵列级别模块，如下所示：", "insmod /lib/modules/2.6.")
        ), "📌"
    ),
    LinuxCommand(
        "iotop", CmdCategory.DEBUG_TOOLS, "iotop命令是一个用来监视磁盘I/O使用状况的top类工具。iotop具有与top相似的UI，其中包括PID、用户、I/O、进程等相关信息。Linux下的IO统计工具如iostat，nmon等大多数是只能统计到per设备的读写情况，如果你",
        "iotop（选项）",
        listOf(
            CmdExample("直接执行iotop就可以看到效果了：", "Total DISK read:       0.00 B/s | Total DISK write:       0.00 B/s")
        ), "📌"
    ),
    LinuxCommand(
        "ip6tables", CmdCategory.NETWORK, "ip6tables命令和iptables一样，都是linux中防火墙软件，不同的是ip6tables采用的TCP/ip协议为IPv6。",
        "ip6tables(选项)",
        listOf(
            CmdExample("在命令行窗口输入下面的指令就可以查看当前的 IPv6 防火墙配置：", "ip6tables -nl --line-numbers"),
            CmdExample("使用编辑器编辑/etc/sysconfig/ip6tables文件：", "vi /etc/sysconfig/ip6tables"),
            CmdExample("可能会看到下面的默认 ip6tables 规则：", "*filter"),
            CmdExample("要开启 80 端口（HTTP 服务器端口），在 COMMIT 一行之前添加如下规则：", "-A RH-Firewall-1-INPUT -m tcp -p tcp --dport 80 -j ACCEPT"),
            CmdExample("要开启 53 端口（DNS 服务器端口），在 COMMIT 一行之前添加如下规则：", "-A RH-Firewall-1-INPUT -m tcp -p tcp --dport 53 -j ACCEPT")
        ), "📌"
    ),
    LinuxCommand(
        "ip6tables-restore", CmdCategory.NETWORK, "ip6tables-restore命令用来还原ip6tables表。",
        "ip6tables-restore(选项)",
        listOf(
            CmdExample("查看帮助信息", "ip6tables-restore --help")
        ), "📌"
    ),
    LinuxCommand(
        "ip6tables-save", CmdCategory.NETWORK, "ip6tables-save命令将Linux内核中ip6tables表导出到标准输出设备上。",
        "ip6tables-save(选项)",
        listOf(
            CmdExample("查看帮助信息", "ip6tables-save --help")
        ), "📌"
    ),
    LinuxCommand(
        "ipcrm", CmdCategory.NETWORK, "ipcrm命令用来删除一个或更多的消息队列、信号量集或者共享内存标识。",
        "ipcrm[-mSharedMemoryID][-MSharedMemoryKey][-qMessageID][-QMessageKey][-sSemaphoreID][-SSemaphoreKey]",
        listOf(
            CmdExample("查看帮助信息", "ipcrm --help")
        ), "📌"
    ),
    LinuxCommand(
        "ipcs", CmdCategory.NETWORK, "ipcs命令用于报告Linux中进程间通信设施的状态，显示的信息包括消息列表、共享内存和信号量的信息。",
        "ipcs(选项)",
        listOf(
            CmdExample("查看帮助信息", "ipcs --help")
        ), "📌"
    ),
    LinuxCommand(
        "iptables-restore", CmdCategory.NETWORK, "iptables-restore命令用来还原iptables-save命令所备份的iptables配置。",
        "iptables-restore(选项)",
        listOf(
            CmdExample("查看帮助信息", "iptables-restore --help")
        ), "📌"
    ),
    LinuxCommand(
        "iptables-save", CmdCategory.NETWORK, "iptables-save命令用于将linux内核中的iptables表导出到标准输出设备商，通常，使用shell中I/O重定向功能将其输出保存到指定文件中。",
        "iptables-save(选项)",
        listOf(
            CmdExample("查看帮助信息", "iptables-save --help")
        ), "📌"
    ),
    LinuxCommand(
        "iptraf", CmdCategory.NETWORK, "iptraf命令可以实时地监视网卡流量，可以生成网络协议数据包信息、以太网信息、网络节点状态和ip校验和错误等信息。",
        "iptraf(选项)",
        listOf(
            CmdExample("查看帮助信息", "iptraf --help")
        ), "📌"
    ),
    LinuxCommand(
        "iptstate", CmdCategory.NETWORK, "iptstate命令以top指令类似的风格时显示Linux内核中iptables的工作状态。",
        "iptstate(选项)",
        listOf(
            CmdExample("查看帮助信息", "iptstate --help")
        ), "📌"
    ),
    LinuxCommand(
        "iscsiadm", CmdCategory.SYS_INFO, "iscsiadm命令用于管理 iSCSI（Internet Small Computer System Interface）存储连接。iSCSI 是一种基于网络的存储协议，允许在计算机之间传输块级别的数据。iscsiadm 命令提供了与 i",
        "iscsiadm [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "iscsiadm --help")
        ), "📌"
    ),
    LinuxCommand(
        "ispell", CmdCategory.SYS_INFO, "ispell命令用于检查文件中出现的拼写错误。",
        "ispell(参数)",
        listOf(
            CmdExample("查看帮助信息", "ispell --help")
        ), "📌"
    ),
    LinuxCommand(
        "jed", CmdCategory.SYS_INFO, "jed命令是由Slang所开发，其主要用用途是编辑程序的源代码。它支持彩色语法加亮显示，可以模拟emacs，EDT，wordstar和Brief编辑器。",
        "jed(选项)(参数)",
        listOf(
            CmdExample("以上下两个编辑区的方式，开启 mysource.c 原始代码文件。若要切换编辑区，可利用稍后介绍的命令，开启操作命令，开", "jed -2 mysource.c"),
            CmdExample("文件", "/usr/share/jed/lib/*.sl 这是默认的运行jed slang的文件。")
        ), "📌"
    ),
    LinuxCommand(
        "joe", CmdCategory.SYS_INFO, "joe命令是一款功能强大的纯文本编辑器，拥有众多编写程序和文本的优良特性。",
        "joe(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "joe --help")
        ), "📌"
    ),
    LinuxCommand(
        "jwhois", CmdCategory.SYS_INFO, "jwhoissearches Whois servers for the object on the command line.The host to query is taken from a global configuration f",
        "jwhois[选项]",
        listOf(
            CmdExample("显示指定用户信息：", "jwhois root"),
            CmdExample("查询域名信息：", "jwhois linuxde.net")
        ), "📌"
    ),
    LinuxCommand(
        "kernelversion", CmdCategory.SYS_INFO, "kernelversion命令用于打印当前Linux内核的主版本号。它是debianutils包的一部分，主要用于脚本中判断内核版本是否满足某些条件。",
        "kernelversion [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "kernelversion --help")
        ), "📌"
    ),
    LinuxCommand(
        "kexec", CmdCategory.SYS_INFO, "kexec命令是Linux内核的一个补丁，让您可以从当前正在运行的内核直接引导到一个新内核。在上面描述的引导序列中，kexec跳过了整个引导装载程序阶段（第一部分）并直接跳转到我们希望引导到的内核。不再有硬件的重启，不再有固件操作，不再涉及",
        "kexec(选项)",
        listOf(
            CmdExample("查看帮助信息", "kexec --help")
        ), "📌"
    ),
    LinuxCommand(
        "l2ping", CmdCategory.SYS_INFO, "l2ping命令用于通过发送 L2CAP 回显请求（Echo Request）并接收回显响应（Echo Response），来测试与目标蓝牙设备（由给定的蓝牙 MAC 地址指定）的底层连通性。它类似于网络中常用作检查 IP 连通性的ping",
        "l2ping [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "l2ping --help")
        ), "📌"
    ),
    LinuxCommand(
        "ld", CmdCategory.DEV_TOOLS, "ld命令是GNU的连接器，将目标文件连接为可执行程序。",
        "ld(选项)(参数)ld[options]objfile...",
        listOf(
            CmdExample("这告诉ld通过将文件/lib/crt0.o与hello.o和库libc.a链接起来，生成一个名为output的文件，该文", "ld -o <output> /lib/crt0.o hello.o -lc")
        ), "📌"
    ),
    LinuxCommand(
        "let", CmdCategory.SYS_INFO, "执行算术运算并赋值，Shell 内建命令",
        "let 表达式",
        listOf(
            CmdExample("整数加法运算", "let a=1+2"),
            CmdExample("自增", "let i++")
        ), "📌"
    ),
    LinuxCommand(
        "lftp", CmdCategory.SYS_INFO, "lftp命令是一款优秀的文件客户端程序，它支持ftp、SETP、HTTP和FTPs等多种文件传输协议。lftp支持tab自动补全，记不得命令双击tab键，就可以看到可能的选项了。",
        "lftp(选项)(参数)",
        listOf(
            CmdExample("登录ftp", "lftp 用户名:密码@ftp地址:传送端口（默认21）"),
            CmdExample("查看文件与改变目录", "ls"),
            CmdExample("get当然是可以的，还可以：", "mget -c *.pdf    #把所有的pdf文件以允许断点续传的方式下载。"),
            CmdExample("同样的put、mput都是对文件的操作，和下载类似。", "mirror -R 本地目录名"),
            CmdExample("模式设置", "set ftp:charset gbk")
        ), "📌"
    ),
    LinuxCommand(
        "lftpget", CmdCategory.SYS_INFO, "lftpget命令通过调用lftp指令下载指定的文件。",
        "lftpget(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "lftpget --help")
        ), "📌"
    ),
    LinuxCommand(
        "lha", CmdCategory.COMPRESS, "lha命令是从lharc演变而来的压缩程序，文件经它压缩后，会另外产生具有.lzh扩展名的压缩文件。",
        "lha [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "lha --help")
        ), "📌"
    ),
    LinuxCommand(
        "lilo", CmdCategory.SYS_INFO, "lilo命令用于安装核心载入，开机管理程序。lilo是个Linux系统核心载入程序，同时具备管理开机的功能。单独执行lilo指令，它会读取/etc/lilo.conf配置文件，然后根据其内容安装lilo。",
        "lilo(选项)",
        listOf(
            CmdExample("lilo.conf 示例文件：", "boot=/dev/hda")
        ), "📌"
    ),
    LinuxCommand(
        "lnstat", CmdCategory.DEBUG_TOOLS, "lnstat命令用来显示Linux系统的网路状态。",
        "lnstat(选项)",
        listOf(
            CmdExample("查看帮助信息", "lnstat --help")
        ), "📌"
    ),
    LinuxCommand(
        "local", CmdCategory.DEV_TOOLS, "在 Shell 函数内部声明局部变量，作用域仅限该函数",
        "local 变量名[=值]",
        listOf(
            CmdExample("在函数里声明局部变量", "myfunc() { local x=1; echo \$x; }")
        ), "📌"
    ),
    LinuxCommand(
        "logrotate", CmdCategory.SYS_INFO, "logrotate命令用于对系统日志进行轮转、压缩和删除，也可以将日志发送到指定邮箱。使用logrotate指令，可让你轻松管理系统所产生的记录文件。每个记录文件都可被设置成每日，每周或每月处理，也能在文件太大时立即处理。您必须自行编辑，指",
        "logrotate(选项)(参数)",
        listOf(
            CmdExample("在/etc/logrotate.d目录下创建任意后缀名的文件,即可使用对日志进行轮转", "/tmp/log/log.txt"),
            CmdExample("这个配置文件代表的意思是将/tmp/log/log.txt文件 进行轮转压缩", "compress                 通过gzip 压缩转储以后的日志")
        ), "📌"
    ),
    LinuxCommand(
        "logsave", CmdCategory.SYS_INFO, "logsave命令运行给定的命令，并将命令的输出信息保存到指定的日志文件中。",
        "logsave(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "logsave --help")
        ), "📌"
    ),
    LinuxCommand(
        "logwatch", CmdCategory.SYS_INFO, "logwatch命令是一个可定制和可插入式的日志监视系统，它通过遍历给定时间范围内的系统日志文件而产生日志报告。logwatch默认每天执行一次，可以从/etc/cron.daily里看到。",
        "logwatch(选项)",
        listOf(
            CmdExample("检查你的主机上是否已经存在Logwatch（Redhat默认已经安装了Logwatch，不过版本比较旧）：", "rpm -qa logwatch"),
            CmdExample("如果主机上没有logwatch，则执行：", "rpm -Ivh logwatch***.rpm"),
            CmdExample("如果有老版本的logwatch，则执行：", "rpm -Uvh logwatch***.rpm")
        ), "📌"
    ),
    LinuxCommand(
        "losetup", CmdCategory.FILE_MGMT, "losetup命令用来设置循环设备。循环设备可把文件虚拟成块设备，籍此来模拟整个文件系统，让用户得以将其视为硬盘驱动器，光驱或软驱等设备，并挂入当作目录来使用。",
        "losetup[-eencryption][-ooffset]loop_devicefilelosetup[-d]loop_device",
        listOf(
            CmdExample("创建空的磁盘镜像文件，这里创建一个1.44M的软盘：", "dd if=/dev/zero of=floppy.img bs=512 count=2880"),
            CmdExample("使用 losetup 将磁盘镜像文件虚拟成块设备：", "losetup /dev/loop1 floppy.img"),
            CmdExample("挂载块设备：", "mount /dev/loop0 /tmp"),
            CmdExample("卸载loop设备：", "umount /tmp")
        ), "📌"
    ),
    LinuxCommand(
        "lp", CmdCategory.SYS_INFO, "lp命令用于打印文件，或者修改排队的打印任务。与lpr命令类似，lp命令既支持文件输入也支持标准输入。它与lpr的不同之处在于它有一个不同（稍微复杂点）的参数选项设置。",
        "lp(选项)(参数)",
        listOf(
            CmdExample("要在连接在设备dlp0上的打印机lp0上打印文件/etc/motd，请输入：", "lp /etc/motd"),
            CmdExample("要使用文件的一个副本打印/etc/motd文件的30个副本，并且要用邮件通知用户作业完成，请输入：", "lp -c -m -n30 -dlp0:lpd0 /etc/motd"),
            CmdExample("要使用后端标志-f和-a并带上作业标题blah打印/etc/motd文件，请输入：", "lp -t \"blah\" -o -f -o -a /etc/motd"),
            CmdExample("要排队MyFile文件并返回作业编号，请输入：", "lp myfile"),
            CmdExample("要排队MyFile文件并禁止作业编号，请输入：", "lp -s myfile")
        ), "📌"
    ),
    LinuxCommand(
        "lpadmin", CmdCategory.SYS_INFO, "lpadmin命令用于配置CUPS套件中的打印机和类，也被用来设置打印服务器默认打印机。",
        "lpadmin(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "lpadmin --help")
        ), "📌"
    ),
    LinuxCommand(
        "lpc", CmdCategory.SYS_INFO, "lpc命令式命令行方式打印机控制程序，有5个内置命令。",
        "lpc",
        listOf(
            CmdExample("查看帮助信息", "lpc --help")
        ), "📌"
    ),
    LinuxCommand(
        "lpq", CmdCategory.SYS_INFO, "lpq命令用于显示打印队列中的打印任务的状态信息。",
        "lpq(选项)",
        listOf(
            CmdExample("查看帮助信息", "lpq --help")
        ), "📌"
    ),
    LinuxCommand(
        "lpr", CmdCategory.SYS_INFO, "lpr命令用于将文件发送给指定打印机进行打印，如果不指定目标打印机，则使用默认打印机。",
        "lpr(选项)(参数)",
        listOf(
            CmdExample("将man1和man2送到打印机lp进行打印：", "lpr -P lp man1 man2")
        ), "📌"
    ),
    LinuxCommand(
        "lprm", CmdCategory.SYS_INFO, "lprm命令用于删除打印队列中的打印任务。尚未完成的打印机任务会被放在打印机贮列之中，这个命令可用来将常未送到打印机的任务取消。",
        "lprm(选项)(参数)",
        listOf(
            CmdExample("将打印机hpprint中的第102号任务移除：", "lprm -Phpprint 102"),
            CmdExample("将第101号任务由预设打印机中移除：", "lprm 101")
        ), "📌"
    ),
    LinuxCommand(
        "lpstat", CmdCategory.DEBUG_TOOLS, "lpstat命令用于显示CUPS中打印机的状态信息。",
        "lpstat(选项)",
        listOf(
            CmdExample("查看帮助信息", "lpstat --help")
        ), "📌"
    ),
    LinuxCommand(
        "lsattr", CmdCategory.SYS_INFO, "lsattr命令用于查看文件的第二扩展文件系统属性。",
        "lsattr(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "lsattr --help")
        ), "📌"
    ),
    LinuxCommand(
        "lsmod", CmdCategory.SYS_INFO, "lsmod命令用于显示已经加载到内核中的模块的状态信息。执行lsmod命令后会列出所有已载入系统的模块。Linux操作系统的核心具有模块化的特性，应此在编译核心时，务须把全部的功能都放入核心。您可以将这些功能编译成一个个单独的模块，待需要时",
        "lsmod",
        listOf(
            CmdExample("查看帮助信息", "lsmod --help"),
            CmdExample("查看已加载模块", "lsmod | head")
        ), "📌"
    ),
    LinuxCommand(
        "lvcreate", CmdCategory.FILE_MGMT, "lvcreate命令用于创建LVM的逻辑卷。逻辑卷是创建在卷组之上的。逻辑卷对应的设备文件保存在卷组目录下，例如：在卷组\"vg1000\"上创建一个逻辑卷\"lvol0\"，则此逻辑卷对应的设备文件为\"/dev/vg1000/lvol0",
        "lvcreate(选项)(参数)",
        listOf(
            CmdExample("使用lvcreate命令在卷组\"vg1000\"上创建一个200MB的逻辑卷。在命令行中输入下面的命令：", "lvcreate -L 200M -n lvol0 vg1000    #创建大小为200M的逻辑卷")
        ), "📌"
    ),
    LinuxCommand(
        "lvdisplay", CmdCategory.FILE_MGMT, "lvdisplay命令用于显示LVM逻辑卷空间大小、读写状态和快照信息等属性。如果省略\"逻辑卷\"参数，则lvdisplay命令显示所有的逻辑卷属性。否则，仅显示指定的逻辑卷属性。",
        "lvdisplay(参数)",
        listOf(
            CmdExample("使用lvdisplay命令显示指定逻辑卷的属性。在命令行中输入下面的命令：", "lvdisplay /dev/vg1000/lvol0     #显示逻辑卷属性"),
            CmdExample("输出信息如下：", "--- Logical volume ---")
        ), "📌"
    ),
    LinuxCommand(
        "lvextend", CmdCategory.FILE_MGMT, "lvextend命令用于在线扩展逻辑卷的空间大小，而不中断应用程序对逻辑卷的访问。使用lvextend命令动态在线扩展磁盘空间，整个空间扩展过程对于应用程序来说是完全透明的。",
        "lvextend(选项)(参数)",
        listOf(
            CmdExample("使用lvextend命令为逻辑卷/dev/vg1000/lvol0增加100M空间。在命令行中输入下面的命令：", "lvextend -L +100M /dev/vg1000/lvol0    #为了解决增加100M空间"),
            CmdExample("输出信息如下：", "Extending logical volume lvol0 to 300.00 MB")
        ), "📌"
    ),
    LinuxCommand(
        "lvreduce", CmdCategory.FILE_MGMT, "lvreduce命令用于减少LVM逻辑卷占用的空间大小。使用lvreduce命令收缩逻辑卷的空间大小有可能会删除逻辑卷上已有的数据，所以在操作前必须进行确认。",
        "lvreduce(选项)(参数)",
        listOf(
            CmdExample("使用lvreduce命令减少指定的逻辑卷的空间大小。在命令行中输入下面的命令：", "lvreduce -L -50M /dev/vg1000/lvol0     #将逻辑卷的空间大小减少50M"),
            CmdExample("输出信息如下：", "......省略部分输出内容......")
        ), "📌"
    ),
    LinuxCommand(
        "lvremove", CmdCategory.FILE_MGMT, "lvremove命令用于删除指定LVM逻辑卷。如果逻辑卷已经使用mount命令加载，则不能使用lvremove命令删除。必须使用umount命令卸载后，逻辑卷方可被删除。",
        "lvremove(选项)(参数)",
        listOf(
            CmdExample("使用lvremove命令删除指定的逻辑卷。在命令行中输入下面的命令：", "lvremove /dev/vg1000/lvol0    #删除逻辑卷\"lvol0\""),
            CmdExample("输出信息如下：", "Do you really want to remove active logical")
        ), "📌"
    ),
    LinuxCommand(
        "lvresize", CmdCategory.FILE_MGMT, "lvresize命令用于调整LVM逻辑卷的空间大小，可以增大空间和缩小空间。使用lvresize命令调整逻辑卷空间大小和缩小空间时需要谨慎，因为它有可能导致数据丢失。",
        "lvresize(选项)(参数)",
        listOf(
            CmdExample("使用lvresize命令调整最大的逻辑卷大小。在命令行中输入下面的命令：", "lvresize -L +200M /dev/vg1000/lvol0     #将逻辑卷空间增加200M"),
            CmdExample("输出信息如下：", "Extending logical volume lvol0 to 280.00 MB")
        ), "📌"
    ),
    LinuxCommand(
        "lvscan", CmdCategory.FILE_MGMT, "lvscan命令用于扫描当前系统中存在的所有的LVM逻辑卷。使用lvscan指令可以发现系统中的所有逻辑卷，及其对应的设备文件。",
        "lvscan(选项)",
        listOf(
            CmdExample("使用lvscan命令扫描系统中的所有逻辑卷。在命令行中输入下面的命令：", "lvscan     #扫描所有的逻辑卷"),
            CmdExample("输出信息如下：", "ACTIVE          '/dev/vg1000/lvol0' [200.00 MB] inherit")
        ), "📌"
    ),
    LinuxCommand(
        "lynx", CmdCategory.SYS_INFO, "lynx命令是纯文本模式的网页浏览器，不支持图形、音视频等多媒体信息。",
        "lynx(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "lynx --help")
        ), "📌"
    ),
    LinuxCommand(
        "mail", CmdCategory.SYS_INFO, "mail命令是命令行的电子邮件发送和接收工具。操作的界面不像elm或pine那么容易使用，但功能非常完整。",
        "mail(选项)(参数)",
        listOf(
            CmdExample("直接使用shell当编辑器", "mail -s \"Hello from jsdig.com by shell\" admin@jsdig.com"),
            CmdExample("使用管道进行邮件发送", "echo \"hello,this is the content of mail.welcome to www.jsdig.com\" | mail -s \"Hello from jsdig.com by pipe\" admin@jsdig.com"),
            CmdExample("使用文件进行邮件发送", "mail -s \"Hello from jsdig.com by file\" admin@jsdig.com < mail.txt"),
            CmdExample("因为mail程序本身就是调用sendmail来进行邮件发送的，因此我们可以在mail命令中使用sendmail的参数进行", "mail -s \"Hello from jsdig.com with sender\" admin@jsdig.com -- -f user@jsdig.com<mail.txt"),
            CmdExample("安装完成后我们就可以来进行附件的发送了，使用如下命令：", "uuencode test.txt test | mail -s \"hello,see the attachement\" admin@jsdig.com<mail.txt")
        ), "📌"
    ),
    LinuxCommand(
        "mailq", CmdCategory.SYS_INFO, "mailq命令用户显示待发送的邮件队列，显示的每一个条目包括邮件队列id、邮件大小、加入队列时间、邮件发送者和接受者。如果邮件最后一次尝试后还没有将邮件投递出去，则显示发送失败的原因。",
        "mailq(选项)",
        listOf(
            CmdExample("查看帮助信息", "mailq --help")
        ), "📌"
    ),
    LinuxCommand(
        "mailstat", CmdCategory.DEBUG_TOOLS, "mailstat命令用来显示到达的邮件状态。",
        "mailstat(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mailstat --help")
        ), "📌"
    ),
    LinuxCommand(
        "mapfile", CmdCategory.TEXT_PROC, "把标准输入或文件的每一行读入数组变量，Shell 内建命令",
        "mapfile [选项] 数组名",
        listOf(
            CmdExample("把文件每行读入数组", "mapfile -t lines < file.txt")
        ), "📌"
    ),
    LinuxCommand(
        "mii-tool", CmdCategory.SYS_INFO, "mii-tool命令是用于查看、管理介质的网络接口的状态，有时网卡需要配置协商方式，比如10/100/1000M的网卡半双工、全双工、自动协商的配置。但大多数的网络设备是不用我们来修改协商，因为大多数网络设置接入的时候，都采用自动协商来解决",
        "usage: mii-tool[-VvRrwl][-A media,...|-Fmedia][interface...]",
        listOf(
            CmdExample("查看网络接口的协商状态：", "mii-tool -v eth0"),
            CmdExample("如果我们想把网络接口eth0改为1000Mb/s全双工的模式应该怎么办呢？", "mii-tool -F 100baseTx-FD"),
            CmdExample("注：是不是已经改过来了？当然，我们也一样用ethtool工具来更改，比如执行下面的命令：", "ethtool -s eth0 speed 100 duplex full")
        ), "📌"
    ),
    LinuxCommand(
        "mkbootdisk", CmdCategory.SYS_INFO, "mkbootdisk命令用来为当前运行的系统创建能够单独使用的系统引导软盘，以便在系统出现故障时能够启动操作进行适当的修复工作。",
        "mkbootdisk(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mkbootdisk --help")
        ), "📌"
    ),
    LinuxCommand(
        "mkcert", CmdCategory.DEV_TOOLS, "为本地开发环境快速生成受信任的 HTTPS 证书",
        "mkcert [选项] 域名",
        listOf(
            CmdExample("为本地域名生成受信任证书", "mkcert localhost 127.0.0.1"),
            CmdExample("安装本地根证书到系统信任库", "mkcert -install")
        ), "📌"
    ),
    LinuxCommand(
        "mke2fs", CmdCategory.SYS_INFO, "mke2fs命令被用于创建磁盘分区上的“etc2/etc3”文件系统。",
        "mke2fs(选项)(参数)",
        listOf(
            CmdExample("创建指定的ext2文件系统。", "mke2fs -q /dev/hda1")
        ), "📌"
    ),
    LinuxCommand(
        "mkinitrd", CmdCategory.SYS_INFO, "mkinitrd命令建立要载入ramdisk的映像文件，以供Linux开机时载入ramdisk。",
        "mkinitrd(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mkinitrd --help")
        ), "📌"
    ),
    LinuxCommand(
        "modetest", CmdCategory.SYS_INFO, "modetest是一个用于测试和验证 DRM（Direct Rendering Manager）驱动程序功能的命令行工具。",
        "modetest[选项]",
        listOf(
            CmdExample("使用modetest查看相关信息", "modetest")
        ), "📌"
    ),
    LinuxCommand(
        "modinfo", CmdCategory.SYS_INFO, "modinfo命令用于显示给定模块的详细信息。",
        "modinfo(选项)(参数)",
        listOf(
            CmdExample("显示sg模块的信息：", "modinfo sg")
        ), "📌"
    ),
    LinuxCommand(
        "modprobe", CmdCategory.SYS_INFO, "modprobe命令用于智能地向内核中加载模块或者从内核中移除模块。",
        "modprobe(选项)(参数)",
        listOf(
            CmdExample("查看modules的配置文件：", "modprobe -c"),
            CmdExample("这里，可以查看modules的配置文件，比如模块的alias别名是什么等。会打印许多行信息，例如其中的一行会类似如下：", "alias symbol:ip_conntrack_unregister_notifier ip_conntrack"),
            CmdExample("列出内核中所有已经或者未挂载的所有模块：", "modprobe -l"),
            CmdExample("挂载vfat模块：", "modprobe vfat"),
            CmdExample("移除已经加载的模块：", "modprobe -r 模块名")
        ), "📌"
    ),
    LinuxCommand(
        "mtools", CmdCategory.SYS_INFO, "mtools命令显示mtools支持的指令，mtools为MS-DOS文件系统的工具程序，可模拟许多MS-DOS的指令。这些指令都是mtools的符号连接，因此会有一些共同的特性。",
        "mtools(选项)",
        listOf(
            CmdExample("使用mtools命令显示其支持的所有的指令，输入如下命令：", "mtools     #显示所有支持的指令名称")
        ), "📌"
    ),
    LinuxCommand(
        "mysql", CmdCategory.SYS_INFO, "mysql命令是MySQL数据库服务器的客户端工具，它工作在命令行终端中，完成对远程MySQL数据库服务器的操作。",
        "mysql(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mysql --help"),
            CmdExample("导入 SQL 文件", "mysql -u root -p dbname < dbname.sql"),
            CmdExample("直接执行一条 SQL", "mysql -u root -p -e \"SHOW DATABASES;\"")
        ), "📌"
    ),
    LinuxCommand(
        "mysqladmin", CmdCategory.SYS_INFO, "mysqladmin命令是mysql服务器管理任务的客户端工具，它可以检查mytsql服务器的配置和当前工作状态，创建和删除数据库，创建用户和修改用户密码等操作。",
        "mysqladmin(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mysqladmin --help")
        ), "📌"
    ),
    LinuxCommand(
        "mysqldump", CmdCategory.SYS_INFO, "mysqldump命令是mysql数据库中备份工具，用于将MySQL服务器中的数据库以标准的sql语言的方式导出，并保存到文件中。",
        "mysqldump(选项)",
        listOf(
            CmdExample("导出整个数据库", "mysqldump -u 用户名 -p 数据库名 > 导出的文件名"),
            CmdExample("导出一个表", "mysqldump -u 用户名 -p 数据库名 表名> 导出的文件名"),
            CmdExample("导出一个数据库结构", "mysqldump -u linuxde -p -d --add-drop-table smgp_apps_linuxde > linuxde_db.sql"),
            CmdExample("备份单个数据库", "mysqldump -u root -p dbname > dbname.sql"),
            CmdExample("备份并压缩", "mysqldump -u root -p dbname | gzip > dbname.sql.gz"),
            CmdExample("备份所有数据库", "mysqldump -u root -p --all-databases > all.sql"),
            CmdExample("备份数据库并压缩", "mysqldump -u root -p mydb | gzip > mydb.sql.gz")
        ), "📌"
    ),
    LinuxCommand(
        "mysqlimport", CmdCategory.SYS_INFO, "mysqlimport命令为mysql数据库服务器提供了一种命令行方式导入数据工具，它从特定格式的文本文件中读取数据插入MySQL数据库表中。",
        "mysqlimport(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mysqlimport --help")
        ), "📌"
    ),
    LinuxCommand(
        "mysqlshow", CmdCategory.SYS_INFO, "mysqlshow命令用于显示mysql服务器中数据库、表和列表信息。",
        "mysqlshow(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "mysqlshow --help")
        ), "📌"
    ),
    LinuxCommand(
        "named-checkzone", CmdCategory.SYS_INFO, "named-checkzone命令可以进行区域文件有效性检查和转换，必须指定区域名称和区域文件名称。",
        "named-checkzone[选项][区域名][区域文件名]",
        listOf(
            CmdExample("查看帮助信息", "named-checkzone --help")
        ), "📌"
    ),
    LinuxCommand(
        "ncftp", CmdCategory.SYS_INFO, "ncftp命令是增强的的ftp工具，比传统的FTP指令更加强大。FTP让用户得以下载存放于服务器主机的文件，也能将文件上传到远端主机放置。ncftp是文字模式FTP程序的佼佼者，它具备多样特色，包括显示传输速率，下载进度，自动续传，标住书签",
        "ncftp(选项)(参数)",
        listOf(
            CmdExample("将本地/etc/目录内的所有文件和目录，上传到FTP服务器的flv/games/目录内(如果不存在flv/games/目", "/usr/local/ncftp/bin/ncftpput -u koumm -p koumm -P 21 -m -R 192.168.162.137  flv/games/ /etc/*")
        ), "📌"
    ),
    LinuxCommand(
        "nethogs", CmdCategory.NETWORK, "有很多适用于Linux系统的开源网络监视工具。比如说，你可以用命令iftop来检查带宽使用情况。netstat用来查看接口统计报告，还有top监控系统当前运行进程。但是如果你想要找一个能够按进程实时统计网络带宽利用率的工具，那么NetHog",
        "nethogs（选项）（参数）",
        listOf(
            CmdExample("查看帮助信息", "nethogs --help")
        ), "📌"
    ),
    LinuxCommand(
        "newusers", CmdCategory.SYS_INFO, "newusers命令用于批处理的方式一次创建多个命令。",
        "newusers(参数)",
        listOf(
            CmdExample("用法很简单，newusers后面直接跟一个文件，文件格式和/etc/passwd的格式相同。", "用户名1:x:UID:GID:用户说明:用户的家目录:所用SHELL"),
            CmdExample("举例：", "jingang0:x:520:520::/home/jingang0:/sbin/nologin"),
            CmdExample("值得一提的是关于SHELL类型，查看主机上所有SHELL，可以通过chsh来查看：", "chsh --list")
        ), "📌"
    ),
    LinuxCommand(
        "nfsstat", CmdCategory.NETWORK, "nfsstat命令用于列出NFS客户端和服务器的工作状态。",
        "nfsstat(选项)",
        listOf(
            CmdExample("要显示关于客户机发送和拒绝的RPC和NFS调用数目的信息，输入：", "nfsstat -c"),
            CmdExample("要显示和打印与客户机NFS调用相关的信息，输入如下命令：", "nfsstat -cn"),
            CmdExample("要显示和打印客户机和服务器的与RPC调用相关的信息，输入如下命令：", "nfsstat -r"),
            CmdExample("要显示关于服务器接收和拒绝的RPC和NFS调用数目的信息，输入如下命令：", "nfsstat –s")
        ), "📌"
    ),
    LinuxCommand(
        "ngrep", CmdCategory.SYS_INFO, "ngrep命令是grep命令的网络版，他力求更多的grep特征，用于搜寻指定的数据包。正由于安装ngrep需用到libpcap库， 所以支持大量的操作系统和网络协议。能识别TCP、UDP和ICMP包，理解bpf的过滤机制。",
        "ngrep<-LhNXViwqpevxlDtTRM><-IO pcap_dump><-n num><-d dev><-A num><-s snaplen><-S limitlen><-w normal|byline|single|none>",
        listOf(
            CmdExample("捕捉cloudian：18080端口的request和response，-W byline用来解析包中的换行符，否则包里", "ngrep -W byline -d lo port 18080"),
            CmdExample("捕捉amazon：80端口的request和response。-d eth0是用来监听对外的网卡：", "ngrep -W byline -d eth0 port 80"),
            CmdExample("可以用-d any来捕捉所有的包：", "ngrep '[a-zA-Z]' -t -W byline -d any tcp port 18080"),
            CmdExample("捕获字符串.flv，比如要查看在Web Flash 视频中的.flv文件的下载地址：", "ngrep -d3 -N -q \\.flv"),
            CmdExample("打开一个视频页面：", "T(6) 10.132.34.23:24860 -> 61.142.208.154:80 [AP]")
        ), "📌"
    ),
    LinuxCommand(
        "nisdomainname", CmdCategory.SYS_INFO, "nisdomainname命令用于显示主机NIS的域名。",
        "nisdomainname(选项)",
        listOf(
            CmdExample("查看帮助信息", "nisdomainname --help")
        ), "📌"
    ),
    LinuxCommand(
        "nmtui", CmdCategory.SYS_INFO, "nmtui是一款基于 curses 库的文本用户界面工具，全称为 NetworkManager Text User Interface。它是nmcli的图形化替代方案（在没有桌面环境的终端中运行），可以让用户通过键盘的上下左右方向键、回车键",
        "nmtui [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "nmtui --help")
        ), "📌"
    ),
    LinuxCommand(
        "nologin", CmdCategory.SYS_INFO, "nologin命令可以实现礼貌地拒绝用户登录系统，同时给出信息。如果尝试以这类用户登录，就在log里添加记录，然后在终端输出This account is currently not available信息，就是这样。一般设置这样的帐号是给",
        "nologin",
        listOf(
            CmdExample("1、禁止个别用户登录，比如禁止lynn用户登录。", "passwd -l lynn"),
            CmdExample("这就话的意思是锁定lynn用户，这样该用户就不能登录了。", "passwd -u lynn"),
            CmdExample("2、我们通过修改/etc/passwd文件中用户登录的shell", "vi /etc/passwd"),
            CmdExample("更改为：", "lynn:x:500:500::/home/lynn:/sbin/nologin"),
            CmdExample("3、禁止所有用户登录。", "touch /etc/nologin")
        ), "📌"
    ),
    LinuxCommand(
        "nstat", CmdCategory.DEBUG_TOOLS, "大多数命令行用户都熟悉 netstat ，这是 net-tools 软件包中的命令。目前新版本中 net-tools 软件包几乎完全被弃用，取而代之的是 ip 命令套件，而 nstat 属于新软件包。",
        "nstat [OPTION] [ PATTERN [ PATTERN ] ]",
        listOf(
            CmdExample("直接输入以查询网络接口状态，以下展示了 IPv4，IPv6，TCP，UDP，ICMP 的统计数据：", "nstat")
        ), "📌"
    ),
    LinuxCommand(
        "ntpdate", CmdCategory.SYS_INFO, "ntpdate命令是用来设置本地日期和时间。它从指定的每个服务器获得了一些样本，并应用标准 NTP 时钟过滤器和选择算法来选择最好的样本。",
        "ntpdate[-b][-d][-s][-u][-aKeyid][-eAuthenticationDelay][-kKeyFile][-oVersion][-pSamples][-tTimeOut]Server...",
        listOf(
            CmdExample("查看帮助信息", "ntpdate --help")
        ), "📌"
    ),
    LinuxCommand(
        "ntsysv", CmdCategory.SYS_INFO, "ntsysv命令提供了一个基于文本界面的菜单操作方式，集中管理系统不同的运行等级下的系统服务启动状态。在RedHat各个发行版，CentOS各个版本，都自带这个工具。它具有互动式操作界面，您可以轻易地利用方向键和空格键等，开启，关闭操作系统",
        "ntsysv(选项)",
        listOf(
            CmdExample("输入ntsysv命令后，出现一个交互式的管理菜单，如下：", "!ntsysv")
        ), "📌"
    ),
    LinuxCommand(
        "partprobe", CmdCategory.SYS_INFO, "partprobe命令用于重读分区表，当出现删除文件后，出现仍然占用空间。可以partprobe在不重启的情况下重读分区。",
        "partprobe(选项)(参数)",
        listOf(
            CmdExample("使用partprobe不重启系统添加新的磁盘分区，主机自带硬盘超过300GB，目前只划分使用了3个主分区，不到70GB，", "df -h"),
            CmdExample("第1步 添加新的磁盘分区：", "fdisk /dev/sda"),
            CmdExample("第2步 使用工具partprobe让kernel读取分区信息：", "partprobe"),
            CmdExample("第3步 格式化文件系统：", "mkfs.ext3 /dev/sda4"),
            CmdExample("第4步 mount新的分区/dev/sda4：", "e2label  /dev/sda4 /data")
        ), "📌"
    ),
    LinuxCommand(
        "pfctl", CmdCategory.SYS_INFO, "pfctl命令是PF防火墙的配置命令，PF防火墙( 全称：Packet Filter )是UNIX LIKE系统上进行TCP/ip流量过滤和网络地址转换的软件系统。PF同样也能提供TCP/IP流量的整形和控制，并且提供带宽控制和数据包优先集",
        "pfctl [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "pfctl --help")
        ), "📌"
    ),
    LinuxCommand(
        "php", CmdCategory.DEV_TOOLS, "php命令是流行的Web开发语言PHP的命令行接口，可以使用PHP语言开发基于命令行的系统管理脚本程序。",
        "php(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "php --help")
        ), "📌"
    ),
    LinuxCommand(
        "pico", CmdCategory.SYS_INFO, "pico命令是功能强大全屏幕的文本编辑器。pico的操作简单，提供了丰富的快捷键。常用的快捷键如下：",
        "pico(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "pico --help")
        ), "📌"
    ),
    LinuxCommand(
        "ping6", CmdCategory.NETWORK, "ping6命令ping6 是 ICMPv6 版的 ping 实现。正确使用、访问 IPv6 网站需要您提前配置本地环境，选择支持 v6 的网站进行访问(ping)。 执行 ping6 指令会使用 ICMPv6 传输(OSI)协议，发出要",
        "ping6(选项)(地址)[网络接口名称]",
        listOf(
            CmdExample("查看帮助信息", "ping6 --help")
        ), "📌"
    ),
    LinuxCommand(
        "pkexec", CmdCategory.SYS_INFO, "pkexec允许授权用户以另一个用户的身份执行 PROGRAM。如果未指定 PROGRAM，则将运行默认 shell。如果未指定用户名，则程序将以管理超级用户 root 的身份执行。",
        "pkexec[--version][--disable-internal-agent][--help]pkexec[--keep-cwd][--user username]PROGRAM[ARGUMENTS...]",
        listOf(
            CmdExample("查看帮助信息", "pkexec --help")
        ), "📌"
    ),
    LinuxCommand(
        "poweroff", CmdCategory.SYS_INFO, "关闭系统电源，立即安全关机。",
        "poweroff[选项]",
        listOf(
            CmdExample("查看帮助信息", "poweroff --help")
        ), "📌"
    ),
    LinuxCommand(
        "ppp-off", CmdCategory.SYS_INFO, "ppp-off命令是Slackware发行版内附的程序，让用户切断PPP（Point-to-Point Protocol，点对点协议）的网络连线。PPP是一种数据链路层协议，用于在两个节点之间建立直接的连接，常用于拨号上网或串行线路通信。",
        "ppp-off [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "ppp-off --help")
        ), "📌"
    ),
    LinuxCommand(
        "protoize", CmdCategory.SYS_INFO, "protoize命令属于gcc套件，用于为C语言源代码文件添加函数原型，将GNU-C代码转换为ANSI-C代码。",
        "protoize(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "protoize --help")
        ), "📌"
    ),
    LinuxCommand(
        "prtstat", CmdCategory.DEBUG_TOOLS, "prtstat命令打印指定进程的统计信息。这个信息来自/proc/PID/stat文件。",
        "prtstat [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "prtstat --help")
        ), "📌"
    ),
    LinuxCommand(
        "pssh", CmdCategory.SYS_INFO, "pssh命令是一个python编写可以在多台服务器上执行命令的工具，同时支持拷贝文件，是同类工具中很出色的，类似pdsh，个人认为相对pdsh更为简便，使用必须在各个服务器上配置好密钥认证访问。",
        "pssh [选项] [参数]",
        listOf(
            CmdExample("获取每台服务器的uptime：", "pssh -h ip.txt -i uptime"),
            CmdExample("查看每台服务器上mysql复制IO/SQL线程运行状态信息：", "pssh -h IP.txt -i \"/usr/local/mysql/bin/mysql -e 'show slave status \\G'\"|grep Running:"),
            CmdExample("保存每台服务器运行的结果：", "pssh -h IP.txt -i -o /tmp/pssh/ uptime"),
            CmdExample("我们来看一下/tmp/pssh/下的文件及其内容", "ll /tmp/pssh/")
        ), "📌"
    ),
    LinuxCommand(
        "pstack", CmdCategory.SYS_INFO, "pstack命令可显示每个进程的栈跟踪。pstack命令必须由相应进程的属主或root运行。可以使用pstack来确定进程挂起的位置。此命令允许使用的唯一选项是要检查的进程的PID。",
        "pstack [选项] [参数]",
        listOf(
            CmdExample("pstree以树结构显示进程", "pstree -p work | grep ad"),
            CmdExample("work为工作用户，-p为显示进程识别码，ad_preprocess共启动了6个子线程，加上主线程共7个线程。", "ps -Lf 4551"),
            CmdExample("pstack显示每个进程的栈跟踪：", "pstack 4551")
        ), "📌"
    ),
    LinuxCommand(
        "pushd", CmdCategory.FILE_MGMT, "把当前目录压入目录栈并切换到新目录，配合 popd 使用",
        "pushd 目录",
        listOf(
            CmdExample("切换到某目录并记住原目录", "pushd /var/log"),
            CmdExample("返回上一个目录", "popd")
        ), "📌"
    ),
    LinuxCommand(
        "pvchange", CmdCategory.FILE_MGMT, "pvchange命令允许管理员改变物理卷的分配许可。如果物理卷出现故障，可以使用pvchange命令禁止分配物理卷上的PE。",
        "pvchange(选项)(参数)",
        listOf(
            CmdExample("使用pvchange命令禁止分配指定物理卷上的PE。在命令行中输入下面的命令：", "pvchange -x n /dev/sdb1     #禁止分配\"/dev/sdb1\"上的PE")
        ), "📌"
    ),
    LinuxCommand(
        "pvck", CmdCategory.FILE_MGMT, "pvck命令用来检测物理卷的LVM元数据的一致性。默认情况下，物理卷中的前4个扇区保存着LVM卷标，可以使用--labelsector选项指定其他的位置（例如：数据恢复时）。",
        "pvck(选项)(参数)",
        listOf(
            CmdExample("使用pvck命令检查物理卷/dev/sdb1。在命令行中输入下面的命令：", "pvck -v /dev/sdb1    #检查物理卷元数据")
        ), "📌"
    ),
    LinuxCommand(
        "pvcreate", CmdCategory.FILE_MGMT, "pvcreate命令用于将物理硬盘分区初始化为物理卷，以便LVM使用。",
        "pvcreate(选项)(参数)",
        listOf(
            CmdExample("查看磁盘信息：", "fdisk -l"),
            CmdExample("检查有无 PV 在系统上，然后将/dev/hda6到/dev/hda9建立成为PV格式", "pvscan"),
            CmdExample("将6-9分区转成pv，注意大括号的用途：", "pvcreate /dev/hda{6,7,8,9}"),
            CmdExample("更详细的列示出系统上面每个 PV 信息：", "pvdisplay")
        ), "📌"
    ),
    LinuxCommand(
        "pvdisplay", CmdCategory.FILE_MGMT, "pvdisplay命令用于显示物理卷的属性。pvdisplay命令显示的物理卷信息包括：物理卷名称、所属的卷组、物理卷大小、PE大小、总PE数、可用PE数、已分配的PE数和UUID。",
        "pvdisplay(选项)(参数)",
        listOf(
            CmdExample("使用pvdisplay命令显示指定的物理卷的基本信息。在命令行中输入下面的命令：", "pvdisplay /dev/sdb1    #显示物理卷基本信息"),
            CmdExample("输出信息如下：", "\"/dev/sdb1\" is a new physical volume of \"101.94 MB\"")
        ), "📌"
    ),
    LinuxCommand(
        "pvremove", CmdCategory.FILE_MGMT, "pvremove命令用于删除一个存在的物理卷。使用pvremove指令删除物理卷时，它将LVM分区上的物理卷信息删除，使其不再被视为一个物理卷。",
        "pvremove(选项)(参数)",
        listOf(
            CmdExample("使用pvremove指令删除物理卷/dev/sdb2。在命令行中输入下面的命令：", "pvremove /dev/sdb2 # 删除物理卷")
        ), "📌"
    ),
    LinuxCommand(
        "pvs", CmdCategory.FILE_MGMT, "pvs命令用于输出格式化的物理卷信息报表。使用pvs命令仅能得到物理卷的概要信息，如果要得到更加详细的信息可以使用pvdisplay命令。",
        "pvs(选项)(参数)",
        listOf(
            CmdExample("使用pvs命令显示系统中所有物理卷的信息报表。在命令行中输入下面的命令：", "输出物理卷信息报表"),
            CmdExample("输出信息如下：", "PV         VG     fmt  Attr PSize   PFree")
        ), "📌"
    ),
    LinuxCommand(
        "pvscan", CmdCategory.FILE_MGMT, "pvscan命令会扫描系统中连接的所有硬盘，列出找到的物理卷列表。使用pvscan命令的-n选项可以显示硬盘中的不属于任何卷组的物理卷，这些物理卷是未被使用的。",
        "pvscan(选项)",
        listOf(
            CmdExample("使用pvscan命令扫描当前系统中所有硬盘的物理卷，在命令行中输入下面的命令：", "pvscan     #扫描所有硬盘上的物理卷"),
            CmdExample("输出信息如下：", "PV /dev/sdb1         lvm2 [101.94 MB]")
        ), "📌"
    ),
    LinuxCommand(
        "pwck", CmdCategory.PERM_USER, "pwck命令用来验证系统认证文件/etc/passwd和/etc/shadow的内容和格式的完整性。",
        "pwck(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "pwck --help")
        ), "📌"
    ),
    LinuxCommand(
        "pwconv", CmdCategory.PERM_USER, "pwconv命令用来开启用户的投影密码。Linux系统里的用户和群组密码，分别存放在名称为passwd和group的文件中， 这两个文件位于/etc目录下。因系统运作所需，任何人都得以读取它们，造成安全上的破绽。投影密码将文件内的密码改存在",
        "pwconv",
        listOf(
            CmdExample("此时可以发现密码段是x", "cat /etc/shadow | grep test")
        ), "📌"
    ),
    LinuxCommand(
        "pwunconv", CmdCategory.PERM_USER, "pwunconv命令与pwconv功能相反，用来关闭用户的投影密码。它会把密码从shadow文件内，重回存到passwd文件里。",
        "pwunconv",
        listOf(
            CmdExample("查看帮助信息", "pwunconv --help")
        ), "📌"
    ),
    LinuxCommand(
        "quota", CmdCategory.SYS_INFO, "quota命令用于显示用户或者工作组的磁盘配额信息。输出信息包括磁盘使用和配额限制。",
        "quota(选项)(参数)",
        listOf(
            CmdExample("重新remount filesystem来驱动设定值。", "umount /dev/hdb1"),
            CmdExample("事实上，也可以利用 mount 的 remount 功能。", "mount -o remount /disk2"),
            CmdExample("扫瞄磁盘的使用者使用状况，并产生重要的 aquota.group 与 aquota.user：", "quotacheck -avug"),
            CmdExample("启动 quota 的限额：", "quotaon -avug"),
            CmdExample("编辑使用者的可使用空间：", "edquota -u quser1")
        ), "📌"
    ),
    LinuxCommand(
        "quotacheck", CmdCategory.SYS_INFO, "quotacheck命令通过扫描指定的文件系统，获取磁盘的使用情况，创建、检查和修复磁盘配额（quota）文件。执行quotacheck指令，扫描挂入系统的分区，并在各分区的文件系统根目录下产生quota.user和quota.group文",
        "quotacheck(选项)(参数)",
        listOf(
            CmdExample("将所有的在/etc/mtab内，含有quota支持的partition进行扫描：", "quotacheck -avug"),
            CmdExample("强制扫描已挂载的filesystem：", "quotacheck -avug -m"),
            CmdExample("扫描指定的filesystem：", "quotacheck -cvug /disk2")
        ), "📌"
    ),
    LinuxCommand(
        "quotaoff", CmdCategory.SYS_INFO, "quotaoff命令用于关闭Linux内核中指定文件系统的磁盘配额功能。",
        "quotaoff(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "quotaoff --help")
        ), "📌"
    ),
    LinuxCommand(
        "quotaon", CmdCategory.SYS_INFO, "quotaon命令执行quotaon指令可开启磁盘对用户和群组的空间使用限制，但在开启前，各分区的文件系统根目录必须存在通过quotacheck命令创建的quota配置文件。",
        "quotaon(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "quotaon --help")
        ), "📌"
    ),
    LinuxCommand(
        "rcconf", CmdCategory.SYS_INFO, "rcconf命令是Debian Linux下的运行等级服务配置工具，用以设置在特定的运行等级下系统服务的启动配置。",
        "rcconf(选项)",
        listOf(
            CmdExample("查看帮助信息", "rcconf --help")
        ), "📌"
    ),
    LinuxCommand(
        "rcp", CmdCategory.SYS_INFO, "rcp命令使在两台Linux主机之间的文件复制操作更简单。通过适当的配置，在两台Linux主机之间复制文件而无需输入密码，就像本地文件复制一样简单。",
        "rcp(选项)(参数)",
        listOf(
            CmdExample("将当前目录下的 test1 复制到名为 webserver1 的远程系统：", "rcp test1 webserver1:/home/root/test3"),
            CmdExample("从远程系统复制文件：要将远程系统中的文件复制到本地目录下：", "rcp remote_hostname:remote_file local_fileEnter"),
            CmdExample("将远程系统 webserver1中的 test2 复制到当前目录：", "rcp webserver1:/home/root/test2 .Enter"),
            CmdExample("如果希望用新名称复制文件，请提供目标文件名。如果希望将 test2 复制到本地系统中的其他目录下，请使用以下绝对或相对路", "rcp webserver1:/home/root/test2 otherdir/ Enter")
        ), "📌"
    ),
    LinuxCommand(
        "readonly", CmdCategory.SYS_INFO, "把变量设置为只读，之后无法修改，Shell 内建命令",
        "readonly 变量名[=值]",
        listOf(
            CmdExample("声明一个只读变量", "readonly PATH_BACKUP=/usr/bin")
        ), "📌"
    ),
    LinuxCommand(
        "reject", CmdCategory.SYS_INFO, "reject命令属于CUPS套件，用于指示打印系统拒绝发往指定目标打印机的打印任务。",
        "reject(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "reject --help")
        ), "📌"
    ),
    LinuxCommand(
        "reposync", CmdCategory.PACKAGE, "把远程 yum/dnf 仓库的软件包同步到本地目录",
        "reposync [选项]",
        listOf(
            CmdExample("同步指定仓库到本地", "reposync --repoid=base --download-path=/local/repo")
        ), "📌"
    ),
    LinuxCommand(
        "repquota", CmdCategory.SYS_INFO, "repquota命令以报表的格式输出指定分区，或者文件系统的磁盘配额信息。",
        "repquota(选项)(参数)",
        listOf(
            CmdExample("显示所有文件系统的磁盘使用情况", "repquota -a")
        ), "📌"
    ),
    LinuxCommand(
        "restore", CmdCategory.COMPRESS, "restore命令是dump命令的逆过程，用于还原dump命令生成的备份文件。倾倒操作可用来备份文件，而还原操作则是写回这些已备份的文件。",
        "restore(选项)",
        listOf(
            CmdExample("用restore命令来恢复备份：", "restore rf /dev/hda3 /home/frank"),
            CmdExample("用restore命令来查看备份文件里的文件列表：", "restore ft /dev/hda3")
        ), "📌"
    ),
    LinuxCommand(
        "restorecon", CmdCategory.COMPRESS, "restorecon命令用来恢复SELinux文件属性即恢复文件的安全上下文。",
        "restorecon[-iFnrRv][-e excludedir][-o filename][-f filename|pathname...]",
        listOf(
            CmdExample("使用CentOS举例,如果默认没有安装apache，确保网络连接，使用下面的命令安装", "yum install httpd"),
            CmdExample("使用ls -Z也可以看出,文件和目录的SELinux信息不匹配", "ls -Z /var/www/html/"),
            CmdExample("使用restorecon来恢复网页主目录中所有文件的SELinux配置信息(如果目标为一个目录，可以添加-R参数递归)", "restorecon -R /var/www/html/")
        ), "📌"
    ),
    LinuxCommand(
        "return", CmdCategory.DEV_TOOLS, "从 Shell 函数中返回，可指定返回值，Shell 内建命令",
        "return [状态码]",
        listOf(
            CmdExample("函数内提前返回", "myfunc() { [ -z \"$1\" ] && return 1; echo ok; }")
        ), "📌"
    ),
    LinuxCommand(
        "rexec", CmdCategory.SYS_INFO, "rexec命令用于在指定的远程Linux系统主机上执行命令，向远程rexec服务器发出执行命令的请求。",
        "rexec(选项)(参数)",
        listOf(
            CmdExample("要在一个远程主机上执行date命令，输入：", "rexec host1 date"),
            CmdExample("要重设自动登录功能并执行远程主机上的date命令，输入：", "rexec -nhost1 date"),
            CmdExample("列出远程主机上另一个用户的目录，输入：", "rexec host1 ls -l /home/karen")
        ), "📌"
    ),
    LinuxCommand(
        "rlogin", CmdCategory.SYS_INFO, "rlogin命令用于从当前终端登录到远程Linux主机。",
        "rlogin(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "rlogin --help")
        ), "📌"
    ),
    LinuxCommand(
        "rmmod", CmdCategory.SYS_INFO, "rmmod命令用于从当前运行的内核中移除指定的内核模块。执行rmmod指令，可删除不需要的模块。Linux操作系统的核心具有模块化的特性，应此在编译核心时，务须把全部的功能都放如核心。你可以将这些功能编译成一个个单独的模块，待有需要时再分别",
        "rmmod(选项)(参数)",
        listOf(
            CmdExample("用rmmod命令主要用于卸载正在使用的Linux内核模块，与modprobe -r命令相似，如下所示：", "lsmod | grep raid1")
        ), "📌"
    ),
    LinuxCommand(
        "route", CmdCategory.NETWORK, "route命令用来显示并设置Linux内核中的网络路由表，route命令设置的路由主要是静态路由。要实现两个不同的子网之间的通信，需要一台连接两个网络的路由器，或者同时位于两个网络的网关来实现。",
        "route(选项)(参数)",
        listOf(
            CmdExample("显示当前路由：", "route")
        ), "📌"
    ),
    LinuxCommand(
        "rpm2cpio", CmdCategory.PACKAGE, "rpm2cpio命令用于将rpm软件包转换为cpio格式的文件。",
        "rpm2cpio(参数)",
        listOf(
            CmdExample("查看帮助信息", "rpm2cpio --help")
        ), "📌"
    ),
    LinuxCommand(
        "rpmbuild", CmdCategory.PACKAGE, "rpmbuild命令被用于创建rpm的二进制软件包和源码软件包。",
        "rpmbuild(选项)",
        listOf(
            CmdExample("查看帮助信息", "rpmbuild --help")
        ), "📌"
    ),
    LinuxCommand(
        "rpmdb", CmdCategory.PACKAGE, "rpmdb命令用于初始化和重建rpm数据库。",
        "rpmdb(选项)",
        listOf(
            CmdExample("查看帮助信息", "rpmdb --help")
        ), "📌"
    ),
    LinuxCommand(
        "rpmquery", CmdCategory.PACKAGE, "rpmquery命令使用多种依据从rpm数据库中查询软件包信息。",
        "rpmquery(选项)",
        listOf(
            CmdExample("使用rpmquery命令查询指定文件所属的软件包：", "rpmquery -qf /usr/bin/htpasswd")
        ), "📌"
    ),
    LinuxCommand(
        "rpmsign", CmdCategory.PACKAGE, "rpmsign命令使用rpm软件包的签名管理工具。",
        "rpmsign(选项)",
        listOf(
            CmdExample("查看帮助信息", "rpmsign --help")
        ), "📌"
    ),
    LinuxCommand(
        "rpmverify", CmdCategory.PACKAGE, "rpmverify命令用来验证已安装的rpm软件包的正确性。",
        "rpmverify(选项)",
        listOf(
            CmdExample("查看帮助信息", "rpmverify --help")
        ), "📌"
    ),
    LinuxCommand(
        "rsh", CmdCategory.SYS_INFO, "rsh命令用于连接到远程的指定主机并执行指定的命令。",
        "rsh(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "rsh --help")
        ), "📌"
    ),
    LinuxCommand(
        "scriptreplay", CmdCategory.SYS_INFO, "scriptreplay用于在终端中，根据script命令记录的终端数据文件和时间日志文件，重现当时用户的所有操作和命令的输出信息。简而言之，重现播放当时终端会话发生的一切信息，而不是重新运行一遍命令。例如，用户当时在输入某条命令时，字符的",
        "scriptreplay[options][-t]timingfile[typescript[divisor]]",
        listOf(
            CmdExample("记录终端内容到文件", "zfb@localhost:~$ script -t 2>time.file -a -f command.log"),
            CmdExample("重新播放终端内容", "zfb@localhost:~$ scriptreplay -d 1 -m 2 -t time.file -s command.log")
        ), "📌"
    ),
    LinuxCommand(
        "seinfo", CmdCategory.SYS_INFO, "seinfo命令是用来查询SELinux的策略提供多少相关规则，一个主体进程能否读取到目标文件资源的重点是在于SELinux的策略以及策略内的各项规则，然后再通过该规则的定义去处理各项目标文件的安全上下文，尤其是“类型”部分。SELinux",
        "seinfo（选项）",
        listOf(
            CmdExample("列出与httpd有关的规则：", "seinfo -b | grep httpd")
        ), "📌"
    ),
    LinuxCommand(
        "semanage", CmdCategory.PERM_USER, "semanage命令是用来查询与修改SELinux默认目录的安全上下文。SELinux的策略与规则管理相关命令：seinfo命令、sesearch命令、getsebool命令、setsebool命令、semanage命令。",
        "semanage{login|user|port|interface|fcontext|translation}-lsemanage fcontext -{a|d|m}[-frst]file_spec",
        listOf(
            CmdExample("查询一下/var/www/html的默认安全性本文的设置：", "semanage fcontext -l"),
            CmdExample("用semanage命令设置/srv/samba目录的默认安全性本文为public_content_t：", "mkdir /srv/samba"),
            CmdExample("如上所示，默认的情况应该是var_t这个咚咚的！", "semanage fcontext -l | grep '/srv'"),
            CmdExample("上面则是默认的/srv底下的安全性本文资料，不过，并没有指定到/srv/samba。", "semanage fcontext -a -t public_content_t \"/srv/samba(/.*)?\"")
        ), "📌"
    ),
    LinuxCommand(
        "sendmail", CmdCategory.SYS_INFO, "sendmail命令是一款著名的电子邮件传送代理程序，也就是平常说的电子邮件服务器，它基于标准的简单邮件传输协议（SMTP）。",
        "sendmail(选项)",
        listOf(
            CmdExample("查看帮助信息", "sendmail --help")
        ), "📌"
    ),
    LinuxCommand(
        "service", CmdCategory.SYS_INFO, "service命令是Redhat Linux兼容的发行版中用来控制系统服务的实用工具，它以启动、停止、重新启动和关闭系统服务，还可以显示所有系统服务的当前状态。",
        "service(选项)(参数)",
        listOf(
            CmdExample("当修改了主机名、ip地址等信息时，经常需要把网络重启使之生效。", "service network status"),
            CmdExample("重启mysql", "service mysqld status")
        ), "📌"
    ),
    LinuxCommand(
        "sesearch", CmdCategory.SYS_INFO, "使用seinfo命令可以查询SELinux的策略提供多少相关规则，如果查到的相关类型或者布尔值，想要知道详细规则时，使用sesearch命令查询。SELinux的策略与规则管理相关命令：seinfo命令、sesearch命令、getsebo",
        "sesearch[-a][-s 主体类型][-t 目标类型][-b 布尔值]",
        listOf(
            CmdExample("找出目标文件资源类型为httpd_sys_content_t的有关信息：", "sesearch -a -t httpd_sys_content_t"),
            CmdExample("找出主体进程为httpd_t且目标文件类型为httpd相关的所有信息：", "sesearch -s httpd_t -t httpd_* -a"),
            CmdExample("查看布尔值httpd_enable_homedirs设置了多少规则", "sesearch -b httpd_enable_homedirs -a")
        ), "📌"
    ),
    LinuxCommand(
        "setpci", CmdCategory.SYS_INFO, "setpci命令是一个查询和配置PCI设备的使用工具。",
        "setpci(选项)(参数)",
        listOf(
            CmdExample("首先进入终端输入lspci命令，列出各种设备的地址：", "lspci"),
            CmdExample("发现00:02.0是VGA设备，于是我们修改它的属性：", "sudo setpci -s 00:02.0 F4.B=FF")
        ), "📌"
    ),
    LinuxCommand(
        "setsebool", CmdCategory.PERM_USER, "setsebool命令是用来修改SElinux策略内各项规则的布尔值。setsebool命令和getsebool命令是SELinux修改和查询布尔值的一套工具组。SELinux的策略与规则管理相关命令：seinfo命令、sesearch命令",
        "setsebool[-P]布尔值=[0|1]",
        listOf(
            CmdExample("允许vsftp匿名用户写入权限：", "setsebool -P allow_ftpd_anon_write=1"),
            CmdExample("如果你希望你的ftp用户可以访问自己的家目录的话，需要开启：", "setsebool -P ftp_home_dir 1"),
            CmdExample("如果你希望将vsftpd以daemon的方式运行的话，需要开启：", "setsebool -P ftpd_is_daemon 1"),
            CmdExample("你可以让SElinux停止保护vsftpd的daemon方式动行：", "setsebool -P ftpd_disable_trans 1"),
            CmdExample("HTTP被设置允许cgi的设置：", "setsebool -P httpd_enable_cgi 1")
        ), "📌"
    ),
    LinuxCommand(
        "sftp", CmdCategory.SYS_INFO, "sftp命令是一款交互式的文件传输程序，命令的运行和使用方式与ftp命令相似，但是，sftp命令对传输的所有信息使用ssh加密，它还支持公钥认证和压缩等功能。",
        "sftp(选项)(参数)",
        listOf(
            CmdExample("建立联接", "sftp username@1.1.1.1 # 回车输入密码"),
            CmdExample("获取文件下载到指定路径", "sftp> get /export/sftp/test.csv /Users/my/Downloads"),
            CmdExample("上传本地文件到服务器指定路径", "sftp> put /Users/my/Downloads/re-produce.gif /export/sftp")
        ), "📌"
    ),
    LinuxCommand(
        "sftp-server", CmdCategory.SYS_INFO, "sftp-server命令是一个\"SFTP\"协议的服务器端程序，它使用加密的方式进行文件传输。它是OpenSSH套件的一部分，通常在后台由sshd守护进程调用，作为SFTP子系统的后端实现。",
        "sftp-server [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "sftp-server --help")
        ), "📌"
    ),
    LinuxCommand(
        "shift", CmdCategory.DEV_TOOLS, "把脚本的位置参数整体左移，常用于遍历命令行参数",
        "shift [n]",
        listOf(
            CmdExample("每次处理第一个参数后左移", "while [ $# -gt 0 ]; do echo $1; shift; done")
        ), "📌"
    ),
    LinuxCommand(
        "shopt", CmdCategory.SYS_INFO, "shopt命令用于显示和设置shell中的行为选项，通过这些选项以增强shell易用性。shopt命令若不带任何参数选项，则可以显示所有可以设置的shell操作选项。",
        "shopt(选项)(参数)",
        listOf(
            CmdExample("使用shopt命令显示当前所有可以设置的shell操作选项，输入如下命令：", "输出所有可以设置的shell操作选项"),
            CmdExample("如图上所示，选项\"cdspell\"的状态为\"off\"，即关闭cd拼写检查选项。现在，可以使用shopt命令将其开启，输入", "shopt -s cdspell          #开启cd拼写检查"),
            CmdExample("执行上面的命令后，该选项的状态将变为\"on\"，即开启状态。可以再次通过该命令显示一下shell操作选项即可，输出信息如下", "cdspell on                #开启cdspell选项")
        ), "📌"
    ),
    LinuxCommand(
        "showmount", CmdCategory.SYS_INFO, "showmount命令查询“mountd”守护进程，以显示NFS服务器加载的信息。",
        "showmount(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "showmount --help")
        ), "📌"
    ),
    LinuxCommand(
        "skill", CmdCategory.SYS_INFO, "skill命令用于向选定的进程发送信号，冻结进程。这个命令初学者并不常用，深入之后牵涉到系统服务优化之后可能会用到。",
        "skill(选项)",
        listOf(
            CmdExample("如果您发现了一个占用大量CPU和内存的进程，但又不想停止它，该怎么办？考虑下面的top命令输出：", "top -c -p 16514"),
            CmdExample("既然您确认进程16514占用了大量内存，您就可以使用skill命令“冻结”它，而不是停止它。", "skill -STOP 1"),
            CmdExample("之后，检查top输出：", "23:01:11  up 12 days,  2:05,  4 users,  load average: 1.20, 0.54, 0.38"),
            CmdExample("现在，CPU 从 0% 空闲变为 94% 空闲。该进程被有效冻结。过一段时间之后，您可能希望唤醒该进程：", "skill -CONT 16514"),
            CmdExample("此命令用途很广。如果您要停止 \"oracle\" 用户的所有进程，只需要一个命令即可实现：", "skill -STOP oracle")
        ), "📌"
    ),
    LinuxCommand(
        "slabtop", CmdCategory.DEBUG_TOOLS, "slabtop命令以实时的方式显示内核“slab”缓冲区的细节信息。",
        "slabtop(选项)",
        listOf(
            CmdExample("查看帮助信息", "slabtop --help")
        ), "📌"
    ),
    LinuxCommand(
        "slocate", CmdCategory.SYS_INFO, "slocate命令是一个命令查找文件或目录。slocate本身具有一个数据库，里面存放了系统中文件与目录的相关信息。",
        "slocate[-u][--help][--version][-d<目录>][查找的文件]",
        listOf(
            CmdExample("使用指令\"slocate\"显示文件名中含有关键字\"fdisk\"的文件路径信息，输入如下命令：", "slocate fdisk #显示文件名中含有fdisk关键字的文件的路径信息"),
            CmdExample("执行以上命令后，指令执行的输出信息如下：", "slocate fdisk #显示文件名中含有fdisk 关键字的文件的路径信息")
        ), "📌"
    ),
    LinuxCommand(
        "smartmontools", CmdCategory.SYS_INFO, "监控和管理硬盘 S.M.A.R.T. 健康状态的工具集（含 smartctl）",
        "smartctl [选项] 设备",
        listOf(
            CmdExample("查看硬盘健康摘要", "smartctl -H /dev/sda"),
            CmdExample("查看完整S.M.A.R.T.信息", "smartctl -a /dev/sda")
        ), "📌"
    ),
    LinuxCommand(
        "smbclient", CmdCategory.NETWORK, "smbclient命令属于samba套件，它提供一种命令行使用交互式方式访问samba服务器的共享资源。",
        "smbclient(选项)(参数)",
        listOf(
            CmdExample("列出某个IP地址所提供的共享文件夹", "smbclient -L 198.168.0.1 -U username%password"),
            CmdExample("像ftp客户端一样使用smbclient", "smbclient //192.168.0.1/tmp  -U username%password"),
            CmdExample("直接一次性使用smbclient命令", "smbclient -c \"ls\"  //192.168.0.1/tmp  -U username%password"),
            CmdExample("创建一个共享文件夹", "smbclient -c \"mkdir share1\" //192.168.0.1/tmp -U username%password")
        ), "📌"
    ),
    LinuxCommand(
        "smbpasswd", CmdCategory.NETWORK, "smbpasswd命令属于samba套件，能够实现添加或删除samba用户和为用户修改密码。",
        "smbpasswd(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "smbpasswd --help")
        ), "📌"
    ),
    LinuxCommand(
        "sosreport", CmdCategory.PACKAGE, "sosreport命令是一个配置和数据收集实用程序，用于收集系统配置、日志文件和诊断信息，以便进行故障排除和分析。它将信息打包到一个压缩的tar文件中，便于传输和分析。sosreport是Red Hat Enterprise Linux系统",
        "sosreport[选项]",
        listOf(
            CmdExample("收集系统诊断信息：", "sosreport"),
            CmdExample("批处理模式收集信息（无需用户交互）：", "sosreport --batch"),
            CmdExample("详细模式收集信息：", "sosreport -v"),
            CmdExample("启用所有插件选项收集完整信息：", "sosreport -a"),
            CmdExample("只收集网络相关信息：", "sosreport -o network")
        ), "📌"
    ),
    LinuxCommand(
        "spell", CmdCategory.SYS_INFO, "spell命令对文件进行拼写检查，并把拼写错误的单词输出。",
        "spell(参数)",
        listOf(
            CmdExample("查看帮助信息", "spell --help")
        ), "📌"
    ),
    LinuxCommand(
        "squid", CmdCategory.SYS_INFO, "squid命令高性能的Web客户端代理缓存服务器套件“squid”的服务器守护进程。",
        "squid(选项)",
        listOf(
            CmdExample("查看帮助信息", "squid --help")
        ), "📌"
    ),
    LinuxCommand(
        "squidclient", CmdCategory.SYS_INFO, "squidclient命令使用squid服务器的客户端管理工具，它可以查看squid服务器的详细运行信息和管理squid服务器。",
        "squidclient(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "squidclient --help")
        ), "📌"
    ),
    LinuxCommand(
        "startx", CmdCategory.SYS_INFO, "startx命令用来启动X Window，实际上启动X Window的程序为xinit。",
        "startx(参数)",
        listOf(
            CmdExample("要在工作站上或 X 终端上启动 X 会话，请输入：", "startx"),
            CmdExample("要在工作站上强制启动 X 会话，请输入：", "startx -w"),
            CmdExample("要为 X 终端启动 X 会话，并注销用户的 telnet 会话，请输入：", "startx; kill -9 $"),
            CmdExample("要使用 .xinitrc 脚本启动 X 会话，请输入：", "startx -x .xinitrc"),
            CmdExample("要使用 mwm 窗口管理器启动 X 会话，请输入：", "startx -m mwm")
        ), "📌"
    ),
    LinuxCommand(
        "supervisord", CmdCategory.PROC_MGMT, "进程管理守护进程，用于监控和自动重启后台服务",
        "supervisord [选项]",
        listOf(
            CmdExample("以配置文件启动supervisord", "supervisord -c /etc/supervisord.conf"),
            CmdExample("查看被管理的进程状态", "supervisorctl status")
        ), "📌"
    ),
    LinuxCommand(
        "suspend", CmdCategory.PROC_MGMT, "把当前 Shell 挂起（暂停），Shell 内建命令",
        "suspend",
        listOf(
            CmdExample("挂起当前Shell", "suspend")
        ), "📌"
    ),
    LinuxCommand(
        "swapoff", CmdCategory.FILE_MGMT, "swapoff命令用于关闭指定的交换空间（包括交换文件和交换分区）。swapoff实际上为swapon的符号连接，可用来关闭系统的交换区。",
        "swapoff(选项)(参数)",
        listOf(
            CmdExample("关闭交换分区", "swapoff /dev/sda2")
        ), "📌"
    ),
    LinuxCommand(
        "swapon", CmdCategory.FILE_MGMT, "swapon命令用于激活Linux系统中交换空间，Linux系统的内存管理必须使用交换区来建立虚拟内存。",
        "swapon(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "swapon --help")
        ), "📌"
    ),
    LinuxCommand(
        "syslog", CmdCategory.SYS_INFO, "syslog是Linux系统默认的日志守护进程。默认的syslog配置文件是/etc/syslog.conf文件。程序，守护进程和内核提供了访问系统的日志信息。因此，任何希望生成日志信息的程序都可以向 syslog 接口呼叫生成该信息。",
        "syslog [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "syslog --help")
        ), "📌"
    ),
    LinuxCommand(
        "systool", CmdCategory.SYS_INFO, "systool命令指令显示基于总线、类和拓扑显示系统中设备的信息。",
        "systool(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "systool --help")
        ), "📌"
    ),
    LinuxCommand(
        "tailf", CmdCategory.SYS_INFO, "tailf命令几乎等同于tail -f，严格说来应该与tail --follow=name更相似些。当文件改名之后它也能继续跟踪，特别适合于日志文件的跟踪（follow the growth of a log file）。与tail -f不",
        "tailf logfile# 动态跟踪日志文件logfile，最初的时候打印文件的最后10行内容。",
        listOf(
            CmdExample("查看帮助信息", "tailf --help")
        ), "📌"
    ),
    LinuxCommand(
        "talk", CmdCategory.NETWORK, "talk命令是talk服务器的客户端工具，通过talk命令可以让用户和其他用户在终端里实时聊天，只要知道对方地址即可邀请交谈。",
        "talk 用户名@主机",
        listOf(
            CmdExample("邀请远程主机上的用户交谈", "talk wangxz@tirc.cs.test.com"),
            CmdExample("接到邀请后回应对方发起交谈", "talk jdx@rs6000.cic.test.com")
        ), "📌"
    ),
    LinuxCommand(
        "tcpdump", CmdCategory.NETWORK, "tcpdump命令是一款抓包，嗅探器工具，它可以打印所有经过网络接口的数据包的头信息，也可以使用-w选项将数据包保存到文件中，方便以后分析。",
        "tcpdump(选项)",
        listOf(
            CmdExample("直接启动tcpdump将监视第一个网络接口上所有流过的数据包", "tcpdump"),
            CmdExample("监视指定网络接口的数据包", "tcpdump -i eth1"),
            CmdExample("打印所有进入或离开sundown的数据包。", "tcpdump host sundown"),
            CmdExample("也可以指定ip,例如截获所有210.27.48.1 的主机收到的和发出的所有的数据包", "tcpdump host 210.27.48.1"),
            CmdExample("打印helios 与 hot 或者与 ace 之间通信的数据包", "tcpdump host helios and \\( hot or ace \\)"),
            CmdExample("抓取指定端口的包", "sudo tcpdump -i eth0 port 80"),
            CmdExample("抓取与某主机的通信", "sudo tcpdump -nn host 1.2.3.4"),
            CmdExample("保存为 pcap 文件", "sudo tcpdump -i any -w out.pcap"),
            CmdExample("显示包内容（ASCII）", "sudo tcpdump -A -s0 port 80"),
            CmdExample("查看与某主机的通信", "sudo tcpdump -nn host 192.168.1.10")
        ), "📌"
    ),
    LinuxCommand(
        "tcpreplay", CmdCategory.NETWORK, "tcpreplay用于重放保存在 pcap 文件中的网络流量，它支持按照捕获 pcap 文件时数据包的速度、或者指定速度去重放网络流量，只要在硬件承受的范围内即可。",
        "tcpreplay [选项] [参数]",
        listOf(
            CmdExample("b、 将 ftp.pcap 文件进行 tcpprep 操作，制作 cache 文件。", "tcpprep -an client -i ftp.pcap -o ftp.cache –v"),
            CmdExample("c、 将 DUT 设备的两个接口和 PC 的两个接口使用网线连接，使用 tcpreplay 重 放报文。注意防火墙的配置", "tcpreplay -c ftp.cache -i eth0 -j eth1 ftp.pcap -R –v"),
            CmdExample("b、将 bt.pcap 文件进行 tcpprep 操作，制作 cache 文件。", "tcpprep -an client -i bt.pcap -o bt.cache -C \"100M BT Packet\" –v"),
            CmdExample("c、使用 tcpreplay 重放报文。", "tcpreplay -c bt.cache -i eth0 -j eth1 bt.pcap -v –R"),
            CmdExample("b、将 pcap 文件进行 tcpprep 的操作，制作 cache 文件。", "tcpprep -an server -i tftp.pcap -o tftp.cache –v")
        ), "📌"
    ),
    LinuxCommand(
        "telinit", CmdCategory.SYS_INFO, "telinit命令用于切换当前正在运行的Linux系统的运行等级。",
        "telint(选项)(参数)telinit[OPTION]... RUNLEVEL",
        listOf(
            CmdExample("查看帮助信息", "telinit --help")
        ), "📌"
    ),
    LinuxCommand(
        "tempfile", CmdCategory.SYS_INFO, "有时候在写Shell脚本的时候需要一些临时存储数据的才做，最适合存储临时文件数据的位置就是/tmp，因为该目录中所有的内容在系统重启后就会被清空。下面是两种方法为临时数据生成标准的文件名。",
        "tempfile [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "tempfile --help")
        ), "📌"
    ),
    LinuxCommand(
        "tftp", CmdCategory.SYS_INFO, "tftp命令用在本机和tftp服务器之间使用TFTP协议传输文件。",
        "tftp(选项)(参数)",
        listOf(
            CmdExample("如果能上网，通过yum安装：", "yum install xinetd"),
            CmdExample("如果不能上网，可以直接安装提供的rpm包：", "rpm -ivh xinetd-2.3.14-18.fc9.i386.rpm"),
            CmdExample("修改/etc/xinetd.d/tftp文件，将其中的disable=yes改为disable=no。主要是设置TFTP", "service tftp"),
            CmdExample("3、启动tftp服务器并关闭防火墙", "/etc/init.d/iptables stop        # 关闭防火墙"),
            CmdExample("4、查看tftp服务是否开启", "netstat -a | grep tftp")
        ), "📌"
    ),
    LinuxCommand(
        "times", CmdCategory.SYS_INFO, "显示当前 Shell 及其子进程累计使用的用户/系统时间",
        "times",
        listOf(
            CmdExample("查看Shell累计耗时", "times")
        ), "📌"
    ),
    LinuxCommand(
        "tload", CmdCategory.SYS_INFO, "tload命令以图形化的方式输出当前系统的平均负载到指定的终端。假设不给予终端机编号，则会在执行tload指令的终端机显示负载情形。",
        "tload(选项)(参数)",
        listOf(
            CmdExample("使用tload命令查看系统负载情况：", "tload -d 1")
        ), "📌"
    ),
    LinuxCommand(
        "tracepath", CmdCategory.DEBUG_TOOLS, "tracepath命令用来追踪并显示报文到达目的主机所经过的路由信息。",
        "tracepath(参数)",
        listOf(
            CmdExample("查看帮助信息", "tracepath --help")
        ), "📌"
    ),
    LinuxCommand(
        "unarj", CmdCategory.SYS_INFO, "unarj命令用来解压缩由arj命令创建的压缩包。",
        "unarj(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "unarj --help")
        ), "📌"
    ),
    LinuxCommand(
        "unprotoize", CmdCategory.SYS_INFO, "unprotoize命令属于gcc套件，用于删除C语言源代码文件中的函数原型。",
        "unprotoize(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "unprotoize --help")
        ), "📌"
    ),
    LinuxCommand(
        "unset", CmdCategory.SYS_INFO, "删除已定义的变量或函数",
        "unset 变量名",
        listOf(
            CmdExample("删除一个环境变量", "unset MY_VAR"),
            CmdExample("删除一个函数", "unset -f myfunc")
        ), "📌"
    ),
    LinuxCommand(
        "usernetctl", CmdCategory.PERM_USER, "usernetctl命令在用于被允许时操作指定的网络接口。",
        "usernetctl(参数)",
        listOf(
            CmdExample("查看帮助信息", "usernetctl --help")
        ), "📌"
    ),
    LinuxCommand(
        "uucico", CmdCategory.SYS_INFO, "uucico命令命令UUCP文件传输服务程序。 uucico是用来处理uucp或uux送到队列的文件传输工具。uucico有两种工作模式：主动模式和附属模式。当在主动模式下时，uucico会调用远端主机；在附属模式下时，uucico则接受远",
        "uucico[-cCDefqvwz][-i<类型>][-I<文件>][-p<连接端口号码>][-][-rl][-s<主机>][-S<主机>][-u<用户>][-x<类型>][--help]",
        listOf(
            CmdExample("使用主动模式启动uucico服务。在命令提示符下直接输入如下命令：", "uucico-r1")
        ), "📌"
    ),
    LinuxCommand(
        "uupick", CmdCategory.SYS_INFO, "uupick命令处理传送进来的文件。 当其他主机通过UUCP将文件传送进来时，可利用uupick指令取出这些文件。",
        "uupick[-v][-I<配置文件>][-s<主机>][-x<层级>][--help]",
        listOf(
            CmdExample("查看帮助信息", "uupick --help")
        ), "📌"
    ),
    LinuxCommand(
        "uuto", CmdCategory.SYS_INFO, "uuto命令为script文件，它实际上会执行uucp，用来将文件传送到远端UUCP主机，并在完成工作后，以邮件通知远端主机上的用户。",
        "uuto[文件][目的]",
        listOf(
            CmdExample("查看帮助信息", "uuto --help")
        ), "📌"
    ),
    LinuxCommand(
        "vdfuse", CmdCategory.SYS_INFO, "vdfuse命令是VirtualBox软件挂载VDI分区文件的一个工具，VirtualBox是一款能创建虚拟机的开源软件，vdi是它的默认磁盘格式。",
        "vdfuse[options]-fimage-file mountpoint",
        listOf(
            CmdExample("使用如下如下语句挂载.vdi文件：", "sudo vdfuse -f /path/to/file.vdi /path/to/mountpoint"),
            CmdExample("/path/to/mountpoint应该包含如下文件EntireDisk、Partition1等，如果只有一个文件，你", "mount /path/to/mountpoint/Partition1 /path/to/someother/mountpoint")
        ), "📌"
    ),
    LinuxCommand(
        "vgchange", CmdCategory.FILE_MGMT, "vgchange命令用于修改卷组的属性，经常被用来设置卷组是处于活动状态或非活动状态。处于活动状态的卷组无法被删除，必须使用vgchange命令将卷组设置为非活动状态后才能删除。",
        "vgchange(选项)(参数)",
        listOf(
            CmdExample("使用vgchange命令将卷组状态改为活动的。在命令行中输入下面的命令：", "vgchange -ay vg1000     #将卷组\"vg1000\"设置为活动状态"),
            CmdExample("输出信息如下：", "1 logical volume(s) in volume group \"vg1000\" now active")
        ), "📌"
    ),
    LinuxCommand(
        "vgconvert", CmdCategory.FILE_MGMT, "vgconvert命令用于转换指定LVM卷组的元数据格式，通常将“LVM1”格式的卷组转换为“LVM2”格式。转换卷组元数据前必须保证卷组处于非活动状态，否则无法完成转换操作。",
        "vgconvert(选项)(参数)",
        listOf(
            CmdExample("转换卷组元数据格式前，使用vgchange命令将卷组设置为非活动状态。在命令行中输入下面的命令：", "vgchange -an vg1000    #设置卷组状态为非活动状态"),
            CmdExample("使用vgchange命令将卷组设置为活动状态。在命令行中输入下面的命令：", "vgchange -ay vg1000     #设置卷组状态为活动状态")
        ), "📌"
    ),
    LinuxCommand(
        "vgcreate", CmdCategory.FILE_MGMT, "vgcreate命令用于创建LVM卷组。卷组（Volume Group）将多个物理卷组织成一个整体，屏蔽了底层物理卷细节。在卷组上创建逻辑卷时不用考虑具体的物理卷信息。",
        "vgcreate(选项)(参数)",
        listOf(
            CmdExample("使用vgcreate命令创建卷组 \"vg1000\"，并且将物理卷/dev/sdb1和/dev/sdb2添加到卷组中。在命", "vgcreate vg1000 /dev/sdb1 /dev/sdb2  #创建卷组\"vg1000\"")
        ), "📌"
    ),
    LinuxCommand(
        "vgdisplay", CmdCategory.FILE_MGMT, "vgdisplay命令用于显示LVM卷组的信息。如果不指定\"卷组\"参数，则分别显示所有卷组的属性。",
        "vgdisplay(选项)(参数)",
        listOf(
            CmdExample("使用vgdisplay命令显示存在的卷组\"vg1000\"的属性。在命令行中输入下面的命令：", "vgdisplay vg1000     #显示卷组\"vg1000\"的属性"),
            CmdExample("输出信息如下：", "--- Volume group ---")
        ), "📌"
    ),
    LinuxCommand(
        "vgextend", CmdCategory.FILE_MGMT, "vgextend命令用于动态扩展LVM卷组，它通过向卷组中添加物理卷来增加卷组的容量。LVM卷组中的物理卷可以在使用vgcreate命令创建卷组时添加，也可以使用vgextend命令动态的添加。",
        "vgextend(选项)(参数)",
        listOf(
            CmdExample("使用vgextend命令向卷组\"vg2000\"中添加物理卷。在命令行中输入下面的命令：", "vgextend vg2000 /dev/sdb2     #将物理卷\"/dev/sdb2\"加入卷组\"vg2000\"")
        ), "📌"
    ),
    LinuxCommand(
        "vgreduce", CmdCategory.FILE_MGMT, "vgreduce命令通过删除LVM卷组中的物理卷来减少卷组容量。不能删除LVM卷组中剩余的最后一个物理卷。",
        "vgreduce(选项)(参数)",
        listOf(
            CmdExample("使用vgreduce命令从卷组\"vg2000\"中移除物理卷/dev/sdb2。在命令行中输入下面的命令：", "vgreduce vg2000 /dev/sdb2    #将物理卷\"/dev/sdb2\"从卷组\"vg2000\"中删除"),
            CmdExample("输出信息如下：", "Removed \"/dev/sdb2\" from volume group \"vg2000\"")
        ), "📌"
    ),
    LinuxCommand(
        "vgremove", CmdCategory.FILE_MGMT, "vgremove命令用于用户删除LVM卷组。当要删除的卷组上已经创建了逻辑卷时，vgremove命令需要进行确认删除，防止误删除数据。",
        "vgremove(选项)(参数)",
        listOf(
            CmdExample("使用vgremove命令删除LVM卷组\"vg1000\"。在命令行中输入下面的命令：", "vgremove vg1000    #删除卷组\"vg1000\"")
        ), "📌"
    ),
    LinuxCommand(
        "vgrename", CmdCategory.FILE_MGMT, "vgrename命令可以重命名LVM卷组的名称。",
        "vgrename[选项][旧卷组路径|旧卷组名称|旧卷组UUID][新卷组路径|新卷组名称]",
        listOf(
            CmdExample("查看帮助信息", "vgrename --help")
        ), "📌"
    ),
    LinuxCommand(
        "vgscan", CmdCategory.FILE_MGMT, "vgscan命令查找系统中存在的LVM卷组，并显示找到的卷组列表。vgscan命令仅显示找到的卷组的名称和LVM元数据类型，要得到卷组的详细信息需要使用vgdisplay命令。",
        "vgscan(选项)",
        listOf(
            CmdExample("使用vgscan命令扫描系统中所有的卷组。在命令行中输入下面的命令：", "vgscan     #扫描并显示LVM卷组列表"),
            CmdExample("输出信息如下：", "Found volume group \"vg2000\" using metadata type lvm2")
        ), "📌"
    ),
    LinuxCommand(
        "volname", CmdCategory.SYS_INFO, "volname命令用于显示指定的“ISO-9660”格式的设备的卷名称，通常这种格式的设备为光驱。",
        "volname(参数)",
        listOf(
            CmdExample("查看帮助信息", "volname --help")
        ), "📌"
    ),
    LinuxCommand(
        "wall", CmdCategory.NETWORK, "wall命令用于向系统当前所有打开的终端上输出信息。通过wall命令可将信息发送给每位同意接收公众信息的终端机用户，若不给予其信息内容，则wall命令会从标准输入设备读取数据，然后再把所得到的数据传送给所有终端机用户。",
        "wall(参数)",
        listOf(
            CmdExample("查看帮助信息", "wall --help")
        ), "📌"
    ),
    LinuxCommand(
        "whatis", CmdCategory.SYS_INFO, "whatis命令是用于查询一个命令执行什么功能，并将查询结果打印到终端上。",
        "whatis",
        listOf(
            CmdExample("查看帮助信息", "whatis --help")
        ), "📌"
    ),
    LinuxCommand(
        "write", CmdCategory.NETWORK, "write命令用于向指定登录用户终端上发送信息。通过write命令可传递信息给另一位登入系统的用户，当输入完毕后，键入EOF表示信息结束，write命令就会将信息传给对方。如果接收信息的用户不只登入本地主机一次，你可以指定接收信息的终端机编",
        "write(参数)",
        listOf(
            CmdExample("传信息给Rollaend，此时Rollaend只有一个连线 :", "write Rollaend"),
            CmdExample("传信息给Rollaend、Rollaend的连线有pts/2、pts/3：", "write Rollaend pts/2")
        ), "📌"
    ),
    LinuxCommand(
        "xauth", CmdCategory.SYS_INFO, "xauth命令用于显示和编辑被用于连接X服务器的认证信息。",
        "xauth(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "xauth --help")
        ), "📌"
    ),
    LinuxCommand(
        "xclip", CmdCategory.SYS_INFO, "在 X 系统里面，从一个窗口复制一段文字到另一个窗口，有两套机制，分别是 Selections 和 cut buffers。",
        "xclip [选项] [参数]",
        listOf(
            CmdExample("查看帮助信息", "xclip --help")
        ), "📌"
    ),
    LinuxCommand(
        "xhost", CmdCategory.SYS_INFO, "xhost命令是X服务器的访问控制工具，用来控制哪些X客户端能够在X服务器上显示。该命令必须从有显示连接的机器上运行。可以通过使用-host参数，从访问列表中除去一个名称。不要从访问列表中除去当前的名称。如果已经这样做了，请在作出任何更改之",
        "xhost(参数)",
        listOf(
            CmdExample("查看帮助信息", "xhost --help")
        ), "📌"
    ),
    LinuxCommand(
        "xinit", CmdCategory.SYS_INFO, "xinit命令是Linux下X-Window系统的初始化程序，主要完成X服务器的初始化设置。",
        "xinit(参数)",
        listOf(
            CmdExample("查看帮助信息", "xinit --help")
        ), "📌"
    ),
    LinuxCommand(
        "xlsatoms", CmdCategory.SYS_INFO, "xlsatoms命令用于列出X服务器内部所有定义的原子成分，每个原子成分都有自身的编号。可利用参数设置列表范围，或直接指定欲查询的成分名称。",
        "xlsatoms(选项)",
        listOf(
            CmdExample("查看帮助信息", "xlsatoms --help")
        ), "📌"
    ),
    LinuxCommand(
        "xlsclients", CmdCategory.SYS_INFO, "xlsclients命令用来列出显示器中的客户端应用程序。",
        "xlsclients(选项)",
        listOf(
            CmdExample("查看帮助信息", "xlsclients --help")
        ), "📌"
    ),
    LinuxCommand(
        "xlsfonts", CmdCategory.SYS_INFO, "xlsfonts命令列出X Server使用的字体，也能使用范本样式仅列出的符合条件的字体。",
        "xlsfonts(选项)",
        listOf(
            CmdExample("查看帮助信息", "xlsfonts --help")
        ), "📌"
    ),
    LinuxCommand(
        "xrandr", CmdCategory.SYS_INFO, "xrandr命令RandR（Rotate and Resize，旋转与缩放）是一个X窗口系统扩展，允许客户端动态调整（即缩放、旋转、翻转）屏幕。xrandr是一款官方的randr扩展配置工具。",
        "xrandr(选项)(参数)",
        listOf(
            CmdExample("测试配置，列出可用设备及其信息", "xrandr"),
            CmdExample("克隆屏幕", "xrandr --output HDMI-1 --auto"),
            CmdExample("多显示器关闭不需要的", "xrandr --output HDMI-1 --off --output HDMI-2 --auto"),
            CmdExample("指定分辨率和刷新率", "xrandr --output HDMI-1 --mode 1920x1080 --rate 60"),
            CmdExample("VGA1位于HDMI1左侧，均使用最佳分辨率，分屏显示", "xrandr --output VGA1 --auto --output HDMI1 --auto --right-of VGA1")
        ), "📌"
    ),
    LinuxCommand(
        "xset", CmdCategory.SYS_INFO, "xset命令是设置X-Window系统中的用户爱好的实用工具。",
        "xset(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "xset --help")
        ), "📌"
    ),
    LinuxCommand(
        "ypdomainname", CmdCategory.SYS_INFO, "ypdomainname命令显示主机的NIS的域名。",
        "ypdomainname(选项)",
        listOf(
            CmdExample("查看帮助信息", "ypdomainname --help")
        ), "📌"
    ),
    LinuxCommand(
        "zfore", CmdCategory.SYS_INFO, "zfore命令强制为gzip格式的压缩文件添加“.gz”后缀。",
        "zfore(参数)",
        listOf(
            CmdExample("查看帮助信息", "zfore --help")
        ), "📌"
    ),
    LinuxCommand(
        "zipinfo", CmdCategory.COMPRESS, "zipinfo命令用来列出压缩文件信息。执行zipinfo指令可得知zip压缩文件的详细信息。",
        "zipinfo(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "zipinfo --help")
        ), "📌"
    ),
    LinuxCommand(
        "zipsplit", CmdCategory.COMPRESS, "zipsplit命令用于将较大的“zip”压缩包分割成各个较小的“zip”压缩包。",
        "zipsplit(选项)(参数)",
        listOf(
            CmdExample("查看帮助信息", "zipsplit --help")
        ), "📌"
    ),
)

/** 按分类分组的指令 */
val commandsByCategory: Map<CmdCategory, List<LinuxCommand>> =
    allLinuxCommands.groupBy { it.category }

/** Linux 指令数量统计 */
val totalCommandCount = allLinuxCommands.size
val categoryCount = CmdCategory.entries.size



