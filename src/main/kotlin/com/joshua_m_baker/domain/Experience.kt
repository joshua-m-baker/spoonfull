package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate

@Serdeable
data class Experience(
    val date: LocalDate,
    val restaurantName: String,
    val restaurantId: String,
    val reviews: List<Review>
)