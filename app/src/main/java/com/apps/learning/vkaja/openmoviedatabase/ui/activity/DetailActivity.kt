package com.apps.learning.vkaja.openmoviedatabase.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.apps.learning.vkaja.openmoviedatabase.R
import com.apps.learning.vkaja.openmoviedatabase.data.Model
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import com.apps.learning.vkaja.openmoviedatabase.data.MovieLab
import com.apps.learning.vkaja.openmoviedatabase.util.MovieUtil
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class DetailActivity : AppCompatActivity(), CoroutineScope {
    var movie : Model.Movie? = null
    var moveInfo : MovieDetails? = null
    var disposable : Disposable? = null
    private var progressBar: FrameLayout? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private lateinit var mImageView: ImageView
    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        movie = intent.getParcelableExtra(MovieUtil.movie)
        moveInfo = intent.getParcelableExtra(MovieUtil.movieInfo)
        progressBar = progressBarHolder
        if(movie != null) {
            convertMovie()
        }
        updateUI()
        fetchData()
    }

    private fun updateUI() {
        this.launch {
            title_view.text = getString(R.string.movie_header) + moveInfo?.title
            mImageView = image_view
            Picasso.get().load(moveInfo?.poster).into(mImageView)
            director_view.text = getString(R.string.director_header) + moveInfo?.director
            year_view.text=getString(R.string.year_header) + "${moveInfo?.year}"
            plot_view.text = getString(R.string.plot_header) + "${moveInfo?.plot}"
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
    }

    private fun showLoadingIndicator(message : String, title: String) {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration = 200
        progressBar?.animation = inAnimation
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration = 200
        progressBar?.animation = outAnimation
        progressBar?.visibility = View.GONE
    }

    private fun fetchData() {

        disposable = MovieUtil.omdbApiService.fetchMovieDetails(moveInfo!!.imdbID,
            MovieUtil.apikey,
            MovieUtil.searchType,
            MovieUtil.plot,
            MovieUtil.returnType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{showLoadingIndicator("Fetching details...", "Movie Detail")}
            .doFinally{hideLoadingIndicator()}
            .subscribe(
                {result -> showResult(result) },
                {error -> showError(error) }
            )
    }

    private fun showResult(result: MovieDetails) {
        val movieLab = MovieLab.getInstance(this)
        moveInfo = result
        moveInfo!!.mId = UUID.randomUUID()
        movieLab.updateMovie(movie = result)
        updateUI()
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, "Failure : ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }


    private fun convertMovie() {
        if (movie != null) {
            moveInfo = MovieDetails(title = movie!!.title,
                year = movie!!.year,
                imdbID = movie!!.imdbID,
                poster = movie!!.poster)
        }
    }

    fun addToFavorite(view : View) {
        if (moveInfo != null) {
            val movieLab = MovieLab.getInstance(this)
            movieLab.addMovie(moveInfo!!)
            setResult(Activity.RESULT_OK)
        }
    }

    fun deleteFromFavorite(view: View) {
        if (moveInfo != null) {
            val movieLab = MovieLab.getInstance(this)
            movieLab.deleteMovie(moveInfo!!)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

}
