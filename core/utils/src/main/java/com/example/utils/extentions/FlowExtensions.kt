package com.example.utils.extentions

import com.example.utils.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


fun <T> Flow<T>.obtainOutcome(): Flow<Resource<T>> =
    this.map {
        Resource.success(it)
    }
    .onStart {
        emit(Resource.Loading)
    }
    .catch { e ->
        emit(Resource.Failure(e))
    }

