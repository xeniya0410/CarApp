package com.example.carapps.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.carapps.model.Car

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>

    @Insert
    suspend fun insertCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("DELETE FROM cars")
    suspend fun deleteAll()
}
