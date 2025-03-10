package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.Restaurant
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Singleton
class RestaurantRepository(
    private val jdbi: Jdbi,
) {
    fun find(id: UUID): Restaurant? {
        val query = "SELECT * FROM restaurant WHERE id = :id"
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("id", id)
                .mapTo<Restaurant>()
                .findOne()
                .getOrNull()
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

    fun insert(restaurant: Restaurant) {
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
}