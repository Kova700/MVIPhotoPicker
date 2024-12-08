package com.kova700.mviphotopicker.core.data.album.external.model

data class Album(
	val id: Long,
	val title: String,
	val photos: List<Photo>,
	val coverPhotoUri: String
) {
	val photoCount: Int
		get() = photos.size
}