package com.thedonorzone.thedonorzone.security


import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import jakarta.servlet.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestFilter : Filter {
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse
        response.setHeader("Access-control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
        response.setHeader("Access-Control-Allow-Headers", "*, Authorization")
        response.setHeader("Access-Control-Expose-Headers", "*, Authorization")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        if (!request.method.equals("OPTIONS", ignoreCase = true)) {
            try {
                chain.doFilter(req, res)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            response.status = HttpServletResponse.SC_OK
        }
    }

    override fun init(filterConfig: FilterConfig) {}
    override fun destroy() {}
}