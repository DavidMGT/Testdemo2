package com.work.teacher.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.teacher.tool.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 消息；类型之问卷
 * Created by maguitao on 2016/9/7.
 */
public class QuestionnarieBean implements Serializable {


    /**
     * wid : 103
     * userid : 129
     * wtitle : 51734e8e672c6821768495ee53778c0367e5
     * wintro : 8c0367e5572868215b66751f7684751f6d3b652f51fa
     * wcontent : @256@257@
     * ctime : 1473405879
     * stime : 1473406023
     * dtime : 1473406023
     * total : 2
     * sendtime : 1473406025
     * classid : MTAwNzg=
     * finishnum : 0
     */

    private String wid;
    private String userid;
    private String wtitle;
    private String wintro;
    private String wcontent;
    private String ctime;
    private String stime;
    private String dtime;
    private String total;
    private String sendtime;
    private String classid;
    private String finishnum;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWtitle() {
        return wtitle;
    }

    public void setWtitle(String wtitle) {
        this.wtitle = wtitle;
    }

    public String getWintro() {
        return wintro;
    }

    public void setWintro(String wintro) {
        this.wintro = wintro;
    }

    public String getWcontent() {
        return wcontent;
    }

    public void setWcontent(String wcontent) {
        this.wcontent = wcontent;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    @Override
    public String toString() {
        return "QuestionnarieBean{" +
                "wid='" + wid + '\'' +
                ", userid='" + userid + '\'' +
                ", wtitle='" + wtitle + '\'' +
                ", wintro='" + wintro + '\'' +
                ", wcontent='" + wcontent + '\'' +
                ", ctime='" + ctime + '\'' +
                ", stime='" + stime + '\'' +
                ", dtime='" + dtime + '\'' +
                ", total='" + total + '\'' +
                ", sendtime='" + sendtime + '\'' +
                ", classid='" + classid + '\'' +
                ", finishnum='" + finishnum + '\'' +
                '}';
    }

    public String getFinishnum() {
        return finishnum;
    }

    public void setFinishnum(String finishnum) {
        this.finishnum = finishnum;
    }

    public static List<QuestionnarieBean> parseData(String json) {
        List<QuestionnarieBean> mlist = null;
        try {
            mlist = new Gson().fromJson(json.toString(), new TypeToken<List<QuestionnarieBean>>() {
            }.getType());
            LogUtils.debug("mlist。size" + mlist.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mlist;
    }
}
