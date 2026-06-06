package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TextSnippet
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthcareplus.theme.BorderLight
import com.example.healthcareplus.theme.Primary
import com.example.healthcareplus.theme.TextPrimary
import com.example.healthcareplus.theme.TextSecondary
import com.example.healthcareplus.theme.White
import com.example.healthcareplus.ui.navigation.Routes
import com.example.healthcareplus.ui.viewmodel.ProfileUiState
import com.example.healthcareplus.ui.viewmodel.ProfileViewModel

// ─────────────────────────────────────────────────────────────────────────────
// ProfileContentContainer  (full screen)
//
// Navigation context:
//   Route:        Routes.DoctorProfile  ("doctor_profile")
//   Entered from: DoctorHomeScreen / DoctorMessagesScreen → bottom Profile tab
//   Edit Profile: → Routes.DoctorEditProfile
//   Privacy:      → Routes.DoctorSecuritySettings
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ProfileContentContainer(
    navController : NavController,
    vm            : ProfileViewModel = viewModel(),
) {
    val state   by vm.state.collectAsStateWithLifecycle()
    val profile = (state as? ProfileUiState.Success)?.profile

    val doctorName  = profile?.name        ?: "Loading..."
    val doctorEmail = profile?.email       ?: ""
    val phoneNumber = profile?.phone       ?: ""
    val dateOfBirth = profile?.dateOfBirth ?: ""
    val bloodGroup  = profile?.bloodGroup  ?: ""
    val medicalId   = profile?.medicalId   ?: ""

    Scaffold(
        containerColor = Color(0xFFF5F6FA),
        bottomBar = {
            ProfileBottomBar(
                onHome    = { navController.navigate(Routes.DoctorHome.route) },
                onReports = { navController.navigate(Routes.DoctorLabReports.route) },
                onChat    = { navController.navigate(Routes.DoctorMessages.route) },
                onProfile = { /* already here */ },
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {

            // ── Blue header ───────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary)
                    .padding(top = 52.dp, bottom = 36.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Avatar circle
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.Person,
                            contentDescription = null,
                            tint               = Primary,
                            modifier           = Modifier.size(52.dp),
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text       = doctorName,
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White,
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text     = doctorEmail,
                        fontSize = 14.sp,
                        color    = Color.White.copy(alpha = 0.85f),
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Personal Information card ─────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            ) {

                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Text(
                            text       = "Personal Information",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )

                        Spacer(Modifier.height(16.dp))

                        PersonalInfoRow(label = "Phone Number", value = phoneNumber.ifEmpty { "—" })

                        HorizontalDivider(
                            modifier  = Modifier.padding(vertical = 14.dp),
                            color     = BorderLight,
                            thickness = 1.dp,
                        )

                        PersonalInfoRow(label = "Date of Birth", value = dateOfBirth.ifEmpty { "—" })

                        HorizontalDivider(
                            modifier  = Modifier.padding(vertical = 14.dp),
                            color     = BorderLight,
                            thickness = 1.dp,
                        )

                        PersonalInfoRow(label = "Blood Group", value = bloodGroup.ifEmpty { "—" })

                        HorizontalDivider(
                            modifier  = Modifier.padding(vertical = 14.dp),
                            color     = BorderLight,
                            thickness = 1.dp,
                        )

                        PersonalInfoRow(label = "Medical ID", value = medicalId.ifEmpty { "—" })

                        Spacer(Modifier.height(20.dp))

                        // ── Edit Profile button ───────────────────────────
                        Button(
                            onClick  = { navController.navigate(Routes.DoctorEditProfile.route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape  = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        ) {
                            Icon(
                                imageVector        = Icons.Default.Edit,
                                contentDescription = null,
                                modifier           = Modifier.size(18.dp),
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text       = "Edit Profile",
                                fontSize   = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ── Privacy & Security row ────────────────────────────────
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(Routes.DoctorSecuritySettings.route) },
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 18.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector        = Icons.Default.Lock,
                                contentDescription = null,
                                tint               = TextSecondary,
                                modifier           = Modifier.size(22.dp),
                            )
                            Spacer(Modifier.width(14.dp))
                            Text(
                                text     = "Privacy & Security",
                                fontSize = 15.sp,
                                color    = TextPrimary,
                            )
                        }
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint               = TextSecondary,
                            modifier           = Modifier.size(16.dp),
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bottom navigation bar — Profile tab selected
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ProfileBottomBar(
    onHome    : () -> Unit,
    onReports : () -> Unit,
    onChat    : () -> Unit,
    onProfile : () -> Unit,
) {
    data class NavItem(
        val icon     : ImageVector,
        val label    : String,
        val onClick  : () -> Unit,
        val selected : Boolean,
    )

    val items = listOf(
        NavItem(Icons.Outlined.FavoriteBorder,    "Home",    onHome,    false),
        NavItem(Icons.Outlined.TextSnippet,       "Reports", onReports, false),
        NavItem(Icons.Outlined.ChatBubbleOutline, "Chat",    onChat,    false),
        NavItem(Icons.Outlined.Person,            "Profile", onProfile, true),
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick  = item.onClick,
                icon     = {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = item.label,
                    )
                },
                label = {
                    Text(
                        text     = item.label,
                        fontSize = 11.sp,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Primary,
                    selectedTextColor   = Primary,
                    indicatorColor      = Color.Transparent,
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                ),
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helper
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PersonalInfoRow(label: String, value: String) {
    Column {
        Text(
            text     = label,
            fontSize = 12.sp,
            color    = TextSecondary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text       = value,
            fontSize   = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color      = TextPrimary,
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ProfileContentContainerPreview() {
    ProfileContentContainer(navController = rememberNavController())
}