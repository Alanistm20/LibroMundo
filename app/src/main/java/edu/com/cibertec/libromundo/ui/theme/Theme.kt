package edu.com.cibertec.libromundo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_light_primary,       // Verde
    onPrimary = md_theme_light_onPrimary,   // Blanco
    secondary = md_theme_light_secondary,   // Dorado
    tertiary = md_theme_light_tertiary,     // Azul
    surfaceVariant = md_theme_light_surfaceVariant // Gris claro
)



private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,       // Verde
    onPrimary = md_theme_light_onPrimary,   // Blanco
    secondary = md_theme_light_secondary,   // Dorado
    tertiary = md_theme_light_tertiary,     // Azul
    surfaceVariant = md_theme_light_surfaceVariant // Gris claro
)

@Composable
fun LibroMundoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
