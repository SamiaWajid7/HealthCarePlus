package com.example.healthcareplus.ui.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors ───────────────────────────────────────────────────────────────────
private val PrimaryBlue     = Color(0xFF3D5AF1)
private val PrimaryBlueDark = Color(0xFF2D4AE0)
private val White           = Color(0xFFFFFFFF)
private val WhiteDim        = Color(0xCCFFFFFF)
private val TextPrimary     = Color(0xFF1A1A2E)
private val TextSecondary   = Color(0xFF6B7280)
private val CardBorder      = Color(0xFFE5E7EB)
private val PatientIconBg   = Color(0xFFEEF2FF)   // light blue
private val DoctorIconBg    = Color(0xFFE6FBF4)   // light teal
private val DoctorIconTint  = Color(0xFF10B981)   // teal

enum class UserRole { PATIENT, DOCTOR }

@Composable
fun RoleSelectionScreen(
    onRoleSelected: (UserRole) -> Unit = {},
) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ) {

        // ── Header banner ────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PrimaryBlue,
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                )
                .padding(top = 56.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Shield icon
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.HealthAndSafety,
                        contentDescription = null,
                        tint               = White,
                        modifier           = Modifier.size(40.dp),
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text       = "Welcome to HealthCare+",
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color      = White,
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text     = "Who are you?",
                    fontSize = 14.sp,
                    color    = WhiteDim,
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // ── Subtitle ─────────────────────────────────────────────────────────
        Text(
            text      = "Select your role to continue",
            fontSize  = 14.sp,
            color     = TextSecondary,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(24.dp))

        // ── Role cards ───────────────────────────────────────────────────────
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            RoleCard(
                icon        = Icons.Outlined.Person,
                iconBg      = PatientIconBg,
                iconTint    = PrimaryBlue,
                title       = "I'm a Patient",
                description = "Access your health records, book appointments, and connect with doctors",
                isSelected  = selectedRole == UserRole.PATIENT,
                onClick     = {
                    selectedRole = UserRole.PATIENT
                    onRoleSelected(UserRole.PATIENT)
                },
            )

            RoleCard(
                icon        = Icons.Outlined.HealthAndSafety,
                iconBg      = DoctorIconBg,
                iconTint    = DoctorIconTint,
                title       = "I'm a Doctor",
                description = "Manage your patients, appointments, and upload lab reports",
                isSelected  = selectedRole == UserRole.DOCTOR,
                onClick     = {
                    selectedRole = UserRole.DOCTOR
                    onRoleSelected(UserRole.DOCTOR)
                },
            )
        }
    }
}

// ── Reusable role card ────────────────────────────────────────────────────────
@Composable
private fun RoleCard(
    icon        : ImageVector,
    iconBg      : Color,
    iconTint    : Color,
    title       : String,
    description : String,
    isSelected  : Boolean,
    onClick     : () -> Unit,
) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) PrimaryBlue else CardBorder,
                shape = RoundedCornerShape(16.dp),
            ),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp,
        ),
    ) {
        Column(
            modifier            = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = iconTint,
                    modifier           = Modifier.size(36.dp),
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text       = title,
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text      = description,
                fontSize  = 13.sp,
                color     = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun RoleSelectionPreview() {
    RoleSelectionScreen()
}