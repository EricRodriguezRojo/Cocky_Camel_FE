package com.example.cockycamel.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cockycamel.R

data class Enfermero(val id: Int, val nombre: String, val especialidad: String)

@Composable
fun SearchNurseScreen(onBack: () -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val searchResults = listOf(
        Enfermero(1, "Ana García", "Pediatría"),
        Enfermero(2, "Juan Pérez", "Urgencias"),
        Enfermero(3, "María López", "UCI")
    ).filter { it.nombre.contains(searchText, ignoreCase = true) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon_desc)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.search_results_header),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(searchResults) { enfermero ->
                EnfermeroCard(enfermero = enfermero)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onBack() }
        ) {
            Text(stringResource(R.string.btn_go_back))
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun EnfermeroCard(enfermero: Enfermero) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(
                        painter = painterResource(R.drawable.img_home),
                        contentDescription = stringResource(R.string.profile_placeholder_desc),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = enfermero.nombre,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.nurse_specialty_format, enfermero.especialidad),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 56.dp)
                )
            }
        }
    }
}
