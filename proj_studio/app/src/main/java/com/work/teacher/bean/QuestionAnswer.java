package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 题库
 * Created by 左丽姬 on 2016/9/7.
 */
public class QuestionAnswer implements Serializable {
    public String id;//题目ID
    public String ino;//题目编号
    public String ib;//收藏列表题目ID
    public String gradeid;//年级ID
    public String subjectid;//科目ID
    public String type;//题目类型
    public int status;//0正常显示,1隐藏
    public String body;//题干内容
    public String correctanswer;//正确答案
    public String explain;//解析
    public int autojudge;
    public int itemflag;
    public int answertype;//答案类型 0 正常类型 1 题干与答案选项合并
    public int optioncnt; //选项个数
    public int diff; // 难易度 1容易 2较易 3一般 4较难 5困难
    public int usedcnt;//使用次数
    public int viewcnt;// 浏览次数
    public int favcnt;//收藏次数
    public int upcnt;//点赞次数
    public int downcnt;//点踩次数
    public int commentcnt;//评论次数
    public int isshared;//0 不共享 1 共享
    public String authorid;//作者ID 公共题库该项为0
    public String piid;
    public String cfrom;
    public long addtime;//添加时间
    public long lastupdatetime;//更新时间
    public int orderidx;
    public int ifrom;// 0 公共题库 1 个人题库
    public String voiceurl;//听力题目音频url地址
    public String fid;//教材ID
    public String fidi;//章节ID
    public String ibid;
    public String xuanxiang;//选项
    public boolean flag = false;//是否选中
    public int sus = 0;
    public long voicetime;

    //作业题目列表
    public String fuserid;
    public String kuid;
    public String pid;
    public String userid;
    public String tpid;

    public QuestionAnswer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIno() {
        return ino;
    }

    public void setIno(String ino) {
        this.ino = ino;
    }

    public String getIb() {
        return ib;
    }

    public void setIb(String ib) {
        this.ib = ib;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getAutojudge() {
        return autojudge;
    }

    public void setAutojudge(int autojudge) {
        this.autojudge = autojudge;
    }

    public int getItemflag() {
        return itemflag;
    }

    public void setItemflag(int itemflag) {
        this.itemflag = itemflag;
    }

    public int getAnswertype() {
        return answertype;
    }

    public void setAnswertype(int answertype) {
        this.answertype = answertype;
    }

    public int getOptioncnt() {
        return optioncnt;
    }

    public void setOptioncnt(int optioncnt) {
        this.optioncnt = optioncnt;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getUsedcnt() {
        return usedcnt;
    }

    public void setUsedcnt(int usedcnt) {
        this.usedcnt = usedcnt;
    }

    public int getViewcnt() {
        return viewcnt;
    }

    public void setViewcnt(int viewcnt) {
        this.viewcnt = viewcnt;
    }

    public int getFavcnt() {
        return favcnt;
    }

    public void setFavcnt(int favcnt) {
        this.favcnt = favcnt;
    }

    public int getUpcnt() {
        return upcnt;
    }

    public void setUpcnt(int upcnt) {
        this.upcnt = upcnt;
    }

    public int getDowncnt() {
        return downcnt;
    }

    public void setDowncnt(int downcnt) {
        this.downcnt = downcnt;
    }

    public int getCommentcnt() {
        return commentcnt;
    }

    public void setCommentcnt(int commentcnt) {
        this.commentcnt = commentcnt;
    }

    public int getIsshared() {
        return isshared;
    }

    public void setIsshared(int isshared) {
        this.isshared = isshared;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getCfrom() {
        return cfrom;
    }

    public void setCfrom(String cfrom) {
        this.cfrom = cfrom;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public long getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(long lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public int getOrderidx() {
        return orderidx;
    }

    public void setOrderidx(int orderidx) {
        this.orderidx = orderidx;
    }

    public int getIfrom() {
        return ifrom;
    }

    public void setIfrom(int ifrom) {
        this.ifrom = ifrom;
    }

    public String getVoiceurl() {
        return voiceurl;
    }

    public void setVoiceurl(String voiceurl) {
        this.voiceurl = voiceurl;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFidi() {
        return fidi;
    }

    public void setFidi(String fidi) {
        this.fidi = fidi;
    }

    public String getIbid() {
        return ibid;
    }

    public void setIbid(String ibid) {
        this.ibid = ibid;
    }

    public String getXuanxiang() {
        return xuanxiang;
    }

    public void setXuanxiang(String xuanxiang) {
        this.xuanxiang = xuanxiang;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getSus() {
        return sus;
    }

    public void setSus(int sus) {
        this.sus = sus;
    }

    public String getTpid() {
        return tpid;
    }

    public void setTpid(String tpid) {
        this.tpid = tpid;
    }

    public String getFuserid() {
        return fuserid;
    }

    public void setFuserid(String fuserid) {
        this.fuserid = fuserid;
    }

    public String getKuid() {
        return kuid;
    }

    public void setKuid(String kuid) {
        this.kuid = kuid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(long voicetime) {
        this.voicetime = voicetime;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "id='" + id + '\'' +
                ", ino='" + ino + '\'' +
                ", ib='" + ib + '\'' +
                ", gradeid='" + gradeid + '\'' +
                ", subjectid='" + subjectid + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", correctanswer='" + correctanswer + '\'' +
                ", explain='" + explain + '\'' +
                ", autojudge=" + autojudge +
                ", itemflag=" + itemflag +
                ", answertype=" + answertype +
                ", optioncnt=" + optioncnt +
                ", diff=" + diff +
                ", usedcnt=" + usedcnt +
                ", viewcnt=" + viewcnt +
                ", favcnt=" + favcnt +
                ", upcnt=" + upcnt +
                ", downcnt=" + downcnt +
                ", commentcnt=" + commentcnt +
                ", isshared=" + isshared +
                ", authorid='" + authorid + '\'' +
                ", piid='" + piid + '\'' +
                ", cfrom='" + cfrom + '\'' +
                ", addtime=" + addtime +
                ", lastupdatetime=" + lastupdatetime +
                ", orderidx=" + orderidx +
                ", ifrom=" + ifrom +
                ", voiceurl='" + voiceurl + '\'' +
                ", fid='" + fid + '\'' +
                ", fidi='" + fidi + '\'' +
                ", ibid='" + ibid + '\'' +
                ", xuanxiang='" + xuanxiang + '\'' +
                ", flag=" + flag +
                ", sus=" + sus +
                '}';
    }
}
