package com.kova700.mviphotopicker.feature.photo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kova700.mviphotopicker.common.DispatcherProvider
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
	dispatcherProvider: DispatcherProvider,
) : ViewModel() {
	private val isTest = savedStateHandle.get<Boolean>(IS_TEST_FLAG) ?: false
	private val photoUri = savedStateHandle.get<String>(SELECTED_PHOTO_Uri) ?: ""
	private val albumId = savedStateHandle.get<Long>(SELECTED_PHOTO_ALBUM_ID) ?: -1

	private val model by model(
		actionProcessors = listOf(photoDetailActionProcessor, photoDetailUserActionProcessor),
		reducers = listOf(photoDetailReducer),
		dispatcherProvider = dispatcherProvider,
		initState = PhotoDetailState.DEFAULT
	)
	val uiState = model.uiState
	val event = model.event

	init {
		if (isTest.not()) {
			process(
				PhotoDetailAction.LoadPhoto(
					photoUri = photoUri,
					albumId = albumId
				)
			)
			process(PhotoDetailAction.LoadStickers)

		}
	}

	fun process(action: PhotoDetailAction) = model.process(action)

	companion object {
		internal const val IS_TEST_FLAG = "IS_TEST_FLAG"
	}
}