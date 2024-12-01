package com.kova700.mviphotopicker.core.data.sticker.external.repository

import com.kova700.mviphotopicker.core.data.sticker.external.model.Sticker

interface StickerRepository {
	fun getStickers(): List<Sticker>
}