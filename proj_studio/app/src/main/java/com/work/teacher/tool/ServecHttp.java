package com.work.teacher.tool;

import com.work.teacher.bean.QuestionAnswer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

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
     * 作业--》》历史查询
     */
    public AjaxParams queryHistoryWork(String key, String userId, String page, String curpage, String jd, String zt,
                                       String gx) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("page", page);
        params.put("curpage", curpage);
        params.put("jd", jd);
        params.put("zt", zt);
        params.put("gx", gx);
        return params;
    }

    /**
     * 作业-》题库查询
     */
    public AjaxParams queryQuestionWork(String key, String userId, String txid, String nyid, String keyword, String zyjd, String page, String curpage) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("page", page);
        params.put("curpage", curpage);
        params.put("txid", txid);
        params.put("nyid", nyid);
        params.put("keyword", keyword);
        params.put("zyjd", zyjd);
        return params;
    }

    /**
     * 作业-》设置属性--》》提交
     */
    public AjaxParams addWorkCommit(String key, String userId, String ms, String titlename, String xueke,
                                    String jiaocai, String jdid, String lx, String torder, String content) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("ms", ms);
        params.put("titlename", titlename);
        params.put("xueke", xueke);
        params.put("jiaocai", jiaocai);
        params.put("jdid", jdid);
        params.put("lx", lx);
        params.put("torder", torder);
        params.put("content", content);
        return params;
    }

    /**
     * 作业-》题库--》》添加收藏
     */
    public AjaxParams workAddCollect(String key, String userId, QuestionAnswer answer, int iscollect) {
        //key、userId、zyid  作业的id、tid  题目id、ifrom //题目来源 0系统 1个人、diff //题目难度、tixing //题目类型
        // zid //章节id、 ino //题目编号、 grade //年级、 subject //科目
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        if (iscollect == 0)//判断是收藏还是移除收藏
            params.put("tid", answer.getId());
        else
            params.put("tid", answer.getIb());
        params.put("ifrom", answer.getIfrom() + "");
        params.put("diff", answer.getDiff() + "");
        params.put("tixing", answer.getType());
        params.put("zid", answer.getFidi());
        params.put("ino", answer.getIno());
        params.put("grade", answer.getGradeid());
        params.put("subject", answer.getSubjectid());
        return params;
    }

    /**
     * @param userId 获取问卷列表
     * @param p
     * @return
     */
    public AjaxParams addQuestionnarieList(String userId, String p) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userId);
        params.put("p", p);
        return params;
    }

    /**
     * @param userId 获取问卷详情
     * @param wid
     * @return
     */
    public AjaxParams addQuestionnarieDetail(String userId, String wid) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userId);
        params.put("wid", wid);
        return params;
    }

    /**
     * 作业-》发布作业-》从题库添加至作业
     */
    public AjaxParams workQuesAdd(String key, String userId, QuestionAnswer answer, String xk, String cartid) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("tlx", answer.getType());
        params.put("xk", xk);
        params.put("cartid", cartid);
        params.put("tid", answer.getId());
        params.put("tku", answer.getIfrom() + "");
        return params;
    }

    /**
     * 作业--》题库--》》编辑
     */
    public AjaxParams workQuestionEdit(String key, String userId, String type, String tid, String body, String explain, String correctanswer, String diff, String options, String id,String tpid,String kuid) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("type", type);
        params.put("tid", tid);
        params.put("body", body);
        params.put("explain", explain);
        params.put("diff", diff);
        params.put("correctanswer", correctanswer);
        params.put("options", options);
        params.put("id", id);
        if(!"".equals(tpid))
            params.put("tpid", tpid);
        if(!"".equals(kuid))
            params.put("kuid", kuid);
        return params;
    }

    /**
     * 作业--》作业发布-》添加题目
     */
    public AjaxParams workAddWork(String key, String userId, String type, String body, String explain, String correctanswer, String diff, String options, String KnowId, String zyid, String xk, String jc, String zj, String voice) {
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        if (!"".equals(zyid))
            params.put("zyid", zyid);
        params.put("xk", xk);
        params.put("jc", jc);
        params.put("zj", zj);
        params.put("type", type);
        params.put("diff", diff);
        params.put("body", body);
        params.put("explain", explain);
        params.put("correctanswer", correctanswer);
        params.put("options", options);
        params.put("voice", voice);
        params.put("id", KnowId);
        return params;
    }

    public AjaxParams publishQuestionarie(String userId, String question) {
        AjaxParams params = new AjaxParams();
        params.put("userid", userId);
        params.put("", question);
        return params;
    }

    /**作业发布*/
    public AjaxParams workRelease(String key, String userId, String zyid,String stu_id,String tea_id,String startimes,String endtimes){
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("zyid", zyid);
        params.put("stu_id", stu_id);
        params.put("tea_id", tea_id);
        params.put("startimes", startimes);
        params.put("endtimes", endtimes);
        return params;
    }
}
