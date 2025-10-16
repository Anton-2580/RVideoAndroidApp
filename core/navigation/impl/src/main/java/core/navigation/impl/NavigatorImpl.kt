package core.navigation.impl

import androidx.navigation.NavHostController
import core.navigation.api.AppDestinations
import core.navigation.api.Navigator
import core.navigation.api.toSerializable


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