package com.kova700.mviphotopicker.core.external_data.internal.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentResolverModule {

	@Provides
	@Singleton
	fun provideContentResolver(
		@ApplicationContext context: Context
	): ContentResolver {
		return context.contentResolver
	}
}