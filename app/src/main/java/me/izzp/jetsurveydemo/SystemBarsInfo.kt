package me.izzp.jetsurveydemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets

@Immutable
class SystemBarsInfo(
    val statusBarHeight: Int,
    val navigationBarHeight: Int,
    val imeHeight: Int,
) {
    val statusBarHeightDp: Dp
        @Composable
        get() = with(LocalDensity.current) { statusBarHeight.toDp() }

    val navigationBarHeightDp: Dp
        @Composable
        get() = with(LocalDensity.current) { navigationBarHeight.toDp() }

    val imeHeightDp: Dp
        @Composable
        get() = with(LocalDensity.current) { imeHeight.toDp() }

    @Composable
    fun dump() {
        println("StatusBarHeight=$statusBarHeightDp, navigationBarHeight=$navigationBarHeightDp, imeHeight=$imeHeightDp")
    }
}

val LocalSystemBarsInfo = staticCompositionLocalOf {
    SystemBarsInfo(0, 0, 0)
}

@Composable
fun ProvideSystemBarsInfo(
    content: @Composable () -> Unit,
) {
    ProvideWindowInsets(false, false) {
        val insets = LocalWindowInsets.current
        val info = SystemBarsInfo(
            insets.statusBars.top,
            insets.navigationBars.bottom,
            insets.ime.bottom,
        )
        CompositionLocalProvider(
            LocalSystemBarsInfo provides info,
            content = content,
        )
    }
}