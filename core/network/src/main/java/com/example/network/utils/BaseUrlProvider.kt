package com.example.network.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseUrlProvider @Inject constructor(
) {

    // Initially set to a static base URL
    private var baseUrl: String ="BASE_URL"

    // Get the current base URL
    fun getBaseUrl(): String {
        return baseUrl
    }

    // Update the base URL with the new dynamic URL
    fun updateBaseUrl(newBaseUrl: String) {
        baseUrl = newBaseUrl
    }
}
