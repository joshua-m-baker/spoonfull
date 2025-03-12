package com.joshua_m_baker

import com.joshua_m_baker.domain.CreateRestaurant
import com.joshua_m_baker.domain.RestaurantResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest


@MicronautTest(transactional = false)
class RestaurantControllerTest(
    @Client("/") httpClient: HttpClient,
) : ShouldSpec({

    should("have at least one restaurant after creating one") {
        val restaurant = CreateRestaurant(name = "World Street Kitchen")

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/restaurants", restaurant), RestaurantResponse::class.java)
        createResponse.status.code shouldBe HttpStatus.OK.code
        val createdRestaurant = createResponse.body()

        val allRestaurants =
            httpClient.toBlocking().retrieve(
                HttpRequest.GET<List<RestaurantResponse>>("/restaurants"),
                Argument.listOf(RestaurantResponse::class.java),
            )

        allRestaurants shouldContain createdRestaurant
    }
})