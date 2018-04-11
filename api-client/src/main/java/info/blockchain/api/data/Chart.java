package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class Chart {

    @JsonProperty("status")
    private String status;

    @JsonProperty("name")
    private String name;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("period")
    private String period;

    @JsonProperty("description")
    private String description;

    @JsonProperty("values")
    private ArrayList<Point> values;

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Point> getValues() {
        return values;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValues(ArrayList<Point> values) {
        this.values = values;
    }

    @JsonIgnore
    public static Chart fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Chart.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
