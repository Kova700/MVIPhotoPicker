package com.kova700.mviphotopicker.core.design_system.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.theme.DarkBlue
import com.kova700.mviphotopicker.core.design_system.theme.LightGray
import com.kova700.mviphotopicker.core.design_system.theme.DefaultTypography

@Composable
fun DefaultButton(
	modifier: Modifier = Modifier,
	text: String,
	onClick: () -> Unit
) {
	Button(
		modifier = modifier,
		colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
		onClick = onClick,
	) {
		Text(
			modifier = Modifier.padding(
				horizontal = 20.dp
			),
			text = text,
			color = LightGray,
			style = DefaultTypography.labelMedium,
		)
	}
}