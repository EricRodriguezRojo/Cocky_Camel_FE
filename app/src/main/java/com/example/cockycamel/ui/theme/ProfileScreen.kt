package com.example.cockycamel.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    onLogout: () -> Unit,
    onBack: () -> Unit // AÑADIDO: Parámetro para volver
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val enfermero = uiState.enfermeros.find { it.user == uiState.currentUser }

    var nombre by remember { mutableStateOf(enfermero?.name ?: "") }
    var password by remember { mutableStateOf(enfermero?.password ?: "") }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Mi Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón Actualizar Datos propias (Punto 17)
        Button(
            onClick = {
                enfermero?.let {
                    viewModel.actualizarPerfil(it.id, nombre, it.user, password) { _, m ->
                        Toast.makeText(context, m, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar Datos")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón Darse de baja (Punto 18)
        Button(
            onClick = {
                enfermero?.let {
                    viewModel.eliminarCuenta(it.id) { exito, m ->
                        Toast.makeText(context, m, Toast.LENGTH_SHORT).show()
                        if (exito) onLogout()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Darse de Baja")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // AÑADIDO: Botón para volver a la HomeScreen
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a Inicio")
        }
    }
}