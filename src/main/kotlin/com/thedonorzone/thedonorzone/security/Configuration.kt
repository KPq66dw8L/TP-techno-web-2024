package com.thedonorzone.thedonorzone.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.filter.ForwardedHeaderFilter

@Configuration
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/h2-console/**", "/users/register", "/users/login") // Disable CSRF for H2 console and specific endpoints
            }
            .headers { headers ->
                headers.frameOptions { it.disable() } // Disable frame options for H2 console access
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // Stateless session
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/h2-console/**").permitAll() // Allow public access to any path under /h2-console/
                    .requestMatchers(HttpMethod.POST, "/users/register", "/users/login").permitAll() // Allow public access for registration and login
                    .anyRequest().authenticated() // Require authentication for all other endpoints
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwtConfigurer ->
                    jwtConfigurer.jwkSetUri("http://localhost:8080/.well-known/jwks.json") // Set up JWT decoding
                }
            }

        return http.build()
    }



    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }
}

@Configuration
class PasswordEncoderConfig {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@Bean
fun corsFilter(): CorsFilter {
    val source = UrlBasedCorsConfigurationSource()
    val config = CorsConfiguration().applyPermitDefaultValues()
    config.addAllowedMethod("*") // Allow all HTTP methods
    source.registerCorsConfiguration("/**", config)
    return CorsFilter(source)
}
