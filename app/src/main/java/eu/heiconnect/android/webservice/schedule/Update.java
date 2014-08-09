package eu.heiconnect.android.webservice.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Update implements Serializable {

    public static final String STATE_OK = "ok";
    public static final String STATE_FAILED = "failed";
    public static final String STATE_DISABLED = "disabled";
    public static final String STATE_SCHEDULED = "scheduled";
    public static final String STATE_UPDATING = "updating";

    private String state;
    @JsonProperty("updated_at")
    private Date updatedAt;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
