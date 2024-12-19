package com.kova700.mviphotopicker.feature.album_list.component.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.design_system.component.DefaultButton
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue
import com.kova700.mviphotopicker.feature.album_list.R
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState.PermissionState.Granted
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState.PermissionState.PartialGranted
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AlbumResult(
	albums: ImmutableList<Album>,
	permissionState: AlbumListState.PermissionState,
	onClickAlbum: (Long) -> Unit,
	onClickFullPermissionGranted: () -> Unit,
) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
	) {
		if (permissionState == PartialGranted) {
			Box(
				modifier = Modifier
					.padding(16.dp)
			) {
				Column {
					Text(
						text = stringResource(id = R.string.album_partial_permission_content),
						color = MediumBlue,
						style = DefaultTypography.bodyMedium,
						textAlign = TextAlign.Center,
					)
					DefaultButton(
						modifier = Modifier
							.align(Alignment.CenterHorizontally)
							.padding(top = 10.dp),
						text = stringResource(id = R.string.allow_permission),
						onClick = onClickFullPermissionGranted
					)
				}
			}
		}

		AlbumGrid(
			albums = albums,
			onClickAlbum = onClickAlbum
		)
	}
}

@Composable
fun AlbumGrid(
	albums: ImmutableList<Album>,
	onClickAlbum: (Long) -> Unit
) {
	LazyVerticalGrid(
		modifier = Modifier,
		columns = GridCells.Fixed(2),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		contentPadding = PaddingValues(
			vertical = 10.dp,
			horizontal = 8.dp
		)
	) {
		items(
			items = albums,
			key = { album -> album.id }
		) { album ->
			AlbumItem(
				album = album,
				onClick = onClickAlbum
			)
		}
	}
}

@Preview
@Composable
fun PartialGrantedAlbumResultPreview() {
	AlbumResult(
		albums = persistentListOf(),
		permissionState = PartialGranted,
		onClickAlbum = { },
		onClickFullPermissionGranted = { }
	)
}

@Preview
@Composable
fun GrantedAlbumResultPreview() {
	AlbumResult(
		albums = persistentListOf(),
		permissionState = Granted,
		onClickAlbum = { },
		onClickFullPermissionGranted = { }
	)
}