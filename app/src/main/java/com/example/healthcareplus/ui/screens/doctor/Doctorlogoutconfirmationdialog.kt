package com.example.healthcareplus.ui.screens.doctor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.healthcareplus.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// DoctorLogoutConfirmationDialog
//
// Navigation context:
//   Shown by:    DoctorSecuritySettingsScreen
//                when user taps "Logout from Account" in the Danger Zone card.
//   NOT a separate route — displayed inline inside DoctorSecuritySettingsScreen.
//
// Callbacks:
//   onDismiss → sets showLogoutDialog = false  (stays on Security Settings)
//   onConfirm → navController.navigate("role_selection") {
//                   popUpTo(0) { inclusive = true }
//               }   (clears full back stack, sends doctor to Role Selection)
//
// Design spec (from Logout_Confirmation_Modal.pdf):
//   • White card with rounded corners and a soft drop shadow
//   • Yellow-tinted circle with a golden logout/exit icon
//   • Bold "Logout" heading
//   • Muted subtitle: "Are you sure you want to logout from your account?"
//   • Two side-by-side buttons:
//       – Cancel  → outlined, dark text, rounded
//       – Logout  → filled red (#D32F2F), white text, rounded
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun DoctorLogoutConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape     = RoundedCornerShape(24.dp),
            colors    = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ── Yellow icon circle ────────────────────────────────────────
                Box(
                    modifier        = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF9C4)),     // soft yellow tint
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint               = Color(0xFFF9A825),  // golden amber
                        modifier           = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── Title ─────────────────────────────────────────────────────
                Text(
                    text       = "Logout",
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ── Subtitle ──────────────────────────────────────────────────
                Text(
                    text       = "Are you sure you want to logout from your account?",
                    fontSize   = 14.sp,
                    color      = TextSecondary,
                    textAlign  = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ── Action buttons ────────────────────────────────────────────
                Row(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Cancel — outlined
                    OutlinedButton(
                        onClick  = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape  = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.5.dp, BorderLight
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextPrimary
                        )
                    ) {
                        Text(
                            text       = "Cancel",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary
                        )
                    }

                    // Logout — filled red
                    Button(
                        onClick  = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape  = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Error)
                    ) {
                        Text(
                            text       = "Logout",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color      = White
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview — wraps in a semi-transparent scrim so it looks like a real dialog
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 390, heightDp = 520)
@Composable
private fun DoctorLogoutConfirmationDialogPreview() {
    Box(
        modifier        = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        DoctorLogoutConfirmationDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}

