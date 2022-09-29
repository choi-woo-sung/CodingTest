package com.example.jobplanettest.fake

import com.example.jobplanettest.model.Company
import com.example.jobplanettest.model.Recruit
import com.example.jobplanettest.model.RecruitItem
import com.example.jobplanettest.repository.RecruitRepository

class FakeRecruitRepository : RecruitRepository {

    override suspend fun fetchRecruitInfo(): Recruit {
        val data: MutableList<RecruitItem> = mutableListOf()
        data.add(
            RecruitItem(
                id = 0,
                title = "잡플래닛",
                reward = 0,
                appeal = "",
                imageURL = "",
                company = Company(
                    name = "",
                    logoPath = "",
                    ratings = listOf()
                )
            )
        )
        val result = Recruit(recruitItems = data)
        return result
    }
}