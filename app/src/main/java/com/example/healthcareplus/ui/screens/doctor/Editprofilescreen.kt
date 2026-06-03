package com.example.healthcareplus.ui.screens.doctor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthcareplus.theme.Background
import com.example.healthcareplus.theme.BorderLight
import com.example.healthcareplus.theme.Primary
import com.example.healthcareplus.theme.PrimaryContainer
import com.example.healthcareplus.theme.SurfaceVariant
import com.example.healthcareplus.theme.TextPrimary
import com.example.healthcareplus.theme.TextSecondary
import com.example.healthcareplus.theme.White

// ─────────────────────────────────────────────────────────────────────────────
// EditProfileScreen
//
// Navigation context:
//   Route:        "edit_profile"  (Routes.EditProfile)
//   Entered from: ProfileScreen → "Edit Profile" button  (route = "profile")
//   Back (< Back): navController.popBackStack() → returns to ProfileScreen
//   Save Changes: shows ChangesSavedDialog internally;
//                 onDismiss of dialog calls navController.popBackStack()
//                 → returns to ProfileScreen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {

    // ── Local state (pre-filled with Dr. Sarah Johnson demo data) ─────────
    var fullName    by remember { mutableStateOf("Dr. Sarah Johnson") }
    var email       by remember { mutableStateOf("SarahJohnson@email.com") }
    var phone       by remember { mutableStateOf("+1 (555) 123-4567") }
    var dob         by remember { mutableStateOf("01/15/1990") }
    var bloodGroup  by remember { mutableStateOf("O+") }
    var showDialog  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {

        // ── Top bar ───────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint               = Primary,
                modifier           = Modifier
                    .size(20.dp)
                    .clickable { navController.popBackStack() },
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text     = "Back",
                color    = Primary,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.popBackStack() },
            )
        }

        // ── Screen title ──────────────────────────────────────────────────
        Text(
            text       = "Edit Profile",
            fontSize   = 24.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            modifier   = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
        )

        HorizontalDivider(
            modifier  = Modifier.padding(top = 12.dp),
            color     = BorderLight,
            thickness = 1.dp,
        )

        // ── Scrollable form ───────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {

            Spacer(Modifier.height(28.dp))

            // ── Avatar with edit badge ────────────────────────────────────
            Box(
                modifier          = Modifier.fillMaxWidth(),
                contentAlignment  = Alignment.Center,
            ) {
                Box(modifier = Modifier.size(84.dp)) {
                    // Avatar circle
                    Box(
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Person,
                            contentDescription = null,
                            tint               = Primary,
                            modifier           = Modifier.size(50.dp),
                        )
                    }
                    // Edit badge
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Primary)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Edit,
                            contentDescription = "Change photo",
                            tint               = White,
                            modifier           = Modifier.size(15.dp),
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Form fields ───────────────────────────────────────────────
            EditProfileField(
                label         = "Full Name",
                value         = fullName,
                onValueChange = { fullName = it },
            )
            Spacer(Modifier.height(16.dp))

            EditProfileField(
                label         = "Email",
                value         = email,
                onValueChange = { email = it },
            )
            Spacer(Modifier.height(16.dp))

            EditProfileField(
                label         = "Phone Number",
                value         = phone,
                onValueChange = { phone = it },
            )
            Spacer(Modifier.height(16.dp))

            EditProfileField(
                label         = "Date of Birth",
                value         = dob,
                onValueChange = { dob = it },
                trailingIcon  = {
                    Icon(
                        imageVector        = Icons.Default.Edit,
                        contentDescription = "Pick date",
                        tint               = TextSecondary,
                        modifier           = Modifier.size(18.dp),
                    )
                }
            )
            Spacer(Modifier.height(16.dp))

            // Blood Group — read-only display (greyed out like the design)
            Column {
                Text(
                    text       = "Blood Group",
                    fontSize   = 13.sp,
                    color      = TextSecondary,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceVariant),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text     = bloodGroup,
                        fontSize = 15.sp,
                        color    = TextPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── Save Changes button ───────────────────────────────────────
            Button(
                onClick  = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape  = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
            ) {
                Text(
                    text       = "Save Changes",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = White,
                )
            }

            Spacer(Modifier.height(28.dp))
        }
    }

    // ── Changes Saved dialog ──────────────────────────────────────────────
    if (showDialog) {
        ChangesSavedDialog(
            onDismiss = {
                showDialog = false
                navController.popBackStack()
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helper: labelled OutlinedTextField
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileField(
    label         : String,
    value         : String,
    onValueChange : (String) -> Unit,
    trailingIcon  : (@Composable () -> Unit)? = null,
) {
    Column {
        Text(
            text       = label,
            fontSize   = 13.sp,
            color      = TextSecondary,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(12.dp),
            singleLine    = true,
            trailingIcon  = trailingIcon,
            colors        = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor     = BorderLight,
                focusedBorderColor       = Primary,
                unfocusedContainerColor  = White,
                focusedContainerColor    = White,
            ),
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}