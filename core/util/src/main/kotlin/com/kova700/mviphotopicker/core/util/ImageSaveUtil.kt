package com.kova700.mviphotopicker.core.util

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Context.saveImageToGallery(bitmap: Bitmap) = withContext(Dispatchers.IO) {
	if (isAndroidVersionAtLeastQ()) {
		saveImageToScopedStorage(
			bitmap = bitmap,
			fileName = "${System.currentTimeMillis()}$JPEG_EXTENSION",
			fileType = IMAGE_TYPE_JPEG,
		)
		return@withContext
	}

	saveImageToLegacyExternalStorage(
		bitmap = bitmap,
		fileName = "${System.currentTimeMillis()}$JPEG_EXTENSION",
		fileType = IMAGE_TYPE_JPEG,
	)
}

private fun Context.saveImageToScopedStorage(
	bitmap: Bitmap,
	fileName: String,
	fileType: String,
) {
	val contentValues = ContentValues().apply {
		put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
		put(MediaStore.Images.Media.MIME_TYPE, fileType)
		put(MediaStore.Images.Media.RELATIVE_PATH, "$DCIM/$PROJECT_DIRECTORY")
		put(MediaStore.Images.Media.IS_PENDING, 1)
	}

	val imageUri =
		contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: return

	val outputStream = contentResolver.openOutputStream(imageUri)
	outputStream?.use { stream ->
		bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_COMPRESSION_RATE, stream)
	}

	contentValues.clear()
	contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
	contentResolver.update(imageUri, contentValues, null, null)
}

private fun Context.saveImageToLegacyExternalStorage(
	bitmap: Bitmap,
	fileName: String,
	fileType: String,
) {
	if (ContextCompat.checkSelfPermission(
			this,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
		) != PackageManager.PERMISSION_GRANTED
	) return

	val contentValues = ContentValues().apply {
		put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
		put(MediaStore.Images.Media.MIME_TYPE, fileType)
		put(
			MediaStore.Images.Media.DATA,
			"${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)}" +
							"/$PROJECT_DIRECTORY/$fileName"
		)
	}

	val imageUri =
		contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: return

	val outputStream = contentResolver.openOutputStream(imageUri)
	outputStream?.use { stream ->
		bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_COMPRESSION_RATE, stream)
	}

}

private fun isAndroidVersionAtLeastQ(): Boolean {
	return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

private const val PROJECT_DIRECTORY = "MVIPhotoPicker"
private const val DCIM = "DCIM"
private const val JPEG = "jpeg"
private const val JPEG_EXTENSION = ".$JPEG"
private const val IMAGE_TYPE_JPEG = "image/$JPEG"
private const val IMAGE_COMPRESSION_RATE = 100
