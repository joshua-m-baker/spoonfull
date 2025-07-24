package com.joshua_m_baker.auth

import jakarta.inject.Singleton
import org.bouncycastle.crypto.generators.SCrypt
import java.security.SecureRandom
import java.util.*

@Singleton
class PasswordEncoder {

    private val cost = 16384
    private val blockSize = 8
    private val parallelization = 1
    private val keyLength = 32
    private val saltLength = 16

    private val secureRandom = SecureRandom()

    fun encode(password: String): String {
        val salt = ByteArray(saltLength).also { secureRandom.nextBytes(it) }
        val hash = SCrypt.generate(
            password.toByteArray(Charsets.UTF_8),
            salt, cost, blockSize, parallelization, keyLength
        )
        val saltB64 = Base64.getEncoder().encodeToString(salt)
        val hashB64 = Base64.getEncoder().encodeToString(hash)
        return "$saltB64:$hashB64"
    }

    fun matches(rawPassword: String, encoded: String): Boolean {
        val parts = encoded.split(":")
        if (parts.size != 2) return false

        val salt = Base64.getDecoder().decode(parts[0])
        val expectedHash = Base64.getDecoder().decode(parts[1])

        val actualHash = SCrypt.generate(
            rawPassword.toByteArray(Charsets.UTF_8),
            salt, cost, blockSize, parallelization, keyLength
        )
        return expectedHash.contentEquals(actualHash)
    }
}