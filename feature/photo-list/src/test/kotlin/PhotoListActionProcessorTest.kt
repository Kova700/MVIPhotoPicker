import app.cash.turbine.test
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListAction
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListActionProcessor
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListMutation
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class PhotoListActionProcessorTest : BehaviorSpec() {
	private val fakeAlbumRepository = mockk<AlbumRepository>()

	private val actionProcessor = PhotoListActionProcessor(
		albumRepository = fakeAlbumRepository,
	)

	private val fakeAlbumId = 1L
	private val fakeAlbum = Album(
		id = fakeAlbumId,
		title = "test Title",
		photos = emptyList(),
		coverPhotoUri = "Test Uri"
	)

	init {
		Given("LoadPhotos Action") {
			val action = PhotoListAction.LoadPhotos(albumId = fakeAlbumId)

			When("albumRepository.getAlbum() returns album") {
				coEvery { fakeAlbumRepository.getAlbum(fakeAlbumId) } returns fakeAlbum

				Then("emit ShowInitLoader & ShowContent") {
					runTest {
						actionProcessor(action).test {
							awaitItem() shouldBe (PhotoListMutation.ShowInitLoader to null)
							awaitItem() shouldBe (PhotoListMutation.ShowContent(fakeAlbum) to null)
							awaitComplete()
						}
					}
				}
			}

			When("albumRepository.getAlbum() throw Exception") {
				coEvery { fakeAlbumRepository.getAlbum(fakeAlbumId) } throws Exception()

				Then("emit ShowInitLoader & ShowError") {
					runTest {
						actionProcessor(action).test {
							awaitItem() shouldBe (PhotoListMutation.ShowInitLoader to null)
							awaitItem() shouldBe (PhotoListMutation.ShowInitError to null)
							awaitComplete()
						}
					}
				}
			}
		}
	}
}