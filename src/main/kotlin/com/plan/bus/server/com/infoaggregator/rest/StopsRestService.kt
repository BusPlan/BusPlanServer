package com.plan.bus.server.com.infoaggregator.rest

import com.plan.bus.server.com.infoaggregator.RESTConnectors.Krakow.H2Config
import com.plan.bus.server.com.infoaggregator.rest.dto.StopDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Created by Gregrog on 2016-08-17.
 */


@RestController
class StopsRestService {

    @Autowired
    private lateinit var h2Config: H2Config

    @RequestMapping("/getAllStops")
    fun getAllStops(): List<StopDTO> {

        val h2Connection = h2Config.dataSource.connection
        val h2Statement = h2Connection.prepareStatement("SELECT Id,Name,Symbol,FirstLetter FROM STOPS")

        val resultSet = h2Statement.executeQuery()
        val stops = ArrayList<StopDTO>()

        while (resultSet.next()) {
            val stop = StopDTO(resultSet.getInt("Id"), resultSet.getString("Name"), resultSet.getString("Symbol"), resultSet.getString("FirstLetter"))
            stops.add(stop)
        }
        return stops
    }

}