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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors ────────────────────────────────────────────────────────────────────
private val PrimaryBlue    = Color(0xFF3D5AF1)
private val PrimaryBlueBg  = Color(0xFFEEF2FF)
private val White          = Color(0xFFFFFFFF)
private val TextPrimary    = Color(0xFF1A1A2E)
private val TextSecondary  = Color(0xFF6B7280)
private val BgLight        = Color(0xFFF3F4F6)
private val BadgePending   = Color(0xFFFFF3CD)
private val BadgePendingText = Color(0xFF856404)
private val BorderWarning  = Color(0xFFFFC107)
private val ErrorRed       = Color(0xFFE53935)
private val ErrorRedBg     = Color(0xFFFFEBEE)

// ── Data model ────────────────────────────────────────────────────────────────
data class DoctorAppointment(
    val id          : String,
    val patientName : String,
    val patientId   : String,
    val dateTime    : String,
    val reason      : String,
    val status      : String,   // "Pending" | "Today" | "Upcoming" | "Completed"
)

// ── Tab labels ────────────────────────────────────────────────────────────────
private val tabs = listOf("Pending", "Today", "Upcoming", "Completed")

// ─────────────────────────────────────────────────────────────────────────────
// DoctorAppointmentsScreen
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun DoctorAppointmentsScreen(
    onBack        : () -> Unit = {},
    onViewDetails : (DoctorAppointment) -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf(0) }

    // Sample data — replace with real data source
    val allAppointments = listOf(
        DoctorAppointment("1", "Robert Brown",  "HC-2024-0789", "Feb 16, 2024 • 2:00 PM",  "Chest pain and shortness of breath",  "Pending"),
        DoctorAppointment("2", "Lisa Anderson", "HC-2024-0891", "Feb 17, 2024 • 10:30 AM", "Follow-up consultation",              "Pending"),
        DoctorAppointment("3", "Mark Davis",    "HC-2024-0654", "Feb 17, 2024 • 3:00 PM",  "Annual physical examination",         "Pending"),
        DoctorAppointment("4", "John Doe",      "HC-2024-0123", "Feb 15, 2024 • 10:00 AM", "Routine Checkup",                     "Today"),
        DoctorAppointment("5", "Emma Wilson",   "HC-2024-0456", "Feb 20, 2024 • 9:00 AM",  "Cardiology follow-up",                "Upcoming"),
        DoctorAppointment("6", "Tom Harris",    "HC-2024-0321", "Feb 10, 2024 • 11:00 AM", "Blood pressure monitoring",           "Completed"),
    )

    val filtered = allAppointments.filter { it.status == tabs[selectedTab] }
    val pendingCount = allAppointments.count { it.status == "Pending" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight),
    ) {
        // ── Top bar ───────────────────────────────────────────────────────
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

        // ── Tab row ───────────────────────────────────────────────────────
        Row(
            modifier            = Modifier
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

        // ── Pending action banner (only on Pending tab) ───────────────────
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

        // ── List ──────────────────────────────────────────────────────────
        LazyColumn(
            contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(filtered) { appt ->
                DoctorAppointmentCard(
                    appointment   = appt,
                    onViewDetails = { onViewDetails(appt) },
                    onApprove     = { /* handle approve */ },
                    onReject      = { /* handle reject */ },
                )
            }
            item { Spacer(Modifier.height(12.dp)) }
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

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        // Left colour accent bar for pending
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

                // ── Patient row ───────────────────────────────────────────
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
                    // Status badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(BadgePending)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text       = appointment.status,
                            fontSize   = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color      = BadgePendingText,
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // ── Date/time ─────────────────────────────────────────────
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

                // ── Action buttons (pending only) ─────────────────────────
                if (isPending) {
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Approve
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
                        // View Details
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
                        // Reject (X button)
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