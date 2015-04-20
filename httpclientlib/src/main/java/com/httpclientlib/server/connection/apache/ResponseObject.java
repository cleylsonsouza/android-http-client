package com.httpclientlib.server.connection.apache;

public class ResponseObject {

    private Integer codeStatus;
    private String body;
    private String reason;

    public ResponseObject() {
        super();
    }

    public ResponseObject(Integer codeStatus, String body, String reason) {
        this.codeStatus = codeStatus;
        this.body = body;
        this.reason = reason;
    }

    public Integer getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(Integer codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "[codeStatus=" + codeStatus + ", body=" + body + "]";
    }
}
