package com.work.teacher.bean;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.Serializable;

/**
 * 作业-》发布作业-》题目列表
 * Created by 左丽姬 on 2016/9/14.
 */
public class WorkRelseContent implements Serializable{

    public String id;
    public String userid;
    public String tpid;
    public String iid;
    public String ifrom;
    public String questype;
    public String autojudge;
    public String itemflag;
    public double score;
    public int orderidx;
    public QuestionAnswer answer;
    public MediaPlayer player=null;
    public AudioManager mAudioManager=null;//声音管理器


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

    public String getTpid() {
        return tpid;
    }

    public void setTpid(String tpid) {
        this.tpid = tpid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getIfrom() {
        return ifrom;
    }

    public void setIfrom(String ifrom) {
        this.ifrom = ifrom;
    }

    public String getQuestype() {
        return questype;
    }

    public void setQuestype(String questype) {
        this.questype = questype;
    }

    public String getAutojudge() {
        return autojudge;
    }

    public void setAutojudge(String autojudge) {
        this.autojudge = autojudge;
    }

    public String getItemflag() {
        return itemflag;
    }

    public void setItemflag(String itemflag) {
        this.itemflag = itemflag;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getOrderidx() {
        return orderidx;
    }

    public void setOrderidx(int orderidx) {
        this.orderidx = orderidx;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public QuestionAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(QuestionAnswer answer) {
        this.answer = answer;
    }

    public WorkRelseContent() {

    }
    @Override
    public String toString() {
        return "WorkRelseQuestion{" +
                ", id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", tpid='" + tpid + '\'' +
                ", iid='" + iid + '\'' +
                ", ifrom='" + ifrom + '\'' +
                ", questype='" + questype + '\'' +
                ", autojudge='" + autojudge + '\'' +
                ", itemflag='" + itemflag + '\'' +
                ", score=" + score +
                ", orderidx=" + orderidx +
                '}';
    }
}
