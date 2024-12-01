package com.kova700.mviphotopicker.feature.base.architecture

/** Mutation을 바탕으로 UiState를 생성
 *  ViewModel의 여러 함수에서 State의 직접적인 수정 방지 */
interface Reducer<M : Mutation, S : UiState> {
	operator fun invoke(mutation: M, currentState: S): S
}