package com.work.student;

import java.util.Timer;
import java.util.TimerTask;

import com.work.student.service.NoticeService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 首页
 *@author 左丽姬
 */
@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity implements OnClickListener {

	private Context context;
	private TabHost mTabHost;
	private LinearLayout tab_message, tab_work, tab_class, tab_more;
	private ImageView tab_message_img, tab_work_img,tab_class_img, tab_more_img;
	private TextView tab_message_text,tab_work_text,tab_class_text,tab_more_text;
	private static boolean isExit=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initTabs();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent=new Intent(this,NoticeService.class);
		startService(intent);
	}
	
	/**
	 * 初始化
	 */
	@SuppressLint("ResourceAsColor")
	public void initTabs(){
		context=this;
		mTabHost = getTabHost();
		TabWidget tabWidget = mTabHost.getTabWidget();
		View message_view=LayoutInflater.from(context).inflate(R.layout.tab_message,null);
		View work_view=LayoutInflater.from(context).inflate(R.layout.tab_work,null);
		View class_view=LayoutInflater.from(context).inflate(R.layout.tab_teaclass,null);
		View more_view=LayoutInflater.from(context).inflate(R.layout.tab_more,null);
		
		tab_message = (LinearLayout) message_view.findViewById(R.id.tab_message);
		tab_message_img = (ImageView) message_view.findViewById(R.id.tab_message_img);
		tab_message_text = (TextView) message_view.findViewById(R.id.tab_message_text);
		tab_message_text.setTextColor(getResources().getColor(R.color.green));
		
		tab_work = (LinearLayout) work_view.findViewById(R.id.tab_work);
		tab_work_img = (ImageView) work_view.findViewById(R.id.tab_work_img);
		tab_work_text = (TextView) work_view.findViewById(R.id.tab_work_text);
		
		
		tab_class = (LinearLayout) class_view.findViewById(R.id.tab_class);
		tab_class_img = (ImageView) class_view.findViewById(R.id.tab_class_img);
		tab_class_text = (TextView) class_view.findViewById(R.id.tab_class_text);
		
		tab_more = (LinearLayout) more_view.findViewById(R.id.tab_more);
		tab_more_img = (ImageView) more_view.findViewById(R.id.tab_more_img);
		tab_more_text = (TextView) more_view.findViewById(R.id.tab_more_text);
		
		mTabHost.addTab(mTabHost.newTabSpec("message")
				.setIndicator(message_view).setContent(new Intent(this, MessageActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("work")
				.setIndicator(work_view).setContent(new Intent(this, WorkActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("class")
				.setIndicator(class_view).setContent(new Intent(this, TeaClassActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("more")
				.setIndicator(more_view).setContent(new Intent(this, MoreActivity.class)));
	    mTabHost.setCurrentTab(0);
	    
	    tab_message.setOnClickListener(this);
	    tab_work.setOnClickListener(this);
	    tab_class.setOnClickListener(this);
	    tab_more.setOnClickListener(this);
	}
	
	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tab_message:
			 seleotColor();
			 tab_message_img.setImageResource(R.drawable.message_yes);
			 tab_message_text.setTextColor(getResources().getColor(R.color.green));
			 mTabHost.setCurrentTab(0);
			break;
		case R.id.tab_work:
			seleotColor();
			tab_work_img.setImageResource(R.drawable.work_yes);
			tab_work_text.setTextColor(getResources().getColor(R.color.green));
			mTabHost.setCurrentTab(1);
			break;
		case R.id.tab_class:
			seleotColor();
			tab_class_img.setImageResource(R.drawable.class_yes);
			tab_class_text.setTextColor(getResources().getColor(R.color.green));
			mTabHost.setCurrentTab(2);
			break;
		case R.id.tab_more:
			seleotColor();
			tab_more_img.setImageResource(R.drawable.more_yes);
			tab_more_text.setTextColor(getResources().getColor(R.color.green));
			mTabHost.setCurrentTab(3);
			break;
		}
	}
	
	/**
	 * 设置底部导航烂选中
	 */
	
	@SuppressLint("ResourceAsColor")
	public void seleotColor(){
		tab_message_img.setImageResource(R.drawable.message_no);
		tab_work_img.setImageResource(R.drawable.work_no);
		tab_class_img.setImageResource(R.drawable.class_no);
		tab_more_img.setImageResource(R.drawable.more_no);
		
		tab_message_text.setTextColor(getResources().getColor(R.color.blackgrey));
		tab_work_text.setTextColor(getResources().getColor(R.color.blackgrey));
		tab_class_text.setTextColor(getResources().getColor(R.color.blackgrey));
		tab_more_text.setTextColor(getResources().getColor(R.color.blackgrey));
	}

	public static void exit(Activity activity) {
        if (!isExit) {
        	Log.e("test", "进入");
            isExit = true;
            Toast.makeText(activity, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit=false;
				}
			}, 3000);
        } else {
        	activity.finish();
            System.exit(0);
        }
    }
}
