package com.lloyds.feature_nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lloyds.feature_nowplaying.NowPlayingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val nowPlayingRepository: NowPlayingRepository,
) : ViewModel() {

    val nowPlayingList = nowPlayingRepository.nowPlaying().cachedIn(viewModelScope)

}
