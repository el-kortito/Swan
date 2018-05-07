
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
    "height",
    "period",
    "direction",
    "compassDirection"
})
public class Combined implements Parcelable
{

    @JsonProperty("height")
    private Double height;
    @JsonProperty("period")
    private Integer period;
    @JsonProperty("direction")
    private Double direction;
    @JsonProperty("compassDirection")
    private String compassDirection;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Combined> CREATOR = new Creator<Combined>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Combined createFromParcel(Parcel in) {
            Combined instance = new Combined();
            instance.height = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.period = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.direction = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.compassDirection = ((String) in.readValue((String.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Combined[] newArray(int size) {
            return (new Combined[size]);
        }

    }
    ;

    @JsonProperty("height")
    public Double getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(Double height) {
        this.height = height;
    }

    @JsonProperty("period")
    public Integer getPeriod() {
        return period;
    }

    @JsonProperty("period")
    public void setPeriod(Integer period) {
        this.period = period;
    }

    @JsonProperty("direction")
    public Double getDirection() {
        return direction;
    }

    @JsonProperty("direction")
    public void setDirection(Double direction) {
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
        return new HashCodeBuilder().append(height).append(period).append(direction).append(compassDirection).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Combined) == false) {
            return false;
        }
        Combined rhs = ((Combined) other);
        return new EqualsBuilder().append(height, rhs.height).append(period, rhs.period).append(direction, rhs.direction).append(compassDirection, rhs.compassDirection).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(height);
        dest.writeValue(period);
        dest.writeValue(direction);
        dest.writeValue(compassDirection);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
