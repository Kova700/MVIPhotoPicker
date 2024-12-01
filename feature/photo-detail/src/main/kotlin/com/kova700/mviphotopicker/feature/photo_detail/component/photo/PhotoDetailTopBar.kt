package com.kova700.mviphotopicker.feature.photo_detail.component.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.R
import com.kova700.mviphotopicker.core.design_system.component.DefaultButton
import com.kova700.mviphotopicker.core.design_system.component.DefaultTopBar
import com.kova700.mviphotopicker.core.design_system.theme.DarkBlue
import com.kova700.mviphotopicker.core.design_system.theme.SoftYellow
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography
import com.kova700.mviphotopicker.feature.photo_detail.R as photoDetailR

@Composable
fun PhotoDetailTopBar(
	albumTitle: String,
	isStickerSelected: Boolean,
	onClickBack: () -> Unit,
	onClickOverlay: () -> Unit,
) {
	DefaultTopBar(
		modifier = Modifier
			.fillMaxWidth()
			.padding(
				start = 10.dp,
				end = 20.dp
			)
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
				style = DefaultTypography.titleMedium,
				modifier = Modifier.weight(1f),
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)

			if (isStickerSelected) {
				DefaultButton(
					text = stringResource(id = photoDetailR.string.overlay),
					onClick = onClickOverlay,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun IsStickerSelectedPhotoDetailTopBarPreview() {
	PhotoDetailTopBar(
		albumTitle = "Album Title",
		isStickerSelected = true,
		onClickBack = { },
		onClickOverlay = { },
	)
}

@Preview(showBackground = true)
@Composable
fun IsStickerUnSelectedPhotoDetailTopBarPreview() {
	PhotoDetailTopBar(
		albumTitle = "Album Title",
		isStickerSelected = false,
		onClickBack = { },
		onClickOverlay = { },
	)
}