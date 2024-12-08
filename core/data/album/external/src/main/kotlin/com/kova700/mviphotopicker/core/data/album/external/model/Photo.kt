package com.kova700.mviphotopicker.core.data.album.external.model

import java.util.Date

data class Photo(
	val id: Long,
	val albumId: Long,
	val uri: String,
	val dateAdded: Date,
)