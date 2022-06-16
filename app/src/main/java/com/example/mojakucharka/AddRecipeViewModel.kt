package com.example.mojakucharka

import android.app.Application
import android.view.View
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import com.example.mojakucharka.database.Recipe
import com.example.mojakucharka.database.RecipeDao

class AddRecipeViewModel(
    val databaseK: RecipeDao,
    application: Application
): AndroidViewModel(application) {

    fun addRecipe(recipe: Recipe) {
        println(recipe.ingredients)
        //insert(recipe)
    }

    private suspend fun insert(recipe: Recipe) {
        databaseK.insert(recipe)
    }
}
