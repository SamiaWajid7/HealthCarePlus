package com.example.healthcareplus.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.example.healthcareplus.ui.common.components.bottombar.ClinicBottomBar
import com.example.healthcareplus.ui.common.components.bottombar.ClinicNavItem
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import com.example.healthcareplus.ui.navigation.Routes

// ─────────────────────────────────────────────────────────────────────────────
// Data Model
// ─────────────────────────────────────────────────────────────────────────────

data class MessageThread(
    val id: String,
    val doctorName: String,
    val specialty: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,   // 0 = no badge
    val isOnline: Boolean,
    val avatarColor: Color,
    val isPhotoMessage: Boolean = false
)

// ─────────────────────────────────────────────────────────────────────────────
// Messages List Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(navController: NavController) {

    var searchQuery by remember { mutableStateOf("") }

    val threads = listOf(
        MessageThread(
            id = "1",
            doctorName = "Dr. Michael Chen",
            specialty = "General Physician",
            lastMessage = "Don't forget to take your medication with...",
            time = "Yesterday",
            unreadCount = 1,
            isOnline = false,
            avatarColor = Color(0xFF9C27B0)
        ),
        MessageThread(
            id = "2",
            doctorName = "Dr. Sarah Johnson",
            specialty = "Cardiologist",
            lastMessage = "Your test results are ready. Please check...",
            time = "10:45 AM",
            unreadCount = 2,
            isOnline = true,
            avatarColor = Color(0xFF2196F3)
        ),
        MessageThread(
            id = "3",
            doctorName = "Dr. Emily Davis",
            specialty = "Dermatologist",
            lastMessage = "Thank you for the follow-up appointment",
            time = "Feb 10",
            unreadCount = 0,
            isOnline = true,
            avatarColor = Color(0xFFF06292)
        ),
        MessageThread(
            id = "4",
            doctorName = "Dr. James Wilson",
            specialty = "Orthopedic Surgeon",
            lastMessage = "You're welcome! Take care",
            time = "Feb 08",
            unreadCount = 0,
            isOnline = false,
            avatarColor = Color(0xFFFF9800)
        ),
        MessageThread(
            id = "5",
            doctorName = "Dr. Lisa Anderson",
            specialty = "Pediatrician",
            lastMessage = "Photo",
            time = "Feb 05",
            unreadCount = 0,
            isOnline = false,
            avatarColor = Color(0xFF4CAF50),
            isPhotoMessage = true
        )
    )

    val filteredThreads = threads.filter {
        it.doctorName.contains(searchQuery, ignoreCase = true) ||
                it.specialty.contains(searchQuery, ignoreCase = true) ||
                it.lastMessage.contains(searchQuery, ignoreCase = true)
    }

    // Bottom nav items matching the Figma design (Home / Reports / Chat / Profile)
    val navItems = listOf(
        ClinicNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, Routes.PatientHome.route),
        ClinicNavItem("Reports", Icons.Filled.Description, Icons.Outlined.Description, Routes.LabReports.route),
        ClinicNavItem("Chat", Icons.Filled.Chat, Icons.Outlined.Chat, Routes.Messages.route),
        ClinicNavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person, Routes.Profile.route),
    )

    Scaffold(
        bottomBar = {
            ClinicBottomBar(
                items = navItems,
                currentRoute = Routes.Messages.route,
                onItemClick = { item -> navController.navigate(item.route) }
            )
        },
        containerColor = Background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
        ) {

            // ── Title ──────────────────────────────────────────────────────
            Text(
                text = "Messages",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 12.dp)
            )

            // ── Search Bar ────────────────────────────────────────────────
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        "Search conversations...",
                        color = TextTertiary,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextTertiary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor   = Primary,
                    unfocusedContainerColor = Surface,
                    focusedContainerColor   = Surface
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ── Thread List ───────────────────────────────────────────────
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredThreads) { thread ->
                    MessageThreadRow(
                        thread = thread,
                        onClick = {
                            navController.navigate(
                                Routes.Chat.createRoute(thread.doctorName)
                            )
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 84.dp),
                        color = BorderLight
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Thread Row Item
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MessageThreadRow(
    thread: MessageThread,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // ── Avatar with badge/online indicator ────────────────────────────
        Box(modifier = Modifier.size(52.dp)) {

            // Avatar circle
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(thread.avatarColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = thread.doctorName
                        .split(" ")
                        .filter { it.isNotEmpty() }
                        .take(2)
                        .joinToString("") { it.first().toString() },
                    color = thread.avatarColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Unread count badge (top-end)
            if (thread.unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = thread.unreadCount.toString(),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Online dot (bottom-end)
            if (thread.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Surface)      // white ring
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Success)  // green dot
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // ── Text content ──────────────────────────────────────────────────
        Column(modifier = Modifier.weight(1f)) {

            // Doctor name + timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = thread.doctorName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextPrimary
                )
                Text(
                    text = thread.time,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Specialty
            Text(
                text = thread.specialty,
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Last message preview (bold if unread)
            Text(
                text = thread.lastMessage,
                fontSize = 13.sp,
                color = if (thread.unreadCount > 0) TextPrimary else TextSecondary,
                fontWeight = if (thread.unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal,
                maxLines = 1
            )
        }
    }
}

