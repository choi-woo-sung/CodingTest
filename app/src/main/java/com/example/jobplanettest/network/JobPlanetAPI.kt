package com.example.jobplanettest.network

import com.example.jobplanettest.model.Enterprise
import com.example.jobplanettest.model.Recruit
import retrofit2.http.GET

interface JobPlanetAPI {

    @GET("test_data_recruit_items.json")
    suspend fun fetchRecruitItems(): Recruit

    @GET("test_data_cell_items.json")
    suspend fun fetchEnterpriseItems(): Enterprise
}
