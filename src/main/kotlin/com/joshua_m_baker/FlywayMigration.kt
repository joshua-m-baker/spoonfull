package com.joshua_m_baker

import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import jakarta.inject.Singleton
import org.flywaydb.core.Flyway
import javax.sql.DataSource

@Singleton
class FlywayMigration(private val dataSource: DataSource) : ApplicationEventListener<ServerStartupEvent> {

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .load()

        flyway.migrate()
    }
}