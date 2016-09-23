package com.work.student.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 消息；类型之通知
 * Created by maguitao on 2016/9/7.
 */
@DatabaseTable(tableName = "tb_inform")
public class InformBean implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String userid;
    @DatabaseField
    private String fid;
    @DatabaseField
    private String agree;

    public long getRtime() {
        return rtime;
    }

    public void setRtime(long rtime) {
        this.rtime = rtime;
    }

    public long getSendtime() {
        return sendtime;
    }

    public void setSendtime(long sendtime) {
        this.sendtime = sendtime;
    }

    public long getFtime() {
        return ftime;
    }

    public void setFtime(long ftime) {
        this.ftime = ftime;
    }

    @DatabaseField
    private long rtime;
    @DatabaseField
    private long sendtime;
    @DatabaseField
    private String type;
    @DatabaseField
    private long ftime;
    @DatabaseField
    private String fcontent;
    @DatabaseField
    private String ftitle;
    @DatabaseField
    private String notarize;
    @DatabaseField
    private String disagree;
    @DatabaseField
    private String amount;
    @DatabaseField
    private String total;

    private String state;
    private String tapeurl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;//标识通知的读取状态
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
