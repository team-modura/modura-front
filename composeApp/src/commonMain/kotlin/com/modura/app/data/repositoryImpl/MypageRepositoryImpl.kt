package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.LoginDataSource
import com.modura.app.data.datasource.MypageDataSource
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
import com.modura.app.domain.model.response.login.LoginResponseModel
import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutDetailResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import com.modura.app.domain.repository.LoginRepository
import com.modura.app.domain.repository.MypageRepository
import com.modura.app.domain.repository.TokenRepository

class MypageRepositoryImpl(
    private val dataSource: MypageDataSource,
    private val tokenRepository: TokenRepository
) : MypageRepository {

    override suspend fun contentsLikes(type: String): Result<ContentsLikedResponseModel> =
        runCatching { dataSource.contentsLikes(type).result!!.toContentsLikedResponseModel() }

    override suspend fun placesLikes(): Result<ContentsLikedResponseModel> =
        runCatching { dataSource.placesLikes().result!!.toContentsLikedResponseModel() }

    override suspend fun stillcuts(): Result<StillcutsResponseModel> =
        runCatching { dataSource.stillcuts().result!!.toStillcutsResponseModel() }

    override suspend fun stillcutDetail(stillcutId: Int): Result<StillcutDetailResponseModel> =
        runCatching { dataSource.stillcutDetail(stillcutId).result!!.toStillcutDetailResponseModel() }
}