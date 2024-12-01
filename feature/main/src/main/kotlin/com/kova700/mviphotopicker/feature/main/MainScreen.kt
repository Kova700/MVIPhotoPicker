package com.kova700.mviphotopicker.feature.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kova700.mviphotopicker.feature.main.navigation.TopNavHost

@Composable
fun MainRoute(
	navController: NavHostController = rememberNavController()
) {
	MainScreen(navController)
}

@Composable
fun MainScreen(navController: NavHostController) {
	TopNavHost(navController = navController)
}