package com.example.mojakucharka

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast


class AddRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
    }

    /**
     * Pridanie databazy receptu do databazy pomocou vyplnenych inputov
     */
    fun addRecipe(view: View) {
        // najdenie miest, kde sa nachadzaju vkladane texty
        val name = findViewById<EditText>(R.id.name_recipe).text.toString()
        val ingredients = findViewById<EditText>(R.id.ingredients_recipe).text.toString()
        val instructions = findViewById<EditText>(R.id.instructions_recipe).text.toString()

        // osetrenie zadaneho vstupu
        if  (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            val toastEmpty = Toast.makeText(applicationContext, "Potrebné vyplniť všetky položky", Toast.LENGTH_LONG)
            toastEmpty.show()
            return
        }
        //databaza
        val dbHelper = MyDatabase(this)
        val db = dbHelper.writableDatabase

        // vytvorenie hodnot, kde su ako kluce jednotilve stlpce tabulky
        val values = ContentValues().apply {
            put(MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME, name)
            put(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INGREDIENTS, ingredients)
            put(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS, instructions)
        }

        // Vlozenie riadku
        val newRowId = db?.insert(MyDatabase.RecipeReader.RecipeEntry.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            val toast = Toast.makeText(applicationContext, "Nepodarilo sa pridať", Toast.LENGTH_LONG)
            toast.show()
        } else {
            val toast = Toast.makeText(applicationContext, "Pridané", Toast.LENGTH_LONG)
            toast.show()
        }

        finish()
    }

}