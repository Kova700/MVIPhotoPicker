package com.kova700.mviphotopicker.feature.album_list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.component.DefaultButton
import com.kova700.mviphotopicker.core.design_system.theme.MediumBlue
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.feature.album_list.R

@Composable
fun NeedPermissionAlbum(
	onClickPerMissionRetry: () -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			modifier = Modifier
				.padding(horizontal = 40.dp),
			text = stringResource(id = R.string.album_need_permission_content),
			style = DefaultTypography.titleMedium,
			color = MediumBlue,
			textAlign = TextAlign.Center
		)
		DefaultButton(
			modifier = Modifier
				.padding(top = 30.dp),
			text = stringResource(id = R.string.allow_permission),
			onClick = onClickPerMissionRetry,
		)
	}
}

@Preview(showBackground = true)
@Composable
fun NeedPermissionAlbumPreview() {
	NeedPermissionAlbum(
		onClickPerMissionRetry = {}
	)
}