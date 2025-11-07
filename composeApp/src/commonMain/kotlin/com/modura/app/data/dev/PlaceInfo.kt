package com.modura.app.data.dev

data class PlaceInfo(
    val id: Int,
    val name: String,
    val address: String,
    val distance: Int, //m 단위
    val rating: Double,
    val reviewCount: Int,
    val bookmark: Boolean,
    val photoUrl: String? = null,
    val filmedContent: List<String> = emptyList()
)
