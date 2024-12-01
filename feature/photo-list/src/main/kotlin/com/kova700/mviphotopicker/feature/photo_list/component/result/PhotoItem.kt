package com.kova700.mviphotopicker.feature.photo_list.component.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.core.design_system.component.PhotoPlaceholder

@Composable
fun PhotoItem(
	photo: Photo,
	onClickPhoto: (Photo) -> Unit,
) {
	SubcomposeAsyncImage(
		modifier = Modifier
			.fillMaxWidth()
			.aspectRatio(1f)
			.clickable { onClickPhoto(photo) },
		model = photo.uri,
		contentDescription = "photo",
		contentScale = ContentScale.Crop,
		loading = { PhotoPlaceholder() },
	)
}