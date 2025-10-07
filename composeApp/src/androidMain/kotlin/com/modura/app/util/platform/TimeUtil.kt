package com.modura.app.util.platform

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
}