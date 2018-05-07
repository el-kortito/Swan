
package itai.swan.forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "localTimestamp",
        "issueTimestamp",
        "fadedRating",
        "solidRating",
        "swell",
        "wind",
        "condition",
        "charts"
})
public class ForecastEntry implements Parcelable {
    private int surfSpot;

    public int getSurfSpot() {
        return surfSpot;
    }

    public void setSurfSpot(int surfSpot) {
        this.surfSpot = surfSpot;
    }

    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonProperty("localTimestamp")
    private Integer localTimestamp;
    @JsonProperty("issueTimestamp")
    private Integer issueTimestamp;
    @JsonProperty("fadedRating")
    private Integer fadedRating;
    @JsonProperty("solidRating")
    private Integer solidRating;
    @JsonProperty("swell")
    private Swell swell;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("condition")
    private Condition condition;
    @JsonProperty("charts")
    private Charts charts;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("localTimestamp")
    public Integer getLocalTimestamp() {
        return localTimestamp;
    }

    @JsonProperty("localTimestamp")
    public void setLocalTimestamp(Integer localTimestamp) {
        this.localTimestamp = localTimestamp;
    }

    @JsonProperty("issueTimestamp")
    public Integer getIssueTimestamp() {
        return issueTimestamp;
    }

    @JsonProperty("issueTimestamp")
    public void setIssueTimestamp(Integer issueTimestamp) {
        this.issueTimestamp = issueTimestamp;
    }

    @JsonProperty("fadedRating")
    public Integer getFadedRating() {
        return fadedRating;
    }

    @JsonProperty("fadedRating")
    public void setFadedRating(Integer fadedRating) {
        this.fadedRating = fadedRating;
    }

    @JsonProperty("solidRating")
    public Integer getSolidRating() {
        return solidRating;
    }

    @JsonProperty("solidRating")
    public void setSolidRating(Integer solidRating) {
        this.solidRating = solidRating;
    }

    @JsonProperty("swell")
    public Swell getSwell() {
        return swell;
    }

    @JsonProperty("swell")
    public void setSwell(Swell swell) {
        this.swell = swell;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return wind;
    }

    @JsonProperty("wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonProperty("condition")
    public Condition getCondition() {
        return condition;
    }

    @JsonProperty("condition")
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @JsonProperty("charts")
    public Charts getCharts() {
        return charts;
    }

    @JsonProperty("charts")
    public void setCharts(Charts charts) {
        this.charts = charts;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**********************************************************************************************/

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(timestamp).append(localTimestamp).append(issueTimestamp).append(fadedRating).append(solidRating).append(swell).append(wind).append(condition).append(charts).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ForecastEntry) == false) {
            return false;
        }
        ForecastEntry rhs = ((ForecastEntry) other);
        return new EqualsBuilder().append(timestamp, rhs.timestamp).append(localTimestamp, rhs.localTimestamp).append(issueTimestamp, rhs.issueTimestamp).append(fadedRating, rhs.fadedRating).append(solidRating, rhs.solidRating).append(swell, rhs.swell).append(wind, rhs.wind).append(condition, rhs.condition).append(charts, rhs.charts).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(surfSpot);
        dest.writeValue(timestamp);
        dest.writeValue(localTimestamp);
        dest.writeValue(issueTimestamp);
        dest.writeValue(fadedRating);
        dest.writeValue(solidRating);
        dest.writeValue(swell);
        dest.writeValue(wind);
        dest.writeValue(condition);
        dest.writeValue(charts);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

    public final static Creator<ForecastEntry> CREATOR = new Creator<ForecastEntry>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ForecastEntry createFromParcel(Parcel in) {
            ForecastEntry instance = new ForecastEntry();
            instance.surfSpot = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.timestamp = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.localTimestamp = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.issueTimestamp = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.fadedRating = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.solidRating = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.swell = ((Swell) in.readValue((Swell.class.getClassLoader())));
            instance.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
            instance.condition = ((Condition) in.readValue((Condition.class.getClassLoader())));
            instance.charts = ((Charts) in.readValue((Charts.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public ForecastEntry[] newArray(int size) {
            return (new ForecastEntry[size]);
        }

    };
}
