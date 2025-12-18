package com.example.cockycamel.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class User(
    val username: String,
    val password: String
)
data class Nurse(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val experiencia: Int
)

data class AppUiState(
    val enfermeros: List<Nurse> = emptyList(),
    val usuarios: List<User> = listOf(User("admin", "1234")),
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

    fun registrarUsuario(user: String, pass: String) {
        val nuevoUsuario = User(user, pass)
        _uiState.update { it.copy(usuarios = it.usuarios + nuevoUsuario) }
    }

    // funcion para simular login
    fun login(user: String, pass: String): Boolean {
        val usuarioEncontrado = _uiState.value.usuarios.find {
            it.username == user && it.password == pass
        }

        return if (usuarioEncontrado != null) {
            _uiState.update { it.copy(isLoggedIn = true, currentUser = user) }
            true
        } else {
            false
        }
    }

    fun agregarEnfermero(nombre: String, especialidad: String, experiencia: Int) {
        val nuevoId = (_uiState.value.enfermeros.maxOfOrNull { it.id } ?: 0) + 1
        val nuevoEnfermero = Nurse(nuevoId, nombre, especialidad, experiencia)
        _uiState.update { currentState ->
            currentState.copy(enfermeros = currentState.enfermeros + nuevoEnfermero)
        }
    }
}
