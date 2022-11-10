package com.example.movieapiretrofit.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Scale
import com.example.movieapiretrofit.R
import com.example.movieapiretrofit.api.ApiService
import com.example.movieapiretrofit.api.ApliClient
import com.example.movieapiretrofit.databinding.ActivityDetailsMovieBinding
import com.example.movieapiretrofit.response.MovieDetails
import com.example.movieapiretrofit.utils.Constants.POSTER_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsMovieActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsMovieBinding
    private val api: ApiService by lazy {
        ApliClient().getClient().create(ApiService::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId:Int = intent.getIntExtra("id",1)
        binding.apply {
            prgBarMovies.visibility = View.VISIBLE
            val  callMoviesApi = api.getMoviesDetails(movieId)
            callMoviesApi.enqueue(object : Callback<MovieDetails>{
                override fun onResponse(call: Call<MovieDetails>,response: Response<MovieDetails>) {
                    prgBarMovies.visibility = View.GONE
                    when(response.code()){
                        in 200..299->{
                            response.body()?.let {itBody->
                                val moviePosterURL = POSTER_BASE_URL + itBody.posterPath
                                imgMovie.load(moviePosterURL){
                                    crossfade(true )
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgMovieBack.load(moviePosterURL){
                                    crossfade(true)
                                    scale(Scale.FILL)
                                }
                                tvMovieTitle.text = itBody.title
                                tvMovieTagLine.text = itBody.tagline
                                tvMovieDateRelease.text = itBody.releaseDate
                                tvMovieRating.text = itBody.voteAverage.toString()
                                tvMovieRuntime.text = itBody.runtime.toString()
                                tvMovieBudget.text = itBody.budget.toString()
                                tvMovieRevenue.text = itBody.revenue.toString()
                                tvMovieOverview.text = itBody.overview
                            }
                        }
                        in 300..399->{
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499->{
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 500..599->{
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                }

            })
        }
    }
}