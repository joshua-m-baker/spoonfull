package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Serdeable
data class CreateRestaurant(
    val name: String,
)

@Serdeable
data class UpdateRestaurant(
    val name: String,
)

@Serdeable
data class RestaurantResponse(
    val id: UUID,
    val name: String,
)