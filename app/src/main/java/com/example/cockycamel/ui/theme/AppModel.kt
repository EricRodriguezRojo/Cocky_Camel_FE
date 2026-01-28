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
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NurseApiService {
    @GET("nurse/index")
    suspend fun getAllNurses(): List<Nurse>

    @GET("nurse/name/{name}")
    suspend fun getNurseByName(@Path("name") name: String): Nurse

    @POST("nurse")
    suspend fun createNurse(@Body nurse: Nurse): ResponseBody

    @POST("nurse/login/{user}/{password}")
    suspend fun loginNurse(
        @Path("user") user: String,
        @Path("password") pass: String
    ): ResponseBody

    @GET("nurse/{id}")
    suspend fun getNurseById(@Path("id") id: Int): Nurse

    @PUT("nurse/{id}")
    suspend fun updateNurse(@Path("id") id: Int, @Body nurse: Nurse): ResponseBody

    @DELETE("nurse/{id}")
    suspend fun deleteNurse(@Path("id") id: Int): ResponseBody
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

    fun loginRemote(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.service.loginNurse(user, pass)
                val mensaje = respuesta.string()

                if (mensaje.contains("Login correcto", ignoreCase = true)) {
                    _uiState.update { it.copy(isLoggedIn = true, currentUser = user) }
                    onResult(true, mensaje)
                } else {
                    onResult(false, mensaje)
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Error Login: ${e.message}")
                onResult(false, "Error de conexión o credenciales inválidas")
            }
        }
    }


    var loggedInNurseId by mutableStateOf<Int?>(null)

    fun cargarPerfil(id: Int) {
        viewModelScope.launch {
            try {
                val nurse = RetrofitClient.service.getNurseById(id)
                // Aquí podrías actualizar un estado específico para el perfil si fuera necesario
                Log.d("Retrofit", "Perfil cargado: ${nurse.name}")
            } catch (e: Exception) {
                Log.e("Retrofit", "Error al cargar perfil: ${e.message}")
            }
        }
    }

    fun actualizarPerfil(id: Int, nombre: String, usuario: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val nurseUpdate = Nurse(id, nombre, usuario, pass)
                val response = RetrofitClient.service.updateNurse(id, nurseUpdate)
                val mensaje = response.string()
                fetchEnfermeros() // Refrescar lista global
                onResult(true, mensaje)
            } catch (e: Exception) {
                onResult(false, "Error al actualizar: ${e.message}")
            }
        }
    }

    fun eliminarCuenta(id: Int, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.service.deleteNurse(id)
                val mensaje = response.string()
                _uiState.update { it.copy(isLoggedIn = false, currentUser = "") }
                loggedInNurseId = null
                onResult(true, mensaje)
            } catch (e: Exception) {
                onResult(false, "Error al eliminar: ${e.message}")
            }
        }
    }
}