package com.apps.learning.vkaja.openmoviedatabase.db

class MovieDbSchema {

    object MovieTable {
        val NAME : String = "movies"

        object Cols {
            val UUID : String = "uuid"
            val TITLE : String = "title"
            val YEAR : String = "year"
            val IMDBID : String = "imdbId"
            val DIRECTOR : String = "director"
            val PLOT : String = "plot"
            val POSTER : String = "poster"
            val RATED : String = "rated"
            val GENRE : String = "genre"
        }
    }
}
