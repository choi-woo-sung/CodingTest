package com.example.jobplanettest.model

data class Recruit(
//    @SerialName("recruit_items")
    val recruitItems: List<RecruitItem>
) : java.io.Serializable

data class RecruitItem(
    val id: Long,
    val title: String,
    val reward: Long,
    val appeal: String,

//    @SerialName("image_url")
    val imageURL: String,

    val company: Company
) : java.io.Serializable

data class Company(
    val name: String,

//    @SerialName("logo_path")
    val logoPath: String,

    val ratings: List<Rating>
) : java.io.Serializable

data class Rating(
    val type: String,
    val rating: Double
) : java.io.Serializable
