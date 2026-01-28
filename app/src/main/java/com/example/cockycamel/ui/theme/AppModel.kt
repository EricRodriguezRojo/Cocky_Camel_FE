package com.example.cockycamel.ui

import android.util.Log
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
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NurseApiService {
    @GET("nurse/index")
    suspend fun getAllNurses(): List<Nurse>

    @GET("nurse/name/{name}")
    suspend fun getNurseByName(@Path("name") name: String): Nurse

    @POST("nurse")
    suspend fun createNurse(@Body nurse: Nurse): ResponseBody
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val service: NurseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NurseApiService::class.java)
    }
}

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
        fetchEnfermeros()
    }

    fun fetchEnfermeros() {
        viewModelScope.launch {
            nurseUiState = NurseUiState.Loading
            try {
                val listaRemota = RetrofitClient.service.getAllNurses()
                _uiState.update { it.copy(enfermeros = listaRemota) }
                nurseUiState = NurseUiState.Success(listaRemota)
                Log.d("Retrofit", "Datos recibidos: ${listaRemota.size}")
            } catch (e: Exception) {
                Log.e("Retrofit", "Error al listar: ${e.message}")
                nurseUiState = NurseUiState.Error
                _uiState.update { it.copy(enfermeros = emptyList()) }
            }
        }
    }

    fun buscarEnfermeroPorNombre(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                fetchEnfermeros()
                return@launch
            }
            try {
                val enfermeroEncontrado = RetrofitClient.service.getNurseByName(query)
                _uiState.update { it.copy(enfermeros = listOf(enfermeroEncontrado)) }
            } catch (e: Exception) {
                Log.e("Retrofit", "No encontrado o error busqueda: ${e.message}")
                _uiState.update { it.copy(enfermeros = emptyList()) }
            }
        }
    }

    fun agregarEnfermero(nombreCompleto: String, usuario: String, pass: String) {
        viewModelScope.launch {
            try {
                val nuevoEnfermero = Nurse(0, nombreCompleto, usuario, pass)
                val respuesta = RetrofitClient.service.createNurse(nuevoEnfermero)
                Log.d("Retrofit", "Creado: ${respuesta.string()}")

                fetchEnfermeros()
            } catch (e: Exception) {
                Log.e("Retrofit", "Error al crear: ${e.message}")
            }
        }
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
}