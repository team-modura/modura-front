package com.modura.app.domain.repository

import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.domain.model.response.map.PlacesResponseModel

interface MapRepository {
    suspend fun getPlaces(query: String?): Result<PlacesResponseModel>
}