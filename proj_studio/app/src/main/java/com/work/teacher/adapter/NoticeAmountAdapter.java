package com.work.teacher.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.work.teacher.R;
import com.work.teacher.bean.NoticeClass;
import com.work.teacher.bean.TeaStuPat;
import com.work.teacher.tool.IBaes;
import com.work.teacher.view.AvatarView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

/**
 * 通知发布--》》班级列表
 * 
 * @author 左丽姬
 */
public class NoticeAmountAdapter extends BaseAdapter {

	private Context context;
	private List<TeaStuPat> tsps;
	private NoticeAmuntHold hold;
	private Bitmap bitmap;

	public NoticeAmountAdapter() {
		super();
	}

	public NoticeAmountAdapter(Context context, List<TeaStuPat> tsps) {
		super();
		this.context = context;
		this.tsps = tsps;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tsps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tsps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		hold = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_noticeamount_lists, null);
			hold = new NoticeAmuntHold(convertView);
			convertView.setTag(hold);
		} else {
			hold = (NoticeAmuntHold) convertView.getTag();
		}
		final TeaStuPat noticeClass = tsps.get(position);
		if (noticeClass.isSelect()) {
			hold.tv_noticeamount_items.setBackgroundResource(R.drawable.select_yes);
		} else {
			hold.tv_noticeamount_items.setBackgroundResource(R.drawable.select_pay_no);
		}
		hold.tv_name_noticeamunt.setText(noticeClass.getName());

		FinalBitmap.create(context).display(hold.avatar_noticeamunt, IBaes.URL + noticeClass.getAvatar());
		FinalBitmap.create(context).clearCache();
		return convertView;
	}

	class NoticeAmuntHold {
		private ImageView avatar_noticeamunt;
		private TextView tv_name_noticeamunt;
		private TextView tv_noticeamount_items;

		public NoticeAmuntHold(View v) {
			avatar_noticeamunt = (AvatarView) v.findViewById(R.id.avatar_noticeamunt);
			tv_name_noticeamunt = (TextView) v.findViewById(R.id.tv_name_noticeamunt);
			tv_noticeamount_items = (TextView) v.findViewById(R.id.tv_noticeamount_items);
		}
	}
}
