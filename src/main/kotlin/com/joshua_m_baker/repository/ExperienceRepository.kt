package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.DatabaseExperience
import com.joshua_m_baker.domain.ExperienceResponse
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

    fun find(id: UUID): ExperienceResponse? {
        val query =
            """SELECT exp.id, exp.date, res.name, exp.restaurant_id, exp.reviews 
                |FROM experience exp
                |INNER JOIN restaurant res ON exp.restaurant_id = res.id
                |WHERE exp.id = :id""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("id", id)
                .map { rs, _ ->
                    ExperienceResponse(
                        id = UUID.fromString(rs.getString("id")),
                        date = rs.getDate("date").toLocalDate(),
                        restaurantName = rs.getString("name"),
                        restaurantId = UUID.fromString(rs.getString("restaurant_id")),
                        reviews = objectMapper.readValue(rs.getString("reviews"), Argument.listOf(Review::class.java)),
                    )
                }
                .findOne()
                .getOrNull()
        }
    }

    fun findAll(): List<ExperienceResponse> {
        val query =
            """SELECT exp.id, exp.date, res.name, exp.restaurant_id, exp.reviews
                |FROM experience exp
                |INNER JOIN restaurant res ON exp.restaurant_id = res.id
                |ORDER BY exp.date""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .map { rs, _ ->
                    ExperienceResponse(
                        id = UUID.fromString(rs.getString("id")),
                        date = rs.getDate("date").toLocalDate(),
                        restaurantName = rs.getString("name"),
                        restaurantId = UUID.fromString(rs.getString("restaurant_id")),
                        reviews = objectMapper.readValue(rs.getString("reviews"), Argument.listOf(Review::class.java)),
                    )
                }
                .toList()
        }
    }

    fun insert(experience: DatabaseExperience) {
        val query =
            """INSERT into experience (id, date, restaurant_id, reviews, created_ts, updated_ts)
                |VALUES (:id, :date, :restaurantId, :reviews::json, :createdTs, :updatedTs)""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bindKotlin(experience)
                .execute()
        }
    }
}