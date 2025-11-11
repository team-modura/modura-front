package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.LoginDataSource
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.response.login.LoginResponseModel
import com.modura.app.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override suspend fun login(request: LoginRequestModel): Result<LoginResponseModel> =
        runCatching { loginDataSource.login(request.toLoginRequestDto()).result.toLoginResponseModel() }
}