package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
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
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White         = Color(0xFFFFFFFF)
private val BgLight       = Color(0xFFF3F4F6)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val OnlineGreen   = Color(0xFF10B981)
private val ErrorRed      = Color(0xFFE53935)

// ── Data model ────────────────────────────────────────────────────────────────
data class DoctorMessageThread(
    val id          : String,
    val patientName : String,
    val specialty   : String,   // role label shown under name
    val lastMessage : String,
    val time        : String,
    val unreadCount : Int,
    val isOnline    : Boolean,
    val avatarColor : Color,
    val isSentByMe  : Boolean = false,
)

// ─────────────────────────────────────────────────────────────────────────────
// DoctorMessagesScreen  (bottom-nav "Chat" destination)
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorMessagesScreen(
    onOpenChat  : (DoctorMessageThread) -> Unit = {},
    onHome      : () -> Unit = {},
    onReports   : () -> Unit = {},
    onProfile   : () -> Unit = {},
) {
    var searchQuery by remember { mutableStateOf("") }

    val threads = listOf(
        DoctorMessageThread("1", "James",     "General Physician",  "Don't forget to take your medication with...", "Yesterday", 1, false, Color(0xFF9C27B0)),
        DoctorMessageThread("2", "Michael",   "Cardiologist",       "Your test results are ready. Please check...", "10:45 AM",  2, true,  Color(0xFF2196F3)),
        DoctorMessageThread("3", "Alexander", "Dermatologist",      "Thank you for the follow-up appointment",      "Feb 10",    0, true,  Color(0xFFF06292)),
        DoctorMessageThread("4", "Matthew",   "Orthopedic Surgeon", "You're welcome! Take care",                    "Feb 08",    0, false, Color(0xFFFF9800)),
        DoctorMessageThread("5", "Edward",    "Pediatrician",       "Photo",                                        "Feb 05",    0, false, Color(0xFF4CAF50), isSentByMe = true),
    )

    val filtered = if (searchQuery.isBlank()) threads
    else threads.filter {
        it.patientName.contains(searchQuery, ignoreCase = true) ||
                it.specialty.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        bottomBar = {
            DoctorBottomBar(
                selected  = 2,          // Chat tab index
                onHome    = onHome,
                onReports = onReports,
                onChat    = {},
                onProfile = onProfile,
            )
        },
        containerColor = BgLight,
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ── Title ─────────────────────────────────────────────────────
            Text(
                text       = "Messages",
                fontSize   = 24.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
                modifier   = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 12.dp),
            )

            // ── Search bar ────────────────────────────────────────────────
            OutlinedTextField(
                value         = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder   = { Text("Search conversations...", color = TextSecondary, fontSize = 14.sp) },
                leadingIcon   = {
                    Icon(Icons.Outlined.Search, contentDescription = null, tint = TextSecondary)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor    = Color.Transparent,
                    focusedBorderColor      = PrimaryBlue,
                    unfocusedContainerColor = White,
                    focusedContainerColor   = White,
                ),
                singleLine = true,
            )

            Spacer(Modifier.height(8.dp))

            HorizontalDivider(color = Color(0xFFE5E7EB))

            // ── Thread list ───────────────────────────────────────────────
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filtered) { thread ->
                    MessageThreadRow(
                        thread  = thread,
                        onClick = { onOpenChat(thread) },
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 82.dp),
                        color    = Color(0xFFEEEEEE),
                    )
                }
            }
        }
    }
}

// ── Thread row ────────────────────────────────────────────────────────────────
@Composable
private fun MessageThreadRow(
    thread  : DoctorMessageThread,
    onClick : () -> Unit,
) {
    val hasUnread = thread.unreadCount > 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar with optional unread badge
        Box {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(thread.avatarColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Person,
                    contentDescription = null,
                    tint               = thread.avatarColor,
                    modifier           = Modifier.size(26.dp),
                )
            }
            // Online dot
            if (thread.isOnline) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(OnlineGreen)
                        .align(Alignment.BottomEnd),
                )
            }
            // Unread count badge
            if (hasUnread) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(ErrorRed)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text     = thread.unreadCount.toString(),
                        fontSize = 10.sp,
                        color    = White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Text(
                    text       = thread.patientName,
                    fontSize   = 15.sp,
                    fontWeight = if (hasUnread) FontWeight.Bold else FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Text(
                    text     = thread.time,
                    fontSize = 12.sp,
                    color    = TextSecondary,
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text     = thread.specialty,
                fontSize = 12.sp,
                color    = TextSecondary,
            )
            Spacer(Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (thread.isSentByMe) {
                    Icon(
                        imageVector        = Icons.Outlined.Check,
                        contentDescription = null,
                        tint               = TextSecondary,
                        modifier           = Modifier.size(13.dp),
                    )
                    Spacer(Modifier.width(2.dp))
                }
                Text(
                    text       = thread.lastMessage,
                    fontSize   = 13.sp,
                    color      = if (hasUnread) TextPrimary else TextSecondary,
                    fontWeight = if (hasUnread) FontWeight.SemiBold else FontWeight.Normal,
                    maxLines   = 1,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorMessagesPreview() {
    DoctorMessagesScreen()
}