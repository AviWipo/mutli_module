package com.example.domain.gateway

import com.example.domain.entity.Movies
import retrofit2.Response

interface APIGateway {
    suspend fun getPopularMovies(page: Int): Response<Movies>
    suspend fun getNowPlayingMovies(page: Int): Response<Movies>
    suspend fun getUpcomingMovies(page: Int): Response<Movies>
}