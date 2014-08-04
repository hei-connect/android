package eu.heiconnect.android.webservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {

    private String url;
    @JsonProperty("android_minimum_version")
    private int androidMinimumVersion;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAndroidMinimumVersion() {
        return androidMinimumVersion;
    }

    public void setAndroidMinimumVersion(int androidMinimumVersion) {
        this.androidMinimumVersion = androidMinimumVersion;
    }
}
