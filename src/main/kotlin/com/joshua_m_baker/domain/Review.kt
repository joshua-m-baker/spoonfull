package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Review(
    val personName: String,
    val personId: String,
    val rating: Int,
    val dishes: List<Dish>,
    val notes: String? = null,
)