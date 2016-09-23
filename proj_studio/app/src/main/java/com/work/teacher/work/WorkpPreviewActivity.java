package com.work.teacher.work;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.WorkAuthor;
import com.work.teacher.bean.WorkRelseQuestion;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.view.MyListView;
import com.work.teacher.work.adapter.WorkEditAdapter;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * 作业--》》预览编辑作业
 *
 * @author 左丽姬
 */
public class WorkpPreviewActivity extends Activity implements OnClickListener {
    private ImageView isnet_image;
    private String sectionId, sectName, subjectCs, jiaocaiCs, wid;
    private ServecHttp servecHttp;
    private WorkJson jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;

    private TextView no_lists_preview,tv_enddate_preview,tv_name_preview,tv_newdate_preview,tv_title_preview;
    private MyListView lists_preview;
    private List<WorkRelseQuestion> lists = new ArrayList<WorkRelseQuestion>();
    private WorkEditAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initpreview();
    }

    public void initpreview() {
        wid = getIntent().getStringExtra("wid");

        servecHttp = new ServecHttp();
        jsonDate = new WorkJson();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();


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
        top_text.setText("预览作业");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("导出");
        top_btn.setVisibility(View.VISIBLE);

        tv_title_preview = (TextView) findViewById(R.id.tv_title_preview);
        tv_newdate_preview = (TextView) findViewById(R.id.tv_newdate_preview);
        tv_name_preview = (TextView) findViewById(R.id.tv_name_preview);
        tv_enddate_preview = (TextView) findViewById(R.id.tv_enddate_preview);

        no_lists_preview = (TextView) findViewById(R.id.no_lists_preview);
        lists_preview = (MyListView) findViewById(R.id.lists_preview);
        adapter = new WorkEditAdapter(this, lists,1);
        lists_preview.setAdapter(adapter);
        queryPreview();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                // 打开设置界面
                mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.top_btn:
                //导出

                break;
        }
    }

    /**
     * 查询作业，编辑
     */
    public void queryPreview() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(WorkpPreviewActivity.this, "网络不给力,请稍后...");
            return;
        }
//        Log.i("test", "wid=" + wid + " " + key + "   " + userId);
        AjaxParams params = servecHttp.updatePwd(key, userId, wid, "id");
        finalHttp.post(IBaes.WORK_EDITTEXT_PREVIEW, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
//                Log.i("test", "编辑作业:" + t.toString());
                Map<String, Object> map = jsonDate.jsonAddTwo(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });

    }

    /**
     * 根据章节id查询章节的名称
     */
    public void previewSection(String fidi) {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(WorkpPreviewActivity.this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = servecHttp.updatePwd(key, userId, fidi, "jd");
        finalHttp.post(IBaes.WORK_SECTION_TIGAN, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                    Log.i("test",o.toString());
                Map<String, Object> map = jsonDate.jsonSection(o.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            int status = (int) map.get("status");
            switch (msg.what) {
                case 0:
                    //显示数据
                    if (status == 1) {
                        WorkAuthor author = (WorkAuthor) map.get("author");
                        previewSection(author.getChapterdid());
                        tv_name_preview.setText("命题人: "+author.getName());
                        GregorianCalendar gc = new GregorianCalendar();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        if(author.getLastupdatetime()==0) {
                            tv_newdate_preview.setText("更新时间：暂无");
                        }
                        else{
                            gc.setTimeInMillis(author.getLastupdatetime()*1000);
                            tv_newdate_preview.setText("更新时间: "+sdf.format(gc.getTime()));
                        }
                        if(author.getEndtime()==0)
                            tv_enddate_preview.setText("截止时间：未添加");
                        else{
                            gc.setTimeInMillis(author.getEndtime()*1000);
                            tv_enddate_preview.setText("截止时间: "+sdf.format(gc.getTime()));
                        }
                        List<WorkRelseQuestion> questions = (List<WorkRelseQuestion>) map.get("wrqs");
                        if (questions.size() > 0) {
                            lists_preview.setVisibility(View.VISIBLE);
                            no_lists_preview.setVisibility(View.GONE);
                            lists.addAll(questions);
                            adapter.notifyDataSetChanged();
                        } else {
                            no_lists_preview.setVisibility(View.VISIBLE);
                            lists_preview.setVisibility(View.GONE);
                        }
                    } else {
                        no_lists_preview.setVisibility(View.VISIBLE);
                        lists_preview.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if(status==1){
                        String name = (String) map.get("name");
                        tv_title_preview.setText(name);
                    }
                    break;

            }
        }
    };


}
