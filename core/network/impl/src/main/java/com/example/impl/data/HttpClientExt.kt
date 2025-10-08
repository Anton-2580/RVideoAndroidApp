package com.example.impl.data

import com.example.impl.BuildConfig
import com.example.api.Result
import com.example.api.data.statuses.NetworkStatus
import io.ktor.client.HttpClient as KtorHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.SerializationException


suspend fun <Response: Any> KtorHttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
    type: TypeInfo,
): Result<NetworkStatus, Response> = safeCall(type) {
    get {
        url(createRoute(route))
        queryParameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
}

suspend fun <Request: Any, Response: Any> KtorHttpClient.post(
    route: String,
    body: Request,
    type: TypeInfo,
    bodyType: TypeInfo,
): Result<NetworkStatus, Response> = safeCall(type) {
    post {
        url(createRoute(route))
        setBody(body, bodyType)
    }
}


suspend fun <Response: Any> KtorHttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
    type: TypeInfo,
): Result<NetworkStatus, Response> = safeCall(type) {
    delete {
        url(createRoute(route))
        queryParameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
}


suspend inline fun <T> safeCall(
    type: TypeInfo,
    execute: () -> HttpResponse,
): Result<NetworkStatus, T> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(NetworkStatus.NoInternet)
    } catch (e: SerializationException) {
        return Result.Error(NetworkStatus.Serialization)
    } /* catch (e: Exception) {
        e.printStackTrace()
        return Result.Error(NetworkStatus.Unknown)
    } */

    return responseToResult(response, type)
}


suspend inline fun <T: Any> responseToResult(
    response: HttpResponse,
    type: TypeInfo,
): Result<NetworkStatus, T> {
    for (networkStatus in NetworkStatus.INSTANCES) {
        if (response.status.value.toShort() == networkStatus.code) {
            return when(networkStatus.code) {
                in 100..299 -> Result.Success(networkStatus, response.body(type))
                in 300..599 -> Result.Error(networkStatus, response.body(type))
                else -> Result.Error(NetworkStatus.Unknown)
            }
        }
    }
    return Result.Error(NetworkStatus.Unknown)
}

fun createRoute(route: String): String {
    return BuildConfig.BASE_URL + "/$route"
}
