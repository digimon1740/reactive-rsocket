package com.digimon.example.reactiversocket.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
class ClientApplication

fun main(args: Array<String>) {
    //runApplication<ServerApplication>(*args)
    SpringApplicationBuilder()
        .main(ClientApplication::class.java)
        .sources(ClientApplication::class.java)
        .profiles("client")
        .run(*args)
}
