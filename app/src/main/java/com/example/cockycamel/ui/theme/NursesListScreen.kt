package com.example.cockycamel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cockycamel.R

data class Nurse(
    val nombre: String,
    val especialidad: String,
    val experiencia: Int
)

@Composable
fun NursesListScreen(onBack: () -> Unit) {

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
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {

        Text(
            text = stringResource(R.string.nurses_list_title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(enfermeros) { enfermero ->
                NurseCard(enfermero)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onBack() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(stringResource(R.string.btn_go_back))
        }

        Spacer(modifier = Modifier.height(10.dp))
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = nurse.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (expanded.value) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.nurse_specialty_format, nurse.especialidad),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Text(
                    text = stringResource(R.string.nurse_experience_format, nurse.experiencia),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}