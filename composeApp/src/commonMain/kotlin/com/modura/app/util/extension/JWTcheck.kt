package com.modura.app.util.extension

import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun isTokenExpired(token: String): Boolean {
    if (token.isBlank()) return true
    try {
        val parts = token.split(".")
        if (parts.size != 3) return true // JWT 형식이 아님

        // Payload(중간 부분) 디코딩
        val payload = parts[1]
        // URL Safe Base64 처리를 위해 패딩 추가
        val paddedPayload = when (payload.length % 4) {
            2 -> "$payload=="
            3 -> "$payload="
            else -> payload
        }

        val decodedBytes = Base64.UrlSafe.decode(paddedPayload)
        val decodedString = decodedBytes.decodeToString()

        val json = Json.parseToJsonElement(decodedString).jsonObject
        val exp = json["exp"]?.jsonPrimitive?.long ?: return true

        val currentTime = Clock.System.now().epochSeconds
        return exp < (currentTime + 5)
    } catch (e: Exception) {
        e.printStackTrace()
        return true
    }
}