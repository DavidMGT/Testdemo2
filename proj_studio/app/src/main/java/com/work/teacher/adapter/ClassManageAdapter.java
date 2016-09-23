package com.work.teacher.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.bean.ClassDetails;
import com.work.teacher.bean.ClassPersionDetails;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.view.AvatarView;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;
import com.work.teacher.view.SwipeListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 班级详情--》》班级管理
 * 
 * @author 左丽姬
 */
public class ClassManageAdapter extends BaseAdapter implements OnLoadingDialogResultListener {

	private Context context;
	private List<ClassPersionDetails> details;
	private int rightWidth;
	private String key;
	private String userId;
	private SwipeListView lv;
	private View vp;
	private int index;
	private LoadingDialog dialog;
	private boolean flag = false;// false.删除,true.同意

	public ClassManageAdapter(Context context, List<ClassPersionDetails> details, int rightWidth) {
		super();
		this.context = context;
		this.details = details;
		this.rightWidth = rightWidth;
		key = SPUtils.get(context, "key", "").toString();
		userId = SPUtils.get(context, "userId", "").toString();
		dialog = new LoadingDialog((Activity) context);
		dialog.setOnLoadingDialogResultListener(this);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return details.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return details.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_class_details, null);
		}
		AvatarView avatar_class_details = (AvatarView) convertView.findViewById(R.id.avatar_class_details);
		TextView tv_class_name_details = (TextView) convertView.findViewById(R.id.tv_class_name_details);
		TextView tv_class_subject_details = (TextView) convertView.findViewById(R.id.tv_class_subject_details);
		TextView item_right_txt = (TextView) convertView.findViewById(R.id.item_right_txt);
		TextView item_reight_add = (TextView) convertView.findViewById(R.id.item_right_add);
		TextView item_reight_top = (TextView) convertView.findViewById(R.id.item_right_top);

		RelativeLayout item_left = (RelativeLayout) convertView.findViewById(R.id.item_left);
		LinearLayout item_right = (LinearLayout) convertView.findViewById(R.id.item_right);
		LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		item_left.setLayoutParams(lp1);
		//////////
		// 测试让item右侧显示不同的个数
		item_reight_add.setVisibility(View.GONE);
		LayoutParams lp2 = new LayoutParams(220, LayoutParams.MATCH_PARENT);
		item_right.setLayoutParams(lp2);

		item_right_txt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					flag = false;
					mListener.onRightItemClick((View) v.getParent(), position);
				}
			}
		});

		item_reight_top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					flag = true;
					mListener.onRightItemClick((View) v.getParent(), position);
				}
			}
		});
		ClassPersionDetails persionDetails = details.get(position);
		tv_class_name_details.setText(persionDetails.getTruename() + "申请加入你的班级");
		FinalBitmap.create(context).display(avatar_class_details, IBaes.URL + persionDetails.getPhoto());
		return convertView;
	}

	/**
	 * 单击事件监听器
	 */
	private onRightItemClickListener mListener = null;

	public void setOnRightItemClickListener(final SwipeListView lv) {
		mListener = new onRightItemClickListener() {
			@Override
			public void onRightItemClick(View v, int position) {
				// TODO Auto-generated method stub
				// Log.i("test", "班级id=" + type.getCid());
				ClassPersionDetails message = (ClassPersionDetails) details.get(position);
				delPersoin(message.getCid(), message.getUserid());
				ClassManageAdapter.this.lv = lv;
				ClassManageAdapter.this.vp = v;
				ClassManageAdapter.this.index = index;
			}
		};
	}

	public interface onRightItemClickListener {
		void onRightItemClick(View v, int position);
	}

	/** 拒绝,同意申请 */
	public void delPersoin(String cid, String uid) {
		if (!IBaes.isNet(context)) {
			IBaes.toastShow((Activity) context, "网络不给力,请稍后...");
			return;
		}
		dialog.show();
		dialog.setText("提交中...");
		AjaxParams params = new AjaxParams();
		params.put("key", key);
		params.put("userId", userId);
		params.put("cid", cid);
		params.put("id", uid);
		String url = "";
		if (flag)
			url = IBaes.CLASS_MANAGE_AGREE;
		else
			url = IBaes.CLASS_MANAGE_REFUSE;
		Log.i("test", url + ",,," + cid + ",," + uid);
		new FinalHttp().post(url, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.i("test", t.toString());
				Map<String, Object> map = new JsonData().jsonAvatar(t.toString());
				Message message = new Message();
				message.obj = map;
				message.what = 0;
				handler.sendMessage(message);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (dialog.isShowing()) {
					dialog.dismiss();
					dialog.cancel();
				}
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				int status = (Integer) map.get("status");
				IBaes.toastShow((Activity) context, map.get("zhuce").toString());
				if (status == 1) {
					Intent intent = new Intent(IBaes.ACTION_CLASS_DELMEMBER);
					context.sendBroadcast(intent);
				}
				lv.hiddenRight((View) vp.getParent(), true);
				break;
			}
		}
	};

	@Override
	public void dialogResult(int tag, int state) {
		// TODO Auto-generated method stub
		if (state == LoadingDialog.SUCCESS) {
			((Activity) context).setResult(100);
			((Activity) context).finish();
		}
	}
}
