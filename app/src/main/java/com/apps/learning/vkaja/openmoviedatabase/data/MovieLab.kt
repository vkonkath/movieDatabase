package com.apps.learning.vkaja.openmoviedatabase.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.apps.learning.vkaja.openmoviedatabase.db.MovieBaseHelper
import com.apps.learning.vkaja.openmoviedatabase.db.MovieCursorWrapper
import com.apps.learning.vkaja.openmoviedatabase.db.MovieDbSchema.MovieTable
import java.util.*

class MovieLab private constructor(context : Context) {
    companion object : SingletonHolder<MovieLab, Context>(::MovieLab)
    private val mContext = context.applicationContext
    private val mDatabase : SQLiteDatabase = MovieBaseHelper(mContext).writableDatabase

    fun getMoviesList(): ArrayList<MovieDetails> {
        val movies = ArrayList<MovieDetails>()
        val cursorWrapper = queryMovies(null, null)
        //Here cursorWrapper and movieCursorWrapper are same to avoid
        //name shadow warning we are giving a new name movieCursorWrapper
        cursorWrapper.use { movieCursorWrapper ->
            movieCursorWrapper.moveToFirst()
            while (!movieCursorWrapper.isAfterLast) {
                movies.add(movieCursorWrapper.getMovie())
                movieCursorWrapper.moveToNext()
            }
        }
        return movies
    }

    fun getMovie(mId : UUID) : MovieDetails? {
        val cursorWrapper = queryMovies(MovieTable.Cols.UUID + " =?", arrayOf(mId.toString()))
        //Here cursorWrapper and movieCursorWrapper are same to avoid
        // name shadow warning we are giving a new name movieCursorWrapper
        cursorWrapper.use { movieCursorWrapper ->
            if(movieCursorWrapper.count == 0) {
                return null
            }

            movieCursorWrapper.moveToFirst()
            return movieCursorWrapper.getMovie()
        }
    }

    fun addMovie(movie : MovieDetails) {
        mDatabase.insert(MovieTable.NAME, null,
            getContentValues(movie))
    }

    fun updateMovie(movie : MovieDetails) {

        mDatabase.update(MovieTable.NAME,
            getContentValues(movie),
            MovieTable.Cols.UUID + " =?",
            arrayOf(movie.mId.toString()))

    }

    fun deleteMovie(movie : MovieDetails) {
        mDatabase.delete(MovieTable.NAME, MovieTable.Cols.UUID + " =?", arrayOf(movie.mId.toString()))
    }

    private fun getContentValues(movie : MovieDetails) : ContentValues {
        val contentValues = ContentValues()
        contentValues.put(MovieTable.Cols.UUID, movie.mId.toString())
        contentValues.put(MovieTable.Cols.TITLE, movie.title)
        contentValues.put(MovieTable.Cols.YEAR, movie.year)
        contentValues.put(MovieTable.Cols.IMDBID, movie.imdbID)
        contentValues.put(MovieTable.Cols.DIRECTOR, movie.director)
        contentValues.put(MovieTable.Cols.PLOT, movie.plot)
        contentValues.put(MovieTable.Cols.POSTER, movie.poster)
        contentValues.put(MovieTable.Cols.RATED, movie.rated)
        contentValues.put(MovieTable.Cols.GENRE, movie.genre)
        return contentValues
    }

    private fun queryMovies(whereClause : String?, whereargs : Array<String>?) : MovieCursorWrapper {
        return MovieCursorWrapper(mDatabase.query(MovieTable.NAME, null, whereClause, whereargs, null, null, null))
    }
}