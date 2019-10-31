package com.example.photogallerychallenge.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {

        if (networkConnectionReceiverListener != null) {
            networkConnectionReceiverListener!!.onNetworkConnectionChanged(isConnected(context))
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    interface NetworkConnectionListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var networkConnectionReceiverListener: NetworkConnectionListener? = null
    }
}