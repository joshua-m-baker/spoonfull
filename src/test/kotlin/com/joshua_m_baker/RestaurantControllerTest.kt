package com.joshua_m_baker

import com.joshua_m_baker.domain.CreateRestaurant
import com.joshua_m_baker.domain.RestaurantResponse
import com.joshua_m_baker.domain.UpdateRestaurant
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
import java.util.*


@MicronautTest(transactional = false)
class RestaurantControllerTest(
    @Client("/api") httpClient: HttpClient,
) : ShouldSpec({

    should("create one restaurant, have it exist in list of all restaurants, delete it, then it does not exist") {
        val restaurant = CreateRestaurant(name = "World Street Kitchen")

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/restaurants", restaurant), RestaurantResponse::class.java)
        createResponse.status shouldBe HttpStatus.OK
        val createdRestaurant = createResponse.body()

        val allRestaurants =
            httpClient.toBlocking().retrieve(
                HttpRequest.GET<List<RestaurantResponse>>("/restaurants"),
                Argument.listOf(RestaurantResponse::class.java),
            )

        allRestaurants shouldContain createdRestaurant

        val deleteRequest = HttpRequest.DELETE<Void>("/restaurants/${createdRestaurant.id}")
        val deleteResponse = httpClient.toBlocking().exchange(deleteRequest, Void::class.java)
        deleteResponse.status shouldBe HttpStatus.NO_CONTENT
    }


    should("create a restaurant then update name") {
        val restaurant = CreateRestaurant(name = "Wrld Strt Ktchn")

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/restaurants", restaurant), RestaurantResponse::class.java)
        createResponse.status shouldBe HttpStatus.OK
        val restaurantId = createResponse.body().id

        val update = UpdateRestaurant(name = "World Street Kitchen")
        val updateResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.PATCH("/restaurants/$restaurantId", update), RestaurantResponse::class.java)
        createResponse.status shouldBe HttpStatus.OK
        updateResponse.body() shouldBe createResponse.body().copy(name = update.name)
    }

    should("return 404 when updating a restaurant that does not exist") {
        val nonExistingRestaurant = RestaurantResponse(
            id = UUID.randomUUID(),
            name = "does not exist"
        )

        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange<String>("/restaurants/${nonExistingRestaurant.id}")
        }
        exception.status shouldBe HttpStatus.NOT_FOUND

        val update = UpdateRestaurant(name = "World Street Kitchen")
        val updateException = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange(
                    HttpRequest.PATCH("/restaurants/${nonExistingRestaurant.id}", update),
                    RestaurantResponse::class.java
                )
        }
        updateException.status shouldBe HttpStatus.NOT_FOUND
    }
})