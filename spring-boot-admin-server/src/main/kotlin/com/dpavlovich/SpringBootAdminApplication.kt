package com.dpavlovich

import de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration
import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@EnableAdminServer
@SpringBootApplication(exclude = [AdminServerHazelcastAutoConfiguration::class])
class SpringBootAdminApplication

fun main(args: Array<String>) {
	runApplication<SpringBootAdminApplication>(*args)
}
