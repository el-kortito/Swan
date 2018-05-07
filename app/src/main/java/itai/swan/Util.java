package itai.swan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import itai.swan.forecast.ForecastEntry;
import itai.swan.notifications.NotificationRule;

/**
 * Created by itai on 08/08/17.
 */

public class Util {
    static Map<Integer, String> spotCodes;
    static Gson gson;
    static Object lock = new Object();

    static public <T> T deepCopy(T object, Class<T> type) {
        if (gson == null) {
            synchronized (lock) {
                if (gson == null) {
                    try {
                        Gson tmp = new Gson();
                        gson = tmp;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return gson.fromJson(gson.toJson(object, type), type);
    }

    static public Bitmap PrepareImage(Context context, ForecastEntry hourlyForecast) {
        int solidCount = hourlyForecast.getSolidRating();
        int fadedCount = hourlyForecast.getFadedRating();

        Bitmap solidRating = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_filled);
        Bitmap fadedRating = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_faded);
        int height = solidRating.getHeight();
        double width = solidRating.getWidth();

        double totalCount = solidCount + fadedCount;
        if (totalCount == 0) {
            return Bitmap.createBitmap((int)(width * 4d), height, Bitmap.Config.ARGB_8888);
        }

        Bitmap comboBitmap = Bitmap.createBitmap((int)(width * 4d), height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(comboBitmap);

        double initialOffset = (width * ((5d - totalCount) * 3d/4d)) / 2d;
        double i = 0;
        while (i < solidCount) {
            comboImage.drawBitmap(solidRating,(int)(initialOffset + width * i * 3d/4d), 0f, null);
            i++;
        }
        while(i < totalCount) {
            comboImage.drawBitmap(fadedRating, (int)(initialOffset + width * i * 3d/4d), 0f, null);
            i++;
        }

        if (i < 6) {
            Bitmap blank = Bitmap.createBitmap((int)width, height, Bitmap.Config.ARGB_8888);
            while (i < 6) {
                comboImage.drawBitmap(blank, (int) (initialOffset + width * i * 3d / 4d), 0f, null);
                i++;
            }
        }
        return comboBitmap;
    }

    static public String getSpotName(int code, Context context) {
        if (spotCodes == null) {
            initSpotCodes(context);
        }

        return spotCodes.get(code);
    }

    static void initSpotCodes(Context context) {
        final int[] codesArr = context.getResources().getIntArray(R.array.surfSpotCodes);
        final String[] namesArr = context.getResources().getStringArray(R.array.surfSpotNames);

        for (int i : codesArr) {
            Log.d("ZZZ codesArr", ""+i);
        }
        for(String s : namesArr) {
            Log.d("ZZZ namesArr", s);
        }

        spotCodes = new HashMap<>();
        for (int i = 0; i < codesArr.length; i++) {
            spotCodes.put(codesArr[i], namesArr[i]);
        }
    }

    static public ArrayList<NotificationRule> queryForNotificationRules(Context context) {
        DBManager.DBHelper mDbHelper = new DBManager.DBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBManager.NotificationRulesTable._ID,
                DBManager.NotificationRulesTable.COLUMN_RULE_NAME,
                DBManager.NotificationRulesTable.COLUMN_DESCRIPTION,
                DBManager.NotificationRulesTable.COLUMN_SPOT,
                DBManager.NotificationRulesTable.COLUMN_MIN_STARS,
                DBManager.NotificationRulesTable.COLUMN_MIN_HEIGHT,
        };

        String sortOrder = DBManager.NotificationRulesTable.COLUMN_SPOT + " DESC";

        ArrayList<NotificationRule> notificationRulesArray = new ArrayList<>();

        if (DBManager.tableExists(db, DBManager.NotificationRulesTable.TABLE_NAME)) {
            Cursor cursor = db.query(
                    DBManager.NotificationRulesTable.TABLE_NAME,   // The table to query
                    projection,                                    // The columns to return
                    null,                                          // null - no columns for the WHERE clause
                    null,                                          // null - no values for the WHERE clause
                    null,                                          // null - don't group the rows
                    null,                                          // null - don't filter by row groups
                    sortOrder                                      // The sort order
            );


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        NotificationRule notificationRule = new NotificationRule();
                        notificationRule.id = cursor.getInt(cursor.getColumnIndex(DBManager.NotificationRulesTable._ID));
                        notificationRule.name = cursor.getString(cursor.getColumnIndex(DBManager.NotificationRulesTable.COLUMN_RULE_NAME));
                        notificationRule.spot = cursor.getInt(cursor.getColumnIndex(DBManager.NotificationRulesTable.COLUMN_SPOT));
                        notificationRule.description = cursor.getString(cursor.getColumnIndex(DBManager.NotificationRulesTable.COLUMN_DESCRIPTION));
                        notificationRule.minStars = cursor.getInt(cursor.getColumnIndex(DBManager.NotificationRulesTable.COLUMN_MIN_STARS));
                        notificationRule.minHeight = cursor.getDouble(cursor.getColumnIndex(DBManager.NotificationRulesTable.COLUMN_MIN_HEIGHT));

                        notificationRulesArray.add(notificationRule);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

        return notificationRulesArray;
    }
}
