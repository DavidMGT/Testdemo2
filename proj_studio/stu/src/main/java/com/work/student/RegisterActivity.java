package com.work.student;

import java.util.Map;
import java.util.regex.Pattern;

import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.ServecHttp;
import com.work.student.view.LoadingDialog;
import com.work.student.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
/**
 * 注册
 *@author 左丽姬
 */
public class RegisterActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {

	private EditText name_reg, pwd_reg, pwd_two, tel_reg, name_verfiy;
	private Button register, get_verfiy,forget_pwd_reg, to_login;
	private ImageView isnet_image;
	private boolean flagRef=false;//判断注册按钮是否可用
	private int max=60;
	private boolean regFlag=false;
	private boolean islogin;//判断是否从登陆页面跳转的
	private boolean isMobile=true;//判断手机号码是否已经注册 
	private boolean isName=true;//判断用户名是否可以注册
	private String verfiycode="F";
	private ServecHttp servecHttp;
	private JsonData jsonData;
	private LoadingDialog load;
	private FinalHttp http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initReg();
	}

	public void initReg(){
		servecHttp=new ServecHttp();
		jsonData=new JsonData();
		load=new LoadingDialog(this);
		load.setOnLoadingDialogResultListener(this);
		http=new FinalHttp();
		
		islogin = getIntent().getBooleanExtra("islogin", true);
		IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
		IBaes.net_relative.setVisibility(View.GONE);
		isnet_image = (ImageView) findViewById(R.id.isnet_image);
		if(!IBaes.isNet(this)){
			//网络不存在时显示
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
		name_reg = (EditText) findViewById(R.id.name_reg);
		pwd_reg = (EditText) findViewById(R.id.pwd_reg);
		pwd_two = (EditText) findViewById(R.id.pwd_two);
		tel_reg = (EditText) findViewById(R.id.tel_reg);
		name_verfiy = (EditText) findViewById(R.id.name_verfiy);
		name_reg.setHintTextColor(getResources().getColor(R.color.weakgrey));
		pwd_reg.setHintTextColor(getResources().getColor(R.color.weakgrey));
		pwd_two.setHintTextColor(getResources().getColor(R.color.weakgrey));
		tel_reg.setHintTextColor(getResources().getColor(R.color.weakgrey));
		name_verfiy.setHintTextColor(getResources().getColor(R.color.weakgrey));
		
		register = (Button) findViewById(R.id.register);
		get_verfiy = (Button) findViewById(R.id.get_verfiy);
		forget_pwd_reg = (Button) findViewById(R.id.forget_pwd_reg);
		to_login = (Button) findViewById(R.id.to_login);
		
		name_reg.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String  name=name_reg.getText().toString();
				String  pwd=pwd_reg.getText().toString();
				String  pass=pwd_two.getText().toString();
				String  tel=tel_reg.getText().toString();
				String  verfiy=name_verfiy.getText().toString();
				if(!"".equals(name)&&!"".equals(pwd)&&!"".equals(pass)&&!"".equals(tel)&&!"".equals(verfiy)){
					flagRef=true;
					register.setBackgroundColor(getResources().getColor(R.color.green));
				}else{
					flagRef=false;
					register.setBackgroundColor(getResources().getColor(R.color.weakgrey));
				}
			}
		});
		pwd_reg.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String  name=name_reg.getText().toString();
				String  pwd=pwd_reg.getText().toString();
				String  pass=pwd_two.getText().toString();
				String  tel=tel_reg.getText().toString();
				String  verfiy=name_verfiy.getText().toString();
				if(!"".equals(name)&&!"".equals(pwd)&&!"".equals(pass)&&!"".equals(tel)&&!"".equals(verfiy)){
					flagRef=true;
					register.setBackgroundColor(getResources().getColor(R.color.green));
				}else{
					flagRef=false;
					register.setBackgroundColor(getResources().getColor(R.color.weakgrey));
				}
			}
		});
		pwd_two.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String  name=name_reg.getText().toString();
				String  pwd=pwd_reg.getText().toString();
				String  pass=pwd_two.getText().toString();
				String  tel=tel_reg.getText().toString();
				String  verfiy=name_verfiy.getText().toString();
				if(!"".equals(name)&&!"".equals(pwd)&&!"".equals(pass)&&!"".equals(tel)&&!"".equals(verfiy)){
					flagRef=true;
					register.setBackgroundColor(getResources().getColor(R.color.green));
				}else{
					flagRef=false;
					register.setBackgroundColor(getResources().getColor(R.color.weakgrey));
				}
			}
		});
		tel_reg.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String  name=name_reg.getText().toString();
				String  pwd=pwd_reg.getText().toString();
				String  pass=pwd_two.getText().toString();
				String  tel=tel_reg.getText().toString();
				String  verfiy=name_verfiy.getText().toString();
				if(!"".equals(name)&&!"".equals(pwd)&&!"".equals(pass)&&!"".equals(tel)&&!"".equals(verfiy)){
					flagRef=true;
					register.setBackgroundColor(getResources().getColor(R.color.green));
				}else{
					flagRef=false;
					register.setBackgroundColor(getResources().getColor(R.color.weakgrey));
				}
				//验证手机号码是否已经注册
				String reg="^1[34578]{1}[0-9]{1}\\d{8}$";
				Pattern p = Pattern.compile(reg);  
				if(p.matcher(tel).find()){
					verfiyTel(tel);
				}
			}
		});
		name_verfiy.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String  name=name_reg.getText().toString();
				String  pwd=pwd_reg.getText().toString();
				String  pass=pwd_two.getText().toString();
				String  tel=tel_reg.getText().toString();
				String  verfiy=name_verfiy.getText().toString();
				if(!"".equals(name)&&!"".equals(pwd)&&!"".equals(pass)&&!"".equals(tel)&&!"".equals(verfiy)){
					flagRef=true;
					register.setBackgroundColor(getResources().getColor(R.color.green));
				}else{
					flagRef=false;
					register.setBackgroundColor(getResources().getColor(R.color.weakgrey));
				}
			}
		});
	
		name_reg.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus==false){
					verfiyUersName();
				}
			}
		});
		pwd_two.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				//判断两次密码是否一致
				if(hasFocus==false){
					String pwd=pwd_reg.getText().toString();
					String pass=pwd_two.getText().toString();
					if(!pwd.equals(pass)){
						IBaes.toastShow(RegisterActivity.this, "两次密码不一致");
					}
				}
			}
		});
		name_verfiy.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus==false){
					String nameverfiy=name_verfiy.getText().toString();
					if(!verfiycode.equals(nameverfiy)){
						IBaes.toastShow(RegisterActivity.this, "验证码输入错误，请重新输入");
					}
				}
			}
		});
		
		
		register.setOnClickListener(this);
		get_verfiy.setOnClickListener(this);
		forget_pwd_reg.setOnClickListener(this);
		to_login.setOnClickListener(this);
		isnet_image.setOnClickListener(this);
		IBaes.net_relative.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.register:
			if(flagRef==false)
				return ;
			if(!IBaes.isNet(this)){
				IBaes.toastShow(RegisterActivity.this, "网络不给力,请稍后...");
			}
			registerCommit();
			break;
		case R.id.get_verfiy:
			getVerFit();
			break;
		case R.id.forget_pwd_reg:
			regFlag=true;
			//忘记密码
			if(!islogin){
				finish();
				return ;
			}
			intent=new Intent(this, ForgetActivity.class);
			intent.putExtra("isForget", false);
			startActivityForResult(intent,IBaes.REG_FORGET);
			break;
		case R.id.to_login:
			regFlag=true;
			if(islogin){
				finish();
				return ;
			}
			intent=new Intent(this, LoginActivity.class);
			intent.putExtra("isRegister", 1);
			startActivity(intent);
			break;
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			//打开设置界面
			 Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
             startActivity(mIntent);
			break;
		}
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			Map<String, Object> map=null;
			switch (msg.what) {
			case -1:
				IBaes.toastShow(RegisterActivity.this, "网络连接错误");
				break;
			case 0:
				isMobile=false;
				IBaes.toastShow(RegisterActivity.this, "手机号已注册");
				break;
			case 1:
				isMobile=true;
				IBaes.toastShow(RegisterActivity.this, "手机号可以注册");
				break;
			case 2:
				//获取验证码
				map=(Map<String, Object>) msg.obj;
				int status=(Integer) map.get("status");
				String yzm=(String) map.get("yzm");
				if(status==0){
					IBaes.toastShow(RegisterActivity.this, yzm);
				}else{
					new RegVerFiyThread().start();
					IBaes.toastShow(RegisterActivity.this, yzm);
					verfiycode=(String) map.get("sjyzm");
				}
				break;
			case 3:
				//验证用户名是否已经注册
				int v=(Integer) msg.obj;
				if(v==0){
					isName=false;
					IBaes.toastShow(RegisterActivity.this, "用户名已注册");
				}
				if(v==1){
					isName=true;
					IBaes.toastShow(RegisterActivity.this, "用户名可以注册");
				}
				break;
			case 4:
				//注册
				int r=(Integer) msg.obj;
				if(r==0){
					IBaes.toastShow(RegisterActivity.this, "账号注册失败");
				}
				if(r==1){
					IBaes.toastShow(RegisterActivity.this, "账号注册成功，马上去登录吧!");
				}
				break;
			}
		}
	};
	/**验证用户名是否已经注册*/
	public void verfiyUersName(){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(RegisterActivity.this, "网络不给力,请稍后...");
			return ;
		}
		String name=name_reg.getText().toString();
		if("".equals(name)){
			IBaes.toastShow(RegisterActivity.this, "用户名不能为空，请填写...");
			return ;
		}
		AjaxParams params=servecHttp.verfiyMobile("username", name);
		http.post(IBaes.REGISTER_NAME,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				int status=jsonData.jsonInt(t.toString());
				Message msg=new Message();
				msg.obj=status;
				msg.what=3;
				handler.sendMessage(msg);
			}
		});
	}
	
	/**验证手机号是否已经注册*/
	public void verfiyTel(String tel){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(RegisterActivity.this, "网络不给力,请稍后...");
			return ;
		}
		AjaxParams params=servecHttp.verfiyMobile("mobile",tel);
		http.post(IBaes.REGISTER_MOBILE,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", "验证手机号："+t.toString());
				int status=jsonData.jsonInt(t.toString());
				handler.sendEmptyMessage(status);
			}
		});
	}
	
	/**
	 * 获取验证码
	 */
	public void getVerFit(){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(RegisterActivity.this, "网络不给力,请稍后...");
			return ;
		}
		get_verfiy.setClickable(false);
		register.setBackgroundColor(getResources().getColor(R.color.weakgrey));
		flagRef=false;
		regFlag=false;
		String reg="^1[34578]{1}[0-9]{1}\\d{8}$";
		String tel=tel_reg.getText().toString();
		Pattern p = Pattern.compile(reg);  
		if(!p.matcher(tel).find()){
			IBaes.toastShow(RegisterActivity.this, "手机格式不匹配,例:1342007826");
			return ;
		}
		AjaxParams params=servecHttp.verfiyMobile("mobile", tel);
		http.post(IBaes.REGISTER_VERFIY,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Map<String, Object> map=jsonData.jsonVerfiy(t.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=2;
				handler.sendMessage(msg);
			}
		});
	}
	/**注册*/
	public void registerCommit(){
		String  name=name_reg.getText().toString();
		String  pwd=pwd_reg.getText().toString();
		String  pass=pwd_two.getText().toString();
		String  tel=tel_reg.getText().toString();
		String  verfiy=name_verfiy.getText().toString();
		//判断用户名是否可用
		if(!isName){
			IBaes.toastShow(this, "用户名已注册");
			return ;
		}
		//判断两次密码是否一致
		if(!pwd.equals(pass)){
			IBaes.toastShow(this, "两次密码不一致,请确认密码...");
			return ;
		}
		//判断手机号码是否可用
		if(!isMobile){
			IBaes.toastShow(this, "手机号已注册");
			return ;
		}
		//判断验证码是否输入正确
		if(!verfiy.equals(verfiycode)){
			IBaes.toastShow(this, "验证码输入错误,请确认...");
			return ;
		}
		
		AjaxParams params=servecHttp.setRegister(name, pwd, tel, verfiy);
		http.post(IBaes.REGISTER, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				int status=jsonData.jsonInt(t.toString());
				Message msg=new Message();
				msg.obj=status;
				msg.what=4;
				handler.sendMessage(msg);
			}
		});
	}
	
	class RegVerFiyThread extends Thread{
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
						get_verfiy.setText("重新发送("+max+")");
						if(max==0||regFlag==true){
							max=60;
							regFlag=false;
							get_verfiy.setClickable(true);
							String  name=name_reg.getText().toString();
							String  pwd=pwd_reg.getText().toString();
							String  pass=pwd_two.getText().toString();
							String  tel=tel_reg.getText().toString();
							String  verfiy=name_verfiy.getText().toString();
							if(!"".equals(name)&&!"".equals(pwd)&&!"".equals(pass)&&!"".equals(tel)&&!"".equals(verfiy)){
								flagRef=true;
								register.setBackgroundColor(getResources().getColor(R.color.green));
							}
							get_verfiy.setText("点击获取");
						}
					}
				});
				if(max==0||regFlag==true){
					break;
				}
			}
		}
	}

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == LoadingDialog.SUCCESS) {
			setResult(100);
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==IBaes.REG_FORGET&&resultCode==IBaes.FORGET_REG){
			finish();
		}
	}
}
