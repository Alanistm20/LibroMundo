package edu.com.cibertec.libromundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.com.cibertec.libromundo.ui.theme.screens.MainScreen
import edu.com.cibertec.libromundo.ui.theme.screens.ResumenScreen
import edu.com.cibertec.libromundo.ui.theme.LibroMundoTheme
import edu.com.cibertec.libromundo.viewmodel.LibroViewModel

class MainActivity : ComponentActivity() {

    private val libroViewModel: LibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibroMundoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(
                            viewModel = libroViewModel,
                            navController = navController
                        )
                    }
                    composable("resumen") {
                        ResumenScreen(
                            viewModel = libroViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

