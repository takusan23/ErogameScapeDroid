package io.github.takusan23.erogamescapedroid.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xfff9a825),
    primaryVariant = Color(0xffc17900),
    secondary = Color(0xffffd95a)
)

private val LightColorPalette = lightColors(
    primary = Color(0xfff9a825),
    primaryVariant = Color(0xffc17900),
    secondary = Color(0xffffd95a)
)

@Composable
fun ErogameScapeDroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        content = content
    )
}