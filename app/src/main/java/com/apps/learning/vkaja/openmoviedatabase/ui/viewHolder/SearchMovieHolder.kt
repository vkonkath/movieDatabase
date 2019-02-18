package com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.apps.learning.vkaja.openmoviedatabase.data.Model
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*

class SearchMovieHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val mTextView: TextView = itemView.movie_title
    val mDateTextView: TextView = itemView.movie_year
    val mIamgeView : ImageView = itemView.list_item_movie_image


    fun bindMovie(movie : Model.Movie, listener : (Model.Movie, Int) -> Unit, pos:Int) {
        mTextView.text = movie.title
        mDateTextView.text = "${movie.year}"
        Picasso.get().load(movie.poster).into(mIamgeView)
        itemView.setOnClickListener{listener(movie, pos)}
    }
}