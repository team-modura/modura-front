package com.modura.app.domain.repository

import com.modura.app.domain.model.response.list.MediaListResponseModel
import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceListResponseModel

interface ListRepository {
    suspend fun topPlaces(): Result<SearchPlaceListResponseModel>
    suspend fun topSeries(): Result<SearchContentListResponseModel>
    suspend fun topMovie(): Result<SearchContentListResponseModel>
}