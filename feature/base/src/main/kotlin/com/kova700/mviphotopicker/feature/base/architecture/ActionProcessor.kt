package com.kova700.mviphotopicker.feature.base.architecture

import kotlinx.coroutines.flow.Flow

/** Intent를 바탕으로 Mutation, UiEvent 생성
 * 비대해진 Viewmodel에서 비즈니스 로직을 분리 */
interface ActionProcessor<A : Action, M : Mutation, E : UiEvent>{
	operator fun invoke(action: A): Flow<Pair<M?, E?>>
}