package com.example.data

import com.example.data.remote.RemoteDataSource
import com.example.domain.entity.Movies
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NetworkRepositoryTests {

    private lateinit var dataSource: RemoteDataSource
    private lateinit var repository: NetworkRepository

    @Before
    fun setUp() {
        dataSource = mockk()
        val response: Response<Movies> = mockk()
        coEvery { dataSource.getPopularMovies(0) }.answers { response }
        coEvery { dataSource.getUpcoming(any()) }.answers { response }
        coEvery { dataSource.getNowPlaying(any()) }.answers { response }
        repository = NetworkRepository(dataSource)
    }

    @Test
    fun test_getPopularMovies() = runBlocking {
        repository.getPopularMovies(0)
        coVerify(exactly = 1) { dataSource.getPopularMovies(0) }
    }
    @Test
    fun test_getUpcoming() = runBlocking {
        repository.getUpcomingMovies(0)
        coVerify(exactly = 1) { dataSource.getUpcoming(0) }
    }
    @Test
    fun test_getNowPlaying() = runBlocking {
        repository.getNowPlayingMovies(0)
        coVerify(exactly = 1) { dataSource.getNowPlaying(0) }
    }
}