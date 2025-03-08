package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema
import org.jdbi.v3.json.Json
import java.time.LocalDate
import java.util.*

@Serdeable
data class Experience(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) val id: UUID = UUID.randomUUID(),   // string/ primary key
    val date: LocalDate,        // date
    val restaurantName: String, // string
    val restaurantId: UUID,   // string/ FK
    @Json val reviews: List<Review> = listOf(),
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) val rating: Int,            // int
)