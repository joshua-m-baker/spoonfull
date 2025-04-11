package com.joshua_m_baker

import com.joshua_m_baker.domain.CreatePerson
import com.joshua_m_baker.domain.LoginPerson
import com.joshua_m_baker.domain.PersonResponse
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest(transactional = false)
class PersonControllerTest(
    @Client("/api/users") httpClient: HttpClient,
) : ShouldSpec({

    should("allow person to sign up and login") {
        val createPerson = CreatePerson(
            firstName = "Joe",
            lastName = "Smith",
            email = "testEmail1@test.com",
            password = "password"
        )

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/signup", createPerson), PersonResponse::class.java)
        createResponse.status shouldBe HttpStatus.OK
        val createdPerson = createResponse.body()

        val getPerson = httpClient.toBlocking()
            .retrieve("/${createdPerson.id}", PersonResponse::class.java)
        createdPerson shouldBe getPerson


        val loginPerson = LoginPerson(email = "testEmail1@test.com", password = "password")
        val loginResponse = httpClient.toBlocking()
            .exchange(HttpRequest.POST("/login", loginPerson), PersonResponse::class.java)

        loginResponse.status shouldBe HttpStatus.OK
        loginResponse.body() shouldBe createdPerson
    }

    should("login fails if password does not match") {
        val createPerson = CreatePerson(
            firstName = "Joe",
            lastName = "Smith",
            email = "testEmail2@test.com",
            password = "password"
        )

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/signup", createPerson), PersonResponse::class.java)
        createResponse.status shouldBe HttpStatus.OK
        val createdPerson = createResponse.body()

        val getPerson = httpClient.toBlocking()
            .retrieve("/${createdPerson.id}", PersonResponse::class.java)
        createdPerson shouldBe getPerson

        val invalidLoginPerson = LoginPerson(email = "testEmail2@test.com", password = "wrongpassword")
        val invalidLoginRequest = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/login", invalidLoginPerson), PersonResponse::class.java)
        }

        invalidLoginRequest.status shouldBe HttpStatus.UNAUTHORIZED
    }

    should("fail on sign up if email already exists") {
        val createPerson1 = CreatePerson(
            firstName = "Joe",
            lastName = "Smith",
            email = "testEmail3@test.com",
            password = "password"
        )

        val createResponse =
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/signup", createPerson1), PersonResponse::class.java)
        createResponse.status shouldBe HttpStatus.OK

        val createPerson2 = CreatePerson(
            firstName = "Bo",
            lastName = "Diddley",
            email = "testEmail3@test.com",
            password = "password1"
        )

        val duplicateEmailRequest = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("/signup", createPerson2), PersonResponse::class.java)
        }
        duplicateEmailRequest.status shouldBe HttpStatus.CONFLICT
    }
})