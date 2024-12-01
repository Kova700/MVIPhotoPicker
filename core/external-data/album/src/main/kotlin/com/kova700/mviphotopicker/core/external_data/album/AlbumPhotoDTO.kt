package com.kova700.mviphotopicker.core.external_data.album

import android.net.Uri

data class AlbumPhotoDTO(
	val photoId: Long,
	val photoUri: Uri,
	val albumId: Long,
	val albumName: String,
	val dateAddedInSeconds: Long,
)