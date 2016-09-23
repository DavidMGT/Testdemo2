package com.work.student.adapter;

import java.util.List;

import com.work.student.R;
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
 * 历史列表类别
 * 
 * @author 左丽姬
 */
public class HistoryTyeAdapter extends BaseAdapter {

	private Context context;
	private List<HistoryType> types;

	public HistoryTyeAdapter(Context context, List<HistoryType> types) {
		super();
		this.context = context;
		this.types = types;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return types.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return types.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HType ht = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_classhistory, null);
			ht = new HType(convertView);
			convertView.setTag(ht);
		} else {
			ht = (HType) convertView.getTag();
		}

		HistoryType type = types.get(position);
		ht.name.setText(type.getName());

		ClassHistoryAdapter adapter = new ClassHistoryAdapter(context, type.getList(), type.getStatus());
		ht.listview.setAdapter(adapter);
		IBaes.setGroupHeight(ht.listview);
		return convertView;
	}

	class HType {
		private TextView name;
		private ListView listview;

		public HType(View v) {
			name = (TextView) v.findViewById(R.id.tv_classname);
			listview = (ListView) v.findViewById(R.id.lv_class_history);
		}
	}
}
