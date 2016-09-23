package com.work.student.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maguitao on 2016/9/20.
 * 说明
 */
public class HomeWorkDetailBean implements Serializable {
    public static final int TYPE_DANXUAN = 21;
    public static final int TYPE_DUOXUAN = 23;

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

    /**
     * id : 61
     * tpid : 79
     * iid : 3715519
     * tlx : 21
     * userid : 177
     * type : 1
     * status : 1
     * answer : 2
     * result : 0
     * score : 0
     * starttime : 1472717988
     * endtime : 1472717993
     * elapsed : 5
     * remark : null
     * corrected : null
     * downloaded : 0
     * times : 1
     * ibid : d3b6588d-bad2-11e4-be86-f01faff19bf6
     * body :
     * 59824e0b56fe6240793aff0cAOB4e3a4e0067606746(81ea91cd4e0d8ba1)ff0cO4e3a652f70b9(OA<OBff0cOD=OA)ff0c5728A7aef60ac63024e0091cd7269Gff0c90a34e48 ff08   ff09
     * </div>
     * explain :
     * 75314e8e5728B70b97528529b768465b95411ff0c4e0d80fd786e5b9aff0c62404ee5529b81c2768459275c0f65e06cd5786e5b9aff0c65456760674676847701529b60c551b565e06cd5786e5b9aff0c654590099879A4e0d6b63786eff1b53ea89815728C70b97528529b768465b9541154114e0a5c3180fd4f7f67606746572856fe793a4f4d7f6e5e738861ff0c654590099879B4e0d6b63786eff1b5728B70b97528529b4f7f67606746572856fe793a4f4d7f6e5e738861ff0c5f53529b768465b954114e0e67606746578276f465f6ff0c529b81c26700957fff0c75316760674676845e73886167614ef653ef77e56b6465f6529b67005c0fff0c654590099879C4e0d6b63786eff1b5728D70b960ac63024e004e2a4e0eG5b8c516876f8540c768472694f53ff0c75314e8eOD=OA,81ea652f70b95230529b76844f5c75287ebf76848ddd79bb4e5f4f1a76f87b49ff0c676067464f1a5e738861ff0c654590099879D6b63786e3002
     * 800370b9ff1a6760674676845e73886167614ef676845e947528</div>
     * cfrom : 20155c4a6d596c5f91d1534e7b2c4e5d4e2d5b66521d4e0912670896366bb568c06d4b726974065377ff085e2689e36790ff09
     * title : 53559879900962e99898
     * option : [{"option":".5728B70b97528529b4f7f67606746572856fe793a4f4d7f6e5e738861ff0c4e005b9a662f7701529b7684","orderidx":"1"},{"option":".5728C70b97528529b4e0d53ef80fd4f7f67606746572856fe793a4f4d7f6e5e738861","orderidx":"2"},{"option":".5728B70b97528529b4f7f67606746572856fe793a4f4d7f6e5e738861ff0c6cbf7ad676f465b9541167007701529b","orderidx":"3"},{"option":".5728D70b960ac63024e004e2a4e0eG5b8c516876f8540c768472694f5380fd4f7f67606746572856fe793a4f4d7f6e5e738861","orderidx":"4"}]
     */
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    private String id;
    private String tpid;
    private String iid;
    private int tlx;
    private String userid;
    private String type;
    private String status;
    private String answer;
    private String result;
    private String score;
    private String starttime;
    private String endtime;
    private String elapsed;
    private String remark;
    private String corrected;
    private String downloaded;
    private String times;
    private String ibid;
    private String body;
    private String explain;
    private String cfrom;
    private String title;
    /**
     * option : .5728B70b97528529b4f7f67606746572856fe793a4f4d7f6e5e738861ff0c4e005b9a662f7701529b7684
     * orderidx : 1
     */

    private List<OptionBean> option;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getTlx() {
        return tlx;
    }

    public void setTlx(int tlx) {
        this.tlx = tlx;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCorrected() {
        return corrected;
    }

    public void setCorrected(String corrected) {
        this.corrected = corrected;
    }

    public String getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(String downloaded) {
        this.downloaded = downloaded;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
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

    public List<OptionBean> getOption() {
        return option;
    }

    public void setOption(List<OptionBean> option) {
        this.option = option;
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

    public static List<HomeWorkDetailBean> ParseData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = (int) jsonObject.get("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                return new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HomeWorkDetailBean>>() {
                }.getType());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
