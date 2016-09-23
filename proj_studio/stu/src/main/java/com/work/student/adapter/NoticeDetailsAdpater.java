package com.work.student.adapter;

import java.util.List;

import com.work.student.R;
import com.work.student.bean.FeedbackDetails;
import com.work.student.tool.IBaes;
import com.work.student.view.AvatarView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

/**
 * 通知详情反馈列表
 * 
 * @author 左丽姬
 */
public class NoticeDetailsAdpater extends BaseAdapter {

	private Context context;
	private List<FeedbackDetails> lists;
	private String argess;
	private String disarg;

	public NoticeDetailsAdpater(Context context, List<FeedbackDetails> lists, String argess, String disarg) {
		super();
		this.context = context;
		this.lists = lists;
		this.argess = argess;
		this.disarg = disarg;
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
		DetailtsHodle hodle = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_notice_details, null);
			hodle = new DetailtsHodle(convertView);
			convertView.setTag(hodle);
		} else {
			hodle = (DetailtsHodle) convertView.getTag();
		}
		FeedbackDetails fDetails = lists.get(position);
		String str = "";
		switch (fDetails.getTsp()) {
		case 0:
			str = "学生";
			break;
		case 1:
			str = "家长";
			break;
		case 2:
			str = "老师";
			break;
		}
		if (fDetails.getValue1() != null && !fDetails.getValue1().equals("null"))
			hodle.name.setText(fDetails.getValue1() + " " + fDetails.getTruename());
		else
			hodle.name.setText(fDetails.getTruename());

		if (fDetails.getMobile() != null && !"".equals(fDetails.getMobile()) && !"null".equals(fDetails.getMobile()))
			hodle.tel.setText(str + "电话：" + fDetails.getMobile());
		else
			hodle.tel.setText(str + "电话：未添加");

		hodle.details.setText("未选择");
		hodle.details.setTextColor(context.getResources().getColor(R.color.weakgrey));
		if ("1".equals(fDetails.getAgree())) {
			hodle.details.setText(argess);
			hodle.details.setTextColor(context.getResources().getColor(R.color.green));
		}
		if ("0".equals(fDetails.getAgree())) {
			hodle.details.setText(disarg);
			hodle.details.setTextColor(context.getResources().getColor(R.color.red));
		}

		FinalBitmap.create(context).display(hodle.avatar_details, IBaes.URL + fDetails.getPhoto());
		return convertView;
	}

	class DetailtsHodle {
		private AvatarView avatar_details;
		private TextView name;
		private TextView tel;
		private TextView details;

		public DetailtsHodle(View v) {
			avatar_details = (AvatarView) v.findViewById(R.id.avatar_details);
			name = (TextView) v.findViewById(R.id.tv_name_details);
			tel = (TextView) v.findViewById(R.id.tv_tel_details);
			details = (TextView) v.findViewById(R.id.tv_details_items);
		}
	}
}
