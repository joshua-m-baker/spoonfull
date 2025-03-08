package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.Restaurant
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo

@Singleton
class RestaurantRepository(
    private val jdbi: Jdbi,
) {
    fun insertRestaurant(restaurant: Restaurant) {
        val query =
            """INSERT into restaurant (id, name)
                |VALUES (:id, :name)""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bindKotlin(restaurant)
                .execute()
        }
    }

    fun findAll(): List<Restaurant> {
        val query = "SELECT * FROM restaurant"
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .mapTo<Restaurant>()
                .toList()
        }
    }
}