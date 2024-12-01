package com.kova700.mviphotopicker.core.data.album.internal.di

import com.kova700.mviphotopicker.core.data.album.external.repository.AlbumRepository
import com.kova700.mviphotopicker.core.data.album.internal.repository.AlbumRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AlbumRepositoryModule {
	@Binds
	@Singleton
	fun bindAlbumRepository(repository: AlbumRepositoryImpl): AlbumRepository
}