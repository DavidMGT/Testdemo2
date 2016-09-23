package com.work.teacher.more.notice;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.work.teacher.R;
import com.work.teacher.adapter.NoticeDetailsAdpater;
import com.work.teacher.bean.FeedbackDetails;
import com.work.teacher.bean.NoticeDetails;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.CustomListView;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/** 通知详情页
 * @author 左丽姬
 *  */
public class NoticeDetailsActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {
	private ImageView isnet_image;
	private String noticeId;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private LoadingDialog dialog;
	private TextView title, cotent, person, date, broaddate;
	private ImageView broad;
	private boolean broad_flag = false;
	private ListView details_lists;
	private List<FeedbackDetails> lists = new ArrayList<FeedbackDetails>();
	private NoticeDetailsAdpater adapter;
	private String agr, disagr;
	private LinearLayout ll_broad;
	private String upload = "";
	private MediaPlayer player = null;
	private int index = 0;// 计算播放时间
	private int stu = 0;// 播放状态（0开始播放，1暂停播放，2结束播放）

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_details);

		initDetails();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (player != null) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.release();
		}
	}

	public void initDetails() {

		noticeId = getIntent().getStringExtra("noticeId");
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
		top_text.setText("通知详情");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);

		title = (TextView) findViewById(R.id.title_notice_details);
		cotent = (TextView) findViewById(R.id.cotent_notice_details);
		person = (TextView) findViewById(R.id.person_notice_details);
		date = (TextView) findViewById(R.id.date_notice_details);
		broad = (ImageView) findViewById(R.id.broad_notice_details);
		broaddate = (TextView) findViewById(R.id.broaddate_notice_details);
		details_lists = (ListView) findViewById(R.id.notice_details_lists);
		ll_broad = (LinearLayout) findViewById(R.id.ll_broad);

		broad.setOnClickListener(this);
		noticeDetails();
	}

	/** 获取通知详情信息 */
	public void noticeDetails() {
		AjaxParams params = servecHttp.noticeDetails(key, userId, noticeId);
		finalHttp.post(IBaes.NOTICE_DETAILS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
//				 Log.i("test", t.toString());
				NoticeDetails details = jsonDate.jsonDetails(t.toString());
				Message msg = new Message();
				msg.obj = details;
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
			if (player != null) {
				if (player.isPlaying()) {
					player.stop();
				}
				player.release();
			}
			break;
		case R.id.broad_notice_details:
			if (!bf) {
				IBaes.toastShow(this, "正在计算音频时间,请稍等");
				return;
			}
			// 语音播放
			if (broad_flag) {
				// 暂停
				broad.setImageResource(R.drawable.broadcast);
				broad_flag = false;
				stu = 1;
				if (player.isPlaying()) {
					player.pause();
				}
			} else {
				// 播放
				broad.setImageResource(R.drawable.pause);
				broad_flag = true;
				if (player != null) {
					Log.i("test", "player.isPlaying()=" + player.isPlaying());
					if (player.isPlaying() == false) {
						player.start();
						if (stu == 0)
							timer.schedule(task, 0, 1000);
						stu = 3;
						player.setOnCompletionListener(new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								if (!mp.isPlaying()) {
									broad.setImageResource(R.drawable.broadcast);
									broad_flag = false;
									stu = 2;
								}
							}
						});
					}
				}
			}
			break;
		}
	}

	// 播放倒计时
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (stu != 1) {
				Message msg = new Message();
				msg.what = index--;
				mh.sendMessage(msg);
			}
		}
	};

	/**计算音频播放的倒计时*/
	Handler mh = new Handler() {
		public void handleMessage(Message msg) {
			Log.i("test", "走了...." + msg.what);
			caleDateValue(msg.what);
			if (msg.what == 0||stu==2) {
				index = time-2;
				caleDateValue(index);
				// 结束Timer计时器
				timer.cancel();
			}
			
		}
	};

	/**倒计时赋值*/
	public void caleDateValue(int i){
		if (i % 60 < 10) {
			if (i/ 60 < 10)
				broaddate.setText("0" + i / 60 + ":0" + i % 60);
			else
				broaddate.setText(i / 60 + ":0" + i % 60);
		} else {
			if (i / 60 < 10)
				broaddate.setText("0" + i / 60 + ":" + i % 60);
			else
				broaddate.setText(i/ 60 + ":" + i % 60);
		}
	}
	
	/**
	 * 
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				NoticeDetails details = (NoticeDetails) msg.obj;
				if (details == null)
					return;
				if (details.getStatus() != 2) {
					title.setText(details.getFtitle());
					cotent.setText("简介：" + details.getFcontent());
					person.setText("发布人：" + details.getUser_name());
					Log.i("test", "时间:" + details.getSendtime());
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTimeInMillis(details.getSendtime() * 1000);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					date.setText("发布时间：" + sdf.format(gc.getTime()));
					if (details.getTapeurl() == null || "".equals(details.getTapeurl())
							|| "null".equals(details.getTapeurl()))
						ll_broad.setVisibility(View.GONE);
					else {
						String URI =IBaes.URL+ details.getTapeurl();
						Log.i("test", URI+"                 "+details.getTapeurl());
						getDug(URI);
						ll_broad.setVisibility(View.VISIBLE);
						player = new MediaPlayer();
						try {
							player.reset();
							player.setAudioStreamType(AudioManager.STREAM_MUSIC);
							player.setDataSource(URI);
							player.setVolume(0.8f, 0.8f);
							player.prepare();
							player.setLooping(false);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					agr = details.getAgree();
					disagr = details.getDisargee();
					if (details.getDetailsl() != null && details.getDetailsl().size() > 0) {
						lists.addAll(details.getDetailsl());
						adapter = new NoticeDetailsAdpater(NoticeDetailsActivity.this, lists, agr, disagr);
						details_lists.setAdapter(adapter);
						IBaes.setGroupHeight(details_lists);
					}
				} else {
					IBaes.toastShow(NoticeDetailsActivity.this, details.getZhuce());
				}
				break;
			}
		}
	};

	boolean bf = false;
	int time = 0;
	MediaPlayer p = new MediaPlayer();

	/** 计算音频播放的时间 */
	public void getDug(String uri) {
		try {
			p.reset();
			p.setAudioStreamType(AudioManager.STREAM_MUSIC);
			Log.i("test", "uri=" + uri);
			p.setDataSource(uri);
			p.prepareAsync();
			p.setLooping(false);
			p.setVolume(0, 0);
			p.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					p.start();
				}
			});
			p.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					if (!mp.isPlaying()) {
						bf = true;
						p.release();
					}
				}
			});

			new Thread() {
				public void run() {
					while (true) {
						time++;
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (bf) {
							handler.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									IBaes.toastShow(NoticeDetailsActivity.this, "音频播放时间计算完成");
									index = time-2;
									if (index % 60 < 10) {
										if (index / 60 < 10)
											broaddate.setText("0" + index / 60 + ":0" + index % 60);
										else
											broaddate.setText(index / 60 + ":0" + index % 60);
									} else {
										if (index / 60 < 10)
											broaddate.setText("0" + index / 60 + ":" + index % 60);
										else
											broaddate.setText(index / 60 + ":" + index % 60);
									}
								}
							});
							break;
						}

					}
				}
			}.start();
			//
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
}
