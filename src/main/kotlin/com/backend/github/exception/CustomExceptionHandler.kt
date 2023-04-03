package com.backend.github.exception

import com.backend.github.dto.GitHubDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpClientErrorException.NotFound

@ControllerAdvice
class CustomExceptionHandler {
    data class ErrorResponse(
        val code: Int,
        val status: String,
        val message: String,
    )

    @ExceptionHandler(NotFound::class)
    @ResponseBody
    fun handleNotFoundException(ex: NotFound): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.toString(),
            ex.message ?: "Not found",
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }

    @ExceptionHandler(BadRequest::class)
    @ResponseBody
    fun handleBadRequestException(ex: BadRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name,
            ex.message ?: "Bad request",
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.name,
            ex.message ?: "Internal server error",
        )
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse)
    }

    fun exceptionMessage(ex: Exception): ResponseEntity<Any> {
        print(ex.message)
        return when (ex.message) {
            "Not Found" -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.toString(),
                    ex.message ?: "Not found",
                ))

            "Bad Request" -> ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.name,
                    ex.message ?: "Bad request",
                ))

            else -> {
                ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.name,
                        ex.message ?: "Internal server error",
                    ))
            }
        }
    }
}
