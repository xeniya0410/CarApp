package com.example.carapps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carapps.model.Brand
import com.example.carapps.model.Car

@Database(entities = [Car::class, Brand::class], version = 1)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun brandDao(): BrandDao

    companion object {
        @Volatile private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
