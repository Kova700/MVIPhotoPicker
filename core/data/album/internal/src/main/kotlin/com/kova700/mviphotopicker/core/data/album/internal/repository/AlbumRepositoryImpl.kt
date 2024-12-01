package com.kova700.mviphotopicker.core.data.album.internal.repository

import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.core.external_data.album.AlbumDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
	private val albumDataSource: AlbumDataSource
) : AlbumRepository {
	private val _albumsMap = MutableStateFlow<Map<Long, Album>>(emptyMap())//(ID, Album)
	private val sortedAlbums = _albumsMap
		.map { it.values.sortedBy(Album::title) }
		.filterNotNull()

	override suspend fun getAlbums(): List<Album> {
		val albums = albumDataSource.getAllAlbums()
		_albumsMap.update { it + albums.associateBy(Album::id) }
		return sortedAlbums.first()
	}

	override fun getAlbum(albumId: Long): Album {
		return _albumsMap.value[albumId] ?: throw IOException("Album not found")
	}

	override fun getPhoto(albumId:Long, photoId: Long): Photo {
		return getAlbum(albumId).photos.find { it.id == photoId }
			?: throw IOException("Photo not found")
	}
}