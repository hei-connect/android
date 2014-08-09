package eu.heiconnect.android.webservice.grades;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Grade implements Serializable {

    private int id;
    @JsonProperty("section_name")
    private String sectionName;
    @JsonProperty("exam_name")
    private String examName;
    private Date date;
    private boolean unknown;
    private double mark;
    private double average;
    @JsonProperty("average_count")
    private int averageCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isUnknown() {
        return unknown;
    }

    public void setUnknown(boolean unknown) {
        this.unknown = unknown;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getAverageCount() {
        return averageCount;
    }

    public void setAverageCount(int averageCount) {
        this.averageCount = averageCount;
    }
}
