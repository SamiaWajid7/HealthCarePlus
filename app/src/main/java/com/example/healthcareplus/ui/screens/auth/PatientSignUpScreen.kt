package com.example.healthcareplus.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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

sealed class PatientSignUpUiState {
    data object Loading : PatientSignUpUiState()
    data object Success : PatientSignUpUiState()
    data class Error(val message: String) : PatientSignUpUiState()
}

class PatientSignUpViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<PatientSignUpUiState?>(null)
    val uiState: StateFlow<PatientSignUpUiState?> = _uiState.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = PatientSignUpUiState.Loading
            val result = authRepository.signUpPatient(name, email, password)
            _uiState.value = result.fold(
                onSuccess = { PatientSignUpUiState.Success },
                onFailure = { PatientSignUpUiState.Error(it.message ?: "Sign up failed") },
            )
        }
    }
}

@Composable
fun PatientSignUpScreen(
    onSignUpSuccess : (email: String) -> Unit = {},  // FIX 1: carries the real email
    onLoginClick    : () -> Unit = {},
    viewModel       : PatientSignUpViewModel = viewModel(),
) {
    var fullName         by remember { mutableStateOf("") }
    var email            by remember { mutableStateOf("") }
    var phone            by remember { mutableStateOf("") }
    var password         by remember { mutableStateOf("") }
    var confirmPassword  by remember { mutableStateOf("") }
    var passwordVisible  by remember { mutableStateOf(false) }
    var confirmVisible   by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState is PatientSignUpUiState.Loading

    LaunchedEffect(uiState) {
        if (uiState is PatientSignUpUiState.Success) {
            onSignUpSuccess(email.trim())  // FIX 2: pass the typed email, not a hardcode
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
                .padding(top = 56.dp, bottom = 28.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text       = "Create Account",
                    fontSize   = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text     = "Join us today",
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

                // Full Name
                AuthLabel("Full Name")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value         = fullName,
                    onValueChange = { fullName = it },
                    placeholder   = "Enter your full name",
                    leadingIcon   = Icons.Outlined.Person,
                )

                Spacer(Modifier.height(14.dp))

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

                Spacer(Modifier.height(14.dp))

                // Phone
                AuthLabel("Phone Number")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value         = phone,
                    onValueChange = { phone = it },
                    placeholder   = "Enter your phone number",
                    leadingIcon   = Icons.Outlined.Phone,
                    keyboardType  = KeyboardType.Phone,
                )

                Spacer(Modifier.height(14.dp))

                // Password
                AuthLabel("Password")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value            = password,
                    onValueChange    = { password = it },
                    placeholder      = "Create a password",
                    leadingIcon      = Icons.Outlined.Lock,
                    keyboardType     = KeyboardType.Password,
                    isPassword       = true,
                    passwordVisible  = passwordVisible,
                    onTogglePassword = { passwordVisible = !passwordVisible },
                )

                Spacer(Modifier.height(14.dp))

                // Confirm Password
                AuthLabel("Confirm Password")
                Spacer(Modifier.height(6.dp))
                AuthTextField(
                    value            = confirmPassword,
                    onValueChange    = { confirmPassword = it },
                    placeholder      = "Confirm your password",
                    leadingIcon      = Icons.Outlined.Lock,
                    keyboardType     = KeyboardType.Password,
                    isPassword       = true,
                    passwordVisible  = confirmVisible,
                    onTogglePassword = { confirmVisible = !confirmVisible },
                )

                Spacer(Modifier.height(24.dp))

                // Sign Up button
                Button(
                    onClick  = { viewModel.signUp(fullName, email, password) },
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
                            text       = "Sign Up",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Color.White,
                        )
                    }
                }

                val errorState = uiState as? PatientSignUpUiState.Error
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

                // Login link
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text     = "Already have an account? ",
                        fontSize = 13.sp,
                        color    = Color(0xFF6B7280),
                    )
                    Text(
                        text       = "Sign In",
                        fontSize   = 13.sp,
                        color      = Color(0xFF3D5AF1),
                        fontWeight = FontWeight.SemiBold,
                        modifier   = Modifier.clickable { onLoginClick() },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PatientSignUpPreview() {
    PatientSignUpScreen()
}