package itai.swan.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

import org.json.JSONException;

import java.util.Calendar;

import itai.swan.R;
import itai.swan.Util;
import itai.swan.forecast.DailyForecast;
import itai.swan.forecast.Forecast;
import itai.swan.forecast.ForecastEntry;

/**
 * Created by itai on 10/07/17.
 */

public class WidgetManager extends AppWidgetProvider {
    RemoteViews remoteViews;
    Context context;
    AppWidgetManager appWidgetManager;

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        this.context = context;
        this.appWidgetManager = appWidgetManager;

        for (int appWidgetId : appWidgetIds) {
            new Task(appWidgetId).execute();
        }

        /*new NotificationManager(context).postNotification();*/
    }

    private class Task extends AsyncTask<Void, Void, Forecast> {
        int widgetId;

        public Task(int widgetId) {
            this.widgetId = widgetId;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Forecast doInBackground(Void... arg) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            int surfSpot = sp.getInt("surfSpotCode" + widgetId, 3663);

            try {
                return new Forecast(surfSpot);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Forecast forecast) {
            displayInWidget(forecast);
            setOnClick();
        }

        void setOnClick() {
//            Resources res = context.getResources();
//            int mainLayoutId = res.getIdentifier("mainLayout", "id", context.getPackageName());
//            remoteViews.setclick

            String url = "http://www.example.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.mainLayout, pendingIntent);



        }

        void displayInWidget(Forecast forecast) {
            Resources res = context.getResources();
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

            String spot = Util.getSpotName(sp.getInt("surfSpotCode" + widgetId, 3663), context);

            int textId = res.getIdentifier("title", "id", context.getPackageName());
            remoteViews.setTextViewText(textId, spot);

            Calendar cal = Calendar.getInstance();

            int i = 0;
            for (DailyForecast dailyForecast : forecast) {
                ForecastEntry daySummery = dailyForecast.summery();

                long time = daySummery.getLocalTimestamp();
                cal.setTimeInMillis(time * 1000);

                textId = res.getIdentifier("text" + i, "id", context.getPackageName());
                remoteViews.setTextViewText(textId, daySummery.getSwell().getMinBreakingHeight() +
                        "-" + daySummery.getSwell().getMaxBreakingHeight() + "\n"
                        + DateFormat.format("EEE", cal).toString());

                final int imageId = res.getIdentifier("image" + i, "id", context.getPackageName());
                Bitmap bitmap = Util.PrepareImage(context, daySummery);
                remoteViews.setImageViewBitmap(imageId, bitmap);

                appWidgetManager.updateAppWidget(widgetId, remoteViews);
                i++;
            }
        }
    }
}