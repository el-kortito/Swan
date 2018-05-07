package itai.swan.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import itai.swan.R;

/**********************************************************************************************/


public class ConfigurationActivity extends Activity implements AdapterView.OnItemSelectedListener, android.view.View.OnClickListener {
    Context context;
    int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration_activity);

        context = ConfigurationActivity.this;
        mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

        setResult(RESULT_CANCELED);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.surfSpotNames, R.layout.spinner_dropdown);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        final int[] codesArr = context.getResources().getIntArray(R.array.surfSpotCodes);
        editor.putInt("surfSpotCode" + mAppWidgetId, codesArr[position]);
        editor.apply();

        Intent intent = new Intent(this, WidgetManager.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = {mAppWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
