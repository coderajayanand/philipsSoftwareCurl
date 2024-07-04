package com.philips.hsdp.apis.support

import com.philips.hsdp.apis.support.logging.CurlGenerator
import com.philips.hsdp.apis.support.logging.CurlLogger
import com.philips.hsdp.apis.support.logging.PlatformLogger
import sun.rmi.transport.Endpoint
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class HttpCurlLog {

    val curlGenerator = CurlGenerator()

    @CurlLogger
    fun getUsers(logger: PlatformLogger): String {
        val url = "https://reqres.in/api/users?page=2"

        val queryParams = extractQueryParams(url)
        val curlCommand = curlGenerator.generateCurlCommand(url, "GET", emptyMap(), queryParams, null)
        logger.debug { "Generated CURL command: $curlCommand" }

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val response = connection.inputStream.use {
            BufferedReader(InputStreamReader(it, "UTF-8")).lines().collect(Collectors.joining("\n"))
        }

        connection.disconnect()

        return response
    }

    private fun extractQueryParams(url: String): Map<String, String> {
        val queryParams = mutableMapOf<String, String>()
        val uri = java.net.URI(url)
        val query = uri.query
        if (query != null) {
            val pairs = query.split("&")
            pairs.forEach {
                val pair = it.split("=")
                if (pair.size == 2) {
                    queryParams[pair[0]] = pair[1]
                }
            }
        }
        return queryParams
    }
}
