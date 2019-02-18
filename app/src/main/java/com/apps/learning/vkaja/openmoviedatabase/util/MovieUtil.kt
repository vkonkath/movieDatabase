package com.apps.learning.vkaja.openmoviedatabase.util

import com.apps.learning.vkaja.openmoviedatabase.retrofit.OMDBApiService

object MovieUtil {
    var pos : String = "position"
    val returnType = "json"
    val searchType = "movie"
    val apikey = "6f8d93f7"
    val movie = "Movie"
    val plot = "full"
    val movieInfo = "movieInfo"

    val omdbApiService by lazy {
        OMDBApiService.create()
    }
}