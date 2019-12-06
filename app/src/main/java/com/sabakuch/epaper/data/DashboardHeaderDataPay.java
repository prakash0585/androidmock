package com.sabakuch.epaper.data;

public class DashboardHeaderDataPay {

    private String examName, attempts,startdate,enddate,totalattempt;


    public DashboardHeaderDataPay(String examName, String attempts, String startdate, String enddate, String totalattempt) {
        super();
        this.examName = examName;
        this.attempts = attempts;
        this.startdate = startdate;
        this.enddate = enddate;
        this.totalattempt = totalattempt;
    }
    public String getEnddate() {
        return enddate;
    }
    public String getExamName() {
        return examName;
    }

    public String getAttempts() {
        return attempts;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getTotalattempt() {
        return totalattempt;
    }
}