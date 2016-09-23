package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 年级科目 ,班级列表
 * 作业发布选择题答案
 *
 * @author 左丽姬
 */
public class SubAndGrade implements Serializable {

    public String id;
    public String name;
    public String url;
    public boolean ischeck = false;
    public boolean isEdit = false;//是否可编辑

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

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SubAndGrade [id=" + id + ", name=" + name + ", ischeck=" + ischeck + "]";
    }


}
