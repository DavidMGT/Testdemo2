package com.work.teacher.more;

import com.work.teacher.LoginActivity;
import com.work.teacher.R;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 账号安全
 *@author 左丽姬
 */
public class SafetyActivity extends Activity implements OnClickListener {

	private ImageView isnet_image;
	private RelativeLayout update_pwd_safety;
	private RelativeLayout update_mobile_safety;
	private TextView mobile_safety;
	private String mobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safety);
		initSafety();
	}

	public void initSafety() {
		mobile=getIntent().getStringExtra("mobile");
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
		top_text.setText("账号安全");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);
		
		update_pwd_safety = (RelativeLayout) findViewById(R.id.update_pwd_safety);
		update_mobile_safety = (RelativeLayout) findViewById(R.id.update_mobile_safety);
		mobile_safety = (TextView) findViewById(R.id.mobile_safety);
		mobile_safety.setText("手机号码："+mobile);
		if("".equals(mobile.trim())){
			mobile_safety.setText("手机号码：未绑定");
		}
		
		update_pwd_safety.setOnClickListener(this);
		update_mobile_safety.setOnClickListener(this);

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
			// 打开设置界面
			mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
			startActivity(mIntent);
			break;
		case R.id.top_back:
			finish();
			break;
		case R.id.update_pwd_safety:
			//修改密码
			mIntent=new Intent(this, UpdatePwdActivity.class);
			mIntent.putExtra("update_name", "Password");
			mIntent.putExtra("update_mobile", "");
			startActivityForResult(mIntent, IBaes.SAFETY_UPDATEPWD);
			break;
		case R.id.update_mobile_safety:
			//修改手机号
			mIntent=new Intent(this, UpdatePwdActivity.class);
			mIntent.putExtra("update_name", "mobile");
			mIntent.putExtra("update_mobile", mobile);
			startActivityForResult(mIntent, IBaes.SAFETY_UPDATEPWD);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//修改密码
		if(requestCode==IBaes.SAFETY_UPDATEPWD&&resultCode==IBaes.UPDATEPWD_SAFETY_PWD){
			SPUtils.remove(this, "key");
			SPUtils.remove(this, "userId");
			Intent intent = new Intent(this, LoginActivity.class);
			intent.putExtra("isRegister", 3);
			startActivity(intent);
		}
		finish();
	}
}
