package com.work.student.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.student.R;
import com.work.student.bean.QuestionnaireBean;
import com.work.student.bean.QuestionnaireCommittedBean;
import com.work.student.tool.DateUtil;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.tool.ServecHttp;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class QuestionNaireActivity extends BaseActivity {
    public LinkedHashMap<String, String> map = new LinkedHashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_naire);
    }

    private String wid;
    QuestionnaireBean bean;

    @Override
    public void initData() {
        LogUtils.debug("initData执行了");
        wid = getIntent().getStringExtra("questionId");
        LogUtils.debug("收到了发送过来的wid" + wid);
        AjaxParams params = mParams.createQuestionnaireParams(IBaes.userid, wid);
        mFinalHttp.get(IBaes.GET_QUSTIONNAIRE, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtils.debug("问卷详情的内容--" + s);
                String msg;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    msg = (String) jsonObject.get("mes");
                    LogUtils.debug("jsonboj.mes" + jsonObject.get("mes"));
                    if (msg.equalsIgnoreCase("success")) {
                        bean = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<QuestionnaireBean>() {
                        }.getType());
                        LogUtils.debug("解析后的问卷的状态" + bean.getMystatus());//1代表提交成功
                    }
                    updateUI();
                    mLoaddingView.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }
        });
    }

    private void updateUI() {
        tv_question_title.setText(bean.getWtitle());
        tv_question_des.setText(bean.getWintro());
        tv_endtime.setText("截至时间：" + DateUtil.getSimpleDate(bean.getDtime()));
        tv_question_publisher.setText("发布人：" + bean.getTeachername());
        for (int i = 0; i < bean.getTask().size(); i++) {
            expandableListView.expandGroup(i);
        }
        if (bean.getMystatus().equalsIgnoreCase("1")) {
            bt_commit.setVisibility(View.GONE);
        }
        meAdapter.notifyDataSetChanged();
    }


    private TextView tv_question_title, tv_question_des, tv_question_publisher, tv_endtime;
    private ExpandableListView expandableListView;

    private MEAdapter meAdapter;
    private Button bt_commit;//提交按钮
    QuestionnaireCommittedBean questionnaireCommittedBean;

    public void commitQestion() {
        AjaxParams params = mParams.createQuestionnaireCommitParams(IBaes.userid, wid, bean);
        mFinalHttp.get(IBaes.GET_QUSTIONNAIRE_COMMIT, params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                LogUtils.debug("提交问卷的返回结果-------" + s);
                try {
                    JSONObject jsonobj = new JSONObject(s);
                    String msg = jsonobj.getString("mes");
                    String status = (String) jsonobj.get("mystatus");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(mContext, "提交成功" + msg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "提交失败" + msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initData();
            }
        });

    }


    @Override
    public void initView() {
        mTvTitle.setText("问卷调查");
        tv_question_title = (TextView) findViewById(R.id.tv_question_title);
        tv_question_des = (TextView) findViewById(R.id.tv_question_des);
        tv_question_publisher = (TextView) findViewById(R.id.tv_question_publisher);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        bt_commit = (Button) findViewById(R.id.bt_commit_question);
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //问卷提交
                commitQestion();
            }
        });
        expandableListView = (ExpandableListView) findViewById(R.id.ex_questions);
        meAdapter = new MEAdapter();
        expandableListView.setAdapter(meAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                QuestionnaireBean.TaskBean taskbean = bean.getTask().get(groupPosition);
                LogUtils.debug("当前点击的groupPosition=" + groupPosition + ":childPosition=" + childPosition);
                int currentselectposition = taskbean.cureentSelectPosition;
                if (currentselectposition == childPosition) {
                    return false;
                }
                taskbean.setCureentSelectPosition(childPosition);
                meAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    class MEAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            if (bean == null) {
                return 0;
            }
            return bean.getTask().size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return bean.getTask().get(groupPosition).getOption().size();
            //return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return bean.getTask().get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.textview_title, null);
            TextView tv_question_name = (TextView) convertView.findViewById(R.id.tv_question_name);
            tv_question_name.setText(groupPosition + 1 + ":" + bean.getTask().get(groupPosition).getTcontent());
            convertView.setClickable(false);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_question_child, null);
            QuestionnaireBean.TaskBean taskbean = bean.getTask().get(groupPosition);
            TextView tv_selelct_state = (TextView) convertView.findViewById(R.id.iv_selelct_state);
            tv_selelct_state.setText(getChoice(childPosition));
            TextView tv_select_content = (TextView) convertView.findViewById(R.id.tv_select_content);
            tv_select_content.setText(taskbean.getOption().get(childPosition).getContent());
            TextView tv_result = (TextView) convertView.findViewById(R.id.tv_result);//问卷调查的结果展示，如果没有完成，则不显示
            LogUtils.debug("bean.getMystatus()--" + bean.getMystatus());
            int currentposition = taskbean.cureentSelectPosition;
            if (bean.getMystatus().equalsIgnoreCase("1")) {
                //1代表完成问卷
                tv_result.setVisibility(View.VISIBLE);
                float percent = taskbean.getOption().get(childPosition).getPercent();
                LogUtils.debug("taskbean.getOption().get(childPosition).getPercent()" + percent);
                tv_result.setText((int) (percent * 100) + "%");
                int position = taskbean.getMychoose() - 1;
                LogUtils.debug("提交的位置是—" + position);
                if (position == childPosition) {
                    tv_result.setTextColor(0xFF4AC4BC);
                    tv_select_content.setTextColor(0xFF4AC4BC);
                    tv_selelct_state.setTextColor(0xFFFFFFFF);
                    tv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_green_bg);
                } else {
                    tv_result.setTextColor(0xFFCBCBCB);
                    tv_select_content.setTextColor(0xFFCBCBCB);
                    tv_selelct_state.setTextColor(0xFFCBCBCB);
                    tv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_whight_bg);
                }
                //   tv_result.setText(""+taskbean.getOption().get());
            } else {
                if (currentposition == childPosition) {
                    //选择态
                    tv_select_content.setTextColor(0xFF4AC4BC);
                    tv_selelct_state.setTextColor(0xFFFFFFFF);
                    tv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_green_bg);
                } else {
                    tv_select_content.setTextColor(0xFFCBCBCB);
                    tv_selelct_state.setTextColor(0xFFCBCBCB);
                    tv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_whight_bg);
                }
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            if (bean.getMystatus().equalsIgnoreCase("1")) {
                LogUtils.debug("");
                return false;
            }
            return true;
        }
    }

    public String getChoice(int num) {
        switch (num) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
        }
        return "A";
    }
}
