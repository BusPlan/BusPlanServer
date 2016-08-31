package com.plan.bus.server.repository

import com.plan.bus.server.model.BusStop
import org.springframework.data.repository.CrudRepository

/**
 * Created by Michal on 2016-08-31.
 */
interface BusStopRepository : CrudRepository<BusStop, Long> {
}