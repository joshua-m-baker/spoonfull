package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.Experience
import com.joshua_m_baker.repository.ExperienceRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import java.util.*


@Controller("/experiences")
class ExperienceController(
    private val experienceRepository: ExperienceRepository,
) {

    @Get("/{id}")
    fun getExperience(@PathVariable id: UUID): HttpResponse<Experience> {
        val experience = experienceRepository.find(id) ?: return HttpResponse.notFound()
        return HttpResponse.ok(experience)
    }

    @Get(produces = [MediaType.TEXT_JSON])
    fun getExperiences(): List<Experience> {
        return experienceRepository.findAll()
    }

    @Post
    fun createExperience(
        @Body experience: Experience
    ): Experience {
        experienceRepository.insert(experience)
        return experience
    }
}