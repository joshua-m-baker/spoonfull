package com.joshua_m_baker

import io.kotest.core.listeners.ProjectListener
import org.flywaydb.core.Flyway
import org.testcontainers.containers.PostgreSQLContainer

object PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:15.2")

object TestContainerListener : ProjectListener {
    override suspend fun beforeProject() {
        PostgresContainer.start()

        val flyway = Flyway.configure()
            .dataSource(PostgresContainer.jdbcUrl, PostgresContainer.username, PostgresContainer.password)
            .load()

        flyway.migrate()
        System.setProperty("datasources.default.url", PostgresContainer.jdbcUrl)
        System.setProperty("datasources.default.username", PostgresContainer.username)
        System.setProperty("datasources.default.password", PostgresContainer.password)
    }

    override suspend fun afterProject() {
        PostgresContainer.stop()
    }
}
