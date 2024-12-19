import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.kova700.mviphotopicker.common.TestDispatcherProvider
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.feature.photo_list.PhotoListViewModel
import com.kova700.mviphotopicker.feature.photo_list.PhotoListViewModel.Companion.IS_TEST_FLAG
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListAction
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListActionProcessor
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListEvent
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListReducer
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListUiState
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListUserActionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import java.util.Date

class PhotoListViewModelTest : BehaviorSpec() {
	private val fakeAlbumRepository = mockk<AlbumRepository>()

	private fun TestScope.photoListViewModel() = PhotoListViewModel(
		savedStateHandle = SavedStateHandle().apply {
			set(IS_TEST_FLAG, true)
		},
		photoListActionProcessor = PhotoListActionProcessor(fakeAlbumRepository),
		photoListActionUserActionProcessor = PhotoListUserActionProcessor(),
		photoListReducer = PhotoListReducer(),
		dispatcherProvider = TestDispatcherProvider(testScheduler),
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
			val loadPhotosAction = PhotoListAction.LoadPhotos(albumId = fakeAlbumId)

			When("albumRepository.getAlbum() returns album") {
				coEvery { fakeAlbumRepository.getAlbum(fakeAlbumId) } returns fakeAlbum

				Then("emit state with photos") {
					runTest {
						with(photoListViewModel()) {
							process(loadPhotosAction)
							uiState.test {
								awaitItem().uiState shouldBe PhotoListUiState.InitLoading
								awaitItem().uiState shouldBe PhotoListUiState.PhotoItems(fakeAlbum.photos.toImmutableList())
							}
							event.test { expectNoEvents() }
						}
					}
				}
			}

			When("albumRepository.getAlbum() throw Exception") {
				coEvery { fakeAlbumRepository.getAlbum(fakeAlbumId) } throws Exception()

				Then("emit state with InitError") {}
				runTest {
					with(photoListViewModel()) {
						process(loadPhotosAction)
						uiState.test {
							awaitItem().uiState shouldBe PhotoListUiState.InitLoading
							awaitItem().uiState shouldBe PhotoListUiState.InitError
						}
						event.test { expectNoEvents() }
					}
				}
			}
		}

		Given("ClickPhoto Action") {
			val fakePhoto = Photo(
				id = 1L,
				albumId = 1L,
				uri = "testUri",
				dateAdded = Date()
			)
			val clickPhotoAction = PhotoListAction.ClickPhoto(fakePhoto)
			Then("emit event with NavigateToPhotoDetail Event") {
				runTest {
					with(photoListViewModel()) {
						process(clickPhotoAction)
						uiState.test {
							awaitItem().uiState shouldBe PhotoListUiState.DEFAULT
						}
						event.test {
							awaitItem() shouldBe PhotoListEvent.NavigateToPhotoDetail(fakePhoto)
						}
					}
				}
			}
		}

		Given("ClickBack Action") {
			val clickBackAction = PhotoListAction.ClickBack

			Then("emit event with NavigateToPrevious Event") {
				runTest {
					with(photoListViewModel()) {
						process(clickBackAction)
						uiState.test {
							awaitItem().uiState shouldBe PhotoListUiState.DEFAULT
						}
						event.test {
							awaitItem() shouldBe PhotoListEvent.NavigateToPrevious
						}
					}
				}
			}
		}

	}

}