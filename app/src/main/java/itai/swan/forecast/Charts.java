
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
    "swell",
    "period",
    "wind",
    "pressure"
})
public class Charts implements Parcelable
{

    @JsonProperty("swell")
    private String swell;
    @JsonProperty("period")
    private String period;
    @JsonProperty("wind")
    private String wind;
    @JsonProperty("pressure")
    private String pressure;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Charts> CREATOR = new Creator<Charts>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Charts createFromParcel(Parcel in) {
            Charts instance = new Charts();
            instance.swell = ((String) in.readValue((String.class.getClassLoader())));
            instance.period = ((String) in.readValue((String.class.getClassLoader())));
            instance.wind = ((String) in.readValue((String.class.getClassLoader())));
            instance.pressure = ((String) in.readValue((String.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Charts[] newArray(int size) {
            return (new Charts[size]);
        }

    }
    ;

    @JsonProperty("swell")
    public String getSwell() {
        return swell;
    }

    @JsonProperty("swell")
    public void setSwell(String swell) {
        this.swell = swell;
    }

    @JsonProperty("period")
    public String getPeriod() {
        return period;
    }

    @JsonProperty("period")
    public void setPeriod(String period) {
        this.period = period;
    }

    @JsonProperty("wind")
    public String getWind() {
        return wind;
    }

    @JsonProperty("wind")
    public void setWind(String wind) {
        this.wind = wind;
    }

    @JsonProperty("pressure")
    public String getPressure() {
        return pressure;
    }

    @JsonProperty("pressure")
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(swell).append(period).append(wind).append(pressure).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Charts) == false) {
            return false;
        }
        Charts rhs = ((Charts) other);
        return new EqualsBuilder().append(swell, rhs.swell).append(period, rhs.period).append(wind, rhs.wind).append(pressure, rhs.pressure).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(swell);
        dest.writeValue(period);
        dest.writeValue(wind);
        dest.writeValue(pressure);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
