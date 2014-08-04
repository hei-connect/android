package eu.heiconnect.android.utils;

import eu.heiconnect.android.BuildConfig;

public class Configuration {

    // ----------------------------------
    // SINGLETON
    // ----------------------------------
    private static Configuration instance = new Configuration();
    public static Configuration getInstance() {
        return instance;
    }

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private String apiBaseUrl;
    private int applicationMinimumVersion;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    private Configuration() {
        apiBaseUrl = BuildConfig.API_URL_BASE;
        applicationMinimumVersion = 0;
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public int getApplicationMinimumVersion() {
        return applicationMinimumVersion;
    }

    public void setApplicationMinimumVersion(int applicationMinimumVersion) {
        this.applicationMinimumVersion = applicationMinimumVersion;
    }
}
