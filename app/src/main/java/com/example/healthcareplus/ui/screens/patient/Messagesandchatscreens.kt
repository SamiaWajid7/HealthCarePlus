package com.example.healthcareplus.ui.screens.patient


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

data class MessageThread(
    val id: String,
    val doctorName: String,
    val specialty: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val isOnline: Boolean,
    val avatarColor: Color
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromMe: Boolean,
    val time: String
)

// ─────────────────────────────────────────────────────────────────────────────
// Messages List Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesListScreen(navController: NavController) {
    val primaryBlue = Color(0xFF3B4EFF)

    var searchQuery by remember { mutableStateOf("") }

    val threads = listOf(
        MessageThread("1", "Dr. Michael Chen", "General Physician", "Don't forget to take your medication with...", "Yesterday", 1, false, Color(0xFF9C27B0)),
        MessageThread("2", "Dr. Sarah Johnson", "Cardiologist", "Your test results are ready. Please check...", "10:45 AM", 2, true, Color(0xFF2196F3)),
        MessageThread("3", "Dr. Emily Davis", "Dermatologist", "Thank you for the follow-up appointment", "Feb 10", 0, true, Color(0xFFF06292)),
        MessageThread("4", "Dr. James Wilson", "Orthopedic Surgeon", "You're welcome! Take care", "Feb 08", 0, false, Color(0xFFFF9800)),
        MessageThread("5", "Dr. Lisa Anderson", "Pediatrician", "Photo", "Feb 05", 0, false, Color(0xFF4CAF50))
    )

    val filteredThreads = threads.filter {
        it.doctorName.contains(searchQuery, ignoreCase = true) ||
                it.specialty.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        Text(
            "Messages",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 12.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search conversations...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = primaryBlue,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredThreads) { thread ->
                MessageThreadItem(
                    thread = thread,
                    primaryBlue = primaryBlue,
                    onClick = { navController.navigate("chat/${thread.id}/${thread.doctorName}") }
                )
                HorizontalDivider(modifier = Modifier.padding(start = 84.dp), color = Color(0xFFEEEEEE))
            }
        }
    }
}

@Composable
fun MessageThreadItem(thread: MessageThread, primaryBlue: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(52.dp)) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(thread.avatarColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    thread.doctorName.split(" ").filter { it.isNotEmpty() }.take(2).joinToString("") { it.first().toString() },
                    color = thread.avatarColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            if (thread.unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(thread.unreadCount.toString(), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
            if (thread.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(thread.doctorName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                Text(thread.time, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(thread.specialty, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                thread.lastMessage,
                fontSize = 13.sp,
                color = if (thread.unreadCount > 0) Color(0xFF1A1A2E) else Color.Gray,
                fontWeight = if (thread.unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal,
                maxLines = 1
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Chat Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, doctorName: String?) {
    val primaryBlue = Color(0xFF3B4EFF)
    val displayName = doctorName ?: "Dr. Michael Chen"

    var messageInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val messages = remember {
        mutableStateListOf(
            ChatMessage("1", "Hello!", true, "10:32 AM"),
            ChatMessage("2", "Hello!", false, "10:30 AM"),
            ChatMessage("3", "How are you feeling today?", false, "10:30 AM"),
            ChatMessage("4", "Much better, thank you. The medication is helping.", true, "10:32 AM"),
            ChatMessage("5", "Great to hear! Continue the prescribed dosage for another week.", false, "10:33 AM")
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F6FA))) {
        // Top Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = primaryBlue,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("MC", color = primaryBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(displayName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1A1A2E))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF4CAF50)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Online", fontSize = 12.sp, color = Color(0xFF4CAF50))
                    }
                }
                Icon(
                    Icons.Default.Videocam,
                    contentDescription = "Video Call",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.navigate("video_call/$displayName") }
                )
            }
        }

        // Messages
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFDDDDDD), RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text("Today", fontSize = 12.sp, color = Color(0xFF666666))
                    }
                }
            }
            items(messages) { msg ->
                ChatBubble(message = msg, primaryBlue = primaryBlue)
            }
        }

        // Input Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF0F0F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    placeholder = { Text("Type a message...", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFF0F0F0),
                        focusedContainerColor = Color(0xFFF0F0F0)
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(primaryBlue)
                        .clickable {
                            if (messageInput.isNotBlank()) {
                                messages.add(
                                    ChatMessage(
                                        id = System.currentTimeMillis().toString(),
                                        text = messageInput,
                                        isFromMe = true,
                                        time = "Now"
                                    )
                                )
                                messageInput = ""
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, primaryBlue: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (message.isFromMe) primaryBlue else Color.White,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                            bottomEnd = if (message.isFromMe) 4.dp else 16.dp
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (message.isFromMe) Color.White else Color(0xFF1A1A2E),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(message.time, fontSize = 11.sp, color = Color.Gray)
        }
    }
}