package com.modura.app.domain.repository

import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
import com.modura.app.domain.model.response.login.LoginResponseModel
import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentReviewsMypageResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.PlaceReviewsMypageResponseModel
import com.modura.app.domain.model.response.mypage.PlacesLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutDetailResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel

interface MypageRepository {
    suspend fun contentsLikes(type: String): Result<ContentsLikedResponseModel>
    suspend fun placesLikes(): Result<PlacesLikedResponseModel>
    suspend fun stillcuts(): Result<StillcutsResponseModel>
    suspend fun stillcutDetail(stillcutId: Int): Result<StillcutDetailResponseModel>
    suspend fun contentReviewsMypage(type: String): Result<ContentReviewsMypageResponseModel>
    suspend fun placeReviewsMypage(type: String): Result<PlaceReviewsMypageResponseModel>
}