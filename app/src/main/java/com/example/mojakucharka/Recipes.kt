package com.example.mojakucharka

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.mojakucharka.database.MyDatabase

class Recipes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        this.showRecipes()
    }

    private fun showRecipes() {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME,
            MyDatabase.RecipeReader.RecipeEntry.COLUMN_INGREDIENTS, MyDatabase.RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME} = ?"
        val selectionArgs = arrayOf("ja")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME} DESC"

        println("pripravene na cursor")

        val cursor : Cursor = db.query(
            "recipes",   // The table to query
            projection,             // The array of columns to return (pass null to get all)
             null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val itemNames = mutableListOf<String>()
        val itemIngredients = mutableListOf<String>()
        val itemInstructions = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val itemName = getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME))
                val itemIngredient= getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INGREDIENTS))
                val itemInstruction = getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS))
                itemNames.add(itemName)
                itemIngredients.add(itemIngredient)
                itemInstructions.add(itemInstruction)
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                addRecipe(id,itemName, itemIngredient, itemInstruction)
            }
        }
        cursor.close()
    }

    private fun addRecipe(id:Long, name:String, ing:String, ins:String) {
        val layout = findViewById<LinearLayout>(R.id.layout_recipes)
        val view = layoutInflater.inflate(R.layout.card, null)

        val nameView = view.findViewById<TextView>(R.id.recipe_n)
        val ingView = view.findViewById<TextView>(R.id.recipe_ing)
        val insView = view.findViewById<TextView>(R.id.recipe_ins)

        nameView.setText(name)
        ingView.setText(ing)
        insView.setText(ins)

        val addButton = view.findViewById<Button>(R.id.buttonList)

        addButton.setOnClickListener(){
            addToShoppingList(ing)
        }

        val delButton = view.findViewById<Button>(R.id.buttonDel)

        delButton.setOnClickListener() {
            deleteRecipe(id)
        }

        layout.addView(view)
    }

    private fun addToShoppingList(ingrediences: String) {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(MyDatabase.RecipeReader.ListEntry.COLUMN_INGREDIENTS, ingrediences)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(MyDatabase.RecipeReader.ListEntry.TABLE_NAME, null, values)
        //addRecipeViewModel.addRecipe(recipe)

        val toast = Toast.makeText(applicationContext, "Pridané", Toast.LENGTH_LONG)
        toast.show()
    }

    private fun deleteRecipe(id: Long) {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.readableDatabase
        // Define 'where' part of query.
        val selection = "${BaseColumns._ID} = ${id}"
        // Specify arguments in placeholder order.
        //val selectionArgs = arrayOf(id.toString())
        // Issue SQL statement.
        val deletedRows = db.delete(MyDatabase.RecipeReader.RecipeEntry.TABLE_NAME, selection, null)

        val toast = Toast.makeText(applicationContext, "Vymazané", Toast.LENGTH_LONG)
        toast.show()

        finish()
    }

}