package com.digimon.example.reactiversocket.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    //runApplication<ServerApplication>(*args)
    SpringApplicationBuilder()
        .main(ServerApplication::class.java)
        .sources(ServerApplication::class.java)
        .profiles("server")
        .run(*args)
}
