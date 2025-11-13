package com.modura.app.domain.model.response.search

import com.modura.app.domain.model.response.search.SearchResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchListResponseModel(
    val contentList : List<SearchResponseModel> = emptyList()
)