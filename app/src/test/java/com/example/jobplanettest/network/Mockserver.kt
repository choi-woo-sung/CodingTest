package com.example.jobplanettest.network

import okhttp3.mockwebserver.MockWebServer

class Mockserver {
    companion object {
        val server = MockWebServer()
    }
}