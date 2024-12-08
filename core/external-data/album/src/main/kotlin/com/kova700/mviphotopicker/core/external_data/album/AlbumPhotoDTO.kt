package com.kova700.mviphotopicker.core.external_data.album

data class AlbumPhotoDTO(
	val photoId: Long,
	val photoUri: String,
	val albumId: Long,
	val albumName: String,
	val dateAddedInSeconds: Long,
)