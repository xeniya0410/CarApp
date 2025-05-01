package com.example.carapps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carapps.model.Car
import com.example.carapps.viewmodel.CarViewModel

@Composable
fun CarListScreen(
    viewModel: CarViewModel,
    onAddClick: () -> Unit
) {
    val carList = viewModel.carList

    LaunchedEffect(Unit) {
        viewModel.loadCars()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Список автомобилей", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            if (carList.isEmpty()) {
                Text("Нет добавленных машин.")
            } else {
                carList.forEach { car ->
                    CarItem(car = car, onDelete = {
                        viewModel.deleteCar(car)
                    })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun CarItem(car: Car, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${car.name} (${car.year})", style = MaterialTheme.typography.titleMedium)
            Text("Пробег: ${car.mileage} км")
            Text("Описание: ${car.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Удалить")
            }
        }
    }
}
