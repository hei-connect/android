package eu.heiconnect.android.webservice.absences;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import eu.heiconnect.android.webservice.schedule.Update;

public class AbsencesResult {

    private List<Absence> absences;
    @JsonProperty("last_update")
    private Update lastUpdate;

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public Update getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Update lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
