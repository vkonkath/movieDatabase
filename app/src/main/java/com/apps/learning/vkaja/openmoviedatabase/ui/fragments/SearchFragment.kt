package com.apps.learning.vkaja.openmoviedatabase.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.apps.learning.vkaja.openmoviedatabase.R
import com.apps.learning.vkaja.openmoviedatabase.adapter.SearchMovieAdapter
import com.apps.learning.vkaja.openmoviedatabase.data.Model
import com.apps.learning.vkaja.openmoviedatabase.ui.activity.DetailActivity
import com.apps.learning.vkaja.openmoviedatabase.util.MovieUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchFragment : Fragment(), CoroutineScope {

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mEditText: EditText
    private lateinit var mEmptyView : LinearLayout
    private lateinit var mAdapter: SearchMovieAdapter
    private lateinit var mSearchButton : Button
    var disposable : Disposable? = null
    var searchText : String? = null
    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewOfLayout : View = inflater.inflate(R.layout.fragment_main, container, false)
        mRecyclerView = viewOfLayout.movie_recycle_view
        mEmptyView = viewOfLayout.empty_view
        mSearchButton = viewOfLayout.search_btn
        mEditText = viewOfLayout.search_src_text
        updateUI()
        return viewOfLayout
    }

    fun updateUI() {
        mEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                searchText = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        mSearchButton.setOnClickListener { search() }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    fun search () {
        if(searchText == null) {
            Toast.makeText(activity, R.string.enter_movie_name, Toast.LENGTH_SHORT).show()
        }
        disposable = MovieUtil.omdbApiService.searchMovies(searchText!!,
            MovieUtil.apikey,
            MovieUtil.searchType,
            MovieUtil.returnType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {result -> showResult(result) },
                {error -> showError(error) }
            )
    }

    private fun showResult(result : Model.Result?) {
        val movies : List<Model.Movie>? = result?.search
        if (movies != null && !movies.isEmpty()) {
            this.launch {
                mRecyclerView.visibility = View.VISIBLE
                mEmptyView.visibility = View.GONE
                mRecyclerView.layoutManager = LinearLayoutManager(activity)
                if(!::mAdapter.isInitialized) {
                    mAdapter = SearchMovieAdapter(movies, activity!!) { movie : Model.Movie, pos : Int -> callDetailActivity(movie, pos) }
                    mRecyclerView.adapter = mAdapter
                } else {
                    mAdapter.setMovies(movies)
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
    }

    private fun callDetailActivity(movie : Model.Movie, pos : Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(MovieUtil.movie, movie)
        startActivity(intent)
    }

    fun showError(error : Throwable) {
        Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_SHORT).show()
    }
}