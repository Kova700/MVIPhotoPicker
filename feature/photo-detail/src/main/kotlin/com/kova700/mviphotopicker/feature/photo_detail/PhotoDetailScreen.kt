package com.kova700.mviphotopicker.feature.photo_detail

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.tooling.preview.Preview
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
		onClickOverlay = { photoDetailViewModel.process(PhotoDetailAction.ClickOverlay) },
		onFinishedImageCombine = { bitmap: Bitmap? ->
			photoDetailViewModel.process(
				PhotoDetailAction.FinishImageCombine(bitmap)
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
	onClickOverlay: () -> Unit,
	onFinishedImageCombine: (Bitmap?) -> Unit,
	onClickBack: () -> Unit,
	onClickSticker: (Sticker) -> Unit,
	onClickReloadSticker: () -> Unit,
	photoDetailState: PhotoDetailState,
) {
	val graphicsLayer = rememberGraphicsLayer()

	LaunchedEffect(photoDetailState.isCombiningImages) {
		if (photoDetailState.isCombiningImages) {
			onFinishedImageCombine(graphicsLayer.toImageBitmap().asAndroidBitmap())
		}
	}

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
				onClickOverlay = { onClickOverlay() },
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
						if (photoDetailState.isCombiningImages) {
							Box(
								modifier = Modifier
									.fillMaxSize(),
								contentAlignment = Alignment.Center
							) {
								CircularProgressIndicator(color = Color.Gray)
							}
						}
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

@Preview(showBackground = true)
@Composable
fun PhotoDetailScreenLoadingPreview() {
	val fakeState = PhotoDetailState(
		albumTitle = "Sample Album",
		uiState = PhotoDetailUiState.InitLoading, // 초기 로딩 상태
		stickersUiState = PhotoDetailStickersUiState.InitLoading, // 스티커 로딩 상태
		isCombiningImages = false, // 이미지 결합 중 아님
		selectedSticker = null
	)

	PhotoDetailScreen(
		onClickOverlay = {},
		onFinishedImageCombine = {},
		onClickBack = {},
		onClickSticker = {},
		onClickReloadSticker = {},
		photoDetailState = fakeState
	)
}

@Preview(showBackground = true)
@Composable
fun PhotoDetailScreenErrorPreview() {
	val fakeState = PhotoDetailState(
		albumTitle = "Sample Album",
		uiState = PhotoDetailUiState.InitError, // 초기 오류 상태
		stickersUiState = PhotoDetailStickersUiState.InitError, // 스티커 오류 상태
		isCombiningImages = false, // 이미지 결합 중 아님
		selectedSticker = null
	)

	PhotoDetailScreen(
		onClickOverlay = {},
		onFinishedImageCombine = {},
		onClickBack = {},
		onClickSticker = {},
		onClickReloadSticker = {},
		photoDetailState = fakeState
	)
}

@Preview(showBackground = true)
@Composable
fun PhotoDetailScreenStickerPreview() {
	val fakeState = PhotoDetailState(
		albumTitle = "Sample Album",
		uiState = PhotoDetailUiState.PhotoDetail(photoUri = "sample/photo/uri"), // 사진 상태
		stickersUiState = PhotoDetailStickersUiState.StickerList(
			stickers = listOf(
				Sticker(uri = "sticker/uri/1"),
				Sticker(uri = "sticker/uri/2")
			)
		),
		isCombiningImages = false, // 이미지 결합 중 아님
		selectedSticker = Sticker(uri = "sticker/uri/1") // 선택된 스티커
	)

	PhotoDetailScreen(
		onClickOverlay = {},
		onFinishedImageCombine = {},
		onClickBack = {},
		onClickSticker = {},
		onClickReloadSticker = {},
		photoDetailState = fakeState
	)
}

@Preview(showBackground = true)
@Composable
fun PhotoDetailScreenCombiningImagesPreview() {
	val fakeState = PhotoDetailState(
		albumTitle = "Sample Album",
		uiState = PhotoDetailUiState.PhotoDetail(photoUri = "sample/photo/uri"), // 사진 상태
		stickersUiState = PhotoDetailStickersUiState.StickerList(
			stickers = listOf(
				Sticker(uri = "sticker/uri/1"),
				Sticker(uri = "sticker/uri/2")
			)
		),
		isCombiningImages = true, // 이미지 결합 중
		selectedSticker = null // 선택된 스티커 없음
	)

	PhotoDetailScreen(
		onClickOverlay = {},
		onFinishedImageCombine = {},
		onClickBack = {},
		onClickSticker = {},
		onClickReloadSticker = {},
		photoDetailState = fakeState
	)
}
