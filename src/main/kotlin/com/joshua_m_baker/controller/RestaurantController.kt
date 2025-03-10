package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.Restaurant
import com.joshua_m_baker.repository.RestaurantRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.util.*

@Controller("/restaurants")
class RestaurantController(
    private val restaurantRepository: RestaurantRepository,
) {

    @Get("/{id}")
    fun getRestaurant(@PathVariable id: UUID): HttpResponse<Restaurant> {
        val restaurant = restaurantRepository.find(id) ?: return HttpResponse.notFound()
        return HttpResponse.ok(restaurant)
    }

    @Get
    fun getRestaurants(): List<Restaurant> {
        return restaurantRepository.findAll()
    }

    @Post
    fun createRestaurant(@Body restaurant: Restaurant): Restaurant {
        restaurantRepository.insert(restaurant)
        return restaurant
    }
}