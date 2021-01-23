package com.example.beers.core.platform

import android.content.Context
import com.example.beers.core.extensions.networkInfo


class NetworkHandler
(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}