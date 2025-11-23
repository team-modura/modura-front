package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.mypage.*
import com.modura.app.domain.model.response.detail.StillcutResponseModel

interface MypageDataSource {
    suspend fun contentsLikes(type: String): BaseResponse<ContentsLikedResponseDto>
    suspend fun placesLikes(): BaseResponse<PlacesLikedResponseDto>
    suspend fun stillcuts(): BaseResponse<StillcutsResponseDto>
    suspend fun stillcutDelete(stillcutId: Int): BaseResponse<Unit>
    suspend fun stillcutDetail(stillcutId: Int): BaseResponse<StillcutDetailResponseDto>
    suspend fun contentReviewsMypage(type: String): BaseResponse<ContentReviewsMypageResponseDto>
    suspend fun placeReviewsMypage(type: String): BaseResponse<PlaceReviewsMypageResponseDto>
    suspend fun logout(): BaseResponse<Unit>
    suspend fun withdraw(): BaseResponse<Unit>
}
