package com.example.impl.presentation

import com.example.api.data.HttpClient
import com.example.api.data.site_api.ModelsApi
import com.example.impl.BaseViewModel
import com.example.impl.models.ManyItemsSerializable
import com.example.impl.models.VideoWithChannelSerializable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val httpClient: HttpClient,
): BaseViewModel() {
    fun getVideos() {
        workWithData<Exception> {
            httpClient.get<ManyItemsSerializable<VideoWithChannelSerializable>>(ModelsApi.VideoWithChannels.url)
        }
    }
}