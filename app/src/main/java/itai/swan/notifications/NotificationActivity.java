package itai.swan.notifications;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import itai.swan.MainActivity;
import itai.swan.R;
import itai.swan.Util;
import itai.swan.forecast.DailyForecast;
import itai.swan.forecast.ForecastEntry;

/**********************************************************************************************/

public class NotificationActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        updateListView();
    }

    void updateListView() {
        Calendar cal = Calendar.getInstance();

        Intent parentIntent = getIntent();

        //ArrayList<DailyForecast> wavyDays = parentIntent.getParcelableArrayListExtra("notificationsList");
        ArrayList<NotificationRule> notificationRulesArray = Util.queryForNotificationRules(this);
        ArrayList<DailyForecast> wavyDays = NotificationManager.getNotificationResultList(notificationRulesArray);

        ArrayList<String> spotArr = new ArrayList<>();
        ArrayList<String> dateArr = new ArrayList<>();
        ArrayList<Bitmap> bitmapArr = new ArrayList<>();
        ArrayList<String> heightArr = new ArrayList<>();


        for (DailyForecast dailyForecast : wavyDays) {
            ForecastEntry daySummery = dailyForecast.summery();
            spotArr.add(Util.getSpotName(dailyForecast.get(0).getSurfSpot(), getApplicationContext()));
            cal.setTimeInMillis((long) daySummery.getLocalTimestamp() * 1000L);

            dateArr.add(DateFormat.format("dd/MM\nEEE", cal).toString());
            bitmapArr.add(Util.PrepareImage(getApplicationContext(), daySummery));
            heightArr.add(daySummery.getSwell().getMinBreakingHeight() + "-" + daySummery.getSwell().getMaxBreakingHeight());
        }

        NotificationResultsListAdapter adapter = new NotificationResultsListAdapter(this, spotArr, dateArr, bitmapArr, heightArr);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public class NotificationResultsListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> spots;
        private final ArrayList<String> dates;
        private final ArrayList<Bitmap> images;
        private final ArrayList<String> heights;

        public NotificationResultsListAdapter(Activity context, ArrayList<String> spots, ArrayList<String> dates, ArrayList<Bitmap> images, ArrayList<String> heights) {
            super(context, R.layout.notification_list, dates);

            this.context = context;
            this.spots = spots;
            this.dates = dates;
            this.images = images;
            this.heights = heights;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.notification_list, null, true);

            TextView spotTextView = (TextView) rowView.findViewById(R.id.spot);
            TextView dateTextView = (TextView) rowView.findViewById(R.id.date);
            ImageView starsImageView = (ImageView) rowView.findViewById(R.id.stars);
            TextView heightTextView = (TextView) rowView.findViewById(R.id.height);

            if (position == 0 || !spots.get(position).equals(spots.get(position - 1))) {
                spotTextView.setText(spots.get(position));
                spotTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ZZZ", "title clicked!");
                    }
                });
            } else {
                ((ViewGroup) spotTextView.getParent()).removeView(spotTextView);
            }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ZZZ", "row clicked!");
                }
            });

            dateTextView.setText(dates.get(position));
            starsImageView.setImageBitmap(images.get(position));
            starsImageView.setScaleX(1.2f);
            starsImageView.setScaleY(1.2f);
            heightTextView.setText(heights.get(position));

            return rowView;

        }
    }
}
