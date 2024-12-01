package com.kova700.mviphotopicker.feature.photo_detail

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.core.design_system.theme.LightGray
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailAction
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailEvent
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailState
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailStickersUiState
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailUiState
import com.kova700.mviphotopicker.feature.photo_detail.component.photo.InitErrorPhotoDetail
import com.kova700.mviphotopicker.feature.photo_detail.component.photo.LoadingPhotoDetail
import com.kova700.mviphotopicker.feature.photo_detail.component.photo.PhotoDetailTopBar
import com.kova700.mviphotopicker.feature.photo_detail.component.photo.result.PhotoCanvas
import com.kova700.mviphotopicker.feature.photo_detail.component.sticker.InitErrorStickers
import com.kova700.mviphotopicker.feature.photo_detail.component.sticker.LoadingStickers
import com.kova700.mviphotopicker.feature.photo_detail.component.sticker.result.StickerHolder
import kotlinx.coroutines.launch

@Composable
fun PhotoDetailRoute(
	navigateToAlbumListClearingBackStack: () -> Unit,
	navigateToPrevious: () -> Unit,
	photoDetailViewModel: PhotoDetailViewModel = hiltViewModel(),
) {
	val lifecycleOwner = LocalLifecycleOwner.current
	val photoDetailState by photoDetailViewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(lifecycleOwner.lifecycle) {
		lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
			photoDetailViewModel.event.collect { event ->
				when (event) {
					PhotoDetailEvent.NavigateToAlbumList -> navigateToAlbumListClearingBackStack()
					PhotoDetailEvent.NavigateToPrevious -> navigateToPrevious()
				}
			}
		}
	}
	PhotoDetailScreen(
		onClickOverlay = { bitmap: Bitmap? ->
			photoDetailViewModel.process(
				PhotoDetailAction.ClickOverlay(
					bitmap
				)
			)
		},
		onClickBack = { photoDetailViewModel.process(PhotoDetailAction.ClickBack) },
		onClickSticker = { sticker ->
			photoDetailViewModel.process(
				PhotoDetailAction.ClickSticker(
					sticker
				)
			)
		},
		onClickReloadSticker = { photoDetailViewModel.process(PhotoDetailAction.LoadStickers) },
		photoDetailState = photoDetailState,
	)
}

@Composable
fun PhotoDetailScreen(
	onClickOverlay: (Bitmap?) -> Unit,
	onClickBack: () -> Unit,
	onClickSticker: (Sticker) -> Unit,
	onClickReloadSticker: () -> Unit,
	photoDetailState: PhotoDetailState
) {
	val coroutineScope = rememberCoroutineScope()
	val graphicsLayer = rememberGraphicsLayer()

	Scaffold(
		containerColor = LightGray,
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
		) {
			PhotoDetailTopBar(
				albumTitle = photoDetailState.albumTitle,
				isStickerSelected = photoDetailState.isStickerSelected,
				onClickBack = onClickBack,
				onClickOverlay = {
					coroutineScope.launch {
						onClickOverlay(graphicsLayer.toImageBitmap().asAndroidBitmap())
					}
				},
			)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f)
			) {
				when (photoDetailState.uiState) {
					is PhotoDetailUiState.InitLoading -> LoadingPhotoDetail()
					is PhotoDetailUiState.InitError -> InitErrorPhotoDetail(onClickBackHome = onClickBack)
					is PhotoDetailUiState.PhotoDetail -> {
						PhotoCanvas(
							modifier = Modifier
								.drawWithContent {
									graphicsLayer
										.record { this@drawWithContent.drawContent() }
									drawLayer(graphicsLayer)
								}
								.background(Color.White),
							photoUri = photoDetailState.uiState.photoUri,
							selectedSticker = photoDetailState.selectedSticker,
						)
					}
				}
			}
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(151.dp)
					.background(Color.White),
				contentAlignment = Alignment.Center
			) {
				when (photoDetailState.stickersUiState) {
					PhotoDetailStickersUiState.InitLoading -> LoadingStickers()
					PhotoDetailStickersUiState.InitError -> InitErrorStickers(
						onClickRetry = onClickReloadSticker
					)

					is PhotoDetailStickersUiState.StickerList -> StickerHolder(
						stickers = photoDetailState.stickersUiState.stickers,
						onClickSticker = onClickSticker
					)
				}
			}

		}
	}
}