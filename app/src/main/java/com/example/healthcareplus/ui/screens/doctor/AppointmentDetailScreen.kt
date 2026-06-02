package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors ────────────────────────────────────────────────────────────────────
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White         = Color(0xFFFFFFFF)
private val BgLight       = Color(0xFFF3F4F6)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val ErrorRed      = Color(0xFFE53935)
private val ErrorRedBg    = Color(0xFFFFEBEE)
private val WarningBg     = Color(0xFFFFF8E1)
private val WarningText   = Color(0xFF856404)

// ── Data model ────────────────────────────────────────────────────────────────
data class AppointmentDetail(
    val patientName   : String = "Robert Brown",
    val patientId     : String = "HC-2024-0789",
    val gender        : String = "Male",
    val age           : Int    = 45,
    val bloodGroup    : String = "O+",
    val phone         : String = "+1 555-0123",
    val email         : String = "robert@email.com",
    val dateTime      : String = "Feb 16, 2024 • 2:00 PM",
    val appointmentType: String = "Video Call Consultation",
    val chiefComplaint: String = "Chest pain and shortness of breath for 2 days. Pain increases with exertion.",
    val lastVisit     : String = "Jan 15, 2024",
    val conditions    : String = "Hypertension, Type 2 Diabetes",
    val allergies     : String = "Penicillin",
    val currentMeds   : String = "Lisinopril, Metformin",
)

// ─────────────────────────────────────────────────────────────────────────────
// AppointmentDetailScreen
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun AppointmentDetailScreen(
    detail    : AppointmentDetail = AppointmentDetail(),
    onBack    : () -> Unit = {},
    onApprove : () -> Unit = {},
    onCancel  : () -> Unit = {},
) {
    // Dialog state
    var showApprovedDialog  by remember { mutableStateOf(false) }
    var showCancelDialog    by remember { mutableStateOf(false) }
    var showCancelledDialog by remember { mutableStateOf(false) }

    // Show Appointment Approved success dialog
    if (showApprovedDialog) {
        AppointmentApprovedSuccessDialog(
            onDismiss = {
                showApprovedDialog = false
                onApprove()
            },
        )
    }

    // Show Cancel Confirmation dialog
    if (showCancelDialog) {
        DoctorCancelAppointmentDialog(
            doctorName = detail.patientName,
            dateTime   = detail.dateTime,
            onDismiss  = { showCancelDialog = false },
            onConfirm  = {
                showCancelDialog    = false
                showCancelledDialog = true
            },
        )
    }

    // Show Appointment Cancelled success dialog
    if (showCancelledDialog) {
        AppointmentCancelledSuccessDialog(
            onDismiss = {
                showCancelledDialog = false
                onCancel()
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight),
    ) {

        // ── Scrollable body ───────────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {

            // Back
            Row(
                modifier          = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text     = "< Back",
                    color    = PrimaryBlue,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable { onBack() },
                )
            }

            Text(
                text       = "Appointment Details",
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
                modifier   = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(16.dp))

            // ── Patient info card ─────────────────────────────────────────
            SectionCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlueBg),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.Person,
                            contentDescription = null,
                            tint               = PrimaryBlue,
                            modifier           = Modifier.size(28.dp),
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            text       = detail.patientName,
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            text     = detail.patientId,
                            fontSize = 12.sp,
                            color    = TextSecondary,
                        )
                        Text(
                            text     = "${detail.gender}, ${detail.age} years • Blood: ${detail.bloodGroup}",
                            fontSize = 12.sp,
                            color    = TextSecondary,
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFE5E7EB))
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text     = "Phone: ${detail.phone}",
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                    Text(
                        text     = "Email: ${detail.email}",
                        fontSize = 12.sp,
                        color    = PrimaryBlue,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Appointment info card ─────────────────────────────────────
            SectionCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text       = "Appointment Information",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Spacer(Modifier.height(12.dp))

                // Date/time
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text     = detail.dateTime,
                        fontSize = 13.sp,
                        color    = TextSecondary,
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Appointment type
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.Videocam,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text     = detail.appointmentType,
                        fontSize = 13.sp,
                        color    = TextSecondary,
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Chief complaint highlighted box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(WarningBg)
                        .padding(12.dp),
                ) {
                    Column {
                        Text(
                            text       = "Chief Complaint",
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = WarningText,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text     = detail.chiefComplaint,
                            fontSize = 13.sp,
                            color    = WarningText,
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Medical history card ──────────────────────────────────────
            SectionCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text       = "Medical History",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Spacer(Modifier.height(12.dp))

                MedicalHistoryRow("Last Visit",    detail.lastVisit)
                Spacer(Modifier.height(8.dp))
                MedicalHistoryRow("Conditions",    detail.conditions)
                Spacer(Modifier.height(8.dp))
                MedicalHistoryRow("Allergies",     detail.allergies)
                Spacer(Modifier.height(8.dp))
                MedicalHistoryRow("Current Meds",  detail.currentMeds)
            }

            Spacer(Modifier.height(20.dp))
        }

        // ── Sticky bottom buttons ─────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // Approve
            Button(
                onClick  = { showApprovedDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Check,
                    contentDescription = null,
                    modifier           = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = "Approve Appointment",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            // Cancel
            OutlinedButton(
                onClick  = { showCancelDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ErrorRed),
            ) {
                Text(
                    text       = "Cancel Appointment",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = ErrorRed,
                )
            }
        }
    }
}

// ── Appointment Approved success dialog ───────────────────────────────────────
@Composable
fun AppointmentApprovedSuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        text = {
            Column(
                modifier            = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE6FBF4)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Check,
                        contentDescription = null,
                        tint               = Color(0xFF10B981),
                        modifier           = Modifier.size(32.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text       = "Appointment Approved",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text     = "Your appointment has been confirmed.\nThe patient has been notified.",
                    fontSize = 14.sp,
                    color    = TextSecondary,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick  = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                ) {
                    Text(
                        text       = "Got it",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = White,
                    )
                }
            }
        },
        shape             = RoundedCornerShape(20.dp),
        containerColor    = White,
    )
}

// ── Helpers ───────────────────────────────────────────────────────────────────
@Composable
private fun SectionCard(
    modifier : Modifier = Modifier,
    content  : @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

@Composable
private fun MedicalHistoryRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, fontSize = 13.sp, color = TextSecondary)
        Text(text = value, fontSize = 13.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AppointmentDetailPreview() {
    AppointmentDetailScreen()
}