package com.example.exerite_11;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;

import java.util.Calendar;

public class ExeriteWidgetProvider extends AppWidgetProvider {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        // Schedule updates every hour
        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                ComponentName componentName = new ComponentName(context, ExeriteWidgetProvider.class);
                manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName), R.id.message1);
                handler.postDelayed(this, 3600000); // 1 hour in milliseconds
            }
        };
        handler.post(runnable);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Create an Intent to launch an activity when clicked
        Intent intent = new Intent(context, ExeriteWidgetProvider.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Get the layout for the App Widget and attach an on-click listener to it
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.message1, pendingIntent);

        // Update the text views with messages and emojis
        views.setTextViewText(R.id.message1, "Stay active!");
        views.setTextViewText(R.id.emoji1, "🏋️‍♂️");
        views.setTextViewText(R.id.message2, "Eat healthy!");
        views.setTextViewText(R.id.emoji2, "🥗");

        // Tell the AppWidgetManager to perform an update on the current widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDisabled(Context context) {
        // Remove any pending callbacks when the widget is disabled
        handler.removeCallbacks(runnable);
    }
}