package com.modura.app.domain.repository

import com.modura.app.domain.model.response.list.AnnouncementListResponseModel
import com.modura.app.domain.model.response.list.AnnouncementResponseModel

interface ListRepository {
    suspend fun getAnnouncementList(): Result<AnnouncementListResponseModel>
    suspend fun getAnnouncementDetail(id: Int): Result<AnnouncementResponseModel>
}