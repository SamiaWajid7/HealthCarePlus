package com.example.healthcareplus.ui.screens.doctor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.healthcareplus.theme.Primary
import com.example.healthcareplus.theme.Success
import com.example.healthcareplus.theme.SuccessContainer
import com.example.healthcareplus.theme.TextPrimary
import com.example.healthcareplus.theme.TextSecondary
import com.example.healthcareplus.theme.White

// ─────────────────────────────────────────────────────────────────────────────
// ChangesSavedDialog
//
// Navigation context:
//   Triggered from: EditProfileScreen  (route = "edit_profile")
//   Shown when:     User taps "Save Changes" button
//   onDismiss:      Closes the dialog and calls navController.popBackStack()
//                   → returns to ProfileScreen (route = "profile")
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ChangesSavedDialog(
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                // ── Success icon circle ───────────────────────────────────────
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(SuccessContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Default.Check,
                        contentDescription = null,
                        tint               = Success,
                        modifier           = Modifier.size(44.dp),
                    )
                }

                Spacer(Modifier.height(24.dp))

                // ── Title ─────────────────────────────────────────────────────
                Text(
                    text       = "Changes Saved!",
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                )

                Spacer(Modifier.height(10.dp))

                // ── Subtitle ──────────────────────────────────────────────────
                Text(
                    text      = "Your profile has been updated successfully.",
                    fontSize  = 14.sp,
                    color     = TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                )

                Spacer(Modifier.height(32.dp))

                // ── Done button ───────────────────────────────────────────────
                Button(
                    onClick  = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape  = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) {
                    Text(
                        text       = "Done",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color      = White,
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ChangesSavedDialogPreview() {
    ChangesSavedDialog(onDismiss = {})
}