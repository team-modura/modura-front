package com.modura.app.domain.model.response.list

import com.modura.app.data.dto.response.list.AnnouncementResponseDto

data class AnnouncementResponseModel(
    val id: Int,
    val region: String,
    val subsidy: Int,
    val eligibility: String,
    val startDate: String,
    val endDate: String,
    val criteria: String,
    val frequency: String,
    val application: String,
    val benefitType: String,
    val contact: String,
    val website: String,
    val description: String,
    val thumbnail: String
){
    fun toAnnouncementResponseDto() = AnnouncementResponseDto(id, region, subsidy, eligibility, startDate, endDate, criteria, frequency, application, benefitType, contact, website, description, thumbnail)
}