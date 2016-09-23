package com.work.teacher;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.work.teacher.more.PersonalSettingsActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * app启动界面
 *
 * @author 左丽姬
 */
public class LaunchActivity extends Activity implements OnClickListener {

    private LinearLayout viewGroup;
    private ViewPager viewPage;
    private Button launchtologin;
    private TextView timeLaunch;

    private int[] images = {R.drawable.main_1, R.drawable.main_2, R.drawable.main_3, R.drawable.main_4};
    private List<View> imageviews;
    private ImageView[] tops;

    //倒计时
    private int maxTime = 10;
    private boolean isStop = false;

    private JsonData jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initTops();
        initImageView();
        initLaunch();
    }

    /**
     * 初始化
     */
    public void initLaunch() {
        jsonDate = new JsonData();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();
        IBaes.USER_ID = userId;
        launchtologin = (Button) findViewById(R.id.launchtologin);
        timeLaunch = (TextView) findViewById(R.id.time_launch);
        viewPage = (ViewPager) findViewById(R.id.viewpage_launch);
        viewPage.setAdapter(new ViewPageAdpater());
        viewPage.setOnPageChangeListener(new Launcherlistener());
        launchtologin.setOnClickListener(this);
    }

    /**
     * 初始化imageview
     */
    public void initImageView() {
        imageviews = new ArrayList<View>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageviews.add(imageView);
        }
    }

    /**
     * 初始化点点
     */
    public void initTops() {
        viewGroup = (LinearLayout) findViewById(R.id.viewgroup_launch);
        tops = new ImageView[images.length];
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            LayoutParams params = new LayoutParams();
            imageView.setLayoutParams(params);
            tops[i] = imageView;
            if (i == 0) {
                tops[i].setBackgroundResource(R.drawable.main_b);
            } else {
                tops[i].setBackgroundResource(R.drawable.main_a);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            viewGroup.addView(imageView, layoutParams);
        }
    }

    /**
     * 倒计时
     */
    public void caleTime() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    maxTime--;
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            timeLaunch.setText(maxTime + " 秒之后跳过");
                            if (maxTime == 0) {
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                goIntent();
                            }
                        }
                    });
                    if (maxTime == 0) {
                        break;
                    }
                    if (isStop) {
                        maxTime = 10;
                        break;
                    }
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.launchtologin:
                isStop = true;
                goIntent();
                break;
        }
    }

    /**
     * 跳转之首页
     */
    public void goIntent() {
        long i = 0;
        long login_date = (Long) SPUtils.get(this, "login_date", i);
        if ("".equals(userId)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();//注销启动页面
            return;
        }
        //得到当前时间
        long newDate = System.currentTimeMillis();
        long day = (newDate - login_date) / (1000 * 60 * 60 * 24);
        if (day >= 7) {
            exitLogin();
            return;
        }
        isLoginKey();
    }

    /**
     * 退出登陆
     */
    public void exitLogin() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(LaunchActivity.this, "网络不给力,请稍后...");
            return;
        }
        finalHttp.post(IBaes.EXIT_LOGIN, new ServecHttp().keyAndId(key, userId), new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
//				Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 判断是否是登陆状态
     */
    public void isLoginKey() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(LaunchActivity.this, "网络不给力,请稍后...");
            return;
        }
        Log.i("test", "进入...");
        AjaxParams params = new AjaxParams();
        params.put("userId", userId);
        finalHttp.post(IBaes.IS_LOGIN, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
//				Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, Object> map = null;
            switch (msg.what) {
                case 1:
                    map = (Map<String, Object>) msg.obj;
                    int status = (Integer) map.get("status");
                    if (status == 1) {
                        Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();//注销启动页面
                    } else {
                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();//注销启动页面
                    }
                    break;
                case 2:
                    map = (Map<String, Object>) msg.obj;
                    int ss = (Integer) map.get("status");
                    if (ss == 1) {
                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();//注销启动页面
                    }
                    break;
            }
        }
    };

    /**
     * viewpager适配器
     */
    class ViewPageAdpater extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageviews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView(imageviews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            View view = imageviews.get(position);
            container.addView(view);
            return view;
        }
    }

    class Launcherlistener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            launchtologin.setVisibility(View.GONE);
            timeLaunch.setVisibility(View.GONE);
            isStop = true;
            if (arg0 == 3) {
                isStop = false;
                caleTime();
                launchtologin.setVisibility(View.VISIBLE);
                timeLaunch.setVisibility(View.VISIBLE);
            }
            setImageBackground(arg0);
        }

        /**
         * 设置选中的tip的背景
         *
         * @param selectItems
         */
        private void setImageBackground(int selectItems) {
            for (int i = 0; i < tops.length; i++) {
                if (i == selectItems) {
                    tops[i].setBackgroundResource(R.drawable.main_b);
                } else {
                    tops[i].setBackgroundResource(R.drawable.main_a);
                }
            }
        }
    }

}
