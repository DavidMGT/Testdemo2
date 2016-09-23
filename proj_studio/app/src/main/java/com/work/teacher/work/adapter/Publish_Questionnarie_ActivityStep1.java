package com.work.teacher.work.adapter;

import android.os.Bundle;

import com.work.teacher.R;
import com.work.teacher.work.BaseActivity;

public class Publish_Questionnarie_ActivityStep1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish__questionnarie__activity_step1);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mTvTitle.setText("问卷发布");
        top_btn.setText("下一步");
    }
}
