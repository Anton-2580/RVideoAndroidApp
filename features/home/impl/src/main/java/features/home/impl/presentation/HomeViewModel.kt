package features.home.impl.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import common.api.domain.Result
import core.network.api.data.HttpClient
import core.network.api.data.site_api.ModelsApi
import common.impl.BaseViewModel
import core.network.impl.models.ManyItemsSerializable
import core.network.impl.models.VideoWithChannelSerializable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


typealias ManyVideoSerializable = ManyItemsSerializable<VideoWithChannelSerializable>

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val httpClient: HttpClient,
): BaseViewModel() {
    private val mutableManyVideos: MutableState<List<ManyVideoSerializable>> = mutableStateOf(listOf())
    val manyVideos: State<List<ManyVideoSerializable>> = mutableManyVideos
    val mutableVideos: MutableState<List<VideoWithChannelSerializable>> = mutableStateOf(listOf())
    val videos: State<List<VideoWithChannelSerializable>> = mutableVideos
    val videoTag = "ManyVideos"

    init {
        createEventFlow(videoTag)
        getFirstVideos()
    }

    private fun getFirstVideos() = getVideos(ModelsApi.VideoWithChannels.url)
    fun getNextVideos() = manyVideos.value.last().next?.let { getVideos(it) }

    private fun getVideos(url: String) {
        workWithData<Exception>(videoTag) {
            val result = httpClient.get<ManyItemsSerializable<VideoWithChannelSerializable>>(url)
            when (result) {
                is Result.Error -> result.data
                is Result.Success -> result.data
            }?.let { videoWithChannel ->
                mutableManyVideos.value = manyVideos.value.plusElement(videoWithChannel)
                mutableVideos.value += videoWithChannel.results
            }
        }
    }
}