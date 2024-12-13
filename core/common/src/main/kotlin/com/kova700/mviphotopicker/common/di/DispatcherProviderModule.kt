package com.kova700.mviphotopicker.common.di

import com.kova700.mviphotopicker.common.DispatcherProvider
import com.kova700.mviphotopicker.common.StandardDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal interface DispatcherProviderModule {
	@Binds
	@Singleton
	fun bindDispatcherProvider(dispatcherProvider: StandardDispatcherProvider): DispatcherProvider
}