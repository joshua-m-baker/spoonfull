package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import org.jdbi.v3.json.Json
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class DatabaseExperience(
    val id: UUID,
    val createdTs: OffsetDateTime,
    val updatedTs: OffsetDateTime,
    val date: LocalDate,
    val restaurantId: UUID,
    @Json val reviews: List<Review>,
)

@Serdeable
data class CreateExperience(
    val date: LocalDate,
    val restaurantId: UUID,
    val reviews: List<Review> = listOf(),
)

@Serdeable
data class ExperienceResponse(
    val id: UUID,
    val date: LocalDate,
    val restaurantName: String,
    val restaurantId: UUID,
    @Json val reviews: List<Review>,
)