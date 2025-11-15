package com.modura.app.domain.model.response.mypage

import com.modura.app.data.dto.response.mypage.PlaceReviewMypageResponseDto
import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentReviewsMypageResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewsMypageResponseModel(
    val placeReviewList: List<PlaceReviewMypageResponseModel>
)