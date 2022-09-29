package com.example.jobplanettest.model

import com.google.gson.annotations.SerializedName

data class Recruit(
    @SerializedName("recruit_items")
    val recruitItems: List<RecruitItem>
) : java.io.Serializable

data class RecruitItem(
    val id: Long,
    val title: String,
    val reward: Long,
    val appeal: String,

    @SerializedName("image_url")
    val imageURL: String,

    val company: Company
) : java.io.Serializable


