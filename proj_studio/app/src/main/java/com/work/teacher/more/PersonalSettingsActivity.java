package com.work.teacher.more;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.work.teacher.HomeActivity;
import com.work.teacher.LoginActivity;
import com.work.teacher.R;
import com.work.teacher.RegisterActivity;
import com.work.teacher.bean.PersonalBean;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.AvatarView;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 个人设置
 *@author 左丽姬
 */
@SuppressLint("InflateParams")
public class PersonalSettingsActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {

	private ImageView isnet_image;
	private AvatarView avatar_personal;
	private TextView id_personal, grade_num_personal, grade_personal, name_personal, subject_personal, school_personal;
	private RelativeLayout relative_name, relative_subandgrade, relative_subandsch, relative_money, relative_security;
	private SeekBar seekbar_personal;
	private Dialog dialog = null;
	private Button exit_Login, avatar_phone, avatar_image, avatar_cancel;
	private static String path = "";// sd路径
	private Bitmap head;// 头像Bitmap
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private PersonalBroadCastReceiver pbc;
	private LoadingDialog load;
	Bitmap bitmap = null;
	PersonalBean pb = null;
	private int isSub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_setting);
		initPersonal();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		pbc = new PersonalBroadCastReceiver();
		IntentFilter filter = new IntentFilter(IBaes.ACTION_NET);
		registerReceiver(pbc, filter);

		IntentFilter filter1 = new IntentFilter(IBaes.ACTION_LOGIN);
		registerReceiver(pbc, filter1);
		IntentFilter filter2 = new IntentFilter(IBaes.ACTION_UPDATE_PERSONAL);
		registerReceiver(pbc, filter2);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(pbc);
	}

	public void initPersonal() {
		load = new LoadingDialog(this);
		load.setOnLoadingDialogResultListener(this);
		isSub=getIntent().getIntExtra("isSub", 0);
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

		// 头部设置
		ImageView top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(this);
		TextView top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("个人设置");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);

		servecHttp = new ServecHttp();
		jsonDate = new JsonData();
		finalHttp = new FinalHttp();

		avatar_personal = (AvatarView) findViewById(R.id.avatar_personal);
		id_personal = (TextView) findViewById(R.id.id_personal);
		grade_num_personal = (TextView) findViewById(R.id.grade_num_personal);
		grade_personal = (TextView) findViewById(R.id.grade_personal);
		name_personal = (TextView) findViewById(R.id.name_personal);
		subject_personal = (TextView) findViewById(R.id.subject_personal);
		school_personal = (TextView) findViewById(R.id.school_personal);

		relative_name = (RelativeLayout) findViewById(R.id.relative_name);
		relative_subandgrade = (RelativeLayout) findViewById(R.id.relative_subandgrade);
		relative_subandsch = (RelativeLayout) findViewById(R.id.relative_subandsch);
		relative_money = (RelativeLayout) findViewById(R.id.relative_money);
		relative_security = (RelativeLayout) findViewById(R.id.relative_security);
		exit_Login = (Button) findViewById(R.id.exit_Login);

		seekbar_personal = (SeekBar) findViewById(R.id.seekbar_personal);

		avatar_personal.setOnClickListener(this);
		exit_Login.setOnClickListener(this);
		relative_name.setOnClickListener(this);
		relative_subandgrade.setOnClickListener(this);
		relative_subandsch.setOnClickListener(this);
		relative_money.setOnClickListener(this);
		relative_security.setOnClickListener(this);

		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();
		Log.i("test", key + "    " + userId);
		getPersonalData();

	}

	/** 获取数据 */
	public void getPersonalData() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		load.show();
		load.setText("加载中...");
		AjaxParams params = servecHttp.keyAndId(key, userId);
		finalHttp.post(IBaes.PERSONAL_SETTING, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
//				Log.i("test", t.toString());
				PersonalBean pb = jsonDate.getPersonal(t.toString());
				Message msg = new Message();
				msg.obj = pb;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (load.isShowing()) {
				load.dismiss();
				load.cancel();
			}
			switch (msg.what) {
			case 0:
				pb = (PersonalBean) msg.obj;
				if (pb == null) {
					return;
				}
				if (pb != null && pb.getStatus() == 1) {
					new Thread() {
						public void run() {
							URL fileUrl = null;
							try {
								fileUrl = new URL(IBaes.URL+pb.getAvatar());
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}

							try {
								HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
								conn.setDoInput(true);
								conn.connect();
								InputStream is = conn.getInputStream();
								bitmap = BitmapFactory.decodeStream(is);
								is.close();
								handler.post(new Runnable() {

									@Override
									public void run() {
										avatar_personal.setImageBitmap(bitmap);
									}
								});
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.start();
					id_personal.setText(pb.getUserId());
					int grade = 0;
					if (!"".equals(pb.getGrade())) {
						grade = Integer.parseInt(pb.getGrade()) / 100;
					}
					grade_num_personal.setText(grade + "级");
					grade_personal.setText(grade * 100 + "%");
					seekbar_personal.setProgress(grade);
					if (pb.getUserName().equals("")) {
						pb.setUserName("请填写");
					}
					if (pb.getSubject().equals("")) {
						pb.setSubject("请选择");
					}
					if (pb.getSchool().equals("")) {
						pb.setSchool("填入你所在的学校编号");
					}
					
					name_personal.setText(pb.getUserName());
					if("null".equals(pb.getUserName())){
						name_personal.setText("");
					}
					subject_personal.setText(pb.getCourse_grade() + "  " + pb.getSubject());
					school_personal.setText(pb.getSchool());
				} else {
					IBaes.toastShow(PersonalSettingsActivity.this, pb.getZhuce());
				}
				break;
			case 1:
				if (dialog != null) {
					dialog.dismiss();
					dialog.cancel();
				}
				Map<String, Object> avatar = (Map<String, Object>) msg.obj;
				int status = (Integer) avatar.get("status");
				String zhuce = avatar.get("zhuce").toString();
				IBaes.toastShow(PersonalSettingsActivity.this, zhuce);
				if (status == 1) {
					Intent intent = new Intent("com.work.teacher.UPDATE_PERSONAL");
					sendBroadcast(intent);
				}
				break;
			case 2:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				int ss = (Integer) map.get("status");
				String zz = map.get("zhuce").toString();
				IBaes.toastShow(PersonalSettingsActivity.this, zz);
				if(ss==1){
					SPUtils.remove(PersonalSettingsActivity.this, "key");
					SPUtils.remove(PersonalSettingsActivity.this, "userId");
					Intent intent = new Intent(PersonalSettingsActivity.this, LoginActivity.class);
					intent.putExtra("isRegister", 3);
					startActivity(intent);
				}
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			// 打开设置界面
			intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
			startActivity(intent);
			break;
		case R.id.top_back:
			if(isSub==1){
				intent=new Intent(this, HomeActivity.class);
				startActivity(intent);
			}
			finish();
			
			break;
		case R.id.avatar_personal:
			setDialog();
			break;
		case R.id.exit_Login:
			// 退出账号
			exitLogin();
			
			break;
		case R.id.relative_name:
			// 修改姓名
			intent = new Intent(this, UpdateNameActivity.class);
			startActivity(intent);
			break;
		case R.id.relative_subandgrade:
			// 修改科目年级
			intent = new Intent(this, SubjectActivity.class);
			intent.putExtra("grade", pb.getCourse_gradeId());
			intent.putExtra("subject", pb.getSubjectId());
			startActivity(intent);
			break;
		case R.id.relative_subandsch:
			// 更改绑定学校
			intent = new Intent(this, SchoolActivity.class);
			if(pb!=null)
				intent.putExtra("school", pb.getSchoolCode());
			startActivity(intent);
			break;
		case R.id.relative_money:
			// 我的钱包
			intent = new Intent(this, MoneyActivity.class);
			startActivity(intent);
			break;
		case R.id.relative_security:
			// 账号安全
			intent=new Intent(this, SafetyActivity.class);
			if(pb!=null){
				intent.putExtra("mobile",pb.getMobile());
			}else{
				intent.putExtra("mobile", "");
			}
			startActivity(intent);
			break;
		case R.id.avatar_phone:
			// 拍照
			Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 下面这句指定调用相机拍照后的照片存储的路径
			takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
			startActivityForResult(takeIntent, IBaes.REQUESTCODE_TAKE);
			break;
		case R.id.avatar_image:
			// 从相册中选择
			Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
			// 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
			pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(pickIntent, IBaes.REQUESTCODE_PICK);
			break;
		case R.id.avatar_cancel:
			// 从相册中选择
			if (dialog != null) {
				dialog.dismiss();
				dialog.cancel();
			}
			break;
		}
	}
	/**退出登陆*/
	public void exitLogin(){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(PersonalSettingsActivity.this, "网络不给力,请稍后...");
			return ;
		}
		load.show();
		load.setText("正在退出...");
		finalHttp.post(IBaes.EXIT_LOGIN,servecHttp.keyAndId(key, userId), new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map=jsonDate.jsonAvatar(t.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=2;
				handler.sendMessage(msg);
			}
		});
	}

	/** 修改头像 */
	@SuppressLint("NewApi")
	public void setDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.avatar_dialog, null);
		dialog = new AlertDialog.Builder(this, R.style.PassDialog).create();
		dialog.show();
		dialog.getWindow().setContentView(view);
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = this.getWindowManager().getDefaultDisplay().getWidth();
		dialog.getWindow().setAttributes(params);
		dialog.getWindow().setGravity(Gravity.BOTTOM);

		avatar_phone = (Button) view.findViewById(R.id.avatar_phone);
		avatar_image = (Button) view.findViewById(R.id.avatar_image);
		avatar_cancel = (Button) view.findViewById(R.id.avatar_cancel);
		avatar_phone.setOnClickListener(this);
		avatar_image.setOnClickListener(this);
		avatar_cancel.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				dialog.cancel();
				return false;
			}
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == IBaes.REQUESTCODE_PICK) {
			// 从相机中选择
			try {
				cropPhoto(data.getData());
			} catch (NullPointerException e) {
				e.printStackTrace();// 用户点击取消操作
			}
		}
		if (requestCode == IBaes.REQUESTCODE_TAKE) {
			// 拍照
			File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
			cropPhoto(Uri.fromFile(temp));// 裁剪图片
		}

		if (requestCode == IBaes.SYS_CROPPHOTO) {
			if (data != null) {
				Bundle extras = data.getExtras();
				head = extras.getParcelable("data");
				if (head != null) {
					/**
					 * 上传服务器代码
					 */
					setPicToView(head);// 保存在SD卡中
				}
			}
		}
	}

	/**
	 * 调用系统的裁剪
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, IBaes.SYS_CROPPHOTO);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Bitmap mBitmap) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}
		path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/teacherPhoto";
		Log.e("test", path);
		FileOutputStream b = null;
		File file = new File(path);
		file.mkdirs();// 创建文件夹
		String fileName = path + "/teacher_avatar.jpg";// 图片名字
		try {
			b = new FileOutputStream(fileName);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
			File fi = new File(fileName);
			
			if(!IBaes.isNet(this)){
				IBaes.toastShow(PersonalSettingsActivity.this, "网络不给力,请稍后...");
				return ;
			}
			
			AjaxParams params = servecHttp.updatePersonalAvatar(key, userId, fi);
			finalHttp.post(IBaes.UPDATE_AVATAR, params, new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					// Log.i("test", "头像上传:"+t.toString());
					Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
					Message msg = new Message();
					msg.obj = map;
					msg.what = 1;
					handler.sendMessage(msg);
				}
			});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	class PersonalBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (IBaes.ACTION_NET.equals(intent.getAction())
					|| IBaes.ACTION_LOGIN.equals(intent.getAction())
					|| IBaes.ACTION_UPDATE_PERSONAL.equals(intent.getAction())) {
				// 网络发生变化 重新登录 个人资料修改
				key = SPUtils.get(PersonalSettingsActivity.this, "key", "").toString();
				userId = SPUtils.get(PersonalSettingsActivity.this, "userId", "").toString();
				getPersonalData();
			}
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
