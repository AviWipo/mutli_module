package com.example.common.items

import com.example.common.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    data object NowPlaying : NavigationItem("Now Playing", R.drawable.ic_baseline_airplay, "NowPlaying")
    data object Popular : NavigationItem("Popular", R.drawable.ic_baseline_trending_up, "Popular")
    data object Upcoming : NavigationItem("Upcoming", R.drawable.ic_baseline_watch_later, "Upcoming")
}
