package core.player.impl.domain

import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import core.player.api.domain.VideoQualityController
import core.player.api.presentation.states.VideoQuality


@UnstableApi
class VideoQualityControllerImpl(
    private val player: ExoPlayer,
): VideoQualityController {
    private val videoGroup
        get() = player.currentTracks.groups.firstOrNull { it.type == C.TRACK_TYPE_VIDEO && it.isSelected }

    override fun getAvailableQualities(): List<VideoQuality> {
        videoGroup?.let {
            return (0 until it.length).map { index ->
                val format = it.mediaTrackGroup.getFormat(index)
                VideoQuality(
                    index = index,
                    width = format.width,
                    height = format.height,
                    bitrate = format.bitrate,
                    isSelected = it.isTrackSelected(index),
                )
            }
        }
        return emptyList()
    }

    override fun isAutoQuality(): Boolean =
        player.trackSelector?.parameters == DefaultTrackSelector.Parameters.DEFAULT
    override fun setAutoQuality() {
        player.trackSelector?.setParameters(DefaultTrackSelector.Parameters.DEFAULT)
    }

    override fun setQuality(qualityIndex: Int) {
        videoGroup?.let {
            if (qualityIndex in 0 until it.length) {
                val newParameters = player.trackSelector?.parameters?.buildUpon()
                    ?.setMinVideoSize(
                        it.mediaTrackGroup.getFormat(qualityIndex).width,
                        it.mediaTrackGroup.getFormat(qualityIndex).height,
                    )
                    ?.setMaxVideoSize(
                        it.mediaTrackGroup.getFormat(qualityIndex).width,
                        it.mediaTrackGroup.getFormat(qualityIndex).height,
                    )
                    ?.build()

                newParameters?.let { parameters -> player.trackSelector?.setParameters(parameters) }
            }
        }
    }

    override fun getQuality(index: Int): VideoQuality? {
        return videoGroup?.let {
            val format = it.mediaTrackGroup.getFormat(index)
            VideoQuality(
                index = index,
                width = format.width,
                height = format.height,
                bitrate = format.bitrate,
                isSelected = it.isTrackSelected(index),
            )
        }
    }

    override fun getCurrentQuality(): VideoQuality? {
        val index = selectedTrack()

        index?.let { return getQuality(index) }
        return null
    }

    private fun selectedTrack(): Int? {
        val index = videoGroup?.let {
            (0 until it.length).find { track -> it.isTrackSelected(track) }
        }
        return index
    }
}