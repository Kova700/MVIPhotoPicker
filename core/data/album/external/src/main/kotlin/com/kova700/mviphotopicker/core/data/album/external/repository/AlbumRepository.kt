package com.kova700.mviphotopicker.core.data.album.external.repository

import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo

interface AlbumRepository {
	suspend fun getAlbums(): List<Album>
	fun getAlbum(albumId: Long): Album
	fun getPhoto(albumId: Long, photoId: Long): Photo
}