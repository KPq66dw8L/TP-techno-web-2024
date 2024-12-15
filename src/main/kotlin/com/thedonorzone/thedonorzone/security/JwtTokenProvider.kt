package com.thedonorzone.thedonorzone.security

import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider {

    private val secretKey = "MySecretKey" // Secret key to sign the JWT tokens
    private val validityInMilliseconds = 3600000L // Token validity duration (1 hour)

    // Generate a JWT token for the user
    fun generateToken(username: String): String {
        val now = System.currentTimeMillis()
        val expiry = now + validityInMilliseconds

        // Token Payload
        // Token Payload
        val payload = "{\"sub\":\"$username\",\"iat\":$now,\"exp\":$expiry}"

        // Base64 encode the header and payload
        // Base64 encode the header and payload
        val header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".toByteArray())
        val encodedPayload = Base64.getEncoder().encodeToString(payload.toByteArray())

        // Generate the signature (HMAC-SHA256)
        val signature = sign("$header.$encodedPayload", secretKey)

        // Return the complete token
        return "$header.$encodedPayload.$signature"
    }

    // Validate a JWT token
    fun validateToken(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return false

            val header = parts[0]
            val payload = parts[1]
            val signature = parts[2]

            // Validate the signature
            val expectedSignature = sign("$header.$payload", secretKey)
            if (expectedSignature != signature) return false

            // Validate expiration
            val decodedPayload = String(Base64.getDecoder().decode(payload))
            val expiry = decodedPayload.replace(".*\"exp\":(\\d+).*".toRegex(), "$1").toLong()
            expiry > System.currentTimeMillis()
        } catch (e: Exception) {
            false
        }
    }

    // Extract username from the JWT token
    fun getUsernameFromToken(token: String): String {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid token")

        val payload = String(Base64.getDecoder().decode(parts[1]))
        return payload.replace(Regex(".*\"sub\":\"([^\"]+)\".*"), "$1")
    }

    // Method to generate HMAC-SHA256 signature
    private fun sign(data: String, secret: String): String {
        return try {
            val mac = Mac.getInstance("HmacSHA256")
            val secretKeySpec = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
            mac.init(secretKeySpec)
            Base64.getEncoder().encodeToString(mac.doFinal(data.toByteArray()))
        } catch (e: Exception) {
            throw RuntimeException("Error signing the token", e)
        }
    }
}
