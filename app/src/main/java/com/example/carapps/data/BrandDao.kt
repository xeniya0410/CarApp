package com.example.carapps.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carapps.model.Brand

@Dao
interface BrandDao {
    @Query("SELECT * FROM brands")
    suspend fun getAllBrands(): List<Brand>

    @Insert
    suspend fun insertBrand(brand: Brand)

    @Delete
    suspend fun deleteBrand(brand: Brand)

    @Query("DELETE FROM brands")
    suspend fun deleteAll()

}
