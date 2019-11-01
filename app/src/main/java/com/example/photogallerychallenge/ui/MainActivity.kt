package com.example.photogallerychallenge.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.broadcast.NetworkConnectionReceiver
import com.example.photogallerychallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NetworkConnectionReceiver.NetworkConnectionListener {

    private lateinit var dataBinding: ActivityMainBinding

    private var networkConnectionReceiver: NetworkConnectionReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        networkConnectionReceiver = NetworkConnectionReceiver()
        registerReceiver(networkConnectionReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        NetworkConnectionReceiver.networkConnectionReceiverListener = this

    }

    override fun onPause() {
        super.onPause()
        if(networkConnectionReceiver != null) {
            unregisterReceiver(networkConnectionReceiver)
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        dataBinding.offlineTextView.isVisible = !isConnected
    }
}