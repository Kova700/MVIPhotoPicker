package com.kova700.mviphotopicker.feature.photo_detail.architecture

import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.feature.base.architecture.Reducer
import javax.inject.Inject

class PhotoDetailReducer @Inject constructor(
) : Reducer<PhotoDetailMutation, PhotoDetailState> {

	override fun invoke(
		mutation: PhotoDetailMutation,
		currentState: PhotoDetailState
	): PhotoDetailState {
		return when (mutation) {
			is PhotoDetailMutation.ShowContent -> currentState.mutateToShowContent(
				albumTitle = mutation.albumTitle,
				photoUri = mutation.photoUri
			)

			PhotoDetailMutation.ShowInitError -> currentState.mutateToShowInitError()
			PhotoDetailMutation.ShowInitLoader -> currentState.mutateToShowInitLoader()
			PhotoDetailMutation.ShowCombiningImagesLoader -> currentState.mutateToShowCombiningImagesLoader()
			PhotoDetailMutation.HideCombiningImagesLoader -> currentState.mutateToHideCombiningImagesLoader()
			is PhotoDetailMutation.ShowBigSticker -> currentState.mutateToShowBigSticker(mutation.sticker)
			PhotoDetailMutation.ShowStickerInitError -> currentState.mutateToShowStickerInitError()
			PhotoDetailMutation.ShowStickerInitLoader -> currentState.mutateToShowStickerInitLoader()
			is PhotoDetailMutation.ShowStickers -> currentState.mutateToShowStickers(mutation.stickers)
		}
	}

	private fun PhotoDetailState.mutateToShowContent(albumTitle: String, photoUri: String) =
		copy(
			albumTitle = albumTitle,
			uiState = PhotoDetailUiState.PhotoDetail(photoUri)
		)

	private fun PhotoDetailState.mutateToShowInitError() =
		copy(uiState = PhotoDetailUiState.InitError)

	private fun PhotoDetailState.mutateToShowInitLoader() =
		copy(uiState = PhotoDetailUiState.InitLoading)

	private fun PhotoDetailState.mutateToShowCombiningImagesLoader() =
		copy(isCombiningImages = true)

	private fun PhotoDetailState.mutateToHideCombiningImagesLoader() =
		copy(isCombiningImages = false)

	private fun PhotoDetailState.mutateToShowBigSticker(sticker: Sticker) =
		copy(selectedSticker = sticker)

	private fun PhotoDetailState.mutateToShowStickerInitError() =
		copy(stickersUiState = PhotoDetailStickersUiState.InitError)

	private fun PhotoDetailState.mutateToShowStickerInitLoader() =
		copy(stickersUiState = PhotoDetailStickersUiState.InitLoading)

	private fun PhotoDetailState.mutateToShowStickers(stickers: List<Sticker>) =
		copy(stickersUiState = PhotoDetailStickersUiState.StickerList(stickers))
}