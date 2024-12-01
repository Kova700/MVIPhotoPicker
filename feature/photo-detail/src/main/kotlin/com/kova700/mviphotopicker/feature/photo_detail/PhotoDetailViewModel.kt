package com.kova700.mviphotopicker.feature.photo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kova700.mviphotopicker.feature.base.architecture.model
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailAction
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailActionProcessor
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailReducer
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailState
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailUserActionProcessor
import com.kova700.mviphotopicker.feature.photo_detail.navigation.SELECTED_PHOTO_ALBUM_ID
import com.kova700.mviphotopicker.feature.photo_detail.navigation.SELECTED_PHOTO_Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	photoDetailActionProcessor: PhotoDetailActionProcessor,
	photoDetailUserActionProcessor: PhotoDetailUserActionProcessor,
	photoDetailReducer: PhotoDetailReducer,
) : ViewModel() {
	private val photoUri = savedStateHandle.get<String>(SELECTED_PHOTO_Uri) ?: ""
	private val albumId = savedStateHandle.get<Long>(SELECTED_PHOTO_ALBUM_ID) ?: -1

	private val model by model(
		actionProcessors = listOf(photoDetailActionProcessor, photoDetailUserActionProcessor),
		reducers = listOf(photoDetailReducer),
		initState = PhotoDetailState.DEFAULT
	)
	val uiState = model.uiState
	val event = model.event

	init {
		process(
			PhotoDetailAction.LoadPhoto(
				photoUri = photoUri,
				albumId = albumId
			)
		)
		process(PhotoDetailAction.LoadStickers)
	}

	fun process(action: PhotoDetailAction) = model.process(action)
}