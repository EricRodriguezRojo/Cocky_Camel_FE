package com.example.cockycamel.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class User(val username: String, val password: String)

data class Nurse(
    val id: Int,
    val name: String,
    val user: String,
    val password: String
)

sealed interface NurseUiState {
    data class Success(val enfermeros: List<Nurse>) : NurseUiState
    object Error : NurseUiState
    object Loading : NurseUiState
}

data class AppUiState(
    val enfermeros: List<Nurse> = emptyList(),
    val usuarios: List<User> = listOf(User("admin", "1234")),
    val isLoggedIn: Boolean = false,
    val currentUser: String = ""
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    var nurseUiState: NurseUiState by mutableStateOf(NurseUiState.Loading)
        private set

    init {
        val datosIniciales = listOf(
            Nurse(1, "María López", "mlopez", "1234"),
            Nurse(2, "Juan Pérez", "jperez", "abcd")
        )
        _uiState.update { it.copy(enfermeros = datosIniciales) }
    }

    fun registrarUsuario(user: String, pass: String) {
        val nuevoUsuario = User(user, pass)
        _uiState.update { it.copy(usuarios = it.usuarios + nuevoUsuario) }
    }

    fun login(user: String, pass: String): Boolean {
        val usuarioEncontrado = _uiState.value.usuarios.find {
            it.username == user && it.password == pass
        }
        return if (usuarioEncontrado != null) {
            _uiState.update { it.copy(isLoggedIn = true, currentUser = user) }
            true
        } else false
    }

    fun agregarEnfermero(nombreCompleto: String, usuario: String, pass: String) {
        val nuevoId = (_uiState.value.enfermeros.maxOfOrNull { it.id } ?: 0) + 1
        val nuevoEnfermero = Nurse(nuevoId, nombreCompleto, usuario, pass)
        _uiState.update { it.copy(enfermeros = it.enfermeros + nuevoEnfermero) }
    }

    fun fetchEnfermeros() {
        viewModelScope.launch {
            nurseUiState = NurseUiState.Loading
            try {
                val listResult = NurseApi.retrofitService.getEnfermeros()
                nurseUiState = NurseUiState.Success(listResult)
            } catch (e: Exception) {
                nurseUiState = NurseUiState.Error
            }
        }
    }
}