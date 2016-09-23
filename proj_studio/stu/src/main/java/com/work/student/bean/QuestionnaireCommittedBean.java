package com.work.student.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.student.tool.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by maguitao on 2016/9/12.
 * 说明 ：提交过问卷之后的bean
 */
public class QuestionnaireCommittedBean {

    /**
     * id : 131
     * userid : 129
     * wid : 103
     * status : 1
     * sendid : 129
     * type : 2
     * sendtime : 1473406025
     * retime : null
     * classid : MTAwNzg=
     * teacherid : 129
     * wtitle : 53559879900962e99898
     * wintro : 53559879900962e9989853559879900962e9989853559879900962e99898
     * wcontent : @256@257@
     * ctime : 1473405879
     * stime : 1473406023
     * dtime : 1473406023
     * total : 1
     * task : [{"tid":"256","userid":"129","title":null,"tcontent":"6df15733572854ea4e2a77014efdff1f","ctime":"1473405945","wid":"103","type":"1","option":[{"content":"4e0a6d77","number":0,"percent":0},{"content":"5c71897f","number":0,"percent":0},{"content":"6d596c5f","number":0,"percent":0},{"content":"5e7f4e1c","number":0,"percent":0}]},{"tid":"257","userid":"129","title":null,"tcontent":"4e0b73ed4e864f604eec4f1a900962e9505a4ec04e48ff1f","ctime":"1473406001","wid":"103","type":"1","option":[{"content":"770b753589c6","number":"3","percent":"0.3"},{"content":"62536e38620f","number":"4","percent":"0.4"},{"content":"776189c9","number":"2","percent":"0.2"},{"content":"8dd16b65","number":"1","percent":"0.1"}]}]
     * teachername : 676880015e08123
     */

    private String id;
    private String userid;
    private String wid;
    private String status;
    private String sendid;
    private String type;
    private String sendtime;
    private String retime;
    private String classid;
    private String teacherid;
    private String wtitle;
    private String wintro;
    private String wcontent;
    private String ctime;
    private String stime;
    private String dtime;
    private String total;
    private String teachername;
    /**
     * tid : 256
     * userid : 129
     * title : null
     * tcontent : 6df15733572854ea4e2a77014efdff1f
     * ctime : 1473405945
     * wid : 103
     * type : 1
     * option : [{"content":"4e0a6d77","number":0,"percent":0},{"content":"5c71897f","number":0,"percent":0},{"content":"6d596c5f","number":0,"percent":0},{"content":"5e7f4e1c","number":0,"percent":0}]
     */

    private List<TaskBean> task;

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

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public Object getRetime() {
        return retime;
    }

    public void setRetime(String retime) {
        this.retime = retime;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
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

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public List<TaskBean> getTask() {
        return task;
    }

    public void setTask(List<TaskBean> task) {
        this.task = task;
    }

    public static class TaskBean {
        private String tid;
        private String userid;
        private Object title;
        private String tcontent;
        private String ctime;
        private String wid;
        private String type;
        /**
         * content : 4e0a6d77
         * number : 0
         * percent : 0
         */

        private List<OptionBean> option;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public String getTcontent() {
            return tcontent;
        }

        public void setTcontent(String tcontent) {
            this.tcontent = tcontent;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<OptionBean> getOption() {
            return option;
        }

        public void setOption(List<OptionBean> option) {
            this.option = option;
        }

        public static class OptionBean {
            private String content;
            private int number;
            private int percent;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getPercent() {
                return percent;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }
        }
    }

    public static QuestionnaireCommittedBean parser(String result) {
        QuestionnaireCommittedBean bean = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            bean = new Gson().fromJson(jsonArray.toString(), new TypeToken<QuestionnaireCommittedBean>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bean;

    }
}
