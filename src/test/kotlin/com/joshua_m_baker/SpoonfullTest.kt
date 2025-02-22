package com.joshua_m_baker
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject

@MicronautTest
class SpoonfullTest(
    private val application: EmbeddedApplication<*>,
    @Client("/") private val client: HttpClient
): StringSpec({


    "test the server is running" {
        assert(application.isRunning)
    }

    "gets response" {
        val response = client.toBlocking().exchange<String>("/experiences")
        response.code() shouldBe HttpStatus.OK.code
    }
})
