package com.example.mojakucharka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.AndroidViewModel

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openAdd(view: View) {
        val intent = Intent(this, AddRecipe::class.java)
        startActivity(intent)
    }

    fun openRecipes(view: View) {
        val intent = Intent(this, Recipes::class.java)
        startActivity(intent)
    }

    fun openShoppingList(view: View) {
        val intent = Intent(this, ShoppingList::class.java)
        startActivity(intent)
    }


}