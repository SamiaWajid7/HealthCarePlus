package com.example.healthcareplus.ui.screens.doctor


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.navigation.Routes

// ─────────────────────────────────────────────────────────────────────────────
// Data Model
// ─────────────────────────────────────────────────────────────────────────────

data class AppointmentItem(
    val id: String,
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val type: String,           // e.g. "In-Person Appointment"
    val location: String,       // e.g. "HealthCare+ Clinic, 5th Floor"
    val status: String,         // "Upcoming" | "Completed" | "Cancelled"
    val avatarColor: Color = Primary
)

// ─────────────────────────────────────────────────────────────────────────────
// My Appointments Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun MyAppointmentsScreen(navController: NavController) {

    // Sample data matching the Figma design
    val appointments = listOf(
        AppointmentItem(
            id = "1",
            doctorName = "Dr. Sarah Johnson",
            specialty = "Cardiologist",
            date = "Feb 15, 2024",
            time = "10:00 AM",
            type = "In-Person Appointment",
            location = "HealthCare+ Clinic, 5th Floor",
            status = "Upcoming",
            avatarColor = Color(0xFF2196F3)
        ),
        AppointmentItem(
            id = "2",
            doctorName = "Dr. Michael Chen",
            specialty = "General Physician",
            date = "Feb 20, 2024",
            time = "2:30 PM",
            type = "In-Person Appointment",
            location = "HealthCare+ Clinic, 5th Floor",
            status = "Upcoming",
            avatarColor = Color(0xFF9C27B0)
        )
    )

    // Dialog state
    var showCancelDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<AppointmentItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        // ── Top Bar ───────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Primary,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Back",
                color = Primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }

        // ── Page Title ────────────────────────────────────────────────────
        Text(
            text = "My Appointments",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Appointment Cards ─────────────────────────────────────────────
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(appointments) { appt ->
                AppointmentItemCard(
                    appointment = appt,
                    onMessage = {
                        navController.navigate(Routes.Chat.createRoute(appt.doctorName))
                    },
                    onCancel = {
                        selectedAppointment = appt
                        showCancelDialog = true
                    }
                )
            }
        }
    }

    // ── Cancel Confirmation Dialog ─────────────────────────────────────────
    if (showCancelDialog && selectedAppointment != null) {
        CancelAppointmentDialog(
            appointment = selectedAppointment!!,
            onDismiss = { showCancelDialog = false },
            onConfirm = {
                showCancelDialog = false
                navController.navigate(Routes.AppointmentCancelled.route)
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Appointment Card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AppointmentItemCard(
    appointment: AppointmentItem,
    onMessage: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ── Doctor info row + status badge ────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(appointment.avatarColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = appointment.doctorName
                                .split(" ")
                                .filter { it.isNotEmpty() }
                                .take(2)
                                .joinToString("") { it.first().toString() },
                            color = appointment.avatarColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = appointment.doctorName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = appointment.specialty,
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Status pill
                StatusBadge(status = appointment.status)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ── Date & Time row ───────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Date",
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = appointment.date,
                    fontSize = 13.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Time",
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = appointment.time,
                    fontSize = 13.sp,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── Location / type card ──────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SuccessContainer),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        tint = Success,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = appointment.type,
                            fontSize = 13.sp,
                            color = Success,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = appointment.location,
                            fontSize = 12.sp,
                            color = Success
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ── Action buttons ────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Message button
                Button(
                    onClick = onMessage,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Message",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Cancel button
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Error),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Error)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Error
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Status Badge
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun StatusBadge(status: String) {
    val (bgColor, textColor) = when (status) {
        "Upcoming"  -> BadgeUpcoming  to BadgeUpcomingText
        "Completed" -> BadgeReady     to BadgeReadyText
        "Cancelled" -> ErrorContainer to Error
        else        -> SurfaceVariant to TextSecondary
    }

    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = status,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Cancel Appointment Dialog  (referenced by MyAppointmentsScreen)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun CancelAppointmentDialog(
    appointment: AppointmentItem,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = "Cancel Appointment",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Are you sure you want to cancel your appointment with ${appointment.doctorName}?",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${appointment.date} at ${appointment.time}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Error),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Yes, Cancel", color = White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
            ) {
                Text("Keep", fontWeight = FontWeight.Bold)
            }
        }
    )
}