package com.example.cockycamel.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cockycamel.ui.AppViewModel

@Composable
fun RegisterScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var esEnfermero by remember { mutableStateOf(false) }

    var especialidad by remember { mutableStateOf("") }
    var experiencia by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (esEnfermero) "Registrar Enfermero" else "Crear nueva cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("¿Es un perfil de enfermero?")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = esEnfermero,
                onCheckedChange = { esEnfermero = it }
            )
        }

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text(if (esEnfermero) "Nombre completo" else "Nombre de usuario") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        if (esEnfermero) {
            OutlinedTextField(
                value = especialidad,
                onValueChange = { especialidad = it },
                label = { Text("Especialidad (ej. Pediatría)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            )

            OutlinedTextField(
                value = experiencia,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) experiencia = it
                },
                label = { Text("Años de experiencia") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (usuario.isNotBlank() && password.isNotBlank()) {
                    if (esEnfermero) {
                        val expInt = experiencia.toIntOrNull() ?: 0
                        if (especialidad.isNotBlank()) {
                            viewModel.registrarUsuario(usuario, password)
                            viewModel.agregarEnfermero(usuario, especialidad, expInt)

                            Toast.makeText(context, "Enfermero registrado y guardado", Toast.LENGTH_SHORT).show()
                            onBack()
                        } else {
                            Toast.makeText(context, "Faltan datos del enfermero", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        viewModel.registrarUsuario(usuario, password)
                        Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                        onBack()
                    }
                } else {
                    Toast.makeText(context, "Rellena los campos básicos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esEnfermero) "Guardar Enfermero" else "Registrarse")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = onBack) {
            Text("Cancelar y volver")
        }
    }
}