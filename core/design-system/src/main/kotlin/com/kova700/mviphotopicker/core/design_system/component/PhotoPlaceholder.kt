package com.kova700.mviphotopicker.core.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kova700.mviphotopicker.core.design_system.theme.SoftYellow

@Composable
fun PhotoPlaceholder(
	modifier: Modifier = Modifier
) {
	Box(
		modifier
			.background(SoftYellow)
	)
}