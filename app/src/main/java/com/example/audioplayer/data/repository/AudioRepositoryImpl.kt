package com.example.audioplayer.data.repository

import com.example.audioplayer.domain.model.Song
import com.example.audioplayer.domain.repository.AudioRepository
import com.example.audioplayer.domain.repository.RepositoryScenario
import com.example.audioplayer.domain.repository.RepositoryScenarioProvider
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
//first lesson
class AudioRepositoryImpl @Inject constructor(
    private val scenarioProvider: RepositoryScenarioProvider
) : AudioRepository {
    override suspend fun getSongs(): List<Song> {
        delay(2.seconds)
        return when (scenarioProvider.scenario.value) {
            RepositoryScenario.EMPTY -> {
                emptyList()
            }
            RepositoryScenario.ERROR -> {
                throw Exception("The songs couldn’t be loaded!")
            }
            RepositoryScenario.UPDATED -> {
                listOf(
                    Song(
                        id = 1,
                        title = "One",
                        artist = "Metallica",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    ),
                    Song(
                        id = 2,
                        title = "1984",
                        artist = "Louna",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    ),
                    Song(
                        id = 3,
                        title = "Liar",
                        artist = "Korn",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    ),
                    Song(
                        id = 4,
                        title = "Future is now",
                        artist = "OffSpring",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    )
                )
            }
            RepositoryScenario.SUCCESS -> {
                listOf(
                    Song(
                        id = 1,
                        title = "One",
                        artist = "Metallica",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    ),
                    Song(
                        id = 2,
                        title = "1984",
                        artist = "Louna",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    ),
                    Song(
                        id = 3,
                        title = "Liar",
                        artist = "Korn",
                        url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
                    )
                )
            }
        }
    }
}

