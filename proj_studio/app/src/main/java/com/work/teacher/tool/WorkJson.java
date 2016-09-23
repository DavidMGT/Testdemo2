package com.work.teacher.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.LeftTree;
import com.work.teacher.bean.QuestionAnswer;
import com.work.teacher.bean.WorkAuthor;
import com.work.teacher.bean.WorkHistory;
import com.work.teacher.bean.WorkRelseContent;
import com.work.teacher.bean.WorkRelseQuestion;

import android.util.Log;

/**
 * 作业模块json解析
 */
public class WorkJson {


    /**
     * 作业-》侧滑菜单--》》学科查询
     */
    public LeftSubject jsonWorkSubjec(String result) {
        LeftSubject lists = null;
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                JSONArray data = object.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.optJSONObject(i);
                        String id = jsonObject.optString("id");
                        String cname = jsonObject.optString("cname");
                        String pid = jsonObject.optString("pid");
                        String cs = jsonObject.optString("cs");
                        String path = jsonObject.optString("path");
                        lists = new LeftSubject(id, cname, pid, cs, path);
                    }
                }
            } else {
                lists = new LeftSubject("", "暂无", "", "", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 作业-》侧滑菜单--》》教材查询
     */
    public List<LeftSubject> jsonWorkJiao(String result) {
        List<LeftSubject> lists = new ArrayList<LeftSubject>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                JSONArray data = object.optJSONArray("data");
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.optJSONObject(i);
                        String id = jsonObject.optString("id");
                        String cname = jsonObject.optString("cname");
                        String pid = jsonObject.optString("pid");
                        String cs = jsonObject.optString("cs");
                        String path = jsonObject.optString("path");
                        LeftSubject subject = new LeftSubject(id, cname, pid, cs, path);
                        lists.add(subject);
                    }
                }
            } else {
                lists.add(new LeftSubject("", "暂无", "", "", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 章节json
     */
    public List<LeftTree> jsonTree(String result) {
        List<LeftTree> lists = null;
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                lists = new ArrayList<LeftTree>();
                JSONArray data = object.optJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.optJSONObject(i);
                    String id = jsonObject.optString("id");
                    String name = jsonObject.optString("name");
                    String fid = jsonObject.optString("fid");
                    String pid = jsonObject.optString("pid");
                    String layer = jsonObject.optString("layer");
                    String orderidx = jsonObject.optString("orderidx");
                    String visible = jsonObject.optString("visible");
                    int panduan = jsonObject.optInt("panduan");
                    LeftTree tree = new LeftTree(id, name, fid, pid, layer, orderidx, visible, panduan);
                    lists.add(tree);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 章节子层
     */
    public List<LeftTree> jsonTreeOther(String result) {
        List<LeftTree> lists = null;
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                lists = new ArrayList<LeftTree>();
                JSONArray data = object.optJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.optJSONObject(i);
                    String id = jsonObject.optString("id");
                    String name = jsonObject.optString("name");
                    String fid = jsonObject.optString("fid");
                    String pid = jsonObject.optString("pid");
                    int panduan = jsonObject.optInt("panduan");
                    LeftTree tree = new LeftTree(id, name, fid, pid, panduan);
                    lists.add(tree);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 作业发布-》设置属性--》》模式及类别获取
     */
    public Map<String, Object> jsonMode_Type(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                //解析模式
                List<LeftSubject> modes = new ArrayList<LeftSubject>();
                JSONArray mslist = object.optJSONArray("mslist");
                if (mslist != null) {
                    for (int i = 0; i < mslist.length(); i++) {
                        JSONObject jsonObject = mslist.optJSONObject(i);
                        String id = jsonObject.optString("id");
                        String cname = jsonObject.optString("cname");
                        String pid = jsonObject.optString("pid");
                        String cs = jsonObject.optString("cs");
                        String path = jsonObject.optString("path");
                        LeftSubject subject = new LeftSubject(id, cname, pid, cs, path);
                        modes.add(subject);
                    }
                }
                //解析类别
                List<LeftSubject> types = new ArrayList<LeftSubject>();
                JSONArray lxlist = object.optJSONArray("lxlist");
                if (lxlist != null) {
                    for (int i = 0; i < lxlist.length(); i++) {
                        JSONObject jsonObject = lxlist.optJSONObject(i);
                        String id = jsonObject.optString("id");
                        String cname = jsonObject.optString("cname");
                        String pid = jsonObject.optString("pid");
                        String cs = jsonObject.optString("cs");
                        String path = jsonObject.optString("path");
                        LeftSubject subject = new LeftSubject(id, cname, pid, cs, path);
                        types.add(subject);
                    }
                }
                map.put("modes", modes);
                map.put("types", types);
            }
            map.put("status", status);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 作业-》设置属性--》》提交
     */
    public Map<String, Object> jsonsaveProperty(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            if (status == 1) {
                String data = object.optString("data");
                map.put("data", data);
            }
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 作业-》编辑作业--》》获取第一步设置的数据
     */
    public Map<String, Object> jsonAddTwo(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            String zhuce = object.optString("zhuce");
            if (status == 1) {
                //作业创建者资料
                JSONObject data1 = object.optJSONObject("data1");
                String id = data1.optString("id");
                String name = data1.optString("name");
                String authorid = data1.optString("authorid");
                String desc = data1.optString("desc");
                double zhongfen = data1.optDouble("zongfen");
                long createtime = data1.optLong("createtime");
                long lastupdatetime = data1.optLong("lastupdatetime");
                long endtime = data1.optLong("endtime");
                String chapterdid=data1.optString("chapterdid");
                String gradeid=data1.optString("gradeid");
                String subjectid=data1.optString("subjectid");
                WorkAuthor author = new WorkAuthor(id, name, authorid, desc, zhongfen, createtime, lastupdatetime,endtime,gradeid,subjectid,chapterdid);
                map.put("author", author);
                //题目列表
                List<WorkRelseQuestion> wrqs = new ArrayList<WorkRelseQuestion>();
                JSONArray data2 = object.optJSONArray("data2");
                if (data2 != null) {
                    for (int i = 0; i < data2.length(); i++) {
                        JSONObject jsonObject = data2.optJSONObject(i);
                        WorkRelseQuestion question = new WorkRelseQuestion();
                        WorkRelseQuestion workRelseQuestion = new WorkRelseQuestion();
                        workRelseQuestion.title = jsonObject.optString("title");
                        workRelseQuestion.tlx = jsonObject.optString("tlx");
                        workRelseQuestion.fen = jsonObject.optDouble("fen");
                        JSONArray content = jsonObject.optJSONArray("content");
                        if (content != null) {
                            for (int j = 0; j < content.length(); j++) {
                                JSONObject cOb = content.optJSONObject(j);
                                WorkRelseContent relseContent=new WorkRelseContent();
                                relseContent.id = cOb.optString("id");
                                relseContent.userid = cOb.optString("userid");
                                relseContent.tpid = cOb.optString("tpid");
                                relseContent.iid = cOb.optString("iid");
                                relseContent.ifrom = cOb.optString("ifrom");
                                relseContent.questype = cOb.optString("questype");
                                relseContent.autojudge = cOb.optString("autojudge");
                                relseContent.autojudge = cOb.optString("autojudge");
                                relseContent.itemflag = cOb.optString("itemflag");
                                relseContent.score = cOb.optDouble("score");
                                relseContent.orderidx = cOb.optInt("orderidx");
                                JSONObject neirong = cOb.optJSONObject("neirong");
                                QuestionAnswer answer = new QuestionAnswer();
                                answer.id = neirong.optString("id");
                                answer.pid = neirong.optString("pid");
                                answer.userid = neirong.optString("userid");
                                answer.tpid = neirong.optString("tpid");
                                answer.kuid = neirong.optString("kuid");
                                answer.fuserid = neirong.optString("fuserid");
                                answer.ib = neirong.optString("ibid");
                                answer.ino = neirong.optString("ino");
                                answer.gradeid = neirong.optString("gradeid");
                                answer.subjectid = neirong.optString("subjectid");
                                answer.type = neirong.optString("type");
                                answer.status = neirong.optInt("status");
                                answer.autojudge = neirong.optInt("autojudge");
                                answer.itemflag = neirong.optInt("itemflag");
                                answer.body = neirong.optString("body");
                                answer.answertype = neirong.optInt("answertype");
                                answer.optioncnt = neirong.optInt("optioncnt");
                                answer.correctanswer = neirong.optString("correctanswer");
                                answer.explain = neirong.optString("explain");
                                answer.itemflag = neirong.optInt("itemflag");
                                answer.diff = neirong.optInt("diff");
                                answer.usedcnt = neirong.optInt("usedcnt");
                                answer.viewcnt = neirong.optInt("viewcnt");
                                answer.favcnt = neirong.optInt("favcnt");
                                answer.upcnt = neirong.optInt("upcnt");
                                answer.downcnt = neirong.optInt("downcnt");
                                answer.commentcnt = neirong.optInt("commentcnt");
                                answer.isshared = neirong.optInt("isshared");
                                answer.authorid = neirong.optString("authorid");
                                answer.piid = neirong.optString("piid");
                                answer.cfrom = neirong.optString("cfrom");
                                answer.addtime = neirong.optLong("addtime");
                                answer.lastupdatetime = neirong.optLong("lastupdatetime");
                                answer.orderidx = neirong.optInt("orderidx");
                                answer.ifrom = neirong.optInt("ifrom");
                                answer.voiceurl = neirong.optString("voiceurl");
                                answer.voicetime = neirong.optLong("voicetime");
                                JSONArray jsonArray = cOb.optJSONArray("xuanxiang");
                                String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
                                String str="";
                                for (int x = 0; x < jsonArray.length(); x++) {
                                    JSONObject jsonObject1=jsonArray.optJSONObject(x);
                                    String option=jsonObject1.optString("option");
                                    if(!"".equals(option)&&!"null".equals(option)) {
                                        if (x == jsonArray.length() - 1) {
                                            str += letter[x] + " . " + option;
                                        } else {
                                            str += letter[x] + " . " + option + "<br>";
                                        }
                                    }
                                }
                                answer.xuanxiang=str;
                                answer.fidi=chapterdid;
                                relseContent.answer = answer;
                                workRelseQuestion.contents.add(relseContent);
                            }
                        }
                        wrqs.add(workRelseQuestion);
                    }
                }
                map.put("wrqs", wrqs);
            }
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 作业-》题库--》》列表解析
     */
    public Map<String, Object> jsonQuestionList(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                JSONArray data = object.optJSONArray("data");
                if (data != null) {
                    List<QuestionAnswer> answers = new ArrayList<QuestionAnswer>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.optJSONObject(i);
                        QuestionAnswer answer = new QuestionAnswer();
                        answer.id = jsonObject.optString("id");
                        answer.ino = jsonObject.optString("ino");
                        answer.ib = jsonObject.optString("ib");//收藏列表题目的ID
                        answer.gradeid = jsonObject.optString("gradeid");//年级ID
                        answer.subjectid = jsonObject.optString("subjectid");//科目ID
                        answer.type = jsonObject.optString("type");//题目类型
                        answer.status = jsonObject.optInt("status");//0正常显示,1隐藏
                        answer.body = jsonObject.optString("body");//题干内容
                        answer.correctanswer = jsonObject.optString("correctanswer");//正确答案
                        answer.explain = jsonObject.optString("explain");//解析
                        answer.autojudge = jsonObject.optInt("autojudge");
                        answer.itemflag = jsonObject.optInt("itemflag");
                        answer.answertype = jsonObject.optInt("answertype");//答案类型 0 正常类型 1 题干与答案选项合并
                        answer.optioncnt = jsonObject.optInt("optioncnt"); //选项个数
                        answer.diff = jsonObject.optInt("diff"); // 难易度 1容易 2较易 3一般 4较难 5困难
                        answer.usedcnt = jsonObject.optInt("usedcnt");//使用次数
                        answer.viewcnt = jsonObject.optInt("viewcnt");// 浏览次数
                        answer.favcnt = jsonObject.optInt("favcnt");//收藏次数
                        answer.upcnt = jsonObject.optInt("upcnt");//点赞次数
                        answer.downcnt = jsonObject.optInt("downcnt");//点踩次数
                        answer.commentcnt = jsonObject.optInt("commentcnt");//评论次数
                        answer.isshared = jsonObject.optInt("isshared");//0 不共享 1 共享
                        answer.authorid = jsonObject.optString("authorid");//作者ID 公共题库该项为0
                        answer.piid = jsonObject.optString("piid");
                        answer.cfrom = jsonObject.optString("cfrom");
                        answer.addtime = jsonObject.optLong("addtime");//添加时间
                        answer.lastupdatetime = jsonObject.optLong("lastupdatetime");//更新时间
                        answer.orderidx = jsonObject.optInt("orderidx");
                        answer.ifrom = jsonObject.optInt("ifrom");// 0 公共题库 1 个人题库
                        answer.voiceurl = jsonObject.optString("voiceurl");//听力题目音频url地址
                        answer.fid = jsonObject.optString("fid");//教材ID
                        answer.fidi = jsonObject.optString("fdid");//章节ID
                        answer.ibid = jsonObject.optString("ibid");
                        answer.xuanxiang = jsonObject.optString("xuanxiang");
                        answers.add(answer);
                    }
                    map.put("answers", answers);
                }
            }
            boolean harmore = object.optBoolean("harmore");
            map.put("status", status);
            map.put("harmore", harmore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 知识点树结构解析
     */
    public List<LeftTree> jsonKownlege(String result) {
        List<LeftTree> lists = null;
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                lists = new ArrayList<LeftTree>();
                JSONArray data = object.optJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.optJSONObject(i);
                    String id = jsonObject.optString("id");
                    String name = jsonObject.optString("name");
                    String pid = jsonObject.optString("pid");
                    String gradeid = jsonObject.optString("gradeid");
                    String subjectid = jsonObject.optString("subjectid");
                    String orderidx = jsonObject.optString("orderidx");
                    String visible = jsonObject.optString("visible");
                    int panduan = jsonObject.optInt("panduan");
                    LeftTree tree = new LeftTree();
                    tree.setId(id);
                    tree.setName(name);
                    tree.setPid(pid);
                    tree.setFid(gradeid);//保存知识点年级id
                    tree.setLayer(subjectid);//知识点章节id
                    tree.setOrderidx(orderidx);
                    tree.setVisible(visible);
                    tree.setPanduan(panduan);
                    lists.add(tree);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 根据章节id查询章节信息
     */
    public Map<String, Object> jsonSection(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject userid = object.optJSONObject("userid");
                String id = userid.optString("id");
                String name = userid.optString("name");
                String pid = userid.optString("pid");
                String fid = userid.optString("fid");
                map.put("id", id);
                map.put("name", name);
                map.put("pid", pid);
                map.put("fid", fid);
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
     * 作业->图片上传j解析
     */
    public Map<String, Object> jsonWorkUpload(String result) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(result);
            int status = object.optInt("status");
            if (status == 1) {
                String data = object.optString("data");
                map.put("data", data);
            }
            String zhuce = object.optString("zhuce");
            map.put("status", status);
            map.put("zhuce", zhuce);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
    /**作业-》历史列表*/
    public Map<String,Object> jsonHistoryLists(String reasult){
        List<WorkHistory> histories=new ArrayList<WorkHistory>();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            JSONObject object=new JSONObject(reasult);
            int status=object.optInt("status");
            if(status==1){
                JSONArray data=object.optJSONArray("data");
                for (int i=0;i<data.length();i++){
                    JSONObject jsonObject=data.optJSONObject(i);
                    WorkHistory history=new WorkHistory();
                    history.id=jsonObject.optString("id");
                    history.name=jsonObject.optString("name");
                    history.createtime=jsonObject.optLong("createtime");
                    history.endtime=jsonObject.optLong("endtime");
                    history.type=jsonObject.optString("type");
                    history.status=jsonObject.optInt("status");
                    history.total=jsonObject.optInt("total");
                    history.chapterdid=jsonObject.optString("chapterdid");
                    history.gradeid=jsonObject.optString("gradeid");
                    history.judge=jsonObject.optString("judge");
                    history.tp_share=jsonObject.optInt("tp_share");
                    histories.add(history);
                }
            }
            boolean  harmore = object.optBoolean("harmore");
            map.put("status",status);
            map.put("histories",histories);
            map.put("harmore",harmore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
