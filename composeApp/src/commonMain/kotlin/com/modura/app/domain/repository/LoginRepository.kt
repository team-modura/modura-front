package com.modura.app.domain.repository

import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
import com.modura.app.domain.model.response.login.LoginResponseModel

interface LoginRepository {

    suspend fun login(request: LoginRequestModel): Result<LoginResponseModel>
    suspend fun user(request: UserRequestModel): Result<Unit>
    suspend fun reactivate(): Result<Unit>
}