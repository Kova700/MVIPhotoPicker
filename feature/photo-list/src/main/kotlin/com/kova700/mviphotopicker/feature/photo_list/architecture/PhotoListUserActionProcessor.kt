package com.kova700.mviphotopicker.feature.photo_list.architecture

import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.feature.base.architecture.ActionProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoListUserActionProcessor @Inject constructor(
) : ActionProcessor<PhotoListAction, PhotoListMutation, PhotoListEvent> {

	override fun invoke(action: PhotoListAction): Flow<Pair<PhotoListMutation?, PhotoListEvent?>> {
		return flow {
			when (action) {
				is PhotoListAction.ClickPhoto -> clickPhoto(action.photo)
				PhotoListAction.ClickBack -> clickBack()

				is PhotoListAction.LoadPhotos -> Unit
			}
		}
	}

	private suspend fun FlowCollector<Pair<PhotoListMutation?, PhotoListEvent?>>.clickPhoto(photo: Photo) {
		emit(null to PhotoListEvent.NavigateToPhotoDetail(photo))
	}

	private suspend fun FlowCollector<Pair<PhotoListMutation?, PhotoListEvent?>>.clickBack() {
		emit(null to PhotoListEvent.NavigateToPrevious)
	}
}