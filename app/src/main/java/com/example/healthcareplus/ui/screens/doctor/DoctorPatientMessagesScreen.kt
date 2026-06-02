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
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White         = Color(0xFFFFFFFF)
private val BgLight       = Color(0xFFF3F4F6)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val ErrorRed      = Color(0xFFE53935)
private val ErrorRedBg    = Color(0xFFFFEBEE)
private val GreenTeal     = Color(0xFF10B981)
private val GreenTealBg   = Color(0xFFE6FBF4)
private val BorderBlue    = Color(0xFFBFD0FF)

// ── Urgency levels ────────────────────────────────────────────────────────────
enum class MessageUrgency { NORMAL, UNREAD, URGENT }

// ── Data model ────────────────────────────────────────────────────────────────
data class PatientMessageThread(
    val id          : String,
    val patientName : String,
    val patientId   : String,
    val age         : Int,
    val lastMessage : String,
    val time        : String,
    val unreadCount : Int,
    val urgency     : MessageUrgency,
    val isSentByMe  : Boolean = false,
)

private val tabs = listOf("All", "Unread", "Urgent")

// ─────────────────────────────────────────────────────────────────────────────
// DoctorPatientMessagesScreen  (navigated from DoctorMessagesScreen or Home)
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorPatientMessagesScreen(
    onBack      : () -> Unit = {},
    onOpenChat  : (PatientMessageThread) -> Unit = {},
) {
    var selectedTab  by remember { mutableStateOf(0) }
    var searchQuery  by remember { mutableStateOf("") }

    val threads = listOf(
        PatientMessageThread("1", "John Doe",      "HC-2024-0123", 45, "I'm experiencing severe chest pain and...", "5 min ago",  3, MessageUrgency.URGENT),
        PatientMessageThread("2", "Jane Smith",    "HC-2024-0456", 32, "When should I schedule my follow-up?",      "11:30 AM",   2, MessageUrgency.UNREAD),
        PatientMessageThread("3", "Emily Wilson",  "HC-2024-0678", 28, "You: Continue your current medication",     "Yesterday",  0, MessageUrgency.NORMAL,  isSentByMe = true),
        PatientMessageThread("4", "Michael Davis", "HC-2024-0234", 52, "You: Your lab results look good",           "Feb 12",     0, MessageUrgency.NORMAL,  isSentByMe = true),
        PatientMessageThread("5", "Robert Brown",  "HC-2024-0789", 45, "Thank you for the prescription update",    "Feb 10",     1, MessageUrgency.UNREAD),
    )

    val filtered = threads
        .filter { t ->
            when (selectedTab) {
                1    -> t.unreadCount > 0
                2    -> t.urgency == MessageUrgency.URGENT
                else -> true
            }
        }
        .filter { t ->
            searchQuery.isBlank() ||
                    t.patientName.contains(searchQuery, ignoreCase = true) ||
                    t.patientId.contains(searchQuery, ignoreCase = true)
        }

    val unreadTotal = threads.count { it.unreadCount > 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight),
    ) {
        // ── Back ──────────────────────────────────────────────────────────
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
            text       = "Patient Messages",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            modifier   = Modifier.padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(14.dp))

        // ── Tabs ──────────────────────────────────────────────────────────
        Row(
            modifier              = Modifier.padding(horizontal = 20.dp),
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
                        .padding(horizontal = 16.dp, vertical = 7.dp),
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

        Spacer(Modifier.height(12.dp))

        // ── Search ────────────────────────────────────────────────────────
        OutlinedTextField(
            value         = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder   = { Text("Search patients...", color = TextSecondary, fontSize = 14.sp) },
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

        Spacer(Modifier.height(10.dp))

        // ── Unread banner ─────────────────────────────────────────────────
        if (unreadTotal > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(White)
                    .border(1.dp, BorderBlue, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
            ) {
                Row {
                    Text(
                        text       = "$unreadTotal unread messages",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = PrimaryBlue,
                    )
                    Text(
                        text     = " from patients",
                        fontSize = 13.sp,
                        color    = TextSecondary,
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        // ── Thread list ───────────────────────────────────────────────────
        LazyColumn(
            contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(filtered) { thread ->
                PatientThreadCard(
                    thread  = thread,
                    onClick = { onOpenChat(thread) },
                )
            }
            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

// ── Patient thread card ───────────────────────────────────────────────────────
@Composable
private fun PatientThreadCard(
    thread  : PatientMessageThread,
    onClick : () -> Unit,
) {
    val accentColor = when (thread.urgency) {
        MessageUrgency.URGENT -> ErrorRed
        MessageUrgency.UNREAD -> GreenTeal
        MessageUrgency.NORMAL -> Color.Transparent
    }
    val hasUnread = thread.unreadCount > 0

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row {
            // Left accent bar
            if (accentColor != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(
                            color = accentColor,
                            shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp),
                        ),
                )
            } else {
                Spacer(Modifier.width(4.dp))
            }

            Row(
                modifier          = Modifier
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Avatar with unread badge
                Box {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
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
                                text       = thread.unreadCount.toString(),
                                fontSize   = 10.sp,
                                color      = White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically,
                    ) {
                        // Name + urgent badge
                        Row(
                            verticalAlignment  = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text(
                                text       = thread.patientName,
                                fontSize   = 14.sp,
                                fontWeight = if (hasUnread) FontWeight.Bold else FontWeight.SemiBold,
                                color      = TextPrimary,
                            )
                            if (thread.urgency == MessageUrgency.URGENT) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(ErrorRedBg)
                                        .padding(horizontal = 8.dp, vertical = 2.dp),
                                ) {
                                    Text(
                                        text       = "Urgent",
                                        fontSize   = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color      = ErrorRed,
                                    )
                                }
                            }
                        }
                        Text(
                            text     = thread.time,
                            fontSize = 12.sp,
                            color    = TextSecondary,
                        )
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text     = "${thread.patientId} • Age ${thread.age}",
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                    Spacer(Modifier.height(4.dp))
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
                            fontWeight = if (hasUnread) FontWeight.SemiBold else FontWeight.Normal,
                            color      = if (hasUnread) TextPrimary else TextSecondary,
                            maxLines   = 1,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorPatientMessagesPreview() {
    DoctorPatientMessagesScreen()
}