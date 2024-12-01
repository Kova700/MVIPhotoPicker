package com.kova700.mviphotopicker.core.data.sticker.internal.di

import com.kova700.mviphotopicker.core.data.sticker.external.repository.StickerRepository
import com.kova700.mviphotopicker.core.data.sticker.internal.repository.StickerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface StickerRepositoryModule {
	@Binds
	@Singleton
	fun bindStickerRepository(repository: StickerRepositoryImpl): StickerRepository
}