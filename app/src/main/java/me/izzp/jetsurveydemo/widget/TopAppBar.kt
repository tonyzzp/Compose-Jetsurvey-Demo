package me.izzp.jetsurveydemo.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.izzp.jetsurveydemo.LocalSystemBarsInfo
import me.izzp.jetsurveydemo.ProvideSystemBarsInfo
import me.izzp.jetsurveydemo.ui.theme.mtColors
import me.izzp.jetsurveydemo.ui.theme.mtTypography

@Composable
fun CenterTitleAppBar(
    title: @Composable () -> Unit,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = mtColors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    topPadding: Dp = 0.dp,
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        Column {
            if (topPadding > 0.dp) {
                Spacer(Modifier.height(topPadding))
            }
            Box(
                contentAlignment = Alignment.Center,
            ) {
                TopAppBar(
                    title = {},
                    navigationIcon = navigationIcon,
                    actions = actions,
                    backgroundColor = backgroundColor,
                    contentColor = contentColor,
                    elevation = 0.dp,
                )
                ProvideTextStyle(mtTypography.h6) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        title()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCenterTitleAppBar() {
    ProvideSystemBarsInfo {
        CenterTitleAppBar(
            title = { Text("Title") },
            navigationIcon = {
                IconButton({}) { Icon(Icons.Default.Home, null) }
            },
            actions = {
                IconButton({}) { Icon(Icons.Default.Refresh, null) }
                IconButton({}) { Icon(Icons.Default.Info, null) }
            },
            topPadding = LocalSystemBarsInfo.current.statusBarHeightDp,
        )
    }
}