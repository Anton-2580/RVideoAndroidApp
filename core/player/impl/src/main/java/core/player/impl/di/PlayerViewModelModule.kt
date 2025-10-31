package core.player.impl.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import core.player.api.domain.PlayerController
import core.player.api.domain.VideoPlayer
import core.player.api.domain.VideoQualityController
import core.player.impl.domain.ExoVideoPlayerImpl
import core.player.impl.domain.PlayerControllerImpl
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.domain.VideoQualityControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named


@Module
@InstallIn(ViewModelComponent::class)
object PlayerViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideExoPlayer(context: Context): ExoPlayer {
        return ExoPlayer.Builder(context)
            .build()
    }

    @Provides
    @ViewModelScoped
    @Named("ExoVideoPlayer")
    fun provideVideoPlayer(player: ExoPlayer): VideoPlayer<ExoPlayer, Player.Listener, MediaItem> {
        return ExoVideoPlayerImpl(player)
    }

    @Provides
    @ViewModelScoped
    fun providePlayerController(
        @Named("ExoVideoPlayer") player: VideoPlayer<ExoPlayer, Player.Listener, MediaItem>,
    ): PlayerController<PlayerUiState, Player.Listener, MediaItem> {
        return PlayerControllerImpl(
            player = player,
        )
    }

    @OptIn(UnstableApi::class)
    @Provides
    @ViewModelScoped
    fun provideVideoQualityController(
        player: ExoPlayer,
    ): VideoQualityController {
        return VideoQualityControllerImpl(
            player = player
        )
    }
}