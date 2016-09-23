package com.work.teacher.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.TeaClassActivity;
import com.work.teacher.adapter.HistoryTyeAdapter;
import com.work.teacher.bean.HistoryType;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 班级历史
 * 
 * @author 左丽姬
 */
public class ClassHistoryActivity extends Activity implements OnClickListener {
	private ImageView isnet_image;
	private ListView classname_lists;
	private TextView classname_data;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private List<HistoryType> types = new ArrayList<HistoryType>();
	private HistoryTyeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classhistory);
		initHistory();
	}

	public void initHistory() {

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
		TextView top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("历史班级");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);

		classname_lists = (ListView) findViewById(R.id.classname_lists);
		adapter = new HistoryTyeAdapter(this, types);
		classname_lists.setAdapter(adapter);
		classname_data = (TextView) findViewById(R.id.classname_data);
		historyData();
	}

	/** 获取班级历史数据 */
	public void historyData() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(ClassHistoryActivity.this, "网络不给力,请稍后...");
			return;
		}

		AjaxParams params = servecHttp.keyAndId(key, userId);
		finalHttp.post(IBaes.CLASS_HISTORY, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonClassHistorys(t.toString());
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
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				int status = (Integer) map.get("status");
				if (status == 1) {
					List<HistoryType> list = (List<HistoryType>) map.get("history");
					if (list.size() > 0) {
						classname_data.setVisibility(View.GONE);
						classname_lists.setVisibility(View.VISIBLE);
						types.addAll(list);
						adapter.notifyDataSetChanged();
					} else {
						classname_data.setVisibility(View.VISIBLE);
						classname_lists.setVisibility(View.GONE);
					}
				} else {
					classname_data.setVisibility(View.VISIBLE);
					classname_lists.setVisibility(View.GONE);
					IBaes.toastShow(ClassHistoryActivity.this, map.get("zhuce").toString());
				}
				break;
			}
		}
	};

}
