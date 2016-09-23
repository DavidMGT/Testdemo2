package com.work.teacher.work.adapter;

import java.util.List;

import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 作业-》》侧滑菜单--》》spinner
 * 
 * @author 左丽姬
 */
public class SpinnerMenuAdpater extends BaseAdapter {

	private Context context;
	private List<LeftSubject> subjects;

	public SpinnerMenuAdpater(Context context, List<LeftSubject> subjects) {
		super();
		this.context = context;
		this.subjects = subjects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return subjects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return subjects.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);
		}
		TextView view = (TextView) convertView.findViewById(R.id.spinner_text);
		view.setText(subjects.get(position).getCname());
		return convertView;
	}

}
