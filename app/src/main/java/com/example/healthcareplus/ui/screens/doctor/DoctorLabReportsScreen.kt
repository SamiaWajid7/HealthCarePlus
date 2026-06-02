package com.example.healthcareplus.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3D5AF1)
private val PrimaryBlueBg = Color(0xFFEEF2FF)
private val White = Color.White
private val TextPrimary = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val BgLight = Color(0xFFF3F4F6)

private val BadgePending = Color(0xFFFFF3CD)
private val BadgePendingText = Color(0xFF856404)

private val BadgeSuccess = Color(0xFFE8F5E9)
private val BadgeSuccessText = Color(0xFF2E7D32)

data class LabReportPatient(
    val patientName: String,
    val patientId: String,
    val testName: String,
    val dateInfo: String,
    val status: String
)

@Composable
fun DoctorLabReportsScreen(
    onBack: () -> Unit = {},
    onUploadReport: (LabReportPatient) -> Unit = {},
    onViewReport: (LabReportPatient) -> Unit = {}
) {

    var searchText by remember { mutableStateOf("") }

    val reports = listOf(
        LabReportPatient(
            "John Doe",
            "HC-2024-0123",
            "Complete Blood Count (CBC)",
            "Requested: Feb 10, 2024",
            "Pending"
        ),
        LabReportPatient(
            "Jane Smith",
            "HC-2024-0456",
            "Lipid Panel",
            "Requested: Feb 12, 2024",
            "Pending"
        ),
        LabReportPatient(
            "Emily Wilson",
            "HC-2024-0678",
            "Thyroid Function Test",
            "Uploaded: Feb 13, 2024",
            "Uploaded"
        )
    )

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
                modifier = Modifier.clickable { onBack() }
            )
        }

        Text(
            text = "Upload Lab Reports",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            leadingIcon = {
                Icon(Icons.Outlined.Search, null)
            },
            placeholder = {
                Text("Search patients...")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BadgePending)
                .padding(14.dp)
        ) {
            Text(
                text = "3 patients need lab reports uploaded",
                color = BadgePendingText,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {

            items(reports) { report ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {

                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryBlueBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Outlined.Person,
                                        null,
                                        tint = PrimaryBlue
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {

                                    Text(
                                        report.patientName,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )

                                    Text(
                                        report.patientId,
                                        color = TextSecondary,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            val pending = report.status == "Pending"

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        if (pending) BadgePending
                                        else BadgeSuccess
                                    )
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 4.dp
                                    )
                            ) {

                                Text(
                                    report.status,
                                    fontSize = 11.sp,
                                    color = if (pending)
                                        BadgePendingText
                                    else
                                        BadgeSuccessText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Test: ${report.testName}",
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = report.dateInfo,
                            color = TextSecondary,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        if (report.status == "Pending") {

                            Button(
                                onClick = {
                                    onUploadReport(report)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryBlue
                                )
                            ) {
                                Text("Upload Report")
                            }

                        } else {

                            OutlinedButton(
                                onClick = {
                                    onViewReport(report)
                                }
                            ) {
                                Text("View Report")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DoctorLabReportsPreview() {
    DoctorLabReportsScreen()
}