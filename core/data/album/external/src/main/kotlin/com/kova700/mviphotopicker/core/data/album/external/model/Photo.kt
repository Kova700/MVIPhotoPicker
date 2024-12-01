package com.kova700.mviphotopicker.core.data.album.external.model

import android.net.Uri
import java.util.Date

data class Photo(
	val id: Long,
	val albumId: Long,
	val uri: Uri,
	val dateAdded: Date,
)