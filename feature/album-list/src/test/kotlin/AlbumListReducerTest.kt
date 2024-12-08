import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListMutation
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListReducer
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUiState
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.toImmutableList
import org.junit.Test

class AlbumListReducerTest {
	private val reducer = AlbumListReducer()

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

	@Test
	fun `invoke ShowInitLoader returns new state`() {
		reducer.invoke(AlbumListMutation.ShowInitLoader, AlbumListState.DEFAULT) shouldBe
						AlbumListState.DEFAULT.copy(
							uiState = AlbumListUiState.InitLoading
						)
	}

	@Test
	fun `invoke ShowInitError returns new state`() {
		reducer.invoke(AlbumListMutation.ShowInitError, AlbumListState.DEFAULT) shouldBe
						AlbumListState.DEFAULT.copy(
							uiState = AlbumListUiState.InitError
						)
	}

	@Test
	fun `invoke ShowContent returns new state`() {
		reducer.invoke(AlbumListMutation.ShowContent(fakeAlbums), AlbumListState.DEFAULT) shouldBe
						AlbumListState.DEFAULT.copy(
							uiState = AlbumListUiState.AlbumItems(
								albums = fakeAlbums.toImmutableList()
							)
						)
	}

	@Test
	fun `invoke NeedPermission returns new state`() {
		reducer.invoke(AlbumListMutation.ShowNeedPermission, AlbumListState.DEFAULT) shouldBe
						AlbumListState.DEFAULT.copy(
							uiState = AlbumListUiState.NeedPermission
						)
	}

	@Test
	fun `invoke UpdatePermissionState returns new state`() {
		val fakePermissionState = AlbumListState.PermissionState.Denied
		reducer.invoke(
			AlbumListMutation.UpdatePermissionState(fakePermissionState),
			AlbumListState.DEFAULT
		) shouldBe
						AlbumListState.DEFAULT.copy(
							permissionState = fakePermissionState
						)
	}
}