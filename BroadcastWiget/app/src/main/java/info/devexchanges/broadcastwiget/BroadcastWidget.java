package info.devexchanges.broadcastwiget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Calendar;

public class BroadcastWidget extends AppWidgetProvider  {
    private static final String ACTION_BROADCASTWIDGETSAMPLE = "ACTION_BROADCASTWIDGETSAMPLE";

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Build the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_broadcast);

        // Create an Intent which is pointing this class
        Intent intent = new Intent(context, BroadcastWidget.class);
        intent.setAction(ACTION_BROADCASTWIDGETSAMPLE);
        // And this time we are sending a broadcast with getBroadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Update widget when clicked
        views.setOnClickPendingIntent(R.id.txt_widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_BROADCASTWIDGETSAMPLE.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_broadcast);
            views.setTextViewText(R.id.txt_widget, getCurrentDateTime());

            // This time we don't have widgetId. Reaching our widget with that way
            ComponentName appWidget = new ComponentName(context, BroadcastWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }

    private String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        int second = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);

        return hour + ":" + minute + ":" + second;
    }
}

