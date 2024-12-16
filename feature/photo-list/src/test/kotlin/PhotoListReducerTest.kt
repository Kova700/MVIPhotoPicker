import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListMutation
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListReducer
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListState
import com.kova700.mviphotopicker.feature.photo_list.architecture.PhotoListUiState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.toImmutableList
import java.util.Date

class PhotoListReducerTest : BehaviorSpec() {
	private val reducer = PhotoListReducer()

	private val fakeAlbum = Album(
		id = 1,
		title = "Sample Album",
		photos = listOf(
			Photo(
				id = 1,
				albumId = 1,
				uri = "testUri 1",
				dateAdded = Date()
			),
			Photo(
				id = 2,
				albumId = 1,
				uri = "testUri 2",
				dateAdded = Date()
			),
		),
		coverPhotoUri = "coverUri"
	)

	init {
		Given("PhotoListMutation.ShowContent") {
			val result = reducer.invoke(PhotoListMutation.ShowContent(fakeAlbum), PhotoListState.DEFAULT)

			Then("state should display the album content & Title") {
				result shouldBe PhotoListState.DEFAULT.copy(
					albumTitle = fakeAlbum.title,
					uiState = PhotoListUiState.PhotoItems(
						photos = fakeAlbum.photos.toImmutableList()
					)
				)
			}
		}

		Given("PhotoListMutation.ShowInitLoader") {
			val result = reducer.invoke(PhotoListMutation.ShowInitLoader, PhotoListState.DEFAULT)

			Then("state should indicate InitLoading") {
				result shouldBe PhotoListState.DEFAULT.copy(
					uiState = PhotoListUiState.InitLoading
				)
			}
		}

		Given("PhotoListMutation.ShowInitError") {
			val result = reducer.invoke(PhotoListMutation.ShowInitError, PhotoListState.DEFAULT)

			Then("state should indicate InitError") {
				result shouldBe PhotoListState.DEFAULT.copy(
					uiState = PhotoListUiState.InitError
				)
			}
		}

	}

}