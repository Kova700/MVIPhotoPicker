package com.kova700.mviphotopicker.feature.photo_detail.component.sticker.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker

@Composable
fun StickerHolder(
	stickers: List<Sticker>,
	onClickSticker: (Sticker) -> Unit
) {
	LazyRow(
		modifier = Modifier
			.fillMaxWidth()
	) {
		items(stickers) { sticker ->
			StickerItem(
				sticker = sticker,
				onClickSticker = onClickSticker
			)
		}
	}
}