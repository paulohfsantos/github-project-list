package com.backend.github.service

import com.backend.github.dto.GitHubDTO
import com.backend.github.exception.CustomExceptionHandler
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.springframework.stereotype.Service

@Service
class GithubRepoService {

    private val github = GitHubBuilder()
        .withOAuthToken(System.getenv("GH_TOKEN")).build()
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
        val repository = user.getRepository(id.toString())
        return GitHubDTO(
            repository.id.toString(),
            repository.name,
            repository.url.toString(),
            repository.language ?: "Unknown"
        )
    }

    suspend fun getRepositoryByName(username: String, name: String): GitHubDTO {
        val user = github.getUser(username)
        val repository = user.getRepository(name)
        return GitHubDTO(
            repository.id.toString(),
            repository.name,
            repository.url.toString(),
            repository.language ?: "Unknown"
        )
    }
}