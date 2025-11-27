package com.example.cockycamel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.cockycamel.R
import androidx.compose.ui.text.style.TextAlign


@Composable
fun HomeScreen(onBack: () -> Unit) {

    var showDateTime by remember { mutableStateOf(false) }
    var dateTimeText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.img_home),
            contentDescription = stringResource(R.string.home_icon_desc),
            modifier = Modifier.size(350.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(R.string.home_welcome_text),
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onBack() }
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
            color = Color(0xFF00897B)
        )

        if (showDateTime) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = dateTimeText,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black
            )
        }
    }
}