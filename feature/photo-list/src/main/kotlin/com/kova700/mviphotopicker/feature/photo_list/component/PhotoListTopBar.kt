package com.kova700.mviphotopicker.feature.photo_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.R
import com.kova700.mviphotopicker.core.design_system.component.DefaultTopBar
import com.kova700.mviphotopicker.core.design_system.theme.DarkBlue
import com.kova700.mviphotopicker.core.design_system.theme.SoftYellow
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography

@Composable
fun PhotoListTopBar(
	albumTitle: String,
	onClickBack: () -> Unit
) {
	DefaultTopBar(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 10.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
		) {
			IconButton(
				modifier = Modifier,
				onClick = onClickBack
			) {
				Icon(
					imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
					contentDescription = "back",
					tint = DarkBlue
				)
			}
			Box(
				modifier = Modifier
					.padding(
						start = 8.dp,
						end = 8.dp
					)
					.height(26.dp)
					.width(1.dp)
					.background(SoftYellow)
			)
			Text(
				text = albumTitle,
				color = DarkBlue,
				style = DefaultTypography.titleMedium
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PhotoListTopBarPreview() {
	PhotoListTopBar(
		albumTitle = "Album Title",
		onClickBack = { }
	)
}