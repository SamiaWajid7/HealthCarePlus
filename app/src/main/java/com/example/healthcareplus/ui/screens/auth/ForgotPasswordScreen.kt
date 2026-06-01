package com.example.healthcareplus.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
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
fun ForgotPasswordScreen(
    onResetSent  : () -> Unit = {},
    onBackToLogin: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint               = Color.White,
                        modifier           = Modifier.size(34.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text       = "Forgot Password?",
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text     = "Don't worry, we'll help you reset it",
                    fontSize = 13.sp,
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

                Text(
                    text      = "Enter your email address and we'll send you instructions to reset your password",
                    fontSize  = 13.sp,
                    color     = Color(0xFF6B7280),
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.fillMaxWidth(),
                    lineHeight = 20.sp,
                )

                Spacer(Modifier.height(20.dp))

                AuthLabel("Email")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value         = email,
                    onValueChange = { email = it },
                    placeholder   = "Enter your email",
                    leadingIcon   = Icons.Outlined.Email,
                    keyboardType  = KeyboardType.Email,
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick  = { onResetSent() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D5AF1),
                    ),
                ) {
                    Text(
                        text       = "Send Reset Link",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Color.White,
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text      = "Back to Login",
                    fontSize  = 13.sp,
                    color     = Color(0xFF3D5AF1),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier  = Modifier
                        .fillMaxWidth()
                        .clickable { onBackToLogin() },
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ForgotPasswordPreview() {
    ForgotPasswordScreen()
}