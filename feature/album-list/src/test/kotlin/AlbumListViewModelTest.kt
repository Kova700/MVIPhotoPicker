import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.kova700.mviphotopicker.common.TestDispatcherProvider
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.feature.album_list.AlbumListViewModel
import com.kova700.mviphotopicker.feature.album_list.AlbumListViewModel.Companion.IS_TEST_FLAG
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListAction
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListActionProcessor
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListEvent
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListReducer
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState.PermissionState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUiState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUserActionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class AlbumListViewModelTest : BehaviorSpec() {

	private val albumRepository = mockk<AlbumRepository>()

	private fun TestScope.albumListViewModel() = AlbumListViewModel(
		savedStateHandle = SavedStateHandle().apply {
			set(IS_TEST_FLAG, true)
		},
		albumListActionProcessor = AlbumListActionProcessor(albumRepository),
		albumListUserActionProcessor = AlbumListUserActionProcessor(),
		dispatcherProvider = TestDispatcherProvider(testScheduler),
		albumListReducer = AlbumListReducer(),
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
		Given("process LoadInitAlbums Action") {
			val loadInitAlbumsAction = AlbumListAction.LoadInitAlbums

			When("albumRepository.getAlbums() returns albums") {
				coEvery { albumRepository.getAlbums() } returns fakeAlbums

				Then("emit state with albums") {
					runTest {
						with(albumListViewModel()) {
							process(loadInitAlbumsAction)
							uiState.test {
								awaitItem().uiState shouldBe AlbumListUiState.InitLoading
								awaitItem().uiState shouldBe AlbumListUiState.AlbumItems(fakeAlbums.toImmutableList())
							}
							event.test { expectNoEvents() }
						}
					}
				}
			}

			When("albumRepository.getAlbums() returns emptyList") {
				coEvery { albumRepository.getAlbums() } returns emptyList()

				Then("emit state with Empty") {
					runTest {
						with(albumListViewModel()) {
							process(loadInitAlbumsAction)
							uiState.test {
								awaitItem().uiState shouldBe AlbumListUiState.InitLoading
								awaitItem().uiState shouldBe AlbumListUiState.Empty
							}
							event.test { expectNoEvents() }
						}
					}
				}
			}

			When("albumRepository.getAlbums() throw Exception") {
				coEvery { albumRepository.getAlbums() } throws Exception()

				Then("emit state with InitError") {
					runTest {
						with(albumListViewModel()) {
							process(loadInitAlbumsAction)
							uiState.test {
								awaitItem().uiState shouldBe AlbumListUiState.InitLoading
								awaitItem().uiState shouldBe AlbumListUiState.InitError
							}
							event.test { expectNoEvents() }
						}
					}
				}
			}
		}

		Given("process RequestPermission Action") {
			val requestPermissionAction = AlbumListAction.RequestPermission

			Then("emit event with RequestPermission") {
				runTest {
					with(albumListViewModel()) {
						process(requestPermissionAction)
						uiState.test {
							awaitItem().uiState shouldBe AlbumListUiState.DEFAULT
						}
						event.test {
							awaitItem() shouldBe AlbumListEvent.RequestPermission
						}
					}
				}
			}
		}

		Given("process CheckPermission Action") {
			val checkPermissionAction = AlbumListAction.CheckPermission

			Then("emit event with CheckPermission") {
				runTest {
					with(albumListViewModel()) {
						process(checkPermissionAction)
						uiState.test {
							awaitItem().uiState shouldBe AlbumListUiState.DEFAULT
						}
						event.test {
							awaitItem() shouldBe AlbumListEvent.CheckPermission
						}
					}
				}
			}
		}

		Given("process ClickAlbum Action") {
			val albumId = 1L
			val clickAlbumAction = AlbumListAction.ClickAlbum(albumId)

			Then("emit event with NavigateToPhotoList") {
				runTest {
					with(albumListViewModel()) {
						process(clickAlbumAction)
						uiState.test {
							awaitItem().uiState shouldBe AlbumListUiState.DEFAULT
						}
						event.test {
							awaitItem() shouldBe AlbumListEvent.NavigateToPhotoList(1)
						}
					}
				}
			}
		}

		Given("process DenyPermission Action") {
			val denyPermissionAction = AlbumListAction.DenyPermission

			Then("emit state with NeedPermission") {
				runTest {
					with(albumListViewModel()) {
						process(denyPermissionAction)
						uiState.test {
							awaitItem().uiState shouldBe AlbumListUiState.DEFAULT
							awaitItem().uiState shouldBe AlbumListUiState.NeedPermission
						}
						event.test { expectNoEvents() }
					}
				}
			}
		}

		Given("process GrantFullPermission Action") {
			val grantFullPermissionAction = AlbumListAction.GrantFullPermission

			Then("emit state with PermissionState Granted") {
				runTest {
					with(albumListViewModel()) {
						process(grantFullPermissionAction)
						uiState.test {
							awaitItem().uiState shouldBe AlbumListUiState.DEFAULT
							awaitItem().permissionState shouldBe PermissionState.Granted
						}
						event.test { expectNoEvents() }
					}
				}
			}
		}

		Given("process GrantPartialPermission Action") {
			val grantPartialPermissionAction = AlbumListAction.GrantPartialPermission

			Then("emit state with PermissionState PartialGranted") {
				runTest {
					with(albumListViewModel()) {
						process(grantPartialPermissionAction)
						uiState.test {
							awaitItem().uiState shouldBe AlbumListUiState.DEFAULT
							awaitItem().permissionState shouldBe PermissionState.PartialGranted
						}
						event.test { expectNoEvents() }
					}
				}
			}
		}
	}
}