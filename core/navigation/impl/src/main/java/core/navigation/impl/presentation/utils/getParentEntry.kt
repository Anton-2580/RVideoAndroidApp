package core.navigation.impl.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import core.navigation.impl.domain.ContentGraph


@Composable
fun getParentEntry(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
): NavBackStackEntry {
    return remember(backStackEntry) {
        navController.getBackStackEntry(ContentGraph)
    }
}