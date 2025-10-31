package core.player.api.domain

import core.player.api.presentation.states.VideoQuality

interface VideoQualityController {
    fun getAvailableQualities(): List<VideoQuality>
    fun getCurrentQuality(): VideoQuality?
    fun getQuality(index: Int): VideoQuality?
    fun setQuality(qualityIndex: Int)
    fun setAutoQuality()
    fun isAutoQuality(): Boolean
}