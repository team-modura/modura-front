package com.modura.app.domain.model.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailResponseModel(
    val id : Int,
    val tmdbId:Int?,
    val type: Int,
    val titleKr: String,
    val titleEng: String,
    val isLiked: Boolean,
    val runtime: Int?,
    val year : Int,
    val contentCategories: List<String?>,
    val plot :String,
    val thumbnail: String,
    val platforms: List<String?>,
    val reviewAvg: Double,
    val fiveStarCount: Int,
    val fourStarCount: Int,
    val threeStarCount: Int,
    val twoStarCount: Int,
    val oneStarCount: Int,
    val reviews: List<ReviewResponseModel?>,
    val places: List<PlaceResponseModel?>
)


