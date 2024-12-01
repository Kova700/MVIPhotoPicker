package com.kova700.mviphotopicker.feature.photo_detail.component.sticker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.component.DefaultButton
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.feature.photo_detail.R

@Composable
fun InitErrorStickers(
	onClickRetry: () -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = stringResource(id = R.string.sticker_load_failed),
			style = DefaultTypography.titleMedium,
			color = MediumBlue
		)
		DefaultButton(
			modifier = Modifier
				.padding(top = 30.dp),
			text = stringResource(id = R.string.retry),
			onClick = onClickRetry,
		)
	}
}

@Preview(showBackground = true)
@Composable
fun InitErrorStickersPreview() {
	InitErrorStickers(onClickRetry = {})
}