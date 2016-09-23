package com.work.student.adapter;

import java.util.List;

import com.work.student.R;
import com.work.student.bean.NoticeClass;
import com.work.student.bean.SubAndGrade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 班级--》》班级列表
 * 
 * @author 左丽姬
 */
public class TeaClassAdapter extends BaseAdapter {

	private Context context;
	private List<SubAndGrade> lsag;
	private TeaClassHold hold;

	public TeaClassAdapter() {
		super();
	}

	public TeaClassAdapter(Context context, List<SubAndGrade> lsag) {
		super();
		this.context = context;
		this.lsag = lsag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lsag.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lsag.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_notice_class, null);
			hold = new TeaClassHold(convertView);
			convertView.setTag(hold);
		} else {
			hold = (TeaClassHold) convertView.getTag();
		}
		SubAndGrade sag = lsag.get(position);
		hold.classname_notice_items.setText(sag.getName());
		return convertView;
	}

	class TeaClassHold {
		private TextView class_notice_items;
		private TextView classname_notice_items;
		private TextView classnum_notice_items;

		public TeaClassHold(View v) {
			classname_notice_items = (TextView) v.findViewById(R.id.classname_notice_items);
			classname_notice_items.setTextSize(16);
			class_notice_items = (TextView) v.findViewById(R.id.class_notice_items);
			class_notice_items.setVisibility(View.GONE);
			classnum_notice_items = (TextView) v.findViewById(R.id.classnum_notice_items);
			classnum_notice_items.setVisibility(View.GONE);
		}
	}
}
