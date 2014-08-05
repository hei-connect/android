package eu.heiconnect.android.webservice.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public class User {

    @JsonProperty("ecampus_id")
    private String ecampusId;
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

    @JsonIgnore
    public String getApiToken() {
        return apiToken;
    }

    @JsonProperty("api_token")
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
