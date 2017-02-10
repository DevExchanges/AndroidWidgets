package info.devexchanges.updatewidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class UpdatingWidget extends AppWidgetProvider {
    private PendingIntent pendingIntent;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, UpdateService.class);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pendingIntent);
        //if you need to call your pendingIntent less then 60 sec
        //answer is here:
        //http://stackoverflow.com/questions/29998313/how-to-run-background-service-after-every-5-sec-not-working-in-android-5-1
        Log.d("UpdatingWidget: ","onUpdate");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("UpdatingWidget: ","onReceive");

    }
}