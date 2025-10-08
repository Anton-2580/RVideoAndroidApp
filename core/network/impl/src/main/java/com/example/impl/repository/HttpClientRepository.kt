package com.example.impl.repository

import com.example.api.Result
import com.example.api.data.HttpClient
import com.example.api.data.statuses.NetworkStatus
import com.example.impl.data.delete
import com.example.impl.data.get
import com.example.impl.data.post
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.KClass
import io.ktor.client.HttpClient as KtorHttpClient


class HttpClientRepository(
    private val httpClient: KtorHttpClient,
): HttpClient() {
    override suspend fun <Response : Any> get(
        route: String,
        queryParameters: Map<String, Any?>,
        type: KClass<Response>,
    ): Result<NetworkStatus, Response> =
        httpClient.get(route, queryParameters, getType(type))

    override suspend fun <Request : Any, Response : Any> post(
        route: String,
        body: Request,
        type: KClass<Response>,
        bodyType: KClass<Request>,
    ): Result<NetworkStatus, Response> =
        httpClient.post(route, body, getType(type), getType(bodyType))

    override suspend fun <Response : Any> delete(
        route: String,
        queryParameters: Map<String, Any?>,
        type: KClass<Response>,
    ): Result<NetworkStatus, Response> =
        httpClient.delete(route, queryParameters, getType(type))


    private fun getType(type: KClass<*>) = TypeInfo(type, type.java)
}