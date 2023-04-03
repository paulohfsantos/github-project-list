package com.backend.github.exception

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
            HttpStatus.BAD_REQUEST.toString(),
            ex.message ?: "Bad request",
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }
}
