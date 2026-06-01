package com.example.healthcareplus.ui.common.components.buttons


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.common.utils.ClinicRadius
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*

enum class ClinicButtonVariant {
    PRIMARY,
    OUTLINE,
    TEXT,
    DANGER
}

@Composable
fun ClinicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ClinicButtonVariant = ClinicButtonVariant.PRIMARY,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {

    val bgColor = when (variant) {
        ClinicButtonVariant.PRIMARY ->
            if (enabled) Primary else Primary.copy(alpha = .4f)

        ClinicButtonVariant.OUTLINE ->
            Color.Transparent

        ClinicButtonVariant.TEXT ->
            Color.Transparent

        ClinicButtonVariant.DANGER ->
            if (enabled) Error else Error.copy(alpha = .4f)
    }

    val contentColor = when (variant) {
        ClinicButtonVariant.PRIMARY,
        ClinicButtonVariant.DANGER -> White

        ClinicButtonVariant.OUTLINE,
        ClinicButtonVariant.TEXT -> Primary
    }

    val border = if (variant == ClinicButtonVariant.OUTLINE)
        BorderStroke(1.5.dp, Primary)
    else null

    Button(
        onClick = { if (!isLoading) onClick() },
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(ClinicRadius.Full),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = contentColor,
            disabledContainerColor = bgColor,
            disabledContentColor = contentColor,
        ),
        border = border,
    ) {

        if (isLoading) {

            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = contentColor,
                strokeWidth = 2.dp,
            )

        } else {

            if (leadingIcon != null) {

                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )

                Spacer(Modifier.width(8.dp))
            }

            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FD, widthDp = 360)
@Composable
private fun PreviewButtons() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ClinicButton("Sign In", {})
        ClinicButton("View My Appointments", {}, variant = ClinicButtonVariant.OUTLINE)
        ClinicButton("Forgot Password?", {}, variant = ClinicButtonVariant.TEXT)
        ClinicButton("Yes, Cancel", {}, variant = ClinicButtonVariant.DANGER)
        ClinicButton("Loading…", {}, isLoading = true)
    }
}