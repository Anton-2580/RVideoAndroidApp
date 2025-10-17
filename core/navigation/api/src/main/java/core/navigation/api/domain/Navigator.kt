package core.navigation.api.domain


interface Navigator {
    fun navigate(destination: AppDestinationsSerializable)
    fun popBack()
}