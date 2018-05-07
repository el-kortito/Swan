package itai.swan.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Iterator;

import itai.swan.Util;

public class DailyForecast implements Iterable<ForecastEntry>, Parcelable {
    private Forecast forecast;
    private int dayIdx;
    ForecastEntry summery;

    DailyForecast(int dayIdx, Forecast forecast) {
        this.forecast = forecast;
        this.dayIdx = dayIdx;
    }

    public ForecastEntry summery() {
        if(summery == null) {
            synchronized (this) {
                if(summery == null) {
                    int i = 0;
                    int hour;
                    Calendar cal = Calendar.getInstance();
                    ForecastEntry shallowCopy;

                    do {
                        shallowCopy = get(i++);
                        cal.setTimeInMillis((long) shallowCopy.getLocalTimestamp() * 1000L);
                        hour = Integer.valueOf(DateFormat.format("HH", cal).toString());
                    }
                    while (hour < 5 || hour > 20);

                    ForecastEntry sum = Util.deepCopy(shallowCopy, ForecastEntry.class);
                    for (ForecastEntry current : this) {
                        cal.setTimeInMillis((long) current.getLocalTimestamp() * 1000L);
                        hour = Integer.valueOf(DateFormat.format("HH", cal).toString());
                        if (hour >= 5 && hour <= 20) {
                                                                    /*Log.d("ZZZ", "sum.getSwell().getMaxBreakingHeight() :: " + sum.getSwell().getMaxBreakingHeight() +
                                                                                    "current.getSwell().getMaxBreakingHeight() :: " + current.getSwell().getMaxBreakingHeight() +
                                                                                    "sum.getSolidRating() :: " + sum.getSolidRating() +
                                                                                    "current.getSolidRating() :: " + current.getSolidRating());*/
                            if (sum.getSwell().getMinBreakingHeight() > current.getSwell().getMinBreakingHeight()) {
                                sum.getSwell().setMinBreakingHeight(current.getSwell().getMinBreakingHeight());
                            }
                            if (sum.getSwell().getMaxBreakingHeight() < current.getSwell().getMaxBreakingHeight()) {
                                sum.getSwell().setMaxBreakingHeight(current.getSwell().getMaxBreakingHeight());
                            }
                            if (sum.getSolidRating() < current.getSolidRating()) {
                                sum.setSolidRating(current.getSolidRating());
                                sum.setFadedRating(current.getFadedRating());
                            } else if (sum.getSolidRating() == current.getSolidRating() && sum.getFadedRating() < current.getFadedRating()) {
                                sum.setFadedRating(current.getFadedRating());
                            }
                        }
                    }
                    summery = sum;
                }
            }
        }

        return summery;
    }

    public ForecastEntry get(int hourIdx) {
        return forecast.completeForecast.get(dayIdx * 8 + hourIdx);
    }

    public Iterator<ForecastEntry> iterator() {
        return new ForcastEntryIterator();
    }

    class ForcastEntryIterator implements Iterator<ForecastEntry> {
        int hourIdx = 0;

        @Override
        public boolean hasNext() {
            return hourIdx < 8;
        }

        @Override
        public ForecastEntry next() {
            return forecast.completeForecast.get(dayIdx * 8 + hourIdx++);
        }
    }

    /**********************************************************************************************/

    protected DailyForecast(Parcel in) {
        forecast = (Forecast) in.readValue(Forecast.class.getClassLoader());
        dayIdx = in.readInt();
        summery = (ForecastEntry) in.readValue(ForecastEntry.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(forecast);
        dest.writeInt(dayIdx);
        dest.writeValue(summery);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DailyForecast> CREATOR = new Parcelable.Creator<DailyForecast>() {
        @Override
        public DailyForecast createFromParcel(Parcel in) {
            return new DailyForecast(in);
        }

        @Override
        public DailyForecast[] newArray(int size) {
            return new DailyForecast[size];
        }
    };
}