package com.example.mojakucharka

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ShoppingList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        addList()
    }

    /**
     * Vycistenie zoznamu a odstranenie z databazy
     */
    fun clearList(view: View) {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.readableDatabase
        db.execSQL("delete from "+ MyDatabase.RecipeReader.ListEntry.TABLE_NAME)
        val toast = Toast.makeText(applicationContext, "Vymazané", Toast.LENGTH_LONG)
        toast.show()

        finish()
    }

    /**
     * Zobrazanie zoznamu, pricom najskor zoznam ziskame z databazy a nasledne ho zobrazime
     */
    private fun addList() {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID, MyDatabase.RecipeReader.ListEntry.COLUMN_INGREDIENTS)

        val cursor : Cursor = db.query(
            "list",   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val itemIngredients = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val itemIngredient= getString(getColumnIndexOrThrow(MyDatabase.RecipeReader.ListEntry.COLUMN_INGREDIENTS))
                itemIngredients.add(itemIngredient)
            }
        }

        cursor.close()

        val textView = findViewById<TextView>(R.id.shopping_list)

        if (itemIngredients.isEmpty()) {
            textView.setText("Nákupný zoznam je prázdny")
        }
        else {
            var text = ""
            for (item in itemIngredients) {
                text = text + "\n" + item
            }
            textView.setText(text)
        }
    }
}