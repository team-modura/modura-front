package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentReviewsMypageResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.PlaceReviewsMypageResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewsMypageResponseDto(
    val placeReviewList: List<PlaceReviewMypageResponseDto>
) {
    fun toPlaceReviewsMypageResponseModel() =
        PlaceReviewsMypageResponseModel(placeReviewList.map { it.toPlaceReviewMypageResponseModel() })
}