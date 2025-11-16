package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.LoginDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.request.login.LoginRequestDto
import com.modura.app.data.dto.request.login.UserRequestDto
import com.modura.app.data.dto.response.login.LoginResponseDto
import com.modura.app.domain.repository.LoginRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LoginDataSourceImpl(
    private val noAuthHttpClient: HttpClient,
    private val authHttpClient: HttpClient
) : LoginDataSource {
    override suspend fun login(request: LoginRequestDto): BaseResponse<LoginResponseDto>
        = noAuthHttpClient.post("/auth/login") { setBody(request) }.body()
    override suspend fun user(request: UserRequestDto): BaseResponse<Unit>
        = authHttpClient.patch("/users"){setBody(request)}.body()
    override suspend fun reactivate(): BaseResponse<Unit>
        = authHttpClient.patch("/auth/reactivate").body()
}