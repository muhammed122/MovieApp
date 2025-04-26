package com.example.utils.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


abstract class FlowPagingUseCase<in Params, Result>(private val coroutineDispatcher: CoroutineDispatcher) {

    @ExperimentalCoroutinesApi
    operator fun invoke(parameters: Params): Flow<Result> =
        execute(parameters)
            .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: Params): Flow<Result>


}