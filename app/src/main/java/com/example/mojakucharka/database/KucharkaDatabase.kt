package com.example.mojakucharka.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class KucharkaDatabase: RoomDatabase() {
    abstract val recipeDao : RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: KucharkaDatabase? = null

        fun getInstance(context: Context): KucharkaDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KucharkaDatabase::class.java,
                        "recipes"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}