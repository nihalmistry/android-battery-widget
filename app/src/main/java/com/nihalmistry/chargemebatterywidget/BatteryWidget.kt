package com.nihalmistry.chargemebatterywidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [BatteryWidgetConfigureActivity]
 */
class BatteryWidget : AppWidgetProvider() {

    var isServiceRunning = false

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        Log.i(context.packageName,"onUpdate")

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

        if (! isServiceRunning) {
            val intent = Intent(context, BatteryLevelService::class.java)
            ContextCompat.startForegroundService(context, intent)
            isServiceRunning = true
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        Log.i(context.packageName,"onDeleted")
        for (appWidgetId in appWidgetIds) {
            BatteryWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }

        if (isServiceRunning) {
            val intent = Intent(context, BatteryLevelService::class.java)
            context.stopService(intent)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.i(context.packageName,"onEnabled")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.i(context.packageName,"onDisabled")

    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val widgetText = BatteryWidgetConfigureActivity.loadTitlePref(context, appWidgetId)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.battery_widget)
            views.setTextViewText(R.id.battery_percent_text, widgetText)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }
    }
}

