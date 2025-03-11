package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.Experience
import com.joshua_m_baker.domain.Review
import io.micronaut.core.type.Argument
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.bindKotlin
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Singleton
class ExperienceRepository(
    private val jdbi: Jdbi,
    private val objectMapper: ObjectMapper
) {

    fun find(id: UUID): Experience? {
        val query =
            """SELECT exp.id, exp.date, res.name, exp.restaurant_id, exp.reviews, exp.rating 
                |FROM experience exp
                |INNER JOIN restaurant res ON exp.restaurant_id = res.id
                |WHERE exp.id = :id""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("id", id)
                .map { rs, _ ->
                    Experience(
                        id = UUID.fromString(rs.getString("id")),
                        date = rs.getDate("date").toLocalDate(),
                        restaurantName = rs.getString("name"),
                        restaurantId = UUID.fromString(rs.getString("restaurant_id")),
                        reviews = objectMapper.readValue(rs.getString("reviews"), Argument.listOf(Review::class.java)),
                        rating = rs.getInt("rating")
                    )
                }
                .findOne()
                .getOrNull()
        }
    }

    fun findAll(): List<Experience> {
        val query =
            """SELECT exp.id, exp.date, res.name, exp.restaurant_id, exp.reviews, exp.rating 
                |FROM experience exp
                |INNER JOIN restaurant res ON exp.restaurant_id = res.id
                |ORDER BY exp.date""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .map { rs, _ ->
                    Experience(
                        id = UUID.fromString(rs.getString("id")),
                        date = rs.getDate("date").toLocalDate(),
                        restaurantName = rs.getString("name"),
                        restaurantId = UUID.fromString(rs.getString("restaurant_id")),
                        reviews = objectMapper.readValue(rs.getString("reviews"), Argument.listOf(Review::class.java)),
                        rating = rs.getInt("rating")
                    )
                }
                .toList()
        }
    }

    fun insert(experience: Experience) {
        val query =
            """INSERT into experience (id, date, restaurant_id, reviews, rating)
                |VALUES (:id, :date, :restaurantId, :reviews::json, :rating)""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bindKotlin(experience)
                .execute()
        }
    }
}