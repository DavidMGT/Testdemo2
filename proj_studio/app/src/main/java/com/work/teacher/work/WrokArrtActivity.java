package com.work.teacher.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.LeftTree;
import com.work.teacher.more.UpdatePwdActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.work.adapter.GradeMenuAdapter;
import com.work.teacher.work.adapter.ModeTypeAdapter;
import com.work.teacher.work.adapter.SpinnerMenuAdpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 设置属性
 * 
 * @author 左丽姬
 */
public class WrokArrtActivity extends Activity implements OnClickListener {
	private ImageView isnet_image;
	private ServecHttp servecHttp;
	private WorkJson jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private String sectionId = "", sectName = "", subjectCs = "", jiaocaiCs = "";// 保存章节ID,章节名称,学科CS，教材CS

	private RadioButton random;
	private EditText title, custom_name, summary;
	/** 保存是否随机 (1.随机，2.不随机) */
	private int torder = 1;
	private GridView mode_workarrt, type_workarrt;
	private List<LeftSubject> modes = new ArrayList<LeftSubject>(), types = new ArrayList<LeftSubject>();
	private ModeTypeAdapter modeAdapter, typeAdapter;
	private LeftSubject mode, type;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workarrt);
		initWorkArrt();
	}

	public void initWorkArrt() {

		servecHttp = new ServecHttp();
		jsonDate = new WorkJson();
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
		top_text.setText("设置属性");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("下一步");
		top_btn.setOnClickListener(this);

		ImageView left_menu_workarrt = (ImageView) findViewById(R.id.left_menu_workarrt);
		left_menu_workarrt.setOnClickListener(this);

		// 随机性
		random = (RadioButton) findViewById(R.id.random_workarrt);
		random.setOnClickListener(this);
		// 标题
		title = (EditText) findViewById(R.id.title_workarrt);
		title.setOnClickListener(this);
		title.setHintTextColor(getResources().getColor(R.color.whitegrey));
		custom_name = (EditText) findViewById(R.id.custom_name_workarrt);
		custom_name.setHintTextColor(getResources().getColor(R.color.whitegrey));
		// 简介
		summary = (EditText) findViewById(R.id.summary_workarrt);
		summary.setHintTextColor(getResources().getColor(R.color.whitegrey));

		mode_workarrt = (GridView) findViewById(R.id.mode_workarrt);
		type_workarrt = (GridView) findViewById(R.id.type_workarrt);
		modeAdapter = new ModeTypeAdapter(this, modes);
		typeAdapter = new ModeTypeAdapter(this, types);
		mode_workarrt.setAdapter(modeAdapter);
		type_workarrt.setAdapter(typeAdapter);
		mode_workarrt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				for (LeftSubject l : modes) {
					if (l != null)
						l.setFlag(false);
				}
				mode = modes.get(position);
				if (mode != null) {
					mode.setFlag(true);
					modeAdapter.notifyDataSetChanged();
				}
			}
		});
		type_workarrt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				for (LeftSubject l : types) {
					if (l != null)
						l.setFlag(false);
				}
				type = types.get(position);
				if (type != null) {
					type.setFlag(true);
					typeAdapter.notifyDataSetChanged();
					if ("自定义".equals(type.getCname().trim()))
						custom_name.setVisibility(view.VISIBLE);
					else
						custom_name.setVisibility(view.GONE);
				}
			}
		});
		custom_name.addTextChangedListener(new TextWatcher() {

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
				String custom = custom_name.getText().toString();
				if (custom.length() > 4) {
					IBaes.toastShow(WrokArrtActivity.this, "自定义作业类别名称不能大于4位数");
					custom = custom.substring(0, 4);
					custom_name.setText(custom);
					// 获取焦点将光标设在末尾
					Editable etext = custom_name.getText();
					Selection.setSelection(etext, etext.length());
				}
			}
		});
		modeAndtype();
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
		case R.id.left_menu_workarrt:
			// 侧滑菜单
			mIntent = new Intent(this, TreeDialog.class);
			startActivityForResult(mIntent, IBaes.WORK_ARRT_TREE);
			break;
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			// 下一步
			oneTotwo();
			break;
		case R.id.random_workarrt:
			// 是否随机
			if (torder == 1) {
				random.setChecked(false);
				torder = 2;
			} else {
				random.setChecked(true);
				torder = 1;
			}
			break;
			case R.id.title_workarrt:
					mIntent = new Intent(WrokArrtActivity.this, TreeDialog.class);
					startActivityForResult(mIntent, IBaes.WORK_ARRT_TREE);
			break;

		}
	}

	/** 获取作业模式、作业类别 */
	public void modeAndtype() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(WrokArrtActivity.this, "网络不给力,请稍后...");
			return;
		}
		finalHttp.get(IBaes.WORK_SETTING_ARRT, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonMode_Type(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});

	}

	/** 提交表单--》》下一步 */
	public void oneTotwo() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(WrokArrtActivity.this, "网络不给力,请稍后...");
			return;
		}
		if (mode == null) {
			IBaes.toastShow(this, "请选择题型");
			return;
		}
		if (type == null) {
			IBaes.toastShow(this, "请选择类型");
			return;
		}
		String custom = custom_name.getText().toString();
		if ("自定义".equals(type.getCname().trim())) {
			if ("".equals(custom.trim())) {
				IBaes.toastShow(this, "请填写自定义类别名称");
				return;
			}
		}
		if ("".equals(sectName.trim())) {
			IBaes.toastShow(this, "请选择章节");
			return;
		}
		String titleContent = title.getText().toString();
		if ("".equals(titleContent.trim())) {
			IBaes.toastShow(this, "请填写标题");
			return;
		}
		String summaryContent = summary.getText().toString();
		if ("".equals(summaryContent.trim())) {
			IBaes.toastShow(this, "请填写简介");
			return;
		}
		// Log.i("test", "key=" + key + ",userId=" + userId + ",ms=" +
		// mode.getCs() + ",title=" + titleContent + ",xueke=" + subjectCs +
		// ",jiaocai=" + jiaocaiCs
		// + ",jdid=" + sectionId + ",lx=" + type.getCs() + ",torder=" + torder
		// + "，content=" + summaryContent);
		AjaxParams params = servecHttp.addWorkCommit(key, userId, mode.getCs(), titleContent, subjectCs, jiaocaiCs,
				sectionId, type.getCs(), torder + "", summaryContent);
		finalHttp.post(IBaes.WORK_SETTING_ARRTCOMMITE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", "设置属性:" + t.toString());
				Map<String, Object> map = jsonDate.jsonsaveProperty(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Map<String, Object> map = (Map<String, Object>) msg.obj;
			int status = (Integer) map.get("status");
			switch (msg.what) {
			case 0:
				// 模式、类别获取结果
				if (status == 1) {
					List<LeftSubject> ms = (List<LeftSubject>) map.get("modes");
					List<LeftSubject> ts = (List<LeftSubject>) map.get("types");
					// 设置默认选择的值
					for (LeftSubject m : ms) {
						if(m!=null&&"标准模式".equals(m.getCname())){
							Log.i("test","进入了标准模式");
							m.setFlag(true);
							mode = new LeftSubject(m.getId(), m.getCname(), m.getPid(), m.getCs(), m.getPath());
						}
					}
					for (LeftSubject t : ts) {
						if(t!=null&&"作业".equals(t.getCname())){
							t.setFlag(true);
							type = new LeftSubject(t.getId(), t.getCname(), t.getPid(), t.getCs(), t.getPath());
						}
					}
					// 填充列表
					if (ms.size() % 3 != 0) {
						for (int i = 0; i < ms.size() % 3; i++) {
							ms.add(null);
						}
					}

					if (ts.size() % 3 != 0) {
						for (int i = 0; i < ts.size() % 3; i++) {
							ts.add(null);
						}
					}
					modes.addAll(ms);
					types.addAll(ts);
					modeAdapter.notifyDataSetChanged();
					typeAdapter.notifyDataSetChanged();
				}
				break;
			case 1:
				// 下一步返回
				if (status == 1) {
					//作业添加成功，改变作业列表
					Intent case_intent=new Intent(IBaes.ACTION_WORK_ADDWROK);
					sendBroadcast(case_intent);
					Intent intent = new Intent(WrokArrtActivity.this, WorkEditActivity.class);
					intent.putExtra("sectionId", sectionId);
					intent.putExtra("sectName", sectName);
					intent.putExtra("subjectCs", subjectCs);
					intent.putExtra("jiaocaiCs", jiaocaiCs);
					intent.putExtra("wid", map.get("data").toString());
					startActivity(intent);
					finish();
				}
				IBaes.toastShow(WrokArrtActivity.this, map.get("zhuce").toString());
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IBaes.WORK_ARRT_TREE) {
			sectionId = data.getStringExtra("sectionId");// 章节id
			sectName = data.getStringExtra("sectName");// 章节的名称
			subjectCs = data.getStringExtra("subjectCs");// 学科Cs
			jiaocaiCs = data.getStringExtra("jiaocaiCs");// 教材CS;
			title.setText(sectName);
			Editable etext = title.getText();
			Selection.setSelection(etext, etext.length());
		}
	}

}
