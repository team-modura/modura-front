package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.datasourceImpl.ListDataSourceImpl
import com.modura.app.domain.model.response.list.AnnouncementListResponseModel
import com.modura.app.domain.model.response.list.AnnouncementResponseModel
import com.modura.app.domain.repository.ListRepository

class ListRepositoryImpl (
    private val listDataSource: ListDataSource
    ): ListRepository{
    override suspend fun getAnnouncementDetail(id: Int): Result<AnnouncementResponseModel> =
        runCatching { listDataSource.getAnnouncementDetail(id).result.toAnnouncementResponseModel()}
    override suspend fun getAnnouncementList(): Result<AnnouncementListResponseModel> =
        runCatching { listDataSource.getAnnouncementList().result.toAnnouncementListResponseModel()}
}