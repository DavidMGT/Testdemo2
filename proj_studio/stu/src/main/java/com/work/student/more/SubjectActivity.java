package com.work.student.more;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.student.R;
import com.work.student.RegisterActivity;
import com.work.student.adapter.SubjectAdapter;
import com.work.student.bean.SubAndGrade;
import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;
import com.work.student.view.LoadingDialog;
import com.work.student.view.LoadingDialog.OnLoadingDialogResultListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 科目年级
 * @author 左丽姬
 */
public class SubjectActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {

	private ImageView isnet_image;
	private LinearLayout gride_subject;

	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private List<SubAndGrade> gs = new ArrayList<SubAndGrade>();
	private List<SubAndGrade> lsags = new ArrayList<SubAndGrade>();
	private GridView gridview_grade, gridview_subject;
	private SubjectAdapter gAdapter, sAdapter;
	private Button update_subject_commit;
	private String subId = "";// 保存科目ID
	private String gradeId = "";// 保存年级ID
	private LoadingDialog load;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
		initSubject();
	}

	public void initSubject() {
		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();

		gradeId = getIntent().getStringExtra("grade");
		subId = getIntent().getStringExtra("subject");

		load = new LoadingDialog(this);
		load.setOnLoadingDialogResultListener(this);
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

		// 顶部设置
		ImageView top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(this);
		TextView top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("学段科目");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);

		gridview_grade = (GridView) findViewById(R.id.gridview_grade);
		gAdapter = new SubjectAdapter(SubjectActivity.this, gs, "grade");
		gridview_grade.setAdapter(gAdapter);
		gridview_grade.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				SubAndGrade su = gs.get(position);
				if (su != null) {
					for (SubAndGrade subAndGrade : gs) {
						if (subAndGrade != null)
							subAndGrade.setIscheck(false);
					}
					gradeId = su.getId();
					su.setIscheck(true);
					gAdapter.notifyDataSetChanged();
				}
			}
		});
		gridview_subject = (GridView) findViewById(R.id.gridview_subject);
		sAdapter = new SubjectAdapter(SubjectActivity.this, lsags, "subject");
		gridview_subject.setAdapter(sAdapter);
		gridview_subject.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				SubAndGrade su = lsags.get(position);
				if (su != null) {
					for (SubAndGrade subAndGrade : lsags) {
						if (subAndGrade != null)
							subAndGrade.setIscheck(false);
					}
					subId = su.getId();
					su.setIscheck(true);
					sAdapter.notifyDataSetChanged();
				}
			}
		});
		update_subject_commit = (Button) findViewById(R.id.update_subject_commit);
		update_subject_commit.setOnClickListener(this);
		getData();

	}

	public void getData() {
		if(!IBaes.isNet(this)){
			IBaes.toastShow(SubjectActivity.this, "网络不给力,请稍后...");
			return ;
		}
		finalHttp.post(IBaes.PERSONAL_SUBJECT_GRIDE, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Map<String, Object> map = jsonDate.jsonGradeAndSub(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 3;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (load.isShowing()) {
				load.dismiss();
				load.cancel();
			}
			switch (msg.what) {
			case 0:
				IBaes.toastShow(SubjectActivity.this, "年级科目修改失败");
				break;
			case 1:
				IBaes.toastShow(SubjectActivity.this, "年级科目修改成功");
				Intent intent = new Intent(IBaes.ACTION_UPDATE_PERSONAL);
				sendBroadcast(intent);
				finish();
				break;
			case 2:
				IBaes.toastShow(SubjectActivity.this, "用户不存在");
				break;
			case 3:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				int status = (Integer) map.get("status");
				if (status == 0) {
					IBaes.toastShow(SubjectActivity.this, "年级科目信息获取失败");
					return;
				}
				List<SubAndGrade> gsg = (List<SubAndGrade>) map.get("grades");
				if (!"".equals(gradeId.trim())) {
					for (SubAndGrade subAndGrade : gsg) {
						if (subAndGrade != null) {
							if (subAndGrade.getId().equals(gradeId)) {
								subAndGrade.setIscheck(true);
							}
						}
					}
				}
				gs.addAll(gsg);
				gAdapter.notifyDataSetChanged();
				Log.e("test", "gs.size=" + gs.size());
				List<SubAndGrade> sbs = (List<SubAndGrade>) map.get("subs");
				if (sbs.size() % 4 != 0) {
					for (int i = 0; i < sbs.size() % 4; i++) {
						SubAndGrade andGrade = null;
						sbs.add(andGrade);
					}
				}
				if (!"".equals(subId.trim())) {
					for (SubAndGrade subAndGrade : sbs) {
						if (subAndGrade != null) {
							if (subAndGrade.getId().equals(subId)) {
								subAndGrade.setIscheck(true);
							}
						}
					}
				}
				lsags.addAll(sbs);
				sAdapter.notifyDataSetChanged();
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
		case R.id.update_subject_commit:
			// 确定修改
			if(!IBaes.isNet(this)){
				IBaes.toastShow(SubjectActivity.this, "网络不给力,请稍后...");
				return ;
			}
			load.show();
			load.setText("正在修改...");
			Log.i("test", "gradeId=" + gradeId);
			AjaxParams params = servecHttp.updateSubjectAndGrade(key, userId, subId, gradeId);
			finalHttp.post(IBaes.UPDATE_SUBJECT_GRADE, params, new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
//					Log.i("test", t.toString());
					int status = jsonDate.jsonInt(t.toString());
					handler.sendEmptyMessage(status);
				}
			});
			break;
		}
	}

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == load.SUCCESS) {
			setResult(100);
			finish();
		}
	}

}
