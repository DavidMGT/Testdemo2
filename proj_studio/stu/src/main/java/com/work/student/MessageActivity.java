package com.work.student;

import java.util.ArrayList;
import java.util.List;

import com.work.student.adapter.MessageViewPageAdapter;
import com.work.student.fragment.MessageClassFragment;
import com.work.student.fragment.MessageMsgFragment;
import com.work.student.fragment.MessageStudyFragment;
import com.work.student.tool.IBaes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * 消息
 *@author 左丽姬
 */
public class MessageActivity extends FragmentActivity implements OnClickListener {
	private ImageView isnet_image;
	private Button msg_message, course_message, study_message;
	private ImageView add_message;
	private ViewPager viewpage_message;
	private List<Fragment> fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initMessage();
	}

	public void initMessage(){
		IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
		IBaes.net_relative.setVisibility(View.GONE);
		isnet_image = (ImageView) findViewById(R.id.isnet_image);
		if(!IBaes.isNet(this)){
			//网络不存在时显示
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
		isnet_image.setOnClickListener(this);
		IBaes.net_relative.setOnClickListener(this);
		
		fragments=new ArrayList<Fragment>();
		fragments.add(new MessageMsgFragment());
		fragments.add(new MessageClassFragment());
		fragments.add(new MessageStudyFragment());
		
		msg_message = (Button) findViewById(R.id.msg_message);
		course_message = (Button) findViewById(R.id.course_message);
		study_message = (Button) findViewById(R.id.study_message);
		add_message = (ImageView) findViewById(R.id.add_message);
		viewpage_message = (ViewPager) findViewById(R.id.viewpage_message);
		viewpage_message.setAdapter(new MessageViewPageAdapter(getSupportFragmentManager(), fragments));
		viewpage_message.setCurrentItem(0);
		viewpage_message.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					//消息
					seleotColor();
					msg_message.setBackgroundResource(R.drawable.message_top_yes);
					msg_message.setTextColor(getResources().getColor(R.color.green));
					break;
				case 1:
					//课程
					seleotColor();
					course_message.setBackgroundResource(R.drawable.class_project_yes);
					course_message.setTextColor(getResources().getColor(R.color.green));
					break;
				case 2:
					//互学
					seleotColor();
					study_message.setBackgroundResource(R.drawable.studey_yes);
					study_message.setTextColor(getResources().getColor(R.color.green));
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		msg_message.setOnClickListener(this);
		course_message.setOnClickListener(this);
		study_message.setOnClickListener(this);
		add_message.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			//打开设置界面
			 Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
             startActivity(mIntent);
			break;
		case R.id.msg_message:
			//消息
			seleotColor();
			msg_message.setBackgroundResource(R.drawable.message_top_yes);
			msg_message.setTextColor(getResources().getColor(R.color.green));
			viewpage_message.setCurrentItem(0);
			break;
		case R.id.course_message:
			//课程
			seleotColor();
			course_message.setBackgroundResource(R.drawable.class_project_yes);
			course_message.setTextColor(getResources().getColor(R.color.green));
			viewpage_message.setCurrentItem(1);
			break;
		case R.id.study_message:
			//互学
			seleotColor();
			study_message.setBackgroundResource(R.drawable.studey_yes);
			study_message.setTextColor(getResources().getColor(R.color.green));
			viewpage_message.setCurrentItem(2);
			break;
		case R.id.add_message:
			//添加消息
			
			break;
		}
	}
	/**
	 * 选择的页面,改变背景颜色，字体颜色
	 */
	public void seleotColor(){
		msg_message.setBackgroundResource(R.drawable.message_top_no);
		course_message.setBackgroundResource(R.drawable.class_project_no);
		study_message.setBackgroundResource(R.drawable.studey_no);
		
		msg_message.setTextColor(getResources().getColor(R.color.white));
		course_message.setTextColor(getResources().getColor(R.color.white));
		study_message.setTextColor(getResources().getColor(R.color.white));
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			HomeActivity.exit(MessageActivity.this);
		}
		return true;
	}
}
