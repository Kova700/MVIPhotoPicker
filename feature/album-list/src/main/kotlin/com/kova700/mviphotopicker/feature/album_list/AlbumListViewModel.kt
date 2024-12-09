package com.kova700.mviphotopicker.feature.album_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListAction
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListActionProcessor
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListReducer
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUserActionProcessor
import com.kova700.mviphotopicker.feature.base.architecture.model
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	albumListActionProcessor: AlbumListActionProcessor,
	albumListUserActionProcessor: AlbumListUserActionProcessor,
	albumListReducer: AlbumListReducer,
) : ViewModel() {
	private val isTest = savedStateHandle.get<Boolean>(IS_TEST_FLAG) ?: false

	private val model by model(
		actionProcessors = listOf(albumListActionProcessor, albumListUserActionProcessor),
		reducers = listOf(albumListReducer),
		initState = AlbumListState.DEFAULT
	)
	val uiState = model.uiState
	val event = model.event

	init {
		if (isTest.not()) process(AlbumListAction.CheckPermission)
	}

	fun process(action: AlbumListAction) = model.process(action)

	companion object {
		internal const val IS_TEST_FLAG = "IS_TEST_FLAG"
	}
}