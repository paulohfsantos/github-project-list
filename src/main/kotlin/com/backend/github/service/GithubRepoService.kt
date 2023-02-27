package com.backend.github.service

import com.backend.github.dto.GitHubDTO
import org.kohsuke.github.GitHub
import org.springframework.stereotype.Service

@Service
class GithubRepoService {

    private val github = GitHub.connectAnonymously()
    suspend fun getRepositories(username: String): List<GitHubDTO> {
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

    suspend fun getRepositoriesByCount(username: String, count: Int): List<GitHubDTO> {
        val user = github.getUser(username)
        val repositories = user.repositories

        return repositories.map {
            GitHubDTO(
                it.value.id.toString(),
                it.value.name,
                it.value.url.toString(),
                it.value.language ?: "Unknown"
            )
        }.sortedByDescending { it.id }.take(count)
    }

    suspend fun getRepositoryById(username: String, id: Long): GitHubDTO {
        val user = github.getUser(username)
        val repositories = user.repositories


        return repositories.map {
            GitHubDTO(
                it.value.id.toString(),
                it.value.name,
                it.value.url.toString(),
                it.value.language ?: "Unknown"
            )
        }.first { it.id == id.toString() }
    }
}