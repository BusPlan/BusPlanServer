package com.plan.bus.server.com.plan.bus.server.infoaggregator.RESTConnectors.Krakow

import org.apache.log4j.Logger
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

/**
* Created by Gregrog on 2016-08-03.
*/
class KrakowMPKConnector {
    val log = Logger.getLogger(this.javaClass.name)

    fun updateSchedule() {

        val databaseURL = getDatabaseURL()

        if (!File(getFileName(databaseURL)).exists()) {
            downloadDatabase(databaseURL)
            log.info(File(".").canonicalFile)
        } else {
            log.info("File already downloaded.")
        }
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

    private fun downloadDatabase(databaseURL: String) {
        val url = URL(databaseURL)
        val channel = Channels.newChannel(url.openStream())
        val fileOutputStream = FileOutputStream(getFileName(databaseURL))
        fileOutputStream.channel.transferFrom(channel, 0, Long.MAX_VALUE)
        fileOutputStream.close()
    }

    private fun getFileName(databaseURL: String) = "MPKDatabase." + databaseURL.substring(databaseURL.lastIndexOf(".") + 1)
}