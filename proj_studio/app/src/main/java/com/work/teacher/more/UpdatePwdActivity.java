package com.work.teacher.more;

import java.util.Map;
import java.util.regex.Pattern;

import com.work.teacher.R;
import com.work.teacher.RegisterActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 修改密码 
 *@author 左丽姬
 */
public class UpdatePwdActivity extends Activity implements OnClickListener {
	
	private ImageView isnet_image;
	private EditText oldpwd_updatepwd, newpwd_updatepwd, twopwd_updatepwd,newmobile_updatemonile,verfiy_updatemonile;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private String update_name;
	private String update_mobile;
	private boolean isMobile=false,mobileFlag=false;
	private String verfiycode="";
	private Button getverfiy_updatemobile;
	private int max=60;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pwd);
		initUpdatePwd();
	}
	
	public void initUpdatePwd(){
		update_name=getIntent().getStringExtra("update_name");
		update_mobile=getIntent().getStringExtra("update_mobile");
		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();
		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();
		
		//网络判断
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
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("确定");
		top_btn.setOnClickListener(this);
		
		
		
		//页面内容
		
		LinearLayout ll_update_paessword=(LinearLayout) findViewById(R.id.ll_update_paessword);
		LinearLayout ll_update_mobile=(LinearLayout) findViewById(R.id.ll_update_mobile);
		if("mobile".equals(update_name)){
			top_text.setText("更改绑定");
			ll_update_paessword.setVisibility(View.GONE);
			ll_update_mobile.setVisibility(View.VISIBLE);
			newmobile_updatemonile=(EditText) findViewById(R.id.newmobile_updatemonile);
			verfiy_updatemonile=(EditText) findViewById(R.id.verfiy_updatemonile);
			newmobile_updatemonile.setHintTextColor(getResources().getColor(R.color.weakgrey));
			verfiy_updatemonile.setHintTextColor(getResources().getColor(R.color.weakgrey));
			getverfiy_updatemobile = (Button) findViewById(R.id.getverfiy_updatemobile);
			getverfiy_updatemobile.setOnClickListener(this);
			newmobile_updatemonile.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					String tel=newmobile_updatemonile.getText().toString();
					//验证手机号码是否已经注册
					String reg="^1[34578]{1}[0-9]{1}\\d{8}$";
					Pattern p = Pattern.compile(reg);  
					if(p.matcher(tel).find()){
						verfiyMobile(tel);
					}
				}
			});
		}else{
			top_text.setText("修改密码");
			ll_update_paessword.setVisibility(View.VISIBLE);
			ll_update_mobile.setVisibility(View.GONE);
			oldpwd_updatepwd = (EditText) findViewById(R.id.oldpwd_updatepwd);
			newpwd_updatepwd = (EditText) findViewById(R.id.newpwd_updatepwd);
			twopwd_updatepwd = (EditText) findViewById(R.id.twopwd_updatepwd);
			oldpwd_updatepwd.setHintTextColor(getResources().getColor(R.color.weakgrey));
			newpwd_updatepwd.setHintTextColor(getResources().getColor(R.color.weakgrey));
			twopwd_updatepwd.setHintTextColor(getResources().getColor(R.color.weakgrey));
		}
		
		Log.i("test", key + "    " + userId);
	
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
		case R.id.top_back:
			mobileFlag=true;
			finish();
			break;
		case R.id.top_btn:
			if("mobile".equals(update_name)){
				//确定修改手机号码
				updateMobile();
				return ;
			}
			//确定修改密码
			commitUpdatePwd();
			break;
		case R.id.getverfiy_updatemobile:
			//获取验证码
			getUpdateVerFiy();
			break;
		}
	}

	/**确定修改密码*/
	public void commitUpdatePwd(){
		String old=oldpwd_updatepwd.getText().toString();
		String newpwd=newpwd_updatepwd.getText().toString();
		String pwd=twopwd_updatepwd.getText().toString();
		if(!IBaes.isNet(this)){
			IBaes.toastShow(UpdatePwdActivity.this, "网络不给力,请稍后...");
			return ;
		}
		if("".equals(old)){
			IBaes.toastShow(this, "原密码不能为空,请填写...");
			return ;
		}
		if(newpwd.length()<6){
			IBaes.toastShow(this, "新密码不能小于6位数");
			return ;
		}
		if(!newpwd.equals(pwd)){
			IBaes.toastShow(this, "两次新密码不一致");
			return ;
		}
		AjaxParams params=servecHttp.updatePwd(key, userId, newpwd,update_name);
		finalHttp.post(IBaes.UPDATE_PAESSWORD,params,new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Map<String, Object> map=jsonDate.jsonAvatar(t.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=2;
				handler.sendMessage(msg);
			}
		});
		
	}
	
	/**验证手机号是否已经注册*/
	public void verfiyMobile(String tel){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(UpdatePwdActivity.this, "网络不给力,请稍后...");
			return ;
		}
		AjaxParams params=servecHttp.verfiyMobile("mobile",tel);
		finalHttp.post(IBaes.REGISTER_MOBILE,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
//				Log.i("test", "验证手机号："+t.toString());
				int status=jsonDate.jsonInt(t.toString());
				handler.sendEmptyMessage(status);
			}
		});
	}
	
	/**
	 * 获取验证码
	 */
	public void getUpdateVerFiy(){
		String tel=newmobile_updatemonile.getText().toString();
		if(!IBaes.isNet(this)){
			IBaes.toastShow(UpdatePwdActivity.this, "网络不给力,请稍后...");
			return ;
		}
		if(isMobile==false){
			IBaes.toastShow(UpdatePwdActivity.this, "正在验证手机号!稍后请重试...");
			verfiyMobile(tel);
			return ;
		}
		getverfiy_updatemobile.setClickable(false);
		mobileFlag=false;
		String reg="^1[34578]{1}[0-9]{1}\\d{8}$";
		Pattern p = Pattern.compile(reg);  
		if(!p.matcher(tel).find()){
			IBaes.toastShow(UpdatePwdActivity.this, "手机格式不匹配,例:1342007826");
			return ;
		}
		AjaxParams params=servecHttp.verfiyMobile("mobile", tel);
		finalHttp.post(IBaes.REGISTER_VERFIY,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Map<String, Object> map=jsonDate.jsonVerfiy(t.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=3;
				handler.sendMessage(msg);
			}
		});
	}
	
	/**确定修改手机号码*/
	public void updateMobile(){
		String tel=newmobile_updatemonile.getText().toString();
		String verfiy=verfiy_updatemonile.getText().toString();
		if(!IBaes.isNet(this)){
			IBaes.toastShow(UpdatePwdActivity.this, "网络不给力,请稍后...");
			return ;
		}
		if("".equals(tel.trim())){
			IBaes.toastShow(UpdatePwdActivity.this, "手机号码不能为空，请填写...");
			return ;
		}
		if(!isMobile){
			IBaes.toastShow(UpdatePwdActivity.this, "手机号已注册");
			return ;
		}
		
		if(!verfiycode.equals(verfiy)){
			IBaes.toastShow(UpdatePwdActivity.this, "验证码不正确!请确认...");
			return ;
		}
		AjaxParams params=servecHttp.updatePwd(key, userId, tel,update_name);
		finalHttp.post(IBaes.UPDATE_MOBILE,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Map<String, Object> map=jsonDate.jsonVerfiy(t.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=4;
				handler.sendMessage(msg);
			}
		});
		
	}
	
	Handler handler=new Handler(){
		Map<String, Object> map=null;
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case -1:
				IBaes.toastShow(UpdatePwdActivity.this, "网络连接错误");
				break;
			case 0:
				isMobile=false;
				IBaes.toastShow(UpdatePwdActivity.this, "手机号已注册");
				break;
			case 1:
				isMobile=true;
				IBaes.toastShow(UpdatePwdActivity.this, "手机号可以注册");
				break;
			case 2:
				//修改密码
				map=(Map<String, Object>) msg.obj;
				String zhuce=map.get("zhuce").toString();
				IBaes.toastShow(UpdatePwdActivity.this, zhuce);
				int status=(Integer) map.get("status");
				if(status==1){
					setResult(IBaes.UPDATEPWD_SAFETY_PWD);
					finish();
				}
				break;
			case 3:
				//获取验证码
				map=(Map<String, Object>) msg.obj;
				int ss=(Integer) map.get("status");
				String yzm=(String) map.get("yzm");
				if(ss==0){
					IBaes.toastShow(UpdatePwdActivity.this, yzm);
				}else{
					new UpdateMobilrThread().start();
					IBaes.toastShow(UpdatePwdActivity.this, yzm);
					verfiycode=(String) map.get("sjyzm");
					Log.i("test", "verfiycode="+verfiycode);
				}
				break;
			case 4:
				//修改手机号码
				map=(Map<String, Object>) msg.obj;
				String zh=map.get("zhuce").toString();
				IBaes.toastShow(UpdatePwdActivity.this, zh);
				int st=(Integer) map.get("status");
				if(st==1){
					isMobile=true;
					Intent intent1=new Intent(IBaes.ACTION_UPDATE_PERSONAL);
					sendBroadcast(intent1);
					setResult(IBaes.UPDATEPWD_SAFETY_MOBILE);
					finish();
				}
				break;
			}
		}
	};
	
	class UpdateMobilrThread extends Thread{
		@Override
		public void run() {
			super.run();
			while(true){
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				max--;
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						getverfiy_updatemobile.setText("重新发送("+max+")");
						if(max==0||mobileFlag==true){
							max=60;
							mobileFlag=false;
							getverfiy_updatemobile.setClickable(true);
							getverfiy_updatemobile.setText("点击获取");
						}
					}
				});
				if(max==0||mobileFlag==true){
					break;
				}
			}
		}
	}
}
