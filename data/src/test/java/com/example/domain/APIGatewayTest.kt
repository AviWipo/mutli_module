package com.example.domain

import com.example.domain.entity.Movies
import com.example.domain.entity.NetworkMovie
import com.example.domain.gateway.APIGateway
import com.example.domain.util.Constants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Response

class APIGatewayTest {

    private val apiGateway: APIGateway = mockk()

    @Test
    fun `getPopularMovies should return a successful response with correct movie data`() = runBlocking {
        // Mocked data
        val mockedMovies = Movies(
            results = listOf(
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
            ),
            currentPage = 1,
            totalPages = 10
        )
        val mockedResponse = Response.success(mockedMovies)

        // Mock behavior
        coEvery { apiGateway.getPopularMovies(any()) } returns mockedResponse

        // Invoke
        val response = apiGateway.getPopularMovies(1)

        // Verify
        assertNotNull(response)
        assertEquals(200, response.code())
        assertEquals(mockedMovies, response.body())

        // Validate movies data
        val movies = response.body()
        assertNotNull(movies)
        assertEquals(1, movies!!.currentPage)
        assertEquals(10, movies.totalPages)
        assertEquals(2, movies.results.size)

        // Validate first movie
        val firstMovie = movies.results[0]
        assertEquals(1, firstMovie.id)
        assertEquals("Movie 1", firstMovie.title)
        assertEquals("An exciting movie.", firstMovie.overview)
        assertEquals("${Constants.POSTER_URL}/poster1.jpg", firstMovie.posterUrl)
        assertEquals(8.5, firstMovie.voteAverage!!, 0.0)

        // Validate second movie
        val secondMovie = movies.results[1]
        assertEquals(2, secondMovie.id)
        assertEquals("Movie 2", secondMovie.title)
        assertEquals("Another thrilling movie.", secondMovie.overview)
        assertEquals("${Constants.POSTER_URL}/poster2.jpg", secondMovie.posterUrl)
        assertEquals(null, secondMovie.voteAverage)
    }

    @Test
    fun `getNowPlayingMovies should return an error response`() = runBlocking {
        // Mocked error response
        val errorResponse = Response.error<Movies>(404, mockk(relaxed = true))

        // Mock behavior
        coEvery { apiGateway.getNowPlayingMovies(any()) } returns errorResponse

        // Invoke
        val response = apiGateway.getNowPlayingMovies(1)

        // Verify
        assertNotNull(response)
        assertEquals(404, response.code())
        assertEquals(null, response.body())
    }

    @Test
    fun `getUpcomingMovies should return a successful response`() = runBlocking {
        // Mocked data
        val mockedMovies = Movies(
            results = listOf(
                NetworkMovie(
                    id = 3,
                    title = "Upcoming Movie 1",
                    overview = "An anticipated movie.",
                    posterPath = "/poster3.jpg",
                    voteAverage = 9.0
                )
            ),
            currentPage = 2,
            totalPages = 5
        )
        val mockedResponse = Response.success(mockedMovies)

        // Mock behavior
        coEvery { apiGateway.getUpcomingMovies(any()) } returns mockedResponse

        // Invoke
        val response = apiGateway.getUpcomingMovies(2)

        // Verify
        assertNotNull(response)
        assertEquals(200, response.code())
        assertEquals(mockedMovies, response.body())

        // Validate movie data
        val movies = response.body()
        assertNotNull(movies)
        assertEquals(2, movies!!.currentPage)
        assertEquals(5, movies.totalPages)
        assertEquals(1, movies.results.size)

        val firstMovie = movies.results[0]
        assertEquals(3, firstMovie.id)
        assertEquals("Upcoming Movie 1", firstMovie.title)
        assertEquals("An anticipated movie.", firstMovie.overview)
        assertEquals("${Constants.POSTER_URL}/poster3.jpg", firstMovie.posterUrl)
        assertEquals(9.0, firstMovie.voteAverage!!, 0.0)
    }
}