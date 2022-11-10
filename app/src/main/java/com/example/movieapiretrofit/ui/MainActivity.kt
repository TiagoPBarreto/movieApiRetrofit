package com.example.movieapiretrofit.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapiretrofit.adapters.MoviesAdapter
import com.example.movieapiretrofit.api.ApiService
import com.example.movieapiretrofit.api.ApliClient
import com.example.movieapiretrofit.databinding.ActivityMainBinding
import com.example.movieapiretrofit.response.MoviesListResponse
import com.google.android.gms.common.api.UnsupportedApiCallException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val moviesAdapter by lazy { MoviesAdapter() }
    private val api: ApiService by lazy {
        ApliClient().getClient().create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Init Views
        binding.apply {
            prgBarMovie.visibility = View.VISIBLE
            val callMoviesApi = api.getPopularMovie(1)
            callMoviesApi.enqueue(object : Callback<MoviesListResponse>{
                override fun onResponse(
                    call: Call<MoviesListResponse>,response: Response<MoviesListResponse>) {
                    prgBarMovie.visibility = View.GONE
                    when(response.code()){
                        in 200..299->{
                            response.body()?.let { itBody->
                                itBody.results.let { itDate->
                                    if (itDate.isNotEmpty()){
                                        moviesAdapter.differ.submitList(itDate)
                                        //RecyclerVew MainActivity
                                        rvMovie.apply {
                                            layoutManager = LinearLayoutManager(this@MainActivity)
                                            adapter = moviesAdapter
                                        }
                                    }
                                }
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

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
}