package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import org.jdbi.v3.json.Json

@Serdeable
data class Review(
    val personName: String,
    val personId: String,
    val rating: Int,
    @Json val dishes: List<Dish>,
    val notes: String? = null,
)