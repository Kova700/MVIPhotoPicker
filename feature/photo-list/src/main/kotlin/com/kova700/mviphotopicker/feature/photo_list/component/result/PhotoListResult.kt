package com.kova700.mviphotopicker.feature.photo_list.component.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PhotoListResult(
	photos: ImmutableList<Photo>,
	onClickPhoto: (Photo) -> Unit,
) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
	) {
		PhotoGrid(
			photos = photos,
			onClickPhoto = onClickPhoto
		)
	}
}

@Composable
fun PhotoGrid(
	photos: ImmutableList<Photo>,
	onClickPhoto: (Photo) -> Unit,
) {
	LazyVerticalGrid(
		modifier = Modifier,
		columns = GridCells.Fixed(3),
		verticalArrangement = Arrangement.spacedBy(2.dp),
		horizontalArrangement = Arrangement.spacedBy(2.dp),
		contentPadding = PaddingValues(vertical = 10.dp)
	) {
		items(photos) { photo ->
			PhotoItem(
				photo = photo,
				onClickPhoto = onClickPhoto
			)
		}
	}
}