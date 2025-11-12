package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutResponseDto(
    val id: Int,
    val imageUrl: String
){
    fun toStillcutResponseModel() = StillcutResponseModel(id, imageUrl)
}
