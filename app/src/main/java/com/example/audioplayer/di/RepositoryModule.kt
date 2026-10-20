package com.example.audioplayer.di

import com.example.audioplayer.data.repository.AudioRepositoryImpl
import com.example.audioplayer.data.repository.LocalAudioRepositoryImpl
import com.example.audioplayer.domain.repository.AudioRepository
import com.example.audioplayer.domain.repository.LocalAudioRepository
import com.example.audioplayer.playback.PlaybackManager
import com.example.audioplayer.playback.PlaybackManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//first lesson

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAudioRepository(audioRepositoryImpl: AudioRepositoryImpl): AudioRepository

    @Binds
    @Singleton
    abstract fun bindPlaybackManager(playbackManagerImpl: PlaybackManagerImpl): PlaybackManager

    @Binds
    @Singleton
    abstract fun bindLocalAudioRepository(localAudioRepositoryImpl: LocalAudioRepositoryImpl): LocalAudioRepository
}

