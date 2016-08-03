package com.plan.bus.server.com.plan.bus.server.infoaggregator.RESTConnectors.Krakow

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
* Created by Gregrog on 2016-08-03.
*/
class KrakowMPKConnector {

    fun updateSchedule() {

        var databaseURL = getDatabaseURL()
        println(databaseURL)

    }

    fun getDatabaseURL(): String {
        val url = URL("http://m.rozklady.mpk.krakow.pl/Services/data.asmx/GetDatabase")
        val content = "{}"
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        connection.setRequestProperty("Content-Length", content.length.toString())
        val outputStraeam = connection.getOutputStream()
        outputStraeam.write(content.toByteArray())
        connection.connect()


        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val result = bufferedReader.readLine()

        bufferedReader.close()

        val jsonObject = JSONObject(result)
        return jsonObject.getString("d")
    }
}