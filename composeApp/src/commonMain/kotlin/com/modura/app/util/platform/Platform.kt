package com.modura.app.util.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect val BASE_URL: String