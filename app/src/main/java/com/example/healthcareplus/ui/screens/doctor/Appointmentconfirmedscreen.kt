package com.example.healthcareplus.ui.screens.doctor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.navigation.Routes

// ─────────────────────────────────────────────────────────────────────────────
// Appointment Confirmed Screen
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Shown after a patient successfully books an appointment.
 * Matches the Figma "confirm" screen:
 *   - Large green check circle
 *   - "Appointment Confirmed!" title
 *   - Doctor details card (name, specialty, date, time)
 *   - Arrival reminder banner
 *   - "Back to Home" primary button
 *   - "View My Appointments" outlined button
 *
 * @param navController  Navigation controller for Back to Home / View Appointments.
 * @param doctorName     Doctor's display name (default: Dr. Sarah Johnson).
 * @param specialty      Doctor's specialty   (default: Cardiologist).
 * @param date           Appointment date     (default: Feb 15, 2024).
 * @param time           Appointment time     (default: 9:00 AM).
 */
@Composable
fun AppointmentConfirmedScreen(
    navController: NavController,
    doctorName: String = "Dr. Sarah Johnson",
    specialty: String  = "Cardiologist",
    date: String       = "Feb 15, 2024",
    time: String       = "9:00 AM"
) {
    // Derive initials from doctor name (e.g. "Dr. Sarah Johnson" → "SJ")
    val initials = doctorName
        .split(" ")
        .filter { it.isNotEmpty() && it.first().isLetter() && !it.startsWith("Dr") }
        .take(2)
        .joinToString("") { it.first().toString() }
        .ifEmpty { "SJ" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // ── Green check circle ─────────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(SuccessLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirmed",
                tint = Success,
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Title ──────────────────────────────────────────────────────────
        Text(
            text = "Appointment Confirmed!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your appointment has been successfully booked",
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── Doctor Details Card ────────────────────────────────────────────
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Doctor avatar
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = doctorName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = specialty,
                    fontSize = 14.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = BorderLight)
                Spacer(modifier = Modifier.height(16.dp))

                // Date row
                AppointmentDetailRow(
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Date", tint = TextSecondary, modifier = Modifier.size(18.dp)) },
                    label = "Date",
                    value = date
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Time row
                AppointmentDetailRow(
                    icon = { Icon(Icons.Default.Schedule, contentDescription = "Time", tint = TextSecondary, modifier = Modifier.size(18.dp)) },
                    label = "Time",
                    value = time
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Reminder Banner ────────────────────────────────────────────────
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Reminder",
                    tint = Primary,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Reminder: Please arrive 10 minutes before your appointment time. " +
                            "You will receive a notification 1 hour before your scheduled time.",
                    fontSize = 13.sp,
                    color = PrimaryDark,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── Back to Home Button ────────────────────────────────────────────
        Button(
            onClick = {
                navController.navigate(Routes.PatientHome.route) {
                    popUpTo(Routes.PatientHome.route) { inclusive = false }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text(
                text = "Back to Home",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── View My Appointments Button ────────────────────────────────────
        OutlinedButton(
            onClick = { navController.navigate(Routes.MyAppointments.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
        ) {
            Text(
                text = "View My Appointments",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Helper: single label-value row inside the details card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AppointmentDetailRow(
    icon: @Composable () -> Unit,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                color = TextSecondary
            )
        }
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

