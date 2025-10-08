package com.example.impl.di

import com.example.impl.data.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient as KtorHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkSingletonModule {

    @Provides
    @Singleton
    fun provideHttpClient(): KtorHttpClient {
        return HttpClientFactory().build()
    }
}