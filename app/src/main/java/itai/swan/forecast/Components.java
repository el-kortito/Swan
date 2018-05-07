
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
    "combined",
    "primary"
})
public class Components implements Parcelable
{

    @JsonProperty("combined")
    private Combined combined;
    @JsonProperty("primary")
    private Primary primary;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Components> CREATOR = new Creator<Components>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Components createFromParcel(Parcel in) {
            Components instance = new Components();
            instance.combined = ((Combined) in.readValue((Combined.class.getClassLoader())));
            instance.primary = ((Primary) in.readValue((Primary.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Components[] newArray(int size) {
            return (new Components[size]);
        }

    }
    ;

    @JsonProperty("combined")
    public Combined getCombined() {
        return combined;
    }

    @JsonProperty("combined")
    public void setCombined(Combined combined) {
        this.combined = combined;
    }

    @JsonProperty("primary")
    public Primary getPrimary() {
        return primary;
    }

    @JsonProperty("primary")
    public void setPrimary(Primary primary) {
        this.primary = primary;
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
        return new HashCodeBuilder().append(combined).append(primary).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Components) == false) {
            return false;
        }
        Components rhs = ((Components) other);
        return new EqualsBuilder().append(combined, rhs.combined).append(primary, rhs.primary).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(combined);
        dest.writeValue(primary);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
