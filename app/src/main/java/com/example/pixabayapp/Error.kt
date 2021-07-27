package com.example.pixabayapp

import java.lang.Exception

sealed class Error {
    data class ConnectionError(val message: String, val cause: Exception? = null) : Error()
}
