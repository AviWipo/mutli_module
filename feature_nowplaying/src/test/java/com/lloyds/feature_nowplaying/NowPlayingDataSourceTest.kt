package com.lloyds.feature_nowplaying

import androidx.paging.PagingSource
import com.example.domain.entity.Movies
import com.example.domain.entity.NetworkMovie
import com.example.domain.usecase.NowPlayingUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NowPlayingDataSourceTest {

    @MockK
    private lateinit var nowPlayingUseCase: NowPlayingUseCase

    private lateinit var nowPlayingDataSource: NowPlayingDataSource

    @Before
    fun setUp() {
        nowPlayingUseCase = mockk()
        nowPlayingDataSource = NowPlayingDataSource(nowPlayingUseCase)
    }


    @Test
    fun `load returns page when successful`() = runBlocking {
        val movies = listOf(
            NetworkMovie(
                id = 1,
                title = "Movie 1",
                overview = "An exciting movie.",
                posterPath = "/poster1.jpg",
                voteAverage = 8.5
            ),
            NetworkMovie(
                id = 2,
                title = "Movie 2",
                overview = "Another thrilling movie.",
                posterPath = "/poster2.jpg",
                voteAverage = null
            )
        )
        val mockedMovies = Movies(
            results = movies,
            currentPage = 1,
            totalPages = 10
        )
        val response = Response.success(mockedMovies)
        coEvery { nowPlayingUseCase(1) }.returns(response)
        val result = nowPlayingDataSource.load(PagingSource.LoadParams.Append(1, 20, false))
        assert(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(movies, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
    }

    @Test
    fun `load returns error when API call fails`() = runBlocking {
        coEvery { nowPlayingUseCase(1) }.throws(RuntimeException("API failure"))
        val result = nowPlayingDataSource.load(PagingSource.LoadParams.Append(1, 20, false))
        assert(result is PagingSource.LoadResult.Error)
        val error = result as PagingSource.LoadResult.Error
        assert(error.throwable is RuntimeException)
        assertEquals("API failure", error.throwable.message)
    }
}
