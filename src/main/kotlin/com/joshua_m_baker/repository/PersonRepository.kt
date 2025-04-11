package com.joshua_m_baker.repository

import com.joshua_m_baker.domain.DatabasePerson
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Singleton
class PersonRepository(
    private val jdbi: Jdbi,
) {

    fun find(id: UUID): DatabasePerson? {
        val query =
            """SELECT id, first_name, last_name, email, password, created_ts, updated_ts FROM person
                | WHERE id = :id""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("id", id)
                .mapTo<DatabasePerson>()
                .findOne()
                .getOrNull()
        }
    }


    fun findByEmail(email: String): DatabasePerson? {
        val query =
            """SELECT id, first_name, last_name, email, password, created_ts, updated_ts FROM person
                | WHERE email = :email""".trimMargin()
        return jdbi.open().use { handle ->
            handle
                .createQuery(query)
                .bind("email", email)
                .mapTo<DatabasePerson>()
                .findOne()
                .getOrNull()
        }
    }

    fun insert(databasePerson: DatabasePerson) {
        val query =
            """INSERT into person (id, first_name, last_name, email, password, created_ts, updated_ts)
                |VALUES (:id, :firstName, :lastName, :email, :password, :createdTs, :updatedTs)""".trimMargin()
        jdbi.open().use { handle ->
            handle
                .createUpdate(query)
                .bindKotlin(databasePerson)
                .execute()
        }
    }
}