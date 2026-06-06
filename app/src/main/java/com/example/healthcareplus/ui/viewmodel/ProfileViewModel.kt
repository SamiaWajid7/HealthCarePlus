package com.example.healthcareplus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcareplus.data.model.UserProfile
import com.example.healthcareplus.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    data object Loading     : ProfileUiState()
    data object SaveSuccess : ProfileUiState()
    data class  Success(val profile: UserProfile) : ProfileUiState()
    data class  Error(val message: String)        : ProfileUiState()
}

class ProfileViewModel(
    private val repo: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init { loadProfile() }

    fun loadProfile() {
        viewModelScope.launch {
            _state.value = ProfileUiState.Loading
            repo.getCurrentUserProfile().fold(
                onSuccess = { _state.value = ProfileUiState.Success(it) },
                onFailure = { _state.value = ProfileUiState.Error(it.message ?: "Error") }
            )
        }
    }

    fun saveProfile(name: String, phone: String, dob: String, bloodGroup: String) {
        viewModelScope.launch {
            repo.updateProfile(name, phone, dob, bloodGroup).fold(
                onSuccess = { _state.value = ProfileUiState.SaveSuccess },
                onFailure = { _state.value = ProfileUiState.Error(it.message ?: "Save failed") }
            )
        }
    }
}