package com.joshua_m_baker

import com.joshua_m_baker.domain.Restaurant
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest


@MicronautTest(transactional = false)
class RestaurantControllerTest(
    @Client("/restaurants") httpClient: HttpClient,
) : ShouldSpec({

    should("create an experience then get it") {
        val restaurant = Restaurant(name = "World Street Kitchen")

        val createResponse =
            httpClient.toBlocking().exchange(HttpRequest.POST("/", restaurant), Restaurant::class.java)
        createResponse.status.code shouldBe HttpStatus.OK.code

        val allRestaurants =
            httpClient.toBlocking().retrieve(HttpRequest.GET<String>("/"), Argument.listOf(Restaurant::class.java))

        allRestaurants shouldBe listOf(restaurant)
    }
})