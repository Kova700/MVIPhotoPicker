package com.kova700.mviphotopicker.feature.album_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.component.DefaultTopBar
import com.kova700.mviphotopicker.core.design_system.theme.DarkBlue
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.feature.album_list.R

@Composable
fun AlbumListTopBar() {
	DefaultTopBar(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 28.dp)
	) {
		Text(
			text = stringResource(R.string.album_list),
			color = DarkBlue,
			style = DefaultTypography.titleMedium
		)
		Column(
			modifier = Modifier
				.padding(top = 30.dp)
		) {
			Text(
				text = stringResource(R.string.my_albums),
				color = MediumBlue,
				style = DefaultTypography.titleSmall
			)
			Box(
				modifier = Modifier
					.padding(top = 10.dp)
					.height(2.dp)
					.width(120.dp)
					.background(DarkBlue)
			)
		}
	}
}

@Preview
@Composable
fun AlbumListTopBarPreview() {
	AlbumListTopBar()
}