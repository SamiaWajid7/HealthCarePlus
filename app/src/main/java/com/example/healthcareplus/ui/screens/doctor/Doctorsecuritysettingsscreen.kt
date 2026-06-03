package com.example.healthcareplus.ui.screens.doctor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthcareplus.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// DoctorSecuritySettingsScreen
//
// Navigation context:
//   Route:          Routes.SecuritySettings  ("security_settings")
//   Navigated from: ProfileContentContainer → "Privacy & Security" row
//   Back:           navController.popBackStack()
//   Logout confirm: navController.navigate(Routes.RoleSelection.route) {
//                       popUpTo(0) { inclusive = true }
//                   }
//
// Dialogs (shown inline — no separate routes needed):
//   PasswordChangedDialog  — shown after tapping "Change Password"
//   DoctorLogoutConfirmationDialog — shown after tapping "Logout from Account"
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSecuritySettingsScreen(navController: NavController) {

    // ── Local state ───────────────────────────────────────────────────────────
    var currentPassword       by remember { mutableStateOf("") }
    var newPassword           by remember { mutableStateOf("") }
    var confirmPassword       by remember { mutableStateOf("") }

    var showCurrentPassword   by remember { mutableStateOf(false) }
    var showNewPassword       by remember { mutableStateOf(false) }
    var showConfirmPassword   by remember { mutableStateOf(false) }

    var showPasswordChanged   by remember { mutableStateOf(false) }
    var showLogoutDialog      by remember { mutableStateOf(false) }

    // ── Screen scaffold ───────────────────────────────────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        // ── Back navigation header ────────────────────────────────────────────
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .clickable { navController.popBackStack() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint               = Primary,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text      = "Back",
                color     = Primary,
                fontSize  = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // ── Page title & subtitle ─────────────────────────────────────────────
        Text(
            text       = "Security Settings",
            fontSize   = 24.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            modifier   = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text     = "Manage your account security",
            fontSize = 14.sp,
            color    = TextSecondary,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        HorizontalDivider(
            modifier  = Modifier.padding(top = 16.dp),
            color     = BorderLight,
            thickness = 1.dp
        )

        // ── Scrollable content ────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // ── Password Card ─────────────────────────────────────────────────
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        text       = "Password",
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Current Password
                    PasswordFieldLabel("Current Password")
                    Spacer(modifier = Modifier.height(6.dp))
                    PasswordInputField(
                        value           = currentPassword,
                        onValueChange   = { currentPassword = it },
                        placeholder     = "Enter current password",
                        isVisible       = showCurrentPassword,
                        onToggleVisible = { showCurrentPassword = !showCurrentPassword }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // New Password
                    PasswordFieldLabel("New Password")
                    Spacer(modifier = Modifier.height(6.dp))
                    PasswordInputField(
                        value           = newPassword,
                        onValueChange   = { newPassword = it },
                        placeholder     = "Enter new password",
                        isVisible       = showNewPassword,
                        onToggleVisible = { showNewPassword = !showNewPassword }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm New Password
                    PasswordFieldLabel("Confirm New Password")
                    Spacer(modifier = Modifier.height(6.dp))
                    PasswordInputField(
                        value           = confirmPassword,
                        onValueChange   = { confirmPassword = it },
                        placeholder     = "Confirm new password",
                        isVisible       = showConfirmPassword,
                        onToggleVisible = { showConfirmPassword = !showConfirmPassword }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Change Password Button
                    Button(
                        onClick  = { showPasswordChanged = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape  = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text(
                            text       = "Change Password",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Danger Zone Card ──────────────────────────────────────────────
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = ErrorContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        text       = "Danger Zone",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Error
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text     = "This action cannot be undone",
                        fontSize = 13.sp,
                        color    = Error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logout Button
                    Button(
                        onClick  = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape  = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Error)
                    ) {
                        Icon(
                            imageVector        = Icons.Default.ExitToApp,
                            contentDescription = null,
                            modifier           = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text       = "Logout from Account",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // ── Dialogs ───────────────────────────────────────────────────────────────
    if (showPasswordChanged) {
        DoctorPasswordChangedDialog(
            onDismiss = {
                showPasswordChanged = false
                navController.popBackStack()
            }
        )
    }

    if (showLogoutDialog) {
        DoctorLogoutConfirmationDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                // Clear the entire back stack and go back to role selection
                navController.navigate("role_selection") {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PasswordFieldLabel(label: String) {
    Text(
        text       = label,
        fontSize   = 13.sp,
        color      = TextSecondary,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun PasswordInputField(
    value          : String,
    onValueChange  : (String) -> Unit,
    placeholder    : String,
    isVisible      : Boolean,
    onToggleVisible: () -> Unit,
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        placeholder   = { Text(placeholder, color = TextTertiary) },
        modifier      = Modifier.fillMaxWidth(),
        shape         = RoundedCornerShape(12.dp),
        singleLine    = true,
        visualTransformation = if (isVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector        = Icons.Default.Lock,
                contentDescription = null,
                tint               = TextTertiary
            )
        },
        trailingIcon = {
            IconButton(onClick = onToggleVisible) {
                Icon(
                    imageVector        = if (isVisible) Icons.Default.VisibilityOff
                    else Icons.Default.Visibility,
                    contentDescription = if (isVisible) "Hide password" else "Show password",
                    tint               = TextTertiary
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor   = BorderLight,
            focusedBorderColor     = BorderFocus,
            unfocusedContainerColor = Surface,
            focusedContainerColor   = Surface
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Password Changed Dialog
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun DoctorPasswordChangedDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier            = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier        = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(SuccessContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Default.Check,
                        contentDescription = null,
                        tint               = Success,
                        modifier           = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text       = "Password Changed!",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text      = "Your password has been updated successfully.",
                    fontSize  = 14.sp,
                    color     = TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick  = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(
                        text       = "Done",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun DoctorSecuritySettingsPreview() {
    DoctorSecuritySettingsScreen(navController = rememberNavController())
}
