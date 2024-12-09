package com.kova700.mviphotopicker.feature.album_list.architecture

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.kova700.mviphotopicker.core.data.album.external.model.Album
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

@Stable
data class AlbumListState(
	val uiState: AlbumListUiState,
	val permissionState: PermissionState,
) : UiState {

	@Stable
	sealed interface PermissionState {
		@Immutable
		data object Granted : PermissionState

		@Immutable
		data object PartialGranted : PermissionState

		@Immutable
		data object Denied : PermissionState
	}

	companion object {
		val DEFAULT = AlbumListState(
			uiState = AlbumListUiState.DEFAULT,
			permissionState = PermissionState.Denied,
		)
	}
}

@Stable
sealed interface AlbumListUiState : UiState {

	@Immutable
	data object Empty : AlbumListUiState

	@Immutable
	data object InitLoading : AlbumListUiState

	@Immutable
	data object NeedPermission : AlbumListUiState

	@Immutable
	data object InitError : AlbumListUiState

	@Stable
	data class AlbumItems(
		val albums: ImmutableList<Album>,
	) : AlbumListUiState {

		companion object {
			val DEFAULT = AlbumItems(
				albums = persistentListOf(),
			)
		}
	}

	companion object {
		val DEFAULT = InitLoading
	}
}

sealed interface AlbumListAction : Action {
	data object LoadInitAlbums : AlbumListAction
	data object RequestPermission : AlbumListAction
	data object CheckPermission : AlbumListAction

	data class ClickAlbum(val albumId: Long) : AlbumListAction
	data object DenyPermission : AlbumListAction
	data object GrantFullPermission : AlbumListAction
	data object GrantPartialPermission : AlbumListAction
}

sealed interface AlbumListMutation : Mutation {
	data object ShowInitLoader : AlbumListMutation
	data object ShowNeedPermission : AlbumListMutation
	data object ShowInitError : AlbumListMutation
	data class ShowContent(val albums: List<Album>) : AlbumListMutation
	data class UpdatePermissionState(
		val permissionState: AlbumListState.PermissionState
	) : AlbumListMutation
}

sealed interface AlbumListEvent : UiEvent {
	data class NavigateToPhotoList(val albumId: Long) : AlbumListEvent
	data object RequestPermission : AlbumListEvent
	data object CheckPermission : AlbumListEvent
}