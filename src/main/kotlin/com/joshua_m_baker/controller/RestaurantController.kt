package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.CreateRestaurant
import com.joshua_m_baker.domain.RestaurantResponse
import com.joshua_m_baker.domain.UpdateRestaurant
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

    @Patch("/{id}")
    fun updateRestaurant(
        @PathVariable id: UUID,
        @Body updateRestaurant: UpdateRestaurant
    ): HttpResponse<RestaurantResponse> {
        val updated = restaurantRepository.update(id, updateRestaurant)

        return if (updated) {
            HttpResponse.ok(
                RestaurantResponse(
                    id = id,
                    name = updateRestaurant.name
                )
            )
        } else {
            HttpResponse.notFound()
        }
    }

    @Delete("/{id}")
    fun deleteRestaurant(@PathVariable id: UUID): HttpResponse<Void> {
        restaurantRepository.delete(id)
        return HttpResponse.noContent()
    }
}