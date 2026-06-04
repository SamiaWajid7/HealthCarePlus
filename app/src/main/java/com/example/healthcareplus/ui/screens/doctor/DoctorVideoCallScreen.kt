package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ── Colors ────────────────────────────────────────────────────────────────────
private val CallBg          = Color(0xFF111827)
private val CallBgMid       = Color(0xFF1A2235)
private val CallBgSurface   = Color(0xFF1F2B3E)
private val CallCardBg      = Color(0xFF1C2A3A)
private val PrimaryBlue     = Color(0xFF3D5AF1)
private val ConnectedGreen  = Color(0xFF34D399)
private val White           = Color(0xFFFFFFFF)
private val WhiteMuted      = Color(0xFFB0B8C8)
private val OverlayDark     = Color(0x99000000)
private val EndCallRed      = Color(0xFFE53935)
private val ButtonSurface   = Color(0xFF2A3B50)

// ─────────────────────────────────────────────────────────────────────────────
// DoctorVideoCallScreen
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun DoctorVideoCallScreen(
    patientName   : String = "John Doe",
    patientId     : String = "HC-2024-0123",
    lastVisit     : String = "Jan 15, 2024",
    condition     : String = "Hypertension",
    onEndCall     : () -> Unit = {},
    onChat        : () -> Unit = {},
) {
    // Timer state
    var seconds by remember { mutableIntStateOf(1) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1_000)
            seconds++
        }
    }
    val timerText = remember(seconds) {
        val m = seconds / 60
        val s = seconds % 60
        "%02d:%02d".format(m, s)
    }

    // Mic / video mute state
    var micMuted   by remember { mutableStateOf(false) }
    var videoMuted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(CallBg, CallBgMid, CallBg))
            )
    ) {

        // ── Main patient avatar area ──────────────────────────────────────
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(bottom = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            // Patient avatar circle
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Person,
                    contentDescription = "Patient avatar",
                    tint               = White,
                    modifier           = Modifier.size(60.dp),
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text       = patientName,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold,
                color      = White,
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text     = "Patient • $patientId",
                fontSize = 13.sp,
                color    = WhiteMuted,
            )

            Spacer(Modifier.height(10.dp))

            // Connected indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(ConnectedGreen),
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text     = "Connected",
                    fontSize = 13.sp,
                    color    = ConnectedGreen,
                )
            }
        }

        // ── Top-left: timer + patient info badge ─────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            // Timer pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(OverlayDark)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                Text(
                    text       = timerText,
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = White,
                )
            }

            // Patient info badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(OverlayDark)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            ) {
                Column {
                    Text(
                        text     = "Last Visit: $lastVisit",
                        fontSize = 11.sp,
                        color    = WhiteMuted,
                    )
                    Row {
                        Text(
                            text     = "Conditions: ",
                            fontSize = 11.sp,
                            color    = WhiteMuted,
                        )
                        Text(
                            text       = condition,
                            fontSize   = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = White,
                        )
                    }
                }
            }
        }

        // ── Top-right: "You" self-view card ──────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 20.dp)
                .size(width = 88.dp, height = 110.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector        = Icons.Outlined.Person,
                    contentDescription = "You",
                    tint               = White,
                    modifier           = Modifier.size(40.dp),
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text     = "You",
                    fontSize = 12.sp,
                    color    = White,
                )
            }
        }

        // ── Bottom controls ───────────────────────────────────────────────
        Column(
            modifier            = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors    = listOf(Color.Transparent, CallBg),
                        startY    = 0f,
                        endY      = 200f,
                    )
                )
                .padding(bottom = 36.dp, top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // Main call buttons row
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                // Mic toggle
                CallControlButton(
                    icon        = if (micMuted) Icons.Outlined.MicOff else Icons.Outlined.Mic,
                    background  = ButtonSurface,
                    tint        = if (micMuted) EndCallRed else White,
                    size        = 56,
                    onClick     = { micMuted = !micMuted },
                )

                Spacer(Modifier.width(20.dp))

                // End call (red, larger)
                CallControlButton(
                    icon        = Icons.Outlined.CallEnd,
                    background  = EndCallRed,
                    tint        = White,
                    size        = 64,
                    onClick     = onEndCall,
                )

                Spacer(Modifier.width(20.dp))

                // Video toggle
                CallControlButton(
                    icon        = if (videoMuted) Icons.Outlined.VideocamOff else Icons.Outlined.Videocam,
                    background  = ButtonSurface,
                    tint        = if (videoMuted) EndCallRed else White,
                    size        = 56,
                    onClick     = { videoMuted = !videoMuted },
                )
            }

            // Secondary buttons row: Chat
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                SecondaryCallButton(
                    icon    = Icons.Outlined.ChatBubbleOutline,
                    label   = "Chat",
                    onClick = onChat,
                )
            }
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun CallControlButton(
    icon       : ImageVector,
    background : Color,
    tint       : Color,
    size       : Int,
    onClick    : () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = tint,
            modifier           = Modifier.size((size * 0.45f).dp),
        )
    }
}

@Composable
private fun SecondaryCallButton(
    icon    : ImageVector,
    label   : String,
    onClick : () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(ButtonSurface)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 9.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = WhiteMuted,
            modifier           = Modifier.size(16.dp),
        )
        Text(
            text     = label,
            fontSize = 13.sp,
            color    = WhiteMuted,
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun DoctorVideoCallPreview() {
    DoctorVideoCallScreen()
}