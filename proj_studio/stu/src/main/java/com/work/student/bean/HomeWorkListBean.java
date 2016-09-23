package com.work.student.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.util.json.JSONArray;
import com.huawei.util.json.JSONException;
import com.huawei.util.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maguitao on 2016/9/19.
 * 说明
 */
public class HomeWorkListBean implements Serializable {


    /**
     * name : 4. 操场上_1472700113
     * desc : hhhhh
     * tpid : 79
     * teaid : 198
     * subjectid : 107
     * starttime : 1970-01-01 08:00
     * endtime : 1970-01-01 08:00
     * status : 1
     * tx : [{"id":"98","tlx":"21","title":"单项选择题","num":"1"},{"id":"97","tlx":"23","title":"双项选择题","num":"1"}]
     * teachername : teacher
     * subject : 语文
     */

    private String name;
    private String desc;
    private String tpid;
    private String teaid;
    private String subjectid;
    private String starttime;
    private String endtime;
    private int status;//0代表未做，2代表已完成，1代表过期
    private String teachername;
    private String subject;
    /**
     * id : 98
     * tlx : 21
     * title : 单项选择题
     * num : 1
     */

    private List<TxBean> tx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTpid() {
        return tpid;
    }

    public void setTpid(String tpid) {
        this.tpid = tpid;
    }

    public String getTeaid() {
        return teaid;
    }

    public void setTeaid(String teaid) {
        this.teaid = teaid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<TxBean> getTx() {
        return tx;
    }

    public void setTx(List<TxBean> tx) {
        this.tx = tx;
    }

    public static class TxBean {
        private String id;
        private String tlx;
        private String title;
        private int num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTlx() {
            return tlx;
        }

        public void setTlx(String tlx) {
            this.tlx = tlx;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static List<HomeWorkListBean> ParseData(String json) {
        List<HomeWorkListBean> mList;
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = (int) jsonObject.get("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                return new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HomeWorkListBean>>() {
                }.getType());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
