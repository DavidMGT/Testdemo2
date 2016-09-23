package com.work.student.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.work.student.R;
import com.work.student.bean.Notice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 通知适配器
 *@author 左丽姬
 */
public class NoticeAdapter extends BaseAdapter {

	private Context context;
	private List<Notice> lists;
	private NoticeHodle nh;
	
	public NoticeAdapter() {
		super();
	}

	public NoticeAdapter(Context context) {
		super();
		this.context = context;
	}

	public NoticeAdapter(Context context, List<Notice> lists) {
		super();
		this.context = context;
		this.lists = lists;
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
		nh=null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.notice_item, null);
			nh=new NoticeHodle(convertView);
			convertView.setTag(nh);
		}else{
			nh=(NoticeHodle) convertView.getTag();
		}
		
		Notice notice=lists.get(position);
		nh.title.setText(notice.getFtitle());
		nh.content.setText(notice.getFcontent());
		nh.feedback.setText("反馈："+notice.getNum()+"/"+notice.getTotal());
		nh.agree.setText(notice.getAgree()+"："+notice.getAgree_num());
		nh.disagree.setText(notice.getDisargee()+"："+notice.getDisargee_num());
		GregorianCalendar gc = new GregorianCalendar(); 
//		Log.i("test", "时间:"+notice.getFtime());
	    gc.setTimeInMillis(notice.getFtime()*1000);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nh.date.setText("发布时间："+sdf.format(gc.getTime()));
		String state="已发布";
		switch (notice.getState()) {
		case 0:
			state="定 时";
			nh.ll_release.setBackgroundResource(R.drawable.notice_release_hurs);
			nh.iv_hurs.setImageResource(R.drawable.hourglass_easyicon);
			break;
		case 1:
			state="未发布";
			nh.ll_release.setBackgroundResource(R.drawable.notice_release_yes);
			nh.iv_hurs.setImageResource(R.drawable.yes_easyicon);
			break;
		case 2:
			state="已发布";
			nh.ll_release.setBackgroundResource(R.drawable.notice_release_yes);
			nh.iv_hurs.setImageResource(R.drawable.yes_easyicon);
			break;
		}
		nh.hurs.setText(state);
		return convertView;
	}

	class NoticeHodle {
		
		private TextView title;
		private TextView content;
		private TextView feedback;
		private TextView agree;
		private TextView disagree;
		private TextView date;
		private TextView hurs;
		private LinearLayout ll_release;
		private ImageView iv_hurs;

		public NoticeHodle(View v){
			title = (TextView) v.findViewById(R.id.tv_title_notice);
			content = (TextView) v.findViewById(R.id.tv_content_notice);
			feedback = (TextView) v.findViewById(R.id.tv_feedback_notice);
			agree = (TextView) v.findViewById(R.id.tv_agree_notice);
			disagree = (TextView) v.findViewById(R.id.tv_disagree_notice);
			date = (TextView) v.findViewById(R.id.tv_date_notice);
			hurs = (TextView) v.findViewById(R.id.tv_hurs_notice);
			ll_release = (LinearLayout) v.findViewById(R.id.ll_release_notice);
			iv_hurs = (ImageView) v.findViewById(R.id.iv_hurs_notice);
		}
	}
}
