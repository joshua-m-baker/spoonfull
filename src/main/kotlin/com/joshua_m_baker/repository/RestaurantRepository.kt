package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.RestaurantResponse
import com.joshua_m_baker.domain.UpdateRestaurant
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
    fun find(id: UUID): RestaurantResponse? {
        val query = "SELECT id, name FROM restaurant WHERE id = :id"
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("id", id)
                .mapTo<RestaurantResponse>()
                .findOne()
                .getOrNull()
        }
    }

    fun findAll(): List<RestaurantResponse> {
        val query = "SELECT id, name FROM restaurant"
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .mapTo<RestaurantResponse>()
                .toList()
        }
    }

    fun insert(restaurant: RestaurantResponse) {
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

    fun update(id: UUID, updateRestaurant: UpdateRestaurant): Boolean {
        val query = """UPDATE restaurant set name = :name WHERE id = :id""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bind("id", id)
                .bindKotlin(updateRestaurant)
                .execute()
        } != 0
    }

    fun delete(id: UUID) {
        val query =
            """DELETE from restaurant WHERE id = :id""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bind("id", id)
                .execute()
        }
    }
}