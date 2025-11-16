package com.modura.app.domain


data class Location(val latitude: Double, val longitude: Double)

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}