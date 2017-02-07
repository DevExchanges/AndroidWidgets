package info.devexchanges.configurablewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Spinner;

import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private AppWidgetManager widgetManager;
    private RemoteViews remoteViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_config);

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        View btnCreate = findViewById(R.id.btn_go);

        //create data
        ArrayList<String> spnOptions = new ArrayList<>();
        spnOptions.add("Go to my site");
        spnOptions.add("Go to Google page");

        //set adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spnOptions);
        spinner.setAdapter(adapter);

        //initializing RemoteViews and AppWidgetManager
        widgetManager = AppWidgetManager.getInstance(this);
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_configurable);

        // Find the widget id from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedUrl;
                if (spinner.getSelectedItemPosition() == 0) {
                    // Go to my website with this selection (position = 1)
                    selectedUrl = "http://www.devexchanges.info";
                } else {
                    selectedUrl = "https://www.google.com";
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedUrl));
                PendingIntent pending = PendingIntent.getActivity(ConfigActivity.this, 0, intent, 0);
                remoteViews.setOnClickPendingIntent(R.id.text_view, pending);
                if (spinner.getSelectedItemPosition() == 0) {
                    remoteViews.setTextViewText(R.id.text_view, "Click to visit my site");
                } else {
                    remoteViews.setTextViewText(R.id.text_view, "Click to visit Google");
                }
                widgetManager.updateAppWidget(mAppWidgetId, remoteViews);
                Intent resultValue = new Intent();

                // Set the results as expected from a 'configure activity'.
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }
}
