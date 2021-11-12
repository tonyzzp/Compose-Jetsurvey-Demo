package me.izzp.jetsurveydemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets

//@Immutable
class SystemBarsInfo(
    density: Density,
    val statusBarHeight: Int,
    val navigationBarHeight: Int,
    val imeHeight: Int,
) {
    val statusBarHeightDp = with(density) { statusBarHeight.toDp() }
    val navigationBarHeightDp = with(density) { navigationBarHeight.toDp() }
    val imeHeightDp = with(density) { imeHeight.toDp() }

    fun dump() {
        println("StatusBarHeight=$statusBarHeightDp, navigationBarHeight=$navigationBarHeightDp, imeHeight=$imeHeightDp")
    }
}

val LocalSystemBarsInfo = staticCompositionLocalOf {
    SystemBarsInfo(Density(1f, 1f), 0, 0, 0)
}

@Composable
fun ProvideSystemBarsInfo(
    content: @Composable () -> Unit,
) {
    ProvideWindowInsets(false, false) {
        val insets = LocalWindowInsets.current
        val info = SystemBarsInfo(
            LocalDensity.current,
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