package com.backend.github

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan


@SpringBootApplication
@ServletComponentScan
class GithubApplication

fun main(args: Array<String>) {
	runApplication<GithubApplication>(*args)
}
