package com.apps.learning.vkaja.openmoviedatabase.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apps.learning.vkaja.openmoviedatabase.R
import com.apps.learning.vkaja.openmoviedatabase.data.Model
import com.apps.learning.vkaja.openmoviedatabase.ui.viewHolder.SearchMovieHolder

class SearchMovieAdapter(var movieList:List<Model.Movie>, val mActivity : Activity, val listener : (Model.Movie, Int) -> Unit) : RecyclerView.Adapter<SearchMovieHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchMovieHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mActivity)
        val view : View = layoutInflater.inflate(R.layout.list_item_movie, p0, false)
        return SearchMovieHolder(view)
    }

    override fun onBindViewHolder(p0: SearchMovieHolder, p1: Int) {
        val movie : Model.Movie = movieList[p1]
        p0.bindMovie(movie, listener, p1)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<Model.Movie>) {
        movieList = movies
    }
}