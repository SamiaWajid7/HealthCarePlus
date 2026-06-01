package com.example.healthcareplus.ui.common.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.common.utils.ClinicRadius


enum class AppointmentStatus { UPCOMING, PENDING, COMPLETED, CANCELLED }

@Composable
fun AppointmentCard(
    doctorName: String,
    specialty: String,
    date: String,
    time: String,
    appointmentType: String,          // e.g. "In-Person Appointment"
    location: String = "",            // e.g. "HealthCare+ Clinic, 5th Floor"
    status: AppointmentStatus = AppointmentStatus.UPCOMING,
    onMessageClick: (() -> Unit)? = null,
    onCancelClick: (() -> Unit)? = null,
    onApproveClick: (() -> Unit)? = null,   // doctor side only
    onViewDetailsClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val statusColor = when (status) {
        AppointmentStatus.UPCOMING   -> Success
        AppointmentStatus.PENDING    -> Warning
        AppointmentStatus.COMPLETED  -> Primary
        AppointmentStatus.CANCELLED  -> TextSecondary
    }
    val statusLabel = when (status) {
        AppointmentStatus.UPCOMING   -> "Upcoming"
        AppointmentStatus.PENDING    -> "Pending"
        AppointmentStatus.COMPLETED  -> "Completed"
        AppointmentStatus.CANCELLED  -> "Cancelled"
    }

    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(ClinicRadius.Large),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Doctor row + status badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryLight),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint     = Primary,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text       = doctorName,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextPrimary,
                    )
                    Text(specialty, fontSize = 12.sp, color = TextSecondary)
                }
                // Status badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(ClinicRadius.Full))
                        .background(statusColor.copy(alpha = .12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text       = statusLabel,
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = statusColor,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Date / Time row
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint     = TextSecondary,
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(date, fontSize = 12.sp, color = TextSecondary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.AccessTime,
                        contentDescription = null,
                        tint     = TextSecondary,
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(time, fontSize = 12.sp, color = TextSecondary)
                }
            }

            // Appointment type chip
            if (appointmentType.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(ClinicRadius.Small))
                        .background(SuccessLight)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Outlined.MedicalServices,
                        contentDescription = null,
                        tint     = Success,
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Column {
                        Text(
                            text       = appointmentType,
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color      = Success,
                        )
                        if (location.isNotBlank()) {
                            Text(location, fontSize = 11.sp, color = Success.copy(alpha = .75f))
                        }
                    }
                }
            }

            // Action buttons
            val showActions = onMessageClick != null || onCancelClick != null ||
                    onApproveClick != null || onViewDetailsClick != null
            if (showActions) {
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (onApproveClick != null) {
                        Button(
                            onClick  = onApproveClick,
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape    = RoundedCornerShape(ClinicRadius.Full),
                            colors   = ButtonDefaults.buttonColors(containerColor = Primary),
                        ) {
                            Icon(Icons.Outlined.Check, null, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Approve", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    if (onViewDetailsClick != null) {
                        OutlinedButton(
                            onClick  = onViewDetailsClick,
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape    = RoundedCornerShape(ClinicRadius.Full),
                            border = BorderStroke(1.dp, BorderLight),

                            ) {
                            Text("View Details", fontSize = 12.sp, color = TextPrimary)
                        }
                    }
                    if (onMessageClick != null) {
                        Button(
                            onClick  = onMessageClick,
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape    = RoundedCornerShape(ClinicRadius.Full),
                            colors   = ButtonDefaults.buttonColors(containerColor = Primary),
                        ) {
                            Icon(Icons.Outlined.ChatBubbleOutline, null, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Message", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    if (onCancelClick != null) {
                        OutlinedButton(
                            onClick  = onCancelClick,
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape    = RoundedCornerShape(ClinicRadius.Full),
                            border   = BorderStroke(1.dp, Error),
                        ) {
                            Text("Cancel", fontSize = 12.sp, color = Error)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360)
@Composable
private fun PreviewAppointmentCard() {
    AppointmentCard(
        doctorName      = "Dr. Sarah Johnson",
        specialty       = "Cardiologist",
        date            = "Feb 15, 2024",
        time            = "10:00 AM",
        appointmentType = "In-Person Appointment",
        location        = "HealthCare+ Clinic, 5th Floor",
        status          = AppointmentStatus.UPCOMING,
        onMessageClick  = {},
        onCancelClick   = {},
        modifier        = Modifier.padding(16.dp),
    )
}