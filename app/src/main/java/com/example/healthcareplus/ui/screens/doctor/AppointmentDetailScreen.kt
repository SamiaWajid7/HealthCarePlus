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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthcareplus.data.model.Appointment
import com.example.healthcareplus.ui.viewmodel.AppointmentUiState
import com.example.healthcareplus.ui.viewmodel.AppointmentViewModel

// ── Colors ────────────────────────────────────────────────────────────────────
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White         = Color(0xFFFFFFFF)
private val BgLight       = Color(0xFFF3F4F6)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val ErrorRed      = Color(0xFFE53935)
private val WarningBg     = Color(0xFFFFF8E1)
private val WarningText   = Color(0xFF856404)

// ─────────────────────────────────────────────────────────────────────────────
// AppointmentDetailScreen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AppointmentDetailScreen(
    appointmentId : String? = null,       // passed from nav argument
    onBack        : () -> Unit = {},
    onApprove     : () -> Unit = {},
    onCancel      : () -> Unit = {},
    vm            : AppointmentViewModel = viewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()

    // Load single appointment when screen opens
    LaunchedEffect(appointmentId) {
        if (!appointmentId.isNullOrEmpty()) {
            vm.loadAppointmentById(appointmentId)
        }
    }

    // Dialog state
    var showApprovedDialog  by remember { mutableStateOf(false) }
    var showCancelDialog    by remember { mutableStateOf(false) }
    var showCancelledDialog by remember { mutableStateOf(false) }

    // Get the single appointment from state
    val appointment = when (state) {
        is AppointmentUiState.SingleSuccess ->
            (state as AppointmentUiState.SingleSuccess).appointment
        else -> null
    }

    // ── Dialogs ───────────────────────────────────────────────────────────────

    if (showApprovedDialog) {
        AppointmentApprovedSuccessDialog(
            onDismiss = {
                appointment?.let { vm.updateStatus(it.id, "Upcoming") }
                showApprovedDialog = false
                onApprove()
            },
        )
    }

    if (showCancelDialog && appointment != null) {
        DoctorCancelAppointmentDialog(
            doctorName = appointment.patientName,
            dateTime   = "${appointment.date} • ${appointment.time}",
            onDismiss  = { showCancelDialog = false },
            onConfirm  = {
                vm.updateStatus(appointment.id, "Cancelled")
                showCancelDialog    = false
                showCancelledDialog = true
            },
        )
    }

    if (showCancelledDialog) {
        AppointmentCancelledSuccessDialog(
            onDismiss = {
                showCancelledDialog = false
                onCancel()
            },
        )
    }

    // ── Loading / Error states ────────────────────────────────────────────────

    when {
        state is AppointmentUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryBlue)
            }
            return
        }
        state is AppointmentUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text      = (state as AppointmentUiState.Error).message,
                    color     = ErrorRed,
                    fontSize  = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.padding(24.dp)
                )
            }
            return
        }
    }

    // ── Main UI ───────────────────────────────────────────────────────────────

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight),
    ) {
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
                            text       = appointment?.patientName ?: "—",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            text     = appointment?.patientId ?: "—",
                            fontSize = 12.sp,
                            color    = TextSecondary,
                        )
                    }
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text     = "${appointment?.date ?: "—"} • ${appointment?.time ?: "—"}",
                        fontSize = 13.sp,
                        color    = TextSecondary,
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.MedicalServices,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text     = appointment?.specialty ?: "—",
                        fontSize = 13.sp,
                        color    = TextSecondary,
                    )
                }

                if (!appointment?.reason.isNullOrEmpty()) {
                    Spacer(Modifier.height(12.dp))
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
                                text     = appointment?.reason ?: "",
                                fontSize = 13.sp,
                                color    = WarningText,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Status card ───────────────────────────────────────────────
            SectionCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text       = "Status",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Spacer(Modifier.height(8.dp))
                val statusColor = when (appointment?.status) {
                    "Upcoming"  -> PrimaryBlue
                    "Completed" -> Color(0xFF28A745)
                    "Cancelled" -> ErrorRed
                    else        -> Color(0xFFF59E0B)
                }
                Text(
                    text       = appointment?.status ?: "Pending",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = statusColor,
                )
            }

            Spacer(Modifier.height(20.dp))
        }

        // ── Sticky bottom buttons (only for Pending appointments) ─────────
        if (appointment?.status == "Pending") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
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
}

// ── Appointment Approved success dialog ───────────────────────────────────────
@Composable
fun AppointmentApprovedSuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton    = {},
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
                    text      = "The appointment has been confirmed.\nThe patient has been notified.",
                    fontSize  = 14.sp,
                    color     = TextSecondary,
                    textAlign = TextAlign.Center,
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
        shape          = RoundedCornerShape(20.dp),
        containerColor = White,
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

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AppointmentDetailPreview() {
    AppointmentDetailScreen()
}