package com.kova700.mviphotopicker.feature.album_list.component.result

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.design_system.component.PhotoPlaceholder
import com.kova700.mviphotopicker.core.design_system.theme.LightBlue
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.feature.album_list.R

@Composable
fun AlbumItem(
	album: Album,
	onClick: (Long) -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick(album.id) },
		colors = CardDefaults.cardColors(containerColor = LightBlue),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		shape = RoundedCornerShape(3.dp)
	) {
		Column {
			SubcomposeAsyncImage(
				model = album.coverPhotoUri,
				contentDescription = "albumCoverPhoto",
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1f),
				contentScale = ContentScale.Crop,
				loading = { PhotoPlaceholder() },
			)
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp)
			) {
				Text(
					text = album.title,
					color = MediumBlue,
					style = DefaultTypography.bodyMedium,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = stringResource(R.string.album_photo_count, album.photoCount),
					color = MediumBlue,
					style = DefaultTypography.bodySmall,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun AlbumItemPreview() {
	AlbumItem(
		Album(
			id = 0,
			title = "Album Title",
			photos = listOf(),
			coverPhotoUri = Uri.parse(""),
		),
		onClick = {}
	)
}