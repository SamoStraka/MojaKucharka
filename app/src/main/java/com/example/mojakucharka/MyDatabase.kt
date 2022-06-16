package com.example.mojakucharka

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.annotation.Nullable

class MyDatabase(@Nullable context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${RecipeReader.RecipeEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${RecipeReader.RecipeEntry.COLUMN_NAME} TEXT," +
                "${RecipeReader.RecipeEntry.COLUMN_INGREDIENTS} TEXT," +
                "${RecipeReader.RecipeEntry.COLUMN_INSTRUCTIONS} TEXT)"
    private val SQL_CREATE_LIST =
        "CREATE TABLE ${RecipeReader.ListEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${RecipeReader.ListEntry.COLUMN_INGREDIENTS} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${RecipeReader.RecipeEntry.TABLE_NAME}"
    private val SQL_DELETE_LIST = "DROP TABLE IF EXISTS ${RecipeReader.RecipeEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_LIST)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(SQL_DELETE_LIST)
        onCreate(db)
    }

    object RecipeReader {
        // Table contents are grouped together in an anonymous object.
        object RecipeEntry : BaseColumns {
            const val TABLE_NAME = "recipes"
            const val COLUMN_NAME = "name"
            const val COLUMN_INGREDIENTS = "ingredients"
            const val COLUMN_INSTRUCTIONS = "instructions"
        }
        object ListEntry : BaseColumns {
            const val TABLE_NAME = "list"
            const val COLUMN_INGREDIENTS = "ingredients"
        }
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "recipes.db"
    }
}