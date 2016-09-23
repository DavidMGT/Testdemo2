package com.work.student.adapter;

import java.util.List;

import com.work.student.R;
import com.work.student.bean.ClassPersionDetails;
import com.work.student.tool.IBaes;
import com.work.student.view.AvatarView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

/**
 * 班级详情--》》班级成员列表
 * 
 * @author 左丽姬
 */
public class ClassDetailListViewAdapter extends BaseAdapter {

	private Context context;
	private List<ClassPersionDetails> details;
	private int status;

	public ClassDetailListViewAdapter(Context context, List<ClassPersionDetails> details, int status) {
		super();
		this.context = context;
		this.details = details;
		this.status = status;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_class_details, null);
		}
		AvatarView avatar_class_details = (AvatarView) convertView.findViewById(R.id.avatar_class_details);
		TextView tv_class_name_details = (TextView) convertView.findViewById(R.id.tv_class_name_details);
		TextView tv_class_subject_details = (TextView) convertView.findViewById(R.id.tv_class_subject_details);
		RelativeLayout contentLayout = (RelativeLayout) convertView.findViewById(R.id.contentLayout);
		ClassPersionDetails persionDetails = details.get(position);
		tv_class_name_details.setText(persionDetails.getTruename());
		if (status == 0 && !"".equals(persionDetails.getValue1()) && !"null".equals(persionDetails.getValue1())) {
			tv_class_subject_details.setText(persionDetails.getValue1());
		}
		FinalBitmap.create(context).display(avatar_class_details, IBaes.URL + persionDetails.getPhoto());
		return convertView;
	}

}
