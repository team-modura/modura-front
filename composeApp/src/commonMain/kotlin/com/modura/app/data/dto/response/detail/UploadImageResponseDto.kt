package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.UploadImageResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class UploadImageResponseDto(
    val key: String,
    val presignedUrl: String
){
    fun toUploadImageResponseModel() = UploadImageResponseModel(key, presignedUrl)
}
