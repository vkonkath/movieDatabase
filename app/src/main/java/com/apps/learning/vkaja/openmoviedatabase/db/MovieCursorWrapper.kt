package com.apps.learning.vkaja.openmoviedatabase.db

import android.database.Cursor
import android.database.CursorWrapper
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import java.util.*

class MovieCursorWrapper(cursor : Cursor) : CursorWrapper(cursor) {

    fun getMovie() : MovieDetails {
        return MovieDetails(mId = UUID.fromString(getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.UUID))),
                title = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.TITLE)),
                year = getInt(getColumnIndex(MovieDbSchema.MovieTable.Cols.YEAR)),
                imdbID = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.IMDBID)),
                director = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.DIRECTOR)),
                plot = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.PLOT)),
                poster = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.POSTER)),
                rated = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.RATED)),
                genre = getString(getColumnIndex(MovieDbSchema.MovieTable.Cols.GENRE))
        )
    }
}