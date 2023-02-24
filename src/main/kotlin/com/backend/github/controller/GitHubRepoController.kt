package com.backend.github.controller

import com.backend.github.dto.GitHubDTO
import com.backend.github.service.GithubRepoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GitHubRepoController(private val repoService: GithubRepoService) {
    @GetMapping("/repos/{username}")
    suspend fun getRepositories(@PathVariable username: String): List<GitHubDTO> {
        return repoService.getRepositories(username)
    }
}