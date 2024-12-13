package com.kova700.mviphotopicker.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

class TestDispatcherProvider(
	private val scheduler: TestCoroutineScheduler,
) : DispatcherProvider {
	override val main: CoroutineDispatcher get() = StandardTestDispatcher(scheduler)
	override val mainImmediate get() = StandardTestDispatcher(scheduler)
	override val io: CoroutineDispatcher get() = StandardTestDispatcher(scheduler)
	override val default: CoroutineDispatcher get() = StandardTestDispatcher(scheduler)
}