package com.example.cockycamel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cockycamel.ui.theme.CockyCamelTheme
import com.example.cockycamel.ui.HomeScreen
import com.example.cockycamel.ui.LoginScreen
import com.example.cockycamel.ui.NursesListScreen
import com.example.cockycamel.ui.SearchNurseScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CockyCamelTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    goHome: () -> Unit,
    goLogin: () -> Unit,
    goList: () -> Unit,
    goSearch: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.img_home),
            contentDescription = "Imagen Presentación",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Bienvenido a la aplicación.\nAccede rápidamente a todas las funciones.",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = goHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a HomeScreen")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = goLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = goList,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lista de enfermeros")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = goSearch,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar enfermero")
        }
    }
}


@Composable
fun AppScreen() {

    var numPantalla by remember { mutableStateOf(0) }

    when (numPantalla) {

        0 -> MainScreen(
            goHome = { numPantalla = 1 },
            goLogin = { numPantalla = 2 },
            goList = { numPantalla = 3 },
            goSearch = { numPantalla = 4 }
        )

        1 -> HomeScreen(
            onBack = { numPantalla = 0 }
        )

        2 -> LoginScreen(
            onBack = { numPantalla = 0 }
        )

        3 -> NursesListScreen(
            onBack = { numPantalla = 0 }
        )

        4 -> SearchNurseScreen(
            onBack = { numPantalla = 0 }
        )
    }
}
