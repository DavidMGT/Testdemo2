package com.work.student.more;

import com.work.student.R;
import com.work.student.tool.IBaes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置
 * 
 * @author 左丽姬
 */
public class SettingActivity extends Activity implements OnClickListener {

	private ImageView isnet_image;
	private RadioButton rb_sys_setting;
	private RadioButton rb_message_setting;
	private RadioButton rb_notice_setting;
	private boolean sys_flag = false, msg_flag = false, notice_flag = false;
	private FrameLayout fl_version_setting;
	private FrameLayout fl_calce_setting;
	private FrameLayout fl_app_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initSetting();
	}

	public void initSetting() {
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
		top_text.setText("设置");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);

		TextView tv_name_setting = (TextView) findViewById(R.id.tv_name_setting);
		tv_name_setting.setText("消息&提醒");

		rb_sys_setting = (RadioButton) findViewById(R.id.rb_sys_setting);
		rb_message_setting = (RadioButton) findViewById(R.id.rb_message_setting);
		rb_notice_setting = (RadioButton) findViewById(R.id.rb_notice_setting);
		fl_version_setting = (FrameLayout) findViewById(R.id.fl_version_setting);
		fl_calce_setting = (FrameLayout) findViewById(R.id.fl_calce_setting);
		fl_app_setting = (FrameLayout) findViewById(R.id.fl_app_setting);
		rb_sys_setting.setOnClickListener(this);
		rb_message_setting.setOnClickListener(this);
		rb_notice_setting.setOnClickListener(this);
		fl_app_setting.setOnClickListener(this);
		fl_calce_setting.setOnClickListener(this);
		fl_version_setting.setOnClickListener(this);

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
		case R.id.rb_sys_setting:
			// 系统推送
			if (sys_flag) {
				sys_flag = false;
				rb_sys_setting.setChecked(false);
			} else {
				sys_flag = true;
				rb_sys_setting.setChecked(true);
			}
			break;
		case R.id.rb_message_setting:
			// 消息提醒
			if (msg_flag) {
				msg_flag = false;
				rb_message_setting.setChecked(false);
			} else {
				msg_flag = true;
				rb_message_setting.setChecked(true);
			}
			break;
		case R.id.rb_notice_setting:
			// 通知/问卷
			if (notice_flag) {
				notice_flag = false;
				rb_notice_setting.setChecked(false);
			} else {
				notice_flag = true;
				rb_notice_setting.setChecked(true);
			}
			break;
		case R.id.fl_app_setting:
			// 关于慧作业
			break;
		case R.id.fl_calce_setting:
			// 清除缓存
			break;
		case R.id.fl_version_setting:
			// 版本更新

			break;
		}
	}

}
