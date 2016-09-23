package com.work.student.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maguitao on 2016/9/22.
 * 说明 作业列表
 */
public class JobDetail implements Serializable {
    public static final int TYPE_PANDUAN = 20;
    public static final int TYPE_DANXUAN = 21;
    public static final int TYPE_DUOXUAN = 23;

    @Override
    public String toString() {
        return "JobDetail{" +
                "selectPosition=" + selectPosition +
                ", selectionPositions=" + selectionPositions +
                ", id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", tpid='" + tpid + '\'' +
                ", iid='" + iid + '\'' +
                ", ifrom='" + ifrom + '\'' +
                ", questype=" + questype +
                ", autojudge='" + autojudge + '\'' +
                ", itemflag='" + itemflag + '\'' +
                ", score=" + score +
                ", orderidx='" + orderidx + '\'' +
                ", ibid='" + ibid + '\'' +
                ", body='" + body + '\'' +
                ", explain='" + explain + '\'' +
                ", cfrom='" + cfrom + '\'' +
                ", title='" + title + '\'' +
                ", sum=" + sum +
                ", index=" + index +
                ", option=" + option +
                '}';
    }

    public String getCommitanswer() {
        return commitanswer;
    }

    public void setCommitanswer(String commitanswer) {
        this.commitanswer = commitanswer;
    }

    private String commitanswer;
    public static final int TYPE_TINGLI = 10039;
    public static final int TYPE_TIANKONG = 10018;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    private int selectPosition = -1;//记录单选的位置
    private List<String> selectionPositions;//记录多选的位置

    public List getSelectionPositions() {
        return selectionPositions;
    }

    public void setSelectionPositions(List selectionPositions) {
        this.selectionPositions = selectionPositions;
    }

    private int indexIntotal;
    /**
     * id : 113
     * userid : 241
     * tpid : 56
     * iid : 367fb67b-ba95-11e4-be86-f01faff19bf6
     * ifrom : 0
     * questype : 21
     * autojudge : 1
     * itemflag : 0
     * score : 6.00
     * orderidx : 1
     * ibid : 367fb67b-ba95-11e4-be86-f01faff19bf6
     * body :
     * 598256feff0c5c0f59da8eab9ad8m572867d06b2162957bee4e2dff0c740376848fd052a88def7ebf662f629b72697ebf76844e0090e85206ff0c82e5547d4e2d7bee57084e2d5fc3ff0c52194ed64e0e7bee5e9576848ddd79bb 662fff08   ff09
     * </div>
     * explain :
     * 5206522b6c4251fa5f53y=3.05548c5f53y=65f67684x7684503cff0c524d976290a34e2a53d66b636570ff0c540e9762768490a34e2a53d68d1f6570ff0c7136540e8ba17b975b834eec4e4b95f476848ddd79bb.
     * 800370b9ff1a4e8c6b2151fd657076846c42503c.</div>
     * cfrom : 20155c4a6d596c5f77016cf0987a53bf4e5d5e747ea74e0a5b66671f671f4e2d8054800365705b668bd55377ff085e2689e36790ff09
     * title : 53559879900962e99898
     * option : [{"option":"3.5m","orderidx":"1"},{"option":"4m","orderidx":"2"},{"option":"4.5m","orderidx":"3"},{"option":"4.6m","orderidx":"4"}]
     * sum : 27
     * index : 1
     */

    private String id;
    private String userid;
    private String tpid;
    private String iid;
    private String ifrom;
    private int questype;
    private String autojudge;
    private String itemflag;
    private float score;//分值
    private String orderidx;
    private String ibid;
    private String body;
    private String explain;
    private String cfrom;
    private String title;
    private int sum;
    private int index;
    private String voiceurl;
    private int ino;//标志每一道题目
    private long statrttime;
    private long endtime;

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

    public String getVoiceurl() {
        return voiceurl;
    }

    public void setVoiceurl(String voiceurl) {
        this.voiceurl = voiceurl;
    }

    /**
     * option : 3.5m
     * orderidx : 1
     */

    private List<OptionBean> option;

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

    public int getQuestype() {
        return questype;
    }

    public void setQuestype(int questype) {
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getOrderidx() {
        return orderidx;
    }

    public void setOrderidx(String orderidx) {
        this.orderidx = orderidx;
    }

    public String getIbid() {
        return ibid;
    }

    public void setIbid(String ibid) {
        this.ibid = ibid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getCfrom() {
        return cfrom;
    }

    public void setCfrom(String cfrom) {
        this.cfrom = cfrom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<OptionBean> getOption() {
        return option;
    }

    public void setOption(List<OptionBean> option) {
        this.option = option;
    }

    public void setIndexIntotal(int indexIntotal) {
        this.indexIntotal = indexIntotal;
    }

    public int getIndexIntotal() {
        return indexIntotal;
    }

    public static class OptionBean implements Serializable {
        private String option;
        private String orderidx;

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getOrderidx() {
            return orderidx;
        }

        public void setOrderidx(String orderidx) {
            this.orderidx = orderidx;
        }
    }

    public static List<JobDetail> ParseData(String json) {
        try {
            List<JobDetail> lsit;
            JSONObject jsonObject = new JSONObject(json);
            int status = (int) jsonObject.get("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                lsit = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<JobDetail>>() {
                }.getType());
                return lsit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
