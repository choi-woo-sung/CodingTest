package com.example.jobplanettest.repository.imp

import com.example.jobplanettest.model.Recruit
import com.example.jobplanettest.network.JobPlanetAPI
import com.example.jobplanettest.repository.RecruitRepository
import com.example.jobplanettest.util.HttpHandler
import javax.inject.Inject

class RecruitRepositoryImp @Inject constructor(private val api: JobPlanetAPI) :
    RecruitRepository {

    override suspend fun fetchRecruitInfo() = HttpHandler<Recruit>()
        .httpRequest { api.fetchRecruitItems() }
        .sendRequest()
}
