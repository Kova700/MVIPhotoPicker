package com.kova700.mviphotopicker.feature.photo_detail.architecture

import android.content.Context
import android.graphics.Bitmap
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.core.data.sticker.external.repository.StickerRepository
import com.kova700.mviphotopicker.core.util.saveImageToGallery
import com.kova700.mviphotopicker.feature.base.architecture.ActionProcessor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoDetailActionProcessor @Inject constructor(
	private val albumRepository: AlbumRepository,
	private val stickerRepository: StickerRepository,
	@ApplicationContext private val context: Context
) : ActionProcessor<PhotoDetailAction, PhotoDetailMutation, PhotoDetailEvent> {

	override fun invoke(action: PhotoDetailAction): Flow<Pair<PhotoDetailMutation?, PhotoDetailEvent?>> {
		return flow {
			when (action) {
				is PhotoDetailAction.LoadPhoto -> loadPhoto(
					photoUri = action.photoUri,
					albumId = action.albumId
				)

				PhotoDetailAction.LoadStickers -> loadStickers()
				is PhotoDetailAction.FinishImageCombine -> finishImageCombine(action.resultBitmap)

				is PhotoDetailAction.ClickSticker,
				is PhotoDetailAction.ClickBack,
				is PhotoDetailAction.ClickOverlay -> Unit
			}
		}
	}

	private suspend fun FlowCollector<Pair<PhotoDetailMutation?, PhotoDetailEvent?>>.loadPhoto(
		photoUri: String,
		albumId: Long
	) {
		emit(PhotoDetailMutation.ShowInitLoader to null)
		runCatching {
			val albumTitle = albumRepository.getAlbum(albumId = albumId).title
			albumTitle to photoUri
		}.onSuccess { (albumTitle, photoUri) ->
			emit(
				PhotoDetailMutation.ShowContent(
					albumTitle = albumTitle,
					photoUri = photoUri
				) to null
			)
		}.onFailure { emit(PhotoDetailMutation.ShowInitError to null) }
	}

	private suspend fun FlowCollector<Pair<PhotoDetailMutation?, PhotoDetailEvent?>>.loadStickers() {
		emit(PhotoDetailMutation.ShowStickerInitLoader to null)
		runCatching { stickerRepository.getStickers() }
			.onSuccess { emit(PhotoDetailMutation.ShowStickers(it) to null) }
			.onFailure { emit(PhotoDetailMutation.ShowStickerInitError to null) }
	}

	private suspend fun FlowCollector<Pair<PhotoDetailMutation?, PhotoDetailEvent?>>.finishImageCombine(
		resultBitmap: Bitmap?
	) {
		resultBitmap?.let { context.saveImageToGallery(it) }
		emit(PhotoDetailMutation.HideCombiningImagesLoader to PhotoDetailEvent.NavigateToAlbumList)
	}

}
