package core.navigation.api


interface Navigator {
    fun navigate(destinations: AppDestinations)
    fun popBack()
}