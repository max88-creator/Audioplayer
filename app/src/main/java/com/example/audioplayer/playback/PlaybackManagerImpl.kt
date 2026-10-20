package com.example.audioplayer.playback

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.audioplayer.domain.model.LocalSong
import com.example.audioplayer.domain.model.Song
import com.example.audioplayer.utils.LOG_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
//first lesson

class PlaybackManagerImpl @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : PlaybackManager {
    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying = _isPlaying.asStateFlow()
    override fun play(song: Song) {
        val item = song.url
        val mediaItem = MediaItem.Builder().setUri(item).build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun stop() {
        exoPlayer.stop()
    }

    override fun forwardToNext() {
        exoPlayer.seekForward()
    }
//second lesson
    override fun playLocal(song: LocalSong) {
        Log.d(LOG_KEY, "Локальное воспроизведение: ${song.uri}")

        val mediaItem = MediaItem.Builder()
            .setUri(song.uri)
            .build()

        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    val _event = MutableSharedFlow<String>()
    override val event = _event.asSharedFlow()
    override fun toPrevious() {
        exoPlayer.seekToPrevious()
    }


    init {
        exoPlayer.addListener(
            object : Player.Listener {

                override fun onPlaybackStateChanged(playbackState: Int) {
                    Log.d(LOG_KEY, "value changed to $playbackState")
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _isPlaying.value = isPlaying
                    Log.d(LOG_KEY, "isPlaying == $isPlaying")
                }


                override fun onPlayerError(error: PlaybackException) {
                    scope.launch {
                        _event.emit(error.message.toString())
                    }
                    Log.d(LOG_KEY, "error: $error")
                }
            }
        )
    }
}

