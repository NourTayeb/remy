package com.nourtayeb.remy.domain.entity

data class Movie(
    val cursor:String,
    val id: Int,
    val title: String,
    val rating: Double,
    val poster: String?
)