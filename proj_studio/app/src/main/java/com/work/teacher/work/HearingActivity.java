package com.work.teacher.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;

import net.tsz.afinal.FinalHttp;

/**
 * 作业-》》设置听力
 * Created by 左丽姬 on 2016/9/10.
 */
public class HearingActivity extends Activity implements View.OnClickListener {
    private ImageView isnet_image;//取消进入设置界面（只有在没有网络的时候显示）
    private ServecHttp servecHttp;
    private WorkJson jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing);
        initWork();
    }
    public void initWork(){

        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();//用户登录保存的key和userid

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
        top_text.setText("听力设置");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setVisibility(View.GONE);
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
        }
    }
}
