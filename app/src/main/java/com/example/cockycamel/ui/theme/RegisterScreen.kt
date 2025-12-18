package com.example.cockycamel.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cockycamel.ui.AppViewModel

@Composable
fun RegisterScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var experiencia by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Registro de Nuevo Enfermero",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = especialidad,
            onValueChange = { especialidad = it },
            label = { Text("Especialidad") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = experiencia,
            onValueChange = { experiencia = it },
            label = { Text("Años de experiencia") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp)
        )

        Button(
            onClick = {
                if (nombre.isNotBlank() && especialidad.isNotBlank()) {
                    // Aquí se simula el guardado. En una fase real llamaríamos a una función del ViewModel
                    Toast.makeText(context, "Enfermero $nombre registrado con éxito", Toast.LENGTH_SHORT).show()
                    onBack() // Volver tras registrar
                } else {
                    Toast.makeText(context, "Por favor, rellena los campos obligatorios", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = onBack) {
            Text("Cancelar y volver")
        }
    }
}