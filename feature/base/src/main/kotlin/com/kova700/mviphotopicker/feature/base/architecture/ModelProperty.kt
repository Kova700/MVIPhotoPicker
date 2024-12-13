package com.kova700.mviphotopicker.feature.base.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kova700.mviphotopicker.common.DispatcherProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified S : UiState, reified A : Action, reified M : Mutation, reified E : UiEvent> ViewModel.model(
	actionProcessors: Collection<ActionProcessor<A, M, E>>,
	reducers: Collection<Reducer<M, S>>,
	dispatcherProvider: DispatcherProvider,
	initState: S,
) = ModelProperty(
	viewModel = this,
	actionProcessors = actionProcessors,
	reducers = reducers,
	dispatcherProvider = dispatcherProvider,
	_uiState = MutableStateFlow(initState),
	_event = Channel(Channel.BUFFERED),
)

/**  getValue 함수를 통해 by 키워드로 delegation */
class ModelProperty<S : UiState, A : Action, M : Mutation, E : UiEvent>(
	private val viewModel: ViewModel,
	private val actionProcessors: Collection<ActionProcessor<A, M, E>>,
	private val reducers: Collection<Reducer<M, S>>,
	private val dispatcherProvider: DispatcherProvider,
	private val _uiState: MutableStateFlow<S>,
	private val _event: Channel<E>,
) : ReadOnlyProperty<Any, Model<S, A, M, E>> {
	override fun getValue(
		thisRef: Any,
		property: KProperty<*>,
	): Model<S, A, M, E> =
		Model(
			actionProcessors = actionProcessors,
			reducers = reducers,
			coroutineScope = viewModel.viewModelScope,
			dispatcherProvider = dispatcherProvider,
			_uiState = _uiState,
			_event = _event,
		)
}
