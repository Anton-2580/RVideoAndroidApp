package com.example.api.data

import com.example.api.Result
import com.example.api.data.statuses.NetworkStatus
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf


abstract class HttpClient {
    abstract suspend fun <Response: Any> get(
        route: String,
        queryParameters: Map<String, Any?>,
        kClass: KClass<Response>,
        kType: KType,
    ): Result<NetworkStatus, Response>

    abstract suspend fun <Request: Any, Response: Any> post(
        route: String,
        body: Request,
        kClass: KClass<Response>,
        kType: KType,
        bodyType: KClass<Request>,
        bodyKType: KType,
    ): Result<NetworkStatus, Response>

    abstract suspend fun <Response: Any> delete(
        route: String,
        queryParameters: Map<String, Any?>,
        kClass: KClass<Response>,
        kType: KType,
    ): Result<NetworkStatus, Response>


    suspend inline fun <reified Response: Any> get(
        route: String,
        queryParameters: Map<String, Any?> = mapOf(),
    ): Result<NetworkStatus, Response> = queryWithTypes(Query.GET, route, queryParameters)

    suspend inline fun <reified Request: Any, reified Response: Any> post(
        route: String,
        body: Request,
    ): Result<NetworkStatus, Response> = queryWithTypes(Query.POST, route, body)

    suspend inline fun <reified Response: Any> delete(
        route: String,
        queryParameters: Map<String, Any?> = mapOf(),
    ): Result<NetworkStatus, Response> = queryWithTypes(Query.DELETE, route, queryParameters)

    suspend inline fun <reified Response: Any> queryWithTypes(
        query: Query,
        route: String,
        queryParameters: Map<String, Any?> = mapOf(),
    ): Result<NetworkStatus, Response> = when (query) {
        Query.GET -> {
            get(
                route,
                queryParameters,
                kClass = getKClass<Response>(),
                kType = typeOf<Response>(),
            )
        }
        Query.DELETE -> {
            delete(
                route,
                queryParameters,
                kClass = getKClass<Response>(),
                kType = typeOf<Response>(),
            )
        }

        else -> Result.Error(NetworkStatus.Unknown)
    }

    suspend inline fun <reified Response: Any, reified Request: Any> queryWithTypes(
        query: Query,
        route: String,
        body: Request
    ): Result<NetworkStatus, Response> = when (query) {
        Query.POST -> {
            post(
                route,
                body,
                kClass = getKClass<Response>(),
                kType = typeOf<Response>(),
                bodyType = getKClass<Request>(),
                bodyKType = typeOf<Request>(),
            )
        }
        else -> Result.Error(NetworkStatus.Unknown)
    }

    inline fun <reified T : Any> getKClass(): KClass<T> = T::class
}