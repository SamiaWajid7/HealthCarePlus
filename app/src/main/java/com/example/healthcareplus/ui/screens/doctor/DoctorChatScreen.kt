package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import kotlinx.coroutines.launch

// ── Colors ────────────────────────────────────────────────────────────────────
private val PrimaryBlue   = Color(0xFF3D5AF1)
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White         = Color(0xFFFFFFFF)
private val BgLight       = Color(0xFFF3F4F6)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val OnlineGreen   = Color(0xFF10B981)
private val BubbleBg      = Color(0xFFF0F2F8)   // received bubble

// ── Data model ────────────────────────────────────────────────────────────────
data class DoctorChatMessage(
    val id       : String,
    val text     : String,
    val isFromMe : Boolean,   // true = doctor sent it
    val time     : String,
)

// ─────────────────────────────────────────────────────────────────────────────
// DoctorChatScreen
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorChatScreen(
    patientName : String = "John Doe",
    isOnline    : Boolean = true,
    onBack      : () -> Unit = {},
) {
    var messageInput by remember { mutableStateOf("") }
    val listState    = rememberLazyListState()
    val scope        = rememberCoroutineScope()

    val messages = remember {
        mutableStateListOf(
            DoctorChatMessage("1", "Hello Doctor, I wanted to ask about my medication dosage.", false, "10:30 AM"),
            DoctorChatMessage("2", "Hello John! I'd be happy to help. What concerns do you have?",  true,  "10:32 AM"),
            DoctorChatMessage("3", "The Lisinopril 10mg – should I take it with food?",              false, "10:33 AM"),
            DoctorChatMessage("4", "You can take Lisinopril with or without food. Take it at the same time each day for best results.", true, "10:35 AM"),
            DoctorChatMessage("5", "Thank you! That's very helpful.",                                false, "10:36 AM"),
            DoctorChatMessage("6", "You can take Lisinopril with or without food. Take it at the same time each day for best results.", true, "10:35 AM"),
        )
    }

    // Scroll to bottom when messages change
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight),
    ) {

        // ── Top bar ───────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text     = "< Back",
                color    = PrimaryBlue,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onBack() },
            )
            Spacer(Modifier.height(10.dp))
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
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text       = patientName,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isOnline) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(OnlineGreen),
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text     = "Online",
                                fontSize = 12.sp,
                                color    = OnlineGreen,
                            )
                        } else {
                            Text(
                                text     = "Offline",
                                fontSize = 12.sp,
                                color    = TextSecondary,
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(color = Color(0xFFE5E7EB))

        // ── Message list ──────────────────────────────────────────────────
        LazyColumn(
            modifier        = Modifier.weight(1f),
            state           = listState,
            contentPadding  = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Date separator
            item {
                Box(
                    modifier         = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFDDDDDD), RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                    ) {
                        Text("Today", fontSize = 12.sp, color = Color(0xFF666666))
                    }
                }
            }
            items(messages) { msg ->
                DoctorChatBubble(message = msg)
            }
        }

        // ── Input bar ─────────────────────────────────────────────────────
        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(0.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Attach
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(BgLight),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.AttachFile,
                        contentDescription = "Attach",
                        tint               = TextSecondary,
                        modifier           = Modifier.size(20.dp),
                    )
                }
                Spacer(Modifier.width(10.dp))
                // Text input
                OutlinedTextField(
                    value         = messageInput,
                    onValueChange = { messageInput = it },
                    placeholder   = { Text("Type a message...", color = TextSecondary, fontSize = 14.sp) },
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(24.dp),
                    colors        = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor    = Color.Transparent,
                        focusedBorderColor      = Color.Transparent,
                        unfocusedContainerColor = BgLight,
                        focusedContainerColor   = BgLight,
                    ),
                    singleLine = true,
                )
                Spacer(Modifier.width(10.dp))
                // Send button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                        .clickable {
                            if (messageInput.isNotBlank()) {
                                messages.add(
                                    DoctorChatMessage(
                                        id       = System.currentTimeMillis().toString(),
                                        text     = messageInput,
                                        isFromMe = true,
                                        time     = "Now",
                                    )
                                )
                                messageInput = ""
                                scope.launch {
                                    listState.animateScrollToItem(messages.size - 1)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Send,
                        contentDescription = "Send",
                        tint               = White,
                        modifier           = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

// ── Chat bubble ───────────────────────────────────────────────────────────────
@Composable
private fun DoctorChatBubble(message: DoctorChatMessage) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start,
    ) {
        Column(
            horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start,
            modifier            = Modifier.widthIn(max = 280.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (message.isFromMe) PrimaryBlue else White,
                        shape = RoundedCornerShape(
                            topStart    = 16.dp,
                            topEnd      = 16.dp,
                            bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                            bottomEnd   = if (message.isFromMe) 4.dp  else 16.dp,
                        ),
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text(
                    text      = message.text,
                    color     = if (message.isFromMe) White else TextPrimary,
                    fontSize  = 14.sp,
                    lineHeight = 21.sp,
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text     = message.time,
                fontSize = 11.sp,
                color    = TextSecondary,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorChatPreview() {
    DoctorChatScreen()
}