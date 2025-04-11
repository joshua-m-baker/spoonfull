package com.joshua_m_baker.controller

import com.joshua_m_baker.auth.PasswordEncoder
import com.joshua_m_baker.domain.CreatePerson
import com.joshua_m_baker.domain.DatabasePerson
import com.joshua_m_baker.domain.LoginPerson
import com.joshua_m_baker.domain.PersonResponse
import com.joshua_m_baker.repository.PersonRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import java.time.OffsetDateTime
import java.util.*

@Controller("/api/users")
class PersonController(
    private val personRepository: PersonRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Get("/{id}")
    fun getById(@PathVariable id: UUID): HttpResponse<PersonResponse> {
        val databasePerson = personRepository.find(id) ?: return HttpResponse.notFound()
        return HttpResponse.ok(databasePerson.toPersonResponse())
    }

    @Post("/login")
    fun getByEmail(@Body person: LoginPerson): HttpResponse<PersonResponse> {
        val databasePerson =
            personRepository.findByEmail(person.email) ?: return HttpResponse.notFound()

        return if (passwordEncoder.matches(person.password, databasePerson.password)) {
            HttpResponse.ok(databasePerson.toPersonResponse())
        } else {
            HttpResponse.unauthorized()
        }
    }

    @Post("/signup")
    fun create(@Body person: CreatePerson): HttpResponse<PersonResponse> {
        val now = OffsetDateTime.now()
        val databasePerson = DatabasePerson(
            id = UUID.randomUUID(),
            createdTs = now,
            updatedTs = now,
            firstName = person.firstName,
            lastName = person.lastName,
            email = person.email,
            password = passwordEncoder.encode(person.password)
        )

        personRepository.findByEmail(person.email)?.let {
            return HttpResponse.status(HttpStatus.CONFLICT)
        }

        personRepository.insert(databasePerson)

        return HttpResponse.ok(databasePerson.toPersonResponse())
    }
}