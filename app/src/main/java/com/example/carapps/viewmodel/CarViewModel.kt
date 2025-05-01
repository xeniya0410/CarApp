package com.example.carapps.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapps.data.CarDatabase
import com.example.carapps.model.Brand
import com.example.carapps.model.Car
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {
    private val db = CarDatabase.getDatabase(application)

    var carList = mutableStateListOf<Car>()
        private set

    var brandList = mutableStateListOf<Brand>()
        private set

    fun loadCars() {
        viewModelScope.launch {
            try {
                carList.clear()
                carList.addAll(db.carDao().getAllCars())
                Log.i("CarViewModel", "Car list successfully loaded from the database.")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error loading car list: ${e.message}", e)
            }
        }
    }

    fun loadBrands() {
        viewModelScope.launch {
            try {
                val brandsFromDb = db.brandDao().getAllBrands()

                if (brandsFromDb.isEmpty()) {
                    val predefined = listOf(
                        Brand(1, "Toyota"),
                        Brand(2, "BMW"),
                        Brand(3, "Audi"),
                        Brand(4, "Lada")
                    )
                    predefined.forEach { db.brandDao().insertBrand(it) }
                    brandList.clear()
                    brandList.addAll(predefined)
                    Log.i("CarViewModel", "Predefined brands inserted.")
                } else {
                    brandList.clear()
                    brandList.addAll(brandsFromDb)
                    Log.i("CarViewModel", "Brands loaded from database.")
                }

            } catch (e: Exception) {
                Log.e("CarViewModel", "Error loading brand list: ${e.message}", e)
            }
        }
    }


    fun addCar(car: Car) {
        viewModelScope.launch {
            try {
                Log.i("CarViewModel", "Attempting to add car: ${car.name}")
                db.carDao().insertCar(car)
                loadCars()
                Log.i("CarViewModel", "Car added to the database: ${car.name}")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error adding car to the database: ${e.message}", e)
            }
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            try {
                db.carDao().deleteCar(car)
                loadCars()
                Log.i("CarViewModel", "Car deleted from the database: ${car.name}")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error deleting car from the database: ${e.message}", e)
            }
        }
    }

    fun addBrand(brand: Brand) {
        viewModelScope.launch {
            try {
                db.brandDao().insertBrand(brand)
                loadBrands()
                Log.i("CarViewModel", "Brand added to the database: ${brand.name}")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error adding brand to the database: ${e.message}", e)
            }
        }
    }

    fun deleteBrand(brand: Brand) {
        viewModelScope.launch {
            try {
                db.brandDao().deleteBrand(brand)
                loadBrands()
                Log.i("CarViewModel", "Brand deleted from the database: ${brand.name}")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error deleting brand from the database: ${e.message}", e)
            }
        }
    }
    fun clearAllData() {
        viewModelScope.launch {
            try {
                db.carDao().deleteAll()
                db.brandDao().deleteAll()
                Log.i("CarViewModel", "Database cleared.")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Error clearing database: ${e.message}", e)
            }
        }
    }
}

