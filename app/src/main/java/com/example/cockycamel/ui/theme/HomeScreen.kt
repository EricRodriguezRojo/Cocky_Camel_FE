package com.example.cockycamel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cockycamel.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    onNavigateToList: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onBack: () -> Unit
) {
    var showDateTime by remember { mutableStateOf(false) }
    var dateTimeText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_home),
            contentDescription = stringResource(R.string.home_icon_desc),
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.home_welcome_text),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(30.dp))


        Button(
            onClick = { onNavigateToList() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Listar Enfermeros")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onNavigateToSearch() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Buscar Enfermeros")
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

        Spacer(modifier = Modifier.height(20.dp))

        val toggleText = if (showDateTime) {
            stringResource(R.string.action_hide_datetime)
        } else {
            stringResource(R.string.action_show_datetime)
        }

        Text(
            text = toggleText,
            modifier = Modifier
                .clickable {
                    showDateTime = !showDateTime
                    if (showDateTime) {
                        val current = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                        dateTimeText = current.format(formatter)
                    }
                }
                .padding(8.dp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        if (showDateTime) {
            Text(
                text = dateTimeText,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}