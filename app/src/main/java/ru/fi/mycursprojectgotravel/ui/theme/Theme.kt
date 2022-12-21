package ru.fi.mycursprojectgotravel.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.fi.mycursprojectgotravel.utils.PRIMARY_COLOR

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = myColor,
    primaryVariant = myColor2,
    secondary = myColor,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val ArabicColorPalette = lightColors(
    primary = colorArabic2,
    primaryVariant = colorArabic,
    secondary = colorArabic,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

enum class Theme{
    DARK, LIGHT, ARABIC
}


@Composable
fun GoTravelTheme(
    theme: Theme,
    content: @Composable () -> Unit
) {
    val colors = when(theme){
        Theme.DARK ->  {
            PRIMARY_COLOR.value = DarkColorPalette.primary
            DarkColorPalette
        }
        Theme.ARABIC -> {
            PRIMARY_COLOR.value = ArabicColorPalette.primary
            ArabicColorPalette
        }
        Theme.LIGHT -> {
            PRIMARY_COLOR.value = LightColorPalette.primary
            LightColorPalette
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}