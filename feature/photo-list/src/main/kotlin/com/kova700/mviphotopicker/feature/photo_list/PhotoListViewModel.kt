package com.kova700.mviphotopicker.feature.photo_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kova700.mviphotopicker.feature.base.architecture.model
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListAction
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListActionProcessor
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListReducer
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListState
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListUserActionProcessor
import com.kova700.mviphotopicker.feature.photo_list.navigation.SELECTED_ALBUM_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	photoListActionProcessor: PhotoListActionProcessor,
	photoListActionUserActionProcessor: PhotoListUserActionProcessor,
	photoListReducer: PhotoListReducer,
) : ViewModel() {
	private val albumId = savedStateHandle.get<Long>(SELECTED_ALBUM_ID) ?: -1

	private val model by model(
		actionProcessors = listOf(photoListActionProcessor, photoListActionUserActionProcessor),
		reducers = listOf(photoListReducer),
		initState = PhotoListState.DEFAULT
	)
	val uiState = model.uiState
	val event = model.event

	init {
		process(PhotoListAction.LoadPhotos(albumId))
	}

	fun process(action: PhotoListAction) = model.process(action)
}