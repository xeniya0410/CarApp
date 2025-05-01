package com.example.carapps.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brands")
data class Brand(
    @PrimaryKey(autoGenerate = true) val brandId: Int = 0,
    val name: String
)
