import app.cash.turbine.test
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListAction
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListEvent
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListUserActionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import java.util.Date

class PhotoListUserActionProcessorTest : BehaviorSpec() {

	private val actionProcessor = PhotoListUserActionProcessor()

	init {
		Given("ClickPhoto Action") {
			val fakePhoto = Photo(
				id = 1L,
				albumId = 1L,
				uri = "testUri",
				dateAdded = Date()
			)
			val action = PhotoListAction.ClickPhoto(photo = fakePhoto)

			Then("emit null & NavigateToPhotoDetail Event") {
				runTest {
					actionProcessor(action).test {
						awaitItem() shouldBe (null to PhotoListEvent.NavigateToPhotoDetail(fakePhoto))
						awaitComplete()
					}
				}
			}
		}

		Given("ClickBack Action") {
			val action = PhotoListAction.ClickBack

			Then("emit null & NavigateToPrevious Event") {
				runTest {
					actionProcessor(action).test {
						awaitItem() shouldBe (null to PhotoListEvent.NavigateToPrevious)
						awaitComplete()
					}
				}
			}
		}

	}

}