package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 作业-》历史列表数据
 * Created by 左丽姬 on 2016/9/14.
 */
public class WorkHistory implements Serializable{
    public String id;
    public String name;
    public long createtime;
    public long endtime;
    public String type;
    public int total;
    public int status;
    public String chapterdid;
    public String gradeid;
    public String judge;
    public int tp_share;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChapterdid() {
        return chapterdid;
    }

    public void setChapterdid(String chapterdid) {
        this.chapterdid = chapterdid;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public int getTp_share() {
        return tp_share;
    }

    public void setTp_share(int tp_share) {
        this.tp_share = tp_share;
    }

    @Override
    public String toString() {
        return "WorkHistory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createtime=" + createtime +
                ", endtime=" + endtime +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", chapterdid='" + chapterdid + '\'' +
                ", judge='" + judge + '\'' +
                '}';
    }
}
