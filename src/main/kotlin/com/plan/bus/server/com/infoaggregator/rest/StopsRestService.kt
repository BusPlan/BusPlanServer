package com.plan.bus.server.com.infoaggregator.rest

import com.plan.bus.server.model.BusStop
import com.plan.bus.server.repository.BusStopRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Gregrog on 2016-08-17.
 */


@RestController
class StopsRestService {

    @Autowired
    private lateinit var busStopRepository: BusStopRepository

    @RequestMapping("/getAllStops")
    fun getAllStops(): List<BusStop> = busStopRepository.findAll().filterNotNull()

}