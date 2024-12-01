package com.kova700.mviphotopicker.feature.album_list

import android.net.Uri
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
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
				Photo(
					id = 2,
					albumId = 1,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
				Photo(
					id = 3,
					albumId = 1,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
			),
			coverPhotoUri = Uri.parse("https://images.unsplash.com/photo-1638863342226-7ef651886a62?q=80&w=2126&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
		),
		Album(
			id = 2,
			title = "Selfie",
			photos = listOf(
				Photo(
					id = 4,
					albumId = 2,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
				Photo(
					id = 5,
					albumId = 2,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
				Photo(
					id = 6,
					albumId = 2,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
			),
			coverPhotoUri = Uri.parse("https://picsum.photos/200/300"),
		),
		Album(
			id = 3,
			title = "Minimal",
			photos = listOf(
				Photo(
					id = 7,
					albumId = 3,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
			),
			coverPhotoUri = Uri.parse("https://picsum.photos/200/300"),
		),
		Album(
			id = 4,
			title = "Selfie",
			photos = listOf(
				Photo(
					id = 8,
					albumId = 4,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
			),
			coverPhotoUri = Uri.parse("https://picsum.photos/200/300"),
		),
		Album(
			id = 5,
			title = "Minimal",
			photos = listOf(
				Photo(
					id = 9,
					albumId = 5,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
			),
			coverPhotoUri = Uri.parse("https://picsum.photos/200/300"),
		),
		Album(
			id = 6,
			title = "Selfie",
			photos = listOf(
				Photo(
					id = 10,
					albumId = 6,
					uri = Uri.parse("https://picsum.photos/200/300"),
					dateAdded = Date()
				),
			),
			coverPhotoUri = Uri.parse("https://picsum.photos/200/300"),
		),
	).toImmutableList()
}
