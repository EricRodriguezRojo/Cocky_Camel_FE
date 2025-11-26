package com.example.cockycamel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cockycamel.R

data class Nurse(
    val nombre: String,
    val especialidad: String,
    val experiencia: Int
)

@Composable
fun NurseListScreen(modifier: Modifier = Modifier) {

    val enfermeros = listOf(
        Nurse("María López", "Pediatría", 5),
        Nurse("Juan Pérez", "Urgencias", 8),
        Nurse("Ana Torres", "Cuidados intensivos", 6),
        Nurse("David Soler", "Geriatría", 4),
        Nurse("Lucía Martín", "Cardiología", 7)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Lista de enfermeros",
            fontSize = 26.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(enfermeros) { enfermero ->
                NurseCard(enfermero)
            }
        }
    }
}

@Composable
fun NurseCard(nurse: Nurse) {

    val expanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded.value = !expanded.value },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            // información sin expandirse
            Row(verticalAlignment = Alignment.CenterVertically) {

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = nurse.nombre,
                    fontSize = 20.sp,
                    color = Color(0xFF00695C)
                )
            }

            // informacion al expamndirse
            if (expanded.value) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Especialidad: ${nurse.especialidad}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = "Experiencia: ${nurse.experiencia} años",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
