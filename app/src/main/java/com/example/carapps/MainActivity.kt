package com.example.carapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carapps.ui.screens.AddCarScreen
import com.example.carapps.ui.screens.CarListScreen
import com.example.carapps.ui.theme.CarAppsTheme
import com.example.carapps.viewmodel.CarViewModel

class MainActivity : ComponentActivity() {

    private val carViewModel: CarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            CarAppsTheme {
                NavHost(navController = navController, startDestination = "list") {

                    composable("list") {
                        CarListScreen(
                            viewModel = carViewModel,
                            onAddClick = { navController.navigate("add") }
                        )
                    }

                    composable("add") {
                        AddCarScreen(
                            viewModel = carViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        carViewModel.clearAllData()
    }
}
