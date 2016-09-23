package com.work.teacher.work;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.teacher.R;
import com.work.teacher.adapter.QuestionnarieAdapter;
import com.work.teacher.bean.QuestionnaireDetailBean;
import com.work.teacher.bean.QuestionnarieBean;
import com.work.teacher.tool.Constants;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.LogUtils;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页点击item进入的列表子页面
 */
public class QuestionnarielistActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SubMessageActivity";
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLvMessage;
    private int page = 1;
    private List<QuestionnarieBean> mList = new ArrayList<>();
    private boolean isRefresh = true;
    private QuestionnarieAdapter mAdapter;
    private RelativeLayout relat_dialog_addwrok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnarie);
    }

    QuestionnarieBean currentClicedkBean;

    @Override
    public void initView() {
        mTvTitle.setText("问卷调查");
        top_btn.setText("发布");
        top_btn.setOnClickListener(this);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new MyOnRefreshListener());
        mLvMessage = (ListView) findViewById(R.id.lv_message);
        mAdapter = new QuestionnarieAdapter(this, mList);
        mLvMessage.setAdapter(mAdapter);
        mLvMessage.setOnScrollListener(new MyOnScrollListener());
        mLvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentClicedkBean = mList.get(position);
                mAdapter.notifyDataSetChanged();
                addDialog();
            }
        });
    }

    /**
     * 编辑操作对话框（收藏、报错、添至作业、编辑）
     */
    Dialog dialog;

    public void addDialog() {
        dialog = new Dialog(this, R.style.PassDialog);
        dialog.show();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_question_edit, null);
        dialog.setContentView(view);
        relat_dialog_addwrok = (RelativeLayout) view.findViewById(R.id.relat_dialog_addwrok);
        LinearLayout ll_commit_again = (LinearLayout) view.findViewById(R.id.ll_commit_again);
        LinearLayout ll_preview_bg = (LinearLayout) view.findViewById(R.id.ll_preview_bg);
        LinearLayout ll_output_bg = (LinearLayout) view.findViewById(R.id.ll_output_bg);
        LinearLayout ll_check_bg = (LinearLayout) view.findViewById(R.id.ll_check_bg);
        ll_commit_again.setOnClickListener(this);
        ll_preview_bg.setOnClickListener(this);
        ll_output_bg.setOnClickListener(this);
        ll_check_bg.setOnClickListener(this);
    }

    @Override
    public void initData() {
        AjaxParams params = mParams.addQuestionnarieList(IBaes.USER_ID, page + "");
        mFinalHttp.get(IBaes.URL_GET_QUESTIONNARIE_LIST, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                String msg = null;
                try {
                    JSONObject jsonobj = new JSONObject(s);
                    msg = (String) jsonobj.get("mes");
                    if (isRefresh) {
                        mList.clear();
                        isRefresh = false;
                    }
                    JSONArray data = (JSONArray) jsonobj.get("data");
                    List<QuestionnarieBean> list = QuestionnarieBean.parseData(data.toString());
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                    mRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(s);
                mHandler.sendEmptyMessage(LOADDING_SUCESS);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_commit_again:
                //点击再发布
                AjaxParams params = mParams.addQuestionnarieDetail(IBaes.USER_ID, currentClicedkBean.getWid());
                mFinalHttp.get(IBaes.URL_GET_QUESTIONNARIE_DETAIL, params, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        String msg;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            msg = (String) jsonObject.get("mes");
                            LogUtils.debug("jsonboj.mes" + jsonObject.get("mes"));
                            if (msg.equalsIgnoreCase("success")) {
                                Constants.questionnaireDetailBean = new Gson().fromJson(jsonObject.get("data").toString(), new TypeToken<QuestionnaireDetailBean>() {
                                }.getType());
                                startActivity(new Intent(mContext, QuestionNaireEditActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(LOADDING_SUCESS);
                        finish();

                    }
                });
                //   Constants.questionnaireDetailBean = currentClicedkBean;
                break;
            case R.id.ll_preview_bg:
                //点击预览
                Intent intent = new Intent(mContext, QuestionNaireDetailActivity.class);
                intent.putExtra("questionId", currentClicedkBean.getWid());
                startActivity(intent);
                break;
            case R.id.ll_output_bg:
                //点击导出结果
                Toast.makeText(mContext, "功能开发中---", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_check_bg:
                //点击查看报告
                Toast.makeText(mContext, "功能开发中---", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_btn:
                //点击了发布问卷
                startActivity(new Intent(mContext, AddQuestionnarieStep1Activity.class));
                break;

        }
    }


    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            Log.d(TAG, scrollState + " onScrollStateChanged");
            if (scrollState == 0 && mLvMessage.getLastVisiblePosition() == mList.size() - 1) {
                page++;
                initData();
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
            initData();
        }
    }
}
