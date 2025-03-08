package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.Restaurant
import com.joshua_m_baker.repository.RestaurantRepository
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

@Controller("/restaurants")
class RestaurantController(
    private val restaurantRepository: RestaurantRepository,
) {
    @Post
    fun createRestaurant(@Body restaurant: Restaurant): Restaurant {
        restaurantRepository.insertRestaurant(restaurant)
        return restaurant
    }

    @Get
    fun getRestaurants(): List<Restaurant> {
        return restaurantRepository.findAll()
    }
}