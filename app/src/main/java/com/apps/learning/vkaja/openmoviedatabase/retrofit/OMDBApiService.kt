package com.apps.learning.vkaja.openmoviedatabase.retrofit

import com.apps.learning.vkaja.openmoviedatabase.data.Model
import com.apps.learning.vkaja.openmoviedatabase.data.MovieDetails
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


interface OMDBApiService {

    companion object {
        fun create() : OMDBApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://omdbapi.com/")
                .build()
            return retrofit.create(OMDBApiService::class.java)
        }
    }

    @GET("?")
    fun searchMovies(@Query("s") s : String,
                      @Query("apikey") apikey : String,
                      @Query("type") type : String,
                      @Query("r") r: String) : Single<Model.Result>

    @GET("?")
    fun fetchMovieDetails(@Query("i") imdbId : String,
                     @Query("apikey") apikey : String,
                     @Query("type") type : String,
                     @Query("plot") plot : String,
                     @Query("r") r: String) : Single<MovieDetails>
}