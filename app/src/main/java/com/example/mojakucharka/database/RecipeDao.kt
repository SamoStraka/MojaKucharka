package com.example.mojakucharka.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)

    @Query("SELECT * from recipes WHERE recipeId = :key")
    suspend fun get(key: Long): Recipe?

    @Query("DELETE FROM recipes WHERE recipeId = :key")
    suspend fun delete(key: Long)
}