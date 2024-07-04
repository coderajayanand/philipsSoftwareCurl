package com.philips.hsdp.apis.support.logging

import java.net.URLEncoder

class CurlGenerator {

    fun generateCurlCommand(url: String, method: String, headers: Map<String, String>, queryParams: Map<String, String>, body: String? = null): String {
        val curlCommand = StringBuilder("curl -X $method '$url")

        if (queryParams.isNotEmpty()) {
            curlCommand.append("?")
            queryParams.forEach { (key, value) ->
                curlCommand.append("$key=${URLEncoder.encode(value, "UTF-8")}&")
            }
            curlCommand.deleteCharAt(curlCommand.length - 1)  // Remove the last '&'
        }

        curlCommand.append("'")

        headers.forEach { (key, value) ->
            curlCommand.append(" -H '$key: $value'")
        }

        body?.let {
            curlCommand.append(" -d '${URLEncoder.encode(it, "UTF-8")}'")
        }

        return curlCommand.toString()
    }
}
