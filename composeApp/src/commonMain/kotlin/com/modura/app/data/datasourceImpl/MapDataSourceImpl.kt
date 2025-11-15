package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.MapDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.map.PlaceResponseDto
import com.modura.app.data.dto.response.map.PlacesResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLParameter

class MapDataSourceImpl(
    private val httpClient: HttpClient
) : MapDataSource {
    override suspend fun getPlaces(query: String?): BaseResponse<PlacesResponseDto> {
        val url = if (query.isNullOrBlank()) {
            "/places"
        } else {
            val encodedQuery = query.encodeURLParameter()
            "/places?query=$encodedQuery"
        }
        return httpClient.get(url).body()
    }

}