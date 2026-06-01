package com.example.healthcareplus.ui.common.components.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow

import com.example.healthcareplus.theme.*

data class ClinicNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)

@Composable
fun ClinicBottomBar(
    items: List<ClinicNavItem>,
    currentRoute: String,
    onItemClick: (ClinicNavItem) -> Unit,
) {
    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            ),
    ) {

        items.forEach { item ->

            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,

                onClick = {
                    onItemClick(item)
                },

                icon = {
                    Icon(
                        imageVector =
                            if (selected) item.selectedIcon
                            else item.unselectedIcon,

                        contentDescription = item.label,

                        modifier = Modifier.size(22.dp),
                    )
                },

                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight =
                            if (selected) FontWeight.SemiBold
                            else FontWeight.Normal,
                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,

                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,

                    indicatorColor = PrimaryLight,
                ),
            )
        }
    }
}