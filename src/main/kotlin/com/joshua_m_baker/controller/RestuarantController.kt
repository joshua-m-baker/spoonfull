package com.joshua_m_baker.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import jakarta.inject.Singleton

@Controller("/experiences")
class ExperienceController {

    @Get(produces = [MediaType.TEXT_JSON])
     fun getExperiences(): String {
        return """
            {
                "date": "2025/02/15",
                "restaurant": "Element Pizza",
                "dishes": [
                    {"name": "Hot Honey", "rating": 4}
                ],
                "rating": 4
            }
        """.trimIndent()
    }
}