package com.plan.bus.server.com.infoaggregator.RESTConnectors.Krakow

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

/**
 * Created by Gregrog on 2016-08-11.
 */

@Configuration
open class H2Config {

    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    open fun getJdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(getDataSource())
    }
}


fun getDataSource(): DataSource {
    val builder = EmbeddedDatabaseBuilder()
    return builder
            .setType(EmbeddedDatabaseType.H2)
            .addScript("create-db.sql")
            .build()
}