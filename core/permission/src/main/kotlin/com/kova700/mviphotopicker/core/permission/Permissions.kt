package com.kova700.mviphotopicker.core.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat

val galleryPermissions =
	when {
		isAndroidVersionAtLeast34() -> arrayOf(
			Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
			Manifest.permission.READ_MEDIA_IMAGES
		)

		isAndroidVersionAtLeast33() -> arrayOf(
			Manifest.permission.READ_MEDIA_IMAGES
		)

		else -> arrayOf(
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
		)
	}

fun Context.isGalleryPermissionFullGranted(): Boolean {
	return galleryPermissions.all { permission -> isGranted(permission) }
}

fun Context.isGalleryPermissionPartialGranted(): Boolean {
	return isAndroidVersionAtLeast34()
					&& isGranted(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
					&& isGalleryPermissionFullGranted().not()
}

fun Context.isGalleryPermissionFullOrPartialGranted(): Boolean {
	return isGalleryPermissionFullGranted()
					|| isGalleryPermissionPartialGranted()
}

fun Context.isGranted(permission: String): Boolean {
	return ContextCompat.checkSelfPermission(
		this,
		permission
	) == PackageManager.PERMISSION_GRANTED
}

fun Context.getSettingIntent(): Intent {
	val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
	intent.data = Uri.parse("package:${this.packageName}")
	return intent
}

private fun isAndroidVersionAtLeast33(): Boolean {
	return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

private fun isAndroidVersionAtLeast34(): Boolean {
	return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
}