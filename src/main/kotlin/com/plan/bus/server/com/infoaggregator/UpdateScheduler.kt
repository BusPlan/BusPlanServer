package com.plan.bus.server.com.infoaggregator

import com.plan.bus.server.com.infoaggregator.RESTConnectors.Krakow.KrakowMPKConnector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.sql.DataSource
import kotlin.concurrent.fixedRateTimer

/**
 * Created by Gregrog on 2016-08-03.
 */
@Component
class UpdateScheduler : ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private lateinit var krakowMpkConnector: KrakowMPKConnector

    override fun onApplicationEvent(event: ApplicationReadyEvent) {

        fixedRateTimer(name = "Database dowloading timer", initialDelay = 0, period = 24 * 60 * 60 * 1000) {
            krakowMpkConnector.updateSchedule()
        }
    }
}