package eu.heiconnect.android.webservice.grades;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import eu.heiconnect.android.webservice.schedule.Update;

public class GradesResult {

    private List<Grade> grades;
    @JsonProperty("last_update")
    private Update lastUpdate;

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Update getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Update lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
