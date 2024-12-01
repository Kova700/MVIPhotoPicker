package com.kova700.mviphotopicker.feature.photo_detail.component.sticker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue

@Composable
fun LoadingStickers(
) {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator(
			modifier = Modifier.align(Alignment.Center),
			color = MediumBlue
		)
	}
}