package com.work.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.widget.LoadingView;
import com.work.student.adapter.MessageMsgSubAdapter;
import com.work.student.bean.MessageBean;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;
import com.work.student.tool.TextUtil;
import com.work.student.view.LoadingDialog;
import com.work.student.view.NoticeDetailActivity;
import com.work.student.view.QuestionNaireActivity;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页点击item进入的列表子页面
 */
public class SubMessageActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "SubMessageActivity";
    private TextView mTvTitle;
    private String mTitle;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLvMessage;
    private int page = 1;
    private int type;
    private FinalHttp mFinalHttp;
    private ServecHttp mServecHttp;
    private List<MessageBean> mList = new ArrayList<>();
    private boolean isRefresh = true;
    private MessageMsgSubAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_message);

        initVariable();
        initView();
        getData();
        //http://student.hzyeah.com/mobile/getNoticeList?p=2&userid=177&type=1
    }


    private void initVariable() {
        mTitle = getIntent().getStringExtra("title");
        if (TextUtil.isEmpty(mTitle)) {
            return;
        }

        if ("通知".equals(mTitle)) {
            type = 1;
        } else {
            type = 2;
        }

        mFinalHttp = new FinalHttp();
        mServecHttp = new ServecHttp();
    }

    private LoadingView mLoadView;

    private void initView() {
        mLoadView = (LoadingView) findViewById(R.id.loadView);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        findViewById(R.id.iv_back).setOnClickListener(this);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new MyOnRefreshListener());

        mLvMessage = (ListView) findViewById(R.id.lv_message);
        mAdapter = new MessageMsgSubAdapter(this, type, mList);
        mLvMessage.setAdapter(mAdapter);
        mLvMessage.setOnScrollListener(new MyOnScrollListener());
        mLvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBean bean = mList.get(position);
                String url = null;
                mList.get(position).setStatus("1");
                mAdapter.notifyDataSetChanged();
                //发送网络请求修改状态
                if (type == 1) {
                    //通知
                    url = IBaes.UPDATE_INFO_STATE + "userid=" + IBaes.userid + "&type=" + 1 + "&fid=" + bean.getFid();
                    LogUtils.debug("UPDATE_INFO_STATE=" + url);
                    AjaxParams params = mServecHttp.createUpdateMessageParams(String.valueOf(IBaes.userid), "1",
                            String.valueOf(bean.getFid()));
                    mFinalHttp.get(url, new AjaxCallBack() {
                        @Override
                        public void onSuccess(Object o) {
                            super.onSuccess(o);
                            LogUtils.debug("修改服务器状态" + o.toString());
                        }
                    });
                    Intent intent = new Intent(SubMessageActivity.this, NoticeDetailActivity.class);
                    intent.putExtra("noticeId", bean.getFid());
                    startActivity(intent);
                } else {
                    //问卷
                    url = IBaes.UPDATE_INFO_STATE + "userid=" + IBaes.userid + "&type=" + 2 + "&wid=" + bean.getWid();
                    AjaxParams params = mServecHttp.createUpdateMessageParams(IBaes.userid, "2",
                            String.valueOf(bean.getWid()));
                    LogUtils.debug("Ibase .userid=" + IBaes.userid);
                    mFinalHttp.get(url, new AjaxCallBack() {
                        @Override
                        public void onSuccess(Object o) {
                            super.onSuccess(o);
                            LogUtils.debug("修改服务器状态" + o.toString());
                        }
                    });
                    Intent intent = new Intent(SubMessageActivity.this, QuestionNaireActivity.class);
                    intent.putExtra("questionId", bean.getWid());
                    startActivity(intent);
                }

            }
        });
    }

    private void getData() {
        AjaxParams params = mServecHttp.createMessageParams(IBaes.userid, String.valueOf(page),
                String.valueOf(type));
        Log.d(TAG, params.toString());
        mFinalHttp.get(IBaes.ACTION_MESSAGE_SUB_LIST, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                mLoadView.setVisibility(View.GONE);
                if (isRefresh) {
                    mList.clear();
                    isRefresh = false;
                }
                List<MessageBean> list = MessageBean.parser(s);
                mList.addAll(list);
                Log.d(TAG, list.size() + "");
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Log.d(TAG, "失败");
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(SubMessageActivity.this, "获取失败,请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            Log.d(TAG, scrollState + " onScrollStateChanged");
            if (scrollState == 0 && mLvMessage.getLastVisiblePosition() == mList.size() - 1) {
                page++;
                getData();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.d(TAG, " onScroll:   " + mLvMessage.getLastVisiblePosition());

        }
    }

    private class MyOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            page = 1;
            isRefresh = true;
            getData();
        }
    }
}
