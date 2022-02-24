package com.example.flixster

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")

        val view = android.view.LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivImage)


        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(context).load(movie.postImageUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(ivPoster)
            } else if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context).load(movie.backImageUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(ivPoster)
            }


        }
    }
}
