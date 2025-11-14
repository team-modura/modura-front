package com.modura.app.domain.model.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class UploadImageResponseModel(
    val key: String,
    val presignedUrl: String
)