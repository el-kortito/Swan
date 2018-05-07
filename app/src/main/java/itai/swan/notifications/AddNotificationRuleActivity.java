package itai.swan.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import itai.swan.DBManager;
import itai.swan.MainActivity;
import itai.swan.R;
import itai.swan.Util;

import static itai.swan.DBManager.NotificationRulesTable.TABLE_NAME;


public class AddNotificationRuleActivity extends Activity {
    NotificationRule notificationRule;
    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notification_rule_activity);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            notificationRule = b.getParcelable("notificationRule");
            if (notificationRule == null) {
                notificationRule = new NotificationRule();
            }
        } else {
            notificationRule = new NotificationRule();
        }

        setDefaultSurfSpotSpinner();
        setDefaultFields();
        setOkButton();
    }

    private void setDefaultFields() {
        EditText editText = (EditText) findViewById(R.id.editText3);
        editText.setText(String.valueOf(String.valueOf(notificationRule.minStars)));
        editText = (EditText) findViewById(R.id.editText4);
        editText.setText(String.valueOf(String.valueOf(notificationRule.minHeight)));
    }

    private void setOkButton() {
        Button btn = (Button) findViewById(R.id.add_notification_ok_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText3);
                String minStars = editText.getText().toString();
                editText = (EditText) findViewById(R.id.editText4);
                String minHeight = editText.getText().toString();

                notificationRule.minStars = Integer.parseInt(minStars);
                notificationRule.minHeight = Double.valueOf(minHeight);


                notificationRule.name = Util.getSpotName(notificationRule.spot, context);
                notificationRule.description = "Minimum Stars: " + notificationRule.minStars + "\nMinimum Height: " + notificationRule.minHeight;

                saveToDB();
                NotificationManager.postNotification(getApplicationContext());
                onBackPressed();
            }
        });
    }

    void saveToDB() {
        DBManager.DBHelper mDbHelper = new DBManager.DBHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBManager.NotificationRulesTable.COLUMN_RULE_NAME, notificationRule.name);
        values.put(DBManager.NotificationRulesTable.COLUMN_DESCRIPTION, notificationRule.description);
        values.put(DBManager.NotificationRulesTable.COLUMN_SPOT, notificationRule.spot);
        values.put(DBManager.NotificationRulesTable.COLUMN_MIN_STARS, notificationRule.minStars);
        values.put(DBManager.NotificationRulesTable.COLUMN_MIN_HEIGHT, notificationRule.minHeight);

        if (!DBManager.tableExists(db, TABLE_NAME)) {
            db.execSQL(DBManager.NotificationRulesTable.SQL_CREATE_NOTIFICATION_RULES_TABLE);
        }

        if (notificationRule.id == null) {
            long newRowId = db.insert(TABLE_NAME, null, values);
        } else {
            db.update(TABLE_NAME, values, "_id=" + notificationRule.id, null);
        }
    }

    private void setDefaultSurfSpotSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.add_notification_case_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.surfSpotNames, R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        String spot = Util.getSpotName(notificationRule.spot, context);
        spinner.setSelection(adapter.getPosition(spot));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final int[] codesArr = getApplicationContext().getResources().getIntArray(R.array.surfSpotCodes);
                notificationRule.spot = codesArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                notificationRule.spot = 3663;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
