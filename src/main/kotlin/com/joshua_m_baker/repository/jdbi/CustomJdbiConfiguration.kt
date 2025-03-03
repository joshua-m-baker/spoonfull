package com.joshua_m_baker.repository.jdbi

import io.micronaut.configuration.jdbi.JdbiCustomizer
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin

@Singleton
class CustomJdbiConfiguration : JdbiCustomizer {
    override fun customize(jdbi: Jdbi) {
        jdbi.installPlugin(KotlinPlugin())
    }
}
