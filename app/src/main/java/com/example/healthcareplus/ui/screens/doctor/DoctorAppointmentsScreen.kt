package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
private val PrimaryBlue      = Color(0xFF3D5AF1)
private val PrimaryBlueBg    = Color(0xFFEEF2FF)
private val White             = Color(0xFFFFFFFF)
private val TextPrimary       = Color(0xFF1A1A2E)
private val TextSecondary     = Color(0xFF6B7280)
private val BgLight           = Color(0xFFF3F4F6)
private val BadgePending      = Color(0xFFFFF3CD)
private val BadgePendingText  = Color(0xFF856404)
private val ErrorRed          = Color(0xFFE53935)

// ── Tab labels ────────────────────────────────────────────────────────────────
private val tabs = listOf("Pending", "Upcoming", "Completed", "Cancelled")

// ── DoctorAppointment wrapper — internal to this file only ───────────────────
private data class DoctorAppointment(
    val id          : String,
    val patientName : String,
    val patientId   : String,
    val dateTime    : String,
    val reason      : String,
    val status      : String,
)

private fun Appointment.toDoctorAppointment() = DoctorAppointment(
    id          = id,
    patientName = patientName,
    patientId   = patientId.ifEmpty { "HC-2024-XXXX" },
    dateTime    = "$date • $time",
    reason      = reason,
    status      = status,
)

// ─────────────────────────────────────────────────────────────────────────────
// DoctorAppointmentsScreen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun DoctorAppointmentsScreen(
    onBack        : () -> Unit = {},
    onViewDetails : (String) -> Unit = {},   // FIX: String ID instead of DoctorAppointment
    vm            : AppointmentViewModel = viewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableStateOf(0) }

    var showConfirmedDialog  by remember { mutableStateOf(false) }
    var showCancelDialog     by remember { mutableStateOf(false) }
    var showCancelledDialog  by remember { mutableStateOf(false) }
    var selectedAppointment  by remember { mutableStateOf<DoctorAppointment?>(null) }

    LaunchedEffect(Unit) { vm.loadDoctorAppointments() }

    val allAppointments = when (state) {
        is AppointmentUiState.Success ->
            (state as AppointmentUiState.Success).appointments.map { it.toDoctorAppointment() }
        else -> emptyList()
    }

    val filtered     = allAppointments.filter { it.status == tabs[selectedTab] }
    val pendingCount = allAppointments.count  { it.status == "Pending" }

    // ── Dialogs ───────────────────────────────────────────────────────────────

    if (showConfirmedDialog && selectedAppointment != null) {
        AppointmentConfirmedDialog(
            patientName = selectedAppointment!!.patientName,
            dateTime    = selectedAppointment!!.dateTime,
            onDismiss   = {
                vm.updateStatus(selectedAppointment!!.id, "Upcoming")
                showConfirmedDialog = false
                selectedAppointment = null
            },
        )
    }

    if (showCancelDialog && selectedAppointment != null) {
        DoctorCancelAppointmentDialog(
            doctorName = selectedAppointment!!.patientName,
            dateTime   = selectedAppointment!!.dateTime,
            onDismiss  = {
                showCancelDialog    = false
                selectedAppointment = null
            },
            onConfirm  = {
                vm.updateStatus(selectedAppointment!!.id, "Cancelled")
                showCancelDialog    = false
                showCancelledDialog = true
            },
        )
    }

    if (showCancelledDialog) {
        AppointmentCancelledSuccessDialog(
            onDismiss = {
                showCancelledDialog = false
                selectedAppointment = null
            },
        )
    }

    // ── Main UI ───────────────────────────────────────────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight),
    ) {
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
            text       = "My Appointments",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            modifier   = Modifier.padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(16.dp))

        // Tab row
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            tabs.forEachIndexed { index, label ->
                val isSelected = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) PrimaryBlue else White)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) PrimaryBlue else Color(0xFFE5E7EB),
                            shape = RoundedCornerShape(20.dp),
                        )
                        .clickable { selectedTab = index }
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text       = label,
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color      = if (isSelected) White else TextSecondary,
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        if (selectedTab == 0 && pendingCount > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BadgePending)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            ) {
                Text(
                    text     = "$pendingCount appointments require your action",
                    fontSize = 13.sp,
                    color    = BadgePendingText,
                )
            }
            Spacer(Modifier.height(10.dp))
        }

        when (state) {
            is AppointmentUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }
            is AppointmentUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text      = (state as AppointmentUiState.Error).message,
                        color     = ErrorRed,
                        fontSize  = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(24.dp)
                    )
                }
            }
            else -> {
                if (filtered.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "No ${tabs[selectedTab].lowercase()} appointments",
                            color    = TextSecondary,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(filtered, key = { it.id }) { appt ->
                            DoctorAppointmentCard(
                                appointment   = appt,
                                onViewDetails = { onViewDetails(appt.id) },  // FIX: pass ID only
                                onApprove     = {
                                    selectedAppointment = appt
                                    showConfirmedDialog = true
                                },
                                onReject      = {
                                    selectedAppointment = appt
                                    showCancelDialog    = true
                                },
                            )
                        }
                        item { Spacer(Modifier.height(12.dp)) }
                    }
                }
            }
        }
    }
}

