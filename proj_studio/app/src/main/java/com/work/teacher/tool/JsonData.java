package com.work.teacher.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.work.teacher.bean.ClassDetails;
import com.work.teacher.bean.ClassPersionDetails;
import com.work.teacher.bean.FeedbackDetails;
import com.work.teacher.bean.HistoryClass;
import com.work.teacher.bean.HistoryType;
import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.Notice;
import com.work.teacher.bean.NoticeClass;
import com.work.teacher.bean.NoticeDetails;
import com.work.teacher.bean.PersonalBean;
import com.work.teacher.bean.School;
import com.work.teacher.bean.SubAndGrade;
import com.work.teacher.bean.TeaStuPat;

import android.R.integer;
import android.util.Log;

/**
 * 解析json
 *
 * @author 左丽姬
 */
public class JsonData implements Serializable {

    /**
     * 登陆
     */
    public Map<String, Object> jsonLogin(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String userId = object.optString("userId");
            String zhuce = object.optString("zhuce");
            String key = object.optString("key");
            map.put("status", status);
            map.put("userId", userId);
            map.put("zhuce", zhuce);
            map.put("key", key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 手机号码
     */
    public int jsonInt(String resutl) {
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            return status;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 找回密码
     */
    public Map<String, Object> jsonForGet(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String username = object.optString("username");
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("username", username);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取验证码
     */
    public Map<String, Object> jsonVerfiy(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String username = object.optString("username");
            String yzm = object.optString("yzm");
            String sjyzm = object.optString("sjyzm");
            map.put("status", status);
            map.put("yzm", yzm);
            map.put("sjyzm", sjyzm);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 个人资料获取
     */
    public PersonalBean getPersonal(String resutl) {
        PersonalBean pb = null;
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String username = object.optString("userName");
            String avatar = object.optString("avatar");
            String userId = object.optString("userId");
            String subjectId = object.optString("subjectId");
            String subject = object.optString("subject");
            String course_gradeId = object.optString("course_gradeId");
            String course_grade = object.optString("course_grade");
            String schoolCode = object.optString("schoolCode");
            String school = object.optString("school");
            String grade = object.optString("grade");
            String zhuce = object.optString("zhuce");
            String mobile = object.optString("mobile");
            mobile = mobile.substring(0, 12);
            mobile = mobile.replaceAll("\\.", "");
            pb = new PersonalBean(status, userId, username, avatar, subjectId, subject, course_gradeId, course_grade,
                    schoolCode, school, grade, zhuce, mobile);
            // Log.i("test", pb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pb;
    }

    /**
     * 年级科目
     */
    public Map<String, Object> jsonGradeAndSub(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                List<SubAndGrade> grades = new ArrayList<SubAndGrade>();
                JSONArray grade_list = object.optJSONArray("grade_list");
                for (int i = 0; i < grade_list.length(); i++) {
                    JSONObject grade = grade_list.optJSONObject(i);
                    SubAndGrade andGrade = new SubAndGrade();
                    andGrade.id = grade.optString("cs");
                    andGrade.name = grade.optString("cname");
                    grades.add(andGrade);
                }
                List<SubAndGrade> subs = new ArrayList<SubAndGrade>();
                JSONArray subject_list = object.optJSONArray("subject_list");
                for (int i = 0; i < subject_list.length(); i++) {
                    JSONObject grade = subject_list.optJSONObject(i);
                    SubAndGrade andGrade = new SubAndGrade();
                    andGrade.id = grade.optString("cs");
                    andGrade.name = grade.optString("cname");
                    andGrade.url = grade.optString("picfile");
                    subs.add(andGrade);
                }
                map.put("grades", grades);
                map.put("subs", subs);
            }
            map.put("status", status);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 年级科目单个
     */
    public Map<String, Object> jsonGradeAndSubOne(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject grade = object.optJSONObject("grade_list");
                SubAndGrade andGrade = new SubAndGrade();
                andGrade.id = grade.optString("gradeId");
                andGrade.name = grade.optString("gradeName");
                JSONObject subject = object.optJSONObject("subject_list");
                SubAndGrade andSubject = new SubAndGrade();
                andSubject.id = subject.optString("subjectId");
                andSubject.name = subject.optString("subjectName");
                andSubject.url = subject.optString("url");
                map.put("grades", andGrade);
                map.put("subs", andSubject);
            }
            map.put("status", status);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 头像上传,返回status,zhuce字段
     */
    public Map<String, Object> jsonAvatar(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询学校
     */
    public School jsonSchool(String resutl) {
        School school = null;
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String userId = object.optString("userId");
            String schoolCode = object.optString("schoolCode");
            String schoolName = object.optString("schoolName");
            String userPessmion = object.optString("userPessmion");
            String zhuce = object.optString("zhuce");
            school = new School(status, userId, schoolCode, schoolName, userPessmion, zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return school;
    }

    /**
     * 通知语音发布
     */
    public Map<String, Object> jsonNoticeUpload(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                String tape = object.optString("tape");
                map.put("tape", tape);
            }
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 发布通知第一步
     */
    public Map<String, Object> jsonNoticeOne(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String noticeid = object.optString("noticeid");
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("noticeid", noticeid);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 发布通知--》》班级列表
     */
    public Map<String, Object> jsonNoticeClass(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<NoticeClass> lists = null;
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            if (status == 1) {
                lists = new ArrayList<NoticeClass>();
                JSONArray data = object.optJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.optJSONObject(i);
                    String cid = jsonObject.optString("cid");
                    String classname = jsonObject.optString("classname");
                    String tea_mun = jsonObject.optString("tea_mun");
                    String stu_mun = jsonObject.optString("stu_mun");
                    String p_mun = jsonObject.optString("p_mun");
                    NoticeClass noticeClass = new NoticeClass(cid, classname, tea_mun, stu_mun, p_mun, false);
                    lists.add(noticeClass);
                }
            }
            map.put("status", status);
            map.put("data", lists);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 发布通知--》》发布对象
     */
    public Map<String, Object> jsonNoticeAmount(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TeaStuPat> t_lists = null;
        List<TeaStuPat> s_lists = null;
        List<TeaStuPat> p_lists = null;
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            if (status == 1) {
                int tea_count = object.optInt("tea_count");
                int student_count = object.optInt("student_count");
                int parent_count = object.optInt("parent_count");
                if (tea_count != 0) {
                    t_lists = new ArrayList<TeaStuPat>();
                    // 老师列表
                    JSONArray tea_data = object.optJSONArray("tea_data");
                    for (int i = 0; i < tea_data.length(); i++) {
                        JSONObject jsonObject = tea_data.getJSONObject(i);

                        String truename = jsonObject.optString("truename");
                        String photo = jsonObject.optString("photo");
                        String userid = jsonObject.optString("userid");
                        String subject = jsonObject.optString("subject");
                        if (subject == null) {
                            subject = "";
                        }
                        TeaStuPat teaStuPat = new TeaStuPat(userid, truename, photo, subject);
                        t_lists.add(teaStuPat);
                    }
                }

                if (student_count != 0) {
                    s_lists = new ArrayList<TeaStuPat>();
                    if (parent_count != 0) {
                        // 家长列表
                        p_lists = new ArrayList<TeaStuPat>();
                    }
                    // 学生列表
                    JSONArray sp_data = object.optJSONArray("sp_data");
                    for (int i = 0; i < sp_data.length(); i++) {
                        JSONObject jsonObject = sp_data.optJSONObject(i);
                        JSONObject student = jsonObject.optJSONObject("student");
                        JSONObject parent = jsonObject.optJSONObject("parent");
                        if (student != null) {
                            String truename = student.optString("truename");
                            String photo = student.optString("photo");
                            String userid = student.optString("userid");
                            TeaStuPat stu = new TeaStuPat(userid, truename, photo);
                            s_lists.add(stu);
                        }
                        if (parent != null) {
                            String truename = student.optString("truename");
                            String photo = student.optString("photo");
                            String userid = student.optString("userid");
                            TeaStuPat pat = new TeaStuPat(userid, truename, photo);
                            p_lists.add(pat);
                        }
                    }

                }
            }
            map.put("status", status);
            map.put("t_lists", t_lists);
            map.put("p_lists", p_lists);
            map.put("s_lists", s_lists);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 通知列表
     */
    public Map<String, Object> jsonNoticeLists(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Notice> lists = null;
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            int page = object.optInt("page");
            boolean harmore = false;
            if (status == 1) {
                harmore = object.optBoolean("harmore");
                lists = new ArrayList<Notice>();
                JSONArray data = object.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.optJSONObject(i);
                        String fid = jsonObject.optString("fid");
                        String fcontent = jsonObject.optString("fcontent");
                        long ftime = jsonObject.optLong("sendtime");
                        String ftitle = jsonObject.optString("ftitle");
                        int total = jsonObject.optInt("total");
                        int state = jsonObject.optInt("state");
                        String agree = jsonObject.optString("agree");
                        String disargee = jsonObject.optString("disargee");
                        String count = jsonObject.optString("count");
                        Notice notice = new Notice(fid, ftime, fcontent, ftitle, state, agree, disargee, total, count);
                        if (!"0".equals(count)) {
                            count = count.replace("{", "").replace("}", "");
                            String[] strs = count.split(",");
                            for (String s : strs) {
                                s = s.replace("\"", "").replace("\"", "");
                                String[] sp = s.split(":");
                                if ("0".equals(sp[0])) {
                                    // 点击否认按钮（不同意）
                                    notice.setDisargee_num(Integer.parseInt(sp[1]));
                                }
                                if ("1".equals(sp[0])) {
                                    // 点击确定按钮（同意）
                                    notice.setAgree_num(Integer.parseInt(sp[1]));
                                }
                                if ("2".equals(sp[0])) {
                                    // 未选择，只查看了通知的人
                                    notice.setNoselect_num(Integer.parseInt(sp[1]));
                                }
                                if ("4".equals(sp[0])) {
                                    // 全部查看了通知的人
                                    notice.setNum(Integer.parseInt(sp[1]));
                                }
                            }
                        }
                        lists.add(notice);
                    }
                }
            }
            map.put("status", status);
            map.put("page", page);
            map.put("harmore", harmore);
            map.put("noticeLists", lists);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 通知详情与用户反馈
     */
    public NoticeDetails jsonDetails(String resutl) {
        NoticeDetails details = null;
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            String user_name = object.optString("user_name");
            if (status == 1) {
                List<FeedbackDetails> lists = new ArrayList<FeedbackDetails>();
                // 解析学生、家长
                JSONArray data = object.optJSONArray("data");/// 学生家长（单数学生双数家长）
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.optJSONObject(i);
                        if (jsonObject != null) {
                            String userid = jsonObject.optString("userid");
                            String truename = jsonObject.optString("truename");
                            String photo = jsonObject.optString("photo");
                            String mobile = jsonObject.optString("mobile");
                            String agree = jsonObject.optString("agree");
                            String value1 = "";
                            int tsp = 0;
                            if (i % 2 == 1) {
                                tsp = 1;// 家长
                            }
                            FeedbackDetails feedbackDetails = new FeedbackDetails(userid, truename, mobile, photo,
                                    agree, value1, tsp);
                            if (!"3".equals(agree))
                                lists.add(feedbackDetails);
                        }

                    }
                }
                // 老师

                JSONArray tea_data = object.optJSONArray("tea_data");
                if (tea_data != null) {
                    for (int i = 0; i < tea_data.length(); i++) {
                        JSONObject jsonObject = tea_data.optJSONObject(i);
                        if (jsonObject != null) {
                            String userid = jsonObject.optString("userid");
                            String truename = jsonObject.optString("truename");
                            String photo = jsonObject.optString("photo");
                            String mobile = jsonObject.optString("mobile");
                            String agree = jsonObject.optString("agree");
                            String value1 = jsonObject.optString("value1");
                            if (value1 == null)
                                value1 = "";
                            int tsp = 2;
                            FeedbackDetails feedbackDetails = new FeedbackDetails(userid, truename, mobile, photo,
                                    agree, value1, tsp);
                            if (!"3".equals(agree))
                                lists.add(feedbackDetails);
                        }
                    }
                }
                // 通知
                JSONObject inform = object.optJSONObject("inform");
                String fcontent = inform.optString("fcontent");
                String ftitle = inform.optString("ftitle");
                long ftime = inform.optLong("ftime");
                String agree = inform.optString("agree");
                String disargee = inform.optString("disargee");
                String tapeurl = inform.optString("tapeurl");
                // 发布时间
                long send = inform.optLong("sendtime");
                details = new NoticeDetails(fcontent, ftitle, ftime, send, lists, status, zhuce, agree, disargee,
                        tapeurl, user_name);
                return details;
            }
            if (status == 0) {
                // 解析通知
                JSONObject data = object.optJSONObject("data");
                String fcontent = data.optString("fcontent");
                String ftitle = data.optString("ftitle");
                long ftime = data.optLong("ftime");
                String agree = data.optString("agree");
                String disargee = data.optString("disargee");
                String tapeurl = data.optString("tapeurl");
                long sendtime = data.optLong("sendtime");
                details = new NoticeDetails(fcontent, ftitle, ftime, sendtime, status, zhuce, agree, disargee, tapeurl,
                        user_name);
                return details;
            }
            details = new NoticeDetails(status, zhuce, user_name);
            return details;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }

    /**
     * 班级列表
     */
    public Map<String, Object> jsonClassLists(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                List<SubAndGrade> create = new ArrayList<SubAndGrade>();
                JSONArray z_class = object.optJSONArray("z_class");
                if (z_class != null) {
                    for (int i = 0; i < z_class.length(); i++) {
                        JSONObject grade = z_class.optJSONObject(i);
                        SubAndGrade andGrade = new SubAndGrade();
                        andGrade.id = grade.optString("cid");
                        andGrade.name = grade.optString("classname");
                        create.add(andGrade);
                    }
                }
                List<SubAndGrade> add = new ArrayList<SubAndGrade>();
                JSONArray j_class = object.optJSONArray("j_class");
                if (j_class != null) {
                    for (int i = 0; i < j_class.length(); i++) {
                        JSONObject grade = j_class.optJSONObject(i);
                        SubAndGrade andGrade = new SubAndGrade();
                        andGrade.id = grade.optString("cid");
                        andGrade.name = grade.optString("classname");
                        add.add(andGrade);
                    }
                }

                List<SubAndGrade> sys = new ArrayList<SubAndGrade>();
                JSONArray x_class = object.optJSONArray("x_class");
                if (x_class != null) {
                    Log.i("test", "x_class=" + x_class);
                    for (int i = 0; i < j_class.length(); i++) {
                        JSONObject grade = j_class.optJSONObject(i);
                        SubAndGrade andGrade = new SubAndGrade();
                        andGrade.id = grade.optString("cid");
                        andGrade.name = grade.optString("classname");
                        add.add(andGrade);
                    }
                }
                map.put("z_class", create);
                map.put("j_class", add);
                map.put("x_class", sys);
            }
            map.put("status", status);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 班级历史列表
     */
    public Map<String, Object> jsonClassHistorys(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                List<HistoryType> types = new ArrayList<HistoryType>();
                // 当前自定义班级
                JSONArray z_class = object.optJSONArray("z_class");
                if (z_class != null) {
                    List<HistoryClass> zLists = new ArrayList<HistoryClass>();
                    for (int i = 0; i < z_class.length(); i++) {
                        JSONObject jsonObject = z_class.optJSONObject(i);
                        String classname = jsonObject.optString("classname");
                        String cid = jsonObject.optString("cid");
                        long startime = jsonObject.optLong("startime");
                        HistoryClass historyClass = new HistoryClass(classname, cid, startime, 0, "", 0, 0);
                        zLists.add(historyClass);
                    }
                    HistoryType type = new HistoryType("当前自定义班级", zLists, 0);
                    if (zLists != null && zLists.size() > 0)
                        types.add(type);
                }
                // 当前加入班级
                JSONArray j_class = object.optJSONArray("j_class");
                if (j_class != null) {
                    List<HistoryClass> jLists = new ArrayList<HistoryClass>();
                    for (int i = 0; i < j_class.length(); i++) {
                        JSONObject jsonObject = j_class.optJSONObject(i);
                        String classname = jsonObject.optString("classname");
                        String cid = jsonObject.optString("cid");
                        String userid = jsonObject.optString("userid");
                        long startime = jsonObject.optLong("startime");
                        long endtime = jsonObject.optLong("endtime");
                        long jointime = jsonObject.optLong("jointime");
                        long quitetime = jsonObject.optLong("quitetime");
                        HistoryClass historyClass = new HistoryClass(classname, cid, startime, endtime, userid,
                                jointime, quitetime);
                        jLists.add(historyClass);
                    }
                    HistoryType type = new HistoryType("当前加入班级", jLists, 1);
                    if (jLists != null && jLists.size() > 0)
                        types.add(type);
                }
                // 用户历史自定义班级
                JSONArray z_history = object.optJSONArray("z_history");
                if (z_history != null) {
                    List<HistoryClass> zhLists = new ArrayList<HistoryClass>();
                    for (int i = 0; i < z_history.length(); i++) {
                        JSONObject jsonObject = z_history.optJSONObject(i);
                        String classname = jsonObject.optString("classname");
                        String cid = jsonObject.optString("cid");
                        long startime = jsonObject.optLong("startime");
                        long endtime = jsonObject.optLong("endtime");
                        HistoryClass historyClass = new HistoryClass(classname, cid, startime, endtime, "", 0, 0);
                        zhLists.add(historyClass);
                    }
                    HistoryType type = new HistoryType("历史自定义班级", zhLists, 2);
                    if (zhLists != null && zhLists.size() > 0)
                        types.add(type);
                }

                // 历史加入班级
                JSONArray j_history = object.optJSONArray("j_history");
                if (j_history != null) {
                    List<HistoryClass> jhLists = new ArrayList<HistoryClass>();
                    for (int i = 0; i < j_history.length(); i++) {
                        JSONObject jsonObject = j_history.optJSONObject(i);
                        String classname = jsonObject.optString("classname");
                        String cid = jsonObject.optString("cid");
                        String userid = jsonObject.optString("userid");
                        long startime = jsonObject.optLong("startime");
                        long endtime = jsonObject.optLong("endtime");
                        long jointime = jsonObject.optLong("jointime");
                        long quitetime = jsonObject.optLong("quitetime");
                        HistoryClass historyClass = new HistoryClass(classname, cid, startime, endtime, userid,
                                jointime, quitetime);
                        jhLists.add(historyClass);
                    }
                    HistoryType type = new HistoryType("历史加入班级", jhLists, 3);
                    if (jhLists != null && jhLists.size() > 0)
                        types.add(type);
                }
                map.put("history", types);
            }
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 加入班级--》》搜索班级信息
     */
    public Map<String, Object> jsonAddQeuryClass(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject data = object.optJSONObject("data");
                String cid = data.optString("cid");
                String classname = data.optString("classname");
                String code = data.optString("code");
                String content = data.optString("content");
                long startime = data.optLong("startime");
                String url = data.optString("url");
                String erweima = data.optString("erweima");
                map.put("cid", cid);
                map.put("classname", classname);
                map.put("code", code);
                map.put("content", content);
                map.put("startime", startime);
                map.put("url", url);
                map.put("erweima", erweima);
            }
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 班级详情
     */
    public Map<String, Object> jsonClassDetails(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject class_data = object.optJSONObject("class_data");
                String cid = class_data.optString("cid");
                String classname = class_data.optString("classname");
                String code = class_data.optString("code");
                String content = class_data.optString("content");
                long startime = class_data.optLong("startime");
                String url = class_data.optString("url");
                String erweima = class_data.optString("erweima");
                String type = class_data.optString("type");

                map.put("cid", cid);
                map.put("classname", classname);
                map.put("code", code);
                map.put("content", content);
                map.put("startime", startime);
                map.put("url", url);
                map.put("erweima", erweima);
                map.put("type", type);

                int tea_count = object.optInt("tea_count");
                int student_count = object.optInt("student_count");
                int parent_count = object.optInt("parent_count");

                List<ClassDetails> lists = new ArrayList<ClassDetails>();
                if (tea_count != 0) {
                    // 老师
                    List<ClassPersionDetails> tea_details = new ArrayList<ClassPersionDetails>();
                    JSONArray tea_data = object.optJSONArray("tea_data");
                    for (int i = 0; i < tea_data.length(); i++) {
                        JSONObject jsonObject = tea_data.optJSONObject(i);
                        if (jsonObject != null) {
                            String truename = jsonObject.optString("truename");
                            String photo = jsonObject.optString("photo");
                            String userid = jsonObject.optString("userid");
                            String subject = jsonObject.optString("subject");
                            ClassPersionDetails details = new ClassPersionDetails(userid, truename, photo, subject);
                            tea_details.add(details);
                        }
                    }
                    ClassDetails classDetails = new ClassDetails("老师", 0, tea_details, tea_count, student_count,
                            parent_count);
                    classDetails.setCid(cid);
                    if (tea_details.size() > 0)
                        lists.add(classDetails);
                }
                if (student_count != 0) {

                    List<ClassPersionDetails> stu_details = new ArrayList<ClassPersionDetails>();
                    List<ClassPersionDetails> pat_details = new ArrayList<ClassPersionDetails>();
                    JSONArray sp_data = object.optJSONArray("sp_data");
                    for (int i = 0; i < sp_data.length(); i++) {
                        JSONObject jsonObject = sp_data.optJSONObject(i);
                        // 学生
                        JSONObject student = jsonObject.optJSONObject("student");
                        if (student != null) {
                            String truename = student.optString("truename");
                            String photo = student.optString("photo");
                            String userid = student.optString("userid");
                            ClassPersionDetails details = new ClassPersionDetails(userid, truename, photo, "");
                            stu_details.add(details);
                        }
                        // 家长
                        JSONObject parent = jsonObject.optJSONObject("parent");
                        if (parent != null) {
                            String truename = student.optString("truename");
                            String photo = student.optString("photo");
                            String userid = student.optString("userid");
                            ClassPersionDetails details = new ClassPersionDetails(userid, truename, photo, "");
                            pat_details.add(details);
                        }
                    }
                    ClassDetails classDetails = new ClassDetails("学生", 1, stu_details, tea_count, student_count,
                            parent_count);
                    classDetails.setCid(cid);
                    if (stu_details.size() > 0)
                        lists.add(classDetails);
                    ClassDetails pat_classDetails = new ClassDetails("家长", 2, pat_details, tea_count, student_count,
                            parent_count);
                    pat_classDetails.setCid(cid);
                    if (pat_details.size() > 0)
                        lists.add(pat_classDetails);

                }
                map.put("lists", lists);
                // 班级信息

            }
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 班级管理
     */
    public Map<String, Object> jsonClassManage(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject class_data = object.optJSONObject("class_data");
                String cid = class_data.optString("cid");
                String classname = class_data.optString("classname");
                String content = class_data.optString("content");
                long startime = class_data.optLong("startime");
                String checktype = class_data.optString("checktype");
                map.put("cid", cid);
                map.put("classname", classname);
                map.put("content", content);
                map.put("startime", startime);
                map.put("checktype", checktype);
                List<ClassPersionDetails> lists = new ArrayList<ClassPersionDetails>();
                // 老师
                JSONArray tea_data = object.optJSONArray("tea_data");
                if (tea_data != null) {
                    for (int i = 0; i < tea_data.length(); i++) {
                        JSONObject jsonObject = tea_data.optJSONObject(i);
                        if (jsonObject != null) {
                            String truename = jsonObject.optString("truename");
                            String photo = jsonObject.optString("photo");
                            String userid = jsonObject.optString("userid");
                            String subject = jsonObject.optString("subject");
                            ClassPersionDetails details = new ClassPersionDetails(userid, truename, photo, subject);
                            details.setCid(cid);
                            lists.add(details);
                        }
                    }
                }
                JSONArray stu_data = object.optJSONArray("stu_data");
                if (stu_data != null) {
                    for (int i = 0; i < stu_data.length(); i++) {
                        JSONObject student = stu_data.optJSONObject(i);
                        // 学生
                        if (student != null) {
                            String truename = student.optString("truename");
                            String photo = student.optString("photo");
                            String userid = student.optString("userid");
                            ClassPersionDetails details = new ClassPersionDetails(userid, truename, photo, "");
                            details.setCid(cid);
                            lists.add(details);
                        }
//						// 家长
//						JSONObject parent = jsonObject.optJSONObject("parent");
//						if (parent != null) {
//							String truename = student.optString("truename");
//							String photo = student.optString("photo");
//							String userid = student.optString("userid");
//							ClassPersionDetails details = new ClassPersionDetails(userid, truename, photo, "");
//							lists.add(details);
//						}
                    }
                }
                map.put("lists", lists);
                // 班级信息
            }
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (

                JSONException e)

        {
            e.printStackTrace();
        }
        return map;
    }
}
