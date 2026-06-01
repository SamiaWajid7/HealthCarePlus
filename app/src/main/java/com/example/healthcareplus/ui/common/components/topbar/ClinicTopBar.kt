package com.example.healthcareplus.ui.common.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcareplus.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.filled.VerifiedUser
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicTopBar(
    title: String = "",
    subtitle: String = "",
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    isHero: Boolean = false,
    heroIcon: @Composable (() -> Unit)? = null,
) {

    if (isHero) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Primary,
                            PrimaryDark
                        )
                    )
                )
                .padding(
                    horizontal = 24.dp,
                    vertical = 32.dp
                ),

            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (heroIcon != null) {

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                White.copy(alpha = 0.15f)
                            ),

                        contentAlignment = Alignment.Center
                    ) {
                        heroIcon()
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (title.isNotBlank()) {

                    Text(
                        text = title,
                        color = White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.2.sp,
                    )
                }

                if (subtitle.isNotBlank()) {

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = subtitle,
                        color = White.copy(alpha = 0.75f),
                        fontSize = 14.sp,
                    )
                }
            }
        }

    } else {

        TopAppBar(

            title = {

                if (title.isNotBlank()) {

                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },

            navigationIcon = {

                if (onBackClick != null) {

                    TextButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Primary,
                            modifier = Modifier.size(20.dp),
                        )

                        Text(
                            text = "Back",
                            color = Primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            },

            actions = actions,

            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White,
            )
        )

        HorizontalDivider(
            color = BorderLight,
            thickness = 0.5.dp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FD, widthDp = 360)
@Composable
private fun PreviewTopBars() {
    Column {
        ClinicTopBar(title = "Lab Reports", onBackClick = {})
        Spacer(Modifier.height(8.dp))
        ClinicTopBar(
            title     = "Welcome to HealthCare+",
            subtitle  = "Who are you?",
            isHero    = true,
            heroIcon  = {
                Icon(Icons.Default.VerifiedUser, null,
                    tint = Color.White, modifier = Modifier.size(32.dp))
            },
        )
    }
}