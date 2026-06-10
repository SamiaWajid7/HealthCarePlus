package com.example.healthcareplus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcareplus.data.model.Appointment
import com.example.healthcareplus.data.repository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AppointmentUiState {
    data object Idle          : AppointmentUiState()
    data object Loading       : AppointmentUiState()
    data object BookSuccess   : AppointmentUiState()
    data class  Success(val appointments: List<Appointment>) : AppointmentUiState()
    data class  SingleSuccess(val appointment: Appointment)  : AppointmentUiState()  // single detail
    data class  Error(val message: String)                   : AppointmentUiState()
}

class AppointmentViewModel(
    private val repo: AppointmentRepository = AppointmentRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<AppointmentUiState>(AppointmentUiState.Idle)
    val state: StateFlow<AppointmentUiState> = _state.asStateFlow()

    // ── Load patient appointments (real-time) ─────────────────────────────
    fun loadPatientAppointments() {
        viewModelScope.launch {
            _state.value = AppointmentUiState.Loading
            repo.getPatientAppointments().collect { list ->
                _state.value = AppointmentUiState.Success(list)
            }
        }
    }

    // ── Load doctor appointments (real-time) ──────────────────────────────
    fun loadDoctorAppointments() {
        viewModelScope.launch {
            _state.value = AppointmentUiState.Loading
            repo.getDoctorAppointments().collect { list ->
                _state.value = AppointmentUiState.Success(list)
            }
        }
    }

    // ── Load single appointment by ID ─────────────────────────────────────
    fun loadAppointmentById(id: String) {
        viewModelScope.launch {
            _state.value = AppointmentUiState.Loading
            repo.getAppointmentById(id).fold(
                onSuccess = { _state.value = AppointmentUiState.SingleSuccess(it) },
                onFailure = { _state.value = AppointmentUiState.Error(it.message ?: "Error") }
            )
        }
    }

    // ── Patient books appointment ─────────────────────────────────────────
    fun bookAppointment(
        doctorId   : String,
        doctorName : String,
        specialty  : String,
        reason     : String,
        date       : String,
        time       : String,
    ) {
        viewModelScope.launch {
            _state.value = AppointmentUiState.Loading
            repo.bookAppointment(doctorId, doctorName, specialty, reason, date, time).fold(
                onSuccess = { _state.value = AppointmentUiState.BookSuccess },
                onFailure = { _state.value = AppointmentUiState.Error(it.message ?: "Failed") }
            )
        }
    }

    // ── Doctor updates status ─────────────────────────────────────────────
    fun updateStatus(appointmentId: String, status: String) {
        viewModelScope.launch {
            repo.updateStatus(appointmentId, status).fold(
                onSuccess = { /* list auto-updates via snapshot listener */ },
                onFailure = { _state.value = AppointmentUiState.Error(it.message ?: "Failed") }
            )
        }
    }
}