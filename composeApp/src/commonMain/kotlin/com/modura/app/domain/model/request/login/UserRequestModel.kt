package com.modura.app.domain.model.request.login

import com.modura.app.data.dto.request.login.UserRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class UserRequestModel(
    val address: String,
    val categoryList: List<Int>
){
    fun toUserRequestDto() = UserRequestDto(address, categoryList)
}
