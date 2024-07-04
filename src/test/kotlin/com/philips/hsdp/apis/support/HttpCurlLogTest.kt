package com.philips.hsdp.apis.support

import com.philips.hsdp.apis.support.logging.CurlLogger
import com.philips.hsdp.apis.support.logging.PlatformLogger
import org.junit.Test

class HttpCurlLogTest {

    private val httpCurlLog = HttpCurlLog()

    @Test
    fun testGetUsers(@CurlLogger logger: PlatformLogger) {
        val response = httpCurlLog.getUsers(logger)
        println("Response from API: $response")
    }
}
