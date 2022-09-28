package com.dev6.data.network

import MainCoroutinesRule
import com.example.jobplanettest.network.ApiAbstract
import com.example.jobplanettest.network.JobPlanetAPI
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class LoginApiTest : ApiAbstract<JobPlanetAPI>() {

    private lateinit var service: JobPlanetAPI

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setRetrofit() {
        service = createService(JobPlanetAPI::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `채용데이터 가져왔을떄_title 제대로 가져오는가?`() {
        runBlocking {
            enqueueResponse("/recruit_items.json")
            val response = service.fetchRecruitItems()
            mockWebServer.takeRequest()
            Truth.assertThat(response.recruitItems.get(0).title).isEqualTo("[잡플래닛] iOS 앱개발")
        }
    }


}
