package com.kova700.mviphotopicker.feature.album_list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUiState
import kotlinx.collections.immutable.toImmutableList
import java.util.Date

class AlbumListScreenPreviewParameterProvider : PreviewParameterProvider<AlbumListState> {
	override val values: Sequence<AlbumListState>
		get() = sequenceOf(
			AlbumListState.DEFAULT.copy(
				uiState = AlbumListUiState.AlbumItems(
					albums = albumDummyList
				),
				permissionState = AlbumListState.PermissionState.Granted
			)
		)

	private val albumDummyList = listOf(
		Album(
			id = 1,
			title = "Minimal",
			photos = listOf(
				Photo(
					id = 1,
					albumId = 1,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
				Photo(
					id = 2,
					albumId = 1,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
				Photo(
					id = 3,
					albumId = 1,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
			),
			coverPhotoUri = "testUri",
		),
		Album(
			id = 2,
			title = "Selfie",
			photos = listOf(
				Photo(
					id = 4,
					albumId = 2,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
				Photo(
					id = 5,
					albumId = 2,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
				Photo(
					id = 6,
					albumId = 2,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
			),
			coverPhotoUri = "https://picsum.photos/200/300",
		),
		Album(
			id = 3,
			title = "Minimal",
			photos = listOf(
				Photo(
					id = 7,
					albumId = 3,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
			),
			coverPhotoUri = "https://picsum.photos/200/300",
		),
		Album(
			id = 4,
			title = "Selfie",
			photos = listOf(
				Photo(
					id = 8,
					albumId = 4,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
			),
			coverPhotoUri = "https://picsum.photos/200/300",
		),
		Album(
			id = 5,
			title = "Minimal",
			photos = listOf(
				Photo(
					id = 9,
					albumId = 5,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
			),
			coverPhotoUri = "https://picsum.photos/200/300",
		),
		Album(
			id = 6,
			title = "Selfie",
			photos = listOf(
				Photo(
					id = 10,
					albumId = 6,
					uri = "https://picsum.photos/200/300",
					dateAdded = Date()
				),
			),
			coverPhotoUri = "https://picsum.photos/200/300",
		),
	).toImmutableList()
}
