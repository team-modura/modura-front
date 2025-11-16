package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.datasourceImpl.ListDataSourceImpl
import com.modura.app.domain.model.response.list.MediaListResponseModel
import com.modura.app.domain.model.response.list.MediaResponseModel
import com.modura.app.domain.model.response.search.SearchContentListResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceListResponseModel
import com.modura.app.domain.repository.ListRepository

class ListRepositoryImpl (
    private val dataSource: ListDataSource
    ): ListRepository{
    override suspend fun topPlaces(): Result<SearchPlaceListResponseModel> =
        runCatching { dataSource.topPlaces().result!!.toSearchPlaceListResponseModel()}

    override suspend fun topSeries(): Result<SearchContentListResponseModel> =
        runCatching { dataSource.topSeries().result!!.toSearchContentListResponseModel() }

    override suspend fun topMovie(): Result<SearchContentListResponseModel> =
        runCatching { dataSource.topMovie().result!!.toSearchContentListResponseModel() }

}