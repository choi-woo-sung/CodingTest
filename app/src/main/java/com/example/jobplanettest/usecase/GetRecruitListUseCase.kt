package com.example.jobplanettest.usecase

import com.example.jobplanettest.model.RecruitItem
import com.example.jobplanettest.repository.RecruitRepository
import com.example.jobplanettest.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias GetRecruitListBaseUseCase = BaseUseCase<Unit, Flow<UiState<List<RecruitItem>>>>

class GetRecruitListUseCase @Inject constructor(
    private val repo: RecruitRepository
) : GetRecruitListBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<UiState<List<RecruitItem>>> = flow {
        emit(UiState.Loading)
        runCatching {
            repo.fetchRecruitInfo()
        }.onSuccess { result ->
            emit(UiState.Success(result.recruitItems))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }
}
