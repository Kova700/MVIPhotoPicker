package com.kova700.mviphotopicker.feature.photo_detail.component.sticker.result

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker

@Composable
fun StickerItem(
	sticker: Sticker,
	onClickSticker: (Sticker) -> Unit
) {
	Box(
		modifier = Modifier
			.size(80.dp)
			.clickable { onClickSticker(sticker) }
			.border(
				width = 1.dp,
				color = Color.Black
			)
			.background(Color.White)
			.padding(7.dp),
		contentAlignment = Alignment.Center
	) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(sticker.uri)
				.decoderFactory(SvgDecoder.Factory())
				.build(),
			contentDescription = "sticker",
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1f),
			contentScale = ContentScale.Crop,
		)
	}
}