package com.schibsted.nde.data.common

import com.schibsted.nde.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NetworkBoundResource @Inject constructor() {

    inline operator fun <QueryResult, FetchResult> invoke(
        crossinline query: () -> Flow<QueryResult>,
        crossinline fetch: suspend () -> FetchResult,
        crossinline saveFetched: suspend (FetchResult) -> Unit,
        shouldFetch: Boolean
    ): Flow<Result<QueryResult>> = flow {
        val flow: Flow<Result<QueryResult>> = runCatching {
            if (shouldFetch) {
                emit(Result.Loading())
                saveFetched(fetch())
            }
            query().map { Result.Success(it) }
        }.getOrElse { e ->
            flowOf(Result.Error(e))
        }

        emitAll(flow)
    }
}