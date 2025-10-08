package com.example.impl.navigation

import com.example.api.AppDestinations
import com.example.api.navigation.HomeDestinations
import kotlinx.serialization.Serializable


@Serializable
sealed class SerializableHomeDestinations: AppDestinations {
    @Serializable
    data object HomePage: SerializableHomeDestinations()
}


fun HomeDestinations.toSerializable(): SerializableHomeDestinations = when (this) {
    HomeDestinations.HomePage -> SerializableHomeDestinations.HomePage
}

fun SerializableHomeDestinations.toDomain() = when (this) {
    SerializableHomeDestinations.HomePage -> HomeDestinations.HomePage
}