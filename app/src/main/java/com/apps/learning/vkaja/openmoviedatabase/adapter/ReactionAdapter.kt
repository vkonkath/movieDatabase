package com.apps.learning.vkaja.openmoviedatabase.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apps.learning.vkaja.openmoviedatabase.R
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import com.apps.learning.vkaja.openmoviedatabase.data.ReactionDetails
import com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder.MovieHolder
import com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder.ReactionHolder

class ReactionAdapter(var reactionList:List<ReactionDetails>, val mActivity : Activity, val listener : (ReactionDetails, Int) -> Unit) : RecyclerView.Adapter<ReactionHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReactionHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mActivity)
        val view : View = layoutInflater.inflate(R.layout.list_item_movie, p0, false)
        return ReactionHolder(view)
    }

    override fun onBindViewHolder(p0: ReactionHolder, p1: Int) {
        val movie : ReactionDetails = reactionList[p1]
        p0.bindReaction(movie, listener, p1)
    }

    override fun getItemCount(): Int {
        return reactionList.size
    }

    fun setMovies(movies: List<ReactionDetails>) {
        reactionList = movies
    }
}