package com.work.student.adapter;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import com.work.student.R;
import com.work.student.bean.HistoryClass;
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
 * 班级--》》班级历史表
 * 
 * @author 左丽姬
 */
public class ClassHistoryAdapter extends BaseAdapter {

	private Context context;
	private List<HistoryClass> lists;
	private HistoryHold hold;
	private int status;

	public ClassHistoryAdapter() {
		super();
	}

	public ClassHistoryAdapter(Context context, List<HistoryClass> lists, int status) {
		super();
		this.context = context;
		this.lists = lists;
		this.status = status;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
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
			hold = new HistoryHold(convertView);
			convertView.setTag(hold);
		} else {
			hold = (HistoryHold) convertView.getTag();
		}
		HistoryClass historyClass = lists.get(position);
		hold.classname_notice_items.setText(historyClass.getClassname());
		String str_date = "";
		switch (status) {
		case 0:
			// 当前自定义班级
			GregorianCalendar create_date = new GregorianCalendar();
			create_date.setTimeInMillis(historyClass.getStartime() * 1000);
			SimpleDateFormat create_sdf = new SimpleDateFormat("yyyy-MM-dd");
			str_date = "创建时间：" + create_sdf.format(create_date.getTime());
			break;
		case 1:
			// 当前加入班级
			GregorianCalendar add_date = new GregorianCalendar();
			add_date.setTimeInMillis(historyClass.getJointime() * 1000);
			SimpleDateFormat add_sdf = new SimpleDateFormat("yyyy-MM-dd");
			str_date = "加入时间：" + add_sdf.format(add_date.getTime());
			break;
		case 2:
			// 历史自定义班级
			GregorianCalendar create_start_date = new GregorianCalendar();
			create_start_date.setTimeInMillis(historyClass.getStartime() * 1000);
			SimpleDateFormat create_start_sdf = new SimpleDateFormat("yyyy-MM-dd");
			GregorianCalendar create_end_date = new GregorianCalendar();
			create_end_date.setTimeInMillis(historyClass.getEndtime() * 1000);
			SimpleDateFormat create_end_sdf = new SimpleDateFormat("yyyy-MM-dd");
			str_date = "起止时间：" + create_start_sdf
					.format(create_start_date.getTime() + "至" + create_end_sdf.format(create_end_date.getTime()));
			break;
		case 3:
			// 历史加入班级
			GregorianCalendar add_start_date = new GregorianCalendar();
			add_start_date.setTimeInMillis(historyClass.getJointime() * 1000);
			SimpleDateFormat add_start_sdf = new SimpleDateFormat("yyyy-MM-dd");
			GregorianCalendar add_end_date = new GregorianCalendar();
			add_end_date.setTimeInMillis(historyClass.getQuitetime() * 1000);
			SimpleDateFormat add_end_sdf = new SimpleDateFormat("yyyy-MM-dd");
			str_date = "起止时间："
					+ add_start_sdf.format(add_start_date.getTime() + "至" + add_end_sdf.format(add_end_date.getTime()));
			break;

		}
		hold.classnum_notice_items.setText(str_date);

		return convertView;
	}

	class HistoryHold {
		private TextView class_notice_items;
		private TextView classname_notice_items;
		private TextView classnum_notice_items;

		public HistoryHold(View v) {
			classname_notice_items = (TextView) v.findViewById(R.id.classname_notice_items);
			classname_notice_items.setTextSize(16);
			classname_notice_items.setPadding(20, 0, 0, 0);
			class_notice_items = (TextView) v.findViewById(R.id.class_notice_items);
			class_notice_items.setVisibility(View.GONE);
			classnum_notice_items = (TextView) v.findViewById(R.id.classnum_notice_items);
		}
	}
}
