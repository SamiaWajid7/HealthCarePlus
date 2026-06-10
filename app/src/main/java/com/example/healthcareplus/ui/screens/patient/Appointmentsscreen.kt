package com.example.healthcareplus.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.healthcareplus.data.model.Appointment
import com.example.healthcareplus.ui.viewmodel.AppointmentUiState
import com.example.healthcareplus.ui.viewmodel.AppointmentViewModel

// ─────────────────────────────────────────────────────────────────────────────
// Appointment Confirmed Screen  (no Firebase needed — just a success screen)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AppointmentConfirmedScreen(navController: NavController) {
    val primaryBlue = Color(0xFF3B4EFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFD4EDDA)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF28A745),
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Appointment Confirmed!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Your appointment has been successfully booked",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("SJ", color = primaryBlue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Dr. Sarah Johnson", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                Text("Cardiologist", fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Date", color = Color.Gray, fontSize = 14.sp)
                    }
                    Text("Feb 15, 2024", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1A1A2E))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Time", color = Color.Gray, fontSize = 14.sp)
                    }
                    Text("9:00 AM", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1A1A2E))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEFF)),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Info, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reminder: Please arrive 10 minutes before your appointment time. You will receive a notification 1 hour before your scheduled time.",
                    fontSize = 13.sp,
                    color = Color(0xFF333366),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("patient_home") },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
        ) {
            Text("Back to Home", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { navController.navigate("my_appointments") },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryBlue),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, primaryBlue)
        ) {
            Text("View My Appointments", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// My Appointments Screen  — live Firestore data
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun MyAppointmentsScreen(
    navController : NavController,
    vm            : AppointmentViewModel = viewModel(),
) {
    val primaryBlue = Color(0xFF3B4EFF)
    val state by vm.state.collectAsStateWithLifecycle()

    var showCancelDialog      by remember { mutableStateOf(false) }
    var selectedAppointment   by remember { mutableStateOf<Appointment?>(null) }

    // Load patient appointments on first composition
    LaunchedEffect(Unit) { vm.loadPatientAppointments() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "< Back",
                color    = primaryBlue,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }

        Text(
            "My Appointments",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF1A1A2E),
            modifier   = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        when (state) {
            is AppointmentUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryBlue)
                }
            }
            is AppointmentUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text      = (state as AppointmentUiState.Error).message,
                        color     = Color.Red,
                        fontSize  = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(24.dp)
                    )
                }
            }
            is AppointmentUiState.Success -> {
                val appointments = (state as AppointmentUiState.Success).appointments
                if (appointments.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "No appointments yet",
                            color    = Color.Gray,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(appointments) { appt ->
                            AppointmentCard(
                                appointment = appt,
                                primaryBlue = primaryBlue,
                                onMessage   = { navController.navigate("chat/${appt.doctorName}") },
                                onCancel    = {
                                    selectedAppointment = appt
                                    showCancelDialog    = true
                                }
                            )
                        }
                    }
                }
            }
            else -> {}
        }
    }

    if (showCancelDialog && selectedAppointment != null) {
        CancelAppointmentDialog(
            appointment = selectedAppointment!!,
            onDismiss   = { showCancelDialog = false },
            onConfirm   = {
                vm.updateStatus(selectedAppointment!!.id, "Cancelled")
                showCancelDialog = false
                navController.navigate("appointment_cancelled")
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Appointment Card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AppointmentCard(
    appointment : Appointment,
    primaryBlue : Color,
    onMessage   : () -> Unit,
    onCancel    : () -> Unit,
) {
    val statusColor = when (appointment.status) {
        "Completed"  -> Color(0xFF28A745)
        "Cancelled"  -> Color.Red
        "Upcoming"   -> Color(0xFF3B4EFF)
        else         -> Color(0xFFF59E0B)  // Pending
    }
    val statusBg = when (appointment.status) {
        "Completed"  -> Color(0xFFE8F5E9)
        "Cancelled"  -> Color(0xFFFFEBEE)
        "Upcoming"   -> Color(0xFFE8EEFF)
        else         -> Color(0xFFFFF3E0)
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier         = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE3F2FD)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            appointment.doctorName.first().toString(),
                            color      = primaryBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(appointment.doctorName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                        Text(appointment.specialty,  fontSize = 13.sp, color = Color.Gray)
                    }
                }
                Box(
                    modifier = Modifier
                        .background(statusBg, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(appointment.status, fontSize = 12.sp, color = statusColor, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(appointment.date, fontSize = 13.sp, color = Color(0xFF555555))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(appointment.time, fontSize = 13.sp, color = Color(0xFF555555))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(10.dp),
                colors    = CardDefaults.cardColors(containerColor = Color(0xFFF0FFF4)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Business, contentDescription = null, tint = Color(0xFF28A745), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("In-Person Appointment", fontSize = 13.sp, color = Color(0xFF28A745), fontWeight = FontWeight.SemiBold)
                        Text("HealthCare+ Clinic",    fontSize = 12.sp, color = Color(0xFF28A745))
                    }
                }
            }

            // Only show action buttons if not already cancelled/completed
            if (appointment.status != "Cancelled" && appointment.status != "Completed") {
                Spacer(modifier = Modifier.height(14.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick  = onMessage,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape    = RoundedCornerShape(10.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                    ) {
                        Icon(Icons.Default.Message, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Message", fontSize = 14.sp)
                    }
                    OutlinedButton(
                        onClick  = onCancel,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape    = RoundedCornerShape(10.dp),
                        colors   = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                        border   = androidx.compose.foundation.BorderStroke(1.5.dp, Color.Red)
                    ) {
                        Text("Cancel", fontSize = 14.sp, color = Color.Red)
                    }
                }
            }
        }
    }
}