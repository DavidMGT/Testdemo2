package com.work.student.adapter;

import java.util.List;

import com.work.student.R;
import com.work.student.bean.ClassDetails;
import com.work.student.bean.HistoryType;
import com.work.student.tool.IBaes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 班级详情--》》成员列表类别
 * 
 * @author 左丽姬
 */
public class ClassDetailsAdapter extends BaseAdapter {

	private Context context;
	private List<ClassDetails> details;

	public ClassDetailsAdapter(Context context, List<ClassDetails> details) {
		super();
		this.context = context;
		this.details = details;
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
		ClassDetailsHold ht = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_classperson_details, null);
			ht = new ClassDetailsHold(convertView);
			convertView.setTag(ht);
		} else {
			ht = (ClassDetailsHold) convertView.getTag();
		}

		ClassDetails type = details.get(position);
		ht.name.setText(type.getName());
		switch (type.getStatus()) {
		case 0:
			ht.num.setText(type.getTea_count() + "");
			break;
		case 1:
			ht.num.setText(type.getStudent_count() + "");
			break;
		case 2:
			ht.num.setText(type.getParent_count() + "");
			break;
		}
		ClassDetailListViewAdapter adapter = new ClassDetailListViewAdapter(context, type.getDetails(),
				type.getStatus());
		ht.listview.setAdapter(adapter);
		IBaes.setGroupHeight(ht.listview);
		return convertView;
	}

	class ClassDetailsHold {
		private TextView name, num;
		private ListView listview;

		public ClassDetailsHold(View v) {
			name = (TextView) v.findViewById(R.id.tv_classname_details);
			num = (TextView) v.findViewById(R.id.tv_num_class_details);
			listview = (ListView) v.findViewById(R.id.lv_class_details);
		}
	}
}
