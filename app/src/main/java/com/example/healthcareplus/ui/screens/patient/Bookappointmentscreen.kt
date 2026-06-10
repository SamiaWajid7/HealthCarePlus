package com.example.healthcareplus.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.healthcareplus.ui.viewmodel.AppointmentUiState
import com.example.healthcareplus.ui.viewmodel.AppointmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    navController : NavController,
    vm            : AppointmentViewModel = viewModel(),
) {
    val primaryBlue = Color(0xFF3B4EFF)
    val state by vm.state.collectAsStateWithLifecycle()

    // ── Local UI state ────────────────────────────────────────────────────
    var selectedDoctor    by remember { mutableStateOf("") }
    var selectedDoctorId  by remember { mutableStateOf("") }
    var selectedSpecialty by remember { mutableStateOf("Cardiology") }
    var selectedTime      by remember { mutableStateOf("9:00 AM") }
    var selectedDate      by remember { mutableStateOf("02/14/2026") }
    var reason            by remember { mutableStateOf("") }

    // Doctors list — in Phase 3 this will come from Firestore
    // For now keeping the same doctors but with placeholder IDs
    val doctors = listOf(
        Triple("doctor_id_1", "Dr. Sarah Johnson", "Cardiologist • 15 years exp."),
        Triple("doctor_id_2", "Dr. Michael Chen",  "Cardiologist • 12 years exp."),
    )

    val timeSlots = listOf("9:00 AM", "10:00 AM", "11:00 AM", "2:00 PM", "3:00 PM", "4:00 PM")

    // Navigate to confirmed screen when booking succeeds
    LaunchedEffect(state) {
        if (state is AppointmentUiState.BookSuccess) {
            navController.navigate("appointment_confirmed")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        // ── Top Bar ───────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = primaryBlue,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("< Back", color = primaryBlue, fontSize = 16.sp)
        }

        Text(
            text       = "Book Appointment",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF1A1A2E),
            modifier   = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // ── Select Specialty ──────────────────────────────────────────
            Text("Select Specialty", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Text(
                    text     = selectedSpecialty,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 15.sp,
                    color    = Color(0xFF1A1A2E)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Select Doctor ─────────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text("Select Doctor", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
                Text("View All", fontSize = 13.sp, color = primaryBlue)
            }
            Spacer(modifier = Modifier.height(8.dp))

            doctors.forEach { (doctorId, name, info) ->
                val isSelected = selectedDoctor == name
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) primaryBlue else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            selectedDoctor   = name
                            selectedDoctorId = doctorId
                        },
                    shape     = RoundedCornerShape(12.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 2.dp)
                ) {
                    Row(
                        modifier          = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier         = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE3F2FD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(name.first().toString(), color = primaryBlue, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                            Text(info, fontSize = 13.sp, color = Color.Gray)
                        }
                        if (isSelected) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = primaryBlue)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Select Date ───────────────────────────────────────────────
            Text("Select Date", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Text(
                    text     = selectedDate,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 15.sp,
                    color    = Color(0xFF1A1A2E)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Reason ────────────────────────────────────────────────────
            Text("Reason for Visit", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value         = reason,
                onValueChange = { reason = it },
                placeholder   = { Text("Briefly describe your symptoms...", fontSize = 13.sp, color = Color.Gray) },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp),
                colors        = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor    = Color(0xFFDDDDDD),
                    focusedBorderColor      = primaryBlue,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor   = Color.White,
                ),
                minLines = 3,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Available Time Slots ──────────────────────────────────────
            Text("Available Time Slots", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(8.dp))

            val chunkedSlots = timeSlots.chunked(3)
            chunkedSlots.forEach { rowSlots ->
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowSlots.forEach { slot ->
                        val isSelected = selectedTime == slot
                        val isDisabled = slot == "4:00 PM"
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.5.dp,
                                    color = if (isSelected) primaryBlue else Color(0xFFDDDDDD),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(
                                    color = if (isSelected) primaryBlue else Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable(enabled = !isDisabled) { selectedTime = slot }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text       = slot,
                                fontSize   = 13.sp,
                                color      = when {
                                    isSelected -> Color.White
                                    isDisabled -> Color(0xFFBBBBBB)
                                    else       -> Color(0xFF1A1A2E)
                                },
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                    repeat(3 - rowSlots.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Appointment Type ──────────────────────────────────────────
            Text("Appointment Type", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier  = Modifier.border(1.5.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp)),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier             = Modifier.padding(20.dp),
                    horizontalAlignment  = Alignment.CenterHorizontally
                ) {
                    Text("🏥", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("In-Person", fontSize = 13.sp, color = Color(0xFF1A1A2E))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Error message
            val errorState = state as? AppointmentUiState.Error
            if (errorState != null) {
                Text(
                    text      = errorState.message,
                    color     = Color.Red,
                    fontSize  = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // ── Book Button ───────────────────────────────────────────────
            Button(
                onClick = {
                    vm.bookAppointment(
                        doctorId   = selectedDoctorId,
                        doctorName = selectedDoctor,
                        specialty  = selectedSpecialty,
                        reason     = reason,
                        date       = selectedDate,
                        time       = selectedTime,
                    )
                },
                enabled  = state !is AppointmentUiState.Loading && selectedDoctor.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape  = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                if (state is AppointmentUiState.Loading) {
                    CircularProgressIndicator(
                        modifier    = Modifier.size(22.dp),
                        color       = Color.White,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text("Book Appointment", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}