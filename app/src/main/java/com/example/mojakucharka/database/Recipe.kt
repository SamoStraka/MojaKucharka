package com.example.mojakucharka.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    var recipeId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "meno",

    @ColumnInfo(name = "ingredients")
    var ingredients: String = "ingredients",

    @ColumnInfo(name = "ingredients")
    var instructions: String = "instructions",
)
