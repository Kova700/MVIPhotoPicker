import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailMutation
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailReducer
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailState
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailStickersUiState
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailUiState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PhotoDetailReducerTest : BehaviorSpec() {
	private val reducer = PhotoDetailReducer()

	private fun invokeReducer(mutation: PhotoDetailMutation) =
		reducer.invoke(mutation, defaultState)

	private val defaultState = PhotoDetailState.DEFAULT

	init {
		Given("PhotoDetailMutation.ShowContent") {
			val fakeAlbumTitle = "TestTitle"
			val fakePhotoUri = "TestUri"
			val result = invokeReducer(
				PhotoDetailMutation.ShowContent(
					albumTitle = fakeAlbumTitle,
					photoUri = fakePhotoUri
				)
			)

			Then("state should display photo & albumTitle") {
				result shouldBe defaultState.copy(
					albumTitle = fakeAlbumTitle,
					uiState = PhotoDetailUiState.PhotoDetail(fakePhotoUri)
				)
			}
		}

		Given("PhotoDetailMutation.ShowInitError") {
			val result = invokeReducer(PhotoDetailMutation.ShowInitError)

			Then("state should indicate InitError") {
				result shouldBe defaultState.copy(
					uiState = PhotoDetailUiState.InitError
				)
			}
		}

		Given("PhotoDetailMutation.ShowInitLoader") {
			val result = invokeReducer(PhotoDetailMutation.ShowInitLoader)

			Then("state should indicate InitLoader") {
				result shouldBe defaultState.copy(
					uiState = PhotoDetailUiState.InitLoading
				)
			}
		}

		Given("PhotoDetailMutation.ShowCombiningImagesLoader") {
			val result = invokeReducer(PhotoDetailMutation.ShowCombiningImagesLoader)

			Then("state should indicate CombiningImagesLoader") {
				result shouldBe defaultState.copy(
					isCombiningImages = true
				)
			}
		}

		Given("PhotoDetailMutation.HideCombiningImagesLoader") {
			val result = invokeReducer(PhotoDetailMutation.HideCombiningImagesLoader)

			Then("state should hide CombiningImagesLoader") {
				result shouldBe defaultState.copy(
					isCombiningImages = false
				)
			}
		}

		Given("PhotoDetailMutation.ShowBigSticker") {
			val fakeSicker = Sticker("TestUri")
			val result = invokeReducer(PhotoDetailMutation.ShowBigSticker(fakeSicker))

			Then("state should indicate BigSticker") {
				result shouldBe defaultState.copy(
					selectedSticker = fakeSicker
				)
			}
		}

		Given("PhotoDetailMutation.ShowStickerInitError") {
			val result = invokeReducer(PhotoDetailMutation.ShowStickerInitError)

			Then("state should indicate StickerInitError") {
				result shouldBe defaultState.copy(
					stickersUiState = PhotoDetailStickersUiState.InitError
				)
			}
		}

		Given("PhotoDetailMutation.ShowStickerInitLoader") {
			val result = invokeReducer(PhotoDetailMutation.ShowStickerInitLoader)

			Then("state should indicate ShowStickerInitLoader") {
				result shouldBe defaultState.copy(
					stickersUiState = PhotoDetailStickersUiState.InitLoading
				)
			}
		}

		Given("PhotoDetailMutation.ShowStickers") {
			val fakeStickers = listOf(
				Sticker("TestUri1"),
				Sticker("TestUri2"),
			)
			val result = invokeReducer(PhotoDetailMutation.ShowStickers(fakeStickers))

			Then("state should indicate ShowStickers") {
				result shouldBe defaultState.copy(
					stickersUiState = PhotoDetailStickersUiState.StickerList(fakeStickers)
				)
			}
		}
	}
}