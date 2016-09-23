package com.work.student.more.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.student.R;
import com.work.student.adapter.NoticeAdapter;
import com.work.student.bean.Notice;
import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;
import com.work.student.view.CustomListView;
import com.work.student.view.CustomListView.OnLoadMoreListener;
import com.work.student.view.CustomListView.OnRefreshListener;
import com.work.student.view.LoadingDialog;
import com.work.student.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 通知
 *
 * @author 左丽姬
 */
public class NoticeActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {
    private ImageView isnet_image;
    private CustomListView noticeLists;
    private TextView noticeNo;
    private List<Notice> lists = new ArrayList<Notice>();
    private NoticeAdapter adapter;
    private int page;
    private int curpage = 1;
    private boolean harmore = false;
    private ServecHttp servecHttp;
    private JsonData jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private LoadingDialog dialog;
    private NoticeBroadCast noticeCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initNotice();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        noticeCast = new NoticeBroadCast();
        IntentFilter filter = new IntentFilter(IBaes.ACTION_NOTICE);
        registerReceiver(noticeCast, filter);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(noticeCast);
    }

    public void initNotice() {
        servecHttp = new ServecHttp();
        jsonDate = new JsonData();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();
        page = (Integer) SPUtils.get(this, "page", 10);
        dialog = new LoadingDialog(this);
        dialog.setOnLoadingDialogResultListener(this);
        // 网络判断
        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if (!IBaes.isNet(this)) {
            // 网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);
        // 头部设置
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        TextView top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("通知");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("发布");
        top_btn.setOnClickListener(this);

        //内容
        noticeLists = (CustomListView) findViewById(R.id.notice_lists);
        adapter = new NoticeAdapter(this, lists);
        noticeLists.setAdapter(adapter);
        noticeNo = (TextView) findViewById(R.id.notice_lists_no);
        dialog.show();
        dialog.setText("加载中...");
        getNoticeList();
        //下拉刷新
        noticeLists.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                lists.clear();//下拉刷新，清空lists
                curpage = 1;
                getNoticeList();
                noticeLists.onRefreshComplete();
            }
        });
        //上拉加载
        noticeLists.setOnLoadListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (harmore) {
                    curpage++;
                    getNoticeList();
                } else {
                    IBaes.toastShow(NoticeActivity.this, "没有更多数据");
                }
                noticeLists.onLoadMoreComplete();
            }
        });

        noticeLists.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int item = position - 1;
                Intent intent = new Intent(NoticeActivity.this, NoticeDetailsActivity.class);
                intent.putExtra("noticeId", lists.get(item).getFid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                // 打开设置界面
                Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.top_btn:
                Intent intent = new Intent(this, AddNoticeOneActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取通知列表
     */
    public void getNoticeList() {
        AjaxParams params = servecHttp.noticeLists(key, userId, page, curpage);
        finalHttp.post(IBaes.NOTICE_LISTS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
//				Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonNoticeLists(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    int status = (Integer) map.get("status");
                    int page = (Integer) map.get("page");
                    harmore = (Boolean) map.get("harmore");
                    List<Notice> notices = (List<Notice>) map.get("noticeLists");
                    if (status == 1) {
                        if (notices != null) {
                            lists.addAll(notices);
                            adapter.notifyDataSetChanged();
                            if (lists.size() == 0) {
                                noticeNo.setVisibility(View.VISIBLE);
                                noticeLists.setVisibility(View.GONE);
                            } else {
                                noticeNo.setVisibility(View.GONE);
                                noticeLists.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        noticeNo.setVisibility(View.VISIBLE);
                        noticeLists.setVisibility(View.GONE);
                    }

                    break;
            }
        }
    };

    @Override
    public void dialogResult(int tag, int state) {
        if (state == LoadingDialog.SUCCESS) {
            setResult(100);
            finish();
        }
    }

    class NoticeBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (IBaes.ACTION_NOTICE.equals(intent.getAction())) {
                lists.clear();
                curpage = 1;
                getNoticeList();
            }
        }

    }
}
