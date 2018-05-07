
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
    "speed",
    "direction",
    "compassDirection",
    "chill",
    "gusts",
    "unit"
})
public class Wind implements Parcelable
{

    @JsonProperty("speed")
    private Integer speed;
    @JsonProperty("direction")
    private Integer direction;
    @JsonProperty("compassDirection")
    private String compassDirection;
    @JsonProperty("chill")
    private Integer chill;
    @JsonProperty("gusts")
    private Integer gusts;
    @JsonProperty("unit")
    private String unit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Wind> CREATOR = new Creator<Wind>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Wind createFromParcel(Parcel in) {
            Wind instance = new Wind();
            instance.speed = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.direction = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.compassDirection = ((String) in.readValue((String.class.getClassLoader())));
            instance.chill = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.gusts = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Wind[] newArray(int size) {
            return (new Wind[size]);
        }

    }
    ;

    @JsonProperty("speed")
    public Integer getSpeed() {
        return speed;
    }

    @JsonProperty("speed")
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @JsonProperty("direction")
    public Integer getDirection() {
        return direction;
    }

    @JsonProperty("direction")
    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @JsonProperty("compassDirection")
    public String getCompassDirection() {
        return compassDirection;
    }

    @JsonProperty("compassDirection")
    public void setCompassDirection(String compassDirection) {
        this.compassDirection = compassDirection;
    }

    @JsonProperty("chill")
    public Integer getChill() {
        return chill;
    }

    @JsonProperty("chill")
    public void setChill(Integer chill) {
        this.chill = chill;
    }

    @JsonProperty("gusts")
    public Integer getGusts() {
        return gusts;
    }

    @JsonProperty("gusts")
    public void setGusts(Integer gusts) {
        this.gusts = gusts;
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
        return new HashCodeBuilder().append(speed).append(direction).append(compassDirection).append(chill).append(gusts).append(unit).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Wind) == false) {
            return false;
        }
        Wind rhs = ((Wind) other);
        return new EqualsBuilder().append(speed, rhs.speed).append(direction, rhs.direction).append(compassDirection, rhs.compassDirection).append(chill, rhs.chill).append(gusts, rhs.gusts).append(unit, rhs.unit).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(speed);
        dest.writeValue(direction);
        dest.writeValue(compassDirection);
        dest.writeValue(chill);
        dest.writeValue(gusts);
        dest.writeValue(unit);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
