import android.content.Context
import android.graphics.Bitmap
import app.cash.turbine.test
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.core.util.saveImageToGallery
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailAction
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailEvent
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailMutation
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailUserActionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.test.runTest

class PhotoDetailUserActionProcessorTest : BehaviorSpec() {
	private val mockContext = mockk<Context>()

	private val actionProcessor = PhotoDetailUserActionProcessor(
		context = mockContext,
	)

	init {
		Given("PhotoDetailAction.ClickSticker") {
			val fakeSticker = Sticker("TestUri")
			val action = PhotoDetailAction.ClickSticker(fakeSticker)

			Then("emit ShowBigSticker & null") {
				runTest {
					actionProcessor(action).test {
						awaitItem() shouldBe (PhotoDetailMutation.ShowBigSticker(fakeSticker) to null)
						awaitComplete()
					}
				}
			}
		}

		Given("PhotoDetailAction.ClickOverlay") {
			val combinedImageBitmap = mockk<Bitmap>()
			val action = PhotoDetailAction.ClickOverlay(combinedImageBitmap)
			mockkStatic("com.kova700.mviphotopicker.core.util.ImageSaveUtilKt")
			coEvery { mockContext.saveImageToGallery(combinedImageBitmap) } just Runs

			Then("Bitmap must be saved as an image, emit ShowCombiningImagesLoader & NavigateToAlbumList Event") {
				runTest {
					actionProcessor(action).test {
						coVerify { mockContext.saveImageToGallery(combinedImageBitmap) }
						awaitItem() shouldBe (PhotoDetailMutation.ShowCombiningImagesLoader to PhotoDetailEvent.NavigateToAlbumList)
						awaitComplete()
					}
				}
			}
			unmockkStatic("com.kova700.mviphotopicker.core.util.ImageSaveUtilKt")
		}

		Given("PhotoDetailAction.ClickBack") {
			val action = PhotoDetailAction.ClickBack

			Then("emit null & NavigateToPrevious Event") {
				runTest {
					actionProcessor(action).test {
						awaitItem() shouldBe (null to PhotoDetailEvent.NavigateToPrevious)
						awaitComplete()
					}
				}
			}
		}
	}
}