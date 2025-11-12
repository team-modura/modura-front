package com.modura.app.domain.repository

import com.modura.app.data.dto.BaseResponse
import com.modura.app.domain.model.response.login.LoginResponseModel
import com.modura.app.domain.model.response.search.SearchListResponseModel
import com.modura.app.domain.model.response.search.SearchResponseModel

interface SearchRepository {

    suspend fun searchContents(query: String): Result<SearchListResponseModel>
    suspend fun searchPlaces(query: String): Result<SearchListResponseModel>
}