package com.apps.learning.vkaja.openmoviedatabase.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieBaseHelper(context: Context, databaseName:String="movieBase.db", version:Int=1):SQLiteOpenHelper(context, databaseName, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table ${MovieDbSchema.MovieTable.NAME}" + "(" +
            "_id integer primary key autoincrement, "+
                MovieDbSchema.MovieTable.Cols.UUID + ", " +
                MovieDbSchema.MovieTable.Cols.TITLE + ", " +
                MovieDbSchema.MovieTable.Cols.YEAR + ", " +
                MovieDbSchema.MovieTable.Cols.IMDBID + ", " +
                MovieDbSchema.MovieTable.Cols.PLOT +", " +
                MovieDbSchema.MovieTable.Cols.POSTER +", " +
                MovieDbSchema.MovieTable.Cols.DIRECTOR + ", " +
                MovieDbSchema.MovieTable.Cols.RATED +", " +
                MovieDbSchema.MovieTable.Cols.GENRE +
            ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}