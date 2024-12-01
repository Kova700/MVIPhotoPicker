package com.kova700.mviphotopicker.core.data.sticker.internal.repository

import android.content.Context
import android.net.Uri
import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker
import com.kova700.mviphotopicker.core.data.sticker.external.repository.StickerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StickerRepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : StickerRepository {
	override fun getStickers(): List<Sticker> {
		return getUriFromAssetFolder(
			folder = "sticker",
			extension = SVG_EXTENSION
		).map { Sticker(it) }
			.toList()
	}

	private fun getUriFromAssetFolder(
		folder: String,
		extension: String,
	): Sequence<Uri> {
		val assetManager = context.assets
		val fileNames = assetManager.list(folder) ?: emptyArray()
		return fileNames
			.asSequence()
			.filter { it.endsWith(extension) }
			.map { fileName -> Uri.parse("$ASSET_PATH$folder/$fileName") }
	}

	companion object {
		private const val SVG_EXTENSION = ".svg"
		private const val ASSET_PATH = "file:///android_asset/"
	}
}