package com.work.student.homework;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.work.student.LoginActivity;
import com.work.student.R;
import com.work.student.bean.JobDetail;
import com.work.student.tool.DateUtil;
import com.work.student.tool.LogUtils;
import com.work.student.tool.TextUtil;
import com.work.student.view.BaseActivity;

import org.apache.commons.collections.map.ListOrderedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 答题卡
 */
public class AnswerSheetActivity extends BaseActivity {
    long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet);
        duration = getIntent().getLongExtra("duration", 0);
        LogUtils.debug("duration=" + duration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_cost_time.setText(DateUtil.formatTime(duration));
    }

    private List<JobDetail> mLists;

    @Override
    public void initData() {
        mLists = HomeWorkActivity.mlist;
        //处理集合的数据
        dealData();

    }

    private ListOrderedMap mMap = new ListOrderedMap();

    public void dealData() {
        int i = 1;
        for (JobDetail jobDetail : mLists) {
            jobDetail.setIndexIntotal(i);
            i++;
            String title = jobDetail.getTitle();
            if (!mMap.containsKey(title)) {
                //如果不包含
                List<JobDetail> list = new ArrayList<>();
                list.add(jobDetail);
                mMap.put(title, list);
            } else {
                //如果已经包含
                List<JobDetail> list = (List<JobDetail>) mMap.get(title);
                list.add(jobDetail);
                mMap.put(title, list);
            }
        }
        ll_manswers.setAdapter(madapter);
        LogUtils.debug("map=" + mMap.size());
        LogUtils.debug("map" + mMap.toString());
        mHandler.sendEmptyMessage(GET_DATA_COMPLETE);
    }

    private TextView tv_cost_time;
    private ListView ll_manswers;
    private MyAnswerAdapter madapter;

    @Override
    public void initView() {
        mTvTitle.setText("答题情况");
        top_btn.setText("交卷");
        tv_cost_time = (TextView) findViewById(R.id.tv_cost_time);
        ll_manswers = (ListView) findViewById(R.id.ll_manswers);
        madapter = new MyAnswerAdapter();

    }

    class MyAnswerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMap.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_answers, null);
            List<JobDetail> list = (List<JobDetail>) mMap.get(mMap.get(position));
            TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_type.setText((String) mMap.get(position));
            GridView gridView = (GridView) convertView.findViewById(R.id.gv_type);
            GvAdapter adapter = new GvAdapter(list);
            gridView.setAdapter(adapter);
            return convertView;
        }

        class GvAdapter extends BaseAdapter {
            private List<JobDetail> list;

            public GvAdapter(List<JobDetail> list1) {
                this.list = list1;
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return mLists.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                JobDetail job = list.get(position);
                convertView = View.inflate(mContext, R.layout.item_anserw_gv, null);
                convertView.setLayoutParams(new ViewGroup.LayoutParams((int) mContext.getResources().getDimension(R.dimen.y100), (int) mContext.getResources().getDimension(R.dimen.y100)));
                TextView tv_answer = (TextView) convertView.findViewById(R.id.tv_answer);
                tv_answer.setText(job.getIndexIntotal() + "");
                int type = job.getQuestype();
                switch (type) {
                    case JobDetail.TYPE_TINGLI:
                    case JobDetail.TYPE_DANXUAN:
                        int selectState = job.getSelectPosition();
                        if (selectState != -1) {
                            tv_answer.setBackgroundResource(R.drawable.drawable_circle_white_bg_green_solid);
                            tv_answer.setTextColor(mContext.getResources().getColor(R.color.green));
                        }
                        break;
                    case JobDetail.TYPE_DUOXUAN:
                        if (job.getSelectionPositions() != null && job.getSelectionPositions().size() > 0) {
                            tv_answer.setBackgroundResource(R.drawable.drawable_circle_white_bg_green_solid);
                            tv_answer.setTextColor(mContext.getResources().getColor(R.color.green));
                        }
                        break;
                    case JobDetail.TYPE_TIANKONG:
                        if (TextUtil.isEmpty(job.getCommitanswer())) {
                            tv_answer.setBackgroundResource(R.drawable.drawable_circle_white_bg_green_solid);
                            tv_answer.setTextColor(mContext.getResources().getColor(R.color.green));
                        }
                        break;
                    default:
                        break;
                }

                return convertView;
            }
        }
    }

}
