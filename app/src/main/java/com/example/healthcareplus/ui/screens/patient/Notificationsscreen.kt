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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
    val type: String, // "appointment", "lab", "message", "reminder", "test"
    val group: String // "TODAY", "YESTERDAY", "EARLIER"
)

@Composable
fun NotificationsScreen(navController: NavController) {
    val primaryBlue = Color(0xFF3B4EFF)

    val notifications = listOf(
        NotificationItem("1", "Appointment Confirmed", "Your appointment with Dr. Sarah Johnson has been confirmed for Feb 15, 2024 at 10:00 AM", "2 hours ago", false, "appointment", "TODAY"),
        NotificationItem("2", "Lab Results Ready", "Your Complete Blood Count test results are now available to view", "4 hours ago", false, "lab", "TODAY"),
        NotificationItem("3", "New Message from Dr. Chen", "Please continue your current medication as prescribed", "6 hours ago", true, "message", "TODAY"),
        NotificationItem("4", "Appointment Reminder", "Don't forget your appointment tomorrow at 10:00 AM with Dr. Sarah Johnson", "Yesterday, 9:00 AM", true, "reminder", "YESTERDAY"),
        NotificationItem("5", "Test Completed", "Your Lipid Panel test has been completed successfully", "Yesterday, 2:30 PM", true, "test", "YESTERDAY")
    )

    val grouped = notifications.groupBy { it.group }
    val groupOrder = listOf("TODAY", "YESTERDAY", "EARLIER")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = primaryBlue,
                    modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Notifications",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
            }
            Text("Mark all as read", fontSize = 13.sp, color = primaryBlue)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            groupOrder.forEach { group ->
                val items = grouped[group] ?: return@forEach
                item {
                    Text(
                        text = group,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 8.dp)
                    )
                }
                items(items) { notification ->
                    NotificationCard(notification = notification)
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    val (iconBg, iconColor, icon) = when (notification.type) {
        "appointment" -> Triple(Color(0xFFE8EEFF), Color(0xFF3B4EFF), Icons.Default.DateRange)
        "lab" -> Triple(Color(0xFFF0FFF4), Color(0xFF28A745), Icons.Default.Description)
        "message" -> Triple(Color(0xFFF3E5FF), Color(0xFF9C27B0), Icons.Default.Message)
        "reminder" -> Triple(Color(0xFFFFF8E1), Color(0xFFFFA726), Icons.Default.Schedule)
        "test" -> Triple(Color(0xFFE8F5E9), Color(0xFF4CAF50), Icons.Default.CheckCircle)
        else -> Triple(Color(0xFFE0E0E0), Color.Gray, Icons.Default.Notifications)
    }

    val bgColor = if (!notification.isRead) Color.White else Color(0xFFFAFAFA)
    val borderColor = when (notification.type) {
        "appointment" -> Color(0xFF3B4EFF)
        "lab" -> Color(0xFF28A745)
        "message" -> Color(0xFF9C27B0)
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(if (!notification.isRead) 2.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Left color bar for unread
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(60.dp)
                        .background(borderColor, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        notification.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A2E)
                    )
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF3B4EFF))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    notification.message,
                    fontSize = 13.sp,
                    color = Color(0xFF555555),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(notification.time, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}