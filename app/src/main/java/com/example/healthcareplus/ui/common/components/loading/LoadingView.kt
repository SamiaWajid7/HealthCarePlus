package com.example.healthcareplus.ui.common.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.common.utils.ClinicRadius

enum class LoadingViewType {
    FULLSCREEN,   // fills the whole screen (white / branded background)
    INLINE,       // centred inside a card or list area
    OVERLAY,      // translucent overlay on top of existing content — must be
    // placed as the last child of a Box that already contains
    // the screen content, so it renders on top via natural z-order
}

/**
 * Consistent loading indicator shown whenever an async Firebase call is in-flight.
 *
 * @param message   Text shown below the spinner. Pass blank to hide it.
 * @param type      Controls layout/background style — see [LoadingViewType].
 * @param modifier  Optional modifier forwarded to the root container.
 *
 * **OVERLAY usage:**
 * ```
 * Box(modifier = Modifier.fillMaxSize()) {
 *     YourScreenContent()
 *     if (isLoading) {
 *         LoadingView(type = LoadingViewType.OVERLAY)
 *     }
 * }
 * ```
 */
@Composable
fun LoadingView(
    message: String = "Please wait…",
    type: LoadingViewType = LoadingViewType.FULLSCREEN,
    modifier: Modifier = Modifier,
) {
    when (type) {
        LoadingViewType.FULLSCREEN -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(White),
                contentAlignment = Alignment.Center,
            ) {
                LoadingContent(message)
            }
        }

        LoadingViewType.INLINE -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                LoadingContent(message)
            }
        }

        LoadingViewType.OVERLAY -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = .35f)),
                contentAlignment = Alignment.Center,
            ) {
                Card(
                    shape     = RoundedCornerShape(ClinicRadius.Large),
                    colors    = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                ) {
                    Box(modifier = Modifier.padding(32.dp)) {
                        LoadingContent(message)
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(message: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            color       = Primary,
            strokeWidth = 3.dp,
            modifier    = Modifier.size(44.dp),
        )
        if (message.isNotBlank()) {
            Spacer(Modifier.height(16.dp))
            Text(
                text     = message,
                fontSize = 14.sp,
                color    = TextSecondary,
            )
        }
    }
}

// ─── Previews ────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun LoadingFullscreenPreview() {
    LoadingView(type = LoadingViewType.FULLSCREEN)
}

@Preview(showBackground = true)
@Composable
private fun LoadingInlinePreview() {
    LoadingView(type = LoadingViewType.INLINE)
}

@Preview(showBackground = true, backgroundColor = 0xFF2196F3)
@Composable
private fun LoadingOverlayPreview() {
    LoadingView(type = LoadingViewType.OVERLAY)
}