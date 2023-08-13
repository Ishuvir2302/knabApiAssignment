package payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardDetails {

    @JsonProperty("id")
    private String id;

    public String getID() {
        return id;
    }

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }
}
