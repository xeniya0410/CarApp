package com.example.carapps.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carapps.model.Car
import com.example.carapps.viewmodel.CarViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(
    viewModel: CarViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedBrandId by remember { mutableStateOf<Int?>(null) }
    var selectedBrandName by remember { mutableStateOf("Выбрать марку") }

    val brands = viewModel.brandList

    LaunchedEffect(Unit) {
        if (brands.isEmpty()) viewModel.loadBrands()
    }

    // Флаг блокировки кнопки
    var isButtonEnabled by remember { mutableStateOf(true) }

    // Логика для управления состоянием кнопки
    LaunchedEffect(isButtonEnabled) {
        if (!isButtonEnabled) {
            delay(1000) // Таймаут блокировки кнопки
            isButtonEnabled = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить автомобиль") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(value = name, onValueChange = { name = it }, label = { Text("Название") })
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Год") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = mileage,
                onValueChange = { mileage = it },
                label = { Text("Пробег") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = description, onValueChange = { description = it }, label = { Text("Описание") })
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedBrandName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Марка") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    brands.forEach { brand ->
                        DropdownMenuItem(
                            text = { Text(brand.name) },
                            onClick = {
                                selectedBrandId = brand.brandId
                                selectedBrandName = brand.name
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isButtonEnabled) {
                        isButtonEnabled = false

                        // Логика добавления автомобиля
                        val carYear = year.toIntOrNull()
                        val carMileage = mileage.toIntOrNull()

                        if (name.isNotBlank() && carYear != null && carMileage != null && selectedBrandId != null) {
                            val car = Car(
                                name = name,
                                year = carYear,
                                mileage = carMileage,
                                description = description,
                                brandId = selectedBrandId!!
                            )
                            viewModel.addCar(car)

                            // Очистка полей
                            name = ""
                            year = ""
                            mileage = ""
                            description = ""
                            selectedBrandId = null
                            selectedBrandName = "Выбрать марку"

                            // Навигация назад
                            navController.popBackStack()
                        } else {
                            Log.i("AddCarScreen", "Not all fields are filled correctly!")
                        }
                    }
                },
                enabled = isButtonEnabled
            ) {
                Text("Добавить автомобиль")
            }
        }
    }
}
