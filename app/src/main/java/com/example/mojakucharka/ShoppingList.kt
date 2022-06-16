package com.example.mojakucharka

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.mojakucharka.database.MyDatabase
import org.w3c.dom.Text

class ShoppingList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        addList()
    }

    fun clearList(view: View) {
        val dbHelper = MyDatabase(this)
        val db = dbHelper.readableDatabase
        // Define 'where' part of query.
        val selection = "${MyDatabase.RecipeReader.ListEntry.COLUMN_INGREDIENTS} LIKE ?"
        // Specify arguments in placeholder order.
        val text = findViewById<TextView>(R.id.shopping_list)
        val selectionArgs = arrayOf(text.text.toString())
        // Issue SQL statement.
        val deletedRows = db.delete(MyDatabase.RecipeReader.ListEntry.TABLE_NAME, selection, selectionArgs)

        val toast = Toast.makeText(applicationContext, "Vymazané", Toast.LENGTH_LONG)
        toast.show()

        finish()
    }

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
            textView.setText(itemIngredients[0])
        }
    }
}