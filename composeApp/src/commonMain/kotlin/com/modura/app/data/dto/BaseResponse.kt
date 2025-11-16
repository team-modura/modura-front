package com.modura.app.data.dto

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T: Any?>(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    @EncodeDefault val result: T?= null,
)