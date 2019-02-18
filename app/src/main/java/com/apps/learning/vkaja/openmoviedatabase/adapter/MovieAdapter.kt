package com.apps.learning.vkaja.openmoviedatabase.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apps.learning.vkaja.openmoviedatabase.R
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder.MovieHolder

class MovieAdapter(var movieList:List<MovieDetails>, val mActivity : Activity, val listener : (MovieDetails, Int) -> Unit) : RecyclerView.Adapter<MovieHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mActivity)
        val view : View = layoutInflater.inflate(R.layout.list_item_movie, p0, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(p0: MovieHolder, p1: Int) {
        val movie : MovieDetails = movieList[p1]
        p0.bindMovie(movie, listener, p1)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<MovieDetails>) {
        movieList = movies
    }
}