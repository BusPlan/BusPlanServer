package com.plan.bus.server.com.infoaggregator.RESTConnectors.Krakow

import com.plan.bus.server.model.BusStop
import com.plan.bus.server.repository.BusStopRepository
import org.apache.log4j.Logger
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.channels.Channels
import java.sql.DriverManager

/**
 * Created by Gregrog on 2016-08-03.
 */
@Component
class KrakowMPKConnector {
    val log = Logger.getLogger(this.javaClass.name)

    @Autowired
    private lateinit var busStopRepository: BusStopRepository
    @Autowired
    private lateinit var krakowMPKUrls: KrakowMPKUrls


    fun updateSchedule() {

        val databaseURL = getDatabaseURL()
        val fileName = getFileName(databaseURL)
        log.info(databaseURL)
        if (!File(fileName).exists()) {
            downloadDatabase(databaseURL, fileName)

            log.info(File(".").canonicalFile)
        } else {
            log.info("File already downloaded.")
        }
        Class.forName("org.sqlite.JDBC")
        val sJdbc = "jdbc:sqlite"
        val sDbUrl = sJdbc + ":" + fileName
        val connection = DriverManager.getConnection(sDbUrl)
        val preparedStatement = connection.createStatement()
        val resultSet = preparedStatement.executeQuery("SELECT Id, Name, Symbol, FirstLetter FROM Stops")

        while (resultSet.next()) {
            val stop = BusStop(null, resultSet.getString("Name"), resultSet.getString("Symbol"), resultSet.getString("FirstLetter"))
            busStopRepository.save(stop)
        }
    }


    fun getDatabaseURL(): String {
        val url = URL(krakowMPKUrls.databaseURL)
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

    private fun downloadDatabase(databaseURL: String, fileName: String) {
        val url = URL(databaseURL)
        val channel = Channels.newChannel(url.openStream())
        val fileOutputStream = FileOutputStream(fileName)
        fileOutputStream.channel.transferFrom(channel, 0, Long.MAX_VALUE)
        fileOutputStream.close()
    }

    private fun getFileName(databaseURL: String) = databaseURL.substring(databaseURL.lastIndexOf("/") + 1)
}