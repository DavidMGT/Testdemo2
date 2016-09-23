package com.work.teacher.work;

import android.content.Intent;
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
import com.work.teacher.more.notice.SelectClassActivity;
import com.work.teacher.tool.Constants;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.LogUtils;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedHashMap;

public class QuestionNaireEditActivity extends BaseActivity implements View.OnClickListener {
    public LinkedHashMap<String, String> map = new LinkedHashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_naire_edit);
    }

    QuestionnaireDetailBean bean;

    @Override
    public void initData() {
        mHandler.sendEmptyMessage(LOADDING_SUCESS);
        bean = Constants.questionnaireDetailBean;
        updateUI();
    }

    private void updateUI() {
        if (bean != null && bean.getTask() != null) {
            for (int i = 0; i < bean.getTask().size(); i++) {
                expandableListView.expandGroup(i);
            }
            meAdapter.notifyDataSetChanged();
        }
    }

    private ExpandableListView expandableListView;
    private MEAdapter meAdapter;

    private Button bt_add;
    private TextView tv_add_ques;

    @Override
    public void initView() {
        mTvTitle.setText("问卷调查");
        top_btn.setText("完成");
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        top_btn.setOnClickListener(this);
        tv_add_ques = (TextView) findViewById(R.id.tv_add_ques);
        if (bean == null || bean.getTask() == null) {
            tv_add_ques.setVisibility(View.VISIBLE);
        } else {
            tv_add_ques.setVisibility(View.GONE);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_btn:
                Intent intent = new Intent();
                intent.putExtra("isFromAddQuestion", true);
                bean.setCtime(new Date().getTime() + "");
                bean.setSendtime(new Date().getTime() + "");
                bean.setSendid(IBaes.USER_ID);
                Type type = new TypeToken<QuestionnaireDetailBean>() {
                }.getType();
                String jsonstr = new Gson().toJson(bean, type);
                LogUtils.debug("jsonstr" + jsonstr);
                intent.setClass(mContext, SelectClassActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_add:
                startActivity(new Intent(mContext, AddQuestionnarieStep2Activity.class));
                finish();
                break;
        }
    }

    class MEAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            if (bean == null || bean.getTask() == null) {
                return 0;
            }
            return bean.getTask().size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            LogUtils.debug("bean.getTask().get(groupPosition).getOption().size()" + bean.getTask().get(groupPosition).getOption().size());
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
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.textview_title, null);
            }
            TextView tv_question_name = (TextView) convertView.findViewById(R.id.tv_question_name);
            Button bt_delete = (Button) convertView.findViewById(R.id.bt_delete);
            bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bean.getTask().remove(groupPosition);
                    meAdapter.notifyDataSetChanged();
                }
            });
            tv_question_name.setText(groupPosition + 1 + ":" + bean.getTask().get(groupPosition).getTitle());
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
