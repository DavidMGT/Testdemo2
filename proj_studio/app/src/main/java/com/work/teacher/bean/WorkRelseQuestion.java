package com.work.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作业-》发布作业-》题目列表
 * Created by 左丽姬 on 2016/9/14.
 */
public class WorkRelseQuestion implements Serializable{

    public String title;
    public String tlx;
    public double fen;
    public List<WorkRelseContent> contents=new ArrayList<WorkRelseContent>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTlx() {
        return tlx;
    }

    public void setTlx(String tlx) {
        this.tlx = tlx;
    }

    public double getFen() {
        return fen;
    }

    public void setFen(double fen) {
        this.fen = fen;
    }


    public WorkRelseQuestion() {
    }
    @Override
    public String toString() {
        return "WorkRelseQuestion{" +
                "title='" + title + '\'' +
                ", tlx='" + tlx + '\'' +
                ", fen=" + fen +
                '}';
    }
}
