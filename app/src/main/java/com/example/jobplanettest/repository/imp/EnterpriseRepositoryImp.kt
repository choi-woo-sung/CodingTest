package com.example.jobplanettest.repository.imp

import com.example.jobplanettest.model.Enterprise
import com.example.jobplanettest.network.JobPlanetAPI
import com.example.jobplanettest.repository.EnterpriseRepository
import com.example.jobplanettest.util.HttpHandler
import javax.inject.Inject

class EnterpriseRepositoryImp @Inject constructor(
    private val api: JobPlanetAPI
) : EnterpriseRepository {
    override suspend fun fetchEnterpriseInfo(): Enterprise = HttpHandler<Enterprise>()
        .httpRequest { api.fetchEnterpriseItems() }
        .sendRequest()
}
