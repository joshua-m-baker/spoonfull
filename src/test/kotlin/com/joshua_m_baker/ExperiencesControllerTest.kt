package com.joshua_m_baker

import com.joshua_m_baker.domain.*
import com.joshua_m_baker.repository.RestaurantRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import java.time.LocalDate
import java.util.*

@MicronautTest(transactional = false)
class ExperiencesControllerTest(
    @Client("/") httpClient: HttpClient,
    private val restaurantRepository: RestaurantRepository,
) : ShouldSpec({

    should("no matching experience returns 404") {
        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange<String>("/experiences/${UUID.randomUUID()}")
        }
        exception.status shouldBe HttpStatus.NOT_FOUND
    }

    should("post creates experience and get returns it") {
        val restaurant = RestaurantResponse(id = UUID.randomUUID(), name = "World Street Kitchen")
        restaurantRepository.insert(restaurant)

        val experience = CreateExperience(
            date = LocalDate.now(),
            restaurantId = restaurant.id,
            reviews = listOf(
                Review(
                    personName = "Josh",
                    personId = "1",
                    rating = 5,
                    notes = "asdf",
                    dishes = listOf(Dish(name = "Hamburger"))
                )
            ),
        )

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/experiences", experience), ExperienceResponse::class.java)
        createResponse.status.code shouldBe HttpStatus.OK.code

        val createdExperience = createResponse.body()

        val restExperience = httpClient.toBlocking()
            .retrieve("/experiences/${createdExperience.id}", ExperienceResponse::class.java)

        createdExperience.date shouldBe experience.date
        createdExperience.restaurantId shouldBe experience.restaurantId
        createdExperience.reviews shouldBe experience.reviews
        createdExperience.restaurantName shouldBe restaurant.name
        restExperience shouldBe createdExperience
    }

    should("have at least one experience after creating one") {
        val restaurant = RestaurantResponse(id = UUID.randomUUID(), name = "World Street Kitchen")
        restaurantRepository.insert(restaurant)

        val experience = CreateExperience(
            date = LocalDate.now(),
            restaurantId = restaurant.id,
            reviews = listOf(),
        )

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/experiences", experience), ExperienceResponse::class.java)
        createResponse.status.code shouldBe HttpStatus.OK.code

        val createdExperience = createResponse.body()

        val restExperiences = httpClient.toBlocking()
            .retrieve(
                HttpRequest.GET<List<ExperienceResponse>>("/experiences"),
                Argument.listOf(ExperienceResponse::class.java)
            )

        restExperiences shouldContain createdExperience
    }
})
