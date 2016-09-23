package com.work.student.bean;

/**
 * Created by maguitao on 2016/9/9.
 */
public class NoticeDetail {

    /**
     * id : 252
     * userid : 173
     * fid : 170
     * agree : null
     * rtime : null
     * sendtime : 1471594566
     * type : 2
     * status : 0
     * teacherid : 173
     * ftime : 1471809506
     * fcontent : 4f5c4e1a201c1.3 622a4e004e2a51e04f554f53_1471598626201d5df253d15e03ff0c5f0059cb65f695f44e3aff1a2016-08-22 03:58:13,622a6b6265f695f4ff1a2016-08-22 03:58:13ff0c8bf7630965f65b8c62104f5c4e1aff01
     * ftitle : 4f5c4e1a201c1.3 622a4e004e2a51e04f554f53_1471598626201d901a77e5
     * notarize : 1
     * disargee : null
     * amount : 0@0
     * total : 3
     * state : 2
     * tapeurl : null
     * teachername : 674e
     */
    private String id;
    private String userid;
    private String fid;
    private String agree;
    private String rtime;
    private String sendtime;
    private String type;
    private String status;
    private String teacherid;
    private String ftime;
    private String fcontent;
    private String ftitle;
    private String notarize;
    private String disargee;

    @Override
    public String toString() {
        return "NoticeDetail{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", fid='" + fid + '\'' +
                ", agree=" + agree +
                ", rtime=" + rtime +
                ", sendtime='" + sendtime + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", teacherid='" + teacherid + '\'' +
                ", ftime='" + ftime + '\'' +
                ", fcontent='" + fcontent + '\'' +
                ", ftitle='" + ftitle + '\'' +
                ", notarize='" + notarize + '\'' +
                ", disargee=" + disargee +
                ", amount='" + amount + '\'' +
                ", total='" + total + '\'' +
                ", state='" + state + '\'' +
                ", tapeurl=" + tapeurl +
                ", teachername='" + teachername + '\'' +
                '}';
    }

    private String amount;
    private String total;
    private String state;
    private String tapeurl;
    private String teachername;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Object getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public Object getRtime() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
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

    public String getDisargee() {
        return disargee;
    }

    public void setDisargee(String disargee) {
        this.disargee = disargee;
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

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }
}
