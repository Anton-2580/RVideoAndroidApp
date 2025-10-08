package com.example.impl

import androidx.navigation.NavHostController
import com.example.api.AppDestinations
import com.example.api.Navigator
import com.example.api.toSerializable


class NavigatorImpl(
    private val navController: NavHostController,
): Navigator {
    override fun navigate(destinations: AppDestinations) {
        navController.navigate(destinations.toSerializable())
    }

    override fun popBack() {
        navController.popBackStack()
    }
}