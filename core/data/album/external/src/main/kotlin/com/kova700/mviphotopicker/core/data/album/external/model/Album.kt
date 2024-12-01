package com.kova700.mviphotopicker.core.data.album.external.model

import android.net.Uri

data class Album(
	val id: Long,
	val title: String,
	val photos: List<Photo>,
	val coverPhotoUri: Uri
) {
	val photoCount: Int
		get() = photos.size
}