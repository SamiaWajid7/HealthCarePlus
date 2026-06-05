package com.example.healthcareplus.ui.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

sealed class DoctorLoginUiState {
    data object Loading : DoctorLoginUiState()
    data object Success : DoctorLoginUiState()
    data class Error(val message: String) : DoctorLoginUiState()
}

class DoctorLoginViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
) : ViewModel() {

    private val _medicalId = MutableStateFlow("")
    val medicalId: StateFlow<String> = _medicalId.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _uiState = MutableStateFlow<DoctorLoginUiState?>(null)
    val uiState: StateFlow<DoctorLoginUiState?> = _uiState.asStateFlow()

    fun onMedicalIdChange(value: String) {
        _medicalId.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = DoctorLoginUiState.Loading
            val result = authRepository.loginDoctor(_medicalId.value, _password.value)
            _uiState.value = result.fold(
                onSuccess = { DoctorLoginUiState.Success },
                onFailure = { DoctorLoginUiState.Error(it.message ?: "Login failed") },
            )
        }
    }
}

@Composable
fun DoctorLoginScreen(
    onLoginSuccess   : () -> Unit = {},
    onForgotPassword : () -> Unit = {},
    viewModel        : DoctorLoginViewModel = viewModel(),
) {
    val medicalId by viewModel.medicalId.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe      by remember { mutableStateOf(false) }

    val isLoading = uiState is DoctorLoginUiState.Loading

    LaunchedEffect(uiState) {
        if (uiState is DoctorLoginUiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {

        // ── Header ───────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF3D5AF1),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                )
                .padding(top = 56.dp, bottom = 36.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.HealthAndSafety,
                        contentDescription = null,
                        tint               = Color(0xFF3D5AF1),
                        modifier           = Modifier.size(40.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text       = "Doctor Portal",
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text     = "Sign in to manage your patients",
                    fontSize = 14.sp,
                    color    = Color(0xCCFFFFFF),
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
            colors    = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // Medical ID / Email
                AuthLabel("Medical ID / Email")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value         = medicalId,
                    onValueChange = viewModel::onMedicalIdChange,
                    placeholder   = "Enter your medical ID or email",
                    leadingIcon   = Icons.Outlined.Email,
                    keyboardType  = KeyboardType.Email,
                )

                Spacer(Modifier.height(16.dp))

                // Password
                AuthLabel("Password")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value            = password,
                    onValueChange    = viewModel::onPasswordChange,
                    placeholder      = "Enter your password",
                    leadingIcon      = Icons.Outlined.Lock,
                    keyboardType     = KeyboardType.Password,
                    isPassword       = true,
                    passwordVisible  = passwordVisible,
                    onTogglePassword = { passwordVisible = !passwordVisible },
                )

                Spacer(Modifier.height(12.dp))

                // Remember me + Forgot password row
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked         = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors          = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF3D5AF1),
                            ),
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text     = "Remember me",
                            fontSize = 13.sp,
                            color    = Color(0xFF6B7280),
                        )
                    }
                    Text(
                        text       = "Forgot Password?",
                        fontSize   = 13.sp,
                        color      = Color(0xFF3D5AF1),
                        fontWeight = FontWeight.Medium,
                        modifier   = Modifier.clickable { onForgotPassword() },
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Sign In button
                Button(
                    onClick  = { viewModel.login() },
                    enabled  = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D5AF1),
                    ),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier    = Modifier.size(24.dp),
                            color       = Color.White,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            text       = "Sign In",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Color.White,
                        )
                    }
                }

                val errorState = uiState as? DoctorLoginUiState.Error
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
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DoctorLoginPreview() {
    DoctorLoginScreen()
}
