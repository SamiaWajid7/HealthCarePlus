package com.example.healthcareplus.ui.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthcareplus.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class PatientLoginUiState {
    data object Loading : PatientLoginUiState()
    data object Success : PatientLoginUiState()
    data class Error(val message: String) : PatientLoginUiState()
}

class PatientLoginViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<PatientLoginUiState?>(null)
    val uiState: StateFlow<PatientLoginUiState?> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = PatientLoginUiState.Loading
            val result = authRepository.loginPatient(email, password)
            _uiState.value = result.fold(
                onSuccess = { PatientLoginUiState.Success },
                onFailure = { PatientLoginUiState.Error(it.message ?: "Login failed") },
            )
        }
    }
}

private val PrimaryBlue   = Color(0xFF3D5AF1)
private val White         = Color(0xFFFFFFFF)
private val WhiteDim      = Color(0xCCFFFFFF)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val InputBorder   = Color(0xFFE5E7EB)
private val InputBg       = Color(0xFFF9FAFB)

@Composable
fun PatientLoginScreen(
    onLoginSuccess    : () -> Unit = {},
    onSignUpClick     : () -> Unit = {},
    onForgotPassword  : () -> Unit = {},
    viewModel         : PatientLoginViewModel = viewModel(),
) {
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState is PatientLoginUiState.Loading

    LaunchedEffect(uiState) {
        if (uiState is PatientLoginUiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState()),
    ) {

        // ── Header ───────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PrimaryBlue,
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                )
                .padding(top = 56.dp, bottom = 36.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.MonitorHeart,
                        contentDescription = null,
                        tint               = White,
                        modifier           = Modifier.size(36.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text       = "Welcome Back",
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color      = White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text     = "Sign in to continue",
                    fontSize = 14.sp,
                    color    = WhiteDim,
                )
            }
        }

        // ── Form card ────────────────────────────────────────────────────────
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = (-20).dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // Email
                AuthLabel("Email")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value         = email,
                    onValueChange = { email = it },
                    placeholder   = "Enter your email",
                    leadingIcon   = Icons.Outlined.Email,
                    keyboardType  = KeyboardType.Email,
                )

                Spacer(Modifier.height(16.dp))

                // Password
                AuthLabel("Password")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value               = password,
                    onValueChange       = { password = it },
                    placeholder         = "Enter your password",
                    leadingIcon         = Icons.Outlined.Lock,
                    keyboardType        = KeyboardType.Password,
                    isPassword          = true,
                    passwordVisible     = passwordVisible,
                    onTogglePassword    = { passwordVisible = !passwordVisible },
                )

                Spacer(Modifier.height(8.dp))

                // Forgot password
                Text(
                    text     = "Forgot Password?",
                    fontSize = 13.sp,
                    color    = PrimaryBlue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onForgotPassword() },
                )

                Spacer(Modifier.height(24.dp))

                // Sign In button
                Button(
                    onClick  = { viewModel.login(email, password) },
                    enabled  = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier    = Modifier.size(24.dp),
                            color       = White,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            text       = "Sign In",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = White,
                        )
                    }
                }

                val errorState = uiState as? PatientLoginUiState.Error
                if (errorState != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text      = errorState.message,
                        fontSize  = 13.sp,
                        color     = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Sign up link
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text     = "Don't have an account? ",
                        fontSize = 13.sp,
                        color    = TextSecondary,
                    )
                    Text(
                        text       = "Sign Up",
                        fontSize   = 13.sp,
                        color      = PrimaryBlue,
                        fontWeight = FontWeight.SemiBold,
                        modifier   = Modifier.clickable { onSignUpClick() },
                    )
                }
            }
        }
    }
}

// ── Shared input components ───────────────────────────────────────────────────

@Composable
internal fun AuthLabel(text: String) {
    Text(
        text       = text,
        fontSize   = 13.sp,
        fontWeight = FontWeight.Medium,
        color      = Color(0xFF1A1A2E),
    )
}

@Composable
internal fun AuthTextField(
    value            : String,
    onValueChange    : (String) -> Unit,
    placeholder      : String,
    leadingIcon      : androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType     : KeyboardType = KeyboardType.Text,
    isPassword       : Boolean = false,
    passwordVisible  : Boolean = false,
    onTogglePassword : () -> Unit = {},
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        placeholder   = {
            Text(placeholder, fontSize = 13.sp, color = Color(0xFF9CA3AF))
        },
        leadingIcon   = {
            Icon(
                imageVector        = leadingIcon,
                contentDescription = null,
                tint               = Color(0xFF9CA3AF),
                modifier           = Modifier.size(18.dp),
            )
        },
        trailingIcon  = if (isPassword) ({
            IconButton(onClick = onTogglePassword) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible) "Hide" else "Show",
                    tint               = Color(0xFF9CA3AF),
                    modifier           = Modifier.size(18.dp),
                )
            }
        }) else null,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine      = true,
        modifier        = Modifier.fillMaxWidth(),
        shape           = RoundedCornerShape(10.dp),
        colors          = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFE5E7EB),
            focusedBorderColor   = Color(0xFF3D5AF1),
            unfocusedContainerColor = Color(0xFFF9FAFB),
            focusedContainerColor   = Color(0xFFFFFFFF),
        ),
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PatientLoginPreview() {
    PatientLoginScreen()
}