package com.example.jobplanettest.repository

import com.example.jobplanettest.model.Enterprise

interface EnterpriseRepository {
    suspend fun fetchEnterpriseInfo(): Enterprise
}
