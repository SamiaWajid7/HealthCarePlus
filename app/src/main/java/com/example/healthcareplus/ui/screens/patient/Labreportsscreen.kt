package com.example.healthcareplus.ui.screens.patient


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class LabReport(
    val id: String,
    val name: String,
    val category: String,
    val status: String, // "Ready" or "Pending"
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabReportsScreen(navController: NavController) {
    val primaryBlue = Color(0xFF3B4EFF)
    val readyGreen = Color(0xFF4CAF50)
    val pendingYellow = Color(0xFFFFA726)

    var searchQuery by remember { mutableStateOf("") }

    val reports = remember {
        listOf(
            LabReport("1", "Complete Blood Count", "Hematology", "Ready", "2024-02-10"),
            LabReport("2", "Lipid Panel", "Chemistry", "Ready", "2024-02-08"),
            LabReport("3", "Thyroid Function Test", "Endocrinology", "Pending", "2024-02-05"),
            LabReport("4", "Thyroid Function Test", "Endocrinology", "Pending", "2024-02-05"),
            LabReport("5", "Thyroid Function Test", "Endocrinology", "Pending", "2024-02-05"),
            LabReport("6", "Thyroid Function Test", "Endocrinology", "Pending", "2024-02-05"),
            LabReport("7", "Thyroid Function Test", "Endocrinology", "Pending", "2024-02-05")
        )
    }

    val filteredReports = reports.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        // Header
        Text(
            text = "Lab Reports",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 16.dp)
        )

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search reports...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = primaryBlue,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(filteredReports) { report ->
                LabReportItem(
                    report = report,
                    readyGreen = readyGreen,
                    pendingYellow = pendingYellow,
                    onClick = { navController.navigate("lab_report_detail/${report.id}") }
                )
                HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun LabReportItem(
    report: LabReport,
    readyGreen: Color,
    pendingYellow: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = report.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A2E)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = report.category,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            val statusColor = if (report.status == "Ready") readyGreen else pendingYellow
            val statusBg = if (report.status == "Ready") Color(0xFFE8F5E9) else Color(0xFFFFF8E1)
            Box(
                modifier = Modifier
                    .background(statusBg, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = report.status, fontSize = 12.sp, color = statusColor, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = report.date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}