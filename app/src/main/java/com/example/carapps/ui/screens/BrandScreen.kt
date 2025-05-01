package com.example.carapps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carapps.model.Brand
import com.example.carapps.viewmodel.CarViewModel

@Composable
fun BrandScreen(viewModel: CarViewModel) {
    var newBrandName by remember { mutableStateOf("") }

    val brandList = viewModel.brandList

    LaunchedEffect(Unit) {
        viewModel.loadBrands()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Бренды автомобилей", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = newBrandName,
                onValueChange = { newBrandName = it },
                label = { Text("Новый бренд") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newBrandName.isNotBlank()) {
                    viewModel.addBrand(Brand(name = newBrandName))
                    newBrandName = ""
                }
            }) {
                Text("Добавить")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        brandList.forEach { brand ->
            BrandItem(brand = brand, onDelete = {
                viewModel.deleteBrand(brand)
            })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun BrandItem(brand: Brand, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(brand.name, style = MaterialTheme.typography.titleMedium)
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Удалить")
            }
        }
    }
}
