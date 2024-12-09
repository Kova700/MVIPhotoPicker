import app.cash.turbine.test
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListAction
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListActionProcessor
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListEvent
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListMutation
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class AlbumListActionProcessorTest : BehaviorSpec() {

	private val fakeAlbumRepository = mockk<AlbumRepository>()

	private val actionProcessor = AlbumListActionProcessor(
		albumRepository = fakeAlbumRepository,
	)

	private val fakeAlbums = listOf<Album>(
		Album(
			id = 1,
			title = "Album 1",
			photos = emptyList(),
			coverPhotoUri = "fakeUri"
		),
		Album(
			id = 2,
			title = "Album 2",
			photos = emptyList(),
			coverPhotoUri = "fakeUri"
		)
	)

	init {
		Given("invoked LoadInitAlbums Action") {
			val loadInitAlbumsAction = AlbumListAction.LoadInitAlbums

			When("albumRepository.getAlbums() returns albums") {
				coEvery { fakeAlbumRepository.getAlbums() } returns fakeAlbums

				Then("emit ShowInitLoader & ShowContent") {
					runTest {
						actionProcessor(loadInitAlbumsAction).test {
							awaitItem() shouldBe (AlbumListMutation.ShowInitLoader to null)
							awaitItem() shouldBe (AlbumListMutation.ShowContent(fakeAlbums) to null)
							awaitComplete()
						}
					}
				}
			}

			When("albumRepository.getAlbums() throw Exception") {
				coEvery { fakeAlbumRepository.getAlbums() } throws Exception()

				Then("emit ShowInitLoader & ShowError") {
					runTest {
						actionProcessor(loadInitAlbumsAction).test {
							awaitItem() shouldBe (AlbumListMutation.ShowInitLoader to null)
							awaitItem() shouldBe (AlbumListMutation.ShowInitError to null)
							awaitComplete()
						}
					}
				}
			}
		}

		Given("invoked CheckPermission Action") {
			val checkPermissionAction = AlbumListAction.CheckPermission
			Then(" emit null & CheckPermission Event") {
				runTest {
					actionProcessor(checkPermissionAction).test {
						awaitItem() shouldBe (null to AlbumListEvent.CheckPermission)
						awaitComplete()
					}
				}
			}
		}

	}
}