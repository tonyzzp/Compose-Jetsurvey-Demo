package me.izzp.jetsurveydemo.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.izzp.jetsurveydemo.ProvideSystemBarsInfo

private val light = lightColors(
    primary = Color(0xff9C27B0),
    primaryVariant = Color(0xff9C27B0),
    secondary = Color(0xffF44336),
    secondaryVariant = Color(0xffF44336),
    surface = Color.White,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
)

private val dark = darkColors(
    primary = Color(0xff9C27B0),
    primaryVariant = Color(0xff9C27B0),
    secondary = Color(0xffF44336),
    secondaryVariant = Color(0xffF44336),
    surface = Color.Black,
    onSurface = Color.White,
    background = Color.Black,
    onBackground = Color.White,
)

private val shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(32.dp),
)

val mtColors
    @Composable
    get() = MaterialTheme.colors

val mtTypography
    @Composable
    get() = MaterialTheme.typography

val mtShapes
    @Composable
    get() = MaterialTheme.shapes

val Colors.grey
    @Composable
    get() = if (mtColors.isLight) Color.LightGray else Color.DarkGray

@Composable
fun JetTheme(
    content: @Composable () -> Unit
) {
    ProvideSystemBarsInfo {
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) dark else light,
            shapes = shapes,
        ) {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(Color.Transparent, !isSystemInDarkTheme())
            val ripple = rememberRipple()
            CompositionLocalProvider(LocalIndication provides ripple) {
                content()
            }
        }
    }
}
