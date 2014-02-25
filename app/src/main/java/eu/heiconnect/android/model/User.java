package eu.heiconnect.android.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("user")
public class User {

    @JsonProperty("ecampus_id")
    private String ecampusId;
    @JsonProperty("api_token")
    private String apiToken;

    private String password;

    public User() {
        super();
    }

    public User(String ecampusId, String password) {
        this.ecampusId = ecampusId;
        this.password = password;
    }

    public String getEcampusId() {
        return ecampusId;
    }

    public void setEcampusId(String ecampusId) {
        this.ecampusId = ecampusId;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
