package com.example.healthcareplus.ui.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DoctorLoginScreen(
    onLoginSuccess   : () -> Unit = {},
    onForgotPassword : () -> Unit = {},
) {
    var medicalId       by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe      by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {

        // ── Header ───────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF3D5AF1),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                )
                .padding(top = 56.dp, bottom = 36.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.HealthAndSafety,
                        contentDescription = null,
                        tint               = Color(0xFF3D5AF1),
                        modifier           = Modifier.size(40.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text       = "Doctor Portal",
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text     = "Sign in to manage your patients",
                    fontSize = 14.sp,
                    color    = Color(0xCCFFFFFF),
                )
            }
        }

        // ── Form card ────────────────────────────────────────────────────────
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = (-20).dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // Medical ID / Email
                AuthLabel("Medical ID / Email")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value         = medicalId,
                    onValueChange = { medicalId = it },
                    placeholder   = "Enter your medical ID or email",
                    leadingIcon   = Icons.Outlined.Email,
                    keyboardType  = KeyboardType.Email,
                )

                Spacer(Modifier.height(16.dp))

                // Password
                AuthLabel("Password")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value            = password,
                    onValueChange    = { password = it },
                    placeholder      = "Enter your password",
                    leadingIcon      = Icons.Outlined.Lock,
                    keyboardType     = KeyboardType.Password,
                    isPassword       = true,
                    passwordVisible  = passwordVisible,
                    onTogglePassword = { passwordVisible = !passwordVisible },
                )

                Spacer(Modifier.height(12.dp))

                // Remember me + Forgot password row
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked         = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors          = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF3D5AF1),
                            ),
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text     = "Remember me",
                            fontSize = 13.sp,
                            color    = Color(0xFF6B7280),
                        )
                    }
                    Text(
                        text       = "Forgot Password?",
                        fontSize   = 13.sp,
                        color      = Color(0xFF3D5AF1),
                        fontWeight = FontWeight.Medium,
                        modifier   = Modifier.clickable { onForgotPassword() },
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Sign In button
                Button(
                    onClick  = { onLoginSuccess() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D5AF1),
                    ),
                ) {
                    Text(
                        text       = "Sign In",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Color.White,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorLoginPreview() {
    DoctorLoginScreen()
}