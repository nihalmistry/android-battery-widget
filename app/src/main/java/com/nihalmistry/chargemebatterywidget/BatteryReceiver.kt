package com.nihalmistry.chargemebatterywidget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.RemoteViews

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context,BatteryWidget::class.java)
        val ids = appWidgetManager.getAppWidgetIds(componentName)

        Log.i("onReceive",intent.action)

        val rv = RemoteViews(context.packageName,R.layout.battery_widget)
        for (appWidgetId in ids) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.action)) {
                val level: Float = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0).toFloat()
                val scale: Float = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0).toFloat()
                val percent = (level/scale) * 100
                rv.setTextViewText(R.id.battery_percent_text,percent.toInt().toString()+"%")
                appWidgetManager.updateAppWidget(componentName,rv)
            } else if (Intent.ACTION_POWER_CONNECTED.equals(intent.action)) {

            } else if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.action)) {

            }
        }
    }
}
