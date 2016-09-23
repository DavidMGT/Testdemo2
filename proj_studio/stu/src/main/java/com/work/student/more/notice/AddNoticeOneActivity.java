package com.work.student.more.notice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.work.student.MoreActivity;
import com.work.student.R;
import com.work.student.tool.IBaes;
import com.work.student.tool.JsonData;
import com.work.student.tool.SPUtils;
import com.work.student.tool.ServecHttp;
import com.work.student.view.LoadingDialog;
import com.work.student.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 发布通知第一步
 * @author 左丽姬
 */
public class AddNoticeOneActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {
	private ImageView isnet_image;
	private EditText title_notice;
	private EditText content_notice;
	private EditText feedback_noticeone;
	private EditText feedback_noticetwo;
	private RadioButton feedback_notice;
	private boolean radio_flag = true;// 判断信息反馈是否打开
	private LinearLayout ll_message_noticeone;
	private Button broad_notice;
	private MediaRecorder recorder;
	private boolean record_flag = false;// 开始录音
	private String path = "";// 保存录音路径
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private LoadingDialog dialog;
	private String fileName = "";
	private String pathName = "";
	private boolean n_flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addnoticeone);
		initAddOne();
	}

	public void initAddOne() {

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
		TextView top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("发布新通知");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setText("下一步");
		top_btn.setOnClickListener(this);

		// 内容

		title_notice = (EditText) findViewById(R.id.add_title_notice);// 标题
		content_notice = (EditText) findViewById(R.id.add_content_notice);// 发布内容
		feedback_noticeone = (EditText) findViewById(R.id.et_feedback_noticeone);// 反馈信息确认
		feedback_noticetwo = (EditText) findViewById(R.id.et_feedback_noticetwo);// 反馈信息否定
		broad_notice = (Button) findViewById(R.id.add_broad_notice);// 录音
		title_notice.setHintTextColor(getResources().getColor(R.color.whitegrey));
		content_notice.setHintTextColor(getResources().getColor(R.color.whitegrey));
		feedback_noticeone.setHintTextColor(getResources().getColor(R.color.whitegrey));
		feedback_noticetwo.setHintTextColor(getResources().getColor(R.color.whitegrey));
		feedback_notice = (RadioButton) findViewById(R.id.add_messageFeedk_notice);// 设置反馈信息
		ll_message_noticeone = (LinearLayout) findViewById(R.id.ll_message_noticeone);
		feedback_notice.setOnClickListener(this);
		broad_notice.setOnClickListener(this);

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
			if (!"".equals(fileName))
				uploadRecord();
			else
				oneTotwo();
			break;
		case R.id.add_messageFeedk_notice:
			// 设置反馈信息内容
			if (radio_flag) {
				radio_flag = false;
				feedback_notice.setChecked(true);
				ll_message_noticeone.setVisibility(View.VISIBLE);
			} else {
				radio_flag = true;
				feedback_notice.setChecked(false);
				ll_message_noticeone.setVisibility(View.GONE);
			}
			break;
		case R.id.add_broad_notice:
			if (!record_flag) {
				record_flag = true;
				n_flag = false;
				// 得到焦点
				broad_notice.setText("停止    说话");
				broad_notice.setTextColor(getResources().getColor(R.color.green));
				initRecord();
				new NoticeThrea().start();
			} else {
				// 失去焦点
				n_flag = true;
				record_flag = false;
			}
			break;
		}
	}

	/** 下一步 */
	public void oneTotwo() {
		n_flag = true;
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(AddNoticeOneActivity.this, "网络不给力,请稍后...");
			return;
		}
		String feedback_one = feedback_noticeone.getText().toString();
		if ("".equals(feedback_one.trim())) {
			feedback_one = "是";
		}
		String feedback_two = feedback_noticetwo.getText().toString();
		if ("".equals(feedback_two.trim())) {
			feedback_two = "否";
		}

		String title = title_notice.getText().toString();
		if ("".equals(title.trim())) {
			IBaes.toastShow(AddNoticeOneActivity.this, "标题不能为空!");
			return;
		}
		String content = content_notice.getText().toString();
		if ("".equals(content.trim())) {
			IBaes.toastShow(AddNoticeOneActivity.this, "内容不能为空!");
			return;
		}
		dialog.show();
		dialog.setText("提交中...");
		AjaxParams params = servecHttp.noticeOne(key, userId, title, content, "/" + pathName, feedback_one,
				feedback_two);
		finalHttp.post(IBaes.NOTICE_ONE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				Map<String, Object> map = jsonDate.jsonNoticeOne(t.toString());
				Message msg = new Message();
				msg.obj = map;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	/** 录音 */
	public void initRecord() {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 设置MediaRecorder的音频源为麦克风
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		// 设置MediaRecorder录制的音频格式
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}
		path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/teacherRecord";
		Log.e("test", path);
		FileOutputStream b = null;
		File file = new File(path);
		file.mkdirs();// 创建文件夹

		fileName = path + "/record_" + System.currentTimeMillis() + ".mp3";
		// 设置MediaRecorder录制音频的编码为amr.
		recorder.setOutputFile(fileName);
		// 设置录制好的音频文件保存路径
		try {
			recorder.prepare();// 准备录制
			recorder.start();// 开始录制
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 上传语音 */
	public void uploadRecord() {
		if (!"".equals(path)) {
			// Log.i("test", path);
			File file = new File(fileName);
			AjaxParams params = new AjaxParams();
			params.put("key", key);
			params.put("userId", userId);
			try {
				params.put("yuyin", file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			finalHttp.post(IBaes.NOTICE_UPLOAD, params, new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					// Log.i("test", t.toString());
					Map<String, Object> map = jsonDate.jsonNoticeUpload(t.toString());
					Message msg = new Message();
					msg.obj = map;
					msg.what = 1;
					handler.sendMessage(msg);
				}
			});
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Map<String, Object> map = null;
			int status = 0;
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				dialog.cancel();
				map = (Map<String, Object>) msg.obj;
				String zhuce = (String) map.get("zhuce");
				IBaes.toastShow(AddNoticeOneActivity.this, zhuce);
				status = (Integer) map.get("status");
				if (status == 1) {
					Intent intentcast = new Intent(IBaes.ACTION_NOTICE);
					sendBroadcast(intentcast);
					Intent intent = new Intent(AddNoticeOneActivity.this, AddNoticeTwoActivity.class);
					String noticeid = (String) map.get("noticeid");
					intent.putExtra("nid", noticeid);
					startActivityForResult(intent, IBaes.NOTICEONE_NOTICETWO);
				}
				break;
			case 1:
				map = (Map<String, Object>) msg.obj;
				status = (Integer) map.get("status");
				if (status == 1) {
					pathName = map.get("tape").toString();
					oneTotwo();
				} else {
					IBaes.toastShow(AddNoticeOneActivity.this, map.get("zhuce").toString());
				}
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IBaes.NOTICEONE_NOTICETWO && resultCode == IBaes.NOTICETWO_NOTICEONE) {
			finish();
		}
	}

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == LoadingDialog.SUCCESS) {
			setResult(100);
			finish();
		}
	}

	int time = 60;

	class NoticeThrea extends Thread {
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
				time--;
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						if (time == 0 || n_flag == true) {
							broad_notice.setText("开始    说话");
							broad_notice.setTextColor(getResources().getColor(R.color.blackgrey));
							if (recorder != null) {
								recorder.stop();// 停止刻录
								recorder.release();// 释放资源
							}
						} else {
							broad_notice.setText("停止    说话 " + time + " s");
							broad_notice.setTextColor(getResources().getColor(R.color.green));
						}

					}
				});
				if (time == 0 || n_flag == true) {
					break;
				}
			}
		}
	}
}
