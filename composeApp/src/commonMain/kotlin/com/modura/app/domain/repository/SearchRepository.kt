package com.modura.app.domain.repository

import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceListResponseModel

interface SearchRepository {

    suspend fun searchContents(query: String): Result<SearchContentListResponseModel>
    suspend fun searchPlaces(query: String): Result<SearchPlaceListResponseModel>
}