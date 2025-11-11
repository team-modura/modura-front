package com.modura.app.domain.repository

import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.response.login.LoginResponseModel
import com.modura.app.domain.model.response.search.SearchResponseModel

interface SearchRepository {

    suspend fun searchContents(query: String): Result<List<List<SearchResponseModel>>>
}