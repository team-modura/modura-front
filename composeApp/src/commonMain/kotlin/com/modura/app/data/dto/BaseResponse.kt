package com.modura.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T:Any?>(
    val success: Boolean,
    val message: String,
    val data: T,
)