package com.work.teacher.team;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.R.string;
import com.work.teacher.more.notice.AddNoticeOneActivity;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 创建班级
 * 
 * @author 左丽姬
 */
public class AddClassActivity extends Activity implements OnClickListener {

	private ImageView isnet_image;
	private EditText add_code_class;
	private LinearLayout ll_add_class, add_suess_fail_class, sousuo_class;
	private TextView top_text;
	private boolean isAdd = true;
	private boolean isQuery = false;
	private ImageView add_sucess_fail;
	private TextView tvadd_sucess_fail, tvexamine_sucess_fail;
	private TextView code_class, sweep_code_class, add_id_class, add_name_class, add_date_class, add_synopsis_class;
	private ImageView sweep_class;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private Button top_btn;
	private String zhuce = "班级编号不能为空!";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addclass);
		initCreateClass();
	}

	public void initCreateClass() {

		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();
		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();

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
		top_text.setText("加入班级");
		top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("确定");
		top_btn.setOnClickListener(this);

		add_code_class = (EditText) findViewById(R.id.add_code_class);
		add_code_class.setHintTextColor(getResources().getColor(R.color.weakgrey));

		ll_add_class = (LinearLayout) findViewById(R.id.ll_add_class);
		ll_add_class.setVisibility(View.VISIBLE);

		// 加入成功和失败
		add_suess_fail_class = (LinearLayout) findViewById(R.id.add_suess_fail_class);
		add_suess_fail_class.setVisibility(View.GONE);
		add_sucess_fail = (ImageView) findViewById(R.id.add_sucess_fail);
		tvadd_sucess_fail = (TextView) findViewById(R.id.tvadd_sucess_fail);
		tvexamine_sucess_fail = (TextView) findViewById(R.id.tvexamine_sucess_fail);

		// 搜索结果
		sousuo_class = (LinearLayout) findViewById(R.id.sousuo_class);
		sousuo_class.setVisibility(View.GONE);
		code_class = (TextView) findViewById(R.id.code_class);
		sweep_class = (ImageView) findViewById(R.id.sweep_class);
		sweep_code_class = (TextView) findViewById(R.id.sweep_code_class);
		add_id_class = (TextView) findViewById(R.id.add_id_class);
		add_name_class = (TextView) findViewById(R.id.add_name_class);
		add_date_class = (TextView) findViewById(R.id.add_date_class);
		add_synopsis_class = (TextView) findViewById(R.id.add_synopsis_class);

		add_code_class.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				queryClass();
			}
		});

	}

	/** 查询班级 */
	public void queryClass() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(AddClassActivity.this, "网络不给力,请稍后...");
			return;
		}
		String code = add_code_class.getText().toString();
		if ("".equals(code.trim())) {
			IBaes.toastShow(AddClassActivity.this, "班级ID不能为空");
			return;
		}
		AjaxParams params = servecHttp.updatePwd(key, userId, code, "name");
		finalHttp.post(IBaes.CLASS_ADD_READ, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonAddQeuryClass(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Map<String, Object> map = (Map<String, Object>) msg.obj;
			int status = (Integer) map.get("status");
			switch (msg.what) {
			case 0:
				if (status == 1) {
					isQuery = true;
					sousuo_class.setVisibility(View.VISIBLE);
					code_class.setText("班级编号:" + map.get("cid").toString());
					String erweima = map.get("erweima").toString();
					if (erweima != null && !"".equals(erweima))
						FinalBitmap.create(AddClassActivity.this).display(sweep_class, erweima);
					sweep_code_class.setVisibility(View.GONE);
					add_id_class.setText("班级  ID：" + map.get("cid").toString());
					add_name_class.setText("班级名称：" + map.get("classname").toString());
					GregorianCalendar create_date = new GregorianCalendar();
					create_date.setTimeInMillis(Long.parseLong(map.get("startime").toString()) * 1000);
					SimpleDateFormat create_sdf = new SimpleDateFormat("yyyy-MM-dd");
					add_date_class = (TextView) findViewById(R.id.add_date_class);
					add_synopsis_class.setText("班级简介：" + map.get("content").toString());
				} else {
					zhuce = (String) map.get("zhuce");
					isQuery = false;
				}
				break;
			case 1:
				switch (status) {
				case 0:
					isAdd = false;
					break;
				case 1:
					isAdd = true;
					break;
				case 2:
					isAdd = false;
					break;
				case 3:
					isAdd = false;
					break;
				case 4:
					isAdd = true;
					break;
				case 5:
					isAdd = true;
					break;
				case 6:
					isAdd = false;
					break;
				case 7:
					isAdd = true;
					break;
				case 8:
					isAdd = true;
					break;
				case 9:
					isAdd = false;
					break;
				}
				top_btn.setVisibility(View.GONE);
				ll_add_class.setVisibility(View.GONE);
				add_suess_fail_class.setVisibility(View.VISIBLE);
				if (isAdd) {
					top_text.setText("申请成功");
					add_sucess_fail.setImageResource(R.drawable.sign_check_icon);
					tvadd_sucess_fail.setText("申请成功");
					Intent intent = new Intent(IBaes.ACTION_CLASS_CREATE);
					sendBroadcast(intent);
				} else {
					add_sucess_fail.setImageResource(R.drawable.sign_error_icon);
					top_text.setText("申请失败");
					tvadd_sucess_fail.setText("申请失败");
				}
				tvexamine_sucess_fail.setText(map.get("zhuce").toString());
				break;
			}
		}
	};

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
			exitAddClass();
			break;
		case R.id.top_btn:
			// 确认
			addClass();
			break;
		}
	}

	/** 创建班级提交 */
	public void addClass() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(AddClassActivity.this, "网络不给力,请稍后...");
			return;
		}
		String dd_code = add_code_class.getText().toString();
		if (!isQuery) {
			IBaes.toastShow(AddClassActivity.this, zhuce);
			return;
		}

		AjaxParams params = servecHttp.updatePwd(key, userId, dd_code, "cid");
		finalHttp.post(IBaes.CLASS_ADD, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	/** 退出创建班级 */
	public void exitAddClass() {
		if (!isAdd) {
			// 创建失败返回创建班级页面
			ll_add_class.setVisibility(View.VISIBLE);
			add_suess_fail_class.setVisibility(View.GONE);
			top_text.setText("创建班级");
			isAdd = true;
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitAddClass();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
