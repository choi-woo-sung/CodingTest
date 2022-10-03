package com.example.jobplanettest.repository

import com.example.jobplanettest.model.Recruit

interface RecruitRepository {
    suspend fun fetchRecruitInfo(): Recruit
}
