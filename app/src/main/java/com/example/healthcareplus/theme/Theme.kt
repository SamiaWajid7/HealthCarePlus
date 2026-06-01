package com.example.healthcareplus.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    // Primary
    primary                = Primary,
    onPrimary              = TextOnPrimary,
    primaryContainer       = PrimaryContainer,
    onPrimaryContainer     = PrimaryDark,

    // Secondary (teal)
    secondary              = Secondary,
    onSecondary            = White,
    secondaryContainer     = SecondaryContainer,
    onSecondaryContainer   = SecondaryDark,

    // Tertiary (used for accent icons / orange)
    tertiary               = IconBgOrange,
    onTertiary             = Error,

    // Error / Danger
    error                  = Error,
    onError                = White,
    errorContainer         = ErrorContainer,
    onErrorContainer       = ErrorDark,

    // Backgrounds
    background             = Background,
    onBackground           = TextPrimary,
    surface                = Surface,
    onSurface              = TextPrimary,
    surfaceVariant         = SurfaceVariant,
    onSurfaceVariant       = TextSecondary,

    // Outlines
    outline                = BorderLight,
    outlineVariant         = BorderLight,

    // Inverse (for snackbars etc.)
    inverseSurface         = TextPrimary,
    inverseOnSurface       = White,
    inversePrimary         = PrimaryLight,

    // Scrim (modal overlay)
    scrim                  = TextPrimary,
)

private val DarkColorScheme = darkColorScheme(
    primary                = PrimaryDarkTheme,
    onPrimary              = BackgroundDark,
    primaryContainer       = Primary,
    onPrimaryContainer     = PrimaryContainer,

    secondary              = Secondary,
    onSecondary            = BackgroundDark,
    secondaryContainer     = SecondaryDark,
    onSecondaryContainer   = SecondaryContainer,

    tertiary               = IconBgOrange,
    onTertiary             = Warning,

    error                  = Error,
    onError                = BackgroundDark,
    errorContainer         = ErrorDark,
    onErrorContainer       = ErrorContainer,

    background             = BackgroundDark,
    onBackground           = TextPrimaryDark,
    surface                = SurfaceDark,
    onSurface              = TextPrimaryDark,
    surfaceVariant         = SurfaceVariantDark,
    onSurfaceVariant       = TextSecondaryDark,

    outline                = BorderDark,
    outlineVariant         = BorderDark,

    inverseSurface         = TextPrimaryDark,
    inverseOnSurface       = BackgroundDark,
    inversePrimary         = Primary,

    scrim                  = BackgroundDark,
)

@Composable
fun HealthCarePlusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+.
    // Set to false to always use HealthCare+ brand colors.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Status bar matches the primary blue header
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = HealthCarePlusTypography,
        shapes      = HealthCarePlusShapes,
        content     = content
    )
}