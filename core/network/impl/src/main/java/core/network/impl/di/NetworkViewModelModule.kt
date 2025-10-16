package core.network.impl.di

import core.network.api.data.HttpClient
import core.network.impl.repository.HttpClientRepository
import io.ktor.client.HttpClient as KtorHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object NetworkViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideHttpClientRepository(httpClient: KtorHttpClient): HttpClient {
        return HttpClientRepository(
            httpClient = httpClient,
        )
    }
}