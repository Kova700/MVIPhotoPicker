package com.kova700.mviphotopicker.common

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
	val main: CoroutineDispatcher
	val mainImmediate: CoroutineDispatcher
	val io: CoroutineDispatcher
	val default: CoroutineDispatcher
}