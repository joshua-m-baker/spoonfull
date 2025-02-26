package com.joshua_m_baker

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
	info = Info(
		title = "Spoonfull",
		version = "0.0",
		description = "Rest API for Spoonfull website",
	),
	servers = [Server(url = "https://spoonfull.joshua-m-baker.com")]
)
object Application {
	@JvmStatic
	fun main(args: Array<String>) {
		run(*args)
	}
}
