package com.kova700.mviphotopicker.feature.photo_detail.architecture

import android.content.Context
import android.graphics.Bitmap
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.feature.base.architecture.ActionProcessor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import com.kova700.mviphotopicker.core.util.saveImageToGallery
import javax.inject.Inject

class PhotoDetailUserActionProcessor @Inject constructor(
	@ApplicationContext private val context: Context
) : ActionProcessor<PhotoDetailAction, PhotoDetailMutation, PhotoDetailEvent> {

	override fun invoke(action: PhotoDetailAction): Flow<Pair<PhotoDetailMutation?, PhotoDetailEvent?>> {
		return flow {
			when (action) {
				is PhotoDetailAction.ClickSticker -> clickSticker(action.sticker)
				is PhotoDetailAction.ClickOverlay -> clickOverlay(action.resultBitmap)
				PhotoDetailAction.ClickBack -> clickBack()

				is PhotoDetailAction.LoadPhoto,
				PhotoDetailAction.LoadStickers,
				PhotoDetailAction.FinishSaveImages -> Unit
			}
		}
	}

	private suspend fun FlowCollector<Pair<PhotoDetailMutation?, PhotoDetailEvent?>>.clickSticker(
		sticker: Sticker
	) {
		emit(PhotoDetailMutation.ShowBigSticker(sticker) to null)
	}

	private suspend fun FlowCollector<Pair<PhotoDetailMutation?, PhotoDetailEvent?>>.clickBack() {
		emit(null to PhotoDetailEvent.NavigateToPrevious)
	}

	private suspend fun FlowCollector<Pair<PhotoDetailMutation?, PhotoDetailEvent?>>.clickOverlay(
		resultBitmap: Bitmap?
	) {
		resultBitmap?.let { context.saveImageToGallery(it) }
		emit(PhotoDetailMutation.ShowCombiningImagesLoader to PhotoDetailEvent.NavigateToAlbumList)
	}
}