package com.kova700.mviphotopicker.feature.photo_list.architecture

import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.feature.base.architecture.Reducer
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class PhotoListReducer @Inject constructor(
) : Reducer<PhotoListMutation, PhotoListState> {

	override fun invoke(mutation: PhotoListMutation, currentState: PhotoListState): PhotoListState {
		return when (mutation) {
			is PhotoListMutation.ShowContent -> currentState.mutateToShowContent(mutation.album)
			PhotoListMutation.ShowInitError -> currentState.mutateToShowInitError()
			PhotoListMutation.ShowInitLoader -> currentState.mutateToShowInitLoader()
		}
	}

	private fun PhotoListState.mutateToShowContent(album: Album) =
		copy(
			albumTitle = album.title,
			uiState = PhotoListUiState.PhotoItems(album.photos.toImmutableList())
		)

	private fun PhotoListState.mutateToShowInitError() =
		copy(uiState = PhotoListUiState.InitError)

	private fun PhotoListState.mutateToShowInitLoader() =
		copy(uiState = PhotoListUiState.InitLoading)
}