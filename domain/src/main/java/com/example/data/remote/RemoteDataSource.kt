package com.example.data.remote

import com.example.domain.entity.Movies
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPopularMovies(page: Int): Response<Movies> {
        return apiService.getPopular(page = page).asMovies()
    }

    suspend fun getNowPlaying(page: Int): Response<Movies> {
        return apiService.getNowPlaying(page = page).asMovies()
    }

    suspend fun getUpcoming(page: Int): Response<Movies> {
        return apiService.getUpcoming(page = page).asMovies()
    }

}