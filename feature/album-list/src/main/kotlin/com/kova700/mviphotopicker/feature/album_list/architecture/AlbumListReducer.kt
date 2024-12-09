package com.kova700.mviphotopicker.feature.album_list.architecture

import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.feature.base.architecture.Reducer
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class AlbumListReducer @Inject constructor(
) : Reducer<AlbumListMutation, AlbumListState> {
	override fun invoke(
		mutation: AlbumListMutation,
		currentState: AlbumListState
	): AlbumListState {
		return when (mutation) {
			AlbumListMutation.ShowInitLoader -> currentState.mutateToShowInitLoader()
			AlbumListMutation.ShowInitError -> currentState.mutateToShowInitError()
			is AlbumListMutation.ShowContent -> currentState.mutateToShowContent(mutation.albums)
			AlbumListMutation.ShowNeedPermission -> currentState.mutateToShowNeedPermission()
			is AlbumListMutation.UpdatePermissionState ->
				currentState.mutateToUpdatePermissionState(mutation.permissionState)
		}
	}

	private fun AlbumListState.mutateToShowInitLoader() =
		copy(uiState = AlbumListUiState.InitLoading)

	private fun AlbumListState.mutateToShowInitError() =
		copy(uiState = AlbumListUiState.InitError)

	private fun AlbumListState.mutateToShowContent(albums: List<Album>) =
		copy(
			uiState = if (albums.isEmpty()) AlbumListUiState.Empty
			else AlbumListUiState.AlbumItems(albums.toImmutableList())
		)

	private fun AlbumListState.mutateToShowNeedPermission() =
		copy(uiState = AlbumListUiState.NeedPermission)

	private fun AlbumListState.mutateToUpdatePermissionState(permissionState: AlbumListState.PermissionState) =
		copy(permissionState = permissionState)
}