package com.modura.app.data.dto.request.detail

import kotlinx.serialization.Serializable

@Serializable
data class UploadImageRequestDto(
    val folder: String,
    val fileNames: List<String>,
    val contentTypes: List<String>
)
