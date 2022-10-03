package com.example.jobplanettest.network

import MainCoroutinesRule
import com.example.jobplanettest.model.enums.CellTypeEnum
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


    @Throws(IOException::class)
    @Test
    fun `기업데이터 가져왔을떄_CellTypeEnum이 제대로 변환되는가?`() {
        runBlocking {
            enqueueResponse("/enterprise_items.json")
            val response = service.fetchEnterpriseItems()
            mockWebServer.takeRequest()
            Truth.assertThat(response.cell_items[0].cell_type).isEqualTo(CellTypeEnum.CELL_TYPE_COMPANY)
        }
    }

    @Throws(IOException::class)
    @Test
    fun `기업데이터 가져왔을떄_3번쨰 item이_CELL_TYPE_HORIZONTAL_THEME 인가?`() {
        runBlocking {
            enqueueResponse("/enterprise_items.json")
            val response = service.fetchEnterpriseItems()
            mockWebServer.takeRequest()
            Truth.assertThat(response.cell_items[2].cell_type)
                .isEqualTo(CellTypeEnum.CELL_TYPE_HORIZONTAL_THEME)
        }
    }

    @Throws(IOException::class)
    @Test
    fun `채용데이터 가져왔을떄_appeal이 분리되는가?`() {
        runBlocking {
            enqueueResponse("/recruit_items.json")
            val response = service.fetchRecruitItems()
            mockWebServer.takeRequest()
            val splitResult = response.recruitItems.get(0).appeal.split(", ")
            Truth.assertThat(splitResult[0]).isEqualTo("복지포인트")
            Truth.assertThat(splitResult[1]).isEqualTo("스낵바")
        }
    }
}
