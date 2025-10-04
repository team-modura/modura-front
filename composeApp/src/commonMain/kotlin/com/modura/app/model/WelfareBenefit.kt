package com.modura.app.model

import kotlinx.datetime.LocalDate

data class WelfareBenefit(
    val id: String,
    val region: String,
    val endDate: LocalDate,
    val name: String,
    val supportAmount: String, val targetAudience: String
)
