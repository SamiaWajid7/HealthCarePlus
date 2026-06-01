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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors ────────────────────────────────────────────────────────────────────
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val White         = Color(0xFFFFFFFF)
private val WhiteDim      = Color(0xCCFFFFFF)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val BgLight       = Color(0xFFF3F4F6)
private val GreenTeal     = Color(0xFF10B981)
private val GreenTealBg   = Color(0xFFE6FBF4)
private val PurpleBg      = Color(0xFFEEF2FF)
private val OrangeBg      = Color(0xFFFFF3E0)
private val OrangeTint    = Color(0xFFF59E0B)

// ── Data ──────────────────────────────────────────────────────────────────────
data class QuickAction(
    val icon    : ImageVector,
    val iconBg  : Color,
    val iconTint: Color,
    val label   : String,
    val sub     : String,
)

data class TodayAppointment(
    val patientName: String,
    val patientId  : String,
    val reason     : String,
    val time       : String,
)

@Composable
fun DoctorHomeScreen(
    doctorName      : String = "Dr. Sarah Johnson",
    specialty       : String = "Cardiologist",
    medicalId       : String = "MD-2024-5678",
    newMessages     : Int    = 5,
    pendingCount    : Int    = 3,
    onNotifications : () -> Unit = {},
    onAppointments  : () -> Unit = {},
    onLabReports    : () -> Unit = {},
    onMessages      : () -> Unit = {},
    onProfile       : () -> Unit = {},
    onJoinCall      : (TodayAppointment) -> Unit = {},
) {
    val quickActions = listOf(
        QuickAction(Icons.Outlined.CalendarMonth, GreenTealBg, GreenTeal,  "Appointments", "View & manage"),
        QuickAction(Icons.Outlined.Description,   PrimaryBlueBg,  PrimaryBlue, "Lab Reports",  "Upload reports"),
        QuickAction(Icons.Outlined.ChatBubbleOutline, PurpleBg,  Color(0xFF8B5CF6), "Messages",     "Chat patients"),
        QuickAction(Icons.Outlined.Person,         OrangeBg,   OrangeTint,  "Profile",      "Account Settings"),
    )

    val todayAppointments = listOf(
        TodayAppointment("John Doe", "HC-2024-0123", "Routine Checkup", "10:00 AM"),
    )

    Scaffold(
        bottomBar = {
            DoctorBottomBar(
                selected        = 0,
                onHome          = {},
                onReports       = onLabReports,
                onChat          = onMessages,
                onProfile       = onProfile,
            )
        },
        containerColor = BgLight,
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {

            // ── Header banner ────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = PrimaryBlue,
                        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                    )
                    .padding(start = 20.dp, end = 20.dp, top = 52.dp, bottom = 24.dp),
            ) {
                Column {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.Top,
                    ) {
                        Column {
                            Text(
                                text     = "Good Morning,",
                                fontSize = 13.sp,
                                color    = WhiteDim,
                            )
                            Text(
                                text       = doctorName,
                                fontSize   = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color      = White,
                            )
                            Text(
                                text     = "$specialty • $medicalId",
                                fontSize = 12.sp,
                                color    = WhiteDim,
                            )
                        }
                        // Bell icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(White.copy(alpha = 0.2f))
                                .clickable { onNotifications() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector        = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint               = White,
                                modifier           = Modifier.size(22.dp),
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Stats row
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatChip(label = "New Msgs",  value = newMessages.toString())
                        StatChip(label = "Pending",   value = pendingCount.toString())
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Quick Actions ────────────────────────────────────────────────
            Text(
                text       = "Quick Actions",
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color      = TextPrimary,
                modifier   = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(12.dp))

            // 2x2 grid
            Column(
                modifier            = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                quickActions.chunked(2).forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowItems.forEach { action ->
                            QuickActionCard(
                                action   = action,
                                modifier = Modifier.weight(1f),
                                onClick  = {
                                    when (action.label) {
                                        "Appointments" -> onAppointments()
                                        "Lab Reports"  -> onLabReports()
                                        "Messages"     -> onMessages()
                                        "Profile"      -> onProfile()
                                    }
                                },
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Today's Appointments ─────────────────────────────────────────
            Text(
                text       = "Today's Appointments",
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color      = TextPrimary,
                modifier   = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(12.dp))

            Column(
                modifier            = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                todayAppointments.forEach { appt ->
                    TodayAppointmentCard(
                        appointment = appt,
                        onJoinCall  = { onJoinCall(appt) },
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

// ── Stat chip ─────────────────────────────────────────────────────────────────
@Composable
private fun StatChip(label: String, value: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(White.copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text       = value,
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = White,
            )
            Text(
                text     = label,
                fontSize = 11.sp,
                color    = WhiteDim,
            )
        }
    }
}

// ── Quick action card ─────────────────────────────────────────────────────────
private val PrimaryBlueBg = Color(0xFFEEF2FF)

@Composable
private fun QuickActionCard(
    action  : QuickAction,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
) {
    Card(
        modifier  = modifier
            .height(100.dp)
            .clickable { onClick() },
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(action.iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = action.icon,
                    contentDescription = null,
                    tint               = action.iconTint,
                    modifier           = Modifier.size(20.dp),
                )
            }
            Column {
                Text(
                    text       = action.label,
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Text(
                    text     = action.sub,
                    fontSize = 11.sp,
                    color    = TextSecondary,
                )
            }
        }
    }
}

// ── Today appointment card ────────────────────────────────────────────────────
@Composable
private fun TodayAppointmentCard(
    appointment: TodayAppointment,
    onJoinCall : () -> Unit,
) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlueBg),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Person,
                        contentDescription = null,
                        tint               = PrimaryBlue,
                        modifier           = Modifier.size(24.dp),
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text       = appointment.patientName,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextPrimary,
                    )
                    Text(
                        text     = "ID: ${appointment.patientId}",
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text     = appointment.reason,
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryBlueBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text       = appointment.time,
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = PrimaryBlue,
                    )
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick  = onJoinCall,
                    modifier = Modifier.height(32.dp),
                    shape    = RoundedCornerShape(8.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                ) {
                    Text(
                        text     = "Join Call",
                        fontSize = 12.sp,
                        color    = White,
                    )
                }
            }
        }
    }
}

// ── Bottom navigation bar ─────────────────────────────────────────────────────
@Composable
fun DoctorBottomBar(
    selected  : Int = 0,
    onHome    : () -> Unit = {},
    onReports : () -> Unit = {},
    onChat    : () -> Unit = {},
    onProfile : () -> Unit = {},
) {
    val items = listOf(
        Triple(Icons.Outlined.MonitorHeart, "Home",    onHome),
        Triple(Icons.Outlined.Description,  "Reports", onReports),
        Triple(Icons.Outlined.ChatBubbleOutline, "Chat", onChat),
        Triple(Icons.Outlined.Person,       "Profile", onProfile),
    )

    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp,
    ) {
        items.forEachIndexed { index, (icon, label, action) ->
            NavigationBarItem(
                selected = selected == index,
                onClick  = action,
                icon     = {
                    Icon(
                        imageVector        = icon,
                        contentDescription = label,
                        modifier           = Modifier.size(22.dp),
                    )
                },
                label  = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = PrimaryBlue,
                    selectedTextColor   = PrimaryBlue,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = PrimaryBlueBg,
                ),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorHomePreview() {
    DoctorHomeScreen()
}