package com.kova700.mviphotopicker.feature.album_list.navigation

import AlbumListRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ALBUM_LIST_ROUTE = "album_list"

fun NavController.navigateToAlbumList(navOptions: NavOptions? = null) =
	navigate(
		route = ALBUM_LIST_ROUTE,
		navOptions = navOptions
	)

fun NavController.navigateToAlbumListClearingBackStack() =
	navigate(
		route = ALBUM_LIST_ROUTE,
		navOptions = NavOptions.Builder()
			.setPopUpTo(graph.startDestinationId, inclusive = false)
			.build()
	)

fun NavGraphBuilder.albumListScreen(
	navigateToPhotoList: (Long) -> Unit,
) {
	composable(route = ALBUM_LIST_ROUTE) {
		AlbumListRoute(
			navigateToPhotoList = navigateToPhotoList,
		)
	}
}