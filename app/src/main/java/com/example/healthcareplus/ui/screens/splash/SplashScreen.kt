package com.example.healthcareplus.ui.screens.splash
// Must match your actual file location

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ── Colours (inline — replace with your ClinicColors if preferred) ──────────
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val White         = Color(0xFFFFFFFF)
private val WhiteDim      = Color(0xCCFFFFFF)   // 80 % white

// ── Dot indicator data ───────────────────────────────────────────────────────
private val dots = listOf(0, 1, 2)

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {},
) {
    // Fade-in animation
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue  = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label        = "splash_alpha",
    )

    // Auto-navigate after 2.5 s
    LaunchedEffect(Unit) {
        visible = true
        delay(2500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier            = Modifier.alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {

            // ── Logo circle ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(White),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = Icons.Outlined.HealthAndSafety,
                    contentDescription = "HealthCare+ logo",
                    tint               = PrimaryBlue,
                    modifier           = Modifier.size(52.dp),
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── App name ────────────────────────────────────────────────────
            Text(
                text       = "HealthCare+",
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                color      = White,
            )

            Spacer(Modifier.height(8.dp))

            // ── Tagline ─────────────────────────────────────────────────────
            Text(
                text       = "Your Health, Our Priority",
                fontSize   = 14.sp,
                fontWeight = FontWeight.Normal,
                color      = WhiteDim,
            )

            Spacer(Modifier.height(48.dp))

            // ── Dot indicators ──────────────────────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                dots.forEach { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == 0) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == 0) White
                                else White.copy(alpha = 0.45f)
                            ),
                    )
                }
            }
        }
    }
}

// ── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}