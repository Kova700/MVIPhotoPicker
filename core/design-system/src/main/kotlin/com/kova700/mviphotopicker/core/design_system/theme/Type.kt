package com.kova700.mviphotopicker.core.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kova700.mviphotopicker.core.design_system.R

val DefaultFontFamily = FontFamily(
	Font(R.font.roboto_bold, FontWeight.Bold),
	Font(R.font.roboto_medium, FontWeight.Medium),
	Font(R.font.roboto_regular, FontWeight.Normal),
)

val DefaultTypography = Typography(
	titleMedium = TextStyle(
		fontFamily = DefaultFontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 18.sp,
		lineHeight = 27.sp,
	),
	titleSmall = TextStyle(
		fontFamily = DefaultFontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 13.sp,
		lineHeight = 20.sp,
	),
	bodyMedium = TextStyle(
		fontFamily = DefaultFontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 13.sp,
		lineHeight = 20.sp,
	),
	bodySmall = TextStyle(
		fontFamily = DefaultFontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 11.sp,
		lineHeight = 16.sp,
	),
	labelMedium = TextStyle(
		fontFamily = DefaultFontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 12.sp,
		lineHeight = 18.sp,
	),
)