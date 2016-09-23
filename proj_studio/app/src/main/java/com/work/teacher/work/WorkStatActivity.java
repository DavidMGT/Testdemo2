package com.work.teacher.work;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.tool.IBaes;
import com.work.teacher.view.TimeSelector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 定时发布
 *
 * @author 左丽姬
 */
public class WorkStatActivity extends Activity implements OnClickListener {
    private ImageView isnet_image;
    private RadioButton rb_no_state;
    private RadioButton rb_yes_state;
    private int state = 2;
    private EditText startDateTime;
    private EditText endDateTime;
    private TimeSelector timeSelector;
    private TextView tv_state_date, tv_no_state;
    private String date = "";
    private String re_date = "";
    private String re_get = "";
    private int deyle=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        initState();
    }

    public void initState() {

        re_date = getIntent().getStringExtra("re_date");
        re_get = getIntent().getStringExtra("re_get");
        date=re_date;
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
        top_text.setText("定时发布");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("确定");
        top_btn.setOnClickListener(this);

        FrameLayout no_state = (FrameLayout) findViewById(R.id.no_state);
        FrameLayout yes_state = (FrameLayout) findViewById(R.id.yes_state);
        no_state.setOnClickListener(this);
        yes_state.setOnClickListener(this);
        rb_no_state = (RadioButton) findViewById(R.id.rb_no_state);
        rb_yes_state = (RadioButton) findViewById(R.id.rb_yes_state);

        Drawable drawableWeiHui = getResources().getDrawable(R.drawable.selector_notice_amount);
        drawableWeiHui.setBounds(0, 0, 25, 25);// 第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb_no_state.setCompoundDrawables(drawableWeiHui, null, null, null);// 只放上面
        Drawable drawable = getResources().getDrawable(R.drawable.selector_notice_amount);
        drawable.setBounds(0, 0, 25, 25);// 第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb_yes_state.setCompoundDrawables(drawable, null, null, null);// 只放上面

        tv_state_date = (TextView) findViewById(R.id.tv_state_date);

        tv_no_state = (TextView) findViewById(R.id.tv_no_state);
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR) + 2;
        int monthOfYear = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                date = time;
                tv_state_date.setText("自定义   " + time);
            }
        }, re_get, year + "-" + monthOfYear + "-" + dayOfMonth + " " + hour + ":" + minute);
        if (re_get.equals("1990-01-01 00:00"))
            setDefaultRes();
        else
            setDefaultAccopt();
    }

    /**
     * 设置默认接受时间
     */
    public void setDefaultRes() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        tv_no_state.setText(sdf.format(gc.getTime()) + "(默认)");

    }

    /**
     * 接受时间
     */
    public void setDefaultAccopt() {
        try {
            SimpleDateFormat asdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date t_d = asdf.parse(re_get);
            int year = t_d.getYear() + 1900;
            int month = t_d.getMonth() + 1;
            int day = t_d.getDate();
            String strDate = year + "-" + month + "-" + (day + 1) + " 8:00:00";
            SimpleDateFormat acctop_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            date = acctop_sdf.parse(strDate);
            tv_no_state.setText(acctop_sdf.format(date) + "(默认)");
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
            case R.id.no_state:
                // 选择无
                state = 2;
                date=tv_no_state.getText().toString().trim().replace("(默认)","");
                rb_no_state.setChecked(true);
                rb_yes_state.setChecked(false);
                break;
            case R.id.yes_state:
                // 选择自定义
                state = 0;
                rb_no_state.setChecked(false);
                rb_yes_state.setChecked(true);
                timeSelector.show();
                break;
            case R.id.top_back:
                deyle=0;
                ecitState();
                break;
            case R.id.top_btn:
                deyle=1;
                ecitState();
                break;
        }
    }

    /**
     * 退出
     */
    public void ecitState() {
        if (state == 1 && re_date.equals(date)) {
            IBaes.toastShow(WorkStatActivity.this, "你未选择时间，默认是无时间");
            state = 2;
        }
        Intent intent = new Intent();
        intent.putExtra("state_status", state);
        intent.putExtra("state_date", date);
        intent.putExtra("deyle", deyle);
        setResult(IBaes.STATE_NOTICETWO, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            deyle=0;
            ecitState();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
