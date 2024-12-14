import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.internal.repository.AlbumRepositoryImpl
import com.kova700.mviphotopicker.core.external_data.album.AlbumDataSource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class AlbumRepositoryTest : BehaviorSpec() {

	private val albumDataSource = mockk<AlbumDataSource>()
	private fun TestScope.albumRepository() = AlbumRepositoryImpl(
		albumDataSource = albumDataSource
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
		),
		Album(
			id = 3,
			title = "AAAA 1",
			photos = emptyList(),
			coverPhotoUri = "fakeUri"
		)
	)

	init {
		Given("AlbumDataSource return albums") {
			coEvery { albumDataSource.getAllAlbums() } returns fakeAlbums

			When("called AlbumRepository.getAlbums()") {
				runTest {
					val result = albumRepository().getAlbums()

					Then("return albums sorted by album title") {
						result shouldBe fakeAlbums.sortedBy(Album::title)
					}
				}
			}
		}

		Given("AlbumDataSource return emptyList") {
			coEvery { albumDataSource.getAllAlbums() } returns emptyList()

			When("called AlbumRepository.getAlbums()") {
				runTest {
					val result = albumRepository().getAlbums()

					Then("return emptyList") {
						result shouldBe emptyList()
					}
				}
			}
		}

	}
}