package com.kova700.mviphotopicker.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kova700.mviphotopicker.feature.photo_detail.navigation.navigateToPhotoDetail
import com.kova700.mviphotopicker.feature.photo_detail.navigation.photoDetailScreen
import com.kova700.mviphotopicker.feature.album_list.navigation.ALBUM_LIST_ROUTE
import com.kova700.mviphotopicker.feature.album_list.navigation.albumListScreen
import com.kova700.mviphotopicker.feature.album_list.navigation.navigateToAlbumListClearingBackStack
import com.kova700.mviphotopicker.feature.photo_list.navigation.navigateToPhotoList
import com.kova700.mviphotopicker.feature.photo_list.navigation.photoListScreen

@Composable
fun TopNavHost(
	navController: NavHostController,
	startDestination: String = ALBUM_LIST_ROUTE
) {
	NavHost(
		navController = navController,
		startDestination = startDestination,
	) {
		albumListScreen(
			navigateToPhotoList = navController::navigateToPhotoList
		)
		photoListScreen(
			navigateToPhotoDetail = navController::navigateToPhotoDetail,
			navigateToPrevious = navController::popBackStack
		)
		photoDetailScreen(
			navigateToAlbumListClearingBackStack = navController::navigateToAlbumListClearingBackStack,
			navigateToPrevious = navController::popBackStack
		)
	}
}