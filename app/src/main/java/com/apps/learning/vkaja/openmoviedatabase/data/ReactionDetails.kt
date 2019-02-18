package com.apps.learning.vkaja.openmoviedatabase.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ReactionDetails (var mId : UUID = UUID.randomUUID(),
                         @SerializedName("Title") val title : String = "",
                         @SerializedName("Year") val year : Int,
                         @SerializedName("imdbID") val imdbID : String = "",
                         @SerializedName("Director")val director : String = "",
                         @SerializedName("Plot") val plot : String = "",
                         @SerializedName("Poster") val poster : String = "",
                         @SerializedName("Rated") val rated : String = "",
                         @SerializedName("Genre") val genre : String = "") : Parcelable