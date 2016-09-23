package com.work.student.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.student.bean.JobDetail;
import com.work.student.bean.QuestionnaireBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.http.AjaxParams;

/**
 * post提交参数封装
 *
 * @author 左丽姬
 */
public class ServecHttp implements Serializable {

    /**
     * 登录
     */
    public AjaxParams setLogin(String name, String pwd) {
        AjaxParams params = new AjaxParams();
        params.put("userName", name);
        params.put("userPwd", pwd);
        return params;
    }

    /**
     * 手机号码验证,获取验证码
     */
    public AjaxParams verfiyMobile(String name, String tel) {
        AjaxParams params = new AjaxParams();
        params.put(name, tel);
        return params;
    }

    /**
     * 注册
     */
    public AjaxParams setRegister(String name, String pwd, String tel, String verfiy) {
        AjaxParams params = new AjaxParams();
        params.put("userName", name);
        params.put("userPwd", pwd);
        params.put("mobile", tel);
        params.put("verifi", verfiy);
        return params;
    }

    /**
     * 封装key，userId两个参数
     */
    public AjaxParams keyAndId(String key, String userId) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        return params;
    }

    /**
     * 修改用户名
     */
    public AjaxParams updatePersonalName(String key, String userId, String pname) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("userName", pname);
        return params;
    }

    /**
     * 修改科目、年级
     */
    public AjaxParams updateSubjectAndGrade(String key, String userId, String subId, String gradeId) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("subject", subId);
        params.put("course_grade", gradeId);
        return params;
    }

    /**
     * 修改用户头像
     */
    public AjaxParams updatePersonalAvatar(String key, String userId, File avatar) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        try {
            params.put("avatar", avatar);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 绑定学校
     */
    public AjaxParams updateBindSchool(String key, String userId, String code) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("schoolCode", code);
        return params;
    }

    /**
     * 修改密码,手机号码 ,验证班级名称是否已经存在,查询班级信息
     */
    public AjaxParams updatePwd(String key, String userId, String pwd, String name) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put(name, pwd);
        return params;
    }

    /**
     * 发布通知第一步
     */
    public AjaxParams noticeOne(String key, String userId, String title, String content, String record, String agress,
                                String disagress) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("title", title);
        params.put("content", title);
        params.put("record", record);
        params.put("agress", agress);
        params.put("disagress", disagress);
        params.put("state", "1");
        params.put("adddate", System.currentTimeMillis() + "");
        return params;
    }

    /**
     * 发布通知第二步
     */
    public AjaxParams noticeTwo(String key, String userId, String notice, String tea_amount, String stu_amount,
                                String pat_amount, int state, long Releasedate) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("notice", notice);
        if (!"".equals(tea_amount))
            params.put("tea_amount", tea_amount);
        if (!"".equals(stu_amount))
            params.put("stu_amount", stu_amount);
        if (!"".equals(pat_amount))
            params.put("pat_amount", pat_amount);
        params.put("state", state + "");
        if (Releasedate != 0)
            params.put("Releasedate", Releasedate + "");
        else
            params.put("Releasedate", System.currentTimeMillis() + "");
        return params;
    }


    /**
     * 发布通知--》》发布对象
     */
    public AjaxParams noticeAmount(String key, String userId, String cid) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("cid", cid);
        return params;
    }

    /**
     * 通知列表
     */
    public AjaxParams noticeLists(String key, String userId, int page, int curpage) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("curpage", curpage + "");
        params.put("page", page + "");
        return params;
    }

    /**
     * 通知详情
     */
    public AjaxParams noticeDetails(String key, String userId, String fid) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("fid", fid);
        return params;
    }

    /**
     * 班级--》》创建班级
     */
    public AjaxParams createClass(String key, String userId, String name, String content, String type) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("name", name);
        params.put("content", content);
        params.put("type", type);
        return params;
    }

    /**
     * 消息请求参数
     */
    public AjaxParams createMessageParams(String userid, String page, String type) {
        AjaxParams params = new AjaxParams();
        params.put("p", page);
        params.put("userid", userid);
        params.put("type", type);
        return params;
    }

    /**
     * 更改未读通知的消息状态接口。消息请求参数
     */
    public AjaxParams createUpdateMessageParams(String userid, String type, String fid) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        params.put("type", type);
        params.put("fid", fid);
        return params;
    }

    /**
     * 更改未读问卷的消息状态接口。消息请求参数
     */
    public AjaxParams createUpdateQuestionParams(String userid, String type, String wid) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        params.put("type", type);
        params.put("wid", wid);
        return params;
    }

    /**
     * 获取通知详情 add by maguitao
     */
    public AjaxParams createNoticeDetailParams(String userid, String type, String fid) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        params.put("type", type);
        params.put("fid", fid);
        return params;
    }

    /**
     * 参加会议或者拒绝 1.同意，0不同意 add by maguitao
     */
    public AjaxParams createNoticStateParams(String userid, String type, String fid, String isagree) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        params.put("type", type);
        params.put("fid", fid);
        params.put("isagree", isagree);
        return params;
    }

    /**
     * 参加会议或者拒绝 1.同意，0不同意 add by maguitao
     */
    public AjaxParams createQuestionnaireParams(String userid, String wid) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        params.put("wid", wid);
        return params;
    }

    /**
     * 提交问卷：add by maguitao
     */
    public AjaxParams createQuestionnaireCommitParams(String userid, String wid, QuestionnaireBean bean) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userid);
        params.put("wid", wid);
        List<QuestionnaireBean.TaskBean> tasks = bean.getTask();
        for (int j = 0; j < tasks.size(); j++) {
            QuestionnaireBean.TaskBean taskbean = tasks.get(j);
            String tid = taskbean.getTid();
            int opt = taskbean.getCureentSelectPosition();
            int selectposition = taskbean.cureentSelectPosition + 1;
            params.put("tid", tid);
            params.put("opt" + j + 1, selectposition + "");
            LogUtils.debug("提交问卷的参数 tid=" + tid + "：opt=" + selectposition);
        }
        return params;
    }

    /**
     * 获取学生作业列表
     *
     * @param stuid 学生id'
     * @param p     页码
     * @return
     */
    public static AjaxParams createHomeWorkListParams(String stuid, String p) {
        AjaxParams params = new AjaxParams();
        params.put("p", p);
        params.put("stuid", stuid);
        return params;
    }

    /**
     * 获取作业详情
     *
     * @param stuid
     * @param tpid  作业的id
     * @return
     */
    public static AjaxParams createHomeWorkDetailtParams(String stuid, String tpid) {
        AjaxParams params = new AjaxParams();
        // params.put("stuid", stuid);
        params.put("tpid", tpid);
        LogUtils.debug("传过来的topid=" + tpid + " userid" + stuid);
        return params;
    }

    /**
     * 提交作业
     *
     * @param
     * @param
     * @param starttime
     * @return
     */
    public static AjaxParams createCommnithomeWork(List<JobDetail> jobDetails, long duration, long starttime) {
        AjaxParams params = new AjaxParams();
        // params.put("stuid", stuid);
        List<JobDetailBean> commitList = new ArrayList<>();
        for (JobDetail jobDetail1 : jobDetails) {
            JobDetailBean bean = new JobDetailBean();
            bean.setUserid(IBaes.userid);
            bean.setTpid(jobDetail1.getTpid());
            bean.setTlx(jobDetail1.getQuestype());
            bean.setDurationtime(duration);
            bean.setStatrttime(starttime);
            bean.setScore(jobDetail1.getScore());
            bean.setEndtime(System.currentTimeMillis());
            bean.setIno(jobDetail1.getIno());
            switch (jobDetail1.getQuestype()) {

                case JobDetail.TYPE_DANXUAN:
                    int select = jobDetail1.getSelectPosition();
                    if (select == -1) {
                        jobDetail1.setCommitanswer("");
                    }
                    jobDetail1.setCommitanswer(select + "");
                    bean.setCommitanswer(select + "");
                    commitList.add(bean);
                    break;
                case JobDetail.TYPE_DUOXUAN:
                    List<String> selects = jobDetail1.getSelectionPositions();
                    if (selects != null && selects.size() > 0) {
                        String seletString = selects.toString();
                        seletString = seletString.substring(1, seletString.length() - 1);
                        LogUtils.debug("seletString=" + seletString);
                        bean.setCommitanswer(seletString);
                    } else {
                        bean.setCommitanswer(null);
                    }
                    commitList.add(bean);
                    break;
                case JobDetail.TYPE_TIANKONG:
                    bean.setCommitanswer(jobDetail1.getCommitanswer());
                    commitList.add(bean);
                    break;
            }

        }
        Type type = new TypeToken<List<JobDetailBean>>() {
        }.getType();
        String jsonstr = new Gson().toJson(commitList, type);
        params.put("params", jsonstr);
        return params;
    }

    static class JobDetailBean {
        public String getCommitanswer() {
            return commitanswer;
        }

        public long getDurationtime() {
            return durationtime;
        }

        public void setDurationtime(long durationtime) {
            this.durationtime = durationtime;
        }

        private long durationtime;

        public int getTlx() {
            return tlx;
        }

        public void setTlx(int tlx) {
            this.tlx = tlx;
        }

        private int tlx;//题目的id

        @Override
        public String toString() {
            return "JobDetailBean{" +
                    "userid='" + userid + '\'' +
                    ", tpid='" + tpid + '\'' +
                    ", commitanswer='" + commitanswer + '\'' +
                    '}';
        }

        public void setCommitanswer(String commitanswer) {
            this.commitanswer = commitanswer;
        }

        private String userid;

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

        private String tpid;
        private String commitanswer;
        private int ino;//标志每一道题目
        private long statrttime;
        private long endtime;
        private float score;

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getIno() {
            return ino;
        }

        public void setIno(int ino) {
            this.ino = ino;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public long getStatrttime() {
            return statrttime;
        }

        public void setStatrttime(long statrttime) {
            this.statrttime = statrttime;
        }
    }

}
