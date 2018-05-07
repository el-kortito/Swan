package itai.swan.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by itai on 08/08/17.
 */

public class Forecast implements Iterable<DailyForecast>, Parcelable {
    private int surfSpot;
    ArrayList<ForecastEntry> completeForecast;

    public Forecast(int surfSpot) throws JSONException {
        this.surfSpot = surfSpot;
        String json = null;
        try {
            json = httpReq().get();
            Log.d("ZZZ", json);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        ForecastEntry[] forecastArr = gson.fromJson(json, ForecastEntry[].class);
        for (ForecastEntry forecastEntry : forecastArr) {
            forecastEntry.setSurfSpot(surfSpot);
        }
        completeForecast = new ArrayList<>(Arrays.asList(forecastArr));
    }

    private Future<String> httpReq() {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));
        Future<String> future =  executor.submit(new Callable<String>() {

            @Override
            public String call() {
                String spotString = (String.valueOf(surfSpot));
                try {
                    URL url = new URL("http://magicseaweed.com/api/469d222fe5e126d26f58819a66120274/forecast/?spot_id=" + spotString + "&units=eu");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    return IOUtils.toString(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });


        return future;
    }

    public DailyForecast get(int dayIdx) {
        return new DailyForecast(dayIdx, Forecast.this);
    }

    @Override
    public Iterator<DailyForecast> iterator() {
        return new DayIterator();
    }

    class DayIterator implements Iterator<DailyForecast> {
        int dayIdx = 0;

        @Override
        public boolean hasNext() {
            return dayIdx < 5;
        }

        @Override
        public DailyForecast next() {
            return new DailyForecast(dayIdx++, Forecast.this);
        }
    }

    /**********************************************************************************************/

    protected Forecast(Parcel in) {
        surfSpot = in.readInt();
        if (in.readByte() == 0x01) {
            completeForecast = new ArrayList<ForecastEntry>();
            in.readList(completeForecast, ForecastEntry.class.getClassLoader());
        } else {
            completeForecast = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(surfSpot);
        if (completeForecast == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(completeForecast);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
}


/*{
    "timestamp": 1500033600,
    "localTimestamp": 1500044400,
    "issueTimestamp": 1499644800,
    "fadedRating": 0,
    "solidRating": 0,
    "swell": {
        "absMinBreakingHeight": 0.46,
        "absMaxBreakingHeight": 0.72,
        "unit": "m",
        "minBreakingHeight": 0.5,
        "maxBreakingHeight": 0.7,
        "components": {
            "combined": {
                "height": 0.7,
                "period": 6,
                "direction": 119.57,
                "compassDirection": "WNW"
            },
            "primary": {
                "height": 0.7,
                "period": 6,
                "direction": 115.7,
                "compassDirection": "WNW"
            }
        }
    },
    "wind": {
        "speed": 22,
        "direction": 117,
        "compassDirection": "WNW",
        "chill": 34,
        "gusts": 19,
        "unit": "kph"
    },
    "condition": {
        "pressure": 1002,
        "temperature": 37,
        "weather": 1,
        "unitPressure": "mb",
        "unit": "c"
    },
    "charts": {
       "swell": "https://hist-3.msw.ms/wave/750/74-1500033600-28.gif",
        "period": "https://hist-3.msw.ms/wave/750/74-1500033600-29.gif",
        "wind": "https://hist-3.msw.ms/gfs/750/74-1500033600-4.gif",
        "pressure": "https://hist-3.msw.ms/gfs/750/74-1500033600-3.gif"
    }
}*/