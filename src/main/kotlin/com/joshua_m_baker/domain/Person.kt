package com.joshua_m_baker.domain

import io.micronaut.serde.annotation.Serdeable
import java.time.OffsetDateTime
import java.util.*

@Serdeable
data class DatabasePerson(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val createdTs: OffsetDateTime,
    val updatedTs: OffsetDateTime
) {
    fun toPersonResponse(): PersonResponse {
        return PersonResponse(
            id = this.id,
            name = "${this.firstName} + ${this.lastName}",
            email = this.email
        )
    }
}

@Serdeable
data class PersonResponse(
    val id: UUID,
    val name: String,
    val email: String,
)

@Serdeable
data class CreatePerson(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

@Serdeable
data class LoginPerson(
    val email: String,
    val password: String
)