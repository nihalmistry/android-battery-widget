package com.nihalmistry.chargemebatterywidget

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class BatteryLevelService : Service() {

    val batteryReceiver = BatteryReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)

        registerReceiver(batteryReceiver,intentFilter)

        return Service.START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(batteryReceiver)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
