package com.github.butul0ve.spacexchecker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build

fun Context.isConnectedToInternet(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val networks = manager.allNetworks
        networks.forEach {
            network ->
            val info = manager.getNetworkInfo(network)
            if (info.state == NetworkInfo.State.CONNECTED) return true
        }
    } else {
        val networkInfo = manager.allNetworkInfo
        networkInfo.forEach {
            info ->
            if (info.state == NetworkInfo.State.CONNECTED) return true
        }
    }

    return false
}