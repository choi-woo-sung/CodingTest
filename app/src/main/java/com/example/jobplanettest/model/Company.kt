package com.example.jobplanettest.model

data class Company(
    val logo_path: String,
    val name: String,
    val ratings: List<Rating>
)