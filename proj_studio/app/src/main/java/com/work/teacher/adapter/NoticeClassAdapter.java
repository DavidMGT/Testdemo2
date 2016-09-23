package com.work.teacher.adapter;

import java.util.List;

import com.work.teacher.R;
import com.work.teacher.bean.NoticeClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 通知发布--》》班级列表
 * 
 * @author 左丽姬
 */
public class NoticeClassAdapter extends BaseAdapter {

	private Context context;
	private List<NoticeClass> lncs;
	private NoticeClassHold hold;

	public NoticeClassAdapter() {
		super();
	}

	public NoticeClassAdapter(Context context, List<NoticeClass> lncs) {
		super();
		this.context = context;
		this.lncs = lncs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lncs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lncs.get(position);
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
			hold = new NoticeClassHold(convertView);
			convertView.setTag(hold);
		} else {
			hold = (NoticeClassHold) convertView.getTag();
		}
		NoticeClass noticeClass = lncs.get(position);
		if (noticeClass.isSelect()) {
			hold.class_notice_items.setBackgroundResource(R.drawable.select_pay_yes);
		} else {
			hold.class_notice_items.setBackgroundResource(R.drawable.select_pay_no);
		}
		String t_str = "教师" + noticeClass.getT_select() + "/" + noticeClass.getT_data();
		String x_str = "学生" + noticeClass.getX_select() + "/" + noticeClass.getX_data();
		String p_str = "家长" + noticeClass.getP_select() + "/" + noticeClass.getP_data();
		hold.classname_notice_items.setText(noticeClass.getClassname());
		hold.classnum_notice_items.setText(t_str + " " + x_str + " " + p_str);
		return convertView;
	}

	class NoticeClassHold {
		private TextView class_notice_items;
		private TextView classname_notice_items;
		private TextView classnum_notice_items;

		public NoticeClassHold(View v) {
			class_notice_items = (TextView) v.findViewById(R.id.class_notice_items);
			classname_notice_items = (TextView) v.findViewById(R.id.classname_notice_items);
			classnum_notice_items = (TextView) v.findViewById(R.id.classnum_notice_items);
		}
	}
}
