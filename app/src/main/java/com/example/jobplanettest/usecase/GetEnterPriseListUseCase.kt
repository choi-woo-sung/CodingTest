package com.example.jobplanettest.usecase

import com.example.jobplanettest.model.CellItem
import com.example.jobplanettest.repository.EnterpriseRepository
import com.example.jobplanettest.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias GetEnterPriseListBaseUseCase = BaseUseCase<Unit, Flow<UiState<List<CellItem>>>>

class GetEnterPriseListUseCase @Inject constructor(
    private val repo: EnterpriseRepository
) : GetEnterPriseListBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<UiState<List<CellItem>>> = flow {
        emit(UiState.Loading)
        runCatching {
            repo.fetchEnterpriseInfo()
        }.onSuccess { result ->
            emit(UiState.Success(result.cell_items))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }
}
