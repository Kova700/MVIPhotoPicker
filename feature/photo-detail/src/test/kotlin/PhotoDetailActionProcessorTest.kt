import app.cash.turbine.test
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.core.data.sticker.external.repository.StickerRepository
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailAction
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailActionProcessor
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailEvent
import com.kova700.mviphotopicker.feature.photo_detail.architecture.PhotoDetailMutation
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class PhotoDetailActionProcessorTest : BehaviorSpec() {
	private val fakeAlbumRepository = mockk<AlbumRepository>()
	private val fakeStickerRepository = mockk<StickerRepository>()

	private val actionProcessor = PhotoDetailActionProcessor(
		albumRepository = fakeAlbumRepository,
		stickerRepository = fakeStickerRepository,
	)
	private val fakePhotoUri = "TestURi"
	private val fakeAlbumId = 1L
	private val fakeAlbum = Album(
		id = fakeAlbumId,
		title = "test Title",
		photos = emptyList(),
		coverPhotoUri = "Test Uri"
	)
	private val fakeStickers = listOf(
		Sticker("TestUri 1"),
		Sticker("TestUri 2"),
	)

	init {
		Given("PhotoDetailAction.LoadPhoto") {
			val action = PhotoDetailAction.LoadPhoto(
				photoUri = fakePhotoUri,
				albumId = fakeAlbumId
			)

			When("albumRepository.getAlbum() returns album") {
				coEvery { fakeAlbumRepository.getAlbum(fakeAlbumId) } returns fakeAlbum

				Then("emit ShowInitLoader & ShowContent") {
					runTest {
						actionProcessor(action).test {
							awaitItem() shouldBe (PhotoDetailMutation.ShowInitLoader to null)
							awaitItem() shouldBe (PhotoDetailMutation.ShowContent(
								albumTitle = fakeAlbum.title,
								photoUri = fakePhotoUri
							) to null)
							awaitComplete()
						}
					}
				}
			}

			When("albumRepository.getAlbum() throw Exception") {
				coEvery { fakeAlbumRepository.getAlbum(fakeAlbumId) } throws Exception()

				Then("emit ShowInitLoader & ShowInitError") {
					runTest {
						actionProcessor(action).test {
							awaitItem() shouldBe (PhotoDetailMutation.ShowInitLoader to null)
							awaitItem() shouldBe (PhotoDetailMutation.ShowInitError to null)
							awaitComplete()
						}
					}
				}
			}
		}

		Given("PhotoDetailAction.LoadStickers") {
			val action = PhotoDetailAction.LoadStickers

			When("stickerRepository.getStickers() returns stickers") {
				coEvery { fakeStickerRepository.getStickers() } returns fakeStickers

				Then("emit ShowStickerInitLoader & ShowStickers") {
					runTest {
						actionProcessor(action).test {
							awaitItem() shouldBe (PhotoDetailMutation.ShowStickerInitLoader to null)
							awaitItem() shouldBe (PhotoDetailMutation.ShowStickers(fakeStickers) to null)
							awaitComplete()
						}
					}
				}
			}
			When("stickerRepository.getStickers() throw Exception") {
				coEvery { fakeStickerRepository.getStickers() } throws Exception()

				Then("emit ShowStickerInitLoader & ShowStickerInitError") {
					runTest {
						actionProcessor(action).test {
							awaitItem() shouldBe (PhotoDetailMutation.ShowStickerInitLoader to null)
							awaitItem() shouldBe (PhotoDetailMutation.ShowStickerInitError to null)
							awaitComplete()
						}
					}
				}
			}
		}


		Given("PhotoDetailAction.FinishSaveImages") {
			val action = PhotoDetailAction.FinishSaveImages

			Then("emit HideCombiningImagesLoader & NavigateToAlbumListEvent") {
				runTest {
					actionProcessor(action).test {
						awaitItem() shouldBe (PhotoDetailMutation.HideCombiningImagesLoader to PhotoDetailEvent.NavigateToAlbumList)
						awaitComplete()
					}
				}
			}
		}

	}

}