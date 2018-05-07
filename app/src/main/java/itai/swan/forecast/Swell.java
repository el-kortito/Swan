
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
    "absMinBreakingHeight",
    "absMaxBreakingHeight",
    "unit",
    "minBreakingHeight",
    "maxBreakingHeight",
    "components"
})
public class Swell implements Parcelable
{

    @JsonProperty("absMinBreakingHeight")
    private Double absMinBreakingHeight;
    @JsonProperty("absMaxBreakingHeight")
    private Double absMaxBreakingHeight;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("minBreakingHeight")
    private Double minBreakingHeight;
    @JsonProperty("maxBreakingHeight")
    private Double maxBreakingHeight;
    @JsonProperty("components")
    private Components components;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Swell> CREATOR = new Creator<Swell>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Swell createFromParcel(Parcel in) {
            Swell instance = new Swell();
            instance.absMinBreakingHeight = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.absMaxBreakingHeight = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.unit = ((String) in.readValue((String.class.getClassLoader())));
            instance.minBreakingHeight = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.maxBreakingHeight = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.components = ((Components) in.readValue((Components.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Swell[] newArray(int size) {
            return (new Swell[size]);
        }

    }
    ;

    @JsonProperty("absMinBreakingHeight")
    public Double getAbsMinBreakingHeight() {
        return absMinBreakingHeight;
    }

    @JsonProperty("absMinBreakingHeight")
    public void setAbsMinBreakingHeight(Double absMinBreakingHeight) {
        this.absMinBreakingHeight = absMinBreakingHeight;
    }

    @JsonProperty("absMaxBreakingHeight")
    public Double getAbsMaxBreakingHeight() {
        return absMaxBreakingHeight;
    }

    @JsonProperty("absMaxBreakingHeight")
    public void setAbsMaxBreakingHeight(Double absMaxBreakingHeight) {
        this.absMaxBreakingHeight = absMaxBreakingHeight;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("minBreakingHeight")
    public Double getMinBreakingHeight() {
        return minBreakingHeight;
    }

    @JsonProperty("minBreakingHeight")
    public void setMinBreakingHeight(Double minBreakingHeight) {
        this.minBreakingHeight = minBreakingHeight;
    }

    @JsonProperty("maxBreakingHeight")
    public Double getMaxBreakingHeight() {
        return maxBreakingHeight;
    }

    @JsonProperty("maxBreakingHeight")
    public void setMaxBreakingHeight(Double maxBreakingHeight) {
        this.maxBreakingHeight = maxBreakingHeight;
    }

    @JsonProperty("components")
    public Components getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(Components components) {
        this.components = components;
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
        return new HashCodeBuilder().append(absMinBreakingHeight).append(absMaxBreakingHeight).append(unit).append(minBreakingHeight).append(maxBreakingHeight).append(components).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Swell) == false) {
            return false;
        }
        Swell rhs = ((Swell) other);
        return new EqualsBuilder().append(absMinBreakingHeight, rhs.absMinBreakingHeight).append(absMaxBreakingHeight, rhs.absMaxBreakingHeight).append(unit, rhs.unit).append(minBreakingHeight, rhs.minBreakingHeight).append(maxBreakingHeight, rhs.maxBreakingHeight).append(components, rhs.components).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(absMinBreakingHeight);
        dest.writeValue(absMaxBreakingHeight);
        dest.writeValue(unit);
        dest.writeValue(minBreakingHeight);
        dest.writeValue(maxBreakingHeight);
        dest.writeValue(components);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
