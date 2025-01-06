import app.cash.turbine.test
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailAction
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailEvent
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailMutation
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailUserActionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class PhotoDetailUserActionProcessorTest : BehaviorSpec() {

	private val actionProcessor = PhotoDetailUserActionProcessor()

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
			val action = PhotoDetailAction.ClickOverlay
			Then("emit ShowCombiningImagesLoader & null") {
				runTest {
					actionProcessor(action).test {
						awaitItem() shouldBe (PhotoDetailMutation.ShowCombiningImagesLoader to null)
						awaitComplete()
					}
				}
			}
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
