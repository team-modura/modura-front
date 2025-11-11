package com.modura.app.domain.model.request.detail

data class ReviewResponseModel(
    val id: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)
