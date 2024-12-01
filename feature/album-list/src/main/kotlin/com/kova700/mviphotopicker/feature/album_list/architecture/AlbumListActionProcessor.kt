package com.kova700.mviphotopicker.feature.album_list.architecture

import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.feature.base.architecture.ActionProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumListActionProcessor @Inject constructor(
	private val albumRepository: AlbumRepository,
) : ActionProcessor<AlbumListAction, AlbumListMutation, AlbumListEvent> {

	override fun invoke(action: AlbumListAction): Flow<Pair<AlbumListMutation?, AlbumListEvent?>> {
		return flow {
			when (action) {
				AlbumListAction.LoadInitAlbums -> loadInitAlbums()
				AlbumListAction.CheckPermission -> checkPermission()

				is AlbumListAction.ClickAlbum,
				AlbumListAction.RequestPermission,
				AlbumListAction.RequestFullPermission,
				AlbumListAction.GrantFullPermission,
				AlbumListAction.GrantPartialPermission,
				AlbumListAction.DenyPermission -> Unit
			}
		}
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.loadInitAlbums() {
		emit(AlbumListMutation.ShowInitLoader to null)
		runCatching { albumRepository.getAlbums() }
			.onSuccess { emit(AlbumListMutation.ShowContent(it) to null) }
			.onFailure { emit(AlbumListMutation.ShowInitError to null) }
	}

	private suspend fun FlowCollector<Pair<AlbumListMutation?, AlbumListEvent?>>.checkPermission() {
		emit(null to AlbumListEvent.CheckPermission)
	}

}