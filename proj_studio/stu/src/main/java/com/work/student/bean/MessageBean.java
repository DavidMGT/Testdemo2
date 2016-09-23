package com.work.student.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 * 消息实体类
 */
public class MessageBean {
    private String agree;
    private String amount;
    private String disargee;
    private String fcontent;    //简介
    private String fid;
    private String ftime;
    private String ftitle;
    private String id;
    private String num;         //未读消息的数量
    private String sendtime;
    private String state;
    private String status;
    private String total;
    private String type;
    private String userid;

    private String classid;
    private String ctime;
    private String dtime;
    //    private String id;
//    private String num;
    private String sendid;
    //    private String sendtime;
//    private String status;
    private String stime;
    //    private String total;
//    private String type;
//    private String userid;
    private String wcontent;
    private String wid;
    private String wintro;  //简介
    private String wtitle;
    private String teachername;

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDisargee() {
        return disargee;
    }

    public void setDisargee(String disargee) {
        this.disargee = disargee;
    }

    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getWcontent() {
        return wcontent;
    }

    public void setWcontent(String wcontent) {
        this.wcontent = wcontent;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWintro() {
        return wintro;
    }

    public void setWintro(String wintro) {
        this.wintro = wintro;
    }

    public String getWtitle() {
        return wtitle;
    }

    public void setWtitle(String wtitle) {
        this.wtitle = wtitle;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "agree='" + agree + '\'' +
                ", amount='" + amount + '\'' +
                ", disargee='" + disargee + '\'' +
                ", fcontent='" + fcontent + '\'' +
                ", fid='" + fid + '\'' +
                ", ftime='" + ftime + '\'' +
                ", ftitle='" + ftitle + '\'' +
                ", id='" + id + '\'' +
                ", num='" + num + '\'' +
                ", sendtime='" + sendtime + '\'' +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", total='" + total + '\'' +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                ", classid='" + classid + '\'' +
                ", ctime='" + ctime + '\'' +
                ", dtime='" + dtime + '\'' +
                ", sendid='" + sendid + '\'' +
                ", stime='" + stime + '\'' +
                ", wcontent='" + wcontent + '\'' +
                ", wid='" + wid + '\'' +
                ", wintro='" + wintro + '\'' +
                ", wtitle='" + wtitle + '\'' +
                '}';
    }

    public static List<MessageBean> parser(String result) {
        List<MessageBean> lists = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            lists = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<MessageBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;

    }


    //    public List<InformBean> mInformBeen;
//    public List<QuestionnarieBean> mQuestionnaires;
//
//    //未读通知
//    public int informUnRead;
//    //未读问卷
//    public int questionnarieUnRead;
//
//    //问卷的简介,取自集合的最后一条的wintro
//    public String questionnarieIntro;
//
//    public static MessageBean parser(String data) {
//
//        MessageBean messageBean = new MessageBean();
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            JSONArray jsonArray = jsonObject.getJSONArray("data");
//            String notifyData = jsonArray.get(0).toString();
//            String questionnarieData = jsonArray.get(1).toString();
//            messageBean.mInformBeen = new Gson().fromJson(notifyData, new TypeToken<List<InformBean>>() {
//            }.getType());
//            messageBean.mQuestionnaires = new Gson().fromJson(questionnarieData, new TypeToken<List<QuestionnarieBean>>() {
//            }.getType());
//
//            //获取通知和问卷未读数 默认是0 未读取。1为读取,2完成问卷调查
//            for (int i = 0; i < messageBean.mInformBeen.size(); i++) {
//                String state = messageBean.mInformBeen.get(i).getState();
//                if (TextUtil.isEmpty(state)) {
//                    continue;
//                }
//                int iState = Integer.parseInt(state);
//                if (iState == 0) {
//                    messageBean.informUnRead++;
//                }
//            }
//
//            int questioinnaireSize = messageBean.mQuestionnaires.size();
//            for (int i = 0; i < questioinnaireSize; i++) {
//                String state = messageBean.mQuestionnaires.get(i).getStatus();
//                if (TextUtil.isEmpty(state)) {
//                    continue;
//                }
//                int iState = Integer.parseInt(state);
//                if (iState == 0) {
//                    messageBean.questionnarieUnRead++;
//                }
//                //取最集本最后一条问卷的简介
//                if(i == questioinnaireSize-1){
//                    messageBean.questionnarieIntro = messageBean.mQuestionnaires.get(i).getWintro();
//                }
//            }
//
//
//            return messageBean;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


}
