package com.example.healthcareplus.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.healthcareplus.ui.viewmodel.ProfileUiState
import com.example.healthcareplus.ui.viewmodel.ProfileViewModel

// ─────────────────────────────────────────────────────────────────────────────
// Profile Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ProfileScreen(
    navController : NavController,
    vm            : ProfileViewModel = viewModel(),
) {
    val primaryBlue = Color(0xFF3B4EFF)
    val state by vm.state.collectAsStateWithLifecycle()
    val profile = (state as? ProfileUiState.Success)?.profile

    val displayName  = profile?.name        ?: "Loading..."
    val displayEmail = profile?.email       ?: ""
    val displayPhone = profile?.phone       ?: ""
    val displayDob   = profile?.dateOfBirth ?: ""
    val displayBlood = profile?.bloodGroup  ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryBlue)
                .padding(top = 40.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = primaryBlue,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(displayEmail, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }

        // Personal Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Personal Information", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))

                Spacer(modifier = Modifier.height(16.dp))

                ProfileInfoRow(label = "Phone Number", value = displayPhone.ifEmpty { "—" })
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))
                ProfileInfoRow(label = "Date of Birth", value = displayDob.ifEmpty { "—" })
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))
                ProfileInfoRow(label = "Blood Group", value = displayBlood.ifEmpty { "—" })
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))
                ProfileInfoRow(label = "Patient ID", value = "HC-2024-0123")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { navController.navigate("edit_profile") },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit Profile", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Privacy & Security
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable { navController.navigate("security_settings") },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Privacy & Security", fontSize = 15.sp, color = Color(0xFF1A1A2E))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Column {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Edit Profile Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController : NavController,
    vm            : ProfileViewModel = viewModel(),
) {
    val primaryBlue = Color(0xFF3B4EFF)
    val state by vm.state.collectAsStateWithLifecycle()
    val profile = (state as? ProfileUiState.Success)?.profile

    // remember(profile) — re-initializes when Firestore data arrives
    var fullName   by remember(profile) { mutableStateOf(profile?.name        ?: "") }
    var email      by remember(profile) { mutableStateOf(profile?.email       ?: "") }
    var phone      by remember(profile) { mutableStateOf(profile?.phone       ?: "") }
    var dob        by remember(profile) { mutableStateOf(profile?.dateOfBirth ?: "") }
    var bloodGroup by remember(profile) { mutableStateOf(profile?.bloodGroup  ?: "") }
    var showSavedDialog by remember { mutableStateOf(false) }

    // Show dialog when save succeeds
    LaunchedEffect(state) {
        if (state is ProfileUiState.SaveSuccess) showSavedDialog = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = primaryBlue,
                modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("< Back", color = primaryBlue, fontSize = 16.sp, modifier = Modifier.clickable { navController.popBackStack() })
        }

        Text(
            "Edit Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Avatar
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(80.dp)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE3F2FD)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(48.dp))
                    }
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(primaryBlue)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            ProfileTextField("Full Name", fullName) { fullName = it }
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTextField("Email", email) { email = it }
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTextField("Phone Number", phone) { phone = it }
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTextField("Date of Birth", dob) { dob = it }
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTextField("Blood Group", bloodGroup) { bloodGroup = it }

            Spacer(modifier = Modifier.height(28.dp))

            // Error message if save fails
            val errorState = state as? ProfileUiState.Error
            if (errorState != null) {
                Text(
                    text = errorState.message,
                    color = Color.Red,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = { vm.saveProfile(fullName, phone, dob, bloodGroup) },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                if (state is ProfileUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save Changes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showSavedDialog) {
        ChangesSavedDialog(onDismiss = {
            showSavedDialog = false
            navController.popBackStack()
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    val primaryBlue = Color(0xFF3B4EFF)
    Column {
        Text(label, fontSize = 13.sp, color = Color(0xFF555555), fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFDDDDDD),
                focusedBorderColor = primaryBlue,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Changes Saved Dialog
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ChangesSavedDialog(onDismiss: () -> Unit) {
    val primaryBlue = Color(0xFF3B4EFF)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(Color(0xFFD4EDDA)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF28A745), modifier = Modifier.size(40.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("Changes Saved!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Your profile has been updated successfully.", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Text("Done", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}