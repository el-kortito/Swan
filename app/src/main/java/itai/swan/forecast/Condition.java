
package itai.swan.forecast;

import java.util.HashMap;
import java.util.Map;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "pressure",
    "temperature",
    "weather",
    "unitPressure",
    "unit"
})
public class Condition implements Parcelable
{

    @JsonProperty("pressure")
    private Integer pressure;
    @JsonProperty("temperature")
    private Integer temperature;
    @JsonProperty("weather")
    private Integer weather;
    @JsonProperty("unitPressure")
    private String unitPressure;
    @JsonProperty("unit")
    private String unit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Condition> CREATOR = new Creator<Condition>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Condition createFromParcel(Parcel in) {
            Condition instance = new Condition();
            instance.pressure = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.temperature = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.weather = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.unitPressure = ((String) in.readValue((String.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Condition[] newArray(int size) {
            return (new Condition[size]);
        }

    }
    ;

    @JsonProperty("pressure")
    public Integer getPressure() {
        return pressure;
    }

    @JsonProperty("pressure")
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    @JsonProperty("temperature")
    public Integer getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("weather")
    public Integer getWeather() {
        return weather;
    }

    @JsonProperty("weather")
    public void setWeather(Integer weather) {
        this.weather = weather;
    }

    @JsonProperty("unitPressure")
    public String getUnitPressure() {
        return unitPressure;
    }

    @JsonProperty("unitPressure")
    public void setUnitPressure(String unitPressure) {
        this.unitPressure = unitPressure;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
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
        return new HashCodeBuilder().append(pressure).append(temperature).append(weather).append(unitPressure).append(unit).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Condition) == false) {
            return false;
        }
        Condition rhs = ((Condition) other);
        return new EqualsBuilder().append(pressure, rhs.pressure).append(temperature, rhs.temperature).append(weather, rhs.weather).append(unitPressure, rhs.unitPressure).append(unit, rhs.unit).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pressure);
        dest.writeValue(temperature);
        dest.writeValue(weather);
        dest.writeValue(unitPressure);
        dest.writeValue(unit);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
