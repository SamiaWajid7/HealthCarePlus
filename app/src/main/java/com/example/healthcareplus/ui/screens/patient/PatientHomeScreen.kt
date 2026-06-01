package com.example.healthcareplus.ui.screens.patient

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
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White         = Color(0xFFFFFFFF)
private val WhiteDim      = Color(0xCCFFFFFF)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val BgLight       = Color(0xFFF3F4F6)
private val GreenTeal     = Color(0xFF10B981)
private val GreenTealBg   = Color(0xFFE6FBF4)
private val PurpleBg      = Color(0xFFEDE9FE)
private val PurpleTint    = Color(0xFF8B5CF6)
private val OrangeBg      = Color(0xFFFFF3E0)
private val OrangeTint    = Color(0xFFF59E0B)
private val CardBorder    = Color(0xFFE5E7EB)

// ── Data models ───────────────────────────────────────────────────────────────
data class QuickActionItem(
    val icon    : ImageVector,
    val iconBg  : Color,
    val iconTint: Color,
    val label   : String,
    val subLabel: String,
)

data class RecentActivityItem(
    val icon       : ImageVector,
    val iconBg     : Color,
    val iconTint   : Color,
    val title      : String,
    val description: String,
    val time       : String,
)

@Composable
fun PatientHomeScreen(
    patientName         : String = "John Doe",
    nextDoctorName      : String = "Dr. Sarah Johnson",
    nextAppointmentDate : String = "Feb 15, 2024 • 10:00 AM",
    onNotifications     : () -> Unit = {},
    onLabReports        : () -> Unit = {},
    onBookVisit         : () -> Unit = {},
    onChat              : () -> Unit = {},
    onProfile           : () -> Unit = {},
) {
    val quickActions = listOf(
        QuickActionItem(
            icon     = Icons.Outlined.Description,
            iconBg   = PrimaryBlueBg,
            iconTint = PrimaryBlue,
            label    = "Lab Reports",
            subLabel = "View results",
        ),
        QuickActionItem(
            icon     = Icons.Outlined.CalendarMonth,
            iconBg   = GreenTealBg,
            iconTint = GreenTeal,
            label    = "Book Visit",
            subLabel = "Schedule now",
        ),
        QuickActionItem(
            icon     = Icons.Outlined.ChatBubbleOutline,
            iconBg   = PurpleBg,
            iconTint = PurpleTint,
            label    = "Chat",
            subLabel = "Ask doctor",
        ),
        QuickActionItem(
            icon     = Icons.Outlined.Person,
            iconBg   = OrangeBg,
            iconTint = OrangeTint,
            label    = "Profile",
            subLabel = "Account Settings",
        ),
    )

    val recentActivities = listOf(
        RecentActivityItem(
            icon        = Icons.Outlined.CalendarMonth,
            iconBg      = PrimaryBlueBg,
            iconTint    = PrimaryBlue,
            title       = "Appointment Confirmed",
            description = "Dr. Sarah Johnson – Feb 15",
            time        = "2h ago",
        ),
        RecentActivityItem(
            icon        = Icons.Outlined.Description,
            iconBg      = GreenTealBg,
            iconTint    = GreenTeal,
            title       = "Lab Results Ready",
            description = "Complete Blood Count",
            time        = "4h ago",
        ),
        RecentActivityItem(
            icon        = Icons.Outlined.ChatBubbleOutline,
            iconBg      = PurpleBg,
            iconTint    = PurpleTint,
            title       = "New Message",
            description = "Dr. Chen sent a message",
            time        = "6h ago",
        ),
    )

    Scaffold(
        bottomBar = {
            PatientBottomBar(
                selected  = 0,
                onHome    = {},
                onReports = onLabReports,
                onChat    = onChat,
                onProfile = onProfile,
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
                        shape = RoundedCornerShape(
                            bottomStart = 28.dp,
                            bottomEnd   = 28.dp,
                        ),
                    )
                    .padding(
                        start  = 20.dp,
                        end    = 20.dp,
                        top    = 52.dp,
                        bottom = 24.dp,
                    ),
            ) {
                Column {
                    // Top row: greeting + bell
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text     = "Welcome back,",
                                fontSize = 13.sp,
                                color    = WhiteDim,
                            )
                            Text(
                                text       = patientName,
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color      = White,
                            )
                        }
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

                    // Next appointment card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(White.copy(alpha = 0.15f))
                            .padding(14.dp),
                    ) {
                        Column {
                            Text(
                                text     = "Next Appointment",
                                fontSize = 11.sp,
                                color    = WhiteDim,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text       = nextDoctorName,
                                fontSize   = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = White,
                            )
                            Text(
                                text     = nextAppointmentDate,
                                fontSize = 12.sp,
                                color    = WhiteDim,
                            )
                        }
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

            Column(
                modifier            = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                quickActions.chunked(2).forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowItems.forEach { action ->
                            PatientQuickActionCard(
                                action   = action,
                                modifier = Modifier.weight(1f),
                                onClick  = {
                                    when (action.label) {
                                        "Lab Reports" -> onLabReports()
                                        "Book Visit"  -> onBookVisit()
                                        "Chat"        -> onChat()
                                        "Profile"     -> onProfile()
                                    }
                                },
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Recent Activity ──────────────────────────────────────────────
            Text(
                text       = "Recent Activity",
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
                recentActivities.forEach { activity ->
                    RecentActivityCard(activity = activity)
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

// ── Quick action card ─────────────────────────────────────────────────────────
@Composable
private fun PatientQuickActionCard(
    action  : QuickActionItem,
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
                    text     = action.subLabel,
                    fontSize = 11.sp,
                    color    = TextSecondary,
                )
            }
        }
    }
}

// ── Recent activity card ──────────────────────────────────────────────────────
@Composable
private fun RecentActivityCard(activity: RecentActivityItem) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(activity.iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = activity.icon,
                    contentDescription = null,
                    tint               = activity.iconTint,
                    modifier           = Modifier.size(22.dp),
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = activity.title,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Text(
                    text     = activity.description,
                    fontSize = 12.sp,
                    color    = TextSecondary,
                )
            }
            Text(
                text     = activity.time,
                fontSize = 11.sp,
                color    = TextSecondary,
            )
        }
    }
}

// ── Patient bottom navigation bar ─────────────────────────────────────────────
@Composable
fun PatientBottomBar(
    selected  : Int = 0,
    onHome    : () -> Unit = {},
    onReports : () -> Unit = {},
    onChat    : () -> Unit = {},
    onProfile : () -> Unit = {},
) {
    val items = listOf(
        Triple(Icons.Outlined.MonitorHeart,      "Home",    onHome),
        Triple(Icons.Outlined.Description,        "Reports", onReports),
        Triple(Icons.Outlined.ChatBubbleOutline,  "Chat",    onChat),
        Triple(Icons.Outlined.Person,             "Profile", onProfile),
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
private fun PatientHomePreview() {
    PatientHomeScreen()
}