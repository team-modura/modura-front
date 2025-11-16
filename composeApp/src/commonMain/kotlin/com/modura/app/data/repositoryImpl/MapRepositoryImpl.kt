package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.MapDataSource
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.domain.model.response.map.PlacesResponseModel
import com.modura.app.domain.repository.MapRepository

class MapRepositoryImpl (
    private val dataSource: MapDataSource
): MapRepository {
    override suspend fun getPlaces(query: String?): Result<PlacesResponseModel> =
        runCatching { dataSource.getPlaces(query).result!!.toPlacesResponseModel() }
}