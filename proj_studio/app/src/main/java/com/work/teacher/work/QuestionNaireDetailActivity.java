package com.work.teacher.work;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.teacher.R;
import com.work.teacher.bean.QuestionnaireDetailBean;
import com.work.teacher.tool.DateUtil;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.LogUtils;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class QuestionNaireDetailActivity extends BaseActivity {
    public LinkedHashMap<String, String> map = new LinkedHashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_naire);
    }

    private String wid;
    QuestionnaireDetailBean bean;

    @Override
    public void initData() {
        wid = getIntent().getStringExtra("questionId");
        LogUtils.debug("收到了发送过来的wid" + wid);
        AjaxParams params = mParams.addQuestionnarieDetail(IBaes.USER_ID, wid);
        mFinalHttp.get(IBaes.URL_GET_QUESTIONNARIE_DETAIL, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtils.debug("问卷详情的内容--" + s);
                String msg;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    msg = (String) jsonObject.get("mes");
                    LogUtils.debug("jsonboj.mes" + jsonObject.get("mes"));
                    if (msg.equalsIgnoreCase("success")) {
                        bean = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<QuestionnaireDetailBean>() {
                        }.getType());
                    }
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(s);
                mHandler.sendEmptyMessage(LOADDING_SUCESS);
            }
        });
    }

    private void updateUI() {
        tv_question_title.setText(bean.getWtitle());
        tv_question_des.setText(bean.getWintro());
        tv_endtime.setText("截至时间：" + DateUtil.getSimpleDateFromString(bean.getDtime()));
        tv_question_publisher.setText("发布人：" + bean.getTeachername());
        for (int i = 0; i < bean.getTask().size(); i++) {
            expandableListView.expandGroup(i);
        }
        meAdapter.notifyDataSetChanged();
    }


    private TextView tv_question_title, tv_question_des, tv_question_publisher, tv_endtime;
    private ExpandableListView expandableListView;

    private MEAdapter meAdapter;
    private Button bt_commit;//提交按钮


    @Override
    public void initView() {
        mTvTitle.setText("问卷调查");
        top_btn.setVisibility(View.GONE);
        tv_question_title = (TextView) findViewById(R.id.tv_question_title);
        tv_question_des = (TextView) findViewById(R.id.tv_question_des);
        tv_question_publisher = (TextView) findViewById(R.id.tv_question_publisher);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        expandableListView = (ExpandableListView) findViewById(R.id.ex_questions);
        meAdapter = new MEAdapter();
        expandableListView.setAdapter(meAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
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
            findViewById(R.id.bt_delete).setVisibility(View.GONE);
            tv_question_name.setText(groupPosition + 1 + ":" + bean.getTask().get(groupPosition).getTcontent());
            convertView.setClickable(false);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_question_child, null);
            QuestionnaireDetailBean.TaskBean taskbean = bean.getTask().get(groupPosition);
            TextView tv_selelct_state = (TextView) convertView.findViewById(R.id.iv_selelct_state);
            tv_selelct_state.setText(getChoice(childPosition));
            TextView tv_select_content = (TextView) convertView.findViewById(R.id.tv_select_content);
            tv_select_content.setText(taskbean.getOption().get(childPosition).getContent());
            TextView tv_result = (TextView) convertView.findViewById(R.id.tv_result);//问卷调查的结果展示，如果没有完成，则不显示
            LogUtils.debug("bean.getMystatus()--" + bean.getMystatus());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
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
