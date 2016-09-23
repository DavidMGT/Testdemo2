package com.work.teacher.team;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.adapter.ClassManageAdapter;
import com.work.teacher.bean.ClassDetails;
import com.work.teacher.bean.ClassPersionDetails;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;
import com.work.teacher.view.SwipeListView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 班级管理
 * 
 * @author 左丽姬
 */
public class ClassManageActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {
	private ImageView isnet_image;
	private String class_id;
	private String class_name;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private LoadingDialog dialog;
	private SwipeListView lv_class_manage;
	private TextView id_class_manage, date_class_manage, name_class_manage, synopsis_class_manage, tv_class_nomanage;
	private RadioButton examine_class_manage;
	private List<ClassPersionDetails> lists = new ArrayList<ClassPersionDetails>();
	private ClassManageAdapter adapter;
	private String cid = "";
	private AgreeRefuseBroadCast agreeCast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classmanage);
		initManage();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		agreeCast = new AgreeRefuseBroadCast();
		IntentFilter filter = new IntentFilter(IBaes.ACTION_CLASS_DELMEMBER);
		registerReceiver(agreeCast, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(agreeCast);
	}

	public void initManage() {

		dialog = new LoadingDialog(this);
		dialog.setOnLoadingDialogResultListener(this);

		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();
		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();

		class_name = getIntent().getStringExtra("class_name");
		class_id = getIntent().getStringExtra("class_id");

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
		top_text.setText(class_name);
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("删除");
		top_btn.setOnClickListener(this);

		id_class_manage = (TextView) findViewById(R.id.id_class_manage);
		date_class_manage = (TextView) findViewById(R.id.date_class_manage);
		name_class_manage = (TextView) findViewById(R.id.name_class_manage);
		synopsis_class_manage = (TextView) findViewById(R.id.synopsis_class_manage);
		tv_class_nomanage = (TextView) findViewById(R.id.tv_class_nomanage);
		examine_class_manage = (RadioButton) findViewById(R.id.examine_class_manage);
		lv_class_manage = (SwipeListView) findViewById(R.id.lv_class_manage);
		adapter = new ClassManageAdapter(this, lists, lv_class_manage.getRightViewWidth());
		adapter.setOnRightItemClickListener(lv_class_manage);
		lv_class_manage.setAdapter(adapter);
		dialog.show();
		dialog.setText("加载中...");
		manageData();
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
		case R.id.top_btn:
			// 删除班级
			delManageClass();
			break;
		}
	}

	/** 获取班级信息 */
	public void manageData() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(ClassManageActivity.this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = servecHttp.updatePwd(key, userId, class_id, "cid");
		finalHttp.post(IBaes.CLASS_QUERY_MANAGE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonClassManage(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	/** 删除班级 */
	public void delManageClass() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(ClassManageActivity.this, "网络不给力,请稍后...");
			return;
		}
		dialog.show();
		dialog.setText("删除中...");
		AjaxParams params = servecHttp.updatePwd(key, userId, cid, "cid");
		finalHttp.post(IBaes.CLASS_DELETE_MANAGE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});

		// CLASS_DELETE_MANAGE
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (dialog.isShowing()) {
				dialog.dismiss();
				dialog.cancel();
			}
			Map<String, Object> map = (Map<String, Object>) msg.obj;
			int status = (Integer) map.get("status");
			switch (msg.what) {
			case 0:
				if (status == 1) {
					cid = map.get("cid").toString();
					id_class_manage.setText("班级  ID：" + cid);
					GregorianCalendar create_date = new GregorianCalendar();
					create_date.setTimeInMillis(Long.parseLong(map.get("startime").toString()) * 1000);
					SimpleDateFormat create_sdf = new SimpleDateFormat("yyyy-MM-dd");
					date_class_manage = (TextView) findViewById(R.id.add_date_class);
					String content = map.get("content").toString();
					if ("null".equals(content)) {
						content = "暂无";
					}
					synopsis_class_manage.setText("班级简介：" + content);
					String checktype = map.get("checktype").toString();
					if ("2".equals(checktype))
						examine_class_manage.setChecked(true);
					else
						examine_class_manage.setChecked(false);
					List<ClassPersionDetails> list = (List<ClassPersionDetails>) map.get("lists");
					if (list != null && list.size() > 0) {
						lists.addAll(list);
						adapter.notifyDataSetChanged();
						IBaes.setGroupHeight(lv_class_manage);
						tv_class_nomanage.setVisibility(View.GONE);
						lv_class_manage.setVisibility(View.VISIBLE);
					} else {
						tv_class_nomanage.setVisibility(View.VISIBLE);
						lv_class_manage.setVisibility(View.GONE);
					}
				}
				break;
			case 1:
				if (status == 1) {
					Intent intent = new Intent(IBaes.ACTION_CLASS_DEL);
					sendBroadcast(intent);
					finish();
				}
				IBaes.toastShow(ClassManageActivity.this, map.get("zhuce").toString());
				break;
			}
		}
	};

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == LoadingDialog.SUCCESS) {
			setResult(100);
			finish();
		}
	}

	class AgreeRefuseBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (IBaes.ACTION_CLASS_DELMEMBER.equals(intent.getAction())) {
				lists.clear();
				manageData();
			}
		}

	}
}
