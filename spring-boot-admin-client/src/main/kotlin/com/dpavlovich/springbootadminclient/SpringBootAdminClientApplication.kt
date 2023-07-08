package com.dpavlovich.springbootadminclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootAdminClientApplication

fun main(args: Array<String>) {
	runApplication<SpringBootAdminClientApplication>(*args)
}
