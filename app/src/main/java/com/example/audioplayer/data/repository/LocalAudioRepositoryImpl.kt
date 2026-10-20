package com.example.audioplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.audioplayer.domain.model.LocalSong
import com.example.audioplayer.domain.repository.LocalAudioRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//second lesson
class LocalAudioRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalAudioRepository {
    override suspend fun getLocalSongs(): List<LocalSong> =
        withContext(Dispatchers.IO) {
            val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection: Array<String> = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
            )
            val query = context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                "${MediaStore.Audio.Media.TITLE} ASC"
            )
            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

                val songs = mutableListOf<LocalSong>()

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val songUri = ContentUris.withAppendedId(collection, id)

                    songs.add(
                        LocalSong(
                            id = id,
                            title = title,
                            artist = artist,
                            uri = songUri.toString()
                        )
                    )
                }

                songs
            }
                ?: emptyList()
        }
}

