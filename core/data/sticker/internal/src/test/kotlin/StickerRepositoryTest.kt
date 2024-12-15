import android.content.Context
import android.content.res.AssetManager
import com.kova700.mviphotopicker.common.TestDispatcherProvider
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.core.data.sticker.internal.repository.StickerRepositoryImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class StickerRepositoryTest : BehaviorSpec() {
	private val mockContext = mockk<Context>()
	private val mockAssetManager = mockk<AssetManager>()

	private fun TestScope.stickerRepository() = StickerRepositoryImpl(
		context = mockContext,
		dispatcherProvider = TestDispatcherProvider(testScheduler)
	)


	private val testFolderName = "sticker"
	private val SVG_EXTENSION = "svg"
	private val ASSET_PATH = "file:///android_asset/"

	private val fakeStickerFiles = arrayOf<String>(
		"testFileName1.$SVG_EXTENSION",
		"testFileName2.$SVG_EXTENSION",
		"testFileName3.$SVG_EXTENSION",
	)

	init {
		Given("Sticker assets is not empty") {
			every { mockContext.assets } returns mockAssetManager
			every { mockAssetManager.list(testFolderName) } returns fakeStickerFiles

			When("called StickerRepository.getStickers()") {
				runTest {
					val result = stickerRepository().getStickers()

					Then("return stickers") {
						result shouldBe fakeStickerFiles.map { fileName ->
							Sticker("$ASSET_PATH$testFolderName/$fileName")
						}
					}
				}
			}
		}

		Given("Sticker assets is empty") {
			every { mockContext.assets } returns mockAssetManager
			every { mockAssetManager.list(testFolderName) } returns emptyArray()

			When("called StickerRepository.getStickers()") {
				runTest {
					val result = stickerRepository().getStickers()

					Then("return emptyList") {
						result shouldBe emptyList()
					}
				}
			}
		}

	}
}