package com.baltroid.apps.course

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.ui.AspectRatioFrameLayout
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel
) {
    val uiState by viewModel.playerState.collectAsStateLifecycleAware()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        uiState.playerModel?.let {
            VideoPlayer(
                mediaItems = listOf(
                    VideoPlayerMediaItem.NetworkMediaItem(
                        url = it.url.orEmpty(),
                        mediaMetadata = MediaMetadata.Builder().setTitle(it.id.toString())
                            .build(),
                        mimeType = MimeTypes.VIDEO_MP4,
                    )
                ),
                handleLifecycle = true,
                autoPlay = true,
                usePlayerController = true,
                enablePip = true,
                handleAudioFocus = true,
                controllerConfig = VideoPlayerControllerConfig(
                    showSpeedAndPitchOverlay = false,
                    showSubtitleButton = false,
                    showCurrentTimeAndTotalTime = true,
                    showBufferingProgress = false,
                    showForwardIncrementButton = true,
                    showBackwardIncrementButton = true,
                    showBackTrackButton = false,
                    showNextTrackButton = false,
                    showRepeatModeButton = true,
                    showFullScreenButton = true,
                    controllerShowTimeMilliSeconds = 5_000,
                    controllerAutoShow = true
                ),
                volume = 0.5f,  // volume 0.0f to 1.0f
                repeatMode = RepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE
                onCurrentTimeChanged = { currentTime -> // long type, current player time (millisec)
                    Log.e("CurrentTime", currentTime.toString())
                },
                playerInstance = { // ExoPlayer instance (Experimental)
                    addAnalyticsListener(
                        object : AnalyticsListener {
                            // player logger
                        }
                    )
                },
                modifier = Modifier.fillMaxSize().align(Alignment.Center)
            )
        }
    }
}