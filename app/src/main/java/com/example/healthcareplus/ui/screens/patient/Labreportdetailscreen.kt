package com.example.healthcareplus.ui.screens.patient


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class TestResult(
    val name: String,
    val value: String,
    val reference: String,
    val status: String // "Normal", "High", "Low"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabReportDetailScreen(navController: NavController, reportId: String?) {
    val primaryBlue = Color(0xFF3B4EFF)
    val normalGreen = Color(0xFF4CAF50)

    val testResults = listOf(
        TestResult("Hemoglobin", "14.5 g/dL", "Ref: 13.5-17.5", "Normal"),
        TestResult("White Blood Cells", "7.2 × 10³/μL", "Ref: 4.5-11.0", "Normal"),
        TestResult("Platelets", "250 × 10³/μL", "Ref: 150-400", "Normal"),
        TestResult("Red Blood Cells", "4.8 × 10⁶/μL", "Ref: 4.5-5.5", "Normal")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = primaryBlue)
            }
            Text(
                text = "< Back",
                color = primaryBlue,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Text(
            text = "Complete Blood Count",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = "Test Date: February 10, 2024",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Results Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Test Results",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A2E)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Download",
                            tint = primaryBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Download PDF", color = primaryBlue, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                testResults.forEach { result ->
                    TestResultRow(result = result, normalGreen = normalGreen)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Doctor's Note Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    imageVector = Icons.Default.Download, // Replace with document icon
                    contentDescription = null,
                    tint = Color(0xFFFFA726),
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFFE0B2), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Doctor's Note",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A2E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "All values are within normal range. Continue with current lifestyle and medication. Schedule a follow-up in 3 months.",
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun TestResultRow(result: TestResult, normalGreen: Color) {
    val statusBg = when (result.status) {
        "Normal" -> Color(0xFFE8F5E9)
        "High" -> Color(0xFFFFEBEE)
        else -> Color(0xFFFFF8E1)
    }
    val statusColor = when (result.status) {
        "Normal" -> normalGreen
        "High" -> Color(0xFFF44336)
        else -> Color(0xFFFFA726)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = result.name, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = result.value, fontSize = 13.sp, color = Color(0xFF555555))
        }
        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .background(statusBg, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(text = result.status, fontSize = 12.sp, color = statusColor, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = result.reference, fontSize = 11.sp, color = Color.Gray)
        }
    }
}