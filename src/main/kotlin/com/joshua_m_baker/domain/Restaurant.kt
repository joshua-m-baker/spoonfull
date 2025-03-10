package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Serdeable
data class Restaurant(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) val id: UUID = UUID.randomUUID(),
    val name: String,
)