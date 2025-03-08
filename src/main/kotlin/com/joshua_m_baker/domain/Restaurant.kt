package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Serdeable
data class Restaurant(
    val id: UUID = UUID.randomUUID(),
    val name: String,
)