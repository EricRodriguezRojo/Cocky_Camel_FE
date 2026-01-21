package com.example.cockycamel.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    var nombreCompleto by remember { mutableStateOf("") }
    var usuarioLogin by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro de Enfermero", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombreCompleto,
            onValueChange = { nombreCompleto = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = usuarioLogin,
            onValueChange = { usuarioLogin = it },
            label = { Text("Nombre de Usuario (Login)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        )

        Button(
            onClick = {
                if (nombreCompleto.isNotBlank() && usuarioLogin.isNotBlank() && password.isNotBlank()) {
                    // Llamamos a la función con los 3 campos que pide tu Eclipse
                    viewModel.agregarEnfermero(nombreCompleto, usuarioLogin, password)

                    // También lo registramos como usuario general para que pueda hacer login
                    viewModel.registrarUsuario(usuarioLogin, password)

                    Toast.makeText(context, "Enfermero guardado con éxito", Toast.LENGTH_SHORT).show()
                    onBack()
                } else {
                    Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar en Base de Datos")
        }

        TextButton(onClick = onBack) { Text("Cancelar") }
    }
}