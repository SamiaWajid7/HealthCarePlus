package com.example.healthcareplus.ui.common.components.textfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcareplus.theme.*
import com.example.healthcareplus.ui.common.utils.ClinicRadius
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.filled.VerifiedUser
@Composable
fun ClinicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    trailingContent: @Composable (() -> Unit)? = null,
) {

    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None

    Column(modifier = modifier) {

        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,

            placeholder = {
                Text(
                    text = placeholder,
                    color = TextSecondary,
                    fontSize = 14.sp,
                )
            },

            leadingIcon = leadingIcon?.let { icon ->
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = TextSecondary,
                    )
                }
            },

            trailingIcon = when {

                isPassword -> {
                    {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible)
                                        Icons.Outlined.Visibility
                                    else
                                        Icons.Outlined.VisibilityOff,

                                contentDescription = null,
                                tint = TextSecondary,
                            )
                        }
                    }
                }

                trailingContent != null -> trailingContent

                else -> null
            },

            shape = RoundedCornerShape(ClinicRadius.Medium),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = BorderLight,
                errorBorderColor = Error,

                focusedContainerColor = White,
                unfocusedContainerColor = White,
                errorContainerColor = ErrorContainer,

                cursorColor = Primary,

                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
            ),
        )

        AnimatedVisibility(
            visible = isError && errorMessage.isNotBlank()
        ) {

            Text(
                text = errorMessage,
                color = Error,
                fontSize = 11.sp,
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 4.dp
                ),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FD, widthDp = 360)
@Composable
private fun PreviewTextField() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        var email by remember { mutableStateOf("") }
        var pass  by remember { mutableStateOf("") }
        ClinicTextField(email, { email = it }, "Email", placeholder = "Enter your email",
            leadingIcon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        ClinicTextField(pass, { pass = it }, "Password", placeholder = "Create a password",
            leadingIcon = Icons.Outlined.Lock, isPassword = true)
        ClinicTextField("", {}, "Phone", placeholder = "Enter phone number",
            leadingIcon = Icons.Outlined.Phone, isError = true, errorMessage = "Invalid phone number")
    }
}