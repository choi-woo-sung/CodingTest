package com.example.jobplanettest.model

data class CellItem(
    val cell_type: String,
    val cons: String,
    val count: Int,
    val industry_name: String,
    val interview_question: String,
    val logo_path: String,
    val name: String,
    val pros: String,
    val rate_total_avg: Double,
    val recommend_recruit: List<RecommendRecruit>,
    val review_summary: String,
    val salary_avg: Int,
    val section_title: String,
    val update_date: String
)