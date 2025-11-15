package com.modura.app.domain.repository

import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceListResponseModel
import com.modura.app.domain.model.response.search.SearchPopularResponseModel

interface SearchRepository {
    suspend fun searchPopular(): Result<SearchPopularResponseModel>
    suspend fun searchContents(query: String): Result<SearchContentListResponseModel>
    suspend fun searchPlaces(query: String): Result<SearchPlaceListResponseModel>
}