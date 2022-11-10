package com.example.movieapiretrofit.api

import com.example.movieapiretrofit.response.MovieDetails
import com.example.movieapiretrofit.response.MoviesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int): Call<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMoviesDetails(@Path("movie_id")id:Int): Call<MovieDetails>
}