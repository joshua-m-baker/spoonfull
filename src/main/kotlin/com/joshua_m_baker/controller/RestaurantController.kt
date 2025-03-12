package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.CreateRestaurant
import com.joshua_m_baker.domain.RestaurantResponse
import com.joshua_m_baker.repository.RestaurantRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.util.*

@Controller("/restaurants")
class RestaurantController(
    private val restaurantRepository: RestaurantRepository,
) {

    @Get("/{id}")
    fun getRestaurant(@PathVariable id: UUID): HttpResponse<RestaurantResponse> {
        val restaurant = restaurantRepository.find(id) ?: return HttpResponse.notFound()
        return HttpResponse.ok(restaurant)
    }

    @Get
    fun getRestaurants(): List<RestaurantResponse> {
        return restaurantRepository.findAll()
    }

    @Post
    fun createRestaurant(@Body createRestaurant: CreateRestaurant): RestaurantResponse {
        val restaurant = RestaurantResponse(
            id = UUID.randomUUID(),
            name = createRestaurant.name
        )
        restaurantRepository.insert(restaurant)
        return restaurant
    }
}