package com.example.healthcareplus.ui.common.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.common.utils.ClinicRadius
enum class DoctorAvailability { AVAILABLE, BUSY, OFFLINE }

@Composable
fun DoctorCard(
    name: String,
    specialty: String,
    yearsExperience: Int,
    rating: Float,
    reviewCount: Int,
    nextSlot: String,
    availability: DoctorAvailability = DoctorAvailability.AVAILABLE,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier,
    onMessageClick: (() -> Unit)? = null,
) {
    val availColor = when (availability) {
        DoctorAvailability.AVAILABLE -> Success
        DoctorAvailability.BUSY      -> Warning
        DoctorAvailability.OFFLINE   -> TextSecondary
    }
    val availText = when (availability) {
        DoctorAvailability.AVAILABLE -> "Available"
        DoctorAvailability.BUSY      -> "Busy"
        DoctorAvailability.OFFLINE   -> "Offline"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(ClinicRadius.Large),
        colors   = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header row
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar placeholder
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(PrimaryLight),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint     = Primary,
                        modifier = Modifier.size(28.dp),
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text       = name,
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Text(
                        text     = specialty,
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                    Text(
                        text     = "$yearsExperience years experience",
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                }

                // Availability badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(ClinicRadius.Full))
                        .background(availColor.copy(alpha = .12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text       = availText,
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = availColor,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Rating + next slot
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint     = Color(0xFFFFC107),
                    modifier = Modifier.size(14.dp),
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text     = "$rating ($reviewCount)",
                    fontSize = 12.sp,
                    color    = TextSecondary,
                )

                if (availability == DoctorAvailability.AVAILABLE) {
                    Spacer(Modifier.width(12.dp))
                    Icon(
                        Icons.Outlined.AccessTime,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text     = nextSlot,
                        fontSize = 12.sp,
                        color    = TextSecondary,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Action row
            if (availability != DoctorAvailability.BUSY) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onBookClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        shape  = RoundedCornerShape(ClinicRadius.Full),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    ) {
                        Text("Book Appointment", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    if (onMessageClick != null) {
                        OutlinedIconButton(
                            onClick = onMessageClick,
                            modifier = Modifier.size(40.dp),
                            shape  = RoundedCornerShape(ClinicRadius.Small),
                            border = BorderStroke(1.dp, BorderLight),
                        ) {
                            Icon(
                                Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "Message",
                                tint = Primary,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360)
@Composable
private fun PreviewDoctorCard() {
    DoctorCard(
        name             = "Dr. Sarah Johnson",
        specialty        = "Cardiologist",
        yearsExperience  = 15,
        rating           = 4.9f,
        reviewCount      = 234,
        nextSlot         = "Today, 2:00 PM",
        availability     = DoctorAvailability.AVAILABLE,
        onBookClick      = {},
        onMessageClick   = {},
        modifier         = Modifier.padding(16.dp),
    )
}
