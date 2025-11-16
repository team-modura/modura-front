package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.map.PlaceResponseDto
import com.modura.app.data.dto.response.map.PlacesResponseDto

interface MapDataSource {
    suspend fun getPlaces(query: String?): BaseResponse<PlacesResponseDto>
}