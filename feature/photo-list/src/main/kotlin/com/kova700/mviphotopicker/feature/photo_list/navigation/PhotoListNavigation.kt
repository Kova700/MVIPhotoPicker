package com.kova700.mviphotopicker.feature.photo_list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kova700.mviphotopicker.feature.photo_list.PhotoListRoute

const val SELECTED_ALBUM_ID = "selected_album_id"
private const val PHOTO_LIST_ROUTE_BASE = "photo_list"
private const val PHOTO_LIST_ROUTE = "$PHOTO_LIST_ROUTE_BASE/{$SELECTED_ALBUM_ID}"

fun NavController.navigateToPhotoList(albumId: Long, navOptions: NavOptions? = null) =
	navigate(
		route = "$PHOTO_LIST_ROUTE_BASE/$albumId",
		navOptions = navOptions
	)

fun NavGraphBuilder.photoListScreen(
	navigateToPhotoDetail: (photoUri: String, albumId: Long) -> Unit,
	navigateToPrevious: () -> Unit
) {
	composable(
		route = PHOTO_LIST_ROUTE,
		arguments = listOf(
			navArgument(SELECTED_ALBUM_ID) { type = NavType.LongType }
		)) {
		PhotoListRoute(
			navigateToPhotoDetail = navigateToPhotoDetail,
			navigateToPrevious = navigateToPrevious,
		)
	}
}