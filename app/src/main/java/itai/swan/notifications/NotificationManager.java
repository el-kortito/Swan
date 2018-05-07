package itai.swan.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import itai.swan.R;
import itai.swan.Util;
import itai.swan.forecast.DailyForecast;
import itai.swan.forecast.Forecast;


/**
 * Created by itai on 31/07/17.
 */

public class NotificationManager {
    static public void postNotification(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                if (!sp.getBoolean("isNotificationEnabled", false)) {
                    return;
                }

                Looper.prepare();
                if (shouldNotify(Util.queryForNotificationRules(context))) {
                    post(context);
                } else {
                    removeNotification(context);
                }
            }
        }).start();
    }

    static private void post(Context context) {
        Intent resultIntent = new Intent(context, NotificationActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Notification notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.icon)
                        .setContentTitle("Surf's up!")
                        .setContentText("tap for details")
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)//make it disappear when pressed
                        .build();

        int notificationId = 1;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, notification);
    }

    static private void removeNotification(Context context) {
        int notificationId = 1;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
    }

    static private boolean shouldNotify(ArrayList<NotificationRule> notificationRulesArray) {
        HashMap<Integer, Forecast> map = new HashMap<>();

        for (NotificationRule notificationRule : notificationRulesArray) {
            int spot = notificationRule.spot;
            if (!map.containsKey(spot)) {
                try {
                    map.put(spot, new Forecast(spot));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        for (NotificationRule notificationRule : notificationRulesArray) {
            Forecast forecast = map.get(notificationRule.spot);
            for (DailyForecast dailyForecast : forecast) {
                if (notificationRule.isSatisfiedBy(dailyForecast.summery())) {
                    return true;
                }
            }
        }
        return false;
    }

    static ArrayList<DailyForecast> getNotificationResultList(ArrayList<NotificationRule> notificationRulesArray) {
        HashMap<Integer, Forecast> map = new HashMap<>();

        for (NotificationRule notificationRule : notificationRulesArray) {
            int spot = notificationRule.spot;
            if (!map.containsKey(spot)) {
                try {
                    map.put(spot, new Forecast(spot));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        final TreeSet<DailyForecast> wavyDays = new TreeSet<>(new Comparator<DailyForecast>() {
            @Override
            public int compare(DailyForecast o1, DailyForecast o2) {
                int compareSpots = o1.summery().getSurfSpot() - o2.summery().getSurfSpot();
                return compareSpots != 0 ? compareSpots : o1.summery().getLocalTimestamp().compareTo(o2.summery().getLocalTimestamp());
            }
        });
        for (NotificationRule notificationRule : notificationRulesArray) {
            Forecast forecast = map.get(notificationRule.spot);
            for (DailyForecast dailyForecast : forecast) {
                if (notificationRule.isSatisfiedBy(dailyForecast.summery())) {
                    wavyDays.add(dailyForecast);
                }
            }
        }

        return new ArrayList<>(wavyDays);
    }
}
