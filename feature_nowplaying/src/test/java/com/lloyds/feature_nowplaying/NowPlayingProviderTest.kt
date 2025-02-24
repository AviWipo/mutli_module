package com.lloyds.feature_nowplaying

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.domain.entity.Movies
import com.example.domain.entity.NetworkMovie
import com.example.domain.usecase.NowPlayingUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NowPlayingProviderTest {

    @MockK
    private lateinit var nowPlayingUseCase: NowPlayingUseCase
    private lateinit var nowPlayingProvider: NowPlayingProvider
    private val testDispatcher = StandardTestDispatcher()
//    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        nowPlayingUseCase = mockk()
        nowPlayingProvider = NowPlayingProvider(nowPlayingUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `nowPlaying returns PagingData successfully`() = runTest {
        val movieList = listOf(NetworkMovie(
            id = 1,
            title = "Movie 1",
            overview = "An exciting movie.",
            posterPath = "/poster1.jpg",
            voteAverage = 8.5
        ))
        val pagingData = PagingData.from(movieList)

        val mockedMovies = Movies(
            results = movieList,
            currentPage = 1,
            totalPages = 10
        )
        val response = Response.success(mockedMovies)
        coEvery { nowPlayingUseCase(any()) }.returns(response)

        val result = nowPlayingProvider.nowPlaying().first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<NetworkMovie>() {
                override fun areItemsTheSame(oldItem: NetworkMovie, newItem: NetworkMovie): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: NetworkMovie, newItem: NetworkMovie): Boolean =
                    oldItem == newItem
            },
            updateCallback = NoopListUpdateCallback(),
            workerDispatcher = testDispatcher
        )

        // Call submitData within a launched coroutine
        launch { differ.submitData(result) }

        testDispatcher.scheduler.runCurrent() // Run any pending tasks
        testDispatcher.scheduler.advanceUntilIdle() // Ensure all coroutines complete
        assertEquals(movieList, differ.snapshot().items)
    }
}

class NoopListUpdateCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}