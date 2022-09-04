package com.example.demo.model;

import java.util.Objects;

public class Analysis {
    private String emc;
    private String fio;
    private String kontengent;
    private String motconsu_resp_id;
    private String resultHiv;
    private String resultHbsAg;
    private String resultatHCV;
    private String resultSyphIfa;
    private String resultMRP;
    private String resultRubG;
    private String resultRubM;
    private String resultHSP60;
    private String resultClamA;
    private String resultClamG;
    private String resultDGA;
    private String main_org_id;
    private String label;
    private String patdirect_id;
    private String date_bio;
    private String hiv;
    private String hbsAg;
    private String atHCV;
    private String syphIFA;
    private String syphMRP;
    private String RubG;
    private String RubM;
    private String hsp60;
    private String ClamA;
    private String ClamG;
    private String sbg;
    private String dga;
    private String code;
    private String sex;
    private String adres;
    private String gCheck;
    private String immCheck;

    public Analysis() {
    }

    public Analysis(String emc, String fio, String kontengent, String motconsu_resp_id, String resultHiv,
                    String resultHbsAg, String resultatHCV, String resultSyphIfa, String resultMRP, String resultRubG,
                    String resultRubM,  String resultHSP60, String resultClamA, String resultClamG, String main_org_id,
                    String label, String patdirect_id, String date_bio, String hiv, String hbsAg, String atHCV,
                    String syphIFA,String syphMRP, String RubG, String RubM, String hsp60, String sbg,
                    String dga, String resultDGA,String code, String sex,
                    String ClamA, String ClamG, String adres) {
        this.emc = emc;
        this.fio = fio;
        this.kontengent = kontengent;
        this.motconsu_resp_id = motconsu_resp_id;
        this.resultHiv = resultHiv;
        this.resultHbsAg = resultHbsAg;
        this.resultatHCV = resultatHCV;
        this.resultSyphIfa = resultSyphIfa;
        this.resultMRP = resultMRP;
        this.resultRubG = resultRubG;
        this.resultRubM = resultRubM;
        this.resultHSP60 = resultHSP60;
        this.resultClamA = resultClamA;
        this.resultClamG = resultClamG;
        this.resultDGA = resultDGA;
        this.main_org_id = main_org_id;
        this.label = label;
        this.patdirect_id = patdirect_id;
        this.date_bio = date_bio;
        this.hiv = hiv;
        this.hbsAg = hbsAg;
        this.atHCV = atHCV;
        this.syphIFA = syphIFA;
        this.syphMRP = syphMRP;
        this.RubG = RubG;
        this.RubM = RubM;
        this.hsp60 = hsp60;
        this.sbg = sbg;
        this.ClamA = ClamA;
        this.ClamG = ClamG;
        this.dga = dga;
        this.code = code;
        this.sex = sex;
        this.adres = adres;
    }

    public String getResultDGA() {
        return resultDGA;
    }

    public void setResultDGA(String resultDGA) {
        this.resultDGA = resultDGA;
    }

    public String getDga() {
        return dga;
    }

    public void setDga(String dga) {
        this.dga = dga;
    }

    public String getSbg() {
        return sbg;
    }

    public void setSbg(String sbg) {
        this.sbg = sbg;
    }

    public String getKontengent() {
        return kontengent;
    }

    public String getMotconsu_resp_id() {
        return motconsu_resp_id;
    }


    public String getMain_org_id() {
        return main_org_id;
    }

    public String getLabel() {
        return label;
    }

    public String getPatdirect_id() {
        return patdirect_id;
    }

    public String getDate_bio() {
        return date_bio;
    }

    public String getEmc() {
        return emc;
    }

    public String getFio() {
        return fio;
    }


    public String getCode() {
        return code;
    }

    public String getHiv() {
        return hiv;
    }

    public void setHiv(String hiv) {
        this.hiv = hiv;
    }

    public String getHbsAg() {
        return hbsAg;
    }

    public void setHbsAg(String hbsAg) {
        this.hbsAg = hbsAg;
    }

