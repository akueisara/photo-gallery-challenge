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

    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        networkConnectionReceiver = NetworkConnectionReceiver()
        registerReceiver(networkConnectionReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume() {
        super.onResume()
        NetworkConnectionReceiver.networkConnectionReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkConnectionReceiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        dataBinding.errorTextView.isVisible = !isConnected
    }
}