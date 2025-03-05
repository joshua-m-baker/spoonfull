package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.Dish
import com.joshua_m_baker.domain.Experience
import com.joshua_m_baker.domain.Review
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import java.time.LocalDate
import java.util.*

@MicronautTest(transactional = false)
class ExperiencesRepositoryTest(
    private val applicationContext: ApplicationContext,
    @Client("/") httpClient: HttpClient,
) : ShouldSpec({

    should("no matching experience returns 404") {
        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange<String>("/experiences/${UUID.randomUUID()}")
        }
        exception.status shouldBe HttpStatus.NOT_FOUND
    }

    should("should insert and find experience") {
        val expectedExperience = Experience(
            id = UUID.randomUUID(),
            date = LocalDate.now(),
            restaurantName = "Vinai",
            restaurantId = UUID.randomUUID(),
            reviews = listOf(),
            rating = 5,
        )
        val repo = applicationContext.getBean(ExperienceRepository::class.java)
        repo.insertExperience(expectedExperience)

        val experience = repo.getExperienceById(expectedExperience.id)
        experience shouldBe expectedExperience
    }

    should("post creates experience and get returns it") {
        val experience = Experience(
            id = UUID.randomUUID(),
            date = LocalDate.now(),
            restaurantName = "Vinai",
            restaurantId = UUID.randomUUID(),
            reviews = listOf(
                Review(
                    personName = "Josh",
                    personId = "1",
                    rating = 5,
                    notes = "asdf",
                    dishes = listOf(Dish(dishName = "Hamburger"))
                )
            ),
            rating = 5,
        )

        val createResponse =
            httpClient.toBlocking().exchange(HttpRequest.POST("/experiences", experience), Experience::class.java)
        createResponse.status.code shouldBe HttpStatus.OK.code

        val createdExperience = createResponse.body()

        val restExperience = httpClient.toBlocking()
            .retrieve("/experiences/${createdExperience.id}", Experience::class.java)

        createdExperience shouldBe experience
        restExperience shouldBe createdExperience
    }
})
