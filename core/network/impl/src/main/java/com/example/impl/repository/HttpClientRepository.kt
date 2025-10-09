package com.example.impl.repository

import com.example.api.Result
import com.example.api.data.HttpClient
import com.example.api.data.statuses.NetworkStatus
import com.example.impl.data.delete
import com.example.impl.data.get
import com.example.impl.data.post
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.javaType
import io.ktor.client.HttpClient as KtorHttpClient


class HttpClientRepository(
    private val httpClient: KtorHttpClient,
): HttpClient() {
    override suspend fun <Response : Any> get(
        route: String,
        queryParameters: Map<String, Any?>,
        kClass: KClass<Response>,
        kType: KType
    ): Result<NetworkStatus, Response> =
        httpClient.get(
            route,
            queryParameters,
            getType(kClass, kType)
        )

    override suspend fun <Request : Any, Response : Any> post(
        route: String,
        body: Request,
        kClass: KClass<Response>,
        kType: KType,
        bodyType: KClass<Request>,
        bodyKType: KType
    ): Result<NetworkStatus, Response> =
        httpClient.post(
            route,
            body,
            getType(kClass, kType),
            getType(bodyType, bodyKType)
        )

    override suspend fun <Response : Any> delete(
        route: String,
        queryParameters: Map<String, Any?>,
        kClass: KClass<Response>,
        kType: KType
    ): Result<NetworkStatus, Response> =
        httpClient.delete(
            route,
            queryParameters,
            getType(kClass, kType)
        )

    @OptIn(ExperimentalStdlibApi::class)
    private fun getType(
        kClass: KClass<*>,
        kType: KType,
    ) = TypeInfo(kClass, kType.javaType, kType)
}