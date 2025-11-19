package com.example.cockycamel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cockycamel.R

@Composable
fun HomeScreen() {

    var showInfo by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Icono de inicio",
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Pantalla Principal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006CFF)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { /* navegación luego */ }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* navegación luego */ }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Registro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* navegación luego */ }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Búsqueda")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = if (showInfo) "Ocultar información" else "Mostrar información",
            modifier = Modifier.clickable { showInfo = !showInfo }.padding(8.dp),
            color = Color(0xFF00897B)
        )

        if (showInfo) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Esta aplicación permite gestionar enfermeros y consultar información de manera rápida.",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black
            )
        }
    }
}
