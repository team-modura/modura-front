package com.modura.app.data.dto.request.login

import com.modura.app.domain.model.request.login.UserRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDto(
    val address: String,
    val categoryList: List<Int>
)