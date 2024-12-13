package com.kova700.mviphotopicker.feature.base.architecture

import com.kova700.mviphotopicker.common.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** 비대해지는 Viewmodel을 분리하기 위한 Model 클래스
 *  상태관리 로직 Reducer로 이전
 *  비즈니스로직 ActionProcessor로 이전
 * */
class Model<S : UiState, A : Action, M : Mutation, E : UiEvent>(
	private val actionProcessors: Collection<ActionProcessor<A, M, E>>,
	private val reducers: Collection<Reducer<M, S>>,
	private val coroutineScope: CoroutineScope,
	private val dispatcherProvider: DispatcherProvider,
	private val _uiState: MutableStateFlow<S>,
	private val _event: Channel<E>,
) {
	val uiState get() = _uiState.asStateFlow()
	val event get() = _event.receiveAsFlow()

	fun process(action: A) = coroutineScope.launch(dispatcherProvider.default) {
		actionProcessors.map { actionProcessor -> actionProcessor(action) }
			.merge()
			.collect { (mutation, event) ->
				mutation?.let { handleMutation(it) }
				event?.let { _event.send(it) }
			}
	}

	private fun handleMutation(mutation: M) {
		reducers.asIterable()
			.forEach { reducer ->
				_uiState.update { currentState -> reducer(mutation, currentState) }
			}
	}
}