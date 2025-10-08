package com.example.api.data

import com.example.api.Result
import com.example.api.data.statuses.NetworkStatus
import kotlin.reflect.KClass


abstract class HttpClient {
    abstract suspend fun <Response: Any> get(
        route: String,
        queryParameters: Map<String, Any?>,
        type: KClass<Response>,
    ): Result<NetworkStatus, Response>

    abstract suspend fun <Request: Any, Response: Any> post(
        route: String,
        body: Request,
        type:  KClass<Response>,
        bodyType: KClass<Request>,
    ): Result<NetworkStatus, Response>

    abstract suspend fun <Response: Any> delete(
        route: String,
        queryParameters: Map<String, Any?>,
        type: KClass<Response>,
    ): Result<NetworkStatus, Response>


    suspend inline fun <reified Response: Any> get(
        route: String,
        queryParameters: Map<String, Any?> = mapOf(),
    ): Result<NetworkStatus, Response> =
        get(route, queryParameters, Response::class)

    suspend inline fun <reified Request: Any, reified Response: Any> post(
        route: String,
        body: Request,
    ): Result<NetworkStatus, Response> =
        post(route, body, Response::class, Request::class)

    suspend inline fun <reified Response: Any> delete(
        route: String,
        queryParameters: Map<String, Any?> = mapOf(),
    ): Result<NetworkStatus, Response> =
        delete(route, queryParameters, Response::class)
}