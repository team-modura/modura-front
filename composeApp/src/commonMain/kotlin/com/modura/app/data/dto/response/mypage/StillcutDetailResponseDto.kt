package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.detail.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutDetailResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutDetailResponseDto(
    val id: Int,
    val imageUrl: String,
    val stillcut: String,
    val title: String,
    val name: String,
    val date: String
){
    fun toStillcutDetailResponseModel() = StillcutDetailResponseModel(id, imageUrl, stillcut, title, name, date)
}