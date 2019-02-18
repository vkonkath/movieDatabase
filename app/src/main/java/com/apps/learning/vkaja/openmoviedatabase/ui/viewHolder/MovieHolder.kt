package com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*
import java.util.*

class MovieHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val mTextView: TextView = itemView.movie_title
    val mIamgeView : ImageView = itemView.list_item_movie_image
    val mDateTextView: TextView = itemView.movie_year
    lateinit var uuid : UUID

    fun bindMovie(movie : MovieDetails, listener : (MovieDetails, Int) -> Unit, pos:Int) {
        uuid = movie.mId
        mTextView.text = movie.title
        mDateTextView.text = "${movie.year}"
        Picasso.get().load(movie.poster).into(mIamgeView)
        itemView.setOnClickListener{listener(movie, pos)}
    }
}