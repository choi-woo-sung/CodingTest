package com.example.jobplanettest.usecase

import com.example.jobplanettest.model.Recruit
import com.example.jobplanettest.repository.RecruitRepository
import com.example.jobplanettest.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias GetRecruitListBaseUseCase = BaseUseCase<Unit, Flow<UiState<Recruit>>>

class GetRecruitListUseCase @Inject constructor(
    private val repo: RecruitRepository
) : GetRecruitListBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<UiState<Recruit>> = flow {
        emit(UiState.Loading)
        runCatching {
            repo.fetchProfileImage()
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }
}
