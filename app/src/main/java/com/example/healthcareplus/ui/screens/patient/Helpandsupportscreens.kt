package com.example.healthcareplus.ui.screens.patient


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// ─────────────────────────────────────────────────────────────────────────────
// Help & FAQ Screen
// ─────────────────────────────────────────────────────────────────────────────

data class FaqItem(val question: String, val answer: String, val category: String)

@Composable
fun HelpFaqScreen(navController: NavController) {
    val primaryBlue = Color(0xFF3B4EFF)

    val faqs = listOf(
        FaqItem("How do I book an appointment?", "Go to the home screen, tap \"Book Appointment\", select your preferred specialty and doctor, choose an available date and time slot, then confirm your booking.", "GENERAL"),
        FaqItem("How do I view my lab reports?", "Tap the \"Reports\" icon in the bottom navigation. All your lab reports will be listed there with their status (Ready or Pending).", "GENERAL"),
        FaqItem("Can I cancel an appointment?", "Yes, go to \"My Appointments\", find the appointment you want to cancel, and tap the Cancel button. A confirmation dialog will appear.", "GENERAL"),
        FaqItem("How do I change my password?", "Go to Profile → Privacy & Security → Security Settings → Change Password.", "ACCOUNT & SECURITY"),
        FaqItem("Is my health data secure?", "Yes, all your health data is encrypted and stored securely following HIPAA guidelines. We never share your data without consent.", "ACCOUNT & SECURITY")
    )

    val grouped = faqs.groupBy { it.category }
    val expandedItems = remember { mutableStateMapOf<String, Boolean>() }

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
                Icons.Default.ArrowBack, contentDescription = "Back", tint = primaryBlue,
                modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("< Back", color = primaryBlue, fontSize = 16.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text("Help & FAQ", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Text("Find answers to common questions", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp, bottom = 20.dp))

            // Contact Us Button
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clickable { navController.navigate("contact_support") },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFE8EEFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Message, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Contact Us", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Frequently Asked Questions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(12.dp))

            grouped.forEach { (category, items) ->
                Text(
                    text = category,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    items.forEachIndexed { index, faq ->
                        val isExpanded = expandedItems[faq.question] ?: false
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedItems[faq.question] = !isExpanded }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    faq.question,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1A1A2E),
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                            AnimatedVisibility(visible = isExpanded, enter = expandVertically(), exit = shrinkVertically()) {
                                Text(
                                    faq.answer,
                                    fontSize = 13.sp,
                                    color = Color(0xFF555555),
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                )
                            }
                            if (index < items.size - 1) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFEEEEEE))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Contact Support Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ContactSupportScreen(navController: NavController) {
    val primaryBlue = Color(0xFF3B4EFF)

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
                Icons.Default.ArrowBack, contentDescription = "Back", tint = primaryBlue,
                modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("< Back", color = primaryBlue, fontSize = 16.sp)
        }

        Text(
            "Contact Support",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Call Us
            Card(
                modifier = Modifier.fillMaxWidth().clickable { },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFE8F5E9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF28A745), modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Call Us", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                            Text("+1 (555) 123-4567", fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
            }

            // Email Us
            Card(
                modifier = Modifier.fillMaxWidth().clickable { },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFE8F5E9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFF28A745), modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Email Us", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                            Text("support@healthcare.com", fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
            }

            // Support Hours
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("Support Hours", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Monday - Friday: 8:00 AM - 8:00 PM", fontSize = 13.sp, color = Color(0xFF555555))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Saturday: 9:00 AM - 5:00 PM", fontSize = 13.sp, color = Color(0xFF555555))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Sunday: Closed", fontSize = 13.sp, color = Color(0xFF555555))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Verify Email Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(navController: NavController, email: String = "john.doe@email.com") {
    val primaryBlue = Color(0xFF3B4EFF)
    val otpLength = 6
    val otpValues = remember { mutableStateListOf(*Array(otpLength) { "" }) }
    var timeLeft by remember { mutableStateOf(59) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            kotlinx.coroutines.delay(1000)
            timeLeft--
        }
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
                Icons.Default.ArrowBack, contentDescription = "Back", tint = primaryBlue,
                modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.size(80.dp).clip(CircleShape).background(Color(0xFFE8EEFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Email, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(40.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Verify Your Email", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(8.dp))
            Text("We sent a verification code to", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Text(email, fontSize = 14.sp, color = primaryBlue, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(32.dp))

            Text("Enter the 6-digit code", fontSize = 14.sp, color = Color(0xFF555555))
            Spacer(modifier = Modifier.height(16.dp))

            // OTP Input Boxes
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(otpLength) { index ->
                    OutlinedTextField(
                        value = otpValues[index],
                        onValueChange = { value ->
                            if (value.length <= 1 && value.all { it.isDigit() }) {
                                otpValues[index] = value
                            }
                        },
                        modifier = Modifier.size(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

            Spacer(modifier = Modifier.height(24.dp))

            if (timeLeft > 0) {
                Text("Didn't receive the code?", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Resend Code", fontSize = 13.sp, color = Color(0xFFBBBBBB))
                Text("Resend in ${timeLeft}s", fontSize = 12.sp, color = Color.Gray)
            } else {
                Text("Didn't receive the code?", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Resend Code",
                    fontSize = 13.sp,
                    color = primaryBlue,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { timeLeft = 59 }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("patient_home") },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                Text("Verify Email", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Wrong email? Change it",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }
    }
}