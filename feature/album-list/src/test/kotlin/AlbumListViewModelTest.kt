import com.kova700.mviphotopicker.feature.album_list.AlbumListViewModel
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

//ViewModel에서의 Test = ACtion을 넣고 정해진 Event와 State가 나오는지 확인
//ActionProcessor에서의 Test = Action을 넣고 정해진 Mutation과 Event가 나오는지 확인
//Reducer에서의 Test = Mutation을 넣고 정해진 State가 나오는지 확인
//Repository에서의 Test = 함수 호출시 올바른 데이터가 나오는지 확인
class AlbumListViewModelTest {

	//Mock은 ActionProcessor와 Reducer를 각각 테스트 할때 사용하고
	//ViewModel 테스트할때는 그냥 Fake 객체 사용하는데 이유가 뭐지
	private val albumListViewModel = AlbumListViewModel(
		albumListActionProcessor = mockk(),
		albumListUserActionProcessor = mockk(),
		albumListReducer = mockk()
	)

	//Init Load시 상태가 순서대로 변경되는지 확인
	@Test
	fun `asd`() = runTest {

	}
}