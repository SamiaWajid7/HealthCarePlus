package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Colors ────────────────────────────────────────────────────────────────────
private val PrimaryBlue = Color(0xFF3D5AF1)
private val White = Color(0xFFFFFFFF)
private val BgLight = Color(0xFFF3F4F6)
private val TextPrimary = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)

@Composable
fun LabReportRequestsScreen(

    onBack: () -> Unit = {},

    onUploadReport: () -> Unit = {},

    // ✅ ADDED (required for View Report navigation)
    onViewReport: () -> Unit = {}
) {

    var testType by remember {
        mutableStateOf("Complete Blood Count (CBC)")
    }

    var testDate by remember {
        mutableStateOf("")
    }

    var doctorsNotes by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight)
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "< Back",
                color = PrimaryBlue,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onBack() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Text(
                text = "Upload Lab Report",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "John Doe",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Text(
                text = "HC-2024-0123",
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Test Type", fontWeight = FontWeight.Medium, color = TextPrimary)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = testType,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text("Test Date", fontWeight = FontWeight.Medium, color = TextPrimary)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = testDate,
                onValueChange = { testDate = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("mm/dd/yyyy") },
                trailingIcon = {
                    Icon(Icons.Outlined.DateRange, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text("Upload Report (PDF)", fontWeight = FontWeight.Medium, color = TextPrimary)

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(12.dp))
                    .clickable {
                        // optional future file picker
                        onViewReport()
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Icon(
                        imageVector = Icons.Outlined.CloudUpload,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text("Click to upload or drag and drop")

                    Spacer(Modifier.height(4.dp))

                    Text("PDF only, max 10MB", color = TextSecondary, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Doctor's Notes (Optional)", fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = doctorsNotes,
                onValueChange = { doctorsNotes = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("Add any observations or recommendations...") }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { onUploadReport() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Upload Report", fontSize = 15.sp)
            }
        }
    }
}