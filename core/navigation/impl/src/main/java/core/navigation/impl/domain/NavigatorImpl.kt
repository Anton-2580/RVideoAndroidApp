package core.navigation.impl.domain

import androidx.navigation.NavHostController
import core.navigation.api.domain.AppDestinationsSerializable
import core.navigation.api.domain.Navigator


class NavigatorImpl(
    private val navController: NavHostController,
): Navigator {
    override fun navigate(destination: AppDestinationsSerializable) {
        navController.navigate(destination)
    }

    override fun popBack() {
        navController.popBackStack()
    }
}