package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.request.login.LoginRequestDto
import com.modura.app.data.dto.response.login.LoginResponseDto

interface LoginDataSource {
    suspend fun login(request: LoginRequestDto): BaseResponse<LoginResponseDto>
}