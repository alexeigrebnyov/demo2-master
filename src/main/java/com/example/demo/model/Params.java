package com.example.demo.model;

public class Params {
    String codeInt;
    String proofCode;
    String redir;
    String done;
    String grpprm;

    public Params() {
    }

    public Params(String codeInt, String proofCode, String redir, String done, String grpprm) {
        this.codeInt = codeInt;
        this.proofCode = proofCode;
        this.redir = redir;
        this.done = done;
        this.grpprm = grpprm;
    }

    public String getCodeInt() {
        return codeInt;
    }

    public void setCodeInt(String codeInt) {
        this.codeInt = codeInt;
    }

    public String getProofCode() {
        return proofCode;
    }

    public void setProofCode(String proofCode) {
        this.proofCode = proofCode;
    }

    public String getRedir() {
        return redir;
    }

    public void setRedir(String redir) {
        this.redir = redir;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getGrpprm() {
        return grpprm;
    }

    public void setGrpprm(String grpprm) {
        this.grpprm = grpprm;
    }

    @Override
    public String toString() {
        return "Params{" +
                "codeInt='" + codeInt + '\'' +
                ", proofCode='" + proofCode + '\'' +
                ", redir='" + redir + '\'' +
                ", done='" + done + '\'' +
                ", grpprm=" + grpprm +
                '}';
    }
}
