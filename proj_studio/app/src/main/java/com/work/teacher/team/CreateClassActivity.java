package com.work.teacher.team;

import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.more.notice.AddNoticeOneActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 创建班级
 * 
 * @author 左丽姬
 */
public class CreateClassActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {

	private ImageView isnet_image;
	private LinearLayout suess_fail_class, ll_create_class;
	private ImageView create_sucess_fail;
	private TextView tv_sucess_fail;
	private EditText name, depict;
	private RadioButton examine;
	private boolean isCreate = true;// 判断是否创建成功
	private boolean isName = false;// 判断班级名称是否已经存在，已经数量是否已经达到极限
	private TextView top_text;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private String name_zhuce = "班级名称不能为空!";
	private Button top_btn;
	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createclass);
		initCreateClass();
	}

	public void initCreateClass() {

		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();
		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();
		dialog = new LoadingDialog(this);
		dialog.setOnLoadingDialogResultListener(this);

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
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("班级创建");
		top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("确定");
		top_btn.setOnClickListener(this);

		name = (EditText) findViewById(R.id.create_name_class);
		name.setHintTextColor(getResources().getColor(R.color.weakgrey));
		depict = (EditText) findViewById(R.id.create_depict_class);
		depict.setHintTextColor(getResources().getColor(R.color.weakgrey));
		examine = (RadioButton) findViewById(R.id.create_examine_class);

		ll_create_class = (LinearLayout) findViewById(R.id.ll_create_class);
		suess_fail_class = (LinearLayout) findViewById(R.id.suess_fail_class);
		ll_create_class.setVisibility(View.VISIBLE);
		suess_fail_class.setVisibility(View.GONE);
		create_sucess_fail = (ImageView) findViewById(R.id.create_sucess_fail);
		tv_sucess_fail = (TextView) findViewById(R.id.tv_sucess_fail);

		name.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					verfiyClassName();
				}
			}
		});

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
			exitClass();
			break;
		case R.id.top_btn:
			// 确定创建
			createClass();
			break;
		}
	}

	/** 验证班级名称 */
	public void verfiyClassName() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(CreateClassActivity.this, "网络不给力,请稍后...");
			return;
		}
		String class_name = name.getText().toString();
		if ("".equals(class_name.trim())) {
			IBaes.toastShow(CreateClassActivity.this, "班级名称不能为空!");
			return;
		}

		AjaxParams params = servecHttp.updatePwd(key, userId, class_name, "name");
		finalHttp.post(IBaes.CLASS_NAME_VERFIY, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}

		});

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Map<String, Object> map = null;
			switch (msg.what) {
			case 0:
				// 验证班级名称是否可用以及是否能够创建班级
				map = (Map<String, Object>) msg.obj;
				name_zhuce = map.get("zhuce").toString();
				IBaes.toastShow(CreateClassActivity.this, name_zhuce);
				int status = (Integer) map.get("status");
				if (status == 1)
					isName = true;
				else
					isName = false;
				break;
			case 1:
				// 创建班级
				map = (Map<String, Object>) msg.obj;
				int s = (Integer) map.get("status");
				if (s == 1) {
					isCreate = true;
					ll_create_class.setVisibility(View.GONE);
					suess_fail_class.setVisibility(View.VISIBLE);
					top_btn.setVisibility(View.GONE);
					create_sucess_fail.setImageResource(R.drawable.sign_check_icon);
					tv_sucess_fail.setText("创建成功!");
					Intent intent = new Intent(IBaes.ACTION_CLASS_CREATE);
					sendBroadcast(intent);
				} else {
					isCreate = false;
					ll_create_class.setVisibility(View.GONE);
					suess_fail_class.setVisibility(View.VISIBLE);
					top_btn.setVisibility(View.GONE);
					create_sucess_fail.setImageResource(R.drawable.sign_error_icon);
					tv_sucess_fail.setText("创建失败!");
				}
				break;
			}
		}
	};

	/** 创建班级提交 */
	public void createClass() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(CreateClassActivity.this, "网络不给力,请稍后...");
			return;
		}
		String class_name = name.getText().toString();
		if (!isName) {
			IBaes.toastShow(CreateClassActivity.this, name_zhuce);
			return;
		}
		String class_depict = depict.getText().toString();
		if ("".equals(class_depict.trim())) {
			IBaes.toastShow(CreateClassActivity.this, "班级描述不能为空!");
			return;
		}
		boolean class_examine = examine.isChecked();
		String examine = "2";
		if (!class_examine) {
			examine = "1";
		}
		AjaxParams params = servecHttp.createClass(key, userId, class_name, class_depict, examine);
		finalHttp.post(IBaes.CLASS_CREATE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	/** 退出创建班级 */
	public void exitClass() {
		if (!isCreate) {
			// 创建失败返回创建班级页面
			ll_create_class.setVisibility(View.VISIBLE);
			suess_fail_class.setVisibility(View.GONE);
			top_btn.setVisibility(View.VISIBLE);
			top_text.setText("创建班级");
			isCreate = true;
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitClass();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == LoadingDialog.SUCCESS) {
			setResult(100);
			finish();
		}
	}
}
