package com.kova700.mviphotopicker.core.external_data.album

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.kova700.mviphotopicker.core.data.album.external.model.Album
import com.kova700.mviphotopicker.core.data.album.external.model.Photo
import java.io.File
import java.util.Date
import javax.inject.Inject

/** 페이징을 고려헀으나 갤러리 특성상 직관적인 UI를 위해 단일 쿼리 선택 */
class AlbumDataSource @Inject constructor(
	private val contentResolver: ContentResolver
) {

	fun getAllAlbums(): List<Album> {

		val projection = arrayOf(
			INDEX_PHOTO_ID,
			INDEX_PHOTO_URI,
			INDEX_ALBUM_ID,
			INDEX_ALBUM_NAME,
			INDEX_DATE_ADDED,
		)

		val sortOrder = "$INDEX_DATE_ADDED DESC"

		val cursor = contentResolver.query(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			projection,
			null,
			null,
			sortOrder
		) ?: return emptyList()

		val rows = mutableListOf<AlbumPhotoDTO>()
		cursor.use {
			while (it.moveToNext()) {
				rows.add(it.getRow() ?: continue)
			}
		}
		return rows
			.groupBy { it.albumId }
			.map { (albumId, rows) ->
				Album(
					id = albumId,
					title = rows.first().albumName,
					photos = rows.map { row ->
						Photo(
							id = row.photoId,
							albumId = albumId,
							uri = row.photoUri,
							dateAdded = Date(row.dateAddedInSeconds)
						)
					},
					coverPhotoUri = rows.first().photoUri
				)
			}
	}

	private fun Cursor.getRow(): AlbumPhotoDTO? =
		try {
			val photoId = getLong(getColumnIndexOrThrow(INDEX_PHOTO_ID))
			val photoUri = getMediaUri()
			val albumId = getLong(getColumnIndexOrThrow(INDEX_ALBUM_ID))
			val albumName = getString(getColumnIndexOrThrow(INDEX_ALBUM_NAME))
			val dateAddedInSeconds = getLong(getColumnIndexOrThrow(INDEX_DATE_ADDED))

			AlbumPhotoDTO(
				photoId = photoId,
				photoUri = photoUri,
				albumId = albumId,
				albumName = albumName,
				dateAddedInSeconds = dateAddedInSeconds
			)
		} catch (exception: Exception) {
			exception.printStackTrace()
			null
		}

	private fun Cursor.getMediaUri(): Uri =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			val id = getLong(getColumnIndexOrThrow(INDEX_PHOTO_ID))
			ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
		} else {
			val mediaPath = getString(getColumnIndexOrThrow(INDEX_PHOTO_URI))
			Uri.fromFile(File(mediaPath))
		}

	companion object {
		private const val INDEX_PHOTO_ID = MediaStore.MediaColumns._ID
		private const val INDEX_PHOTO_URI = MediaStore.MediaColumns.DATA
		private const val INDEX_ALBUM_ID = MediaStore.MediaColumns.BUCKET_ID
		private const val INDEX_ALBUM_NAME = MediaStore.MediaColumns.BUCKET_DISPLAY_NAME
		private const val INDEX_DATE_ADDED = MediaStore.MediaColumns.DATE_ADDED
	}
}