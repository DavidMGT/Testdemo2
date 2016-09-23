package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 消息；类型之通知
 * Created by maguitao on 2016/9/7.
 */
public class InformBean implements Serializable {
     private int id;
     private String userid;
     private String fid;
     private String agree;
     private String rtime;
     private String sendtime;
     private String type;
     private String ftime;
     private String fcontent;
     private String ftitle;
    private String notarize;
    private String disagree;
    private String amount;
    private String total;

    @Override
    public String toString() {
        return "InformBean{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", fid='" + fid + '\'' +
                ", agree='" + agree + '\'' +
                ", rtime='" + rtime + '\'' +
                ", sendtime='" + sendtime + '\'' +
                ", type='" + type + '\'' +
                ", ftime='" + ftime + '\'' +
                ", fcontent='" + fcontent + '\'' +
                ", ftitle='" + ftitle + '\'' +
                ", notarize='" + notarize + '\'' +
                ", disagree='" + disagree + '\'' +
                ", amount='" + amount + '\'' +
                ", total='" + total + '\'' +
                ", state='" + state + '\'' +
                ", tapeurl='" + tapeurl + '\'' +
                '}';
    }

    private String state;
    private String tapeurl;
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent;
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    public String getNotarize() {
        return notarize;
    }

    public void setNotarize(String notarize) {
        this.notarize = notarize;
    }

    public String getDisagree() {
        return disagree;
    }

    public void setDisagree(String disagree) {
        this.disagree = disagree;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTapeurl() {
        return tapeurl;
    }

    public void setTapeurl(String tapeurl) {
        this.tapeurl = tapeurl;
    }



}
