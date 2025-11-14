package com.modura.app.domain.model.request.detail

import com.modura.app.data.dto.request.detail.UploadImageRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class UploadImageRequestModel(
    val folder: String,
    val fileNames: List<String>,
    val contentTypes: List<String>
){
    fun toUploadImageRequestDto() = UploadImageRequestDto(folder ,fileNames, contentTypes)
}
