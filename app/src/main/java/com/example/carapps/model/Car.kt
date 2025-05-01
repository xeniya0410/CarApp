package com.example.carapps.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cars",
    foreignKeys = [ForeignKey(
        entity = Brand::class,
        parentColumns = ["brandId"],
        childColumns = ["brandId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Car(
    @PrimaryKey(autoGenerate = true) val carId: Int = 0,
    val name: String,
    val description: String,
    val year: Int,
    val mileage: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val brandId: Int
)
