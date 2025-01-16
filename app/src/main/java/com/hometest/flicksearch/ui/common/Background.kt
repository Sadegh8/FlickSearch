package com.hometest.flicksearch.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hometest.flicksearch.ui.theme.appBackground

@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val color = MaterialTheme.colorScheme.appBackground
    val tonalElevation = LocalBackgroundTheme.current.tonalElevation
    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        tonalElevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
        modifier = modifier.fillMaxSize(),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}


/**
 * A class to model background color and tonal elevation values.
 */
@Immutable
data class BackgroundTheme(
    val color: Color = Color.Companion.Unspecified,
    val tonalElevation: Dp = Dp.Companion.Unspecified,
)

/**
 * A composition local for [BackgroundTheme].
 */
val LocalBackgroundTheme =
    staticCompositionLocalOf { BackgroundTheme() }
