package com.plan.bus.server.com.infoaggregator.RESTConnectors.Krakow

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Gregrog on 2016-08-18.
 */
@Component
@ConfigurationProperties(prefix = "KrakowMPKUrls")
class KrakowMPKUrls {
    var databaseURL : String = ""

}