package com.backend.github.config

import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import org.slf4j.Logger
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CORSFilter : OncePerRequestFilter() {
    private val LOG: Logger = LoggerFactory.getLogger(CORSFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        LOG.info("Adding CORS Headers ........................")
        res.setHeader("Access-Control-Allow-Origin", "*")
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        res.setHeader("Access-Control-Max-Age", "3600")
        res.setHeader(
            "Access-Control-Allow-Headers",
            "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"
        )
        res.addHeader("Access-Control-Expose-Headers", "xsrf-token")
        if ("OPTIONS" == req.method) {
            res.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(req, res)
        }
    }
}