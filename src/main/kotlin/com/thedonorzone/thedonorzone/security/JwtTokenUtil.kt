package com.thedonorzone.thedonorzone.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.*

object JwtTokenUtil {
    private const val JWT_EXPIRATION_MS = 3600000 // 1 hour
    private val JWT_SECRET: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256) // Generate a secret key

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
            .signWith(JWT_SECRET)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(JWT_SECRET).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String? {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(JWT_SECRET)
            .build()
            .parseClaimsJws(token)
            .body
        return claims.subject
    }
}
