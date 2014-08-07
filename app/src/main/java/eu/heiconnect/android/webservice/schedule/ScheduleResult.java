package eu.heiconnect.android.webservice.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScheduleResult {

    private List<Course> courses;
    @JsonProperty("last_update")
    private Update lastUpdate;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Update getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Update lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