    public String getAtHCV() {
        return atHCV;
    }

    public void setAtHCV(String atHCV) {
        this.atHCV = atHCV;
    }

    public String getSyphIFA() {
        return syphIFA;
    }

    public void setSyphIFA(String syphIFA) {
        this.syphIFA = syphIFA;
    }

    public String getSyphMRP() {
        return syphMRP;
    }

    public void setSyphMRP(String syphMRP) {
        this.syphMRP = syphMRP;
    }

    public void setEmc(String emc) {
        this.emc = emc;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setKontengent(String kontengent) {
        this.kontengent = kontengent;
    }

    public void setMotconsu_resp_id(String motconsu_resp_id) {
        this.motconsu_resp_id = motconsu_resp_id;
    }


    public void setMain_org_id(String main_org_id) {
        this.main_org_id = main_org_id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPatdirect_id(String patdirect_id) {
        this.patdirect_id = patdirect_id;
    }

    public void setDate_bio(String date_bio) {
        this.date_bio = date_bio;
    }

    public String getResultHiv() {
        return resultHiv;
    }

    public void setResultHiv(String resultHiv) {
        this.resultHiv = resultHiv;
    }

    public String getResultHbsAg() {
        return resultHbsAg;
    }

    public void setResultHbsAg(String resultHbsAg) {
        this.resultHbsAg = resultHbsAg;
    }

    public String getResultatHCV() {
        return resultatHCV;
    }

    public void setResultatHCV(String resultatHCV) {
        this.resultatHCV = resultatHCV;
    }

    public String getResultSyphIfa() {
        return resultSyphIfa;
    }

    public void setResultSyphIfa(String resultSyphIfa) {
        this.resultSyphIfa = resultSyphIfa;
    }

    public String getResultMRP() {
        return resultMRP;
    }

    public void setResultMRP(String resultMRP) {
        this.resultMRP = resultMRP;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getResultRubG() {
        return resultRubG;
    }

    public void setResultRubG(String resultRubG) {
        this.resultRubG = resultRubG;
    }

    public String getResultRubM() {
        return resultRubM;
    }

    public void setResultRubM(String resultRubM) {
        this.resultRubM = resultRubM;
    }

    public String getResultHSP60() {
        return resultHSP60;
    }

    public void setResultHSP60(String resultHSP60) {
        this.resultHSP60 = resultHSP60;
    }

    public String getResultClamA() {
        return resultClamA;
    }

    public void setResultClamA(String resultClamA) {
        this.resultClamA = resultClamA;
    }

    public String getResultClamG() {
        return resultClamG;
    }

    public void setResultClamG(String resultClamG) {
        this.resultClamG = resultClamG;
    }

    public String getRubG() {
        return RubG;
    }

    public void setRubG(String rubG) {
        RubG = rubG;
    }

    public String getRubM() {
        return RubM;
    }

    public void setRubM(String rubM) {
        RubM = rubM;
    }

    public String getHSP60() {
        return hsp60;
    }

    public void setHSP60(String hsp60) {
        this.hsp60 = hsp60;
    }

    public String getClamA() {
        return ClamA;
    }

    public void setClamA(String clamA) {
        ClamA = clamA;
    }

    public String getClamG() {
        return ClamG;
    }

    public void setClamG(String clamG) {
        ClamG = clamG;
    }


    @Override
    public String toString() {
        return "Analysis{" +
                "emc='" + emc + '\'' +
                ", fio='" + fio + '\'' +
                ", kontengent='" + kontengent + '\'' +
                ", motconsu_resp_id='" + motconsu_resp_id + '\'' +
                ", resultHiv='" + resultHiv + '\'' +
                ", resultHbsAg='" + resultHbsAg + '\'' +
                ", resultatHCV='" + resultatHCV + '\'' +
                ", resultSyphIfa='" + resultSyphIfa + '\'' +
                ", resultMRP='" + resultMRP + '\'' +
                ", resultRubG='" + resultRubG + '\'' +
                ", resultRubM='" + resultRubM + '\'' +
                ", resultHSP60='" + resultHSP60 + '\'' +
                ", resultClamA='" + resultClamA + '\'' +
                ", resultClamG='" + resultClamG + '\'' +
                ", resultDGA='" + resultDGA + '\'' +
                ", main_org_id='" + main_org_id + '\'' +
                ", label='" + label + '\'' +
                ", patdirect_id='" + patdirect_id + '\'' +
                ", date_bio='" + date_bio + '\'' +
                ", hiv='" + hiv + '\'' +
                ", hbsAg='" + hbsAg + '\'' +
                ", atHCV='" + atHCV + '\'' +
                ", syphIFA='" + syphIFA + '\'' +
                ", syphMRP='" + syphMRP + '\'' +
                ", RubG='" + RubG + '\'' +
                ", RubM='" + RubM + '\'' +
                ", HSP60='" + hsp60 + '\'' +
                ", ClamA='" + ClamA + '\'' +
                ", ClamG='" + ClamG + '\'' +
                ", SBG='" + sbg + '\'' +
                ", DGA='" + dga + '\'' +
                ", code='" + code + '\'' +
                ", sex='" + sex + '\'' +
                ", adres='" + adres + '\'' +
                ", gCheck='" + gCheck + '\'' +
                ", gormonuCheck='" + gormonuChek() + '\'' +
                '}';
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Analysis analysis = (Analysis) o;
//        return Objects.equals(getEmc(), analysis.getEmc()) &&
//                Objects.equals(getFio(), analysis.getFio()) &&
//                Objects.equals(getKontengent(), analysis.getKontengent()) &&
//                Objects.equals(getMotconsu_resp_id(), analysis.getMotconsu_resp_id()) &&
//                Objects.equals(getResultHiv(), analysis.getResultHiv()) &&
//                Objects.equals(getResultHbsAg(), analysis.getResultHbsAg()) &&
//                Objects.equals(getResultatHCV(), analysis.getResultatHCV()) &&
//                Objects.equals(getResultSyphIfa(), analysis.getResultSyphIfa()) &&
//                Objects.equals(getResultMRP(), analysis.getResultMRP()) &&
//                Objects.equals(getMain_org_id(), analysis.getMain_org_id()) &&
//                Objects.equals(getLabel(), analysis.getLabel()) &&
//                Objects.equals(getPatdirect_id(), analysis.getPatdirect_id()) &&
//                Objects.equals(getDate_bio(), analysis.getDate_bio()) &&
//                Objects.equals(getHiv(), analysis.getHiv()) &&
//                Objects.equals(getHbsAg(), analysis.getHbsAg()) &&
//                Objects.equals(getAtHCV(), analysis.getAtHCV()) &&
//                Objects.equals(getSyphIFA(), analysis.getSyphIFA()) &&
//                Objects.equals(getSyphMRP(), analysis.getSyphMRP()) &&
//                Objects.equals(getCode(), analysis.getCode());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getEmc(), getFio(), getKontengent(), getMotconsu_resp_id(), getResultHiv(), getResultHbsAg(), getResultatHCV(), getResultSyphIfa(), getResultMRP(), getMain_org_id(), getLabel(), getPatdirect_id(), getDate_bio(), getHiv(), getHbsAg(), getAtHCV(), getSyphIFA(), getSyphMRP(), getCode());
//    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Analysis analysis = (Analysis) o;
//        return Objects.equals(getEmc(), analysis.getEmc()) &&
//                Objects.equals(getCode(), analysis.getCode());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getEmc(), getCode());
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Analysis analysis = (Analysis) o;
        return Objects.equals(getEmc(), analysis.getEmc()) &&
                Objects.equals(getHiv(), analysis.getHiv()) &&
                Objects.equals(getHbsAg(), analysis.getHbsAg()) &&
                Objects.equals(getAtHCV(), analysis.getAtHCV()) &&
                Objects.equals(getSyphIFA(), analysis.getSyphIFA()) &&
                Objects.equals(getSyphMRP(), analysis.getSyphMRP()) &&
                Objects.equals(getCode(), analysis.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmc(), getHiv(), getHbsAg(), getAtHCV(), getSyphIFA(), getSyphMRP(), getCode());
    }

    public boolean chekHiv() {
        if (hiv.equals("1")){
        try {
            return !resultHiv.equals("");
        } catch (Exception ex) {return  false;}}
       return true;
    }
    public boolean chekHbs() {
        if (hbsAg.equals("1")){
        try {
            return !resultHbsAg.equals("");
        } catch (Exception ex) {return  false;}}
       return true;
    }
    public boolean chekatHCV() {
        if (atHCV.equals("1")){
        try {
            return !resultatHCV.equals("");
        } catch (Exception ex) {return false;}}
       return true;
    }
    public boolean chekSyphIfa() {
        if (syphIFA.equals("1")){
        try {
            return !resultSyphIfa.equals("");
        } catch (Exception ex) {return  false;}}
        return true;
    }
    public boolean chek() {
        if (syphMRP.equals("1")) {
            try {
                return !resultMRP.equals("");
            } catch (Exception ex) {return false;}
        }
        return true;

    }

    public boolean chekRubG() {
        if (RubG.equals("1")){
            try {
                return !resultRubG.equals("");
            } catch (Exception ex) {return  false;}}
        return true;
    }
    public boolean chekRubM() {
        if (RubM.equals("1")){
            try {
                return !resultRubM.equals("");
            } catch (Exception ex) {return  false;}}
        return true;
    }
    public boolean chekClamG() {
        if (ClamG.equals("1")){
            try {
                return !resultClamG.equals("");
            } catch (Exception ex) {return  false;}}
        return true;
    }
    public boolean chekClamA() {
        if (ClamA.equals("1")){
            try {
                return !resultClamA.equals("");
            } catch (Exception ex) {return  false;}}
        return true;
    }
    public boolean chekHSP() {
        if (hsp60.equals("1")){
            try {
                return !resultHSP60.equals("");
            } catch (Exception ex) {return  false;}}
        return true;
    }

    public boolean chekAMG() {
        try {
            if (hiv.equals("1")) {

                return !resultHiv.equals("");
            }
        }catch (Exception ex) {return  false;}
        return true;
    }

    public boolean chek17() {
        try {
        if (hbsAg.equals("1")) {

            return !resultHbsAg.equals("");
        }
            } catch (Exception ex) {return  false;}
        return true;
    }

    public boolean chekCA() {
        try {
        if (atHCV.equals("1")) {

            return !resultatHCV.equals("");
        }
            } catch (Exception ex) {return false;}
        return true;
    }

    public boolean chekE2() {
        try {
        if (hiv.equals("1")){

                return !resultHiv.equals("");
        }
            } catch (Exception ex) {return  false;}
        return true;
    }
    public boolean chekSBG() {
        try {
            if (sbg.equals("1")){

                return !resultHSP60.equals("");
            }
        } catch (Exception ex) {return  false;}
        return true;
    }

    public boolean chekDGA() {
        try {
            if (dga.equals("1")){

                return !resultDGA.equals("");
            }
        } catch (Exception ex) {return  false;}
        return true;
    }


    public String totalChek() {
        if(chek() && chekatHCV() && chekHbs() && chekHiv() && chekSyphIfa()) {return " ";}
        return "!!!!!";
    }

    public String torchChek() {
        if(chekRubG() && chekRubM() && chekClamG() && chekClamA() && chekHSP()) {return " ";}
        return "!!!!!";
    }

    public String gormonuChek() {
        if(chekAMG() && chek17() && chekCA() ) {return" ";}
        return "!!!!!";
    }

    public String getImmChek() {
        if(chekHiv() && chekHbs() && chekatHCV() && chekSyphIfa()&& chekRubM() && chekRubG() && chekClamG() && chekClamA() && chekE2() && chekSBG()
        && chekDGA()) {return" ";}
        return "!!!!!";
    }

    public String getgCheck() {

        return gormonuChek();
    }

    public void setGCheck(String gCheck) {
        this.gCheck = gCheck;
    }
}
