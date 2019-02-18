package com.apps.learning.vkaja.openmoviedatabase.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import com.apps.learning.vkaja.openmoviedatabase.R
import com.apps.learning.vkaja.openmoviedatabase.adapter.MovieAdapter
import com.apps.learning.vkaja.openmoviedatabase.adapter.ReactionAdapter
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import com.apps.learning.vkaja.openmoviedatabase.data.MovieLab
import com.apps.learning.vkaja.openmoviedatabase.ui.activity.DetailActivity
import com.apps.learning.vkaja.openmoviedatabase.util.MovieUtil
import kotlinx.android.synthetic.main.fraagment_favorite.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavoriteListFragment : Fragment(), CoroutineScope {

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mEmptyView : LinearLayout
    private lateinit var mAdapter: MovieAdapter
    private lateinit var rAdapter: ReactionAdapter
    private val MOVIE_INTENT_ACTIVITY : Int = 1
    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewOfLayout : View = inflater.inflate(R.layout.fraagment_favorite, container, false)
        mRecyclerView = viewOfLayout.favorite_recycle_view
        mEmptyView = viewOfLayout.empty_view
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return viewOfLayout
    }

    private fun updateUI() {
        this.launch {
            val movieLab : MovieLab = MovieLab.getInstance(activity!!.baseContext)
            val movies : List<MovieDetails> = movieLab.getMoviesList()
            if(movies.isNotEmpty()) {

                mRecyclerView.visibility = View.VISIBLE
                mEmptyView.visibility = View.GONE

                if(!::mAdapter.isInitialized) {
                    mAdapter = MovieAdapter(movies, activity!!) { movie : MovieDetails, pos : Int -> callDetailActivity(movie, pos) }
                    mRecyclerView.adapter = mAdapter
                } else {
                    mAdapter.setMovies(movies)
                    mAdapter.notifyDataSetChanged()
                }

            } else {
                mRecyclerView.visibility = View.GONE
                mEmptyView.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == MOVIE_INTENT_ACTIVITY) {
            updateUI()
        }
    }

    private fun callDetailActivity(movie:MovieDetails, pos:Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(MovieUtil.movieInfo, movie)
        intent.putExtra(MovieUtil.pos, pos)
        startActivityForResult(intent, MOVIE_INTENT_ACTIVITY)
    }


}