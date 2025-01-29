package com.example.data.remote

import com.example.data.remote.response.Movie
import com.example.data.remote.response.MoviesResponse
import com.example.domain.entity.Movies
import com.example.domain.entity.NetworkMovie
import retrofit2.Response


fun Response<MoviesResponse>.asMovies(): Response<Movies> = Response.success(body()?.let {
    Movies(
        results = it.results.map { movies -> movies.asMovie() },
        currentPage = 1,
        totalPages = it.totalPages,
    )
})

fun Movie.asMovie() = NetworkMovie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    voteAverage = voteAverage,
)