package com.example.mojakucharka

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.mojakucharka.database.KucharkaDatabase
import com.example.mojakucharka.database.MyDatabase
import com.example.mojakucharka.database.Recipe



class AddRecipe : AppCompatActivity() {
    //val databaseK = KucharkaDatabase.getInstance(application).recipeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
    }

    fun addRecipe(view: View) {
        val name = findViewById<EditText>(R.id.name_recipe).text.toString()
        val ingredients = findViewById<EditText>(R.id.ingredients_recipe).text.toString()
        val instructions = findViewById<EditText>(R.id.instructions_recipe).text.toString()

        val recipe = Recipe()

        //val addRecipeViewModel = AddRecipeViewModel(databaseK, requireNotNull(application))

        recipe.ingredients = ingredients
        recipe.name = name
        recipe.instructions = instructions

        val dbHelper = MyDatabase(this)
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME, name)
            put(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INGREDIENTS, ingredients)
            put(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS, instructions)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(MyDatabase.RecipeReader.RecipeEntry.TABLE_NAME, null, values)
        //addRecipeViewModel.addRecipe(recipe)

        val toast = Toast.makeText(applicationContext, "Pridané", Toast.LENGTH_LONG)
        toast.show()

        finish()

        //insert(recipe)
    }



}