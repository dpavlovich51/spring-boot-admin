package com.dpavlovich.springbootadminclient

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootAdminClientApplication {

	@Value("#{systemEnvironment['SPRING_BOOT_SERVER']}")
	lateinit var serverUrl: String

	@PostConstruct
	fun postConstract() {
		println("show the server url: $serverUrl")
	}

}

fun main(args: Array<String>) {
	runApplication<SpringBootAdminClientApplication>(*args)
}
