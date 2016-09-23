package com.work.student.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.work.student.R;
import com.work.student.bean.JobDetail;
import com.work.student.fragment.FragmentFactory;
import com.work.student.tool.HomeWorkTools;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.view.BaseActivity;
import com.work.student.view.CustomDialog;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeWorkActivity extends BaseActivity implements View.OnClickListener {

    String tpid;
    String title;
    long starttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starttime = System.currentTimeMillis();
        mlist = new ArrayList<>();//每次进来要初始化
        setContentView(R.layout.activity_homeworkdetail);
    }

    public static List<JobDetail> mlist;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void initData() {
        tpid = getIntent().getStringExtra("tpid");
        title = getIntent().getStringExtra("title");
        LogUtils.debug("HomeWorkActivity tpid=" + tpid);
        mTvTitle.setText(title);
        AjaxParams params = mParams.createHomeWorkDetailtParams(IBaes.userid, tpid);
        mFinalHttp.get(IBaes.GET_HOME_WORK_DETAIL, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtils.debug("GET_HOME_WORK_DETAIL---" + s);
                mlist = JobDetail.ParseData(s);
                if (mlist == null || mlist.size() == 0) {
                    Toast.makeText(mContext, "获取失败", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                LogUtils.debug("mlist。size（）" + mlist.size());
                tv_numbers.setText(1 + "/" + mlist.size());
                mLoaddingView.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                vp_answers.setCurrentItem(0);
                super.onSuccess(s);
            }
        });
    }

    private TextView tv_numbers, tv_answer_station;
    private ViewPager vp_answers;
    private MyFragmentPagerAdapter mAdapter;
    private Button bt_commit;

    @Override
    public void initView() {
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setText("反馈");
        top_btn.setOnClickListener(this);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(this);
        tv_answer_station = (TextView) findViewById(R.id.tv_answer_station);
        tv_answer_station.setOnClickListener(this);
        tv_numbers = (TextView) findViewById(R.id.tv_numbers);
        vp_answers = (ViewPager) findViewById(R.id.vp_answers);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vp_answers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_numbers.setText(position + 1 + "/" + mlist.size());
                if (position == mlist.size() - 1) {
                    bt_commit.setVisibility(View.VISIBLE);
                } else {
                    bt_commit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_answers.setAdapter(mAdapter);

    }

    CustomDialog dialog;


    public void showDialog(long duration, long starttime) {
        dialog = new CustomDialog(mContext);
        dialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "setOnNegativeListener", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(mContext, "setOnPositiveListener", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        long duration = System.currentTimeMillis() - starttime;
        switch (v.getId()) {
            case R.id.bt_commit:
                Toast.makeText(mContext, "提交作业", Toast.LENGTH_LONG).show();
                //遍历集合，看是否有题目未作
                if (HomeWorkTools.isExitUndo(mlist)) {
                    showDialog(duration, starttime);
                    return;
                }
                mLoaddingView.setVisibility(View.VISIBLE);
                AjaxParams params = mParams.createCommnithomeWork(mlist, duration, starttime);
                mFinalHttp.post(IBaes.POST_HOME_WORK_COMMITE, params, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        LogUtils.debug("POST_HOME_WORK_COMMITE sucess!" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int status = (int) jsonObject.get("status");
                            if (status == 1) {
                                Toast.makeText(mContext, "提交作业成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "提交作业失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mLoaddingView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        LogUtils.debug("strMsg=" + strMsg);
                        Toast.makeText(mContext, "提交作业失败" + strMsg, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
                finish();
                break;
            case R.id.top_btn:
                startActivity(new Intent(mContext, FeedBackActivity.class));
                break;
            case R.id.tv_answer_station:
                Intent intent = new Intent();
                intent.putExtra("duration", duration);
                intent.setClass(mContext, AnswerSheetActivity.class);
                startActivity(intent);
                break;
            default:

                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int position = vp_answers.getCurrentItem();
        if (position == mlist.size()) {
            bt_commit.setVisibility(View.VISIBLE);
        } else {
            bt_commit.setVisibility(View.GONE);
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            JobDetail bean = mlist.get(position);
            int tx = bean.getQuestype();
            switch (tx) {
                case 21://单项选择题
                case 23://双项选择题
                    return FragmentFactory.createHomeWorkFragment(FragmentFactory.TYPE_DANXUAN, bean, position);
                case JobDetail.TYPE_TINGLI://听力题
                    return new TingLiFragment();
                case 10018:
                default:
                    return FragmentFactory.createHomeWorkFragment(FragmentFactory.TYPE_TIANKONG, bean, position);
            }
        }

        @Override
        public int getCount() {
            return mlist.size();
        }
    }
}
