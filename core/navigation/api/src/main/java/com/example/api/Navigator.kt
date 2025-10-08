package com.example.api


interface Navigator {
    fun navigate(destinations: AppDestinations)
    fun popBack()
}