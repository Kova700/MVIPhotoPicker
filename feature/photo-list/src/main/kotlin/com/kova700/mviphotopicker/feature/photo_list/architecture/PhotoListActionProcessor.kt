package com.kova700.mviphotopicker.feature.photo_list.architecture

import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.feature.base.architecture.ActionProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoListActionProcessor @Inject constructor(
	private val albumRepository: AlbumRepository,
) : ActionProcessor<PhotoListAction, PhotoListMutation, PhotoListEvent> {

	override fun invoke(action: PhotoListAction): Flow<Pair<PhotoListMutation?, PhotoListEvent?>> {
		return flow {
			when (action) {
				is PhotoListAction.LoadPhotos -> loadPhotos(action.albumId)

				is PhotoListAction.ClickPhoto,
				PhotoListAction.ClickBack -> Unit
			}
		}
	}

	private suspend fun FlowCollector<Pair<PhotoListMutation?, PhotoListEvent?>>.loadPhotos(albumId: Long) {
		emit(PhotoListMutation.ShowInitLoader to null)
		runCatching { albumRepository.getAlbum(albumId) }
			.onSuccess { emit(PhotoListMutation.ShowContent(it) to null) }
			.onFailure { emit(PhotoListMutation.ShowInitError to null) }
	}

}