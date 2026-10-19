package com.example.p65020d

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.p65020d.ui.theme.AppTheme

/** 用于代码块的高亮颜色 */
private val CodeBlockBg = Color(0xFF1E1E2E)
private val CodeBlockText = Color(0xFFCDD6F4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                LinuxManualApp()
            }
        }
    }
}

// ============================================================
//  导航状态：底部三个 tab + 覆盖层（搜索/分类详情/指令详情）
// ============================================================
enum class BottomTab(val label: String, val icon: ImageVector) {
    HOME("首页", Icons.Filled.Home),
    CATEGORIES("分类", Icons.Filled.List),
    FAVORITES("终端", Icons.Filled.Info)
}

sealed class Overlay {
    data object Search : Overlay()
    data class CategoryDetail(val category: CmdCategory) : Overlay()
    data class CommandDetail(val command: LinuxCommand) : Overlay()
}

// ============================================================
//  主入口
// ============================================================
@Composable
fun LinuxManualApp() {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(BottomTab.HOME) }
    var overlay by remember { mutableStateOf<Overlay?>(null) }
    var showAbout by remember { mutableStateOf(false) }

    val favoriteNames = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) { favoriteNames.addAll(loadFavorites(context)) }

    val recentNames = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) { recentNames.addAll(loadRecentlyViewed(context)) }

    val onToggleFavorite: (LinuxCommand) -> Unit = remember(context, favoriteNames) {
        { cmd: LinuxCommand -> toggleFavorite(context, cmd.name, favoriteNames) }
    }
    val onCommandClick: (LinuxCommand) -> Unit = remember(context, recentNames) {
        { cmd: LinuxCommand ->
            overlay = Overlay.CommandDetail(cmd)
            addRecentlyViewed(context, cmd.name, recentNames)
        }
    }

    // 拦截系统返回手势/返回键：有覆盖层（搜索/分类详情/指令详情）时，
    // 先关闭覆盖层回到当前 tab，而不是直接把整个 App 退出到桌面。
    BackHandler(enabled = overlay != null) {
        overlay = null
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            val imeUp = WindowInsets.ime.getBottom(LocalDensity.current) > 0
            if (overlay == null && !imeUp) {
                BottomNavBar(selected = selectedTab, onSelect = { selectedTab = it })
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val o = overlay) {
                null -> {
                    when (selectedTab) {
                        BottomTab.HOME -> HomeScreen(
                            favoriteCount = favoriteNames.size,
                            onInfoClick = { showAbout = true },
                            onSearchClick = { overlay = Overlay.Search },
                            onSeeAllCategories = { selectedTab = BottomTab.CATEGORIES },
                            favoriteNames = favoriteNames,
                            onToggleFavorite = onToggleFavorite,
                            onCommandClick = onCommandClick,
                            recentNames = recentNames,
                            onClearRecent = { clearRecentlyViewed(context, recentNames) }
                        )
                        BottomTab.CATEGORIES -> CategoriesScreen(
                            onCategoryClick = { cat -> overlay = Overlay.CategoryDetail(cat) }
                        )
                        BottomTab.FAVORITES -> TerminalScreen(innerPadding)
                    }
                }
                is Overlay.Search -> SearchScreen(
                    favoriteNames = favoriteNames,
                    onToggleFavorite = onToggleFavorite,
                    onBack = { overlay = null },
                    onCommandClick = onCommandClick
                )
                is Overlay.CategoryDetail -> CategoryDetailScreen(
                    category = o.category,
                    favoriteNames = favoriteNames,
                    onToggleFavorite = onToggleFavorite,
                    onBack = { overlay = null },
                    onCommandClick = onCommandClick
                )
                is Overlay.CommandDetail -> CommandDetailScreen(
                    command = o.command,
                    isFavorite = o.command.name in favoriteNames,
                    onToggleFavorite = { onToggleFavorite(o.command) },
                    onBack = { overlay = null }
                )
            }
        }
    }

    if (showAbout) {
        val uriHandler = LocalUriHandler.current
        AlertDialog(
            onDismissRequest = { showAbout = false },
            confirmButton = {
                TextButton(onClick = { showAbout = false }) { Text("知道了") }
            },
            title = { Text("Linux 指令手册") },
            text = {
                Column(
                    modifier = Modifier
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("收录 $totalCommandCount 条常用 Linux 指令，覆盖 $categoryCount 个分类，随手即查、可离线使用。")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "源代码",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "https://github.com/poqq26566-cmyk/linux-manual",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            uriHandler.openUri("https://github.com/poqq26566-cmyk/linux-manual")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "数据来源",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "https://github.com/jaywcjlove/linux-command",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            uriHandler.openUri("https://github.com/jaywcjlove/linux-command")
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "感谢该项目的维护者和贡献者们提供的优质内容。",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
    }
}

// ============================================================
//  底部导航栏
// ============================================================
@Composable
fun BottomNavBar(selected: BottomTab, onSelect: (BottomTab) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelect(tab) },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label, fontSize = 12.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

// ============================================================
//  首页
// ============================================================
@Composable
fun HomeScreen(
    favoriteCount: Int,
    onInfoClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSeeAllCategories: () -> Unit,
    favoriteNames: List<String>,
    onToggleFavorite: (LinuxCommand) -> Unit,
    onCommandClick: (LinuxCommand) -> Unit,
    recentNames: List<String> = emptyList(),
    onClearRecent: () -> Unit = {}
) {
    val popular = remember { allLinuxCommands.take(8) }
    // recentNames 是同一个 SnapshotStateList 实例，不能当 remember 的 key，
    // 否则清空后不会重新计算；直接读取即可，列表变化时会自动重组
    val recentCommands = recentNames.mapNotNull { name -> allLinuxCommands.find { it.name == name } }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            // 顶部小标题栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onInfoClick) {
                    Icon(Icons.Filled.Info, contentDescription = "关于", modifier = Modifier.size(22.dp))
                }
                Text(
                    text = "Linux 指令手册",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Filled.Search, contentDescription = "搜索", modifier = Modifier.size(22.dp))
                }
            }
        }

        item {
            HeroCard(
                favoriteCount = favoriteCount,
                onSearchClick = onSearchClick,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "⭐", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "常用命令",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onSeeAllCategories) {
                    Text("查看全部分类", fontSize = 13.sp)
                }
            }
        }

        item {
            // 2 列网格，用 Column+Row 手动铺（避免 LazyGrid 嵌套 LazyColumn 的滚动冲突）
            val rows = popular.chunked(2)
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rows.forEach { rowCommands ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowCommands.forEach { cmd ->
                            PopularCommandCard(
                                command = cmd,
                                isFavorite = cmd.name in favoriteNames,
                                onToggleFavorite = { onToggleFavorite(cmd) },
                                onClick = { onCommandClick(cmd) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowCommands.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        if (recentCommands.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🕐", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "最近查看",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onClearRecent) {
                        Text("清空", fontSize = 13.sp)
                    }
                }
            }

            item {
                val rows = recentCommands.chunked(2)
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rows.forEach { rowCommands ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowCommands.forEach { cmd ->
                                PopularCommandCard(
                                    command = cmd,
                                    isFavorite = cmd.name in favoriteNames,
                                    onToggleFavorite = { onToggleFavorite(cmd) },
                                    onClick = { onCommandClick(cmd) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (rowCommands.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroCard(
    favoriteCount: Int,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "💻", fontSize = 26.sp)
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Linux 指令手册",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "精心整理 $totalCommandCount 条常用命令，随手即查。",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .clickable(onClick = onSearchClick),
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "搜索命令、用途或关键字",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HeroStatCard(
                    icon = Icons.Filled.Info,
                    value = "$totalCommandCount",
                    label = "命令总数",
                    iconBg = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                HeroStatCard(
                    icon = Icons.Filled.Favorite,
                    value = "$favoriteCount",
                    label = "我的收藏",
                    iconBg = Color(0xFFE91E63),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun HeroStatCard(
    icon: ImageVector,
    value: String,
    label: String,
    iconBg: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = RoundedCornerShape(10.dp),
                color = iconBg.copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = iconBg, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun PopularCommandCard(
    command: LinuxCommand,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = command.icon, fontSize = 18.sp)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (isFavorite) "⭐" else "☆",
                    fontSize = 16.sp,
                    modifier = Modifier.clickable(onClick = onToggleFavorite)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = command.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = command.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ============================================================
//  分类 tab
// ============================================================
@Composable
fun CategoriesScreen(onCategoryClick: (CmdCategory) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "全部分类",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(CmdCategory.entries, key = { it.name }) { category ->
                val count = commandsByCategory[category]?.size ?: 0
                CategoryRow(category = category, count = count, onClick = { onCategoryClick(category) })
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

@Composable
fun CategoryRow(category: CmdCategory, count: Int, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = category.icon, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = category.displayName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "$count 条指令", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun CategoryDetailScreen(
    category: CmdCategory,
    favoriteNames: List<String>,
    onToggleFavorite: (LinuxCommand) -> Unit,
    onBack: () -> Unit,
    onCommandClick: (LinuxCommand) -> Unit
) {
    val commands = commandsByCategory[category] ?: emptyList()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
            Text(
                text = "${category.icon} ${category.displayName}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(commands, key = { it.name }) { cmd ->
                CommandCard(
                    command = cmd,
                    isFavorite = cmd.name in favoriteNames,
                    onToggleFavorite = { onToggleFavorite(cmd) },
                    onClick = { onCommandClick(cmd) }
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

// ============================================================
//  收藏 tab
// ============================================================
@Composable
fun FavoritesScreen(
    favoriteNames: List<String>,
    onToggleFavorite: (LinuxCommand) -> Unit,
    onGoSearch: () -> Unit,
    onCommandClick: (LinuxCommand) -> Unit
) {
    val favCommands = remember(favoriteNames) {
        allLinuxCommands.filter { it.name in favoriteNames }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "我的收藏",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )

        if (favCommands.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(72.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Filled.FavoriteBorder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "还没有收藏的指令", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "在指令详情页点击星标即可收藏，方便下次快速查看。",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onGoSearch,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("去搜索命令")
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favCommands, key = { it.name }) { cmd ->
                    CommandCard(
                        command = cmd,
                        isFavorite = true,
                        onToggleFavorite = { onToggleFavorite(cmd) },
                        onClick = { onCommandClick(cmd) }
                    )
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

// ============================================================
//  搜索
// ============================================================
@Composable
fun SearchScreen(
    favoriteNames: List<String>,
    onToggleFavorite: (LinuxCommand) -> Unit,
    onBack: () -> Unit,
    onCommandClick: (LinuxCommand) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    val results = remember(query) {
        if (query.isBlank()) emptyList()
        else allLinuxCommands.filter {
            it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("搜索指令名称或描述…", fontSize = 14.sp) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Filled.Clear, contentDescription = "清除", modifier = Modifier.size(18.dp))
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
            )
        }

        if (query.isBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "输入指令名称或关键字试试，比如 “权限” 或 “压缩”",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(32.dp)
                )
            }
        } else if (results.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "未找到匹配的指令",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results, key = { it.name }) { cmd ->
                    CommandCard(
                        command = cmd,
                        isFavorite = cmd.name in favoriteNames,
                        onToggleFavorite = { onToggleFavorite(cmd) },
                        onClick = { onCommandClick(cmd) }
                    )
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

// ============================================================
//  通用指令列表卡片（分类详情/收藏/搜索共用）
// ============================================================
@Composable
fun CommandCard(
    command: LinuxCommand,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = command.icon, fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = command.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = command.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = if (isFavorite) "⭐" else "☆",
                    fontSize = 18.sp,
                    modifier = Modifier.clickable(onClick = onToggleFavorite)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "查看详情",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ============================================================
//  指令详情页
// ============================================================
@Composable
fun CommandDetailScreen(
    command: LinuxCommand,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
            Text(text = command.icon, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = command.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onToggleFavorite) {
                Text(text = if (isFavorite) "⭐" else "☆", fontSize = 22.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = command.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            SectionTitle(icon = "💻", title = "语法", modifier = Modifier.padding(bottom = 8.dp))
            CodeBlock(text = command.syntax, modifier = Modifier.padding(bottom = 24.dp))

            SectionTitle(icon = "📝", title = "示例（${command.examples.size} 个）", modifier = Modifier.padding(bottom = 12.dp))

            command.examples.forEachIndexed { index, example ->
                ExampleBlock(
                    number = index + 1,
                    description = example.description,
                    code = example.code,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            if (command.tips != null) {
                Spacer(modifier = Modifier.height(8.dp))
                TipsSection(tips = command.tips)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ============================================================
//  详情页子组件
// ============================================================
@Composable
fun SectionTitle(icon: String, title: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = icon, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun CodeBlock(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = CodeBlockBg,
        shadowElevation = 2.dp
    ) {
        Text(
            text = text,
            color = CodeBlockText,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(14.dp)
        )
    }
}

@Composable
fun ExampleBlock(number: Int, description: String, code: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(22.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$number",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = CodeBlockBg.copy(alpha = 0.08f)
            ) {
                val clipboard = LocalClipboardManager.current
                var copied by remember { mutableStateOf(false) }
                LaunchedEffect(copied) {
                    if (copied) { delay(1500); copied = false }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$ $code",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .weight(1f)
                            .horizontalScroll(rememberScrollState())
                            .padding(12.dp)
                    )
                    TextButton(
                        onClick = {
                            clipboard.setText(AnnotatedString(code))
                            copied = true
                        },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                    ) {
                        Text(if (copied) "已复制" else "复制", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TipsSection(tips: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Text(text = "💡", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = tips,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                lineHeight = 20.sp
            )
        }
    }
}

// ============================================================
//  收藏工具函数
// ============================================================
private const val PREFS_NAME = "linux_cmd_prefs"
private const val FAV_KEY = "favorites"
private const val RECENT_KEY = "recently_viewed"
private const val RECENT_MAX = 10

fun loadFavorites(context: Context): List<String> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val raw = prefs.getString(FAV_KEY, "") ?: ""
    return if (raw.isEmpty()) emptyList() else raw.split(",").filter { it.isNotBlank() }
}

fun saveFavorites(context: Context, favorites: List<String>) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(FAV_KEY, favorites.joinToString(","))
        .apply()
}

/**
 * 最近查看：列表按"最近查看在前"排序，最多保留 RECENT_MAX 条。
 * 存储方式跟收藏一样，用逗号分隔存进 SharedPreferences，App 重启也不会丢。
 */
fun loadRecentlyViewed(context: Context): List<String> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val raw = prefs.getString(RECENT_KEY, "") ?: ""
    return if (raw.isEmpty()) emptyList() else raw.split(",").filter { it.isNotBlank() }
}

fun saveRecentlyViewed(context: Context, recent: List<String>) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(RECENT_KEY, recent.joinToString(","))
        .apply()
}

fun addRecentlyViewed(context: Context, cmdName: String, currentList: MutableList<String>) {
    currentList.remove(cmdName) // 如果之前看过，先挪走，等下重新插到最前面
    currentList.add(0, cmdName)
    while (currentList.size > RECENT_MAX) {
        currentList.removeAt(currentList.size - 1)
    }
    saveRecentlyViewed(context, currentList)
}

fun clearRecentlyViewed(context: Context, currentList: MutableList<String>) {
    currentList.clear()
    saveRecentlyViewed(context, currentList)
}

fun toggleFavorite(context: Context, cmdName: String, currentList: MutableList<String>) {
    if (cmdName in currentList) {
        currentList.remove(cmdName)
    } else {
        currentList.add(cmdName)
    }
    saveFavorites(context, currentList.toList())
}
