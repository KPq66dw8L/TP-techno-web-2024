package com.thedonorzone.thedonorzone.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
    }
}

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Disable CSRF for stateless requests like JWT
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.GET, "/CSS/**").permitAll() // Allow static CSS files
                    .requestMatchers(HttpMethod.GET, "/**").permitAll() // Allow all GET requests for everyone
                    .requestMatchers(HttpMethod.POST, "/users/register").permitAll() // Allow registration without authentication
                    .requestMatchers(HttpMethod.POST, "/users/login").permitAll() // Allow login without authentication
                    .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                    .anyRequest().authenticated() // Require authentication for all other requests
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless authentication (JWT)
            }

        http.addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        return http.build() // Return the configured SecurityFilterChain
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder() // Use BCrypt for password hashing
    }
}

