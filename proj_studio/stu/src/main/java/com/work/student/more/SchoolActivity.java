package com.work.student.more;

import java.util.Map;

import com.work.student.R;
import com.work.student.RegisterActivity;
import com.work.student.bean.School;
import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
 * 绑定学校
 * @author 左丽姬
 */
public class SchoolActivity extends Activity implements OnClickListener {
	private ImageView isnet_image;
	private LinearLayout ll_update_school, ll_add_school, fail_sucess_school;
	private Button top_btn, cloes_sucess, update_school;
	private TextView name_school, code_school, permission_school, verson_school, tv_fail_sucess, name_sucess,
			code_sucess;
	private EditText update_name;
	private ImageView update_name_cancel, iv_fail_sucess;
	private boolean isSucess = false, isAdd = false, isSchool = false;
	private String school;
	int max = 10;
	private TextView date_back;
	private String key;
	private String userId;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private SchoolBroadCast sbc;
	private School sl=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school);
		initSchool();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		sbc=new SchoolBroadCast();
		IntentFilter filter=new IntentFilter(IBaes.ACTION_UPDATE_PERSONAL);
		registerReceiver(sbc, filter);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(sbc);
	}
	
	public void initSchool() {
		school = getIntent().getStringExtra("school");
		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();
		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();

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
		top_text.setText("绑定学校");
		top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("确定");
		top_btn.setOnClickListener(this);

		ll_update_school = (LinearLayout) findViewById(R.id.ll_update_school);// 学校绑定查询
		ll_add_school = (LinearLayout) findViewById(R.id.ll_add_school);// 修改绑定学校
		fail_sucess_school = (LinearLayout) findViewById(R.id.fail_sucess_school);// 成功或者失败

		if ("".equals(school.trim())) {
			isAdd = true;
			ll_add_school.setVisibility(View.VISIBLE);
			ll_update_school.setVisibility(View.GONE);
		} else {
			isAdd = false;
			ll_update_school.setVisibility(View.VISIBLE);
			ll_add_school.setVisibility(View.GONE);
		}

		if (ll_add_school.getVisibility() == View.VISIBLE) {
			top_btn.setVisibility(View.VISIBLE);
		} else {
			top_btn.setVisibility(View.GONE);
		}
		// 学校
		name_school = (TextView) findViewById(R.id.name_school);
		code_school = (TextView) findViewById(R.id.code_school);
		permission_school = (TextView) findViewById(R.id.permission_school);
		update_school = (Button) findViewById(R.id.update_school);
		verson_school = (TextView) findViewById(R.id.verson_school);
		update_school.setOnClickListener(this);

		// 绑定学校
		update_name = (EditText) findViewById(R.id.update_name);
		update_name_cancel = (ImageView) findViewById(R.id.update_name_cancel);
		update_name_cancel.setOnClickListener(this);

		// 成功、失败
		iv_fail_sucess = (ImageView) findViewById(R.id.iv_fail_sucess);
		tv_fail_sucess = (TextView) findViewById(R.id.tv_fail_sucess);
		name_sucess = (TextView) findViewById(R.id.name_sucess);
		code_sucess = (TextView) findViewById(R.id.code_sucess);

		date_back = (TextView) findViewById(R.id.date_back);
		cloes_sucess = (Button) findViewById(R.id.cloes_sucess);
		cloes_sucess.setOnClickListener(this);

		getDate();

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
			schoolExit();
			break;
		case R.id.top_btn:
			//绑定学校
			commitSchool();
			break;
		case R.id.cloes_sucess:
			schoolExit();
			break;
		case R.id.update_school:
			// 更改绑定
			ll_add_school.setVisibility(View.VISIBLE);
			top_btn.setVisibility(View.VISIBLE);
			ll_update_school.setVisibility(View.GONE);
			break;
		case R.id.update_name_cancel:
			update_name.setText("");
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//查询学校信息
				sl = (School) msg.obj;
				if (sl != null) {
					IBaes.toastShow(SchoolActivity.this, sl.getZhuce());
					if (sl.getStatus() == 1) {
						school=sl.getSchoolCode();
						name_school.setText("学校名称："+sl.getSchoolName());
						code_school.setText("学校编号："+sl.getSchoolCode());
						permission_school.setText("用户权限：已激活-全部权限");
						verson_school.setText(getVersion(sl.getUserPessmion()));
						update_name.setText(sl.getSchoolCode());
						if(fail_sucess_school.getVisibility()==View.VISIBLE){
							name_sucess.setText("学校名称："+sl.getSchoolName());
							code_sucess.setText("学校编号："+sl.getSchoolCode());
						}
					}
				}
				break;
			case 1:
				Map<String, Object> map=(Map<String, Object>) msg.obj;
				IBaes.toastShow(SchoolActivity.this, map.get("zhuce").toString());
				if(((Integer)map.get("status"))==1){
					isSucess=true;
					Intent intent=new Intent(IBaes.ACTION_UPDATE_PERSONAL);
					sendBroadcast(intent);
				}else{
					isSucess=false;
				}
				if (isSucess) {
					iv_fail_sucess.setImageResource(R.drawable.sign_check_icon);
					tv_fail_sucess.setText("成功绑定学校!");
					getDate();
				} else {
					iv_fail_sucess.setImageResource(R.drawable.sign_error_icon);
					tv_fail_sucess.setText("绑定失败!");
					name_sucess.setText("稍后请重试");
					code_sucess.setVisibility(View.GONE);
				}
				new SchoolThread().start();
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			schoolExit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 2 * 获取版本号 3 * @return 当前应用的版本号 4
	 */
	public String getVersion(String str) {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return  "当前软件版本："+str+" v"+version;
		} catch (Exception e) {
			e.printStackTrace();
			return "当前软件版本：免费版 v0.0";
		}
	}

	/** 查询学校信息 */
	public void getDate() {
		if(!IBaes.isNet(this)){
			IBaes.toastShow(SchoolActivity.this, "网络不给力,请稍后...");
			return ;
		}
		AjaxParams params = servecHttp.keyAndId(key, userId);
		finalHttp.post(IBaes.QUEY_SCHOOL, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.i("test", t.toString());
				School jsonSchool = jsonDate.jsonSchool(t.toString());
				Message msg = new Message();
				msg.obj = jsonSchool;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	/** 绑定学校 */
	public void commitSchool() {
		if(!IBaes.isNet(this)){
			IBaes.toastShow(SchoolActivity.this, "网络不给力,请稍后...");
			return ;
		}
		ll_add_school.setVisibility(View.GONE);
		fail_sucess_school.setVisibility(View.VISIBLE);
		top_btn.setVisibility(View.GONE);
		String name=update_name.getText().toString();
		if("".equals(name.trim())){
			IBaes.toastShow(this, "学校编号不能为空");
			return ;
		}
		AjaxParams params=servecHttp.updateBindSchool(key, userId, name);
		finalHttp.post(IBaes.UPDATE_ADD_SCHOOL,params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map=jsonDate.jsonAvatar(t.toString());
				Message mes=new Message();
				mes.obj=map;
				mes.what=1;
				handler.sendMessage(mes);
			}
		});
	}

	/** 退出 */
	public void schoolExit() {
		if (fail_sucess_school.getVisibility() == View.VISIBLE) {
			// 失败--》绑定 成功--》学校显示
			isSchool = true;
			if (isSucess) {
				fail_sucess_school.setVisibility(View.GONE);
				ll_update_school.setVisibility(View.VISIBLE);
			} else {
				ll_add_school.setVisibility(View.VISIBLE);
				top_btn.setVisibility(View.VISIBLE);
				fail_sucess_school.setVisibility(View.GONE);
			}
			return;
		}
		if (ll_add_school.getVisibility() == View.VISIBLE) {
			if (isAdd) {
				finish();
				return;
			}
			ll_add_school.setVisibility(View.GONE);
			top_btn.setVisibility(View.GONE);
			ll_update_school.setVisibility(View.VISIBLE);
			return;
		}
		if (ll_update_school.getVisibility() == View.VISIBLE) {
			finish();
			return;
		}
	}

	;

	class SchoolThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (true) {
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
						date_back.setText(max + "秒之后关闭，");
						if (max == 0) {
							schoolExit();
						}
						if (max == 0 || isSchool == true) {
							max = 10;
							isSchool = false;
						}
					}
				});
				if (max == 0 || isSchool == true) {
					Log.e("test", "isSchool=" + isSchool);
					break;
				}
			}
		}
	}

	class SchoolBroadCast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(IBaes.ACTION_UPDATE_PERSONAL.equals(intent.getAction())){
				getDate();
			}
		}
		
	}
}
