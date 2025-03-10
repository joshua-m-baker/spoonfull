package com.joshua_m_baker

import com.joshua_m_baker.domain.Restaurant
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest


@MicronautTest(transactional = false)
class RestaurantControllerTest(
    @Client("/") httpClient: HttpClient,
) : ShouldSpec({

    should("create a restaurant then get it") {
        val restaurant = Restaurant(name = "World Street Kitchen")

        val createResponse =
            httpClient.toBlocking().exchange(HttpRequest.POST("/restaurants", restaurant), Restaurant::class.java)
        createResponse.status.code shouldBe HttpStatus.OK.code

        val databaseRestaurant =
            httpClient.toBlocking().retrieve(
                HttpRequest.GET<String>("/restaurants/${restaurant.id}"),
                Restaurant::class.java
            )

        databaseRestaurant shouldBe restaurant
    }
})