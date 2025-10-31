package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.datasourceImpl.ListDataSourceImpl
import com.modura.app.domain.model.response.list.MediaListResponseModel
import com.modura.app.domain.model.response.list.MediaResponseModel
import com.modura.app.domain.repository.ListRepository

class ListRepositoryImpl (
    private val listDataSource: ListDataSource
    ): ListRepository{
        override suspend fun getMediaList(): Result<MediaListResponseModel> =
        runCatching { listDataSource.getMediaList().result.toMediaListResponseModel()}
}