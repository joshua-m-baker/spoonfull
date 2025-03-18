package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.CreateExperience
import com.joshua_m_baker.domain.DatabaseExperience
import com.joshua_m_baker.domain.ExperienceResponse
import com.joshua_m_baker.repository.ExperienceRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.time.OffsetDateTime
import java.util.*


@Controller("/api/experiences")
class ExperienceController(
    private val experienceRepository: ExperienceRepository,
) {

    @Get("/{id}")
    fun getExperience(@PathVariable id: UUID): HttpResponse<ExperienceResponse> {
        val experience = experienceRepository.find(id) ?: return HttpResponse.notFound()
        return HttpResponse.ok(experience)
    }

    @Get
    fun getExperiences(): List<ExperienceResponse> {
        return experienceRepository.findAll()
    }

    @Post
    fun createExperience(
        @Body experience: CreateExperience
    ): ExperienceResponse {
        val now = OffsetDateTime.now()
        val toInsert = DatabaseExperience(
            id = UUID.randomUUID(),
            createdTs = now,
            updatedTs = now,
            date = experience.date,
            restaurantId = experience.restaurantId,
            reviews = experience.reviews,
        )
        experienceRepository.insert(toInsert)
        return experienceRepository.find(toInsert.id)!!
    }
}