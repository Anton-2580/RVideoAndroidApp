package com.example.api.navigation

import com.example.api.AppDestinations


sealed class HomeDestinations: AppDestinations {
    data object HomePage: HomeDestinations()
}
