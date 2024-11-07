package com.thedonorzone.thedonorzone.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null

    @Throws(Exception::class)
    protected override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JwtAuthenticationFilter(authenticationManager(), jwtUtil))
            .addFilter(JwtAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService))
    } // Additional configurations...
}
