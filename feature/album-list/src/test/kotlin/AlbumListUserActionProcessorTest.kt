import app.cash.turbine.test
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListAction
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListEvent
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListMutation
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState.PermissionState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUserActionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class AlbumListUserActionProcessorTest : BehaviorSpec() {

	private val actionProcessor = AlbumListUserActionProcessor()

	init {
		Given("invoked ClickAlbum Action") {
			val albumId = 1L
			val clickAlbumAction = AlbumListAction.ClickAlbum(albumId)
			Then("emit null & NavigateToPhotoList Event") {
				runTest {
					actionProcessor(clickAlbumAction).test {
						awaitItem() shouldBe (null to AlbumListEvent.NavigateToPhotoList(albumId))
						awaitComplete()
					}
				}
			}
		}

		Given("invoked RequestPermission Action") {
			val requestPermissionAction = AlbumListAction.RequestPermission
			Then("emit null & RequestPermission Event") {
				runTest {
					actionProcessor(requestPermissionAction).test {
						awaitItem() shouldBe (null to AlbumListEvent.RequestPermission)
						awaitComplete()
					}
				}
			}
		}

		Given("invoked DenyPermission Action") {
			val denyPermissionAction = AlbumListAction.DenyPermission
			Then("emit ShowNeedPermission Mutation & null") {
				runTest {
					actionProcessor(denyPermissionAction).test {
						awaitItem() shouldBe (AlbumListMutation.ShowNeedPermission to null)
						awaitComplete()
					}
				}
			}
		}

		Given("invoked GrantFullPermission Action") {
			val grantFullPermissionAction = AlbumListAction.GrantFullPermission
			Then("emit UpdatePermissionState Mutation & null") {
				runTest {
					actionProcessor(grantFullPermissionAction).test {
						awaitItem() shouldBe (AlbumListMutation.UpdatePermissionState(PermissionState.Granted) to null)
						awaitComplete()
					}
				}
			}
		}

		Given("invoked GrantPartialPermission Action") {
			val grantPartialPermissionAction = AlbumListAction.GrantPartialPermission
			Then("emit UpdatePermissionState Mutation & null") {
				runTest {
					actionProcessor(grantPartialPermissionAction).test {
						awaitItem() shouldBe (AlbumListMutation.UpdatePermissionState(PermissionState.PartialGranted) to null)
						awaitComplete()
					}
				}
			}
		}

	}
}