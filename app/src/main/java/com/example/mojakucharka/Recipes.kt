package com.example.mojakucharka

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class Recipes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        this.showRecipes()
    }

    /**
     * zobrazenie receptov, ktore su ulozene v databaze
     * pre kazdy recept vytvori card, na ktorom recept zobrazi
     */
    private fun showRecipes() {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.readableDatabase

        // ake stlpce ideme vraciat z databazy
        val projection = arrayOf(BaseColumns._ID, MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME,
            MyDatabase.RecipeReader.RecipeEntry.COLUMN_INGREDIENTS, MyDatabase.RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS)

        val cursor : Cursor = db.query(
            MyDatabase.RecipeReader.RecipeEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
             null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        // prechadzanie kurzora a vytvaranie receptov
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val itemName = getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.RecipeEntry.COLUMN_NAME))
                val itemIngredient= getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INGREDIENTS))
                val itemInstruction = getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS))

                addRecipe(id,itemName, itemIngredient, itemInstruction)
            }
        }
        cursor.close()
    }

    /**
     * vytvorenie 1 card pre recept, kde nastavi vsetky hodnoty textov
     */
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

    /**
     * pridanie do nakupneho zoznamu ingrediencii po stlaceni tlacidla do zoznamu
     */
    private fun addToShoppingList(ingrediences: String) {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.writableDatabase

        // vkladane hodnoty
        val values = ContentValues().apply {
            put(MyDatabase.RecipeReader.ListEntry.COLUMN_INGREDIENTS, ingrediences)
        }

        // insert hodnoty
        val newRowId = db?.insert(MyDatabase.RecipeReader.ListEntry.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            val toast = Toast.makeText(applicationContext, "Nepodarilo sa pridať", Toast.LENGTH_LONG)
            toast.show()
        } else {
            val toast = Toast.makeText(applicationContext, "Pridané", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    /**
     * odstranenie receptu na zaklade jeho id po stlaceni tlacidla odstranit
     */
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