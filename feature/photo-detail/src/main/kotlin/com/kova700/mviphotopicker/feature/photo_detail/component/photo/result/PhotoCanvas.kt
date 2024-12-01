package com.kova700.mviphotopicker.feature.photo_detail.component.photo.result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.core.design_system.component.PhotoPlaceholder

@Composable
fun PhotoCanvas(
	modifier: Modifier = Modifier,
	photoUri: String,
	selectedSticker: Sticker?
) {
	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center
	) {

		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(photoUri)
				.decoderFactory(SvgDecoder.Factory())
				.build(),
			contentDescription = "Photo",
			modifier = Modifier
				.fillMaxWidth(),
			contentScale = ContentScale.Crop,
			loading = { PhotoPlaceholder() },
		)

		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(selectedSticker?.uri)
				.decoderFactory(SvgDecoder.Factory())
				.build(),
			contentDescription = "BigSticker",
			modifier = Modifier
				.size(240.dp),
			contentScale = ContentScale.Crop,
		)
	}
}