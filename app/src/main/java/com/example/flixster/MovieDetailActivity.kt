package com.example.flixster

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers


private const val TAG = "MovieDetailActivity"
private const val YT_API_KEY ="AIzaSyBmoEdv7rmlSHBrvtSfUpP4tBIiDgcBzpw'"
private const val trailer_url = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MovieDetailActivity : YouTubeBaseActivity() {
//    private lateinit var ivMoviePoster: ImageView
    private lateinit var tvMovieTitle: TextView
    private lateinit var tvMovieDetail: TextView
    private lateinit var ratingBarMovie: RatingBar
    private lateinit var youtubeView: YouTubePlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // link layout to var
//        ivMoviePoster = findViewById<ImageView>(R.id.ivMoviePoster)
        tvMovieTitle = findViewById<TextView>(R.id.tvTitle) as TextView
        tvMovieDetail = findViewById<TextView>(R.id.tvOverview)
        ratingBarMovie = findViewById<RatingBar>(R.id.ratingBar)
        youtubeView = findViewById<YouTubePlayerView>(R.id.YTplayerView)

        // get data from intent
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")

        val client = AsyncHttpClient()
        client.get(trailer_url.format(movie.movieId), object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON data $json")
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0) {
                    Log.w(TAG, "No movie trailer available")
                    return
                }
                val movieTrailerJson = results.getJSONObject(0)
                val youtubeKey = movieTrailerJson.getString("key")
                initYoutubeKey(youtubeKey, movie.vote)
            }

        })


        // assign to layout

        tvMovieTitle.text = movie.title
        tvMovieDetail.text = movie.overview
        ratingBarMovie.rating = movie.vote.toFloat()


    }

    private fun initYoutubeKey(youtubeKey: String, vote: Double) {
        youtubeView.initialize(
            YT_API_KEY,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer,
                    b: Boolean
                ) {

                    // do any work here to cue video, play video, etc.
                    if (vote > 5) {
                        youTubePlayer.loadVideo(youtubeKey,10)
                    } else {
                        youTubePlayer.cueVideo(youtubeKey)
                    }

                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Log.e(TAG, youTubeInitializationResult.toString())
                }
            })

    }
}