package com.kova700.mviphotopicker.feature.album_list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.feature.album_list.R

@Composable
fun EmptyAlbum() {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = stringResource(id = R.string.album_does_not_exist),
			style = DefaultTypography.titleMedium,
			color = MediumBlue
		)
	}
}

@Preview(showBackground = true)
@Composable
internal fun PreviewEmptyAlbum() {
	EmptyAlbum()
}