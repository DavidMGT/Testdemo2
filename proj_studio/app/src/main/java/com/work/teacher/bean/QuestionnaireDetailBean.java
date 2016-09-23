package com.work.teacher.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maguitao on 2016/9/13.
 * 说明 问卷详情的bean
 */
public class QuestionnaireDetailBean {

    public int getDstype() {
        return dstype;
    }

    public void setDstype(int dstype) {
        this.dstype = dstype;
    }

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
     * wtitle : 51734e8e672c6821768495ee53778c0367e5
     * wintro : 8c0367e5572868215b66751f7684751f6d3b652f51fa
     * wcontent : @256@257@
     * ctime : 1473405879
     * stime : 1473406023
     * dtime : 1473406023
     * total : 2
     * finishnum : 0
     * mystatus : 0
     * task : [{"tid":"256","userid":"129","title":null,"tcontent":"6df15733572854ea4e2a77014efdff1f","ctime":"1473405945","wid":"103","type":"1","option":[{"content":"4e0a6d77","number":"4","percent":"0.11"},{"content":"5c71897f","number":"3","percent":"0.08"},{"content":"6d596c5f","number":"2","percent":"0.05"},{"content":"5e7f4e1c","number":"1","percent":"0.03"}]},{"tid":"257","userid":"129","title":null,"tcontent":"4e0b73ed4e864f604eec4f1a900962e9505a4ec04e48ff1f","ctime":"1473406001","wid":"103","type":"1","option":[{"content":"770b753589c6","number":"9","percent":"0.24"},{"content":"62536e38620f","number":"6","percent":"0.16"},{"content":"776189c9","number":"11","percent":"0.29"},{"content":"8dd16b65","number":"2","percent":"0.05"}]}]
     * teachername : 676880015e08123
     */
    private int dstype = 1;//2代表定时发布，1代表马上发布

    public String getDstime() {
        return dstime;
    }

    public void setDstime(String dstime) {
        this.dstime = dstime;
    }

    private String dstime;//定时发布的时间
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
    private String stime;//发布时间
    private String dtime;
    private String total;

    @Override
    public String toString() {
        return "QuestionnaireDetailBean{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", wid='" + wid + '\'' +
                ", status='" + status + '\'' +
                ", sendid='" + sendid + '\'' +
                ", type='" + type + '\'' +
                ", sendtime='" + sendtime + '\'' +
                ", retime='" + retime + '\'' +
                ", classid='" + classid + '\'' +
                ", teacherid='" + teacherid + '\'' +
                ", wtitle='" + wtitle + '\'' +
                ", wintro='" + wintro + '\'' +
                ", wcontent='" + wcontent + '\'' +
                ", ctime='" + ctime + '\'' +
                ", stime='" + stime + '\'' +
                ", dtime='" + dtime + '\'' +
                ", total='" + total + '\'' +
                ", finishnum='" + finishnum + '\'' +
                ", mystatus=" + mystatus +
                ", teachername='" + teachername + '\'' +
                ", task=" + task +
                '}';
    }

    private String finishnum;
    private int mystatus;
    private String teachername;
    /**
     * tid : 256
     * userid : 129
     * title : null
     * tcontent : 6df15733572854ea4e2a77014efdff1f
     * ctime : 1473405945
     * wid : 103
     * type : 1
     * option : [{"content":"4e0a6d77","number":"4","percent":"0.11"},{"content":"5c71897f","number":"3","percent":"0.08"},{"content":"6d596c5f","number":"2","percent":"0.05"},{"content":"5e7f4e1c","number":"1","percent":"0.03"}]
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

    public String getRetime() {
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

    public String getFinishnum() {
        return finishnum;
    }

    public void setFinishnum(String finishnum) {
        this.finishnum = finishnum;
    }

    public int getMystatus() {
        return mystatus;
    }

    public void setMystatus(int mystatus) {
        this.mystatus = mystatus;
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
        @Override
        public String toString() {
            return "TaskBean{" +
                    "tid='" + tid + '\'' +
                    ", userid='" + userid + '\'' +
                    ", title='" + title + '\'' +
                    ", tcontent='" + tcontent + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", wid='" + wid + '\'' +
                    ", type='" + type + '\'' +
                    ", option=" + option +
                    '}';
        }

        private String tid;
        private String userid;
        private String title;
        private String tcontent;
        private String ctime;
        private String wid;
        private String type;
        /**
         * content : 4e0a6d77
         * number : 4
         * percent : 0.11
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
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
            private String number;
            private String percent;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            @Override
            public String toString() {
                return "OptionBean{" +
                        "content='" + content + '\'' +
                        ", number='" + number + '\'' +
                        ", percent='" + percent + '\'' +
                        '}';
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }
        }
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<String> getJiaZhangs() {
        return jiaZhangs;
    }

    public void setJiaZhangs(List<String> jiaZhangs) {
        this.jiaZhangs = jiaZhangs;
    }

    private List<String> students = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();
    private List<String> jiaZhangs = new ArrayList<>();
}
