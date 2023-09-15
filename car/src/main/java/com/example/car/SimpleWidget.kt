package com.example.car

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class SimpleWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds)
            updateAppWidget(context, appWidgetManager, appWidgetId)
    }
}

internal fun updateAppWidget(context: Context,
                             appWidgetManager: AppWidgetManager,
                             appWidgetId: Int) {
    val widgetText = "hello world"
    val views = RemoteViews(context.packageName, R.layout.simple_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}