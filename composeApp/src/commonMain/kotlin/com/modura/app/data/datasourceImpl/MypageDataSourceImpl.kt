package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.LoginDataSource
import com.modura.app.data.datasource.MypageDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.request.login.LoginRequestDto
import com.modura.app.data.dto.request.login.UserRequestDto
import com.modura.app.data.dto.response.login.LoginResponseDto
import com.modura.app.data.dto.response.mypage.ContentsLikedResponseDto
import com.modura.app.data.dto.response.mypage.PlacesLikedResponseDto
import com.modura.app.data.dto.response.mypage.StillcutDetailResponseDto
import com.modura.app.data.dto.response.mypage.StillcutsResponseDto
import com.modura.app.domain.repository.LoginRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class MypageDataSourceImpl(
    private val httpClient: HttpClient
) : MypageDataSource {
    override suspend fun contentsLikes(type: String): BaseResponse<ContentsLikedResponseDto>
        = httpClient.get("/users/likes/contents?type=$type").body()

    override suspend fun placesLikes(): BaseResponse<PlacesLikedResponseDto>
        = httpClient.get("/users/likes/places").body()

    override suspend fun stillcuts(): BaseResponse<StillcutsResponseDto>
        = httpClient.get("/users/stillcuts").body()

    override suspend fun stillcutDetail(stillcutId: Int): BaseResponse<StillcutDetailResponseDto>
        = httpClient.get("/users/stillcuts/$stillcutId").body()

}