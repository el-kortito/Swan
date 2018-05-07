package itai.swan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import itai.swan.notifications.AddNotificationRuleActivity;
import itai.swan.notifications.MyJobService;
import itai.swan.notifications.NotificationManager;
import itai.swan.notifications.NotificationRule;

/**
 * Created by itai on 01/08/17.
 */

public class MainActivity extends Activity {
    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setUpButtons();
        setUpSwitch();
        showRules();
    }

    private void setUpSwitch() {
        final Switch mySwitch = (Switch) findViewById(R.id.switch1);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (sp.getBoolean("isNotificationEnabled", false)) {
            mySwitch.setChecked(true);
            mySwitch.setText(R.string.notifications_enabled);
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                Context context = getApplicationContext();

                if (mySwitch.isChecked()) {
                    mySwitch.setText(R.string.notifications_enabled);
                    MyJobService.scheduleJob(context);
                    sp.edit().putBoolean("isNotificationEnabled", true).apply();
                } else {
                    mySwitch.setText(R.string.notifications_disabled);
                    MyJobService.removeJob(context);
                    sp.edit().putBoolean("isNotificationEnabled", false).apply();
                }
            }
        });
    }

    private void setUpButtons() {
        Button btn = (Button) findViewById(R.id.add_notification_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNotificationRuleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn = (Button) findViewById(R.id.clear_notifications_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.DBHelper mDbHelper = new DBManager.DBHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.execSQL(DBManager.NotificationRulesTable.SQL_DELETE_ENTRIES);
                NotificationManager.postNotification(context);
                showRules();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showRules();
    }

    void showRules() {
        ArrayList<NotificationRule> notificationRulesArray = Util.queryForNotificationRules(getApplicationContext());
        if (notificationRulesArray == null) {
            notificationRulesArray = new ArrayList<>();
        }

        NotificationRulesListAdapter adapter = new NotificationRulesListAdapter(this, notificationRulesArray);
        ListView list = (ListView) findViewById(R.id.rules_list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class NotificationRulesListAdapter extends ArrayAdapter<NotificationRule> {
        private final Activity context;
        private final ArrayList<NotificationRule> notificationRulesArray;

        public NotificationRulesListAdapter(Activity context, ArrayList<NotificationRule> notificationRulesArray) {
            super(context, R.layout.rules_list, notificationRulesArray);

            this.context = context;
            this.notificationRulesArray = notificationRulesArray;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.rules_list, null, true);

            TextView textView = (TextView) rowView.findViewById(R.id.rule_name);
            textView.setText(notificationRulesArray.get(position).name);
            textView = (TextView) rowView.findViewById(R.id.rule_description);
            textView.setText(notificationRulesArray.get(position).description);



            LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.rule_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddNotificationRuleActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("notificationRule", notificationRulesArray.get(position));
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                }
            });

            ImageButton trashButton = (ImageButton) rowView.findViewById(R.id.rule_trash);

            final int finalPosition = position;
            trashButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBManager.DBHelper mDbHelper = new DBManager.DBHelper(getApplicationContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    db.delete(DBManager.NotificationRulesTable.TABLE_NAME, DBManager.NotificationRulesTable._ID + "=" + notificationRulesArray.get(finalPosition).id, null);
                    showRules();
                    NotificationManager.postNotification(context);
                }
            });
            return rowView;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}