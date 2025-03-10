package com.joshua_m_baker

import com.joshua_m_baker.domain.Dish
import com.joshua_m_baker.domain.Experience
import com.joshua_m_baker.domain.Restaurant
import com.joshua_m_baker.domain.Review
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
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
) : ShouldSpec({

    should("no matching experience returns 404") {
        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange<String>("/experiences/${UUID.randomUUID()}")
        }
        exception.status shouldBe HttpStatus.NOT_FOUND
    }

    should("post creates experience and get returns it") {
        val restaurant = Restaurant(name = "World Street Kitchen")
        httpClient.toBlocking().exchange(HttpRequest.POST("/restaurants", restaurant), Restaurant::class.java)

        val experience = Experience(
            id = UUID.randomUUID(),
            date = LocalDate.now(),
            restaurantName = restaurant.name,
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
