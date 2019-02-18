package com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import com.apps.learning.vkaja.openmoviedatabase.data.ReactionDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*
import java.util.*

class ReactionHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val mTextView: TextView = itemView.movie_title
    val mIamgeView : ImageView = itemView.list_item_movie_image
    val mDateTextView: TextView = itemView.movie_year
    lateinit var uuid : UUID

    fun bindReaction(reaction : ReactionDetails, listener : (ReactionDetails, Int) -> Unit, pos:Int) {
        uuid = reaction.mId
        mTextView.text = reaction.title
        mDateTextView.text = "${reaction.year}"
        Picasso.get().load(reaction.poster).into(mIamgeView)
        itemView.setOnClickListener{listener(reaction, pos)}
    }
}