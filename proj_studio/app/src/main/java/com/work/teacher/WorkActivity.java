package com.work.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.LeftTree;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.work.HistoryWorkActivity;
import com.work.teacher.work.QuestionWorkActivity;
import com.work.teacher.work.TreeDialog;
import com.work.teacher.work.WrokArrtActivity;
import com.work.teacher.work.adapter.GradeMenuAdapter;
import com.work.teacher.work.adapter.SpinnerMenuAdpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 作业
 *@author 左丽姬
 */
public class WorkActivity extends ActivityGroup implements OnClickListener {
	private ImageView isnet_image;
	private LinearLayout selectsub_work;
	private TextView subject_work, version_work, question_number;
	private FrameLayout work_questions_work;
	private Button question_work, history_work;
	private ImageView left_menu_work;
	private ViewPager viewpage_work;
	List<View> views = new ArrayList<View>();
	private ServecHttp servecHttp;
	private WorkJson jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private String jid = "";
	private LeftSubject sub = null;
	private WorkReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);
		initWork();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		receiver = new WorkReceiver();
		IntentFilter filter = new IntentFilter(IBaes.ACTION_LOGIN);
		registerReceiver(receiver, filter);
		IntentFilter filter1 = new IntentFilter(IBaes.ACTION_NET);
		registerReceiver(receiver, filter1);
		//添加个人信息修改监听
		IntentFilter filter2 = new IntentFilter(IBaes.ACTION_UPDATE_PERSONAL);
		registerReceiver(receiver, filter2);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	public void initWork() {

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

		// 头部
		selectsub_work = (LinearLayout) findViewById(R.id.selectsub_work);
		subject_work = (TextView) findViewById(R.id.subject_work);
		version_work = (TextView) findViewById(R.id.version_work);
		question_number = (TextView) findViewById(R.id.work_question_number);
		work_questions_work = (FrameLayout) findViewById(R.id.work_questions_work);
		left_menu_work = (ImageView) findViewById(R.id.left_menu_work);
		question_work = (Button) findViewById(R.id.question_work);
		history_work = (Button) findViewById(R.id.history_work);
		viewpage_work = (ViewPager) findViewById(R.id.viewpage_work);
		initPageView();
		question_work.setOnClickListener(this);
		history_work.setOnClickListener(this);
		selectsub_work.setOnClickListener(this);
		left_menu_work.setOnClickListener(this);
		work_questions_work.setOnClickListener(this);
		// 查询科目
		getLeftSubject();

	}

	/** 初始化activity */
	public void initPageView() {
		Intent historyIntent = new Intent(this, HistoryWorkActivity.class);
		View view = getLocalActivityManager().startActivity("A", historyIntent).getDecorView();
		views.add(view);
		Intent questionIntent = new Intent(this, QuestionWorkActivity.class);
		View v = getLocalActivityManager().startActivity("B", questionIntent).getDecorView();
		views.add(v);
		viewpage_work.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				// super.destroyItem(container, position, object);
				ViewPager viewPager = (ViewPager) container;
				viewPager.removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				// TODO Auto-generated method stub
				ViewPager pViewPager = ((ViewPager) container);
				pViewPager.addView(views.get(position));
				return views.get(position);
			}
		});
		viewpage_work.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					setWorkSelect();
					history_work.setBackgroundResource(R.drawable.message_top_yes);
					history_work.setTextColor(getResources().getColor(R.color.green));
					break;
				case 1:
					setWorkSelect();
					question_work.setBackgroundResource(R.drawable.studey_yes);
					question_work.setTextColor(getResources().getColor(R.color.green));
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

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
		case R.id.history_work:
			// 历史
			setWorkSelect();
			history_work.setBackgroundResource(R.drawable.message_top_yes);
			history_work.setTextColor(getResources().getColor(R.color.green));
			viewpage_work.setCurrentItem(0);
			break;
		case R.id.question_work:
			// 题库
			setWorkSelect();
			question_work.setBackgroundResource(R.drawable.studey_yes);
			question_work.setTextColor(getResources().getColor(R.color.green));
			viewpage_work.setCurrentItem(1);
			break;
		case R.id.selectsub_work:
			//侧滑菜单
			mIntent = new Intent(this, TreeDialog.class);
			startActivityForResult(mIntent, IBaes.WORK_HISTORY_TREE);
			break;
		case R.id.left_menu_work:
			//侧滑菜单
			mIntent = new Intent(this, TreeDialog.class);
			startActivityForResult(mIntent, IBaes.WORK_HISTORY_TREE);
			break;
		case R.id.work_questions_work:
			// 试题栏
			mIntent = new Intent(this, WrokArrtActivity.class);
			startActivity(mIntent);
			break;
		}
	}

	/** 查询学科 */
	public void getLeftSubject() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(WorkActivity.this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = servecHttp.keyAndId(key, userId);
		finalHttp.post(IBaes.WORK_SUBJECT_LISTS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				LeftSubject lists = jsonDate.jsonWorkSubjec(t.toString());
				Message msg = new Message();
				msg.obj = lists;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 侧滑学科查询
				sub = (LeftSubject) msg.obj;
				subject_work.setText(sub.getCname());
				SPUtils.put(WorkActivity.this,"xuke_cs",sub.getCs());
				SPUtils.put(WorkActivity.this,"xuke",sub.getCname());
				break;
			}
		}
	};

	/** 选择的tab页 */
	public void setWorkSelect() {
		question_work.setBackgroundResource(R.drawable.studey_no);
		history_work.setBackgroundResource(R.drawable.message_top_no);
		question_work.setTextColor(getResources().getColor(R.color.white));
		history_work.setTextColor(getResources().getColor(R.color.white));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			HomeActivity.exit(WorkActivity.this);
		}
		return true;
	}

	class WorkReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (IBaes.ACTION_LOGIN.equals(intent.getAction()) || IBaes.ACTION_NET.equals(intent.getAction())) {
				key = SPUtils.get(WorkActivity.this, "key", "").toString();
				userId = SPUtils.get(WorkActivity.this, "userId", "").toString();
				getLeftSubject();
			}
			if(IBaes.ACTION_UPDATE_PERSONAL.equals(intent.getAction())){
				getLeftSubject();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IBaes.WORK_HISTORY_TREE) {
			String name = data.getStringExtra("sname");
			String sectionId = data.getStringExtra("sectionId");
			if (!"".equals(sectionId)) {
				// 选中后发送广播，改变作业的数据
				Intent intent = new Intent(IBaes.ACTION_WORK_HISTORY_SUBJECT);
				intent.putExtra("sectionId",sectionId);
				sendBroadcast(intent);
			}
		}
		if(requestCode==IBaes.NOTICETWO_GET){
			HistoryWorkActivity workActivity = (HistoryWorkActivity) getLocalActivityManager().getActivity("A");
			 workActivity.handleActivityResult(requestCode, resultCode, data);
		}
	}
}
