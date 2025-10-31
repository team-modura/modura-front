package com.modura.app.domain.repository

import com.modura.app.domain.model.response.list.MediaListResponseModel

interface ListRepository {
    suspend fun getMediaList(): Result<MediaListResponseModel>
}