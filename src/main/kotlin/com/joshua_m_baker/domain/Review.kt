package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import org.jdbi.v3.json.Json

@Serdeable
data class Review(
    val personName: String,     // String
    val personId: String,       // string/ fk
    val rating: Int,            // int
    @Json val dishes: List<Dish>,     // jsonB?
    val notes: String? = null,  // string (nullable?
)