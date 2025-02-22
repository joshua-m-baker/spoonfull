package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Dish(
    val dishName: String,
    val dishDescription: String? = null,
    val imageUrl: String? = null,
    val rating: String? = null,
    val notes: String? = null,
)
