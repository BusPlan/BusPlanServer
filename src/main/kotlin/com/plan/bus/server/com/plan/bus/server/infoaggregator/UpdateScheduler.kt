package com.plan.bus.server.com.plan.bus.server.infoaggregator

import com.plan.bus.server.com.plan.bus.server.infoaggregator.RESTConnectors.Krakow.KrakowMPKConnector
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.fixedRateTimer

/**
 * Created by Gregrog on 2016-08-03.
 */
@Component
class UpdateScheduler : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {

        fixedRateTimer(name = "Database dowloading timer", initialDelay = 0, period = 24 * 60 * 60 * 1000) {
            KrakowMPKConnector().updateSchedule()
        }
    }
}