package com.work.teacher.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.work.adapter.ScoreAdpter;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作业-》设置分数
 * Created by 左丽姬 on 2016/9/10.
 */
public class ScoreActivity extends Activity implements View.OnClickListener {
    private ImageView isnet_image;//取消进入设置界面（只有在没有网络的时候显示）
    private ServecHttp servecHttp;
    private WorkJson jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private List<String> lists;
    private List<String> alls=new ArrayList<String>();
    private ListView lv_scores;
    private Button commit_score;;
    private ScoreAdpter adpter;
    private String wid="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initWork();
    }
    public void initWork(){

        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();//用户登录保存的key和userid
        lists = getIntent().getStringArrayListExtra("scores");
        alls.addAll(lists);
        wid = getIntent().getStringExtra("wid");

        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if(!IBaes.isNet(this)){
            //网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);

        // 头部设置
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        TextView top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("分值设置");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setVisibility(View.GONE);

        lv_scores = (ListView) findViewById(R.id.lv_scores);
        TextView tv_noscore= (TextView) findViewById(R.id.tv_noscore);
        commit_score = (Button) findViewById(R.id.commit_score);
        commit_score.setOnClickListener(this);
        if(lists.size()==0){
            tv_noscore.setVisibility(View.VISIBLE);
        }
        adpter=new ScoreAdpter(this,lists);
        adpter.setOnEditLis();
        lv_scores.setAdapter(adpter);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent mIntent=null;
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                //打开设置界面
                mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.commit_score:
                //修改分数
                UpdateScore();
                break;
        }
    }

    /**修改分数*/
    public void UpdateScore(){
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(ScoreActivity.this, "网络不给力,请稍后...");
            return;
        }

        for (String a:alls) {
            String[] as=a.split("∮∞");
            for (String l:lists) {
                String[] ls=l.split("∮∞");
                if(ls.length>3){
                    if(as[0].equals(ls[0])&&Double.parseDouble(as[1])!=Double.parseDouble(ls[1])){
                        AjaxParams params=new AjaxParams();
                        params.put("key",key);
                        params.put("userId",userId);
                        params.put("zyid",wid);
                        params.put("tmlx",ls[3]);
                        params.put("fs",Double.parseDouble(ls[1])+"");
                        new FinalHttp().post(IBaes.WORK_UPDATE_SCORE, params, new AjaxCallBack<Object>() {
                            @Override
                            public void onSuccess(Object o) {
                                super.onSuccess(o);
//                                Log.i("test","分数修改:"+o.toString());
                                Map<String,Object> map=new JsonData().jsonAvatar(o.toString());
                                Message msg=new Message();
                                msg.obj=map;
                                msg.what=0;
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }
            }
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Map<String,Object> map= (Map<String, Object>) msg.obj;
                    int status= (int) map.get("status");
                    if(status==1){
                        Intent intent = new Intent(IBaes.ACTION_WORK_ADDWROK);
                        sendBroadcast(intent);
                    }
                    IBaes.toastShow(ScoreActivity.this,map.get("zhuce").toString());
                    break;
            }
        }
    };
}
