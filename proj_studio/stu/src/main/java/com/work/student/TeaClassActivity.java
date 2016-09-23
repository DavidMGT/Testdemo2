package com.work.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.student.adapter.TeaClassAdapter;
import com.work.student.bean.SubAndGrade;
import com.work.student.more.notice.AddNoticeOneActivity;
import com.work.student.team.AddClassActivity;
import com.work.student.team.ClassHistoryActivity;
import com.work.student.team.CreateClassActivity;
import com.work.student.team.TeaClassDetailsActivity;
import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 班级
 *@author 左丽姬
 */
public class TeaClassActivity extends Activity implements OnClickListener {

	private ImageView isnet_image;
	private LinearLayout sys_class, add_create, add_other;
	private ListView sys_teaclass, addclass_teaclass, addother_teaclass;
	private Button create, add;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private TeaClassAdapter z_adpater, j_adpater, x_adpter;
	private List<SubAndGrade> z_list = new ArrayList<SubAndGrade>();
	private List<SubAndGrade> j_list = new ArrayList<SubAndGrade>();
	private List<SubAndGrade> x_list = new ArrayList<SubAndGrade>();
	private TeaClassBroadCast broad = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teaclass);
		initTeaClass();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		broad = new TeaClassBroadCast();
		IntentFilter filter = new IntentFilter(IBaes.ACTION_CLASS_CREATE);
		registerReceiver(broad, filter);
		IntentFilter filter1 = new IntentFilter(IBaes.ACTION_LOGIN);
		registerReceiver(broad, filter1);
		IntentFilter filter3 = new IntentFilter(IBaes.ACTION_NET);
		registerReceiver(broad, filter3);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broad);
	}

	public void initTeaClass() {

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
		top_back.setVisibility(View.GONE);
		TextView top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("班级列表");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("历史");
		top_btn.setOnClickListener(this);

		// 备用二期项目
		sys_class = (LinearLayout) findViewById(R.id.sys_class);
		sys_teaclass = (ListView) findViewById(R.id.sys_teaclass);
		x_adpter = new TeaClassAdapter(this, x_list);
		sys_teaclass.setAdapter(x_adpter);

		add_create = (LinearLayout) findViewById(R.id.add_create);
		add_create.setVisibility(View.VISIBLE);
		add_other = (LinearLayout) findViewById(R.id.add_other);
		add_other.setVisibility(View.VISIBLE);
		addclass_teaclass = (ListView) findViewById(R.id.addclass_teaclass);
		addother_teaclass = (ListView) findViewById(R.id.addother_teaclass);
		z_adpater = new TeaClassAdapter(this, z_list);
		addclass_teaclass.setAdapter(z_adpater);
		j_adpater = new TeaClassAdapter(this, j_list);
		addother_teaclass.setAdapter(j_adpater);

		create = (Button) findViewById(R.id.create_teaclass);
		add = (Button) findViewById(R.id.add_teaclass);
		create.setOnClickListener(this);
		add.setOnClickListener(this);
		queyLists();

		sys_teaclass.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SubAndGrade sag = x_list.get(position);
				if (sag != null) {
					Intent intent = new Intent(TeaClassActivity.this, TeaClassDetailsActivity.class);
					intent.putExtra("class_name", sag.getName());
					intent.putExtra("class_id", sag.getId());
					startActivity(intent);
				}
			}
		});
		addclass_teaclass.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SubAndGrade sag = z_list.get(position);
				if (sag != null) {
					Intent intent = new Intent(TeaClassActivity.this, TeaClassDetailsActivity.class);
					intent.putExtra("class_name", sag.getName());
					intent.putExtra("class_id", sag.getId());
					startActivity(intent);
				}
			}
		});
		addother_teaclass.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SubAndGrade sag = j_list.get(position);
				if (sag != null) {
					Intent intent = new Intent(TeaClassActivity.this, TeaClassDetailsActivity.class);
					intent.putExtra("class_name", sag.getName());
					intent.putExtra("class_id", sag.getId());
					startActivity(intent);
				}
			}
		});

	}

	/** 查询班级数据 */
	public void queyLists() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(TeaClassActivity.this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = servecHttp.keyAndId(key, userId);
		finalHttp.post(IBaes.CLASS_LISTS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonClassLists(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent mIntent = null;
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			// 打开设置界面
			mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
			startActivity(mIntent);
			break;
		case R.id.top_btn:
			// 历史
			mIntent = new Intent(this, ClassHistoryActivity.class);
			startActivity(mIntent);
			break;
		case R.id.create_teaclass:
			// 创建班级
			mIntent = new Intent(this, CreateClassActivity.class);
			startActivity(mIntent);
			break;
		case R.id.add_teaclass:
			// 加入班级
			mIntent = new Intent(this, AddClassActivity.class);
			startActivity(mIntent);
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				int status = (Integer) map.get("status");
				if (status == 1) {
					List<SubAndGrade> list_z = (List<SubAndGrade>) map.get("z_class");
					List<SubAndGrade> list_j = (List<SubAndGrade>) map.get("j_class");
					List<SubAndGrade> list_x = (List<SubAndGrade>) map.get("x_class");
					if (list_z != null) {
						if (list_z.size() > 0) {
							z_list.addAll(list_z);
							z_adpater.notifyDataSetChanged();
						}
						IBaes.setGroupHeight(addclass_teaclass);
					}
					if (list_j != null) {
						if (list_j.size() > 0) {
							j_list.addAll(list_j);
							j_adpater.notifyDataSetChanged();
							add_other.setVisibility(View.VISIBLE);
						} else {
							add_other.setVisibility(View.GONE);
						}
						IBaes.setGroupHeight(addother_teaclass);
					} else {
						add_other.setVisibility(View.GONE);
					}

					if (list_x != null) {
						if (list_x.size() > 0) {
							x_list.addAll(list_x);
							x_adpter.notifyDataSetChanged();
							sys_class.setVisibility(View.VISIBLE);
						} else {
							sys_class.setVisibility(View.GONE);
						}
						IBaes.setGroupHeight(sys_teaclass);
					} else {
						sys_class.setVisibility(View.GONE);
					}
				}
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			HomeActivity.exit(TeaClassActivity.this);
		}
		return true;
	}

	class TeaClassBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (IBaes.ACTION_CLASS_CREATE.equals(intent.getAction()) || IBaes.ACTION_LOGIN.equals(intent.getAction())
					|| IBaes.ACTION_NET.equals(intent.getAction())) {
				key = SPUtils.get(TeaClassActivity.this, "key", "").toString();
				userId = SPUtils.get(TeaClassActivity.this, "userId", "").toString();
				z_list.clear();
				j_list.clear();
				x_list.clear();
				queyLists();
			}

		}

	}
}
