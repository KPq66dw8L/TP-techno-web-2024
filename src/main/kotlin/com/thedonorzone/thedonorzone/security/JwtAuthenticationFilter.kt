package com.thedonorzone.thedonorzone.security


import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import java.io.IOException


@WebFilter("/*")  // Optionally specify the URL patterns to filter
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: jakarta.servlet.ServletRequest,
        response: jakarta.servlet.ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val session = httpRequest.session // Get the current session
        val token = session?.getAttribute("jwtToken") as? String // Retrieve JWT from session

        if (token != null && jwtTokenProvider.validateToken(token)) {
            val username = jwtTokenProvider.getUsernameFromToken(token)

            val authentication = UsernamePasswordAuthenticationToken(
                username, null, null // You can include authorities if needed here
            )

            authentication.details = WebAuthenticationDetailsSource().buildDetails(httpRequest)

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response) // Continue the filter chain
    }

    override fun init(filterConfig: FilterConfig?) {
        // No initialization needed in this case
    }

    override fun destroy() {
        // No cleanup needed in this case
    }
}
