package features.home.impl.presentation

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.util.LruCache
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import coil3.compose.AsyncImagePainter
import common.api.domain.Result
import common.impl.presentation.BaseViewModel
import core.network.api.data.HttpClient
import core.network.api.data.site_api.ModelsApi
import core.network.impl.models.ManyItemsSerializable
import core.network.impl.models.VideoWithChannelSerializable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


typealias ManyVideoSerializable = ManyItemsSerializable<VideoWithChannelSerializable>

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val httpClient: HttpClient,
): BaseViewModel() {
    val listState = LazyListState()
    private val mutableManyVideos: MutableState<List<ManyVideoSerializable>> = mutableStateOf(listOf())
    val manyVideos: State<List<ManyVideoSerializable>> = mutableManyVideos
    val mutableVideos: MutableState<List<VideoWithChannelSerializable>> = mutableStateOf(listOf())
    val videos: State<List<VideoWithChannelSerializable>> = mutableVideos
    val videoTag = "ManyVideos"

    init {
        createEventFlow(videoTag)
    }

    fun getFirstVideos() = getVideos(ModelsApi.VideoWithChannels.url)
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

    val mutableVideoPosters: MutableState<LruCache<String, Bitmap?>> = mutableStateOf(LruCache(20 * 1024 * 1024))
    val videoPosters: State<LruCache<String, Bitmap?>> = mutableVideoPosters
    val mutableRelatedVideoImages: MutableState<LruCache<String, AsyncImagePainter>> = mutableStateOf(LruCache(10 * 1024 * 1024))
    val relatedVideoImages: State<LruCache<String, AsyncImagePainter>> = mutableRelatedVideoImages

    fun loadPosterFromVideo(url: String) {
        viewModelScope.launch {
            videoPosters.value.get(url) ?: let {
                val retriever = MediaMetadataRetriever()
                try {
                    retriever.setDataSource(url)
                    mutableVideoPosters.value.put(url, retriever.getFrameAtTime(0))
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    retriever.release()
                }
            }
        }
    }

    fun loadAsyncImage(url: String) {
        viewModelScope.launch {
            mutableRelatedVideoImages.value
        }
    }
}