// ── Appointment card ──────────────────────────────────────────────────────────
@Composable
private fun DoctorAppointmentCard(
    appointment   : DoctorAppointment,
    onViewDetails : () -> Unit,
    onApprove     : () -> Unit,
    onReject      : () -> Unit,
) {
    val isPending = appointment.status == "Pending"

    val statusBg = when (appointment.status) {
        "Upcoming"  -> Color(0xFFE8EEFF)
        "Completed" -> Color(0xFFE8F5E9)
        "Cancelled" -> Color(0xFFFFEBEE)
        else        -> BadgePending
    }
    val statusColor = when (appointment.status) {
        "Upcoming"  -> PrimaryBlue
        "Completed" -> Color(0xFF28A745)
        "Cancelled" -> ErrorRed
        else        -> BadgePendingText
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row {
            if (isPending) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(
                            color = PrimaryBlue,
                            shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp),
                        ),
                )
            }
            Column(modifier = Modifier.padding(14.dp)) {

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlueBg),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector        = Icons.Outlined.Person,
                                contentDescription = null,
                                tint               = PrimaryBlue,
                                modifier           = Modifier.size(22.dp),
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text       = appointment.patientName,
                                fontSize   = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = TextPrimary,
                            )
                            Text(
                                text     = appointment.patientId,
                                fontSize = 12.sp,
                                color    = TextSecondary,
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(statusBg)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text       = appointment.status,
                            fontSize   = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color      = statusColor,
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text     = appointment.dateTime,
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text     = "Reason: ${appointment.reason}",
                    fontSize = 12.sp,
                    color    = TextSecondary,
                )

                if (isPending) {
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick        = onApprove,
                            modifier       = Modifier.height(34.dp),
                            shape          = RoundedCornerShape(8.dp),
                            colors         = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            contentPadding = PaddingValues(horizontal = 14.dp),
                        ) {
                            Icon(
                                imageVector        = Icons.Outlined.Check,
                                contentDescription = null,
                                modifier           = Modifier.size(14.dp),
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Approve", fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick        = onViewDetails,
                            modifier       = Modifier.height(34.dp),
                            shape          = RoundedCornerShape(8.dp),
                            colors         = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border         = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                            contentPadding = PaddingValues(horizontal = 14.dp),
                        ) {
                            Text("View Details", fontSize = 12.sp)
                        }
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, ErrorRed, RoundedCornerShape(8.dp))
                                .clickable { onReject() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("✕", fontSize = 14.sp, color = ErrorRed)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorAppointmentsPreview() {
    DoctorAppointmentsScreen()
}