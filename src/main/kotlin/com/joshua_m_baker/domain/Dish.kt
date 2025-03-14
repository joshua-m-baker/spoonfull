package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Dish(
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val rating: Int? = null,
    val notes: String? = null,
)
