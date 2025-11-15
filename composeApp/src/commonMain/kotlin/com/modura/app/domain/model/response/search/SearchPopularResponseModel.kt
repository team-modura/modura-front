package com.modura.app.domain.model.response.search

import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchPopularResponseModel(
    val keywords: List<String>
)