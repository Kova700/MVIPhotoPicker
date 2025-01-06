package com.kova700.mviphotopicker.feature.photo_detail.architecture

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.feature.base.architecture.Action
import com.kova700.mviphotopicker.feature.base.architecture.Mutation
import com.kova700.mviphotopicker.feature.base.architecture.UiEvent
import com.kova700.mviphotopicker.feature.base.architecture.UiState

//UiState
//Action
//Mutation
//Event

@Stable
data class PhotoDetailState(
	val albumTitle: String,
	val uiState: PhotoDetailUiState,
	val stickersUiState: PhotoDetailStickersUiState,
	val isCombiningImages: Boolean,
	val selectedSticker: Sticker?
) : UiState {

	val isStickerSelected: Boolean
		get() = selectedSticker != null

	companion object {
		val DEFAULT = PhotoDetailState(
			albumTitle = "",
			uiState = PhotoDetailUiState.DEFAULT,
			stickersUiState = PhotoDetailStickersUiState.DEFAULT,
			isCombiningImages = false,
			selectedSticker = null
		)
	}
}

@Stable
sealed interface PhotoDetailUiState {
	@Immutable
	data object InitLoading : PhotoDetailUiState

	@Immutable
	data object InitError : PhotoDetailUiState

	@Immutable
	data class PhotoDetail(
		val photoUri: String
	) : PhotoDetailUiState

	companion object {
		val DEFAULT = InitLoading
	}
}

@Stable
sealed interface PhotoDetailStickersUiState {
	@Immutable
	data object InitLoading : PhotoDetailStickersUiState

	@Immutable
	data object InitError : PhotoDetailStickersUiState

	@Immutable
	data class StickerList(
		val stickers: List<Sticker>
	) : PhotoDetailStickersUiState

	companion object {
		val DEFAULT = InitLoading
	}
}

sealed interface PhotoDetailAction : Action {
	data class ClickSticker(val sticker: Sticker) : PhotoDetailAction
	data object ClickBack : PhotoDetailAction
	data object ClickOverlay : PhotoDetailAction

	data class FinishImageCombine(
		val resultBitmap: Bitmap?
	) : PhotoDetailAction

	data class LoadPhoto(
		val photoUri: String,
		val albumId: Long
	) : PhotoDetailAction

	data object LoadStickers : PhotoDetailAction
}

sealed interface PhotoDetailMutation : Mutation {
	data object ShowInitLoader : PhotoDetailMutation
	data object ShowInitError : PhotoDetailMutation
	data object ShowStickerInitLoader : PhotoDetailMutation
	data object ShowStickerInitError : PhotoDetailMutation
	data class ShowStickers(val stickers: List<Sticker>) : PhotoDetailMutation
	data class ShowContent(
		val albumTitle: String,
		val photoUri: String
	) : PhotoDetailMutation

	data object ShowCombiningImagesLoader : PhotoDetailMutation
	data object HideCombiningImagesLoader : PhotoDetailMutation
	data class ShowBigSticker(val sticker: Sticker) : PhotoDetailMutation
}

sealed interface PhotoDetailEvent : UiEvent {
	data object NavigateToAlbumList : PhotoDetailEvent
	data object NavigateToPrevious : PhotoDetailEvent
}
