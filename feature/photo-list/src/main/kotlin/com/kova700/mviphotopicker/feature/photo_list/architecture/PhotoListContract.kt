package com.kova700.mviphotopicker.feature.photo_list.architecture

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.feature.base.architecture.Action
import com.kova700.mviphotopicker.feature.base.architecture.Mutation
import com.kova700.mviphotopicker.feature.base.architecture.UiEvent
import com.kova700.mviphotopicker.feature.base.architecture.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

//UiState
//Action
//Mutation
//Event

data class PhotoListState(
	val albumTitle: String,
	val uiState: PhotoListUiState
) : UiState {

	companion object {
		val DEFAULT = PhotoListState(
			albumTitle = "",
			uiState = PhotoListUiState.DEFAULT
		)
	}

}

@Stable
sealed interface PhotoListUiState {
	@Immutable
	data object InitLoading : PhotoListUiState

	@Immutable
	data object InitError : PhotoListUiState

	@Immutable
	data class PhotoItems(
		val photos: ImmutableList<Photo>
	) : PhotoListUiState {

		companion object {
			val DEFAULT = PhotoItems(
				photos = persistentListOf()
			)
		}
	}

	companion object {
		val DEFAULT = InitLoading
	}
}

sealed interface PhotoListAction : Action {
	data class ClickPhoto(val photo: Photo) : PhotoListAction
	data object ClickBack : PhotoListAction

	data class LoadPhotos(val albumId: Long) : PhotoListAction
}

sealed interface PhotoListMutation : Mutation {
	data object ShowInitLoader : PhotoListMutation
	data object ShowInitError : PhotoListMutation
	data class ShowContent(val album: Album) : PhotoListMutation
}

sealed interface PhotoListEvent : UiEvent {
	data class NavigateToPhotoDetail(
		val photo: Photo,
	) : PhotoListEvent

	data object NavigateToPrevious : PhotoListEvent
}