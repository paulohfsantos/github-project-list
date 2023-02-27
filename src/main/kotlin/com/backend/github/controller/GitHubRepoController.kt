package com.backend.github.controller

import com.backend.github.dto.GitHubDTO
import com.backend.github.service.GithubRepoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GitHubRepoController(private val repoService: GithubRepoService) {
    @GetMapping("/repos/{username}")
    suspend fun getRepositories(@PathVariable username: String): ResponseEntity<List<GitHubDTO>> {
        return ResponseEntity.ok(repoService.getRepositories(username))
    }

    @GetMapping("/repos/{username}/{id}")
    suspend fun getRepositoryById(@PathVariable username: String, @PathVariable id: Long): GitHubDTO {
        return repoService.getRepositoryById(username, id)
    }

    @GetMapping("/repos/{username}/c")
    suspend fun getRepositoriesByCount(
        @PathVariable username: String,
        @RequestParam count: Int,
    ): ResponseEntity<List<GitHubDTO>> {
        return ResponseEntity.ok(repoService.getRepositoriesByCount(username, count))
    }
}