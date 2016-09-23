package com.work.student.team;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.work.student.R;
import com.work.student.adapter.ClassDetailsAdapter;
import com.work.student.bean.ClassDetails;
import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;
import com.work.student.view.LoadingDialog;
import com.work.student.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 班级详情
 * 
 * @author 左丽姬
 */
public class TeaClassDetailsActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {
	private ImageView isnet_image;
	private String class_name;
	private ImageView details_sweep_class;
	private TextView details_sweep_code_class, details_id_class, details_date_class, details_synopsis_class;
	private ListView teaclass_details;
	private TextView teaclass_nodata;
	private String class_id;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private ClassDetailsAdapter adapter;
	private List<ClassDetails> details = new ArrayList<ClassDetails>();
	private com.work.student.view.LoadingDialog dialog;
	private String cid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teaclass_details);
		initTeaClassDetails();
	}

	public void initTeaClassDetails() {

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
		top_btn.setText("退出");
		top_btn.setOnClickListener(this);

		details_sweep_class = (ImageView) findViewById(R.id.details_sweep_class);
		details_sweep_code_class = (TextView) findViewById(R.id.details_sweep_code_class);
		details_id_class = (TextView) findViewById(R.id.details_id_class);
		details_date_class = (TextView) findViewById(R.id.details_date_class);
		details_synopsis_class = (TextView) findViewById(R.id.details_synopsis_class);

		teaclass_details = (ListView) findViewById(R.id.teaclass_details);
		teaclass_nodata = (TextView) findViewById(R.id.teaclass_nodata);
		adapter = new ClassDetailsAdapter(this, details);
		teaclass_details.setAdapter(adapter);
		teaclassDetails();
	}

	/** 获取详情数据 */
	public void teaclassDetails() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(TeaClassDetailsActivity.this, "网络不给力,请稍后...");
			return;
		}
		dialog.show();
		dialog.setText("加载中...");
		AjaxParams params = servecHttp.updatePwd(key, userId, class_id, "cid");
		finalHttp.post(IBaes.CLASS_DETAILS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonClassDetails(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
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
					String erweima = map.get("erweima").toString();
					if (erweima != null && !"".equals(erweima))
						FinalBitmap.create(TeaClassDetailsActivity.this).display(details_sweep_class, erweima);
					cid = map.get("cid").toString();
					details_id_class.setText("班级  ID：" + cid);
					GregorianCalendar create_date = new GregorianCalendar();
					create_date.setTimeInMillis(Long.parseLong(map.get("startime").toString()) * 1000);
					SimpleDateFormat create_sdf = new SimpleDateFormat("yyyy-MM-dd");
					details_date_class = (TextView) findViewById(R.id.add_date_class);
					String content = map.get("content").toString();
					if ("null".equals(content)) {
						content = "暂无";
					}
					details_synopsis_class.setText("班级简介：" + content);
					List<ClassDetails> list = (List<ClassDetails>) map.get("lists");
					Log.i("test", "list.size()=" + list.size());
					if (list != null && list.size() > 0) {
						details.addAll(list);
						adapter.notifyDataSetChanged();
						IBaes.setGroupHeight(teaclass_details);
						teaclass_nodata.setVisibility(View.GONE);
						teaclass_details.setVisibility(View.VISIBLE);
					} else {
						teaclass_nodata.setVisibility(View.VISIBLE);
						teaclass_details.setVisibility(View.GONE);
					}
				}
				break;
			case 1:
				// 退出该班级
				if (status == 1) {
					Intent intent = new Intent(IBaes.ACTION_CLASS_CREATE);
					sendBroadcast(intent);
					finish();
				}
				IBaes.toastShow(TeaClassDetailsActivity.this, map.get("zhuce").toString());
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
			finish();
			break;
		case R.id.top_btn:
			finish();
			break;
		}
	}

	/** 退出该班级 */
	public void exitClass() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(TeaClassDetailsActivity.this, "网络不给力,请稍后...");
			return;
		}
		if ("".equals(cid)) {
			return;
		}
		Log.i("test", IBaes.CLASS_QUITE_EXIT);
		dialog.show();
		dialog.setText("正在退出...");
		AjaxParams params = servecHttp.updatePwd(key, userId, cid, "cid");
		finalHttp.post(IBaes.CLASS_QUITE_EXIT, params, new AjaxCallBack<Object>() {
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

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == LoadingDialog.SUCCESS) {
			setResult(100);
			finish();
		}
	}
}
