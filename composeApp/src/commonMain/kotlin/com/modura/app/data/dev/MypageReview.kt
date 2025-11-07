package com.modura.app.data.dev

data class MypageReview(
    val id: Int,
    val type: String, // "시리즈", "영화", "장소"
    val title: String,
    val location: String? = null, // 장소 리뷰에만 사용
    val region: String? = null, // 장소 리뷰에만 사용
    val name: String,
    val score: Float,
    val date: String,
    val text: String,
    // val imageUrl: String? = null // 나중에 URL로 받을 이미지
)