package com.plan.bus.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
open class BusPlanServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(BusPlanServerApplication::class.java, *args)
}
