package com.example.healthcareplus.ui.screens.doctor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthcareplus.theme.BorderLight
import com.example.healthcareplus.theme.Primary
import com.example.healthcareplus.theme.TextPrimary
import com.example.healthcareplus.theme.TextSecondary
import com.example.healthcareplus.theme.White
import com.example.healthcareplus.ui.navigation.Routes

// ─────────────────────────────────────────────────────────────────────────────
// ProfileContentContainer
//
// Navigation context:
//   Used inside:     ProfileScreen  (route = "profile")
//   Edit Profile:    navigates to Routes.EditProfile  ("edit_profile")
//   Privacy & Security: navigates to Routes.SecuritySettings ("security_settings")
//
// Data shown:
//   Personal information card — phone, dob, blood group, medical ID
//   Edit Profile button (blue, full-width)
//   Privacy & Security list row (with lock icon + chevron)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ProfileContentContainer(
    navController : NavController,
    // Data parameters — replace with real ViewModel state as needed
    phoneNumber   : String = "+1 (555) 123-4567",
    dateOfBirth   : String = "January 15, 1990",
    bloodGroup    : String = "O+",
    medicalId     : String = "HC-2024-0123",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {

        // ── Personal Information card ─────────────────────────────────────
        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(16.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // Card title
                Text(
                    text       = "Personal Information",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                )

                Spacer(Modifier.height(16.dp))

                // ── Info rows ─────────────────────────────────────────────
                PersonalInfoRow(label = "Phone Number", value = phoneNumber)

                HorizontalDivider(
                    modifier  = Modifier.padding(vertical = 14.dp),
                    color     = BorderLight,
                    thickness = 1.dp,
                )

                PersonalInfoRow(label = "Date of Birth", value = dateOfBirth)

                HorizontalDivider(
                    modifier  = Modifier.padding(vertical = 14.dp),
                    color     = BorderLight,
                    thickness = 1.dp,
                )

                PersonalInfoRow(label = "Blood Group", value = bloodGroup)

                HorizontalDivider(
                    modifier  = Modifier.padding(vertical = 14.dp),
                    color     = BorderLight,
                    thickness = 1.dp,
                )

                PersonalInfoRow(label = "Medical ID", value = medicalId)

                Spacer(Modifier.height(20.dp))

                // ── Edit Profile button ───────────────────────────────────
                Button(
                    onClick  = { navController.navigate(Routes.EditProfile.route) },
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

        // ── Privacy & Security row ────────────────────────────────────────
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Routes.SecuritySettings.route) },
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
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helper: label + value stacked row
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

@Preview(showBackground = true, widthDp = 360, heightDp = 600)
@Composable
private fun ProfileContentContainerPreview() {
    ProfileContentContainer(navController = rememberNavController())
}