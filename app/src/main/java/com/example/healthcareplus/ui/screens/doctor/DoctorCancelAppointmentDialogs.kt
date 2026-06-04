package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// ── Colors (match project palette) ───────────────────────────────────────────
private val PrimaryBlue    = Color(0xFF3D5AF1)
private val ErrorRed       = Color(0xFFD32F2F)
private val ErrorRedBg     = Color(0xFFFFEBEE)
private val SuccessGreen   = Color(0xFF28A745)
private val SuccessGreenBg = Color(0xFFD4EDDA)
private val White          = Color(0xFFFFFFFF)
private val TextPrimary    = Color(0xFF1A1A2E)
private val TextSecondary  = Color(0xFF555555)
private val TextMuted      = Color(0xFF9CA3AF)
private val BorderLight    = Color(0xFFCCCCCC)

// ─────────────────────────────────────────────────────────────────────────────
// 1. Cancel Appointment Confirmation Dialog
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun DoctorCancelAppointmentDialog(
    doctorName  : String = "Dr. Sarah Johnson",
    dateTime    : String = "Feb 15, 2024 at 10:00 AM",
    onDismiss   : () -> Unit = {},
    onConfirm   : () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        ) {
            Column(
                modifier            = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Warning icon
                Box(
                    modifier         = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(ErrorRedBg),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Warning,
                        contentDescription = null,
                        tint               = ErrorRed,
                        modifier           = Modifier.size(36.dp),
                    )
                }

                Spacer(Modifier.height(18.dp))

                Text(
                    text       = "Cancel Appointment?",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    textAlign  = TextAlign.Center,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text      = doctorName,
                    fontSize  = 15.sp,
                    color     = TextSecondary,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text      = dateTime,
                    fontSize  = 13.sp,
                    color     = TextMuted,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    text      = "Are you sure you want to cancel this appointment? This action cannot be undone.",
                    fontSize  = 14.sp,
                    color     = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Keep Appointment
                    OutlinedButton(
                        onClick  = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape    = RoundedCornerShape(14.dp),
                        colors   = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextPrimary,
                        ),
                        border   = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            BorderLight,
                        ),
                    ) {
                        Text(
                            text       = "Keep Appointment",
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                    }

                    // Yes, Cancel
                    Button(
                        onClick  = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape    = RoundedCornerShape(14.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = ErrorRed,
                        ),
                    ) {
                        Text(
                            text       = "Yes, Cancel",
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// 2. Appointment Cancelled Success Dialog
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun AppointmentCancelledSuccessDialog(
    onDismiss : () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        ) {
            Column(
                modifier            = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Success icon
                Box(
                    modifier         = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(SuccessGreenBg),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Check,
                        contentDescription = null,
                        tint               = SuccessGreen,
                        modifier           = Modifier.size(36.dp),
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text       = "Appointment Cancelled",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    textAlign  = TextAlign.Center,
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text      = "The appointment has been successfully cancelled.",
                    fontSize  = 14.sp,
                    color     = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                )

                Spacer(Modifier.height(28.dp))

                Button(
                    onClick  = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                    ),
                ) {
                    Text(
                        text       = "Got it",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// 3. Appointment Confirmed Dialog  ← NEW
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun AppointmentConfirmedDialog(
    patientName : String = "Robert Brown",
    dateTime    : String = "Feb 16, 2024 at 2:00 PM",
    onDismiss   : () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        ) {
            Column(
                modifier            = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Checkmark icon in blue circle
                Box(
                    modifier         = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEF2FF)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Check,
                        contentDescription = null,
                        tint               = PrimaryBlue,
                        modifier           = Modifier.size(40.dp),
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text       = "Appointment Confirmed!",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    textAlign  = TextAlign.Center,
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text      = "You have successfully approved the appointment for",
                    fontSize  = 14.sp,
                    color     = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text       = patientName,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                    textAlign  = TextAlign.Center,
                )
                Text(
                    text      = dateTime,
                    fontSize  = 13.sp,
                    color     = TextMuted,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(28.dp))

                Button(
                    onClick  = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                    ),
                ) {
                    Text(
                        text       = "Done",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun CancelDialogPreview() {
    DoctorCancelAppointmentDialog()
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun CancelledSuccessDialogPreview() {
    AppointmentCancelledSuccessDialog()
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ConfirmedDialogPreview() {
    AppointmentConfirmedDialog()
}