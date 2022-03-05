package com.example.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private const val TAG = "MovieAdapter"
const val MOVIE_EXTRA = "MOVIE_EXTRA"
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


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivImage)
        private val ivPlay = itemView.findViewById<ImageView>(R.id.ivPlayImage)

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            if (movie.vote > 5) {
            Glide.with(context).load(R.drawable.play).into(ivPlay) }


            if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(context)
                .load(movie.postImageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCornersTransformation(30,0))
                .into(ivPoster)
            } else if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context)
                    .load(movie.backImageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .centerCrop()
                    .transform(RoundedCornersTransformation(30,0))
                    .into(ivPoster)
            }
        }

        override fun onClick(p0: View?) {
            // 1. Get notified of the movie selected or clicked
            val movie_tapped = movies[adapterPosition]
//            Toast.makeText(context, movie_tapped.title, Toast.LENGTH_SHORT).show()
            // 2. use the intent system
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie_tapped)
//            val (first, second) p0 = Pair.create(tvTitle as TextView, "tvTitle")
//            val (first1, second1) = Pair.create(vPalette, "palette")
            context.startActivity(intent)
        }
    }
}
