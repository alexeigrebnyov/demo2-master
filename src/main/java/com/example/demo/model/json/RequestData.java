package com.example.demo.model.json;

public class RequestData {
    public String code;
    public String done;
    public String gprm;
    public String server;
    public String status;

    public RequestData() {
    }

    public RequestData(String code, String done, String gprm, String server, String status) {
        this.code = code;
        this.done = done;
        this.gprm = gprm;
        this.server = server;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getGprm() {
        return gprm;
    }

    public void setGprm(String gprm) {
        this.gprm = gprm;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "code='" + code + '\'' +
                ", done='" + done + '\'' +
                ", gprm='" + gprm + '\'' +
                ", server='" + server + '\'' +
                ", status=" + status +
                '}';
    }
}
