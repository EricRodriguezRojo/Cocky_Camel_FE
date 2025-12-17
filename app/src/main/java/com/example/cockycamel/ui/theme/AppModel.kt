package com.example.cockycamel.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Nurse(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val experiencia: Int
)

data class AppUiState(
    val enfermeros: List<Nurse> = emptyList(),
    val isLoggedIn: Boolean = false,
    val currentUser: String = ""
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        val datosIniciales = listOf(
            Nurse(1, "María López", "Pediatría", 5),
            Nurse(2, "Juan Pérez", "Urgencias", 8),
            Nurse(3, "Ana Torres", "Cuidados intensivos", 6),
            Nurse(4, "David Soler", "Geriatría", 4),
            Nurse(5, "Lucía Martín", "Cardiología", 7)
        )
        _uiState.update { it.copy(enfermeros = datosIniciales) }
    }

    // funcion para simular login
    fun login(user: String) {
        _uiState.update { it.copy(isLoggedIn = true, currentUser = user) }
    }
}
