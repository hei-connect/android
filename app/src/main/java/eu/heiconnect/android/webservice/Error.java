package eu.heiconnect.android.webservice;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
public class Error {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}