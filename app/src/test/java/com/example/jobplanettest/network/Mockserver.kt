package com.dev6.data.network

import okhttp3.mockwebserver.MockWebServer

class Mockserver {
    companion object {
        val server = MockWebServer()
    }
}