package com.thedonorzone.thedonorzone.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
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

@Bean
fun authenticationEntryPoint(): AuthenticationEntryPoint {
    return AuthenticationEntryPoint { request: HttpServletRequest?, response: HttpServletResponse, authException: AuthenticationException? ->
        // Redirect to login endpoint if user is unauthenticated
        response.sendRedirect("/login")
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
                    .requestMatchers(HttpMethod.GET, "/RESSOURCES/**").permitAll() // Allow static CSS files
                    .requestMatchers(HttpMethod.GET, "/register").permitAll() // Allow registration without authentication
                    .requestMatchers(HttpMethod.GET, "/login").permitAll() // Allow login without authentication
                    .requestMatchers(HttpMethod.POST, "/register").permitAll() // Allow registration without authentication
                    .requestMatchers(HttpMethod.POST, "/login").permitAll() // Allow login without authentication
                    .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                    .anyRequest().authenticated() // Require authentication for all other requests
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Allow creation of sessions if needed
            }
            .exceptionHandling {
                it
                    .authenticationEntryPoint { _, response, _ ->
                        response.sendRedirect("/login?error=Unauthenticated")
                    }
                    .accessDeniedHandler { _, response, _ ->
                        response.sendRedirect("/login?error=Access+Denied")
                    }
            }

        // Adding JwtAuthenticationFilter to the filter chain
        http.addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
        http.headers { it.frameOptions { frame -> frame.disable() } }
        return http.build() // Return the configured SecurityFilterChain
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder() // Use BCrypt for password hashing
    }
}