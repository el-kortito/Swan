package itai.swan.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import itai.swan.forecast.ForecastEntry;

public class NotificationRule implements Parcelable {
    public Integer id;
    public String name;
    public String description;
    public int spot = 3663;
    public int minStars = 0;
    public double minHeight = 0;

    public NotificationRule(){}

    boolean isSatisfiedBy(ForecastEntry entry) {
        return spot == entry.getSurfSpot() &&
                minStars <= entry.getSolidRating() + entry.getFadedRating() &&
                minHeight <= entry.getSwell().getMaxBreakingHeight();
    }

    protected NotificationRule(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        spot = in.readInt();
        minStars = in.readInt();
        minHeight = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(spot);
        dest.writeInt(minStars);
        dest.writeDouble(minHeight);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NotificationRule> CREATOR = new Parcelable.Creator<NotificationRule>() {
        @Override
        public NotificationRule createFromParcel(Parcel in) {
            return new NotificationRule(in);
        }

        @Override
        public NotificationRule[] newArray(int size) {
            return new NotificationRule[size];
        }
    };
}