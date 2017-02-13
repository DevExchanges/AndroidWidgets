package info.devexchanges.updatewidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.util.Calendar;

public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String time = getCurrentDateTime();

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget_updating);
        view.setTextViewText(R.id.txt_widget, time);
        ComponentName theWidget = new ComponentName(this, UpdatingWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, view);

        return super.onStartCommand(intent, flags, startId);
    }

    private String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);

        return hour + ":" + minute;
    }
}

