package com.example.cockycamel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cockycamel.ui.theme.CockyCamelTheme
import com.example.cockycamel.ui.*
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CockyCamelTheme {
                val viewModel: AppViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "Main"
                ) {

                    composable("Main") {
                        MainScreen(navController)
                    }

                    composable("Login") {
                        LoginScreen(
                            viewModel = viewModel,
                            onLoginSuccess = {
                                navController.navigate("Home") {
                                    popUpTo("Main") { inclusive = false }
                                }
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("Register") {
                        RegisterScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable("Home") {
                        HomeScreen(
                            onNavigateToList = { navController.navigate("ListNurses") },
                            onNavigateToSearch = { navController.navigate("SearchNurses") },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable("ListNurses") {
                        NursesListScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable("SearchNurses") {
                        SearchNurseScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {

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

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("Login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("Register") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}