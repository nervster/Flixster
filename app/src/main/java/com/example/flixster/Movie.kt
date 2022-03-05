package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray


@Parcelize
data class Movie(
    val movieId: Int,
    val title: String,
    val overview: String,
    private val posterPath: String,
    private val backdropPath: String,
    val vote: Double
) : Parcelable {
    @IgnoredOnParcel
    val postImageUrl = "https://image.tmdb.org/t/p/w342$posterPath"
    @IgnoredOnParcel
    val backImageUrl = "https://image.tmdb.org/t/p/w342$backdropPath"
    // calls method on class without an instance
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieId = movieJson.getInt("id"),
                        title = movieJson.getString("title"),
                        overview = movieJson.getString("overview"),
                        posterPath = movieJson.getString("poster_path"),
                        backdropPath = movieJson.getString("backdrop_path"),
                        vote = movieJson.getDouble("vote_average")
                    )
                )
            }
            return movies
        }

    }
}
