package com.kova700.mviphotopicker.feature.photo_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kova700.mviphotopicker.feature.photo_detail.PhotoDetailRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val SELECTED_PHOTO_Uri = "selected_photo_uri"
const val SELECTED_PHOTO_ALBUM_ID = "selected_photo_album_id"
private const val PHOTO_DETAIL_ROUTE_BASE = "photo_detail"
private const val PHOTO_DETAIL_ROUTE =
	"$PHOTO_DETAIL_ROUTE_BASE/{$SELECTED_PHOTO_ALBUM_ID}/{$SELECTED_PHOTO_Uri}"

fun NavController.navigateToPhotoDetail(
	uri: String,
	albumId: Long,
	navOptions: NavOptions? = null
) {
	val encodedUri = URLEncoder.encode(uri, StandardCharsets.UTF_8.toString())
	navigate(
		route = "$PHOTO_DETAIL_ROUTE_BASE/$albumId/$encodedUri",
		navOptions = navOptions
	)
}

fun NavGraphBuilder.photoDetailScreen(
	navigateToAlbumListClearingBackStack: () -> Unit,
	navigateToPrevious: () -> Unit,
) {
	composable(
		route = PHOTO_DETAIL_ROUTE,
		arguments = listOf(
			navArgument(SELECTED_PHOTO_ALBUM_ID) { type = NavType.LongType },
			navArgument(SELECTED_PHOTO_Uri) { type = NavType.StringType }
		)
	) {
		PhotoDetailRoute(
			navigateToAlbumListClearingBackStack = navigateToAlbumListClearingBackStack,
			navigateToPrevious = navigateToPrevious
		)
	}
}