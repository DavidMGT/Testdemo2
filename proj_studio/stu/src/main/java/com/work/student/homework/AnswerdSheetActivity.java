package com.work.student.homework;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.view.BaseActivity;

public class AnswerdSheetActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answerd_sheet);
    }

    @Override
    public void initData() {
        mHandler.sendEmptyMessage(GET_DATA_COMPLETE);
    }

    TextView tv_homework_title, tv_stuname, tv_classname, tv_st_sub, tv_sum_score, tv_sum_time_cost, tv_class_sum_score, tv_average_time_cost;
    Button bt_review_errors, bt_all_answer;

    @Override
    public void initView() {
        mTvTitle.setText("成绩单");
        top_btn.setVisibility(View.GONE);
        tv_homework_title = (TextView) findViewById(R.id.tv_homework_title);
        tv_stuname = (TextView) findViewById(R.id.tv_stuname);
        tv_classname = (TextView) findViewById(R.id.tv_classname);
        tv_st_sub = (TextView) findViewById(R.id.tv_st_sub);
        tv_sum_score = (TextView) findViewById(R.id.tv_sum_score);
        tv_sum_time_cost = (TextView) findViewById(R.id.tv_sum_time_cost);
        tv_class_sum_score = (TextView) findViewById(R.id.tv_class_sum_score);
        tv_average_time_cost = (TextView) findViewById(R.id.tv_average_time_cost);
        bt_all_answer = (Button) findViewById(R.id.bt_all_answer);
        bt_all_answer.setOnClickListener(this);
        bt_review_errors = (Button) findViewById(R.id.bt_review_errors);
        bt_review_errors.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_review_errors:
                //错题回顾

                break;
            case R.id.bt_all_answer:
                //所有答案

                break;
        }
    }
}
