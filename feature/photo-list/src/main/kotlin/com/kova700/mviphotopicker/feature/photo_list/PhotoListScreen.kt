package com.kova700.mviphotopicker.feature.photo_list

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.core.design_system.theme.LightGray
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListAction
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListEvent
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListState
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListUiState
import com.kova700.mviphotopicker.feature.photo_list.component.InitErrorPhotoList
import com.kova700.mviphotopicker.feature.photo_list.component.LoadingPhotoList
import com.kova700.mviphotopicker.feature.photo_list.component.PhotoListTopBar
import com.kova700.mviphotopicker.feature.photo_list.component.result.PhotoListResult
import kotlinx.collections.immutable.persistentListOf
import java.util.Date

@Composable
fun PhotoListRoute(
	navigateToPhotoDetail: (photoUri: String, albumId: Long) -> Unit,
	navigateToPrevious: () -> Unit,
	photoListViewModel: PhotoListViewModel = hiltViewModel(),
) {
	val photoListState by photoListViewModel.uiState.collectAsStateWithLifecycle()

	val lifecycleOwner = LocalLifecycleOwner.current
	LaunchedEffect(lifecycleOwner.lifecycle) {
		lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
			photoListViewModel.event.collect { event ->
				when (event) {
					is PhotoListEvent.NavigateToPhotoDetail -> navigateToPhotoDetail(
						event.photo.uri.toString(),
						event.photo.albumId
					)

					PhotoListEvent.NavigateToPrevious -> navigateToPrevious()
				}
			}
		}
	}

	PhotoListScreen(
		photoListState = photoListState,
		onClickPhoto = { photo -> photoListViewModel.process(PhotoListAction.ClickPhoto(photo)) },
		onClickBack = { photoListViewModel.process(PhotoListAction.ClickBack) },
	)
}

@Composable
fun PhotoListScreen(
	photoListState: PhotoListState,
	onClickPhoto: (Photo) -> Unit,
	onClickBack: () -> Unit,
) {
	Scaffold(
		containerColor = LightGray,
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
		) {
			PhotoListTopBar(
				albumTitle = photoListState.albumTitle,
				onClickBack = onClickBack
			)

			when (photoListState.uiState) {
				PhotoListUiState.InitLoading -> LoadingPhotoList()
				is PhotoListUiState.PhotoItems -> PhotoListResult(
					photoListState.uiState.photos,
					onClickPhoto = onClickPhoto
				)

				PhotoListUiState.InitError -> InitErrorPhotoList(
					onClickBack = onClickBack
				)
			}

		}
	}
}

@Preview(showBackground = true)
@Composable
fun InitLoadingPhotoListScreenPreview() {
	PhotoListScreen(
		photoListState = PhotoListState.DEFAULT.copy(
			uiState = PhotoListUiState.InitLoading
		),
		onClickPhoto = {},
		onClickBack = {}
	)
}

@Preview(showBackground = true)
@Composable
fun InitErrorPhotoListScreenPreview() {
	PhotoListScreen(
		photoListState = PhotoListState.DEFAULT.copy(
			uiState = PhotoListUiState.InitError
		),
		onClickPhoto = {},
		onClickBack = {}
	)
}

@Preview(showBackground = true)
@Composable
fun PhotoItemsPhotoListScreenPreview() {
	PhotoListScreen(
		photoListState = PhotoListState.DEFAULT.copy(
			uiState = PhotoListUiState.PhotoItems(
				photos = persistentListOf(
					Photo(
						id = 1,
						albumId = 1,
						uri = Uri.parse("https://picsum.photos/200/300"),
						dateAdded = Date()
					),
					Photo(
						id = 2,
						albumId = 2,
						uri = Uri.parse("https://picsum.photos/200/300"),
						dateAdded = Date()
					),
					Photo(
						id = 3,
						albumId = 3,
						uri = Uri.parse("https://picsum.photos/200/300"),
						dateAdded = Date()
					),
					Photo(
						id = 4,
						albumId = 4,
						uri = Uri.parse("https://picsum.photos/200/300"),
						dateAdded = Date()
					),
					Photo(
						id = 5,
						albumId = 5,
						uri = Uri.parse("https://picsum.photos/200/300"),
						dateAdded = Date()
					),
				)
			)
		),
		onClickPhoto = {},
		onClickBack = {}
	)
}