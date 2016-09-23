package com.work.student.more.notice;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.student.R;
import com.work.student.adapter.NoticeListAdapter;
import com.work.student.bean.InformBean;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.view.CustomListView;
import com.work.student.view.LoadingDialog;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by maguitao on 2016/9/8.
 */
public class NoticeListActivity extends Activity implements LoadingDialog.OnLoadingDialogResultListener, View.OnClickListener {

    LoadingDialog loadingdialog;
    CustomListView notice_lists;
    NoticeListAdapter adapter;
    private List<InformBean> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initView();
        initLoading();
    }

    @Override
    protected void onStart() {
        initData();
        super.onStart();
    }

    public int curIndex = 1;
    String noticeListurl = "http://student.hzyeah.com/mobile/getNoticeList?p=" + curIndex + "&userid=" + IBaes.userid + "&type=1";

    private void initData() {
        //  http://student.hzyeah.com/mobile/getNoticeList?p=2&userid=177&type=1
        FinalHttp fh = new FinalHttp();
        fh.get(noticeListurl, new AjaxCallBack() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                loadingdialog.dismiss();
                notice_lists_no.setVisibility(View.GONE);
                parseData(o.toString());
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                loadingdialog.dismiss();
            }
        });

    }

    private void parseData(String httpresult) {
        try {
            JSONObject json = new JSONObject(httpresult);
            String jsonString = json.getString("data");
            Type type = new TypeToken<List<InformBean>>() {
            }.getType();
            infos = new Gson().fromJson(jsonString, type);
            adapter = new NoticeListAdapter(infos, this);
            notice_lists.setAdapter(adapter);
        } catch (Exception e) {
            LogUtils.debug("e" + e);
        }
    }

    Button top_btn;
    ImageView top_back;
    TextView top_text;
    TextView notice_lists_no;//列表为空的时候显示

    private void initView() {
        top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setVisibility(View.GONE);
        notice_lists = (CustomListView) findViewById(R.id.notice_lists);
        notice_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoticeListActivity.this, NoticeDetailsActivity.class);
                intent.putExtra("noticeId", infos.get(position).getFid());
                startActivity(intent);
            }
        });
        top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("通知");
        notice_lists_no = (TextView) findViewById(R.id.notice_lists_no);
    }

    private void initLoading() {
        loadingdialog = new LoadingDialog(this);
        loadingdialog.show();
        loadingdialog.setOnLoadingDialogResultListener(this);
    }

    @Override
    public void dialogResult(int tag, int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }

    }
}
