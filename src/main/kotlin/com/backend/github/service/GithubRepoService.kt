package com.backend.github.service

import com.backend.github.dto.GitHubDTO
import org.kohsuke.github.GitHub
import org.springframework.stereotype.Service

@Service
class GithubRepoService {
    suspend fun getRepositories(username: String): List<GitHubDTO> {
        val github = GitHub.connectAnonymously()
        val user = github.getUser(username)
        val repositories = user.repositories

        return repositories.map {
            GitHubDTO(
                it.value.id.toString(),
                it.value.name,
                it.value.url.toString(),
                it.value.language ?: "Unknown"
            )
        }
    }
}