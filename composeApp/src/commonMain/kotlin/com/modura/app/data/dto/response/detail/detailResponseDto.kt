package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.request.detail.DetailResponseModel
import com.modura.app.domain.model.request.detail.ReviewResponseModel

data class DetailResponseDto(
    val id : Int,
    val tmdbId:Int,
    val type: Int,
    val titleKr: String,
    val titleEng: String,
    val isLiked: Boolean,
    val runtime: Int?,
    val year : Int,
    val contentCategories: List<String>,
    val plot :String,
    val thumbnail: String,
    val platform: List<String>,
    val reviewAvg: Float,
    val fiveStarCount: Int,
    val fourStarCount: Int,
    val threeStarCount: Int,
    val twoStarCount: Int,
    val oneStarCount: Int,
    val reviews: List<ReviewResponseDto>,
    val places: List<PlaceResponseDto>
){
    fun toDetailResponseModel()= DetailResponseModel(id, tmdbId, type, titleKr, titleEng, isLiked, runtime, year, contentCategories, plot, thumbnail, platform, reviewAvg, fiveStarCount, fourStarCount, threeStarCount, twoStarCount, oneStarCount, reviews.map{it.toReviewResponseModel()}, places.map{it.toPlaceResponseModel()})
}
