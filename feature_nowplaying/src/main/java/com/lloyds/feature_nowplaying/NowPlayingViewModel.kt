package com.lloyds.feature_nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val nowPlayingProvider: NowPlayingProvider,
) : ViewModel() {

    val nowPlayingList = nowPlayingProvider.nowPlaying().cachedIn(viewModelScope)

}
