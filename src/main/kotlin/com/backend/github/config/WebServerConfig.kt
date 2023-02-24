package com.backend.github.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebServerConfig {
    @Value("\${cors.originPatterns:default}")
    private val corsOriginPatterns: String = ""

    fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins(*corsOriginPatterns.split(",").toTypedArray())
                    .allowedMethods(
                        HttpMethod.GET.name,
                        HttpMethod.POST.name,
                        HttpMethod.PUT.name,
                        HttpMethod.DELETE.name,
                        HttpMethod.OPTIONS.name
                    )
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }
        }
    }
}