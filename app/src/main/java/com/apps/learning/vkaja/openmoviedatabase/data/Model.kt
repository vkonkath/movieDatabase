package com.apps.learning.vkaja.openmoviedatabase.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

object Model {

    data class Result(@SerializedName("Search") val search : List<Movie>?)

    @Parcelize
    data class Movie (@SerializedName("Title") val title : String = "",
                 @SerializedName("Year") val year : Int,
                 @SerializedName("Type") val type : String = "",
                 @SerializedName("imdbID") val imdbID : String = "",
                 @SerializedName("Poster") val poster : String = "") : Parcelable
}