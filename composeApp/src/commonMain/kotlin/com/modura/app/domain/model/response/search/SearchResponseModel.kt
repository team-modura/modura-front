package com.modura.app.domain.model.response.search

data class SearchResponseModel(
    val id : Int,
    val title:String,
    val isLiked: Boolean,
    val thumbnail:String
)