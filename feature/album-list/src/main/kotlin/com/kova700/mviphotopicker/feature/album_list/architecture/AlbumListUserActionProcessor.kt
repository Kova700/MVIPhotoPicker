package com.kova700.mviphotopicker.feature.album_list.architecture

import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState.PermissionState
import com.kova700.mviphotopicker.feature.base.architecture.ActionProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumListUserActionProcessor @Inject constructor(
) : ActionProcessor<AlbumListAction, AlbumListMutation, AlbumListEvent> {

	override fun invoke(action: AlbumListAction): Flow<Pair<AlbumListMutation?, AlbumListEvent?>> {
		return flow {
			when (action) {
				is AlbumListAction.ClickAlbum -> clickAlbum(action.albumId)
				AlbumListAction.RequestPermission -> requestPermission()
				AlbumListAction.RequestFullPermission -> requestFullPermission()
				AlbumListAction.DenyPermission -> denyPermission()
				AlbumListAction.GrantFullPermission -> grantFullPermission()
				AlbumListAction.GrantPartialPermission -> grantPartialPermission()

				is AlbumListAction.LoadInitAlbums,
				AlbumListAction.CheckPermission -> Unit
			}
		}
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.clickAlbum(albumId: Long) {
		emit(null to AlbumListEvent.NavigateToPhotoList(albumId))
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.requestPermission() {
		emit(null to AlbumListEvent.RequestPermission)
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.requestFullPermission() {
		emit(null to AlbumListEvent.RequestFullPermission)
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.denyPermission() {
		emit(AlbumListMutation.ShowNeedPermission to null)
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.grantFullPermission() {
		emit(AlbumListMutation.UpdatePermissionState(PermissionState.Granted) to null)
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.grantPartialPermission() {
		emit(AlbumListMutation.UpdatePermissionState(PermissionState.PartialGranted) to null)
	}

}