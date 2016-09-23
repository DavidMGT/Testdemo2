package com.work.student.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.work.student.R;
import com.work.student.tool.LogUtils;
import com.work.student.tool.ServecHttp;

import net.tsz.afinal.FinalHttp;

public abstract class BaseActivity extends FragmentActivity {
    public static final int GET_DATA_COMPLETE = 0X001;
    public transient Context mContext;
    private LinearLayout rootView;
    private RelativeLayout rl_header;
    public TextView mTvTitle;
    public FinalHttp mFinalHttp;
    public ServecHttp mParams;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DATA_COMPLETE:
                    mLoaddingView.setVisibility(View.GONE);
                    break;
            }
        }
    };
    public LoadingView mLoaddingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        mParams = new ServecHttp();
        mFinalHttp = new FinalHttp();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public abstract void initData();

    public abstract void initView();

    ;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_activity);
        rootView = (LinearLayout) findViewById(R.id.container);
        rl_header = (RelativeLayout) findViewById(R.id.header);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        rootView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initBaseView();
        initView();
        initData();
    }

    public Button top_btn;

    private void initBaseView() {
        mTvTitle = (TextView) findViewById(R.id.top_text);
        top_btn = (Button) findViewById(R.id.top_btn);
        mLoaddingView = (LoadingView) findViewById(R.id.loadView);
        findViewById(R.id.top_btn).setVisibility(View.GONE);
        findViewById(R.id.top_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
