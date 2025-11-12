package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.LoginDataSource
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
import com.modura.app.domain.model.response.login.LoginResponseModel
import com.modura.app.domain.repository.LoginRepository
import com.modura.app.domain.repository.TokenRepository

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val tokenRepository: TokenRepository
) : LoginRepository {

    override suspend fun login(request: LoginRequestModel): Result<LoginResponseModel> {
        return runCatching {
            val response = loginDataSource.login(request.toLoginRequestDto())
            if (response.isSuccess&& response.result != null) {
                tokenRepository.saveTokens(accessToken = response.result.accessToken, refreshToken = response.result.refreshToken)
                response.result.toLoginResponseModel()
            } else {
                throw Exception(response.message ?: "로그인에 실패했습니다.")
            }
        }
    }

    override suspend fun user(request: UserRequestModel): Result<Unit> =
        runCatching { loginDataSource.user(request.toUserRequestDto()) }

}