package info.devexchanges.simplewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class SimpleWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_simple);

        // Create an Intent object includes my website address
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.devexchanges.info/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //handle click event of the TextView (launch browser and go to my website)
        views.setOnClickPendingIntent(R.id.txtWidget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
