package core.network.impl.repository

import common.api.Result
import core.network.api.data.statuses.NetworkStatus
import core.network.impl.data.delete
import core.network.impl.data.get
import core.network.impl.data.post
import io.ktor.client.HttpClient
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.KClass
import kotlin.reflect.KType

class HttpClientRepository(
    private val httpClient: HttpClient,
): core.network.api.data.HttpClient() {
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

    private fun getType(
        kClass: KClass<*>,
        kType: KType,
    ) = TypeInfo(type = kClass, kotlinType = kType)
}