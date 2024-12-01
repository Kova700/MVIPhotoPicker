package com.kova700.mviphotopicker.core.design_system.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kova700.mviphotopicker.core.design_system.theme.LightGray

@Composable
fun DefaultTopBar(
	modifier: Modifier = Modifier,
	content: @Composable ColumnScope.() -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth(),
		shape = RoundedCornerShape(0.dp),
		colors = CardDefaults.cardColors(
			containerColor = LightGray
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 10.dp
		)
	) {
		Column(
			modifier = modifier
				.fillMaxWidth()
				.padding(
					top = 40.dp,
				),
		) {
			content()
		}
	}
}


@Preview(showBackground = true)
@Composable
fun DefaultTopBarPreview() {
	DefaultTopBar {

	}
}