package com.backend.github.controller

import com.backend.github.dto.GitHubDTO
import com.backend.github.exception.CustomExceptionHandler
import com.backend.github.service.GithubRepoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class GitHubRepoController(private val repoService: GithubRepoService) {
    @GetMapping("/repos/{username}")
    suspend fun getRepositories(@PathVariable username: String): ResponseEntity<List<GitHubDTO>> {
        return try {
            val repositories = repoService.getRepositories(username)

            when {
                repositories.isEmpty() -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(listOf())
                username.isEmpty() -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(listOf())
                else -> ResponseEntity
                    .ok(repositories)
            }

        } catch (ex: Exception) {
            CustomExceptionHandler.ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name,
                ex.message ?: "Internal server error",
            )
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(listOf())
        }
    }

    @GetMapping("/repos/{username}/{id}")
    suspend fun getRepositoryById(@PathVariable username: String, @PathVariable id: Long): GitHubDTO {
        return repoService.getRepositoryById(username, id)
    }

    @GetMapping("/repos/{username}/n")
    suspend fun getRepositoryByName(
        @PathVariable username: String,
        @RequestParam name: String,
    ): ResponseEntity<List<GitHubDTO>> {
        return ResponseEntity.ok(listOf(repoService.getRepositoryByName(username, name)))
    }

    @GetMapping("/repos/{username}/c")
    suspend fun getRepositoriesByCount(
        @PathVariable username: String,
        @RequestParam count: Int,
    ): ResponseEntity<List<GitHubDTO>> {
        return ResponseEntity.ok(repoService.getRepositoriesByCount(username, count))
    }
}