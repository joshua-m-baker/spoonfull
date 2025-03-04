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

    fun findAllExperiences(): List<Experience> {
        val query = "SELECT * FROM experience ORDER BY date LIMIT 5"
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .map { rs, _ ->
                    Experience(
                        experienceId = UUID.fromString(rs.getString("experience_id")),
                        date = rs.getDate("date").toLocalDate(),
                        restaurantName = rs.getString("restaurant_name"),
                        restaurantId = UUID.fromString(rs.getString("restaurant_id")),
                        reviews = objectMapper.readValue(rs.getString("reviews"), Argument.listOf(Review::class.java)),
                        rating = rs.getInt("rating")
                    )
                }
                .toList()
        }
    }

    fun getExperienceById(id: UUID): Experience? {
        val query = "SELECT * FROM experience WHERE experience_id = :id"
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("id", id)
                .map { rs, _ ->
                    Experience(
                        experienceId = UUID.fromString(rs.getString("experience_id")),
                        date = rs.getDate("date").toLocalDate(),
                        restaurantName = rs.getString("restaurant_name"),
                        restaurantId = UUID.fromString(rs.getString("restaurant_id")),
                        reviews = objectMapper.readValue(rs.getString("reviews"), Argument.listOf(Review::class.java)),
                        rating = rs.getInt("rating")
                    )
                }
                .findOne()
                .getOrNull()
        }
    }

    fun insertExperience(experience: Experience) {
        val query =
            """INSERT into experience (experience_id, date, restaurant_name, restaurant_id, reviews, rating)
                |VALUES (:experienceId, :date, :restaurantName, :restaurantId, :reviews::json, :rating)""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bindKotlin(experience)
                .execute()
        }
    }
}