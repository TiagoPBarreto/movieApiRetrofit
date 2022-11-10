package com.example.movieapiretrofit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieapiretrofit.ui.DetailsMovieActivity
import com.example.movieapiretrofit.R
import com.example.movieapiretrofit.databinding.ItemRowBinding
import com.example.movieapiretrofit.response.MoviesListResponse
import com.example.movieapiretrofit.utils.Constants.POSTER_BASE_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private lateinit var binding: ItemRowBinding
    private lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRowBinding.inflate(inflater,parent,false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: MoviesListResponse.Result){
            binding.apply {
                tvMovieName.text = item.title
                tvDate.text = item.releaseDate
                tvRate.text = item.voteAverage.toString()
                val moviePosterURL = POSTER_BASE_URL + item.posterPath
                imgMovie.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvlang.text = item.originalLanguage
                root.setOnClickListener {
                    val intent = Intent(context, DetailsMovieActivity::class.java)
                    intent.putExtra("id",item.id)
                    context.startActivity(intent)
                }
            }
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<MoviesListResponse.Result>(){
        override fun areItemsTheSame(oldItem: MoviesListResponse.Result,newItem: MoviesListResponse.Result ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesListResponse.Result,newItem: MoviesListResponse.Result): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}