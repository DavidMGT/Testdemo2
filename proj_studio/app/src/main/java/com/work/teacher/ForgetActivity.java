package com.work.teacher;

import java.util.Map;
import java.util.regex.Pattern;

import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
/**
 * 忘记密码
 *@author 左丽姬
 */
public class ForgetActivity extends Activity implements OnClickListener {

	private boolean isForget;
	private ImageView isnet_image;
	private EditText name_forget;
	private Button forget,forget_to_login, forget_to_reg;
	private boolean flagForget=false;
	private boolean forgetsub=false;
	private int max=60;
	FinalHttp http;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		initForget();
	}

	public void initForget(){
		http=new FinalHttp();
		isForget=getIntent().getBooleanExtra("isForget", false);
		IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
		IBaes.net_relative.setVisibility(View.GONE);
		isnet_image = (ImageView) findViewById(R.id.isnet_image);
		if(!IBaes.isNet(this)){
			//网络不存在时显示
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
		
		name_forget = (EditText) findViewById(R.id.name_forget);
		name_forget.setHintTextColor(getResources().getColor(R.color.weakgrey));
		forget = (Button) findViewById(R.id.forget);
		forget_to_login = (Button) findViewById(R.id.forget_to_login);
		forget_to_reg = (Button) findViewById(R.id.forget_to_reg);
		
		name_forget.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				forget.setBackgroundColor(getResources().getColor(R.color.weakgrey));
				flagForget=false;
				if(!"".equals(name_forget.getText().toString())){
					flagForget=true;
					forget.setBackgroundColor(getResources().getColor(R.color.green));
				}
			}
		});
		
		isnet_image.setOnClickListener(this);
		forget.setOnClickListener(this);
		forget_to_login.setOnClickListener(this);
		forget_to_reg.setOnClickListener(this);
		IBaes.net_relative.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			//打开设置界面
			 Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
             startActivity(mIntent);
			break;
		case R.id.forget:
			//找回密码
			if(flagForget)
			{
				getForget();
			}
			break;
		case R.id.forget_to_login:
			forgetsub=true;
			//立即登陆
			if(isForget){
				finish();
				return ;
			}
			intent=new Intent(this, LoginActivity.class);
			intent.putExtra("isRegister", 2);
			startActivity(intent);
			break;
		case R.id.forget_to_reg:
			forgetsub=true;
			if(!isForget){
				finish();
				return ;
			}
			intent=new Intent(this, RegisterActivity.class);
			intent.putExtra("islogin", false);
			startActivity(intent);
			break;
		}
	}
	
	/**
	 * 找回密码
	 */
	public void getForget(){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(ForgetActivity.this, "网络不给力,请稍后...");
			return ;
		}
		forget.setClickable(false);
		forget.setBackgroundColor(getResources().getColor(R.color.weakgrey));
		flagForget=false;
		forgetsub=false;
		String reg="^1[34578]{1}[0-9]{1}\\d{8}$";
		String tel=name_forget.getText().toString();
		Pattern p = Pattern.compile(reg);  
		if(!p.matcher(tel).find()){
			Toast.makeText(this,"手机号码匹配不成功", 0).show();
			return ;
		}
		AjaxParams params=new AjaxParams();
		params.put("mobile", tel);
		http.post(IBaes.MOBILE_FORGET,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map=new JsonData().jsonForGet(t.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=0;
				handler.sendMessage(msg);
			}
		});
	}
	
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				new ForgetThread().start();
				Map<String, Object> map=(Map<String, Object>) msg.obj;
				int status=(Integer) map.get("status");
				String zhuce=map.get("zhuce").toString();
				IBaes.toastShow(ForgetActivity.this, zhuce);
				if(status==1){
					if(!isForget){
						ForgetActivity.this.setResult(IBaes.FORGET_REG);
					}
					finish();
				}
				break;
			}
			
		}
	};
	class ForgetThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
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
						forget.setText("找回密码        "+max+"s");
						if(max==0||forgetsub==true){
							max=60;
							forgetsub=false;
							forget.setClickable(true);
							String  name=name_forget.getText().toString();
							if(!"".equals(name)){
								flagForget=true;
								forget.setBackgroundColor(getResources().getColor(R.color.green));
							}
							forget.setText("找回密码");
						}
					}
				});
				if(max==0||forgetsub==true){
					break;
				}
			}
		}
	}
}
