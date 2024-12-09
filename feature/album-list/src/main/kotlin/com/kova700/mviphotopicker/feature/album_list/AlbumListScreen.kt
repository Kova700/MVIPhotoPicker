import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kova700.mviphotopicker.core.design_system.theme.LightGray
import com.kova700.mviphotopicker.core.permission.galleryPermissions
import com.kova700.mviphotopicker.core.permission.getSettingIntent
import com.kova700.mviphotopicker.core.permission.isGalleryPermissionFullGranted
import com.kova700.mviphotopicker.core.permission.isGalleryPermissionPartialGranted
import com.kova700.mviphotopicker.feature.album_list.AlbumListScreenPreviewParameterProvider
import com.kova700.mviphotopicker.feature.album_list.AlbumListViewModel
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListAction
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListEvent
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListState
import com.kova700.mviphotopicker.feature.album_list.architecture.AlbumListUiState
import com.kova700.mviphotopicker.feature.album_list.component.AlbumListTopBar
import com.kova700.mviphotopicker.feature.album_list.component.EmptyAlbum
import com.kova700.mviphotopicker.feature.album_list.component.InitErrorAlbum
import com.kova700.mviphotopicker.feature.album_list.component.LoadingAlbum
import com.kova700.mviphotopicker.feature.album_list.component.NeedPermissionAlbum
import com.kova700.mviphotopicker.feature.album_list.component.result.AlbumResult

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AlbumListRoute(
	navigateToPhotoList: (Long) -> Unit,
	albumListViewModel: AlbumListViewModel = hiltViewModel(),
) {
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current
	val albumListState by albumListViewModel.uiState.collectAsStateWithLifecycle()

	val galleryPermissionState = rememberMultiplePermissionsState(
		permissions = galleryPermissions.toList(),
	)

	/** 사진 부분선택의 경우 선택된 사진들이 실시간 반영이 되지않아 LifeCycle observing */
	DisposableEffect(lifecycleOwner) {
		val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_RESUME) {
				when {
					context.isGalleryPermissionFullGranted() -> {
						albumListViewModel.process(AlbumListAction.GrantFullPermission)
						albumListViewModel.process(AlbumListAction.LoadInitAlbums)
					}

					context.isGalleryPermissionPartialGranted() -> {
						albumListViewModel.process(AlbumListAction.GrantPartialPermission)
						albumListViewModel.process(AlbumListAction.LoadInitAlbums)
					}

					else -> albumListViewModel.process(AlbumListAction.DenyPermission)
				}
			}
		}

		lifecycleOwner.lifecycle.addObserver(observer)

		onDispose {
			lifecycleOwner.lifecycle.removeObserver(observer)
		}
	}

	fun requestPermission() {
		context.startActivity(context.getSettingIntent())
	}

	LaunchedEffect(lifecycleOwner.lifecycle) {
		lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
			albumListViewModel.event.collect { event ->
				when (event) {
					is AlbumListEvent.NavigateToPhotoList -> navigateToPhotoList(event.albumId)
					AlbumListEvent.RequestPermission -> requestPermission()
					AlbumListEvent.CheckPermission -> galleryPermissionState.launchMultiplePermissionRequest()
				}
			}
		}
	}

	AlbumListScreen(
		albumListState = albumListState,
		onClickAlbum = { id -> albumListViewModel.process(AlbumListAction.ClickAlbum(id)) },
		onClickInitRetry = { albumListViewModel.process(AlbumListAction.LoadInitAlbums) },
		onClickPerMissionRetry = { albumListViewModel.process(AlbumListAction.RequestPermission) },
		onClickFullPermissionGranted = { albumListViewModel.process(AlbumListAction.RequestPermission) }
	)
}

@Composable
fun AlbumListScreen(
	albumListState: AlbumListState,
	onClickInitRetry: () -> Unit,
	onClickPerMissionRetry: () -> Unit,
	onClickFullPermissionGranted: () -> Unit,
	onClickAlbum: (Long) -> Unit,
) {
	Scaffold(
		containerColor = LightGray,
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
		) {
			AlbumListTopBar()

			when (albumListState.uiState) {
				is AlbumListUiState.AlbumItems -> AlbumResult(
					albums = albumListState.uiState.albums,
					permissionState = albumListState.permissionState,
					onClickAlbum = onClickAlbum,
					onClickFullPermissionGranted = onClickFullPermissionGranted
				)

				AlbumListUiState.Empty -> EmptyAlbum()
				AlbumListUiState.InitError -> InitErrorAlbum(
					onClickInitRetry = onClickInitRetry
				)

				AlbumListUiState.InitLoading -> LoadingAlbum()
				AlbumListUiState.NeedPermission -> NeedPermissionAlbum(
					onClickPerMissionRetry = onClickPerMissionRetry
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun AlbumListScreenPreview(
	@PreviewParameter(AlbumListScreenPreviewParameterProvider::class)
	albumListState: AlbumListState
) {
	AlbumListScreen(
		albumListState = albumListState,
		onClickInitRetry = {},
		onClickPerMissionRetry = {},
		onClickAlbum = {},
		onClickFullPermissionGranted = {}
	)
}

@Preview(showBackground = true)
@Composable
fun EmptyAlbumListScreenPreview() {
	AlbumListScreen(
		albumListState = AlbumListState.DEFAULT.copy(
			uiState = AlbumListUiState.Empty,
		),
		onClickInitRetry = {},
		onClickPerMissionRetry = {},
		onClickAlbum = {},
		onClickFullPermissionGranted = {}
	)
}


@Preview(showBackground = true)
@Composable
fun InitErrorAlbumListScreenPreview() {
	AlbumListScreen(
		albumListState = AlbumListState.DEFAULT.copy(
			uiState = AlbumListUiState.InitError
		),
		onClickInitRetry = {},
		onClickPerMissionRetry = {},
		onClickAlbum = {},
		onClickFullPermissionGranted = {}
	)
}

@Preview(showBackground = true)
@Composable
fun NeedPermissionAlbumListScreenPreview() {
	AlbumListScreen(
		albumListState = AlbumListState.DEFAULT.copy(
			uiState = AlbumListUiState.NeedPermission
		),
		onClickInitRetry = {},
		onClickPerMissionRetry = {},
		onClickAlbum = {},
		onClickFullPermissionGranted = {}
	)
}